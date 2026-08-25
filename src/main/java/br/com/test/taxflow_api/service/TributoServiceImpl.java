package br.com.test.taxflow_api.service;

import br.com.test.taxflow_api.domain.Tributo;
import br.com.test.taxflow_api.dto.TributoPatchDTO;
import br.com.test.taxflow_api.dto.TributoRequestDTO;
import br.com.test.taxflow_api.dto.TributoResponseDTO;
import br.com.test.taxflow_api.exception.RecursoNaoEncontradoException;
import br.com.test.taxflow_api.mapper.TributoMapper;
import br.com.test.taxflow_api.repository.TributoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TributoServiceImpl implements TributoService {

    private final TributoRepository repository;
    private final TributoMapper mapper;

    @Override
    public TributoResponseDTO salvar(TributoRequestDTO dto) {

        Tributo tributo = mapper.toEntity(dto);

        Tributo tributoSalvo = repository.save(tributo);

        return mapper.toResponseDTO(tributoSalvo);
    }

    @Override
    public TributoResponseDTO atualizar(Long id, TributoRequestDTO dto) {
        Tributo tributoExistente = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Tributo não encontrado com o ID " + id));

        mapper.atualizar(dto, tributoExistente);

        Tributo tributoAtualizado = repository.save(tributoExistente);

        return mapper.toResponseDTO(tributoAtualizado);
    }

    @Override
    public TributoResponseDTO atualizarParcial(Long id, TributoPatchDTO dto) {
        Tributo tributoExistente = repository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Tributo não encontrado com o ID " + id));

        mapper.atualizarParcial(dto, tributoExistente);

        Tributo tributoAtualizado =repository.save(tributoExistente);

        return mapper.toResponseDTO(tributoAtualizado);
    }

    @Override
    public Page<TributoResponseDTO> listarTodos(Pageable number) {
        return repository.findAll(number).map(mapper::toResponseDTO);
    }

    @Override
    public TributoResponseDTO listarPorId(Long id) {
        Tributo tributo = repository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Tributo não encontrado com o ID " + id));
        return mapper.toResponseDTO(tributo);
    }

    @Override
    public void deletar(Long id) {
        if(!repository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Tributo não encontrado com o ID: " + id);
        }
        repository.deleteById(id);
    }
}