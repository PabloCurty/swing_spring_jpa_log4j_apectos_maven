package app.visao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import app.model.Dept;
import app.model.Emp;
import app.service.DeptAppService;
import app.service.EmpAppService;
import excecao.DepartamentoNaoEncontradoException;
import excecao.EmpNaoEncontradoException;
import util.Jobs;

public class EmpModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	public static final int COLUNA_ID = 0;
	public static final int COLUNA_NUMERO = 1;
	public static final int COLUNA_NOME = 2;
	public static final int COLUNA_JOB = 3;
	public static final int COLUNA_DEPT = 4;
	public static final int COLUNA_ACAO = 5;

	private final static int NUMERO_DE_LINHAS_POR_PAGINA = 6;

	private String[] jobs = { "", Jobs.CONSULTANT.toString(), Jobs.DEVELOPER.toString(), Jobs.MANAGER.toString(),
			Jobs.SYSTEMS_ANALYST.toString(), Jobs.TESTER.toString() };

	private static EmpAppService empService;

	static {
		@SuppressWarnings("resource")
		ApplicationContext fabrica = new ClassPathXmlApplicationContext("beans-jpa.xml");

		empService = (EmpAppService) fabrica.getBean("empAppService");
	}


	private Map<Integer, Emp> cache;
	private int rowIndexAnterior = 0;
	private Integer qtd;
	private String nomeEmp;

	public EmpModel() {
		this.qtd = null;
		this.cache = new HashMap<Integer, Emp>(170);
	}

	public void setNomeEmp(String nomeEmp) {
		this.nomeEmp = nomeEmp;
	}

	public String getColumnName(int c) {
		if (c == COLUNA_ID)
			return "ID";
		if (c == COLUNA_NUMERO)
			return "Número";
		if (c == COLUNA_NOME)
			return "Nome";
		if (c == COLUNA_JOB)
			return "Job";
		if (c == COLUNA_DEPT)
			return "Departamento";
		if (c == COLUNA_ACAO)
			return "Ação";
		return null;
	}

	@Override
	public int getRowCount() {
		if (qtd == null)
			qtd = empService.recuperaEmpPeloNome(nomeEmp).size();
		return qtd;
	}

	@Override
	public int getColumnCount() {
		return 6;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (!cache.containsKey(rowIndex)) {
			if (cache.size() > (NUMERO_DE_LINHAS_POR_PAGINA * 3)) {
				cache.clear();
				if (rowIndex >= rowIndexAnterior) {
					List<Emp> resultados = empService.recuperaEmpPeloNome(nomeEmp); 
					int j = 0;
					for (Emp emp : resultados) {
						cache.put(rowIndex - (NUMERO_DE_LINHAS_POR_PAGINA - 1) + j, emp);
						j++;
					}
				} else {
					int inicio = rowIndex - NUMERO_DE_LINHAS_POR_PAGINA;
					if (inicio < 0)
						inicio = 0;
					int j = 0;
					List<Emp> resultados = empService.recuperaEmpPeloNome(nomeEmp); 
					for (Emp emp : resultados) {
						cache.put(inicio + j, emp);
						j++;
					}
				}
			} else if (rowIndex >= rowIndexAnterior) {
				List<Emp> resultados = empService.recuperaEmpPeloNome(nomeEmp); 
				int j = 0;
				for (Emp emp : resultados) {
					cache.put(rowIndex + j, emp);
					j++;
				}
			} else {
				int inicio = rowIndex - (NUMERO_DE_LINHAS_POR_PAGINA * 2 - 1);
				if (inicio < 0)
					inicio = 0;

				int j = 0;
				List<Emp> resultados = empService.recuperaEmpPeloNome(nomeEmp); 
				for (Emp emp : resultados) {
					cache.put(inicio + j, emp);
					j++;
				}

			}
		}
		rowIndexAnterior = rowIndex;

		Emp emp = cache.get(rowIndex);

		if (columnIndex == COLUNA_ID)
			return emp.getId();
		if (columnIndex == COLUNA_NUMERO)
			return emp.getEmpno();
		else if (columnIndex == COLUNA_NOME)
			return emp.getEname();
		else if (columnIndex == COLUNA_JOB)
			// return localidades[dept.getLoc()];
			return emp.getJob();
		else if (columnIndex == COLUNA_DEPT)
			return emp.getDeptno().getId();
		else
			return null;
	}

	public Class<?> getColumnClass(int c) {
		Class<?> classe = null;
		if (c == COLUNA_ID)
			classe = Long.class;
		if (c == COLUNA_NUMERO)
			classe = Integer.class;
		if (c == COLUNA_NOME)
			classe = String.class;
		if (c == COLUNA_JOB)
			classe = Jobs.class;
		if (c == COLUNA_DEPT)
			classe = Long.class;
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
		Emp umEmp = cache.get(r);

		if (c == COLUNA_ID)
			umEmp.setId((Long) obj);
		
		if (c == COLUNA_NOME)
			umEmp.setEname((String) obj);

		if (c == COLUNA_NUMERO)
			umEmp.setEmpno((Long) obj);

		if (c == COLUNA_JOB) {
			if (((String) obj).equalsIgnoreCase(Jobs.CONSULTANT.toString()))
				umEmp.setJob(Jobs.CONSULTANT);
			else if (((String) obj).equalsIgnoreCase(Jobs.DEVELOPER.toString()))
				umEmp.setJob(Jobs.DEVELOPER);
			else if (((String) obj).equalsIgnoreCase(Jobs.MANAGER.toString()))
				umEmp.setJob(Jobs.MANAGER);
			else if (((String) obj).equalsIgnoreCase(Jobs.SYSTEMS_ANALYST.toString()))
				umEmp.setJob(Jobs.SYSTEMS_ANALYST);
			else if (((String) obj).equalsIgnoreCase(Jobs.SYSTEMS_ANALYST.toString()))
				umEmp.setJob(Jobs.SYSTEMS_ANALYST);
		}
		if (c == COLUNA_DEPT)
				umEmp.setDeptno((Dept) obj);

		try {
			empService.change(umEmp);
		} catch (EmpNaoEncontradoException e) {
			e.printStackTrace();
		}

	}

}
