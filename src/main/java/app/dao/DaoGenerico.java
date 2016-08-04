package app.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import excecao.ObjetoNaoEncontradoException;

/**
 * A interface GenericDao básica com os métodos CRUD. Os métodos
 * de busca são adicionados por herança de interface.
 */
public interface DaoGenerico<T, PK extends Serializable>
{
	@Transactional
	T inclui(T obj);

	@Transactional
    void altera(T obj);

	@Transactional
    void exclui(T obj);
	
	@Transactional
	void remove(T obj);

    T getPorId(PK id) throws ObjetoNaoEncontradoException;

    T getPorIdComLock(PK id) throws ObjetoNaoEncontradoException;
    
    List<T> recuperaDPeloNome(String nome, int deslocamento, int linhasPorPagina);
    
    List<T> recuperaEPeloNome(String nome, int deslocamento, int linhasPorPagina);
}
