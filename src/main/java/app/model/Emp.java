package app.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import util.Jobs;

/**
 * @author Pablo
 *
 */
@NamedQueries(
		{	@NamedQuery
			(	name = "Emp.recuperaListaDeLances",
				query = "select e from Emp e order by e.id"
			),
			@NamedQuery
			(	name = "Emp.recuperaUltimoLance",
				query = "select e from Emp e where e.deptno = ?1 order by e.id desc"
			),
			@NamedQuery
			(	name = "Emp.recuperaUmLanceComProduto",
				query = "select e from Emp e left outer join fetch e.deptno where e.id = ?1"
			)
		})

@Entity
@Table(name="EMP")
@SequenceGenerator(name="SEQUENCIA_EMP",
				   sequenceName="SEQ_EMP",
				   allocationSize=1)
public class Emp {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQUENCIA_EMP")
	private long id;
	
	@Basic(optional= false)
	@Column(name = "EMPNO")
	private Short empno;
	
	@Basic(optional= false)
	@Column(name = "ENAME")
	private String ename;
	
	@Column(name = "JOB")
	private Jobs job;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="DEPTNO")
	private Dept deptno;
	
	@Column(name = "HIREDATE")
	@Temporal(TemporalType.DATE)
	private Date hireDate;
	
	@Column(name = "SAL")
	private BigDecimal sal;

	// ********* Constructors *********
	
	/**
	 * empty constructor
	 */
	public Emp() {
		super();
	}

	/**
	 * constructor with all parameters
	 * @param empno (employer number)
	 * @param ename (employer name)
	 * @param job (employer job type)
	 * @param deptno (number of the employer department)
	 * @param hireDate (date when the employer was hire)
	 * @param sal (value of the employer salary)
	 */
	public Emp(Short empno, String ename, Jobs job, Dept deptno, Date hireDate, BigDecimal sal) {
		super();
		this.empno = empno;
		this.ename = ename;
		this.job = job;
		this.deptno = deptno;
		this.hireDate = hireDate;
		this.sal = sal;
	}

	// ********* Get and set methods *********
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Short getEmpno() {
		return empno;
	}

	public void setEmpno(Short empno) {
		this.empno = empno;
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public Jobs getJob() {
		return job;
	}

	public void setJob(Jobs job) {
		this.job = job;
	}

	public Dept getDeptno() {
		return deptno;
	}

	public void setDeptno(Dept deptno) {
		this.deptno = deptno;
	}

	public Date getHireDate() {
		return hireDate;
	}

	public void setHireDate(Date hireDate) {
		this.hireDate = hireDate;
	}

	public BigDecimal getSal() {
		return sal;
	}

	public void setSal(BigDecimal sal) {
		this.sal = sal;
	}
	
}
