package com.fitcore.users.application.exception

// Exceções relacionadas a estudantes
class EmailAlreadyRegisteredException(email: String) : 
    RuntimeException("E-mail $email already registered")

class CpfAlreadyRegisteredException(cpf: String) : 
    RuntimeException("CPF $cpf already registered")

class StudentNotFoundException(id: String) : 
    RuntimeException("Student with ID $id not found")

// Exceções relacionadas a funcionários 
class EmployeeNotFoundException(id: String) : 
    RuntimeException("Employee with ID $id not found")

// Adicione esta exceção junto com as outras no arquivo
class ProfilePictureNotFoundException(userId: String) : 
    RuntimeException("User with ID $userId does not have a profile picture")