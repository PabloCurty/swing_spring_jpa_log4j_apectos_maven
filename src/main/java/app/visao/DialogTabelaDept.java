package app.visao;

import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.TableColumnModel;

public class DialogTabelaDept extends JDialog implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	
	private JTextField nomeTextField;
	private JTextField numeroTextField;
	private JTable table;
	private JScrollPane scrollPane;
	private JComboBox<String> localizacaoComboBox;
	private TableColumnModel columnModel;
	private DeptModel deptModel;
	private boolean ehEmp;
	
	DialogDept dialogDept;
	DialogEmp dialogEmp;
	
	public DialogTabelaDept(DialogEmp dialogDepartamento){
		super(dialogDepartamento);
		
		this.dialogEmp = dialogDepartamento;
		
		setTitulo();
		
		ehEmp = true;
	}
	
	public DialogTabelaDept(DialogDept dialogDepartamento){
		super(dialogDepartamento);
		
		this.dialogDept = dialogDepartamento;
		
		setTitulo();
		
		ehEmp = false;
	}	
	
	private void setTitulo() {
		setTitle("Pesquisa Departamentos pelo Nome");
		setBounds(110, 171, 608, 301);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 588, 111);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblPesquisaPorNome = new JLabel("Pesquisa Departamentos pelo Nome");
		lblPesquisaPorNome.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblPesquisaPorNome.setHorizontalAlignment(SwingConstants.CENTER);
		lblPesquisaPorNome.setBounds(203, 11, 195, 22);
		panel.add(lblPesquisaPorNome);
		
		
		JLabel lblNome = new JLabel("Nome:");
		lblNome.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNome.setBounds(92, 42, 55, 22);
		panel.add(lblNome);
		
		nomeTextField = new JTextField();
		nomeTextField.setBackground(UIManager.getColor("Button.light"));
		nomeTextField.setForeground(SystemColor.desktop);
		nomeTextField.setBounds(142, 44, 324, 20);
		panel.add(nomeTextField);
		nomeTextField.setColumns(10);
		
		JButton btnPesquisar = new JButton("Pesquisar");
		btnPesquisar.addActionListener(this);
		btnPesquisar.setBounds(249, 75, 102, 23);
		panel.add(btnPesquisar);
		
		scrollPane = new JScrollPane();
		scrollPane.setVisible(false);
		scrollPane.setBounds(0, 112, 588, 144);
		getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JLabel lblNumero = new JLabel("N�mero:");
		lblNumero.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNumero.setBounds(92, 156, 55, 22);
		panel.add(lblNumero);
		
		numeroTextField = new JTextField();
		numeroTextField.setBackground(UIManager.getColor("Button.light"));
		numeroTextField.setForeground(SystemColor.desktop);
		numeroTextField.setBounds(142, 152, 324, 20);
		panel.add(numeroTextField);
		numeroTextField.setColumns(10);
		
		
		localizacaoComboBox = new JComboBox<String>();
		localizacaoComboBox.addItem("Praia vermelha");
		localizacaoComboBox.addItem("Valonguinho");
		localizacaoComboBox.addItem("Gragoata");
		localizacaoComboBox.addItem("Rio das Ostras");

		columnModel = table.getColumnModel();
		
	}

	

	@Override
	public void actionPerformed(ActionEvent e) {
		deptModel = new DeptModel();
		deptModel.setNomeDept(nomeTextField.getText());
		table.setModel(deptModel);
		
		if(ehEmp){
			new ButtonColumnDeEmpParaDept(table, 4, this, dialogEmp);
		}else{
			new ButtonColumn(table, 4, this, dialogDept);
		}
		columnModel.getColumn(0).setPreferredWidth(20);
		columnModel.getColumn(1).setPreferredWidth(20);
		columnModel.getColumn(2).setPreferredWidth(40);
		columnModel.getColumn(3).setPreferredWidth(40);
		columnModel.getColumn(4).setPreferredWidth(50);
		
		scrollPane.setVisible(true);
	}

}
