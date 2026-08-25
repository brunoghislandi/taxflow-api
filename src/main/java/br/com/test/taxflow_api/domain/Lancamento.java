package br.com.test.taxflow_api.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "lancamentos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Lancamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal valor;
    private LocalDate dataLancamento;
    @Enumerated(EnumType.STRING)
    private SituacaoLancamento situacao;
    @JoinColumn(name = "tributo_id")
    private @ManyToOne Tributo tributo;
}
