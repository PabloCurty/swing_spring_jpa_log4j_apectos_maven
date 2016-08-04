package control;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import app.model.Dept;
import app.service.DeptAppService;
import corejava.Console;
import excecao.DepartamentoComFuncionarioException;
import excecao.DepartamentoNaoEncontradoException;

/**
 * @author Pablo
 *
 */
public class App {
	public static void main(String[] args) {
		
		String name;
		Long number;
		String location;
		Dept oneDept;
		
		@SuppressWarnings("resource")
		ApplicationContext fabrica = new ClassPathXmlApplicationContext("beans-jpa.xml");
		
		DeptAppService deptAppService = (DeptAppService) fabrica.getBean("deptAppService");

		
		boolean continua = true;
		while (continua) {
			System.out.println('\n' + "What you want to do?");
			System.out.println('\n' + "1. registering a department");
			System.out.println("2. Change a department");
			System.out.println("3. Remove a department");
			System.out.println("4. List the department and its employees");
			System.out.println("5. List all the department and its employees");
			System.out.println("6. exit");

			int opcao = Console.readInt('\n' + "Type a number between 1 and 6:");

			switch (opcao) {
				case 1: {
	
					name = Console.readLine('\n' + "Enter the department name: ");
					number = (long) Console.readInt("Enter the department number: ");
					location = Console.readLine("Enter the department location: ");
	
					Dept dept = new Dept(number, name, location);
	
					long num = deptAppService.inclui(dept);
	
					System.out.println('\n' + "Department number " + num + ", successfully included!");
	
					break;
	
				}
				case 2: {
					int answer = Console.readInt('\n' + "Type de department number that you want change: ");
					try{
						oneDept = deptAppService.getOneDept(answer);
					}catch(DepartamentoNaoEncontradoException e){
						System.out.println('\n' + e.getMessage());
						break;
					}
					System.out.println('\n' + 
							"Name = " + oneDept.getNameDept() + 
							"Number = " + oneDept.getDeptno() +
							"Location = " + oneDept.getLoc());
					
					System.out.println('\n' + "What do you want to change?");
					System.out.println('\n' + "1. Name");
					System.out.println('\n' + "2. Number");
					System.out.println("3. Location");
					
					int changeOption = Console.readInt('\n' + "Type a number between 1 to 3:");
					
					switch (changeOption) {
						case 1:{
							String newName = Console.readLine("Type new name");
							oneDept.setNameDept(newName);
							try {
								deptAppService.change(oneDept);
								System.out.println('\n' + "successfully name change!");
							} catch (DepartamentoNaoEncontradoException e) {
								System.out.println('\n' + e.getMessage());
							}
							break;
						}
						case 2:{
							Long newNumber = (long) Console.readInt("Type new number");
							oneDept.setDeptno(newNumber);
							try {
								deptAppService.change(oneDept);
								System.out.println('\n' + "successfully number change!");
							} catch (DepartamentoNaoEncontradoException e) {
								System.out.println('\n' + e.getMessage());
							}
							break;
						}
						case 3:{
							String newLocation = Console.readLine("Type new location");
							oneDept.setLoc(newLocation);
							try {
								deptAppService.change(oneDept);
								System.out.println('\n' + "successfully location change!");
							} catch (DepartamentoNaoEncontradoException e) {
								System.out.println('\n' + e.getMessage());
							}
							break;
						}
						default:{
							System.out.println('\n' + "Invalid option!");
						}	
					}
					break;
				}
				case 3:{
					int answer = Console.readInt('\n' + "Type de department number that you want to remove: ");
					try {
						oneDept = deptAppService.getOneDept(answer);
					} catch (DepartamentoNaoEncontradoException e) {
						System.out.println('\n' + e.getMessage());
						break;
					}
					System.out.println('\n' + 
							"Name = " + oneDept.getNameDept() + 
							"Number = " + oneDept.getDeptno()  +
							"Location = " + oneDept.getLoc());
					
					String resp = Console.readLine('\n' + "Confirms the removal of the product?");
					
					if(resp.equals("s")){
						try {
							deptAppService.excludes(oneDept);
						} catch (DepartamentoNaoEncontradoException | DepartamentoComFuncionarioException e) {
							System.out.println('\n' + e.getMessage());
						}
					}else{
						System.out.println("Successfully removed product");
					}
					
					break;
				}
				default:
					break;
			}

		}
	}
}
