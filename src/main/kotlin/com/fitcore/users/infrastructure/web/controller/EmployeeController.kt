package com.fitcore.users.infrastructure.web.controller

import com.fitcore.users.domain.model.common.UserId
import com.fitcore.users.domain.port.`in`.employee.FindEmployeeUseCase
import com.fitcore.users.domain.port.`in`.employee.ManageEmployeeUseCase
import com.fitcore.users.infrastructure.web.dto.employee.EmployeeRequestDto
import com.fitcore.users.infrastructure.web.dto.employee.EmployeeResponseDto
import com.fitcore.users.infrastructure.web.dto.employee.EmployeeUpdateDto
import com.fitcore.users.infrastructure.web.dto.employee.EmployeeTerminationDto
import com.fitcore.users.infrastructure.web.mapper.EmployeeDtoMapper
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping("/api/employees")
class EmployeeController(
    private val manageEmployeeUseCase: ManageEmployeeUseCase,
    private val findEmployeeUseCase: FindEmployeeUseCase,
    private val employeeDtoMapper: EmployeeDtoMapper
) {
    
    @PostMapping
    fun createEmployee(@RequestBody request: EmployeeRequestDto): ResponseEntity<EmployeeResponseDto> {
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
    
    @GetMapping
    fun getAllEmployees(): ResponseEntity<List<EmployeeResponseDto>> {
        val employees = findEmployeeUseCase.findAll()
        val responseList = employees.map { employeeDtoMapper.toResponseDto(it) }
        
        return ResponseEntity.ok(responseList)
    }
    
    @GetMapping("/{id}")
    fun getEmployeeById(@PathVariable id: String): ResponseEntity<EmployeeResponseDto> {
        val userId = UserId.of(id)
        val employee = findEmployeeUseCase.findById(userId)
        return ResponseEntity.ok(employeeDtoMapper.toResponseDto(employee!!))
    }
    
    @GetMapping("/email/{email}")
    fun getEmployeeByEmail(@PathVariable email: String): ResponseEntity<EmployeeResponseDto> {
        val employee = findEmployeeUseCase.findByEmail(email)
        return if (employee != null) {
            ResponseEntity.ok(employeeDtoMapper.toResponseDto(employee))
        } else {
            ResponseEntity.notFound().build()
        }
    }
    
    @GetMapping("/cpf/{cpf}")
    fun getEmployeeByCpf(@PathVariable cpf: String): ResponseEntity<EmployeeResponseDto> {
        val employee = findEmployeeUseCase.findByCpf(cpf)
        return if (employee != null) {
            ResponseEntity.ok(employeeDtoMapper.toResponseDto(employee))
        } else {
            ResponseEntity.notFound().build()
        }
    }
    
    @GetMapping("/by-role/{role}")
    fun getEmployeesByRole(@PathVariable role: String): ResponseEntity<List<EmployeeResponseDto>> {
        val employees = findEmployeeUseCase.findByRole(role)
        val responseList = employees.map { employeeDtoMapper.toResponseDto(it) }
        return ResponseEntity.ok(responseList)
    }
    
    @GetMapping("/active")
    fun getActiveEmployees(): ResponseEntity<List<EmployeeResponseDto>> {
        val employees = findEmployeeUseCase.findAllActive()
        val responseList = employees.map { employeeDtoMapper.toResponseDto(it) }
        return ResponseEntity.ok(responseList)
    }
    
    @PutMapping("/{id}")
    fun updateEmployee(
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
    
    @PatchMapping("/{id}/status")
    fun updateEmployeeStatus(
        @PathVariable id: String,
        @RequestBody request: EmployeeTerminationDto
    ): ResponseEntity<EmployeeResponseDto> {
        val userId = UserId.of(id)
        
        val employee = if (request.terminationDate != null) {
            // Desativar funcionário
            manageEmployeeUseCase.terminateEmployee(userId, request.terminationDate)
        } else {
            // Reativar funcionário
            manageEmployeeUseCase.reactivateEmployee(userId)
        }
        
        return ResponseEntity.ok(employeeDtoMapper.toResponseDto(employee))
    }
    
    @DeleteMapping("/{id}")
    fun deleteEmployee(@PathVariable id: String): ResponseEntity<Void> {
        val userId = UserId.of(id)
        val deleted = manageEmployeeUseCase.deleteEmployee(userId)
        return if (deleted) {
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }
}