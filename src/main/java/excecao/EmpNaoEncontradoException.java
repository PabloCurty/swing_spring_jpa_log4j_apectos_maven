package excecao;

public class EmpNaoEncontradoException extends Exception
{	
	private final static long serialVersionUID = 1;
	
	public EmpNaoEncontradoException()
	{	super();
	}

	public EmpNaoEncontradoException(String msg)
	{	super(msg);
	}
}	