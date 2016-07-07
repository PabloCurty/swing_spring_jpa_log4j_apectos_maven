package app.dao.impl;

import app.dao.EmpDAO;
import app.model.Emp;

/**
 * @author Pablo
 *
 */
public abstract class EmpDaoImpl extends JPADaoGenerico<Emp, Long> implements EmpDAO{
	public EmpDaoImpl() {
		super(Emp.class);
	}
}
