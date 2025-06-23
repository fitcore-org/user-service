package com.fitcore.users.infrastructure.web.controller

import com.fitcore.users.domain.port.`in`.student.FindStudentUseCase
import com.fitcore.users.domain.port.`in`.student.ManageStudentUseCase
import com.fitcore.users.infrastructure.web.dto.student.StudentRequestDto
import com.fitcore.users.infrastructure.web.dto.student.StudentResponseDto
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
}