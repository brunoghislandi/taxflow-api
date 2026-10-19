package br.com.test.taxflow_api.repository;

import br.com.test.taxflow_api.domain.Lancamento;
import br.com.test.taxflow_api.domain.SituacaoLancamento;
import br.com.test.taxflow_api.domain.TipoTributo;
import br.com.test.taxflow_api.domain.Tributo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class LancamentoRepositoryTest {

    @Autowired
    LancamentoRepository repository;
    @Autowired
    TributoRepository tributoRepository;
    @Autowired
    TestEntityManager testEntityManager;

    @Container
    @ServiceConnection
    static PostgreSQLContainer postgres = new PostgreSQLContainer("postgres:17");

    @Test
    void deveSalvarLancamentoComTributoVinculado() {
        Tributo tributo = new Tributo(null, "ISS", new BigDecimal("5.00"), "Imposto Sobre Serviços", TipoTributo.MUNICIPAL);
        testEntityManager.persistAndFlush(tributo);

        Lancamento lancamento = new Lancamento(null, new BigDecimal("100.00"), LocalDate.of(2026, 8, 20), SituacaoLancamento.ABERTO, tributo);
        testEntityManager.persistAndFlush(lancamento);

        Optional<Lancamento> lancamentoEncontrado = repository.findById(lancamento.getId());

        assertTrue(lancamentoEncontrado.isPresent());
        assertEquals(tributo.getId(), lancamentoEncontrado.get().getTributo().getId());
    }

    @Test
    void procurarIdQueNaoExiste() {
        Optional<Lancamento> lancamentoEncontrado = repository.findById(Long.MAX_VALUE);

        assertTrue(lancamentoEncontrado.isEmpty());
    }

    @Test
    void apagarLancamentoPorId() {
        Tributo tributo = new Tributo(null, "ISS", new BigDecimal("5.00"), "Imposto Sobre Serviços", TipoTributo.MUNICIPAL);
        testEntityManager.persistAndFlush(tributo);

        Lancamento lancamento = new Lancamento(null, new BigDecimal("100.00"), LocalDate.of(2026, 8, 20), SituacaoLancamento.ABERTO, tributo);
        testEntityManager.persistAndFlush(lancamento);

        repository.deleteById(lancamento.getId());
        Optional<Lancamento> lancamentoApagado = repository.findById(lancamento.getId());

        assertTrue(lancamentoApagado.isEmpty());
    }

    @Test
    void validaPaginacao() {
        Tributo tributo = new Tributo(null, "ISS", new BigDecimal("5.00"), "Imposto Sobre Serviços", TipoTributo.MUNICIPAL);
        testEntityManager.persist(tributo);

        testEntityManager.persist(new Lancamento(null, new BigDecimal("100.00"), LocalDate.of(2026, 8, 20), SituacaoLancamento.ABERTO, tributo));
        testEntityManager.persist(new Lancamento(null, new BigDecimal("200.00"), LocalDate.of(2026, 8, 21), SituacaoLancamento.ABERTO, tributo));
        testEntityManager.persist(new Lancamento(null, new BigDecimal("300.00"), LocalDate.of(2026, 8, 22), SituacaoLancamento.ABERTO, tributo));
        testEntityManager.persist(new Lancamento(null, new BigDecimal("400.00"), LocalDate.of(2026, 8, 23), SituacaoLancamento.ABERTO, tributo));

        testEntityManager.flush();

        Page<Lancamento> lancamentosPageados = repository.findAll(PageRequest.of(0, 2));

        assertEquals(2, lancamentosPageados.getContent().size());
        assertEquals(4, lancamentosPageados.getTotalElements());
        assertEquals(2, lancamentosPageados.getTotalPages());
    }

    @Test
    void naoApagaTributoVinculado() {
        Tributo tributo = new Tributo(null, "ISS", new BigDecimal("5.00"), "Imposto Sobre Serviços", TipoTributo.MUNICIPAL);
        testEntityManager.persistAndFlush(tributo);

        Lancamento lancamento = new Lancamento(null, new BigDecimal("100.00"), LocalDate.of(2026, 8, 20), SituacaoLancamento.ABERTO, tributo);
        testEntityManager.persistAndFlush(lancamento);

        testEntityManager.clear();

        assertThrows(DataIntegrityViolationException.class, () -> {
            tributoRepository.deleteById(tributo.getId());
            tributoRepository.flush();
        });
    }
}
