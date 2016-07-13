package rest;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import app.service.DeptAppService;

@Path("/departamento")
@Produces("application/json;charset=utf-8")
@Consumes("application/json;charset=utf-8")
public class DeptResource {

	@Inject
	DeptAppService deptAppService;
}
