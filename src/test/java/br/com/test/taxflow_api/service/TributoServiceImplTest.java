package br.com.test.taxflow_api.service;

import br.com.test.taxflow_api.domain.TipoTributo;
import br.com.test.taxflow_api.domain.Tributo;
import br.com.test.taxflow_api.dto.TributoPatchDTO;
import br.com.test.taxflow_api.dto.TributoRequestDTO;
import br.com.test.taxflow_api.dto.TributoResponseDTO;
import br.com.test.taxflow_api.exception.RecursoNaoEncontradoException;
import br.com.test.taxflow_api.mapper.TributoMapper;
import br.com.test.taxflow_api.repository.TributosRepository;
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
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TributoServiceImplTest {

    @Mock TributosRepository repository;
    @InjectMocks TributoServiceImpl service;
    @Mock TributoMapper mapper;

    TributoRequestDTO request;
    TributoRequestDTO requestAtualizacao;
    TributoPatchDTO requestAtualizarParcial;
    Tributo tributoSemId;
    Tributo tributoSalvo;
    Tributo tributoAtualizado;
    TributoResponseDTO response;
    TributoResponseDTO responseAtualizado;
    TributoResponseDTO responseAtualizadoParcial;

    @BeforeEach
    void setUp() {
        request = new TributoRequestDTO("teste", new BigDecimal("10.00"), "descricao", TipoTributo.MUNICIPAL);
        requestAtualizacao = new TributoRequestDTO("atualizado", new BigDecimal("20.23"), "teste atualizado", TipoTributo.ESTADUAL);
        requestAtualizarParcial = new TributoPatchDTO("atualizado parcial", null, null, null);
        tributoSemId = new Tributo(null, "teste", new BigDecimal("10.00"), "descricao", TipoTributo.MUNICIPAL);
        tributoSalvo = new Tributo(1L, "teste", new BigDecimal("10.00"), "descricao", TipoTributo.MUNICIPAL);
        tributoAtualizado = new Tributo(1L, "atualizado", new BigDecimal("20.23"), "teste atualizado", TipoTributo.ESTADUAL);
        response = new TributoResponseDTO(1L, "teste", new BigDecimal("10.00"), "descricao", TipoTributo.MUNICIPAL);
        responseAtualizado = new TributoResponseDTO(1L, "atualizado", new BigDecimal("20.23"), "teste atualizado", TipoTributo.ESTADUAL);
        responseAtualizadoParcial = new TributoResponseDTO(1L, "atualizado parcial", new BigDecimal("10.00"), "descricao", TipoTributo.MUNICIPAL);
    }

    @Test
    void deveSalvarTributosSucesso() {
        when(mapper.toEntity(request)).thenReturn(tributoSemId);
        when(repository.save(tributoSemId)).thenReturn(tributoSalvo);
        when(mapper.toResponseDTO(tributoSalvo)).thenReturn(response);

        TributoResponseDTO salvo = service.salvar(request);

        assertEquals(response, salvo);

        verify(repository).save(tributoSemId);
    }

    @Test
    void deveAtualizarTributo() {
        when(repository.findById(1L)).thenReturn(Optional.of(tributoSalvo));
        when(repository.save(tributoSalvo)).thenReturn(tributoAtualizado);
        when(mapper.toResponseDTO(tributoAtualizado)).thenReturn(responseAtualizado);

        TributoResponseDTO atualizado = service.atualizar(1L, requestAtualizacao);

        assertEquals(responseAtualizado, atualizado);

        verify(repository).save(tributoSalvo);
        verify(mapper).atualizarEntidade(requestAtualizacao, tributoSalvo);
    }

    @Test
    void naoDeveAtualizarTributo() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RecursoNaoEncontradoException.class, () -> service.atualizar(1L, requestAtualizacao));
        verify(repository, never()).save(any());
    }

    @Test
    void atualizarParcialTributo() {
        when(repository.findById(1L)).thenReturn(Optional.of(tributoSalvo));
        when(repository.save(tributoSalvo)).thenReturn(tributoAtualizado);
        when(mapper.toResponseDTO(tributoAtualizado)).thenReturn(responseAtualizadoParcial);

        TributoResponseDTO atualizadoParcial = service.atualizarParcial(1L, requestAtualizarParcial);

        assertEquals(responseAtualizadoParcial, atualizadoParcial);

        verify(repository).save(tributoSalvo);
        verify(mapper).atualizarParcial(requestAtualizarParcial, tributoSalvo);
    }

    @Test
    void naoDeveAtualizarParcialTributo() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RecursoNaoEncontradoException.class, () -> service.atualizarParcial(1L, requestAtualizarParcial));
        verify(repository, never()).save(any());
    }

    @Test
    void listarPorIdTributo() {
        when(repository.findById(1L)).thenReturn(Optional.of(tributoSalvo));
        when(mapper.toResponseDTO(tributoSalvo)).thenReturn(response);

        TributoResponseDTO encontrado = service.listarPorId(1L);

        assertEquals(response, encontrado);
    }

    @Test
    void naoDeveListarPorIdTributo() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RecursoNaoEncontradoException.class, () -> service.listarPorId(1L));
    }

    @Test
    void apagarTributo() {
        when(repository.existsById(1L)).thenReturn(true);
        service.deletar(1L);
        verify(repository).deleteById(1L);
    }

    @Test
    void naoDeveApagarTributo() {
        when(repository.existsById(1L)).thenReturn(false);
        assertThrows(RecursoNaoEncontradoException.class, () -> service.deletar(1L));
        verify(repository, never()).deleteById(any());
    }

    @Test
    void listarTributos() {
        Page<Tributo> paginaTeste = new PageImpl<>(List.of(tributoSalvo));
        when(repository.findAll(any(Pageable.class))).thenReturn(paginaTeste);
        when(mapper.toResponseDTO(tributoSalvo)).thenReturn(response);

        Page<TributoResponseDTO> resultado = service.listarTodos(PageRequest.of(0, 5));

        assertEquals(response, resultado.getContent().getFirst());
    }
}