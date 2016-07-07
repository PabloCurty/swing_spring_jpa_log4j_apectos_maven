package app.dao.controle;

import net.sf.cglib.proxy.Enhancer;

public class FabricaDeDao {
	// Esse método pode ser executado de 2 formas:
	// 1. produtoDAO =
	// FabricaDeDao.<ProdutoDAOImpl>getDao(ProdutoDAOImpl.class);
	// 2. produtoDAO = FabricaDeDao.getDao(ProdutoDAOImpl.class);

	@SuppressWarnings("unchecked")
	public static <T> T getDao(String classeDoDaoComoString) throws Exception {
		
		// recebe string dao.impl.ProdutoDAOImpl
		Class<?> classeDoDao = Class.forName(classeDoDaoComoString); 

		
		// método create retorna objeto que é subclasse de produto dao impl pelo parametro classe do dao
		// faz override dos metodos não final(nosso caso são só 3)
		// chama o método do interceptador
		
		// cria classe que extende produto dao impl(cria um proxy)
		return (T) Enhancer.create(classeDoDao, new InterceptadorDeDAO()); // biblioteca para criar proxy cgLib

		// O proxy deve estender a classe (ProdutoDAOImpl por exemplo),
		// que deve estender a classe JPADaoGenerico. O proxy deve ainda
		// chamar o método intercept() da classe interceptadora, isto é, da
		// classe InterceptadorDeDAO (classe callback).

		// Enhancer enhancer = new Enhancer();
		// enhancer.setSuperclass(classeDoDao); // Superclasse do DAO
		// enhancer.setCallback(new InterceptadorDeDAO()); // Interceptador do
		// DAO

		// return (T) enhancer.create();
	}
}