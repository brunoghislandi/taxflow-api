package br.com.test.taxflow_api.dto;

import br.com.test.taxflow_api.domain.TipoTributo;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TributoPatchDTO {

    private String nome;

    @Positive(message = "A alíquota deve ser maior que zero!")
    private BigDecimal aliquota;

    private String descricao;

    private TipoTributo tipo;
}