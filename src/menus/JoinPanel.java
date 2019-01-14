package menus;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;

import online.NetworkHandler;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;

import sound_engine.SoundManager;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.awt.event.ActionEvent;

public class JoinPanel extends JPanel
{
    private static final long serialVersionUID = 1L;
    private JTextField ipField;
    private static final int port = 9995;
    protected MainWindow mainWindow = null;
    private String userName = "";

    public JoinPanel(MainWindow mainWindow)
    {
	this.mainWindow = mainWindow;

	setPreferredSize(new java.awt.Dimension(800 + 106, 600 + 29));
	setBackground(Color.BLACK);
	setBorder(null);

	JLabel joinLbl = new JLabel("Conectarse a un servidor");
	joinLbl.setHorizontalAlignment(SwingConstants.CENTER);
	joinLbl.setForeground(new Color(153, 0, 51));
	joinLbl.setFont(new Font("Tahoma", Font.PLAIN, 50));
	joinLbl.setBackground(Color.WHITE);

	JLabel ipLbl = new JLabel("Ingresa la direccion IP del servidor (Puerto 9995):");
	ipLbl.setHorizontalAlignment(SwingConstants.CENTER);
	ipLbl.setForeground(Color.GREEN);
	ipLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));

	ipField = new JTextField();
	ipField.setHorizontalAlignment(SwingConstants.CENTER);
	ipField.setForeground(Color.GREEN);
	ipField.setFont(new Font("Tahoma", Font.PLAIN, 18));
	ipField.setColumns(10);
	ipField.setBackground(Color.GRAY);

	JButton backToMenuBtn = new JButton("Menu");
	backToMenuBtn.addActionListener(new ActionListener()
	{
	    public void actionPerformed(ActionEvent e)
	    {
		SoundManager.play("click");
		mainWindow.returnToMainMenu();
	    }
	});
	backToMenuBtn.setFont(new Font("Tahoma", Font.BOLD, 26));
	backToMenuBtn.setFocusTraversalKeysEnabled(false);
	backToMenuBtn.setFocusPainted(false);
	backToMenuBtn.setBorderPainted(false);
	backToMenuBtn.setBackground(new Color(0, 255, 102));
	backToMenuBtn.setAlignmentX(0.5f);

	JButton okBtn = new JButton("OK");
	okBtn.addActionListener(new ActionListener()
	{
	    public void actionPerformed(ActionEvent e)
	    {
		SoundManager.play("click");
		InetAddress ip;

		try
		{
		    ip = InetAddress.getByName(ipField.getText());
		    connect(ip);
		} catch (UnknownHostException e1)
		{
		    ErrorDialog errorDialog = new ErrorDialog(mainWindow, 1);
		    errorDialog.setVisible(true);
		}

	    }
	});
	okBtn.setFont(new Font("Tahoma", Font.PLAIN, 20));
	okBtn.setFocusTraversalKeysEnabled(false);
	okBtn.setFocusPainted(false);
	okBtn.setBackground(Color.GREEN);

	JButton cancelBtn = new JButton("Cancelar");
	cancelBtn.addActionListener(new ActionListener()
	{
	    public void actionPerformed(ActionEvent e)
	    {
		SoundManager.play("click");
		mainWindow.openOnline();
	    }
	});
	cancelBtn.setFont(new Font("Tahoma", Font.PLAIN, 20));
	cancelBtn.setFocusTraversalKeysEnabled(false);
	cancelBtn.setFocusPainted(false);
	cancelBtn.setBackground(Color.GREEN);
	GroupLayout groupLayout = new GroupLayout(this);
	groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
		.createSequentialGroup()
		.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
			.addGroup(groupLayout.createSequentialGroup().addGap(239).addComponent(backToMenuBtn,
				GroupLayout.PREFERRED_SIZE, 428, GroupLayout.PREFERRED_SIZE))
			.addGroup(groupLayout.createSequentialGroup().addGap(300)
				.addComponent(okBtn, GroupLayout.PREFERRED_SIZE, 144, GroupLayout.PREFERRED_SIZE)
				.addGap(18)
				.addComponent(cancelBtn, GroupLayout.PREFERRED_SIZE, 144, GroupLayout.PREFERRED_SIZE))
			.addGroup(groupLayout.createSequentialGroup().addGap(177).addGroup(groupLayout
				.createParallelGroup(Alignment.LEADING)
				.addComponent(ipLbl, GroupLayout.PREFERRED_SIZE, 553, GroupLayout.PREFERRED_SIZE)
				.addGroup(groupLayout.createSequentialGroup().addGap(62).addComponent(ipField,
					GroupLayout.PREFERRED_SIZE, 428, GroupLayout.PREFERRED_SIZE))))
			.addGroup(groupLayout.createSequentialGroup().addGap(145).addComponent(joinLbl,
				GroupLayout.PREFERRED_SIZE, 617, GroupLayout.PREFERRED_SIZE)))
		.addContainerGap(144, Short.MAX_VALUE)));
	groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
		.addGroup(groupLayout.createSequentialGroup().addGap(91)
			.addComponent(joinLbl, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE).addGap(62)
			.addComponent(ipLbl, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE).addGap(18)
			.addComponent(ipField, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
			.addPreferredGap(ComponentPlacement.RELATED, 72, Short.MAX_VALUE)
			.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(okBtn, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
				.addComponent(cancelBtn, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE))
			.addGap(93)
			.addComponent(backToMenuBtn, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
			.addGap(47)));
	setLayout(groupLayout);

    }

    private void connect(InetAddress ip)
    {
	NetworkHandler access = new NetworkHandler(mainWindow);

	try
	{

	    if (access.InitSocket(ip, port) == true)
	    {
		// Me conecte correctamente al server...
		mainWindow.joinGame(access, this.userName);
	    }

	} catch (IOException ex)
	{
	    ErrorDialog errorDialog = new ErrorDialog(mainWindow, 2);
	    errorDialog.setVisible(true);
	}
    }

    public void setUserName(String userName)
    {
	this.userName = userName;
    }
}