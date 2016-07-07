package app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.dao.DeptDAO;
import app.dao.EmpDAO;
import app.model.Dept;
import app.model.Emp;
import excecao.DepartamentoNaoEncontradoException;
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
	public long inclui(Emp oneEmp)throws DepartamentoNaoEncontradoException{
		
		Dept oneDept = oneEmp.getDeptno();
		
		try {
			oneDept = deptDao.getPorIdComLock(oneDept.getId());
		} catch (ObjetoNaoEncontradoException e) {
			throw new DepartamentoNaoEncontradoException("Not found a Department");
		}
		
		return empDao.inclui(oneEmp).getId();
	}
	
}
