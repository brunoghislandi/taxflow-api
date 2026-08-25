package br.com.test.taxflow_api.controller;

import br.com.test.taxflow_api.dto.LancamentoPatchDTO;
import br.com.test.taxflow_api.dto.LancamentoRequestDTO;
import br.com.test.taxflow_api.dto.LancamentoResponseDTO;
import br.com.test.taxflow_api.service.LancamentoService;
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
@RequestMapping("/lancamentos")
@RequiredArgsConstructor
@Tag(name = "lancamentos (Lancamentos)", description = "API de testes tributária")
public class LancamentoController {

    private final LancamentoService service;

    @PostMapping
    public ResponseEntity<LancamentoResponseDTO> cadastrar(@Valid @RequestBody LancamentoRequestDTO dto) {
        LancamentoResponseDTO response = service.salvar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LancamentoResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody LancamentoRequestDTO dto) {
        LancamentoResponseDTO response = service.atualizar(id, dto);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<LancamentoResponseDTO> atualizarParcial(@PathVariable Long id, @Valid @RequestBody LancamentoPatchDTO dto) {
        LancamentoResponseDTO response = service.atualizarParcial(id, dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<LancamentoResponseDTO>> listar(@PageableDefault(size = 5, sort = {"dataLancamento"}) Pageable number) {
        Page<LancamentoResponseDTO> response = service.listarTodos(number);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LancamentoResponseDTO> listarPorId(@PathVariable Long id) {
        LancamentoResponseDTO response = service.listarPorId(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
