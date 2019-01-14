package menus;

import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.LayoutStyle.ComponentPlacement;
import sound_engine.SoundManager;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class OnlinePanel extends JPanel
{
    private static final long serialVersionUID = 1L;
    private JTextField nickTxt;

    public OnlinePanel(MainWindow mainWindow)
    {
	setPreferredSize(new java.awt.Dimension(800 + 106, 600 + 29));
	setBackground(Color.BLACK);
	setBorder(null);

	JButton hostBtn = new JButton("Crear");
	hostBtn.addActionListener(new ActionListener()
	{
	    public void actionPerformed(ActionEvent e)
	    {
		SoundManager.play("click");
		
		if (!nickTxt.getText().trim().isEmpty())
		{
		    try
		    {
			mainWindow.openHostPanel(nickTxt.getText());
		    } catch (Exception e1)
		    {
			e1.printStackTrace();
		    }
		}
	    }
	});
	hostBtn.setFont(new Font("Tahoma", Font.BOLD, 26));
	hostBtn.setFocusTraversalKeysEnabled(false);
	hostBtn.setFocusPainted(false);
	hostBtn.setBorderPainted(false);
	hostBtn.setBackground(new Color(0, 255, 102));
	hostBtn.setAlignmentX(0.5f);

	JButton joinBtn = new JButton("Unirse");
	joinBtn.addActionListener(new ActionListener()
	{
	    public void actionPerformed(ActionEvent e)
	    {
		SoundManager.play("click");
		
		if (!nickTxt.getText().trim().isEmpty())
		{
		    mainWindow.openJoinPanel(nickTxt.getText());
		}
	    }
	});
	joinBtn.setFont(new Font("Tahoma", Font.BOLD, 26));
	joinBtn.setFocusTraversalKeysEnabled(false);
	joinBtn.setFocusPainted(false);
	joinBtn.setBorderPainted(false);
	joinBtn.setBackground(new Color(0, 255, 102));
	joinBtn.setAlignmentX(0.5f);

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

	nickTxt = new JTextField();
	nickTxt.setForeground(Color.GREEN);
	nickTxt.setHorizontalAlignment(SwingConstants.CENTER);
	nickTxt.setFont(new Font("Tahoma", Font.PLAIN, 18));
	nickTxt.setBackground(Color.GRAY);
	nickTxt.setColumns(10);

	JLabel nickLbl = new JLabel("Ingresa tu Nickname (los dem\u00E1s jugadores te ver\u00E1n con este nombre)");
	nickLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
	nickLbl.setHorizontalAlignment(SwingConstants.CENTER);
	nickLbl.setForeground(Color.GREEN);
	
	JLabel lblOnline = new JLabel("Online");
	lblOnline.setHorizontalAlignment(SwingConstants.CENTER);
	lblOnline.setForeground(new Color(153, 0, 51));
	lblOnline.setFont(new Font("Tahoma", Font.PLAIN, 50));
	lblOnline.setBackground(Color.WHITE);

	GroupLayout groupLayout = new GroupLayout(this);
	groupLayout.setHorizontalGroup(
		groupLayout.createParallelGroup(Alignment.LEADING)
			.addGroup(groupLayout.createSequentialGroup()
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
					.addGroup(groupLayout.createSequentialGroup()
						.addGap(239)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(joinBtn, GroupLayout.DEFAULT_SIZE, 428, Short.MAX_VALUE)
								.addComponent(hostBtn, GroupLayout.DEFAULT_SIZE, 428, Short.MAX_VALUE))
							.addComponent(backToMenuBtn, GroupLayout.PREFERRED_SIZE, 428, GroupLayout.PREFERRED_SIZE)))
					.addGroup(groupLayout.createSequentialGroup()
						.addGap(177)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
							.addComponent(nickLbl)
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(61)
								.addComponent(nickTxt, GroupLayout.PREFERRED_SIZE, 428, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED, 64, Short.MAX_VALUE))))
					.addGroup(groupLayout.createSequentialGroup()
						.addGap(145)
						.addComponent(lblOnline, GroupLayout.PREFERRED_SIZE, 617, GroupLayout.PREFERRED_SIZE)))
				.addContainerGap(144, Short.MAX_VALUE))
	);
	groupLayout.setVerticalGroup(
		groupLayout.createParallelGroup(Alignment.TRAILING)
			.addGroup(groupLayout.createSequentialGroup()
				.addGap(52)
				.addComponent(lblOnline, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
				.addGap(18)
				.addComponent(nickLbl, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
				.addGap(18)
				.addComponent(nickTxt, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
				.addGap(64)
				.addComponent(hostBtn, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
				.addGap(27)
				.addComponent(joinBtn, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.RELATED, 91, Short.MAX_VALUE)
				.addComponent(backToMenuBtn, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
				.addGap(70))
	);
	setLayout(groupLayout);
    }
}
