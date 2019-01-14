package menus;

import javax.swing.JDialog;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import sound_engine.SoundManager;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import javax.swing.JPanel;

public class ErrorDialog extends JDialog
{
    private static final long serialVersionUID = 1L;

    public ErrorDialog(MainWindow mainWindow, int option)
    {
	setUndecorated(true);
	setForeground(Color.BLACK);
	this.setSize(new Dimension(500, 300));
	setAlwaysOnTop(true);
	setLocationRelativeTo(mainWindow);
	setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
	getContentPane().setBackground(Color.GRAY);

	JPanel panel = new JPanel();
	panel.setBackground(Color.BLACK);

	GroupLayout groupLayout = new GroupLayout(getContentPane());
	groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
		.addGroup(groupLayout.createSequentialGroup().addContainerGap()
			.addComponent(panel, GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE).addContainerGap()));
	groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
		.addGroup(groupLayout.createSequentialGroup().addContainerGap()
			.addComponent(panel, GroupLayout.PREFERRED_SIZE, 278, GroupLayout.PREFERRED_SIZE)
			.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

	JLabel errorLbl = new JLabel("TEXTO DE PRUEBA");
	errorLbl.setHorizontalAlignment(SwingConstants.CENTER);
	errorLbl.setForeground(Color.GREEN);
	errorLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
	errorLbl.setBackground(Color.BLACK);

	JButton okBtn = new JButton("OK");
	okBtn.setFocusTraversalKeysEnabled(false);
	okBtn.setFocusPainted(false);
	okBtn.setBackground(Color.GREEN);
	okBtn.setActionCommand("Cancel");
	okBtn.addActionListener(new ActionListener()
	{
	    public void actionPerformed(ActionEvent e)
	    {
		SoundManager.play("click");
		dispose();
	    }
	});

	switch (option)
	{
	case 1:
	    errorLbl.setText("Error de formato de IP");
	    break;
	case 2:
	    errorLbl.setText(
		    "<html><center>La IP ingresada es inválida</center><br><center>o no corresponde a un Servidor</center></html>");
	    break;
	case 3:
	    errorLbl.setText("Error al conectarse al Servidor");
	    break;
	case 4:
	    errorLbl.setText("Fuiste kickeado por el Host");
	    break;
	case 5:
	    errorLbl.setText("El Servidor fue cerrado");
	    break;
	case 6:
	    errorLbl.setText("Se ha perdido la conexión con el Servidor");
	    break;
	default:
	    break;
	}

	GroupLayout gl_panel = new GroupLayout(panel);
	gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.LEADING)
		.addGroup(gl_panel.createSequentialGroup()
			.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup().addContainerGap().addComponent(errorLbl,
					GroupLayout.DEFAULT_SIZE, 460, Short.MAX_VALUE))
				.addGroup(gl_panel.createSequentialGroup().addGap(182).addComponent(okBtn,
					GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE)))
			.addContainerGap()));
	gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.LEADING)
		.addGroup(gl_panel.createSequentialGroup().addContainerGap()
			.addComponent(errorLbl, GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE).addGap(52)
			.addComponent(okBtn, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE).addGap(20)));
	panel.setLayout(gl_panel);

	getContentPane().setLayout(groupLayout);
    }
}