package br.com.test.taxflow_api.service;

import br.com.test.taxflow_api.dto.LancamentoPatchDTO;
import br.com.test.taxflow_api.dto.LancamentoRequestDTO;
import br.com.test.taxflow_api.dto.LancamentoResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LancamentoService {
    LancamentoResponseDTO salvar(LancamentoRequestDTO dto);
    LancamentoResponseDTO atualizar(Long id, LancamentoRequestDTO dto);
    LancamentoResponseDTO atualizarParcial(Long id, LancamentoPatchDTO dto);
    Page<LancamentoResponseDTO> listarTodos(Pageable number);
    LancamentoResponseDTO listarPorId(Long id);
    void deletar(Long id);
}
