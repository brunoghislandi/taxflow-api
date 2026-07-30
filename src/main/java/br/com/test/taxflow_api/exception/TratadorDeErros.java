package br.com.test.taxflow_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class TratadorDeErros {
    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<ErroResposta> tratarErro404(RecursoNaoEncontradoException ex) {
        ErroResposta erroPadronizado = new ErroResposta(ex.getMessage(), HttpStatus.NOT_FOUND.value());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erroPadronizado);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErroValidacao>> tratarErro400(MethodArgumentNotValidException ex) {
        List<FieldError> errosDoSpring = ex.getFieldErrors();

        List<ErroValidacao> errosTratados = errosDoSpring.stream().map(erro -> new ErroValidacao(erro.getField(), erro.getDefaultMessage())).collect(Collectors.toList());

        return ResponseEntity.badRequest().body(errosTratados);
    }

    public record ErroValidacao(String campo, String mensagem) {}
}
