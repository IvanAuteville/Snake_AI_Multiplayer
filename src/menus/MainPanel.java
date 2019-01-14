package menus;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
//import javax.swing.Icon;
//import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import sound_engine.SoundManager;

public class MainPanel extends JPanel
{
    private static final long serialVersionUID = 1L;
    
    public MainPanel(MainWindow mainWindow)
    {
	setPreferredSize(new java.awt.Dimension(800 + 106, 600 + 29));
	setBackground(Color.BLACK);
	setBorder(null);
	
	
	
		JButton onePlayerButton = new JButton("Jugar Offline");
		onePlayerButton.setFocusTraversalKeysEnabled(false);
		onePlayerButton.setFocusPainted(false);
		onePlayerButton.addActionListener(new ActionListener()
		{
		    public void actionPerformed(ActionEvent arg0)
		    {
			SoundManager.play("click");
			mainWindow.openGameConfig();
		    }
		});
		onePlayerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		onePlayerButton.setBackground(new Color(0, 255, 102));
		onePlayerButton.setBorderPainted(false);
		onePlayerButton.setFont(new Font("Tahoma", Font.BOLD, 26));
	
		JButton multiplayerButton = new JButton("Jugar Online");
		multiplayerButton.setFocusTraversalKeysEnabled(false);
		multiplayerButton.setFocusPainted(false);
		multiplayerButton.addActionListener(new ActionListener()
		{
		    public void actionPerformed(ActionEvent arg0)
		    {
			SoundManager.play("click");
			mainWindow.openOnline();
		    }
		});
		multiplayerButton.setBackground(new Color(0, 255, 102));
		multiplayerButton.setBorderPainted(false);
		multiplayerButton.setFont(new Font("Tahoma", Font.BOLD, 26));
	
		JButton optionsButton = new JButton("Opciones");
		optionsButton.setFocusTraversalKeysEnabled(false);
		optionsButton.setFocusPainted(false);
		optionsButton.addActionListener(new ActionListener()
		{
		    public void actionPerformed(ActionEvent e)
		    {
			SoundManager.play("click");
			mainWindow.openOptions();
		    }
		});
		optionsButton.setBackground(new Color(0, 255, 102));
		optionsButton.setBorderPainted(false);
		optionsButton.setFont(new Font("Tahoma", Font.BOLD, 26));
	
		JButton exitButton = new JButton("Salir");
		exitButton.setFocusTraversalKeysEnabled(false);
		exitButton.setFocusPainted(false);
		exitButton.addActionListener(new ActionListener()
		{
		    public void actionPerformed(ActionEvent arg0)
		    {
			SoundManager.play("click");
			mainWindow.exit();
		    }
		});
		exitButton.setBackground(new Color(0, 255, 102));
		exitButton.setBorderPainted(false);
		exitButton.setFont(new Font("Tahoma", Font.BOLD, 26));
		
		JLabel snakeLbl = new JLabel("Snake");
		snakeLbl.setHorizontalAlignment(SwingConstants.CENTER);
		snakeLbl.setForeground(new Color(153, 0, 51));
		snakeLbl.setFont(new Font("Tahoma", Font.PLAIN, 85));
		snakeLbl.setBackground(Color.WHITE);
		
		JButton howToButton = new JButton("Como Jugar");
		howToButton.setFocusTraversalKeysEnabled(false);
		howToButton.setFocusPainted(false);
		howToButton.setFont(new Font("Tahoma", Font.BOLD, 26));
		howToButton.setBorderPainted(false);
		howToButton.setBackground(new Color(0, 255, 102));
		howToButton.addActionListener(new ActionListener()
		{
		    public void actionPerformed(ActionEvent arg0)
		    {
			SoundManager.play("click");
			mainWindow.openHowTo();
		    }
		});
		
		JLabel drakonLbl = new JLabel("<html>Grupo DRAKON® 2018: Iván García, Hernán Maurice, Lautaro Costa, Andrés Schiro<br><center>Música creada por Leandro Perticarini</center></html>");
		drakonLbl.setHorizontalAlignment(SwingConstants.CENTER);
		drakonLbl.setForeground(new Color(153, 0, 51));
		drakonLbl.setFont(new Font("Tahoma", Font.PLAIN, 20));
		drakonLbl.setBackground(Color.WHITE);
		
//		Icon icon = new ImageIcon(getClass().getClassLoader().getResource("snake.gif"));
		JLabel snakeGifLbl = new JLabel();
//		snakeGifLbl.setHorizontalAlignment(SwingConstants.CENTER);
//		snakeGifLbl.setForeground(Color.WHITE);
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(24)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(215)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(multiplayerButton, GroupLayout.PREFERRED_SIZE, 428, GroupLayout.PREFERRED_SIZE)
										.addComponent(optionsButton, GroupLayout.PREFERRED_SIZE, 428, GroupLayout.PREFERRED_SIZE)
										.addComponent(onePlayerButton, GroupLayout.PREFERRED_SIZE, 428, GroupLayout.PREFERRED_SIZE)
										.addComponent(howToButton, GroupLayout.PREFERRED_SIZE, 428, GroupLayout.PREFERRED_SIZE)
										.addComponent(exitButton, GroupLayout.PREFERRED_SIZE, 428, GroupLayout.PREFERRED_SIZE)))
								.addComponent(drakonLbl, GroupLayout.PREFERRED_SIZE, 856, GroupLayout.PREFERRED_SIZE)))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(snakeGifLbl, GroupLayout.PREFERRED_SIZE, 237, GroupLayout.PREFERRED_SIZE)
							.addGap(49)
							.addComponent(snakeLbl, GroupLayout.PREFERRED_SIZE, 334, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(26, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(63)
							.addComponent(snakeLbl, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
							.addGap(61)
							.addComponent(onePlayerButton)
							.addGap(18)
							.addComponent(multiplayerButton)
							.addGap(18)
							.addComponent(optionsButton)
							.addGap(18)
							.addComponent(howToButton, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(exitButton))
						.addComponent(snakeGifLbl, GroupLayout.PREFERRED_SIZE, 195, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(drakonLbl, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE)
					.addGap(53))
		);
		setLayout(groupLayout);
    }
}
