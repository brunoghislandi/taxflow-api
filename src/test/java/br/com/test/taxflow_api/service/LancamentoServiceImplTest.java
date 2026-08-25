package br.com.test.taxflow_api.service;

import br.com.test.taxflow_api.domain.Lancamento;
import br.com.test.taxflow_api.domain.SituacaoLancamento;
import br.com.test.taxflow_api.domain.TipoTributo;
import br.com.test.taxflow_api.domain.Tributo;
import br.com.test.taxflow_api.dto.*;
import br.com.test.taxflow_api.exception.RecursoNaoEncontradoException;
import br.com.test.taxflow_api.mapper.LancamentoMapper;
import br.com.test.taxflow_api.repository.LancamentoRepository;
import br.com.test.taxflow_api.repository.TributoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LancamentoServiceImplTest {

    @Mock LancamentoRepository repository;
    @Mock TributoRepository tributoRepository;
    @Mock LancamentoMapper mapper;
    @InjectMocks LancamentoServiceImpl service;

    LancamentoRequestDTO request;
    LancamentoRequestDTO requestAtualizacao;
    LancamentoPatchDTO requestAtualizarParcial;
    LancamentoPatchDTO requestAtualizarParcialComTributo;
    Lancamento lancamentoSemId;
    Lancamento lancamentoSalvo;
    Lancamento lancamentoAtualizado;
    Tributo tributoQualquer;
    LancamentoResponseDTO response;
    LancamentoResponseDTO responseAtualizado;
    LancamentoResponseDTO responseAtualizadoParcial;

    @BeforeEach
    void setUp() {
        request = new LancamentoRequestDTO(new BigDecimal("10.00"), LocalDate.of(2026, 8, 12), SituacaoLancamento.ABERTO, 12L);
        requestAtualizacao = new LancamentoRequestDTO(new BigDecimal("20.23"), LocalDate.of(2025, 7, 11), SituacaoLancamento.PAGO, 13L);
        requestAtualizarParcial = new LancamentoPatchDTO(new BigDecimal("30.30"), null, null, null );
        requestAtualizarParcialComTributo = new LancamentoPatchDTO(new BigDecimal("30.30"), null, null, 99L);
        lancamentoSemId = new Lancamento(null, new BigDecimal("10.00"), LocalDate.of(2026, 8, 12), SituacaoLancamento.ABERTO, null);
        lancamentoSalvo = new Lancamento(1L,  new BigDecimal("10.00"), LocalDate.of(2026, 8, 12), SituacaoLancamento.ABERTO, null);
        lancamentoAtualizado = new Lancamento(1L, new BigDecimal("20.23"), LocalDate.of(2025, 7, 11), SituacaoLancamento.PAGO, null);
        tributoQualquer = new Tributo(12L, "ISS", new BigDecimal("5.00"), "Imposto Sobre Serviços", TipoTributo.MUNICIPAL);
        response = new LancamentoResponseDTO(1L, new BigDecimal("10.00"), LocalDate.of(2026, 8, 12), SituacaoLancamento.ABERTO, 12L);
        responseAtualizado = new LancamentoResponseDTO(1L, new BigDecimal("20.23"), LocalDate.of(2025, 7, 11), SituacaoLancamento.PAGO, 13L);
        responseAtualizadoParcial = new LancamentoResponseDTO(1L, new BigDecimal("30.30"), LocalDate.of(2026, 8, 12), SituacaoLancamento.ABERTO, 12L);
    }

    @Test
    void deveSalvarLancamento(){
        when(mapper.toEntity(request)).thenReturn(lancamentoSemId);
        when(tributoRepository.findById(request.getTributoId())).thenReturn(Optional.of(tributoQualquer));
        when(repository.save(lancamentoSemId)).thenReturn(lancamentoSalvo);
        when(mapper.toResponseDTO(lancamentoSalvo)).thenReturn(response);

        LancamentoResponseDTO salvo = service.salvar(request);

        assertEquals(response, salvo);

        verify(repository).save(lancamentoSemId);
    }

    @Test
    void naoDeveSalvarLancamentoQuandoTributoNaoExiste(){
        when(tributoRepository.findById(request.getTributoId())).thenReturn(Optional.empty());

        assertThrows(RecursoNaoEncontradoException.class, () -> service.salvar(request));
        verify(repository, never()).save(any());
    }

    @Test
    void deveAtualizarLancamento(){
        when(repository.findById(1L)).thenReturn(Optional.of(lancamentoSalvo));
        when(tributoRepository.findById(requestAtualizacao.getTributoId())).thenReturn(Optional.of(tributoQualquer));
        when(repository.save(lancamentoSalvo)).thenReturn(lancamentoAtualizado);
        when(mapper.toResponseDTO(lancamentoAtualizado)).thenReturn(responseAtualizado);

        LancamentoResponseDTO atualizado = service.atualizar(1L, requestAtualizacao);
        assertEquals(responseAtualizado, atualizado);
        verify(mapper).atualizar(requestAtualizacao, lancamentoSalvo);
    }

    @Test
    void naoDeveAtualizarLancamento(){
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RecursoNaoEncontradoException.class, () -> service.atualizar(1L, requestAtualizacao));
        verify(repository, never()).save(any());
    }

    @Test
    void naoDeveAtualizarLancamentoQuandoTributoNaoExiste(){
        when(repository.findById(1L)).thenReturn(Optional.of(lancamentoSalvo));
        when(tributoRepository.findById(requestAtualizacao.getTributoId())).thenReturn(Optional.empty());

        assertThrows(RecursoNaoEncontradoException.class, () -> service.atualizar(1L, requestAtualizacao));
        verify(repository, never()).save(any());
    }

    @Test
    void deveAtualizarParcialLancamento(){
        when(repository.findById(1L)).thenReturn(Optional.of(lancamentoSalvo));
        when(repository.save(lancamentoSalvo)).thenReturn(lancamentoAtualizado);
        when(mapper.toResponseDTO(lancamentoAtualizado)).thenReturn(responseAtualizadoParcial);

        LancamentoResponseDTO atualizadoParcial = service.atualizarParcial(1L, requestAtualizarParcial);

        assertEquals(responseAtualizadoParcial, atualizadoParcial);

        verify(mapper).atualizarParcial(requestAtualizarParcial, lancamentoSalvo);
    }

    @Test
    void naoDeveAtualizarParcialLancamento(){
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RecursoNaoEncontradoException.class, () -> service.atualizarParcial(1L, requestAtualizarParcial));
        verify(repository, never()).save(any());
    }

    @Test
    void naoDeveAtualizarParcialLancamentoQuandoTributoNaoExiste(){
        when(repository.findById(1L)).thenReturn(Optional.of(lancamentoSalvo));
        when(tributoRepository.findById(requestAtualizarParcialComTributo.getTributoId())).thenReturn(Optional.empty());

        assertThrows(RecursoNaoEncontradoException.class, () -> service.atualizarParcial(1L, requestAtualizarParcialComTributo));
        verify(repository, never()).save(any());
    }

    @Test
    void deveListarLancamentos(){
        Page<Lancamento> paginaTeste = new PageImpl<>(List.of(lancamentoSalvo));
        when(repository.findAll(any(Pageable.class))).thenReturn(paginaTeste);
        when(mapper.toResponseDTO(lancamentoSalvo)).thenReturn(response);

        Page<LancamentoResponseDTO> resultado = service.listarTodos(PageRequest.of(0, 5));

        assertEquals(response, resultado.getContent().getFirst());
    }

    @Test
    void deveListarPorIdLancamento(){
        when(repository.findById(1L)).thenReturn(Optional.of(lancamentoSalvo));
        when(mapper.toResponseDTO(lancamentoSalvo)).thenReturn(response);

        LancamentoResponseDTO resultado = service.listarPorId(1L);

        assertEquals(response, resultado);
    }

    @Test
    void naoDeveListarPorIdLancamento(){
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RecursoNaoEncontradoException.class, () -> service.listarPorId(1L));
    }

    @Test
    void deveApagarLancamento(){
        when(repository.existsById(1L)).thenReturn(true);

        service.deletar(1L);
        verify(repository).deleteById(1L);
    }

    @Test
    void naoDeveApagarLancamento(){
        when(repository.existsById(1L)).thenReturn(false);

        assertThrows(RecursoNaoEncontradoException.class, () -> service.deletar(1L));
        verify(repository, never()).deleteById(any());
    }
}



















