package com.fitcore.users.application.exception

class CpfAlreadyRegisteredException(val cpf: String) : RuntimeException("CPF $cpf already registered")
