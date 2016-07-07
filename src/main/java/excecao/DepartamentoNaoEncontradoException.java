package excecao;

import anotacao.ExcecaoDeAplicacao;

@ExcecaoDeAplicacao
public class DepartamentoNaoEncontradoException extends Exception{

	private final static long serialVersionUID = 1;
	
	public DepartamentoNaoEncontradoException(String msg){
		super(msg);
	}
}
