package com.fitcore.users.infrastructure.web.controller

import com.fitcore.users.domain.model.common.UserId
import com.fitcore.users.domain.port.`in`.student.FindStudentUseCase
import com.fitcore.users.domain.port.`in`.student.ManageStudentUseCase
import com.fitcore.users.infrastructure.web.dto.student.PhysicalDataUpdateDto
import com.fitcore.users.infrastructure.web.dto.student.StudentRequestDto
import com.fitcore.users.infrastructure.web.dto.student.StudentResponseDto
import com.fitcore.users.infrastructure.web.dto.student.StudentUpdateDto
import com.fitcore.users.infrastructure.web.mapper.StudentDtoMapper
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/students")
class StudentController(
    private val manageStudentUseCase: ManageStudentUseCase,
    private val findStudentUseCase: FindStudentUseCase,
    private val studentDtoMapper: StudentDtoMapper
) {
    
    @PostMapping
    fun createStudent(@RequestBody request: StudentRequestDto): ResponseEntity<StudentResponseDto> {
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
    
    @GetMapping
    fun getAllStudents(): ResponseEntity<List<StudentResponseDto>> {
        val students = findStudentUseCase.findAll()
        val responseList = students.map { studentDtoMapper.toResponseDto(it) }
        
        return ResponseEntity.ok(responseList)
    }
    
    @GetMapping("/{id}")
    fun getStudentById(@PathVariable id: String): ResponseEntity<StudentResponseDto> {
        val userId = UserId.of(id)
        val student = findStudentUseCase.findById(userId)
        return ResponseEntity.ok(studentDtoMapper.toResponseDto(student!!))
    }
    
    @GetMapping("/email/{email}")
    fun getStudentByEmail(@PathVariable email: String): ResponseEntity<StudentResponseDto> {
        val student = findStudentUseCase.findByEmail(email)
        return if (student != null) {
            ResponseEntity.ok(studentDtoMapper.toResponseDto(student))
        } else {
            ResponseEntity.notFound().build()
        }
    }
    
    @GetMapping("/cpf/{cpf}")
    fun getStudentByCpf(@PathVariable cpf: String): ResponseEntity<StudentResponseDto> {
        val student = findStudentUseCase.findByCpf(cpf)
        return if (student != null) {
            ResponseEntity.ok(studentDtoMapper.toResponseDto(student))
        } else {
            ResponseEntity.notFound().build()
        }
    }
    
    @GetMapping("/plan/{planType}")
    fun getStudentsByPlan(@PathVariable planType: String): ResponseEntity<List<StudentResponseDto>> {
        val students = findStudentUseCase.findByPlan(planType)
        val responseList = students.map { studentDtoMapper.toResponseDto(it) }
        return ResponseEntity.ok(responseList)
    }
    
    @GetMapping("/active")
    fun getActiveStudents(): ResponseEntity<List<StudentResponseDto>> {
        val students = findStudentUseCase.findAllActive()
        val responseList = students.map { studentDtoMapper.toResponseDto(it) }
        return ResponseEntity.ok(responseList)
    }
    
    @PutMapping("/{id}")
    fun updateStudent(
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
    fun updatePhysicalData(
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
    
    @PatchMapping("/{id}/activate")
    fun activateStudent(@PathVariable id: String): ResponseEntity<StudentResponseDto> {
        val userId = UserId.of(id)
        val student = manageStudentUseCase.activateStudent(userId)
        return ResponseEntity.ok(studentDtoMapper.toResponseDto(student))
    }
    
    @PatchMapping("/{id}/deactivate")
    fun deactivateStudent(@PathVariable id: String): ResponseEntity<StudentResponseDto> {
        val userId = UserId.of(id)
        val student = manageStudentUseCase.deactivateStudent(userId)
        return ResponseEntity.ok(studentDtoMapper.toResponseDto(student))
    }
    
    @DeleteMapping("/{id}")
    fun deleteStudent(@PathVariable id: String): ResponseEntity<Void> {
        val userId = UserId.of(id)
        val deleted = manageStudentUseCase.deleteStudent(userId)
        return if (deleted) {
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }
}