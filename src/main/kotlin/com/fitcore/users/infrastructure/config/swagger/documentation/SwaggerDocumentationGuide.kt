package com.fitcore.users.infrastructure.config.swagger.documentation

/**
 * Guia para criar documentação Swagger para novos controllers.
 * 
 * Este arquivo serve como template e guia para desenvolvedores que precisam
 * documentar novos controllers seguindo o padrão estabelecido.
 */

/*
=====================================================================================
PASSO A PASSO PARA DOCUMENTAR UM NOVO CONTROLLER
=====================================================================================

1. CRIAR INTERFACE DE DOCUMENTAÇÃO
   - Crie uma nova interface no pacote: config.swagger.documentation
   - Nome: [NomeController]Doc (ex: PaymentControllerDoc)
   - Adicione a anotação @Tag com o nome e descrição

2. IMPLEMENTAR A INTERFACE NO CONTROLLER
   - Faça o controller implementar a interface criada
   - Não adicione nenhuma anotação Swagger diretamente no controller

3. DOCUMENTAR CADA MÉTODO
   - Use @Operation para descrever o método
   - Use @Parameter para documentar parâmetros
   - Use @RequestBody para documentar o corpo da requisição
   - Use @ApiResponses para documentar as respostas possíveis

4. CRIAR SCHEMAS CUSTOMIZADOS (OPCIONAL)
   - Crie classes no pacote: config.swagger.schema
   - Use @Schema para adicionar descrições e exemplos
   - Nome: [Dominio]Schemas (ex: PaymentSchemas)

5. ATUALIZAR CONSTANTES (SE NECESSÁRIO)
   - Adicione novas tags em SwaggerConstants
   - Adicione novas descrições reutilizáveis

=====================================================================================
EXEMPLO DE IMPLEMENTAÇÃO
=====================================================================================

// 1. Interface de documentação
@Tag(name = "Pagamentos", description = "Operações para gerenciamento de pagamentos")
interface PaymentControllerDoc {
    
    @Operation(
        summary = "Processar pagamento",
        description = "Processa um novo pagamento para um estudante"
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "201",
            description = "Pagamento processado com sucesso",
            content = [Content(schema = Schema(implementation = PaymentResponseDto::class))]
        ),
        ApiResponse(
            responseCode = "400",
            description = "Dados de pagamento inválidos",
            content = [Content()]
        )
    ])
    fun processPayment(
        @RequestBody(
            description = "Dados do pagamento",
            required = true,
            content = [Content(schema = Schema(implementation = PaymentRequestDto::class))]
        )
        request: PaymentRequestDto
    ): ResponseEntity<PaymentResponseDto>
}

// 2. Controller implementando a interface
@RestController
@RequestMapping("/api/payments")
class PaymentController(
    private val paymentService: PaymentService
) : PaymentControllerDoc {
    
    @PostMapping
    override fun processPayment(request: PaymentRequestDto): ResponseEntity<PaymentResponseDto> {
        // Lógica de negócio aqui
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }
}

// 3. Schema customizado (opcional)
object PaymentSchemas {
    @Schema(name = "PaymentRequest", description = "Dados para processamento de pagamento")
    data class PaymentRequestSchema(
        @Schema(description = "Valor do pagamento", example = "99.90", required = true)
        val amount: Double,
        
        @Schema(description = "Método de pagamento", example = "CREDIT_CARD", required = true)
        val method: String
    )
}

=====================================================================================
BOAS PRÁTICAS
=====================================================================================

✅ FAÇA:
- Mantenha a documentação separada da lógica de negócio
- Use descrições claras e exemplos práticos
- Documente todos os códigos de resposta possíveis
- Reutilize constantes e schemas quando possível
- Agrupe operações relacionadas com tags
- Use nomes descritivos para schemas e operações

❌ NÃO FAÇA:
- Não adicione anotações Swagger diretamente nos controllers
- Não duplique constantes ou descrições
- Não deixe operações sem documentação
- Não use descrições genéricas ou vagas
- Não esqueça de documentar os tipos de erro

=====================================================================================
VALIDAÇÃO DA DOCUMENTAÇÃO
=====================================================================================

Após implementar a documentação:

1. Execute a aplicação: ./gradlew bootRun
2. Acesse: http://localhost:8080/swagger-ui/index.html
3. Verifique se:
   - Todas as operações estão visíveis
   - As descrições estão claras
   - Os exemplos são válidos
   - Os códigos de resposta estão corretos
   - Os schemas estão bem formatados

=====================================================================================
*/
