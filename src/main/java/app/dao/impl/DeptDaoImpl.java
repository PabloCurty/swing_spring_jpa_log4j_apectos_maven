package app.dao.impl;

import app.dao.DeptDAO;
import app.model.Dept;

/**
 * @author Pablo
 *
 */
public abstract class DeptDaoImpl extends JPADaoGenerico<Dept, Long> implements DeptDAO{
	public DeptDaoImpl() {
		super(Dept.class);
	}
}