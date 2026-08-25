package br.com.test.taxflow_api.controller;

import br.com.test.taxflow_api.dto.TributoPatchDTO;
import br.com.test.taxflow_api.dto.TributoRequestDTO;
import br.com.test.taxflow_api.dto.TributoResponseDTO;
import br.com.test.taxflow_api.service.TributoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tributos")
@RequiredArgsConstructor
@Tag(name = "tributos (Tributos)", description = "API de testes tributária")
public class TributoController {

    private final TributoService service;

    @Operation(
            summary = "Cadastra um novo tributo no sistema.",
            description = "Este endpoint permite a <b>criação de um novo tributo</b> na base de dados.<br><br>"
                    + "<b>O payload de requisição deve obrigatoriamente incluir:</b>"
                    + "<ul>"
                    + "<li><code>nome</code> : Nome ou sigla do imposto (Não pode ser vazio);</li>"
                    + "<li><code>tipo</code> : Categorização (FEDERAL, ESTADUAL, MUNICIPAL);</li>"
                    + "<li><code>aliquota</code> : Valor percentual cobrado (Deve ser maior que zero).</li>"
                    + "</ul>"
    )
    @PostMapping
    public ResponseEntity<TributoResponseDTO> cadastrar(@Valid @RequestBody TributoRequestDTO dto) {
        TributoResponseDTO response = service.salvar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Atualiza integralmente um tributo existente.",
            description = "Este endpoint permite a <b>atualização completa</b> de um tributo. Todos os atributos obrigatórios devem ser enviados no payload, pois os dados anteriores serão totalmente substituídos pelos novos.<br><br>"
                    + "<b>O retorno do endpoint inclui:</b>"
                    + "<ul>"
                    + "<li>Os dados consolidados do tributo após a atualização.</li>"
                    + "</ul>"
    )
    @PutMapping("/{id}")
    public ResponseEntity<TributoResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody TributoRequestDTO dto) {
        TributoResponseDTO response = service.atualizar(id, dto);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Atualiza parcialmente um tributo existente.",
            description = "Este endpoint permite a <b>atualização parcial (PATCH)</b> de um tributo. Apenas os atributos enviados no payload serão alterados, preservando o valor dos demais campos já salvos no banco de dados.<br><br>"
                    + "<b>O retorno do endpoint inclui:</b>"
                    + "<ul>"
                    + "<li>Os dados consolidados do tributo após a modificação parcial.</li>"
                    + "</ul>"
    )
    @PatchMapping("/{id}")
    public ResponseEntity<TributoResponseDTO> atualizarParcial(@PathVariable Long id, @Valid @RequestBody TributoPatchDTO dto) {
        TributoResponseDTO response = service.atualizarParcial(id, dto);
        return  ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Lista os tributos cadastrados de forma paginada.",
            description = "Este endpoint permite a <b>consulta de múltiplos tributos</b> com suporte nativo a paginação e ordenação.<br><br>"
                    + "<b>Parâmetros de URL suportados (opcionais):</b>"
                    + "<ul>"
                    + "<li><code>page</code> : Número da página desejada (Iniciando em 0);</li>"
                    + "<li><code>size</code> : Quantidade de registros retornados por página;</li>"
                    + "<li><code>sort</code> : Critério de ordenação, ex: <code>nome,asc</code> ou <code>aliquota,desc</code>.</li>"
                    + "</ul>"
    )
    @GetMapping
    public ResponseEntity<Page<TributoResponseDTO>> listar(@PageableDefault(size = 5, sort = {"nome"}) Pageable number) {
        Page<TributoResponseDTO> response = service.listarTodos(number);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Consulta o status do tributo cadastrado.",
            description = "Este endpoint permite a <b>consulta de tributos</b> salvos na base de dados vinculada à aplicação. Ele permite a consulta eficiente dos registros.<br><br>"
                    + "<b>O retorno do endpoint inclui:</b>"
                    + "<ul>"
                    + "<li><code>id</code> : Identificador gerado ao efetivar a transação;</li>"
                    + "<li><code>nome</code> : Nome da sigla do imposto;</li>"
                    + "<li><code>tipo</code> : Enum com o status de categorização (FEDERAL, ESTADUAL, MUNICIPAL);</li>"
                    + "<li><code>aliquota</code> : Valor percentual cobrado;</li>"
                    + "</ul>"
    )
    @GetMapping("/{id}")
    public ResponseEntity<TributoResponseDTO> listarPorId(@PathVariable Long id) {
        TributoResponseDTO response = service.listarPorId(id);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Remove um tributo pelo seu identificador.",
            description = "Este endpoint realiza a <b>exclusão permanente</b> do tributo da base de dados correspondente ao <code>id</code> informado.<br><br>"
                    + "<b>Retorno esperado:</b>"
                    + "<ul>"
                    + "<li><code>204 No Content</code>: Quando a exclusão é efetuada com sucesso (sem corpo na resposta);</li>"
                    + "<li><code>404 Not Found</code>: Caso o <code>id</code> não seja localizado na base.</li>"
                    + "</ul>"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}