package br.com.test.taxflow_api.dto;

import br.com.test.taxflow_api.domain.SituacaoLancamento;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
public class LancamentoRequestDTO {

    @NotNull(message = "O valor lançado não pode ser nulo!")
    @Positive(message = "O valor lançado deve ser maior que zero!")
    private BigDecimal valor;

    @NotNull(message = "A data de lançamento não pode ser vazia!")
    private LocalDate dataLancamento;

    @NotNull(message = "A situação não pode ser vazia!")
    private SituacaoLancamento situacao;

    @NotNull(message = "O tributoId não pode ser nulo!")
    private Long tributoId;
}
