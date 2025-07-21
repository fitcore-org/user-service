package com.fitcore.users.infrastructure.config

import com.fitcore.users.infrastructure.config.swagger.SwaggerConstants
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import io.swagger.v3.oas.models.servers.Server
import io.swagger.v3.oas.models.tags.Tag
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {
    
    @Bean
    fun openAPI(): OpenAPI {
        return OpenAPI()
            .info(
                Info()
                    .title("FitCore User Service API")
                    .description("""
                        ## API para gerenciamento de usuários do sistema FitCore
                        
                        Esta API é responsável por gerenciar todas as operações relacionadas a:
                        - **Estudantes**: Cadastro, atualização, consulta e gerenciamento de planos
                        - **Funcionários**: Cadastro, atualização, consulta e gerenciamento de cargos
                        - **Profile Management**: Upload, atualização e remoção de fotos de perfil
                        
                        ### Recursos Principais:
                        - Validação de dados de entrada (CPF, email, telefone)
                        - Gerenciamento de status (ativo/inativo)
                        - Upload seguro de imagens de perfil
                        - Consultas por diferentes critérios (email, CPF, plano, cargo)
                        - Eventos de domínio para integração com outros serviços
                        ```
                        
                        ### Formatos de Dados
                        - **Datas**: ISO 8601 (YYYY-MM-DD)
                        - **CPF**: XXX.XXX.XXX-XX
                        - **Telefone**: (XX) XXXXX-XXXX
                        - **IDs**: UUID v4
                    """.trimIndent())
                    .version("1.0.0")
                    .contact(
                        Contact()
                            .name("FitCore Development Team")
                            .email("dev@fitcore.com")
                            .url("https://fitcore.com/docs")
                    )
                    .license(
                        License()
                            .name("MIT License")
                            .url("https://opensource.org/licenses/MIT")
                    )
            )
            .servers(
                listOf(
                    Server()
                        .url("http://localhost:8080")
                        .description("Ambiente de Desenvolvimento Local"),
                    Server()
                        .url("https://dev-api.fitcore.com")
                        .description("Ambiente de Desenvolvimento"),
                    Server()
                        .url("https://staging-api.fitcore.com")
                        .description("Ambiente de Homologação"),
                    Server()
                        .url("https://api.fitcore.com")
                        .description("Ambiente de Produção")
                )
            )
            .tags(
                listOf(
                    Tag()
                        .name(SwaggerConstants.STUDENT_TAG)
                        .description("""
                            ### Gerenciamento de Estudantes
                            
                            Operações para gerenciar estudantes do sistema FitCore, incluindo:
                            - Cadastro e atualização de dados pessoais
                            - Gerenciamento de planos (BASIC, PREMIUM, VIP)
                            - Controle de dados físicos (peso e altura)
                            - Ativação/desativação de contas
                            - Gerenciamento de fotos de perfil
                            
                            **Planos Disponíveis:**
                            - `BASIC`: Acesso às áreas básicas da academia
                            - `PREMIUM`: Acesso completo + aulas em grupo
                        """.trimIndent()),
                    Tag()
                        .name(SwaggerConstants.EMPLOYEE_TAG)
                        .description("""
                            ### Gerenciamento de Funcionários
                            
                            Operações para gerenciar funcionários do sistema FitCore, incluindo:
                            - Cadastro e atualização de dados pessoais
                            - Gerenciamento de cargos e permissões
                            - Controle de contratação e desligamento
                            - Ativação/reativação de contas
                            - Gerenciamento de fotos de perfil
                            
                            **Cargos Disponíveis:**
                            - `ADMIN`: Administrador com acesso total ao sistema
                            - `MANAGER`: Gerente com acesso administrativo limitado
                            - `INSTRUCTOR`: Instrutor com acesso às suas turmas e alunos
                            - `RECEPTIONIST`: Recepcionista com acesso ao atendimento
                        """.trimIndent()),
                    Tag()
                        .name(SwaggerConstants.PROFILE_TAG)
                        .description("""
                            ### Gerenciamento de Fotos de Perfil
                            
                            Operações para gerenciar as fotos de perfil dos usuários:
                            - Upload de novas fotos
                            - Atualização de fotos existentes
                            - Remoção de fotos
                            - Geração de URLs temporárias para acesso
                            
                            **Formatos Aceitos:** JPG, JPEG, PNG, WEBP
                            **Tamanho Máximo:** 5MB
                            **Resolução Recomendada:** 400x400px
                        """.trimIndent())
                )
            )
    }
}
