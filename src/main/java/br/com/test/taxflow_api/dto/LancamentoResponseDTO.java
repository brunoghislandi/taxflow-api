package br.com.test.taxflow_api.dto;

import br.com.test.taxflow_api.domain.SituacaoLancamento;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LancamentoResponseDTO {
    private Long id;
    private BigDecimal valor;
    private LocalDate dataLancamento;
    private SituacaoLancamento situacao;
    private Long tributoId;
}
