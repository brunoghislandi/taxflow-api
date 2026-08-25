package br.com.test.taxflow_api.service;

import br.com.test.taxflow_api.dto.TributoPatchDTO;
import br.com.test.taxflow_api.dto.TributoRequestDTO;
import br.com.test.taxflow_api.dto.TributoResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TributoService {
    TributoResponseDTO salvar (TributoRequestDTO dto);
    TributoResponseDTO atualizar (Long id, TributoRequestDTO dto);
    TributoResponseDTO atualizarParcial(Long id, TributoPatchDTO dto);
    Page<TributoResponseDTO> listarTodos(Pageable number);
    TributoResponseDTO listarPorId(Long id);
    void deletar(Long id);
}
