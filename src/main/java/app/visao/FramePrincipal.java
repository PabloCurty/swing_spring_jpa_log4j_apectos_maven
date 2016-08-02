package app.visao;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;


public class FramePrincipal extends JFrame
{
	private static final long serialVersionUID = 1L;

	private JMenuItem menuItemDept;
	private JMenuItem menuItemEmp;
	private JMenuItem menuItemSair;
	private JPanel panel;
	private JFrame frame;
	
	public FramePrincipal() 
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 628, 382);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnCadastrar = new JMenu("Cadastro");
		menuBar.add(mnCadastrar);
	
		frame = this;
		
		menuItemDept = new JMenuItem("Departamento");
		menuItemDept.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.ALT_MASK)); // Diz a combinação necessaria para chamar os action listeners
		menuItemDept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DialogDept dialogDept = new DialogDept(frame);
				dialogDept.novo();
				dialogDept.setVisible(true);
			}
		});
		mnCadastrar.add(menuItemDept);
		
		menuItemEmp = new JMenuItem("Empregado");
		menuItemEmp.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.ALT_MASK)); // Diz a combinação necessaria para chamar os action listeners
		menuItemEmp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//DialogEmp dialogDept = new DialogEmp(frame);
				//dialogEmp.novo();
				//dialogEMo.setVisible(true);
			}
		});
		mnCadastrar.add(menuItemEmp);
		
		menuItemSair = new JMenuItem("Sair");
		menuItemSair.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK)); // Diz a combinação necessaria para chamar os action listeners
		menuItemSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		mnCadastrar.add(menuItemSair);
		
		getContentPane().setLayout(null);
		
		panel = new JPanel();
		panel.setBounds(0, 0, 614, 325);
		getContentPane().add(panel);
	}
}
