package br.com.test.taxflow_api.dto;

import br.com.test.taxflow_api.domain.TipoTributo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TributoResponseDTO {
    private Long id;
    private String nome;
    private BigDecimal aliquota;
    private String descricao;
    private TipoTributo tipo;
}