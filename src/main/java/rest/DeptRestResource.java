package rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import app.model.Dept;
import app.service.DeptAppService;
import excecao.DepartamentoNaoEncontradoException;

@Path("/departamento")
@Produces("application/json;charset=utf-8")
@Consumes("application/json;charset=utf-8")
public class DeptRestResource {

	ApplicationContext fabrica = new ClassPathXmlApplicationContext("beans-jpa.xml");
	
	DeptAppService deptAppService = (DeptAppService) fabrica.getBean("deptAppService");
	
	@GET
	@Path("buscaID/{id}")
	public String getNomeDept(@PathParam("id") Long id){
		String name = "dept não encontrado";
		try {
			Dept dept = deptAppService.getOneDept(id);
			name = dept.getNameDept();
		} catch (DepartamentoNaoEncontradoException e) {
			e.printStackTrace();
		}
		return name;
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String hello(){
		
		return "hello world";
	}
}
