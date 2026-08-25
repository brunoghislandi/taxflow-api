package br.com.test.taxflow_api.service;

import br.com.test.taxflow_api.domain.Lancamento;
import br.com.test.taxflow_api.domain.Tributo;
import br.com.test.taxflow_api.dto.LancamentoPatchDTO;
import br.com.test.taxflow_api.dto.LancamentoRequestDTO;
import br.com.test.taxflow_api.dto.LancamentoResponseDTO;
import br.com.test.taxflow_api.exception.RecursoNaoEncontradoException;
import br.com.test.taxflow_api.mapper.LancamentoMapper;
import br.com.test.taxflow_api.repository.LancamentoRepository;
import br.com.test.taxflow_api.repository.TributoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LancamentoServiceImpl implements LancamentoService {

    private final LancamentoRepository repository;
    private final TributoRepository tributoRepository;
    private final LancamentoMapper mapper;

    @Override
    public LancamentoResponseDTO salvar(LancamentoRequestDTO dto) {

        Lancamento lancamento = mapper.toEntity(dto);

        Tributo tributoEncontrado = tributoRepository.findById(dto.getTributoId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Tributo não encontrado com o ID " + dto.getTributoId()));

        lancamento.setTributo(tributoEncontrado);

        Lancamento lancamentoSalvo = repository.save(lancamento);

        return mapper.toResponseDTO(lancamentoSalvo);
    }

    @Override
    public LancamentoResponseDTO atualizar(Long id, LancamentoRequestDTO dto) {

        Lancamento lancamentoExistente = repository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Lançamento não encontrado com o ID " + id));

        mapper.atualizar(dto, lancamentoExistente);

        Tributo tributoEncontrado = tributoRepository.findById(dto.getTributoId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Tributo não encontrado com o ID " + dto.getTributoId()));

        lancamentoExistente.setTributo(tributoEncontrado);

        Lancamento lancamento = repository.save(lancamentoExistente);

        return mapper.toResponseDTO(lancamento);
    }

    @Override
    public LancamentoResponseDTO atualizarParcial(Long id, LancamentoPatchDTO dto) {
        Lancamento lancamentoExistente = repository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Lançamento não encontrado com o ID " + id));

        mapper.atualizarParcial(dto, lancamentoExistente);

        if(dto.getTributoId() != null) {
            Tributo tributoEncontrado = tributoRepository.findById(dto.getTributoId()).orElseThrow(() -> new RecursoNaoEncontradoException("Tributo não encontrado com o ID " + dto.getTributoId()));

            lancamentoExistente.setTributo(tributoEncontrado);
        }

        Lancamento lancamento = repository.save(lancamentoExistente);

        return mapper.toResponseDTO(lancamento);
    }

    @Override
    public Page<LancamentoResponseDTO> listarTodos(Pageable number) {
        return repository.findAll(number).map(mapper::toResponseDTO);
    }

    @Override
    public LancamentoResponseDTO listarPorId(Long id) {
        Lancamento lancamento = repository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Lancamento não encontrado com o ID " + id));

        return mapper.toResponseDTO(lancamento);
    }

    @Override
    public void deletar(Long id) {
        if(!repository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Lancamento não encontrado com o ID " + id);
        }
        repository.deleteById(id);
    }
}
