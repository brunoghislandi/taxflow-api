package br.com.test.taxflow_api.repository;

import br.com.test.taxflow_api.domain.Lancamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LancamentoRepository extends JpaRepository <Lancamento, Long> {
}
