package rest;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import app.service.DeptAppService;
import excecao.DepartamentoNaoEncontradoException;

@Path("/departamento")
@Produces("application/json;charset=utf-8")
@Consumes("application/json;charset=utf-8")
public class DeptRestResource {

	@Inject
	DeptAppService deptAppService;
	
	@GET
	@Path("buscaID/{id}")
	public String getNomeDept(@PathParam("id") Long id){
		String name = "dept não encontrado";
		try {
			name = deptAppService.getOneDept(id).getNameDept();
		} catch (DepartamentoNaoEncontradoException e) {
			e.printStackTrace();
		}
		return name;
	}
	
	@GET
	@Path("hello")
	@Produces(MediaType.TEXT_PLAIN)
	public String hello(){
		
		return "hello world";
	}
}
