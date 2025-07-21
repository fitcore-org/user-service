package com.fitcore.users.infrastructure.config.swagger

object SwaggerConstants {
    
    // Tags
    const val STUDENT_TAG = "Estudantes"
    const val EMPLOYEE_TAG = "Funcionários"
    const val PROFILE_TAG = "Profile Management"
    
    // Common descriptions
    const val ID_PARAM_DESCRIPTION = "ID único do usuário"
    const val EMAIL_PARAM_DESCRIPTION = "Email do usuário"
    const val CPF_PARAM_DESCRIPTION = "CPF do usuário (formato: XXX.XXX.XXX-XX)"
    const val PLAN_PARAM_DESCRIPTION = "Tipo do plano"
    const val ROLE_PARAM_DESCRIPTION = "Tipo do cargo"
    const val FILE_PARAM_DESCRIPTION = "Arquivo de imagem para foto de perfil"
    
    // HTTP Status descriptions
    const val STATUS_200_DESCRIPTION = "Operação realizada com sucesso"
    const val STATUS_201_DESCRIPTION = "Recurso criado com sucesso"
    const val STATUS_204_DESCRIPTION = "Operação realizada com sucesso - sem conteúdo"
    const val STATUS_400_DESCRIPTION = "Dados de entrada inválidos"
    const val STATUS_404_DESCRIPTION = "Recurso não encontrado"
    const val STATUS_409_DESCRIPTION = "Conflito - recurso já existe"
    const val STATUS_500_DESCRIPTION = "Erro interno do servidor"
}
