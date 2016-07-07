package app.dao.controle;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import anotacao.RecuperaConjunto;
import anotacao.RecuperaLista;
import anotacao.RecuperaObjeto;
import anotacao.RecuperaUltimoOuPrimeiro;
import app.dao.impl.JPADaoGenerico;
import excecao.InfraestruturaException;

public class InterceptadorDeDAO implements MethodInterceptor 
{
	/* Parametros:
	 * 
	 * objeto - "this", o objeto "enhanced", isto é, o proxy.
	 * 
	 * metodo - o  m�todo   interceptado,  isto é,  um método  da 
	 *          interface ProdutoDAO, LanceDAO, etc. 
	 * 
	 * args - um  array  de args; tipos  primitivos são empacotados.
	 *        Contém   os   argumentos  que  o  m�todo  interceptado 
	 *        recebeu.
	 * 
	 * metodoProxy - utilizado para executar um método super. Veja o
	 *               comenário abaixo.
	 * 
	 * MethodProxy  -  Classes  geradas pela  classe Enhancer passam 
	 * este objeto para o objeto MethodInterceptor registrado quando
	 * um método  interceptado é  executado.  Ele pode ser utilizado
	 * para  invocar o  m�todo  original,  ou  chamar o mesmo método
	 * sobre um objeto diferente do mesmo tipo.
	 * 
	 */
	
	public Object intercept (Object objeto,    // proxy
    		                 Method metodo,    // método sendo executado
    		                 Object[] args,    //  
                             MethodProxy metodoDoProxy) 
    	throws Throwable 
    {
		// O s�mbolo ? representa um tipo desconhecido.
        JPADaoGenerico<?,?> daoGenerico = (JPADaoGenerico<?,?>)objeto;

        if(metodo.isAnnotationPresent(RecuperaLista.class))
		{	// O m�todo buscaLista() retorna um List
        	return daoGenerico.buscaLista(metodo, args);
        }
        else if(metodo.isAnnotationPresent(RecuperaConjunto.class))
        {	// O m�todo buscaConjunto() retorna um Set
        	return daoGenerico.buscaConjunto(metodo, args);
        }
        else if(metodo.isAnnotationPresent(RecuperaUltimoOuPrimeiro.class))
        {	// O m�todo buscaUltimoOuPrimeiro() retorna um Objeto (Entidade)
        	return daoGenerico.buscaUltimoOuPrimeiro(metodo, args);
        }
        else if(metodo.isAnnotationPresent(RecuperaObjeto.class))
        {	// O m�todo busca() retorna um Objeto (Entidade)
        	return daoGenerico.busca(metodo, args);
        }
        else 
        {  	throw new InfraestruturaException("a non-final method ceased to be noted");
        	// return metodoDoProxy.invokeSuper(objeto, args);
        }
    }
}
