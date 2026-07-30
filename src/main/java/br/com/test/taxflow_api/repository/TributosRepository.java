package br.com.test.taxflow_api.repository;

import br.com.test.taxflow_api.domain.Tributo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TributosRepository extends JpaRepository<Tributo, Long> {
}
