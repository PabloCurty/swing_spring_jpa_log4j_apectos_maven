package excecao;

import anotacao.ExcecaoDeAplicacao;

@ExcecaoDeAplicacao
public class DepartamentoComFuncionarioException extends Exception{
	
	private final static long serialVersionUID = 1;

	public DepartamentoComFuncionarioException(String msg) {
		super(msg);
	}
	
	
}
