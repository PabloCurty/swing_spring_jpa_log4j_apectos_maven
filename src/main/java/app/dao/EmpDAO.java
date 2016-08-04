package app.dao;

import java.util.List;

import anotacao.RecuperaLista;
import anotacao.RecuperaObjeto;
import anotacao.RecuperaUltimoOuPrimeiro;
import app.model.Dept;
import app.model.Emp;
import excecao.ObjetoNaoEncontradoException;

/**
 * @author Pablo
 *
 */
public interface EmpDAO extends DaoGenerico<Emp, Long>{

	/* ****** Métodos Genéricos ******* */
	
	@RecuperaLista
	List<Emp> recuperaListaDeFunc();
	
	@RecuperaUltimoOuPrimeiro
	Emp recuperaUltimoFunc(Dept produto)
		throws ObjetoNaoEncontradoException; 
	
	@RecuperaLista
	List<Emp> recuperaFuncsDeDept(long deptNum);

	@RecuperaLista
	List<Emp> recuperaEmpPeloNome(String nomeEmp);

	@RecuperaObjeto
	Emp recuperaUmFuncDeDept(Long valueAt) throws ObjetoNaoEncontradoException;
}
