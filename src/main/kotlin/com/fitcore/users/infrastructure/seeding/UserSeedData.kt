package com.fitcore.users.infrastructure.seeding

import com.fitcore.users.domain.model.student.Student
import com.fitcore.users.domain.model.student.StudentPlan
import com.fitcore.users.domain.model.employee.Employee
import com.fitcore.users.domain.model.employee.Role
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.Month
import kotlin.random.Random

@Component
class UserSeedData {

    private val firstNames = listOf("Ana", "Bruno", "Carlos", "Daniela", "Eduardo", "Fernanda", "Gabriel", "Helena", "Igor", "Juliana", "Lucas", "Mariana", "Nicolas", "Olivia", "Pedro", "Quintino", "Rafaela", "Sergio", "Tatiana", "Victor")
    private val lastNames = listOf("Silva", "Santos", "Oliveira", "Souza", "Rodrigues", "Ferreira", "Alves", "Pereira", "Lima", "Gomes", "Ribeiro", "Martins", "Costa", "Melo")

    fun getStudents(): List<Student> {
        val students = mutableListOf<Student>()
        val today = LocalDate.of(2025, Month.JULY, 18)

        val registrationsPerMonth = mapOf(
            5 to 12, 4 to 13, 3 to 7, 2 to 8, 1 to 15, 0 to 5
        )

        var studentIndex = 0
        registrationsPerMonth.forEach { (monthOffset, studentCount) ->
            repeat(studentCount) {
                val name = "${firstNames.random()} ${lastNames.random()}"
                val registrationDate = today.minusMonths(monthOffset.toLong()).withDayOfMonth(Random.nextInt(1, 28))
                val registrationDateTime = registrationDate.atTime(Random.nextInt(7, 22), Random.nextInt(0, 60))

                students.add(Student.createWithRegistrationDate(
                    name = name,
                    email = "student${studentIndex + 1}@fitcore.com",
                    cpf = generateValidCpf(formatted = true),
                    birthDate = LocalDate.of(1990 + (studentIndex % 15), (studentIndex % 12) + 1, (studentIndex % 28) + 1),
                    phone = "(11) 91234-${String.format("%04d", studentIndex + 1)}",
                    plan = if (studentIndex % 3 == 0) StudentPlan.PREMIUM else StudentPlan.BASIC,
                    weight = Random.nextDouble(55.0, 95.0),
                    height = Random.nextInt(155, 190),
                    registrationDate = registrationDateTime
                ))
                studentIndex++
            }
        }
        return students
    }

    fun getEmployees(): List<Employee> {
        val employees = mutableListOf<Employee>()
        var employeeIndex = 0

        val roleDistribution = mapOf(
            Role.MANAGER to 2,
            Role.RECEPTIONIST to 4,
            Role.INSTRUCTOR to 14
        )

        roleDistribution.forEach { (role, count) ->
            repeat(count) {
                val name = "${firstNames.random()} ${lastNames.random()}"
                employees.add(Employee.create(
                    name = name,
                    email = "${role.name.lowercase()}${employeeIndex++}@fitcore.com",
                    cpf = generateValidCpf(formatted = true),
                    birthDate = LocalDate.of(Random.nextInt(1980, 2000), Random.nextInt(1, 13), Random.nextInt(1, 29)),
                    phone = "(11) 98765-${String.format("%04d", employeeIndex)}",
                    role = role,
                    hireDate = LocalDate.now().minusMonths(Random.nextLong(3, 36))
                ))
            }
        }
        return employees
    }

    /**
     * FUNÇÃO AUXILIAR: Gera um CPF matematicamente válido para testes.
     */
    private fun generateValidCpf(formatted: Boolean): String {
        val n = List(9) { Random.nextInt(0, 10) }
        
        val d1 = n.mapIndexed { i, digit -> digit * (10 - i) }.sum().let { (it * 10 % 11) % 10 }
        val d2 = (n + d1).mapIndexed { i, digit -> digit * (11 - i) }.sum().let { (it * 10 % 11) % 10 }

        val cpf = "${n.joinToString("")}$d1$d2"

        return if (formatted) {
            cpf.replace(Regex("(\\d{3})(\\d{3})(\\d{3})(\\d{2})"), "$1.$2.$3-$4")
        } else {
            cpf
        }
    }
}