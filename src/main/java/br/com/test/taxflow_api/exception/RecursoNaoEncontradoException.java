package br.com.test.taxflow_api.exception;

public class RecursoNaoEncontradoException extends  RuntimeException {
    public RecursoNaoEncontradoException (String mensagem) {
        super(mensagem);
    }
}
