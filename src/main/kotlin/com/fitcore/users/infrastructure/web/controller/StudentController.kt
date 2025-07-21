package com.fitcore.users.infrastructure.web.controller

import com.fitcore.users.domain.model.common.UserId
import com.fitcore.users.domain.port.`in`.student.FindStudentUseCase
import com.fitcore.users.domain.port.`in`.student.ManageStudentUseCase
import com.fitcore.users.infrastructure.config.swagger.documentation.StudentControllerDoc
import com.fitcore.users.infrastructure.web.dto.student.PhysicalDataUpdateDto
import com.fitcore.users.infrastructure.web.dto.student.StudentRequestDto
import com.fitcore.users.infrastructure.web.dto.student.StudentResponseDto
import com.fitcore.users.infrastructure.web.dto.student.StudentUpdateDto
import com.fitcore.users.infrastructure.web.dto.student.ChangePlanDto
import com.fitcore.users.infrastructure.web.mapper.StudentDtoMapper
import org.springframework.web.multipart.MultipartFile
import com.fitcore.users.infrastructure.service.StorageService
import com.fitcore.users.application.exception.ProfilePictureNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/students")
class StudentController(
    private val manageStudentUseCase: ManageStudentUseCase,
    private val findStudentUseCase: FindStudentUseCase,
    private val studentDtoMapper: StudentDtoMapper,
    private val storageService: StorageService
) : StudentControllerDoc {
    
    @PostMapping
    override fun createStudent(@RequestBody request: StudentRequestDto): ResponseEntity<StudentResponseDto> {
        val student = manageStudentUseCase.registerStudent(
            name = request.name,
            email = request.email,
            cpf = request.cpf,
            birthDate = request.birthDate,
            phone = request.phone,
            planType = request.planType,
            weight = request.weight,
            height = request.height
        )
        
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(studentDtoMapper.toResponseDto(student))
    }

    @PostMapping("/{id}/profile")
    override fun uploadProfile(
        @PathVariable id: String,
        @RequestParam("file") file: MultipartFile
    ): ResponseEntity<StudentResponseDto> {
        val userId = UserId.of(id)
        val student = findStudentUseCase.findById(userId)
            ?: return ResponseEntity.notFound().build()

        // Upload para o MinIO
        val objectKey = storageService.uploadProfile(id, file)

        // Atualize o campo profileUrl no domínio e persista
        val savedStudent = manageStudentUseCase.updateStudent(
            id = userId,
            name = student.name,
            email = student.email,
            phone = student.phone,
            planType = student.plan.name,
            weight = student.weight,
            height = student.height,
            profileUrl = objectKey 
        )

        return ResponseEntity.ok(studentDtoMapper.toResponseDto(savedStudent))
    }

    @PutMapping("/{id}/profile")
    override fun updateProfile(
        @PathVariable id: String,
        @RequestParam("file") file: MultipartFile
    ): ResponseEntity<StudentResponseDto> {
        val userId = UserId.of(id)
        val student = findStudentUseCase.findById(userId)
            ?: return ResponseEntity.notFound().build()

        // Verificar e remover a foto de perfil anterior, se existir
        student.profileUrl?.let { 
            storageService.deleteProfile(it)
        }

        // Upload para o MinIO
        val objectKey = storageService.uploadProfile(id, file)

        // Atualize o campo profileUrl no domínio
        val savedStudent = manageStudentUseCase.updateStudent(
            id = userId,
            name = student.name,
            email = student.email,
            phone = student.phone,
            planType = student.plan.name,
            weight = student.weight,
            height = student.height,
            profileUrl = objectKey
        )

        return ResponseEntity.ok(studentDtoMapper.toResponseDto(savedStudent))
    }

    @DeleteMapping("/{id}/profile")
    override fun deleteProfile(@PathVariable id: String): ResponseEntity<StudentResponseDto> {
        val userId = UserId.of(id)
        val student = findStudentUseCase.findById(userId)
            ?: return ResponseEntity.notFound().build()
        
        // Se existe uma foto, exclua-a
        if (student.profileUrl != null) {
            storageService.deleteProfile(student.profileUrl)
            
            // Atualize o campo profileUrl para null no domínio
            val savedStudent = manageStudentUseCase.updateStudent(
                id = userId,
                name = student.name,
                email = student.email,
                phone = student.phone,
                planType = student.plan.name,
                weight = student.weight,
                height = student.height,
                profileUrl = null // Define como null para remover a referência
            )
            
            return ResponseEntity.ok(studentDtoMapper.toResponseDto(savedStudent))
        } else {
            // Se não existe foto, retorne 204 No Content
            return ResponseEntity.noContent().build()
        }
    }
        
    @GetMapping("/{id}/profile-url")
    override fun getProfileUrl(@PathVariable id: String): ResponseEntity<Map<String, String>> {
        val userId = UserId.of(id)
        val student = findStudentUseCase.findById(userId)
            ?: return ResponseEntity.notFound().build()
        val key = student.profileUrl ?: throw ProfilePictureNotFoundException(id)
        val url = storageService.getPresignedUrl(key)
        
        return ResponseEntity.ok(mapOf("profileUrl" to url))
    }
    
    @GetMapping
    override fun getAllStudents(): ResponseEntity<List<StudentResponseDto>> {
        val students = findStudentUseCase.findAll()
        val responseList = students.map { studentDtoMapper.toResponseDto(it) }
        
        return ResponseEntity.ok(responseList)
    }
    
    @GetMapping("/{id}")
    override fun getStudentById(@PathVariable id: String): ResponseEntity<StudentResponseDto> {
        val userId = UserId.of(id)
        val student = findStudentUseCase.findById(userId)
        return ResponseEntity.ok(studentDtoMapper.toResponseDto(student!!))
    }
    
    @GetMapping("/email/{email}")
    override fun getStudentByEmail(@PathVariable email: String): ResponseEntity<StudentResponseDto> {
        val student = findStudentUseCase.findByEmail(email)
        return if (student != null) {
            ResponseEntity.ok(studentDtoMapper.toResponseDto(student))
        } else {
            ResponseEntity.notFound().build()
        }
    }
    
    @GetMapping("/cpf/{cpf}")
    override fun getStudentByCpf(@PathVariable cpf: String): ResponseEntity<StudentResponseDto> {
        val student = findStudentUseCase.findByCpf(cpf)
        return if (student != null) {
            ResponseEntity.ok(studentDtoMapper.toResponseDto(student))
        } else {
            ResponseEntity.notFound().build()
        }
    }
    
    @GetMapping("/plan/{planType}")
    override fun getStudentsByPlan(@PathVariable planType: String): ResponseEntity<List<StudentResponseDto>> {
        val students = findStudentUseCase.findByPlan(planType)
        val responseList = students.map { studentDtoMapper.toResponseDto(it) }
        return ResponseEntity.ok(responseList)
    }
    
    @GetMapping("/active")
    override fun getActiveStudents(): ResponseEntity<List<StudentResponseDto>> {
        val students = findStudentUseCase.findAllActive()
        val responseList = students.map { studentDtoMapper.toResponseDto(it) }
        return ResponseEntity.ok(responseList)
    }
    
    @PutMapping("/{id}")
    override fun updateStudent(
        @PathVariable id: String,
        @RequestBody request: StudentUpdateDto
    ): ResponseEntity<StudentResponseDto> {
        val userId = UserId.of(id)
        val student = manageStudentUseCase.updateStudent(
            id = userId,
            name = request.name,
            email = request.email,
            phone = request.phone,
            planType = request.planType,
            weight = request.weight,
            height = request.height
        )
        return ResponseEntity.ok(studentDtoMapper.toResponseDto(student))
    }
    
    @PatchMapping("/{id}/physical-data")
    override fun updatePhysicalData(
        @PathVariable id: String,
        @RequestBody request: PhysicalDataUpdateDto
    ): ResponseEntity<StudentResponseDto> {
        val userId = UserId.of(id)
        val student = manageStudentUseCase.updatePhysicalData(
            id = userId,
            weight = request.weight,
            height = request.height
        )
        return ResponseEntity.ok(studentDtoMapper.toResponseDto(student))
    }

    @PatchMapping("/{id}/plan")
    override fun changePlan(
        @PathVariable id: String,
        @RequestBody request: ChangePlanDto
    ): ResponseEntity<StudentResponseDto> {
        val userId = UserId.of(id)
        val student = manageStudentUseCase.changePlan(userId, request.planType)
        return ResponseEntity.ok(studentDtoMapper.toResponseDto(student))
    }
    
    @PatchMapping("/{id}/activate")
    override fun activateStudent(@PathVariable id: String): ResponseEntity<StudentResponseDto> {
        val userId = UserId.of(id)
        val student = manageStudentUseCase.activateStudent(userId)
        return ResponseEntity.ok(studentDtoMapper.toResponseDto(student))
    }
    
    @PatchMapping("/{id}/deactivate")
    override fun deactivateStudent(@PathVariable id: String): ResponseEntity<StudentResponseDto> {
        val userId = UserId.of(id)
        val student = manageStudentUseCase.deactivateStudent(userId)
        return ResponseEntity.ok(studentDtoMapper.toResponseDto(student))
    }
    
    @DeleteMapping("/{id}")
    override fun deleteStudent(@PathVariable id: String): ResponseEntity<Void> {
        val userId = UserId.of(id)
        val deleted = manageStudentUseCase.deleteStudent(userId)
        return if (deleted) {
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }
}