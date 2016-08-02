package app.visao;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import app.service.DeptAppService;

public class DialogEmp extends JDialog implements ActionListener{
	
	private static final long serialVersionUID = 1L;

	private static DeptAppService deptService;
	
    static
    {
    	@SuppressWarnings("resource")
		ApplicationContext fabrica = new ClassPathXmlApplicationContext("beans-jpa.xml");

    	deptService = (DeptAppService)fabrica.getBean ("deptAppService");
    }

    private JPanel panel;
    private JTextField nomeTextField;
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
