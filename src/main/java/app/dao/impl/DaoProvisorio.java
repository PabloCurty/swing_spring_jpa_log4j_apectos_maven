package app.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import app.model.Dept;


//TODO dao provisorio so para teste, retirar e colocar no jpaDaoGENERICO
@Repository
public class DaoProvisorio {
	@PersistenceContext
	private EntityManager em;
	
	//TODO por que não funciona?
	public long recuperaQtdPeloNome(String nome) {
		
		long qtd = (Long) em.createQuery("select count(*) from DEPT d where d.dname like :nome;")
						    .setParameter("nome", nome.toUpperCase())
							.getSingleResult();
		return qtd;
	}
	
	@SuppressWarnings("unchecked")
	public List<Dept> recuperaPeloNome(String nome, 
            							  int deslocamento, 
            							  int linhasPorPagina)
	{	
		List<Dept> depts = em
			.createQuery("select * from DEPT d "
					   + "where d.dname like :nome order by d.dname asc")
			.setParameter("nome", nome.toUpperCase())
			.setFirstResult(deslocamento)
			.setMaxResults(linhasPorPagina)
			.getResultList();

		return depts;
	}

}
