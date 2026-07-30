package br.com.test.taxflow_api.dto;

import br.com.test.taxflow_api.domain.TipoTributo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class TributoRequestDTO {

    @NotBlank(message = "O nome do tributo não pode ser vazio!")
    private String nome;

    @NotNull(message = "A alíquota é obrigatoria!")
    @Positive(message = "A alíquota deve ser maior que zero!")
    private BigDecimal aliquota;

    private String descricao;

    @NotNull(message = "O tipo do tributo é obrigatório!")
    private TipoTributo tipo;
}