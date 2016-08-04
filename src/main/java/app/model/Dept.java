package app.model;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author Pablo
 * Spring JPA class to department
 */
@NamedQueries(
		{	@NamedQuery
			(	name = "Dept.recuperaUmDeptEFunc",
				query = "select d from Dept d left outer join fetch d.emps where d.id = ?1"
			),
			@NamedQuery
			(	name = "Dept.recuperaListaDeDept",
				query = "select d from Dept d order by d.id"
			),
			@NamedQuery
			(	name = "Dept.recuperaListaDeDeptEFunc",
				query = "select distinct d from Dept d left outer join fetch d.emps order by d.id asc"
			),
			@NamedQuery
			(	name = "Dept.recuperaConjuntoDeDeptEFunc",
				query = "select d from Dept d left outer join fetch d.emps order by d.id asc"
			),
			@NamedQuery
			(	name = "Dept.recuperaDeptPeloNome",
				query = "select d from Dept d where d.nameDept = ?1 order by d.id asc"
			)
		})

@Entity
@Table(name="DEPT")
@SequenceGenerator(name="SEQUENCIA_DEPT",
				   sequenceName="SEQ_DEPT",
				   allocationSize=1)
public class Dept {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQUENCIA_DEPT")
	private Long id;
	
	@Basic(optional= false)
	@Column(name = "DEPTNO")
	private Long deptno;
	
	@Basic(optional= false)
	@Column(name = "DNAME")
	private String nameDept;
	
	@Column(name = "LOC")
	private String loc;
	
	@OneToMany(mappedBy="deptno", cascade={CascadeType.ALL})
	@OrderBy
	private List<Emp> emps;

	// ********* Constructors *********
	
	/**
	 * empty constructor
	 */
	public Dept() {
		super();
	}

	
	/**
	 * constructor with not null parameters
	 * @param deptno (department number)
	 * @param nameDept (department name)
	 */
	public Dept(Long deptno, String nameDept) {
		super();
		this.deptno = deptno;
		this.nameDept = nameDept;
	}


	/**
	 * constructor with all parameters any less employers list
	 * @param deptno (department number)
	 * @param nameDept (department name)
	 * @param loc ( department location)
	 */
	public Dept(Long deptno, String nameDept, String loc) {
		super();
		this.deptno = deptno;
		this.nameDept = nameDept;
		this.loc = loc;
	}
	
	
	/**
	 * constructor with all parameters
	 * @param deptno (department number)
	 * @param nameDept (department name)
	 * @param loc ( department location)
	 * @param emps (employers list)
	 */
	public Dept(Long deptno, String nameDept, String loc, List<Emp> emps) {
		super();
		this.deptno = deptno;
		this.nameDept = nameDept;
		this.loc = loc;
		this.emps = emps;
	}


	// ********* Get and set methods *********

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDeptno() {
		return deptno;
	}

	public void setDeptno(Long deptno) {
		this.deptno = deptno;
	}

	public String getNameDept() {
		return nameDept;
	}

	public void setNameDept(String nameDept) {
		this.nameDept = nameDept;
	}

	public String getLoc() {
		return loc;
	}


	public void setLoc(String loc) {
		this.loc = loc;
	}

	public List<Emp> getEmps() {
		return emps;
	}

	public void setEmps(List<Emp> emps) {
		this.emps = emps;
	}
	
}
