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

	public static final int COLUNA_ID = 0;
	public static final int COLUNA_NUMERO = 1;
	public static final int COLUNA_NOME = 2;
	public static final int COLUNA_LOCALIZACAO = 3;
	public static final int COLUNA_ACAO = 4;

	private final static int NUMERO_DE_LINHAS_POR_PAGINA = 6;

	private String[] localidades = { "", "Praia Vermelha", "Valonguinho", "Gragoata", "Rio das Ostras" };

	private static DeptAppService deptService;

	static {
		@SuppressWarnings("resource")
		ApplicationContext fabrica = new ClassPathXmlApplicationContext("beans-jpa.xml");

		deptService = (DeptAppService) fabrica.getBean("deptAppService");
	}

	private Map<Integer, Dept> cache;
	private int rowIndexAnterior = 0;
	private Integer qtd;
	private String nomeDept;

	public DeptModel() {
		this.qtd = null;
		this.cache = new HashMap<Integer, Dept>(170);
	}

	public void setNomeDept(String nomeDept) {
		this.nomeDept = nomeDept.toUpperCase()+"%";
	}

	public String getColumnName(int c) {
		if (c == COLUNA_ID)
			return "ID";
		if (c == COLUNA_NUMERO)
			return "Número";
		if (c == COLUNA_NOME)
			return "Nome";
		if (c == COLUNA_LOCALIZACAO)
			return "Localização";
		if (c == COLUNA_ACAO)
			return "Ação";
		return null;
	}

	@Override
	public int getRowCount() {
		if (qtd == null)
			qtd = deptService.recuperaDeptPeloNome(nomeDept).size();
		return qtd;
	}

	@Override
	public int getColumnCount() {
		return 5;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (!cache.containsKey(rowIndex)) {
			if (cache.size() > (NUMERO_DE_LINHAS_POR_PAGINA * 3)) {
				cache.clear();
				if (rowIndex >= rowIndexAnterior) {

					List<Dept> resultados = deptService.recuperaDPeloNome(nomeDept, rowIndex - (NUMERO_DE_LINHAS_POR_PAGINA - 1), NUMERO_DE_LINHAS_POR_PAGINA * 2); //deptService.recuperaDeptPeloNome(nomeDept); 
					int j = 0;
					for (Dept dept : resultados) {
						cache.put(rowIndex - (NUMERO_DE_LINHAS_POR_PAGINA - 1) + j, dept);
						j++;
					}
				} else {
					int inicio = rowIndex - NUMERO_DE_LINHAS_POR_PAGINA;
					if (inicio < 0)
						inicio = 0;
					int j = 0;
					List<Dept> resultados = deptService.recuperaDPeloNome(nomeDept,inicio, NUMERO_DE_LINHAS_POR_PAGINA * 2); //deptService.recuperaDeptPeloNome(nomeDept); 
					for (Dept dept : resultados) {
						cache.put(inicio + j, dept);
						j++;
					}
				}
			} else if (rowIndex >= rowIndexAnterior) {
				List<Dept> resultados = deptService.recuperaDPeloNome(nomeDept,rowIndex, NUMERO_DE_LINHAS_POR_PAGINA * 2); //deptService.recuperaDeptPeloNome(nomeDept); 
				int j = 0;
				for (Dept dept : resultados) {
					cache.put(rowIndex + j, dept);
					j++;
				}
			} else {
				int inicio = rowIndex - (NUMERO_DE_LINHAS_POR_PAGINA * 2 - 1);
				if (inicio < 0)
					inicio = 0;

				int j = 0;
				List<Dept> resultados = deptService.recuperaDPeloNome(nomeDept, inicio, NUMERO_DE_LINHAS_POR_PAGINA * 2); //deptService.recuperaDeptPeloNome(nomeDept); 
				for (Dept dept : resultados) {
					cache.put(inicio + j, dept);
					j++;
				}

			}
		}
		rowIndexAnterior = rowIndex;

		Dept dept = cache.get(rowIndex);

		if (columnIndex == COLUNA_ID)
			return dept.getId();
		if (columnIndex == COLUNA_NUMERO)
			return dept.getDeptno();
		else if (columnIndex == COLUNA_NOME)
			return dept.getNameDept();
		else if (columnIndex == COLUNA_LOCALIZACAO)
			// return localidades[dept.getLoc()];
			return dept.getLoc();
		else
			return null;
	}

	public Class<?> getColumnClass(int c) {
		Class<?> classe = null;
		if (c == COLUNA_ID)
			classe = Long.class;
		if (c == COLUNA_NUMERO)
			classe = Long.class;
		if (c == COLUNA_NOME)
			classe = String.class;
		if (c == COLUNA_LOCALIZACAO)
			classe = String.class;
		if (c == COLUNA_ACAO)
			classe = ButtonColumn.class;

		return classe;
	}

	// Para que as células referentes às colunas 1 em diante possam ser editadas
	public boolean isCellEditable(int r, int c) {
		return c != 0;
	}

	@Override
	public void setValueAt(Object obj, int r, int c) {
		Dept umDept = cache.get(r);

		if (c == COLUNA_ID)
			umDept.setId((Long) obj);
		
		if (c == COLUNA_NOME)
			umDept.setNameDept((String) obj);

		if (c == COLUNA_NUMERO)
			umDept.setDeptno((Long) obj);

		if (c == COLUNA_LOCALIZACAO) {
			if (((String) obj).equalsIgnoreCase("Praia vermelha"))
				umDept.setLoc("Praia vermelha");
			else if (((String) obj).equalsIgnoreCase("Valonguinho"))
				umDept.setLoc("Valonguinho");
			else if (((String) obj).equalsIgnoreCase("Gragoata"))
				umDept.setLoc("Gragoata");
			else if (((String) obj).equalsIgnoreCase("Rio das Ostras"))
				umDept.setLoc("Rio das Ostras");
		}

		try {
			deptService.change(umDept);
		} catch (DepartamentoNaoEncontradoException e) {
			e.printStackTrace();
		}

	}
}
