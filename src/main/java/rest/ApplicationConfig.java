package rest;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("rest")
public class ApplicationConfig extends Application{

	@Override
	public Set<Class<?>> getClasses(){
		Set<Class<?>> resources = new HashSet<>();
		addRestResourcesClasses(resources);
		return resources;
	}
	
	public void addRestResourcesClasses(Set<Class<?>> resources){
		resources.add(rest.DeptRestResource.class);
	}
}
