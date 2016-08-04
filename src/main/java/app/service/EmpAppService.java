package app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.dao.DeptDAO;
import app.dao.EmpDAO;
import app.model.Dept;
import app.model.Emp;
import excecao.DepartamentoNaoEncontradoException;
import excecao.EmpNaoEncontradoException;
import excecao.ObjetoNaoEncontradoException;

/**
 * @author Pablo
 *
 */
@Service
public class EmpAppService {

	private DeptDAO deptDao = null;
	private EmpDAO empDao = null;

	//@Autowired
	public void setEmpDao(EmpDAO empDao) {
		this.empDao = empDao;
	}
	
	//@Autowired
	public void setDeptDao(DeptDAO deptDao) {
		this.deptDao = deptDao;
	}
	
	public DeptDAO getDeptDao() {
		return deptDao;
	}

	public EmpDAO getEmpDao() {
		return empDao;
	}
	
	@Transactional
	public void change(Emp oneEmp)throws EmpNaoEncontradoException{
		try {
			empDao.getPorIdComLock(oneEmp.getId());
			empDao.altera(oneEmp);
		} catch (ObjetoNaoEncontradoException e) {
			throw new EmpNaoEncontradoException("Not found a Department");
		}
	}


	@Transactional
	public long inclui(Emp oneEmp)throws EmpNaoEncontradoException{
		
		Dept oneDept = oneEmp.getDeptno();
		
		try {
			oneDept = deptDao.getPorIdComLock(oneDept.getId());
		} catch (ObjetoNaoEncontradoException e) {
			throw new EmpNaoEncontradoException("Not found a Department");
		}
		
		return empDao.inclui(oneEmp).getId();
	}
	
	
	@Transactional
	public void excludes(Emp umEmp) throws EmpNaoEncontradoException	{	
		empDao.exclui(umEmp);
	}
	
	@Transactional
	public void remove(Emp umEmp) throws EmpNaoEncontradoException	{	
		empDao.remove(umEmp);
	}

	public Emp getOneEmp(long numero) throws EmpNaoEncontradoException{
		try{	
			return empDao.getPorId(numero);
		}catch(ObjetoNaoEncontradoException e){	
			throw new EmpNaoEncontradoException("Empregado não encontrado");
		}
	}

	public List<Emp> recuperaEmps(){	
		return empDao.recuperaListaDeFunc();
	}
	
	public List<Emp> recuperaFuncsDeDept(long deptNum){
		return empDao.recuperaFuncsDeDept(deptNum);
	}

	public List<Emp> recuperaEmpPeloNome(String nomeEmp) {
		return empDao.recuperaEmpPeloNome(nomeEmp);
	}

	public Emp recuperaUmFuncDeDept(Long valueAt) throws EmpNaoEncontradoException{
		try{	
			return empDao.recuperaUmFuncDeDept(valueAt);
		}catch(ObjetoNaoEncontradoException e){	
			throw new EmpNaoEncontradoException("Empregado não encontrado");
		}
	}
}
