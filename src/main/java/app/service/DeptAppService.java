package app.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.dao.DeptDAO;
import app.model.Dept;
import excecao.DepartamentoComFuncionarioException;
import excecao.DepartamentoNaoEncontradoException;
import excecao.ObjetoNaoEncontradoException;

/**
 * @author Pablo
 *
 */

public class DeptAppService {
	
	private DeptDAO deptDao = null;

	//@Autowired
	public void setDeptDao(DeptDAO deptDao) {
		this.deptDao = deptDao;
	}

	public DeptDAO getDeptDao() {
		return deptDao;
	}

	public long inclui(Dept umDept) {
		return deptDao.inclui(umDept).getId();
	}
	
	@Transactional
	public void change(Dept oneDept)throws DepartamentoNaoEncontradoException{
		try {
			deptDao.getPorIdComLock(oneDept.getId());
			deptDao.altera(oneDept);
		} catch (ObjetoNaoEncontradoException e) {
			throw new DepartamentoNaoEncontradoException("Not found a Department");
		}
	}


	public Dept getOneDept(long number)throws DepartamentoNaoEncontradoException{
		try {
			return deptDao.getPorId(number);
		} catch (ObjetoNaoEncontradoException e) {
			throw new DepartamentoNaoEncontradoException("Not found a Department");
		}
	}
	
	@Transactional
	public void excludes(Dept oneDept) throws DepartamentoNaoEncontradoException, DepartamentoComFuncionarioException {
		
		try {
			Dept dept = deptDao.recuperaUmDeptEFunc(oneDept.getId());
			if(dept.getEmps().size() > 0){
				throw new DepartamentoComFuncionarioException("This department has employers and can not be removed");
			}
			deptDao.exclui(dept);
		} catch (ObjetoNaoEncontradoException e) {
			throw new DepartamentoNaoEncontradoException("Not found a Department");
		}
	}
}
