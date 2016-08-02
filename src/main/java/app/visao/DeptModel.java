package app.visao;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import app.model.Dept;
import app.service.DeptAppService;
import excecao.DepartamentoNaoEncontradoException;

public class DeptModel extends AbstractTableModel {
	
private static final long serialVersionUID = 1L;
	
	public static final int COLUNA_NUMERO = 0;
	public static final int COLUNA_NOME = 1;
	public static final int COLUNA_LOCALIZACAO = 2;
	public static final int COLUNA_ACAO = 3;
	
    private final static int NUMERO_DE_LINHAS_POR_PAGINA = 6;

	private String[] localidades = {"", "Praia Vermelha", "Valonguinho", "Gragoata", "Rio das Ostras" };

	private static DeptAppService deptService;
	
    static
    {
    	@SuppressWarnings("resource")
		ApplicationContext fabrica = new ClassPathXmlApplicationContext("beans-jpa.xml");

    	deptService = (DeptAppService)fabrica.getBean ("deptAppService");
    }
    
    private Map<Integer, Dept > cache;
    private int rowIndexAnterior = 0;
    private Integer qtd;
    private String nomeDept;
    
    
    public DeptModel(){	
    	this.qtd = null;
		this.cache = new HashMap<Integer, Dept>(170);
	}

    public void setNomeDept(String nomeDept){
    	this.nomeDept = nomeDept;
    }
    
    
    public String getColumnName(int c)
	{
		if(c == COLUNA_NUMERO) return "Número";
		if(c == COLUNA_NOME) return "Nome";
		if(c == COLUNA_LOCALIZACAO) return "Localização";
		if(c == COLUNA_ACAO) return "Ação";
		return null;
	}
    
    
    
	@Override
	public int getRowCount() {
		if(qtd == null)
			qtd =  4;//(int)deptService.recuperaQtdPeloNome(nomeDept);
		return qtd;
	}

	@Override
	public int getColumnCount() {
		return 4;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (!cache.containsKey(rowIndex)) {
			if(cache.size() > (NUMERO_DE_LINHAS_POR_PAGINA * 3)){
				cache.clear();
				if(rowIndex >= rowIndexAnterior) {
					
					// TODO fazer pesquisa por nome
					//deptService.getListaDepts(); // 
					List<Dept> resultados = deptService.getListaDepts(); // deptService.recuperaPeloNome(nomeDept, rowIndex - (NUMERO_DE_LINHAS_POR_PAGINA - 1), NUMERO_DE_LINHAS_POR_PAGINA * 2);
					int j = 0;
					for (Dept dept : resultados) {
						cache.put(rowIndex - (NUMERO_DE_LINHAS_POR_PAGINA - 1) + j, dept);
						j++;
					}
				}else {
					int inicio = rowIndex - NUMERO_DE_LINHAS_POR_PAGINA;
					if (inicio < 0) inicio = 0;
					int j = 0;
					//deptService.getListaDepts(); // 
					List<Dept> resultados = deptService.getListaDepts(); // deptService.recuperaPeloNome(nomeDept, inicio, NUMERO_DE_LINHAS_POR_PAGINA * 2);
					for (Dept dept : resultados) {
						cache.put(inicio + j, dept);
						j++;
					}
				}
			}else if(rowIndex >= rowIndexAnterior){
					//deptService.getListaDepts(); //
					List<Dept> resultados =  deptService.getListaDepts(); // deptService.recuperaPeloNome(nomeDept, rowIndex, NUMERO_DE_LINHAS_POR_PAGINA * 2);
					int j = 0;
					for (Dept dept : resultados) {
						cache.put(rowIndex + j, dept);
						j++;
					}
				  } else{
					   int inicio = rowIndex - (NUMERO_DE_LINHAS_POR_PAGINA * 2 - 1);
					   if (inicio < 0) inicio = 0;
					   
					   int j = 0;
					   //deptService.getListaDepts(); // 
					   List<Dept> resultados = deptService.getListaDepts(); // deptService.recuperaPeloNome(nomeDept, inicio, NUMERO_DE_LINHAS_POR_PAGINA * 2);
					   for (Dept dept : resultados) {
							cache.put(inicio + j, dept);
							j++;
					   }
					   
				  }
		}
		rowIndexAnterior = rowIndex;
		
		
		Dept dept = cache.get(rowIndex);

		if(columnIndex == COLUNA_NUMERO)
			return dept.getDeptno();
		else if (columnIndex == COLUNA_NOME)
			return dept.getNameDept();
		else if (columnIndex == COLUNA_LOCALIZACAO)
			//return localidades[dept.getLoc()];
			return dept.getLoc();
		else
			return null;
	}

	
	public Class<?> getColumnClass(int c)
	{
		Class<?> classe = null;
		if(c == COLUNA_NUMERO) classe = Integer.class;
		if(c == COLUNA_NOME) classe = String.class;
		if(c == COLUNA_LOCALIZACAO) classe = String.class;
		if(c == COLUNA_ACAO) classe = ButtonColumn.class;

		return classe;
	}
	
	@Override
	public void setValueAt(Object obj, int r, int c) {
		Dept umDept = cache.get(r);

		if(c == COLUNA_NOME) umDept.setNameDept((String)obj);
		
		if(c == COLUNA_NUMERO) umDept.setDeptno((Double) obj);

		if(c == COLUNA_LOCALIZACAO){
			if(((String)obj).equalsIgnoreCase("Praia vermelha"))
				umDept.setLoc("Praia vermelha");
			else if(((String)obj).equalsIgnoreCase("Valonguinho"))
				umDept.setLoc("Valonguinho");
			else if(((String)obj).equalsIgnoreCase("Gragoata"))
				umDept.setLoc("Gragoata");
			else if(((String)obj).equalsIgnoreCase("Rio das Ostras"))
				umDept.setLoc("Rio das Ostras");
		}

		try {	
			deptService.change(umDept);
		} catch (DepartamentoNaoEncontradoException e){
			e.printStackTrace();
		}
		
		
	}
}
