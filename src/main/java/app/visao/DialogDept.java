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
import app.service.DeptAppService;
import excecao.DepartamentoComFuncionarioException;
import excecao.DepartamentoNaoEncontradoException;

public class DialogDept extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;

	private static DeptAppService deptService;

	static {
		@SuppressWarnings("resource")
		ApplicationContext fabrica = new ClassPathXmlApplicationContext("beans-jpa.xml");

		deptService = (DeptAppService) fabrica.getBean("deptAppService");
	}

	private JPanel panel;
	private JTextField nomeTextField;
	private JTextField numeroTextField;
	private JComboBox<String> localizacaoComboBox;

	private Dept umDept;

	private JLabel nomeMensagem;
	private JLabel localizacaoMensagem;
	private JLabel numeroMensagem;

	private JButton novoButton;
	private JButton cadastrarButton;
	private JButton editarButton;
	private JButton alterarButton;
	private JButton removerButton;
	private JButton cancelarButton;
	private JButton buscarButton;
	// private JButton cadastrarFuncButton;

	public void designaDepartamentoAFrame() {
		nomeTextField.setText("");
		numeroTextField.setText("");
		localizacaoComboBox.setSelectedItem("");

		nomeMensagem.setText("");
		localizacaoMensagem.setText("");
		numeroMensagem.setText("");
	}

	public void designaDepartamentoAFrame(Dept umDept) {
		this.umDept = umDept;

		nomeTextField.setText(umDept.getNameDept());
		numeroTextField.setText(Long.toString(umDept.getDeptno()));
		localizacaoComboBox.setSelectedItem(umDept.getLoc());

		nomeMensagem.setText("");
		localizacaoMensagem.setText("");
		numeroMensagem.setText("");

	}

	public void novo() {
		designaDepartamentoAFrame();
		nomeTextField.setEnabled(true);
		numeroTextField.setEnabled(true);
		localizacaoComboBox.setEnabled(true);
		
		novoButton.setEnabled(true);
		cadastrarButton.setEnabled(true);
		editarButton.setEnabled(true);
		alterarButton.setEnabled(true);
		removerButton.setEnabled(true);
		cancelarButton.setEnabled(true);
		buscarButton.setEnabled(true);

	}

	public DialogDept(JFrame frame) {
		super(frame);

		setBounds(105, 147, 618, 330);
		setTitle("Cadastro de Departamentos");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);

		panel = new JPanel();
		panel.setBounds(0, 0, 602, 292);
		getContentPane().add(panel);
		panel.setLayout(null);

		// Titulo do label
		JLabel cadastrarLabel = new JLabel("Cadastro de Departamentos");
		cadastrarLabel.setHorizontalAlignment(SwingConstants.CENTER);
		cadastrarLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		cadastrarLabel.setBounds(189, 21, 190, 26);
		panel.add(cadastrarLabel);

		// Nome do departamento
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

		// localização
		JLabel localizacaoLabel = new JLabel("localização");
		localizacaoLabel.setBounds(62, 156, 46, 14);
		panel.add(localizacaoLabel);

		localizacaoComboBox = new JComboBox<String>();
		localizacaoLabel.setLabelFor(localizacaoComboBox);
		localizacaoComboBox.setModel(new DefaultComboBoxModel<String>(
				new String[] { "", "Praia vermelha", "Valonguinho", "Gragoata", "Rio das Ostras" }));
		localizacaoComboBox.setBounds(138, 152, 142, 20);
		localizacaoComboBox.setBackground(SystemColor.window);
		panel.add(localizacaoComboBox);

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

		localizacaoMensagem = new JLabel("");
		localizacaoMensagem.setForeground(Color.RED);
		localizacaoMensagem.setFont(new Font("Tahoma", Font.PLAIN, 9));
		localizacaoMensagem.setBounds(138, 170, 241, 14);
		panel.add(localizacaoMensagem);

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

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		Object obj = e.getSource();
		if (obj == novoButton) {
			novo();
		} else if (obj == cadastrarButton) {
			boolean deuErro = validaDepartamento();

			if (!deuErro) {
				umDept = new Dept();
				umDept.setNameDept(nomeTextField.getText().toUpperCase());
				umDept.setDeptno(Long.parseLong(numeroTextField.getText()));
				umDept.setLoc(localizacaoComboBox.getSelectedItem().toString());

				deptService.inclui(umDept);

				salvo();

				JOptionPane.showMessageDialog(this, "Departamento cadastrado com sucesso", "",
						JOptionPane.INFORMATION_MESSAGE);
			}
		} else if (obj == editarButton) {
			editavel();
		} else if (obj == alterarButton) {
			boolean deuErro = validaDepartamento();
			if (!deuErro) {
				umDept.setNameDept(nomeTextField.getText().toUpperCase());
				umDept.setDeptno(Long.parseLong(numeroTextField.getText()));
				umDept.setLoc(localizacaoComboBox.getSelectedItem().toString());

				try {

					deptService.change(umDept);

					salvo();

					JOptionPane.showMessageDialog(this, "Departamento atualizado com sucesso", "",
							JOptionPane.INFORMATION_MESSAGE);
				} catch (DepartamentoNaoEncontradoException e1) {
					novo();

					JOptionPane.showMessageDialog(this, "Departamento não encontrado", "", JOptionPane.ERROR_MESSAGE);
				}
			}

		} else if (obj == removerButton) {
			try {
				deptService.excludes(umDept);

				removido();

				JOptionPane.showMessageDialog(this, "Departamento removido com sucesso", "",
						JOptionPane.INFORMATION_MESSAGE);
			} catch (DepartamentoNaoEncontradoException | DepartamentoComFuncionarioException e1) {
				novo();

				JOptionPane.showMessageDialog(this, "Departamento não encontrado ou Departamento com funcionários", "",
						JOptionPane.ERROR_MESSAGE);
			}
		} else if (obj == cancelarButton) {
			try {
				umDept = deptService.getOneDept(umDept.getId());
				nomeTextField.setText(umDept.getNameDept());
				numeroTextField.setText(Long.toString(umDept.getDeptno()));
				localizacaoComboBox.setSelectedItem(umDept.getLoc());

				cancelado();
			} catch (DepartamentoNaoEncontradoException e1) {
				novo();

				JOptionPane.showMessageDialog(this, "Departamento não encontrado", "", JOptionPane.ERROR_MESSAGE);
			}
		} else if (obj == buscarButton) {
			DialogTabelaDept dialog = new DialogTabelaDept(this);
			dialog.setVisible(true);
		}
	}

	private boolean validaDepartamento() {

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
		if (localizacaoComboBox.getSelectedIndex() == 0) {
			deuErro = true;
			numeroMensagem.setText("Campo de preenchimento obrigatório");
		}
		return deuErro;
	}

	public void salvo() {

		nomeTextField.setEnabled(false);
		numeroTextField.setEnabled(false);
		localizacaoComboBox.setEnabled(false);

		novoButton.setEnabled(true);
		cadastrarButton.setEnabled(false);
		editarButton.setEnabled(true);
		alterarButton.setEnabled(false);
		removerButton.setEnabled(true);
		cancelarButton.setEnabled(false);
		buscarButton.setEnabled(true);

	}

	public void editavel() {
		nomeTextField.setEnabled(true);
		numeroTextField.setEnabled(true);
		localizacaoComboBox.setEnabled(true);

		novoButton.setEnabled(false);
		cadastrarButton.setEnabled(false);
		editarButton.setEnabled(false);
		alterarButton.setEnabled(true);
		removerButton.setEnabled(false);
		cancelarButton.setEnabled(true);
		buscarButton.setEnabled(false);
	}

	public void removido() {
		nomeTextField.setEnabled(false);
		numeroTextField.setEnabled(false);
		localizacaoComboBox.setEnabled(false);

		novoButton.setEnabled(true);
		cadastrarButton.setEnabled(false);
		editarButton.setEnabled(false);
		alterarButton.setEnabled(false);
		removerButton.setEnabled(false);
		cancelarButton.setEnabled(false);
		buscarButton.setEnabled(true);
	}

	public void cancelado() {
		nomeTextField.setEnabled(false);
		numeroTextField.setEnabled(false);
		localizacaoComboBox.setEnabled(false);

		novoButton.setEnabled(true);
		cadastrarButton.setEnabled(false);
		editarButton.setEnabled(true);
		alterarButton.setEnabled(false);
		removerButton.setEnabled(true);
		cancelarButton.setEnabled(false);
		buscarButton.setEnabled(true);
	}
}
