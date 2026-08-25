package br.com.test.taxflow_api.dto;

import br.com.test.taxflow_api.domain.SituacaoLancamento;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LancamentoPatchDTO {

    @Positive(message = "O valor lançado deve ser maior que zero!")
    private BigDecimal valor;

    private LocalDate dataLancamento;

    private SituacaoLancamento situacao;

    private Long tributoId;
}
