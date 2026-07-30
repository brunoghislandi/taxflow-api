package br.com.test.taxflow_api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI configuracaoOpenAPI() {

        // Criando a string HTML para a descrição rica
        String descricaoHtml = "<p>O <b>Service Layer do Tributos</b> permite o gerenciamento e sincronização de dados entre sistemas, oferecendo um conjunto de endpoints para consulta, criação, atualização e remoção de registros. O objetivo é garantir a integridade dos dados durante o processo.</p>"
                + "<p>Cada recurso pode disponibilizar os seguintes endpoints:</p>"
                + "<ul>"
                + "<li><code>GET /{resource}/{id}</code> – Consulta um registro pelo seu identificador (<code>id</code>).</li>"
                + "<li><code>POST /{resource}</code> – Envia um payload de registros para criação no sistema.</li>"
                + "<li><code>PUT /{resource}/{id}</code> – Envia um payload para atualização completa do registro.</li>"
                + "<li><code>PATCH /{resource}/{id}</code> – Envia um payload para atualização parcial do registro.</li>"
                + "<li><code>DELETE /{resource}/{id}</code> – Remove um registro específico pelo seu identificador.</li>"
                + "</ul>"
                + "<p>Os processos de <b>criação, atualização e remoção</b> retornam os dados consolidados do banco.</p>";

        return new OpenAPI()
                .info(new Info()
                        .title("Tributos: API Service Layer")
                        .version("v2")
                        .description(descricaoHtml)
                        .contact(new Contact()
                                .name("Vertical Arrecadação - Website")
                                .url("https://seusite.com.br")
                                .email("suporte@seusite.com.br")));
    }
}