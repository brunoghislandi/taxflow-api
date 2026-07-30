package br.com.test.taxflow_api.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "tributos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tributo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private BigDecimal aliquota;
    private String descricao;
    @Enumerated(EnumType.STRING)
    private TipoTributo tipo;
}
