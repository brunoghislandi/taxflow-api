package br.com.test.taxflow_api.config;

import br.com.test.taxflow_api.domain.TipoTributo;
import br.com.test.taxflow_api.domain.Tributo;
import br.com.test.taxflow_api.repository.TributosRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
public class TestDatabaseSeeder implements CommandLineRunner {

    private final TributosRepository repository;

    @Override
    public void run(String... args) throws Exception {
        if (repository.count() == 0) {

            Tributo t1 = new Tributo(null, "ISS", new BigDecimal("5.00"), "Imposto Sobre Serviços", TipoTributo.MUNICIPAL);
            Tributo t2 = new Tributo(null, "IPTU", new BigDecimal("3.00"), "Imposto Predial e Territorial Urbano", TipoTributo.MUNICIPAL);
            Tributo t3 = new Tributo(null, "ITBI", new BigDecimal("2.00"), "Imposto de Transmissão de Bens Imóveis", TipoTributo.MUNICIPAL);

            Tributo t4 = new Tributo(null, "ICMS", new BigDecimal("18.00"), "Imposto sobre Circulação de Mercadorias", TipoTributo.ESTADUAL);
            Tributo t5 = new Tributo(null, "IPVA", new BigDecimal("4.00"), "Imposto sobre Propriedade de Veículos Automotores", TipoTributo.ESTADUAL);
            Tributo t6 = new Tributo(null, "ITCMD", new BigDecimal("4.00"), "Imposto sobre Transmissão Causa Mortis e Doação", TipoTributo.ESTADUAL);

            Tributo t7 = new Tributo(null, "IPI", new BigDecimal("10.00"), "Imposto sobre Produtos Industrializados", TipoTributo.FEDERAL);
            Tributo t8 = new Tributo(null, "IOF", new BigDecimal("6.38"), "Imposto sobre Operações Financeiras", TipoTributo.FEDERAL);
            Tributo t9 = new Tributo(null, "IRPF", new BigDecimal("27.50"), "Imposto de Renda Pessoa Física", TipoTributo.FEDERAL);
            Tributo t10 = new Tributo(null, "IRPJ", new BigDecimal("15.00"), "Imposto de Renda Pessoa Jurídica", TipoTributo.FEDERAL);
            Tributo t11 = new Tributo(null, "COFINS", new BigDecimal("7.60"), "Contribuição para o Financiamento da Seguridade Social", TipoTributo.FEDERAL);
            Tributo t12 = new Tributo(null, "PIS", new BigDecimal("1.65"), "Programa de Integração Social", TipoTributo.FEDERAL);

            repository.saveAll(Arrays.asList(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12));

            System.out.println("✅ Banco de dados populado com 12 tributos com sucesso!");
        }
    }
}