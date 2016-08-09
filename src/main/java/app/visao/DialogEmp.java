package app.visao;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import app.model.Dept;
import app.model.Emp;
import app.service.DeptAppService;
import app.service.EmpAppService;
import excecao.DepartamentoNaoEncontradoException;
import excecao.EmpNaoEncontradoException;
import util.Jobs;

public class DialogEmp extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;

	private static EmpAppService empService;

	static {
		@SuppressWarnings("resource")
		ApplicationContext fabrica = new ClassPathXmlApplicationContext("beans-jpa.xml");

		empService = (EmpAppService) fabrica.getBean("empAppService");
	}

	private static DeptAppService deptService;

	static {
		@SuppressWarnings("resource")
		ApplicationContext fabrica = new ClassPathXmlApplicationContext("beans-jpa.xml");

		deptService = (DeptAppService) fabrica.getBean("deptAppService");
	}

	private JPanel panel;
	private JTextField nomeTextField;
	private JTextField numeroTextField;
	private JTextField numeroDeptTextField;
	private JComboBox<String> jobsComboBox;

	private Emp umEmp;
	private Dept umDept;

	private JButton novoButton;
	private JButton cadastrarButton;
	private JButton editarButton;
	private JButton alterarButton;
	private JButton removerButton;
	private JButton cancelarButton;
	private JButton buscarButton;
	private JButton buscarDeptButton;

	private JLabel nomeMensagem;
	private JLabel jobMensagem;
	private JLabel numeroMensagem;
	private JLabel nomeDeptMensagem;

	public void designaEmpregadoAFrame() {
		nomeTextField.setText("");
		numeroTextField.setText("");
		jobsComboBox.setSelectedItem("");
		numeroDeptTextField.setText("");

		nomeMensagem.setText("");
		jobMensagem.setText("");
		numeroMensagem.setText("");
		nomeDeptMensagem.setText("");

	}

	public void designaEmpregadoAFrame(Dept umDept) {
		this.umDept = umDept;

		
		numeroDeptTextField.setText(Long.toString(umDept.getId()));

	}

	public void designaEmpregadoAFrame(Emp umEmp) {
		this.umEmp = umEmp;

		nomeTextField.setText(umEmp.getEname());
		numeroTextField.setText(Long.toString(umEmp.getEmpno()));
		jobsComboBox.setSelectedItem(umEmp.getJob().toString());
		numeroDeptTextField.setText(Long.toString(umEmp.getDeptno().getId()));

		nomeMensagem.setText("");
		jobMensagem.setText("");
		numeroMensagem.setText("");
		nomeDeptMensagem.setText("");

	}

	public void novo() {
		designaEmpregadoAFrame();
		nomeTextField.setEnabled(true);
		numeroTextField.setEnabled(true);
		jobsComboBox.setEnabled(true);
		numeroDeptTextField.setEnabled(false);

		novoButton.setEnabled(true);
		cadastrarButton.setEnabled(true);
		editarButton.setEnabled(true);
		alterarButton.setEnabled(true);
		removerButton.setEnabled(true);
		cancelarButton.setEnabled(true);
		buscarButton.setEnabled(true);

	}

	public DialogEmp(JFrame frame) {
		super(frame);

		setBounds(105, 147, 618, 330);
		setTitle("Cadastro de Empregados");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);

		panel = new JPanel();
		panel.setBounds(0, 0, 602, 292);
		getContentPane().add(panel);
		panel.setLayout(null);

		// Titulo do label
		JLabel cadastrarLabel = new JLabel("Cadastro de Empregados");
		cadastrarLabel.setHorizontalAlignment(SwingConstants.CENTER);
		cadastrarLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		cadastrarLabel.setBounds(189, 21, 190, 26);
		panel.add(cadastrarLabel);

		// Nome do empregado
		JLabel nomeLabel = new JLabel("Nome");
		nomeLabel.setBounds(62, 78, 46, 14);
		panel.add(nomeLabel);

		nomeTextField = new JTextField();
		nomeLabel.setLabelFor(nomeTextField);
		nomeTextField.setBounds(138, 75, 278, 20);
		panel.add(nomeTextField);
		nomeTextField.setColumns(50);
		nomeTextField.setBackground(SystemColor.window);

		// número do departamento
		JLabel numeroLabel = new JLabel("numero");
		numeroLabel.setBounds(62, 116, 46, 14);
		panel.add(numeroLabel);

		numeroTextField = new JTextField();
		numeroLabel.setLabelFor(numeroTextField);
		numeroTextField.setBounds(138, 113, 88, 18);
		panel.add(numeroTextField);
		numeroTextField.setColumns(50);
		numeroTextField.setBackground(SystemColor.window);

		// job
		JLabel jobsLabel = new JLabel("Job");
		jobsLabel.setBounds(62, 156, 46, 14);
		panel.add(jobsLabel);

		jobsComboBox = new JComboBox<String>();
		jobsLabel.setLabelFor(jobsComboBox);
		jobsComboBox.setModel(new DefaultComboBoxModel<String>(
				new String[] { "", "DEVELOPER", "SYSTEMS_ANALYST", "MANAGER", "CONSULTANT", "TESTER" }));
		jobsComboBox.setBounds(138, 152, 142, 20);
		jobsComboBox.setBackground(SystemColor.window);
		panel.add(jobsComboBox);

		// número do departamento
		JLabel numeroDeptLabel = new JLabel("ID Dept");
		numeroDeptLabel.setBounds(62, 196, 46, 14);
		panel.add(numeroDeptLabel);

		numeroDeptTextField = new JTextField();
		numeroDeptLabel.setLabelFor(numeroDeptTextField);
		numeroDeptTextField.setBounds(138, 190, 88, 18);
		panel.add(numeroDeptTextField);
		numeroDeptTextField.setColumns(50);
		numeroDeptTextField.setBackground(SystemColor.window);

		// mensagens
		nomeMensagem = new JLabel("");
		nomeMensagem.setForeground(Color.RED);
		nomeMensagem.setFont(new Font("Tahoma", Font.PLAIN, 9));
		nomeMensagem.setBounds(138, 92, 241, 14);
		panel.add(nomeMensagem);

		numeroMensagem = new JLabel("");
		numeroMensagem.setForeground(Color.RED);
		numeroMensagem.setFont(new Font("Tahoma", Font.PLAIN, 9));
		numeroMensagem.setBounds(138, 170, 241, 14);
		panel.add(numeroMensagem);

		jobMensagem = new JLabel("");
		jobMensagem.setForeground(Color.RED);
		jobMensagem.setFont(new Font("Tahoma", Font.PLAIN, 9));
		jobMensagem.setBounds(138, 200, 241, 14);
		panel.add(jobMensagem);

		nomeDeptMensagem = new JLabel("");
		nomeDeptMensagem.setForeground(Color.RED);
		nomeDeptMensagem.setFont(new Font("Tahoma", Font.PLAIN, 9));
		nomeDeptMensagem.setBounds(138, 246, 241, 14);
		panel.add(nomeDeptMensagem);

		novoButton = new JButton("Novo");
		novoButton.setBounds(452, 50, 96, 23);
		panel.add(novoButton);
		novoButton.addActionListener(this);

		cadastrarButton = new JButton("Cadastrar");
		cadastrarButton.setBounds(452, 77, 96, 23);
		panel.add(cadastrarButton);
		cadastrarButton.addActionListener(this);

		editarButton = new JButton("Editar");
		editarButton.setBounds(452, 104, 96, 23);
		panel.add(editarButton);
		editarButton.addActionListener(this);

		alterarButton = new JButton("Alterar");
		alterarButton.setBounds(452, 131, 96, 23);
		panel.add(alterarButton);
		alterarButton.addActionListener(this);

		removerButton = new JButton("Remover");
		removerButton.setBounds(452, 158, 96, 23);
		panel.add(removerButton);
		removerButton.addActionListener(this);

		cancelarButton = new JButton("Cancelar");
		cancelarButton.setBounds(452, 185, 96, 23);
		panel.add(cancelarButton);
		cancelarButton.addActionListener(this);

		buscarButton = new JButton("Buscar");
		buscarButton.setBounds(452, 212, 96, 23);
		panel.add(buscarButton);
		buscarButton.addActionListener(this);

		buscarDeptButton = new JButton("Busca Dept");
		buscarDeptButton.setBounds(138, 224, 142, 20);
		panel.add(buscarDeptButton);
		buscarDeptButton.addActionListener(this);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == novoButton) {
			novo();
		} else if (obj == cadastrarButton) {
			boolean deuErro = validaEmpregado();

			if (!deuErro) {
				umEmp = new Emp();
				umEmp.setEname(nomeTextField.getText().toUpperCase());
				umEmp.setEmpno(Long.parseLong(numeroTextField.getText()));
				umEmp.setJob(Jobs.valueOf(jobsComboBox.getSelectedItem().toString()));

				try {
					umEmp.setDeptno(deptService.getOneDept(Long.parseLong(numeroDeptTextField.getText())));
				} catch (NumberFormatException | DepartamentoNaoEncontradoException e1) {
					JOptionPane.showMessageDialog(this, "Não foi possível encontrar o departamento", "",
							JOptionPane.ERROR_MESSAGE);
				}

				try {
					empService.inclui(umEmp);

					salvo();

					JOptionPane.showMessageDialog(this, "Empregado cadastrado com sucesso", "",
							JOptionPane.INFORMATION_MESSAGE);
				} catch (EmpNaoEncontradoException e2) {
					JOptionPane.showMessageDialog(this, "Não foi possível cadastrar o Empregado", "",
							JOptionPane.ERROR_MESSAGE);
				}

			}
		} else if (obj == editarButton) {
			editavel();
		} else if (obj == alterarButton) {
			boolean deuErro = validaEmpregado();
			if (!deuErro) {
				umEmp.setEname(nomeTextField.getText().toUpperCase());
				umEmp.setEmpno(Long.parseLong(numeroTextField.getText()));
				umEmp.setJob(Jobs.valueOf(jobsComboBox.getSelectedItem().toString()));

				umEmp.getDeptno().setDeptno(Long.parseLong(numeroDeptTextField.getText()));

				try {

					empService.change(umEmp);

					salvo();

					JOptionPane.showMessageDialog(this, "Empregado atualizado com sucesso", "",
							JOptionPane.INFORMATION_MESSAGE);
				} catch (EmpNaoEncontradoException e1) {
					novo();

					JOptionPane.showMessageDialog(this, "Empregado não encontrado", "", JOptionPane.ERROR_MESSAGE);
				}
			}

		} else if (obj == removerButton) {
			try {
				if (umEmp != null) {
					empService.remove(umEmp);
					
					removido();

					JOptionPane.showMessageDialog(this, "Empregado removido com sucesso", "",
							JOptionPane.INFORMATION_MESSAGE);
				}else{
					JOptionPane.showMessageDialog(this, "É preciso buscar empregado primeiro", "",
							JOptionPane.INFORMATION_MESSAGE);
				}
				
			} catch (EmpNaoEncontradoException e1) {
				novo();

				JOptionPane.showMessageDialog(this, "Empregado não encontrado ou Departamento com funcionários", "",
						JOptionPane.ERROR_MESSAGE);
			}
		} else if (obj == cancelarButton) {
			try {
				if (umEmp != null) {
					umEmp = empService.getOneEmp(umEmp.getId());
					umEmp.setEname(nomeTextField.getText().toUpperCase());
					umEmp.setEmpno(Long.parseLong(numeroTextField.getText()));
					umEmp.setJob(Jobs.valueOf(jobsComboBox.getSelectedItem().toString()));

					umEmp.getDeptno().setDeptno(Long.parseLong(numeroDeptTextField.getText()));
				}
				novo();
				// cancelado();
			} catch (EmpNaoEncontradoException e1) {
				novo();

				JOptionPane.showMessageDialog(this, "Empregado não encontrado", "", JOptionPane.ERROR_MESSAGE);
			}
		} else if (obj == buscarButton) {
			DialogTabelaEmp dialog = new DialogTabelaEmp(this);
			dialog.setVisible(true);
		} else if (obj == buscarDeptButton) {
			DialogTabelaDept dialog = new DialogTabelaDept(this);
			dialog.setVisible(true);
		}

	}

	private boolean validaEmpregado() {

		boolean deuErro = false;
		if (nomeTextField.getText().trim().length() == 0) {
			deuErro = true;
			nomeMensagem.setText("Campo de preenchimento obrigatório");
		} else {
			nomeMensagem.setText("");
		}

		if (numeroTextField.getText().trim().length() == 0) {
			deuErro = true;
			numeroMensagem.setText("Campo de preenchimento obrigatório");
		} else {
			numeroMensagem.setText("");
		}
		if (jobsComboBox.getSelectedIndex() == 0) {
			deuErro = true;
			jobMensagem.setText("Campo de preenchimento obrigatório");
		}

		if (numeroDeptTextField.getText().trim().length() == 0) {
			deuErro = true;
			nomeDeptMensagem.setText("Campo de preenchimento obrigatório");
		} else {
			nomeDeptMensagem.setText("");
		}
		return deuErro;
	}

	public void migraDept() {
		nomeTextField.setEnabled(true);
		numeroTextField.setEnabled(true);
		jobsComboBox.setEnabled(true);
		numeroDeptTextField.setEnabled(false);

		novoButton.setEnabled(true);
		cadastrarButton.setEnabled(true);
		editarButton.setEnabled(true);
		alterarButton.setEnabled(true);
		removerButton.setEnabled(true);
		cancelarButton.setEnabled(true);
		buscarButton.setEnabled(true);
	}

	public void salvo() {

		nomeTextField.setEnabled(false);
		numeroTextField.setEnabled(false);
		jobsComboBox.setEnabled(false);
		numeroDeptTextField.setEnabled(false);

		novoButton.setEnabled(true);
		cadastrarButton.setEnabled(false);
		editarButton.setEnabled(true);
		alterarButton.setEnabled(false);
		removerButton.setEnabled(true);
		cancelarButton.setEnabled(false);
		buscarButton.setEnabled(true);
		buscarDeptButton.setEnabled(true);

	}

	public void editavel() {
		nomeTextField.setEnabled(true);
		numeroTextField.setEnabled(true);
		jobsComboBox.setEnabled(true);
		numeroDeptTextField.setEnabled(false);

		novoButton.setEnabled(false);
		cadastrarButton.setEnabled(false);
		editarButton.setEnabled(false);
		alterarButton.setEnabled(true);
		removerButton.setEnabled(true);
		cancelarButton.setEnabled(true);
		buscarButton.setEnabled(false);
		buscarDeptButton.setEnabled(true);
	}

	public void removido() {
		nomeTextField.setEnabled(false);
		numeroTextField.setEnabled(false);
		jobsComboBox.setEnabled(false);
		numeroDeptTextField.setEnabled(false);

		novoButton.setEnabled(true);
		cadastrarButton.setEnabled(false);
		editarButton.setEnabled(false);
		alterarButton.setEnabled(false);
		removerButton.setEnabled(false);
		cancelarButton.setEnabled(false);
		buscarButton.setEnabled(true);
		buscarDeptButton.setEnabled(true);
	}

	public void cancelado() {
		nomeTextField.setEnabled(false);
		numeroTextField.setEnabled(false);
		jobsComboBox.setEnabled(false);
		numeroDeptTextField.setEnabled(false);

		novoButton.setEnabled(true);
		cadastrarButton.setEnabled(false);
		editarButton.setEnabled(true);
		alterarButton.setEnabled(false);
		removerButton.setEnabled(true);
		cancelarButton.setEnabled(false);
		buscarButton.setEnabled(true);
		buscarDeptButton.setEnabled(true);
	}

}
