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

public class DialogTabelaEmp extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JTextField nomeTextField;
	private JTextField numeroTextField;
	private JComboBox<String> jobComboBox;
	private JTable table;
	private JScrollPane scrollPane;
	private TableColumnModel columnModel;
	private EmpModel empModel;

	DialogEmp dialogEmp;

	public DialogTabelaEmp(DialogEmp dialogEmp) {
		super(dialogEmp);
		this.dialogEmp = dialogEmp;

		setTitle("Pesquisa de Empregados");
		setBounds(110, 171, 608, 301);
		getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 588, 111);
		getContentPane().add(panel);
		panel.setLayout(null);

		JLabel lblPesquisaPorNome = new JLabel("Pesquisa Empregado pelo nome");
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

		// TODO por que não aparece?
		JLabel lblNumero = new JLabel("Número:");
		lblNumero.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNumero.setBounds(92, 156, 55, 22);
		panel.add(lblNumero);

		numeroTextField = new JTextField();
		numeroTextField.setBackground(UIManager.getColor("Button.light"));
		numeroTextField.setForeground(SystemColor.desktop);
		numeroTextField.setBounds(142, 152, 324, 20);
		panel.add(numeroTextField);
		numeroTextField.setColumns(10);

		jobComboBox = new JComboBox<String>();
		jobComboBox.addItem("DEVELOPER");
		jobComboBox.addItem("SYSTEMS_ANALYST");
		jobComboBox.addItem("MANAGER");
		jobComboBox.addItem("CONSULTANT");
		jobComboBox.addItem("TESTER");

		columnModel = table.getColumnModel();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		empModel = new EmpModel();
		empModel.setNomeEmp(nomeTextField.getText());
		table.setModel(empModel);

		new ButtonColumnEmp(table, 5, this, dialogEmp);

		columnModel.getColumn(0).setPreferredWidth(20);
		columnModel.getColumn(1).setPreferredWidth(20);
		columnModel.getColumn(2).setPreferredWidth(40);
		columnModel.getColumn(3).setPreferredWidth(40);
		columnModel.getColumn(4).setPreferredWidth(40);
		columnModel.getColumn(5).setPreferredWidth(40);

		scrollPane.setVisible(true);

	}

}
