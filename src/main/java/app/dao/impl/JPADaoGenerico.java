package app.dao.impl;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import app.dao.DaoGenerico;
import excecao.InfraestruturaException;
import excecao.ObjetoNaoEncontradoException;

/**
 * A implementa��o de um DAO gen�rico para a JPA Uma implementa��o "typesafe"
 * dos m�todos CRUD e dos m�todos de busca.
 */
public class JPADaoGenerico<T, PK extends Serializable> implements DaoGenerico<T, PK> {
	private Class<T> tipo;

	@PersistenceContext
	protected EntityManager em; // Para subclasses enxergarem

	public JPADaoGenerico(Class<T> tipo) {
		this.tipo = tipo;
	}

	public final T inclui(T o) {
		try {
			em.persist(o);
		} catch (RuntimeException e) {
			throw new InfraestruturaException(e);
		}

		return o;
	}

	public final void altera(T o) {
		try {
			em.merge(o);
		} catch (RuntimeException e) {
			throw new InfraestruturaException(e);
		}
	}

	public final void exclui(T o) {
		try {
			em.remove(o);
		} catch (RuntimeException e) {
			throw new InfraestruturaException(e);
		}
	}
	
	public final void remove(T o) {
		try {
			o = em.merge(o);
			em.remove(o);
		} catch (RuntimeException e) {
			throw new InfraestruturaException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public final List<T> recuperaEPeloNome(String nome, int deslocamento, int linhasPorPagina){
		List<T> emps = em
				.createQuery("select e from Emp e left outer join fetch e.deptno "
						   + "where e.ename like :nome order by e.ename asc")
				.setParameter("nome", nome.toUpperCase())
				.setFirstResult(deslocamento)
				.setMaxResults(linhasPorPagina)
				.getResultList();

			return emps;
	}

	@SuppressWarnings("unchecked")
	@Override
	public final List<T> recuperaDPeloNome(String nome, int deslocamento, int linhasPorPagina){	
		List<T> depts = em
			.createQuery("select d from Dept d "
					   + "where d.nameDept like :nome order by d.nameDept asc")
			.setParameter("nome", nome.toUpperCase())
			.setFirstResult(deslocamento)
			.setMaxResults(linhasPorPagina)
			.getResultList();

		return depts;
	}
	
	public final T getPorId(PK id) throws ObjetoNaoEncontradoException {
		T t = null;
		try {
			t = em.find(tipo, id);

			if (t == null) {
				throw new ObjetoNaoEncontradoException();
			}
		} catch (RuntimeException e) {
			throw new InfraestruturaException(e);
		}
		return t;
	}

	public final T getPorIdComLock(PK id) throws ObjetoNaoEncontradoException {
		T t = null;
		try {
			t = em.find(tipo, id, LockModeType.PESSIMISTIC_WRITE);

			if (t == null) {
				throw new ObjetoNaoEncontradoException();
			}
		} catch (RuntimeException e) {
			throw new InfraestruturaException(e);
		}

		return t;
	}

	@SuppressWarnings("unchecked")
	public final T busca(Method metodo, Object[] argumentos) throws ObjetoNaoEncontradoException {
		T t = null;
		try {
			String nomeDaBusca = getNomeDaBuscaPeloMetodo(metodo);
			Query namedQuery = em.createNamedQuery(nomeDaBusca);

			if (argumentos != null) {
				for (int i = 0; i < argumentos.length; i++) {
					Object arg = argumentos[i];
					namedQuery.setParameter(i + 1, arg); // Par�metros de buscas
															// s�o 1-based.
				}
			}
			t = (T) namedQuery.getSingleResult();

			return t;
		} catch (NoResultException e) {
			throw new ObjetoNaoEncontradoException();
		} catch (RuntimeException e) {
			throw new InfraestruturaException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public final T buscaUltimoOuPrimeiro(Method metodo, Object[] argumentos) throws ObjetoNaoEncontradoException {
		T t = null;
		try {
			List<T> lista;
			String nomeDaBusca = getNomeDaBuscaPeloMetodo(metodo);
			Query namedQuery = em.createNamedQuery(nomeDaBusca);

			if (argumentos != null) {
				for (int i = 0; i < argumentos.length; i++) {
					Object arg = argumentos[i];
					namedQuery.setParameter(i + 1, arg);
				}
			}
			lista = namedQuery.getResultList();

			t = (lista.size() == 0) ? null : (T) lista.get(0);

			if (t == null) {
				throw new ObjetoNaoEncontradoException();
			}

			return t;
		} catch (RuntimeException e) {
			throw new InfraestruturaException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public final List<T> buscaLista(Method metodo, Object[] argumentos) {
		try {
			String nomeDaBusca = getNomeDaBuscaPeloMetodo(metodo); //string nomeDaBusca Produto.recuperaUmprodutoElances
			Query namedQuery = em.createNamedQuery(nomeDaBusca);

			if (argumentos != null) {
				for (int i = 0; i < argumentos.length; i++) {
					Object arg = argumentos[i];
					namedQuery.setParameter(i + 1, arg); // Par�metros de buscas
															// s�o 1-based.
				}
			}
			return (List<T>) namedQuery.getResultList();
		} catch (RuntimeException e) {
			throw new InfraestruturaException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public final Set<T> buscaConjunto(Method metodo, Object[] argumentos) {
		try {
			String nomeDaBusca = getNomeDaBuscaPeloMetodo(metodo);
			Query namedQuery = em.createNamedQuery(nomeDaBusca);

			if (argumentos != null) {
				for (int i = 0; i < argumentos.length; i++) {
					Object arg = argumentos[i];
					namedQuery.setParameter(i + 1, arg); // Par�metros de buscas
															// s�o 1-based.
				}
			}

			List<T> lista = namedQuery.getResultList();

			return new LinkedHashSet<T>(lista);
		} catch (RuntimeException e) {
			throw new InfraestruturaException(e);
		}
	}

	private String getNomeDaBuscaPeloMetodo(Method metodo) {
		return tipo.getSimpleName() + "." + metodo.getName();
	}

	
}