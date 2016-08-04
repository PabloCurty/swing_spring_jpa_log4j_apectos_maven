package app.dao;

import java.util.List;
import java.util.Set;

import anotacao.RecuperaConjunto;
import anotacao.RecuperaLista;
import anotacao.RecuperaObjeto;
import app.model.Dept;
import excecao.ObjetoNaoEncontradoException;

/**
 * @author Pablo
 *
 */
public interface DeptDAO extends DaoGenerico<Dept, Long>{

	/* ****** MÈtodos GenÈricos ******* */

	@RecuperaObjeto
	Dept recuperaUmDeptEFunc(long numero) 
		throws ObjetoNaoEncontradoException;

	@RecuperaLista
	List<Dept> recuperaListaDeDept();
	
	@RecuperaLista
	List<Dept> recuperaListaDeDeptEFunc();

	@RecuperaConjunto
	Set<Dept> recuperaConjuntoDeDeptEFunc();

	@RecuperaLista
	List<Dept> recuperaDeptPeloNome(String nome);
	
	/* ****** not generic methods ******* */

	// Um m√©todo definido aqui, que n√£o seja anotado, dever√° ser
	// implementado como final em ProdutoDAOImpl.
	
}
