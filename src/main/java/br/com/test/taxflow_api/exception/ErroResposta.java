package br.com.test.taxflow_api.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErroResposta {
    private String erro;
    private int status;
}
