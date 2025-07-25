package com.fitcore.users.infrastructure.web.controller

import com.fitcore.users.domain.model.common.UserId
import com.fitcore.users.domain.port.`in`.employee.FindEmployeeUseCase
import com.fitcore.users.domain.port.`in`.employee.ManageEmployeeUseCase
import com.fitcore.users.infrastructure.config.swagger.documentation.EmployeeControllerDoc
import com.fitcore.users.infrastructure.web.dto.employee.EmployeeRequestDto
import com.fitcore.users.infrastructure.web.dto.employee.EmployeeResponseDto
import com.fitcore.users.infrastructure.web.dto.employee.EmployeeUpdateDto
import com.fitcore.users.infrastructure.web.dto.employee.EmployeeTerminationDto
import com.fitcore.users.infrastructure.web.dto.employee.ChangeRoleDto
import com.fitcore.users.infrastructure.web.mapper.EmployeeDtoMapper
import org.springframework.web.multipart.MultipartFile
import com.fitcore.users.infrastructure.service.StorageService
import com.fitcore.users.application.exception.ProfilePictureNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping("/api/employees")
class EmployeeController(
    private val manageEmployeeUseCase: ManageEmployeeUseCase,
    private val findEmployeeUseCase: FindEmployeeUseCase,
    private val employeeDtoMapper: EmployeeDtoMapper,
    private val storageService: StorageService
) : EmployeeControllerDoc {
    
    @PostMapping
    override fun createEmployee(@RequestBody request: EmployeeRequestDto): ResponseEntity<EmployeeResponseDto> {
        val employee = manageEmployeeUseCase.registerEmployee(
            name = request.name,
            email = request.email,
            cpf = request.cpf,
            birthDate = request.birthDate,
            phone = request.phone,
            roleType = request.roleType,
            hireDate = request.hireDate ?: LocalDate.now()
        )
        
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(employeeDtoMapper.toResponseDto(employee))
    }

    @PostMapping("/{id}/profile")
    override fun uploadProfile(
        @PathVariable id: String,
        @RequestParam("file") file: MultipartFile
    ): ResponseEntity<EmployeeResponseDto> {
        val userId = UserId.of(id)
        val employee = findEmployeeUseCase.findById(userId)
            ?: return ResponseEntity.notFound().build()

        // Upload para o MinIO
        val objectKey = storageService.uploadProfile(id, file)

        // Atualize o campo profileUrl no domínio e persista
        val savedEmployee = manageEmployeeUseCase.updateEmployee(
            id = userId,
            name = employee.name,
            email = employee.email,
            phone = employee.phone,
            roleType = employee.role.name,
            profileUrl = objectKey 
        )

        return ResponseEntity.ok(employeeDtoMapper.toResponseDto(savedEmployee))
    }

    @PutMapping("/{id}/profile")
    override fun updateProfile(
        @PathVariable id: String,
        @RequestParam("file") file: MultipartFile
    ): ResponseEntity<EmployeeResponseDto> {
        val userId = UserId.of(id)
        val employee = findEmployeeUseCase.findById(userId)
            ?: return ResponseEntity.notFound().build()

        // Verificar e remover a foto de perfil anterior, se existir
        employee.profileUrl?.let { 
            storageService.deleteProfile(it)
        }

        // Upload para o MinIO
        val objectKey = storageService.uploadProfile(id, file)

        // Atualize o campo profileUrl no domínio
        val savedEmployee = manageEmployeeUseCase.updateEmployee(
            id = userId,
            name = employee.name,
            email = employee.email,
            phone = employee.phone,
            roleType = employee.role.name,
            profileUrl = objectKey 
        )

        return ResponseEntity.ok(employeeDtoMapper.toResponseDto(savedEmployee))
    }

    @DeleteMapping("/{id}/profile")
    override fun deleteProfile(@PathVariable id: String): ResponseEntity<EmployeeResponseDto> {
        val userId = UserId.of(id)
        val employee = findEmployeeUseCase.findById(userId)
            ?: return ResponseEntity.notFound().build()
        
        // Se existe uma foto, exclua-a
        if (employee.profileUrl != null) {
            storageService.deleteProfile(employee.profileUrl)
            
            // Atualize o campo profileUrl para null no domínio
            val savedEmployee = manageEmployeeUseCase.updateEmployee(
                id = userId,
                name = employee.name,
                email = employee.email,
                phone = employee.phone,
                roleType = employee.role.name,
                profileUrl = null // Removendo a referencia da url 
            )
                
            return ResponseEntity.ok(employeeDtoMapper.toResponseDto(savedEmployee))
        } else {
            // Se não existe foto, retorne 204 No Content
            return ResponseEntity.noContent().build()
        }
    }
        
    @GetMapping("/{id}/profile-url")
    override fun getProfileUrl(@PathVariable id: String): ResponseEntity<Map<String, String>> {
        val userId = UserId.of(id)
        val employee = findEmployeeUseCase.findById(userId)
            ?: return ResponseEntity.notFound().build()
        val key = employee.profileUrl ?: throw ProfilePictureNotFoundException(id)
        val url = storageService.getPresignedUrl(key)
        
        return ResponseEntity.ok(mapOf("profileUrl" to url))
    }
    
    @GetMapping
    override fun getAllEmployees(): ResponseEntity<List<EmployeeResponseDto>> {
        val employees = findEmployeeUseCase.findAll()
        val responseList = employees.map { employeeDtoMapper.toResponseDto(it) }
        
        return ResponseEntity.ok(responseList)
    }
    
    @GetMapping("/{id}")
    override fun getEmployeeById(@PathVariable id: String): ResponseEntity<EmployeeResponseDto> {
        val userId = UserId.of(id)
        val employee = findEmployeeUseCase.findById(userId)
        return ResponseEntity.ok(employeeDtoMapper.toResponseDto(employee!!))
    }
    
    @GetMapping("/email/{email}")
    override fun getEmployeeByEmail(@PathVariable email: String): ResponseEntity<EmployeeResponseDto> {
        val employee = findEmployeeUseCase.findByEmail(email)
        return if (employee != null) {
            ResponseEntity.ok(employeeDtoMapper.toResponseDto(employee))
        } else {
            ResponseEntity.notFound().build()
        }
    }
    
    @GetMapping("/cpf/{cpf}")
    override fun getEmployeeByCpf(@PathVariable cpf: String): ResponseEntity<EmployeeResponseDto> {
        val employee = findEmployeeUseCase.findByCpf(cpf)
        return if (employee != null) {
            ResponseEntity.ok(employeeDtoMapper.toResponseDto(employee))
        } else {
            ResponseEntity.notFound().build()
        }
    }
    
    @GetMapping("/by-role/{role}")
    override fun getEmployeesByRole(@PathVariable role: String): ResponseEntity<List<EmployeeResponseDto>> {
        val employees = findEmployeeUseCase.findByRole(role)
        val responseList = employees.map { employeeDtoMapper.toResponseDto(it) }
        return ResponseEntity.ok(responseList)
    }
    
    @GetMapping("/active")
    override fun getActiveEmployees(): ResponseEntity<List<EmployeeResponseDto>> {
        val employees = findEmployeeUseCase.findAllActive()
        val responseList = employees.map { employeeDtoMapper.toResponseDto(it) }
        return ResponseEntity.ok(responseList)
    }
    
    @PutMapping("/{id}")
    override fun updateEmployee(
        @PathVariable id: String,
        @RequestBody request: EmployeeUpdateDto
    ): ResponseEntity<EmployeeResponseDto> {
        val userId = UserId.of(id)
        val employee = manageEmployeeUseCase.updateEmployee(
            id = userId,
            name = request.name,
            email = request.email,
            phone = request.phone,
            roleType = request.roleType
        )
        return ResponseEntity.ok(employeeDtoMapper.toResponseDto(employee))
    }

    @PatchMapping("/{id}/role")
    override fun changeRole(
        @PathVariable id: String,
        @RequestBody request: ChangeRoleDto
    ): ResponseEntity<EmployeeResponseDto> {
        val userId = UserId.of(id)
        val employee = manageEmployeeUseCase.changeRole(userId, request.roleType)
        return ResponseEntity.ok(employeeDtoMapper.toResponseDto(employee))
    }
    
    @PatchMapping("/{id}/activate")
    override fun activateEmployee(
        @PathVariable id: String
    ): ResponseEntity<EmployeeResponseDto> {
        val userId = UserId.of(id)
        val employee = manageEmployeeUseCase.reactivateEmployee(userId)
        return ResponseEntity.ok(employeeDtoMapper.toResponseDto(employee))
    }

    @PatchMapping("/{id}/deactivate")
    override fun deactivateEmployee(
        @PathVariable id: String,
        @RequestBody request: EmployeeTerminationDto
    ): ResponseEntity<EmployeeResponseDto> {
        val userId = UserId.of(id)
        if (request.terminationDate == null) {
            return ResponseEntity.badRequest().build()
        }
        val employee = manageEmployeeUseCase.terminateEmployee(userId, request.terminationDate)
        return ResponseEntity.ok(employeeDtoMapper.toResponseDto(employee))
    }
    
    @DeleteMapping("/{id}")
    override fun deleteEmployee(@PathVariable id: String): ResponseEntity<Void> {
        val userId = UserId.of(id)
        val deleted = manageEmployeeUseCase.deleteEmployee(userId)
        return if (deleted) {
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }
}