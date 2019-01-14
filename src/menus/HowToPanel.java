package menus;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JPanel;
import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import sound_engine.SoundManager;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.LayoutStyle.ComponentPlacement;

public class HowToPanel extends JPanel
{
    private static final long serialVersionUID = 1L;
    private Image [] instructions;
    private int sizeId = 0;
    private int arrayIndex = 0;

    public HowToPanel(MainWindow mainWindow)
    {
    	setForeground(Color.BLACK);
	Color color = new Color(51,204,51);
	setPreferredSize(new java.awt.Dimension(800 + 106, 600 + 29));
	setBackground(color);
	setBorder(null);

	JButton backToMenuBtn = new JButton("Menu");
	backToMenuBtn.setFont(new Font("Tahoma", Font.PLAIN, 20));
	backToMenuBtn.setFocusTraversalKeysEnabled(false);
	backToMenuBtn.setFocusPainted(false);
	backToMenuBtn.setBackground(Color.GREEN);
	backToMenuBtn.addActionListener(new ActionListener()
	{
	    public void actionPerformed(ActionEvent arg0)
	    {
		SoundManager.play("click");
		arrayIndex = 0;
		mainWindow.returnToMainMenu();
	    }
	});

	try
	{
	    instructions = new Image[3];
	    
	    instructions[0] = ImageIO.read(getClass().getClassLoader().getResource("instructions_1.png"));
	    instructions[1] = ImageIO.read(getClass().getClassLoader().getResource("instructions_2.png"));
	    instructions[2] = ImageIO.read(getClass().getClassLoader().getResource("instructions_3.png"));
	} catch (IOException e)
	{
	    e.printStackTrace();
	}
	
	JButton controlsBtn = new JButton("Controles");
	controlsBtn.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) 
		{
		    SoundManager.play("click");
		    arrayIndex = 2;
		    repaint();
		}
	});
	controlsBtn.setFont(new Font("Tahoma", Font.PLAIN, 20));
	controlsBtn.setFocusTraversalKeysEnabled(false);
	controlsBtn.setFocusPainted(false);
	controlsBtn.setBackground(Color.GREEN);
	
	JButton mapsBtn = new JButton("Mapas/Modos");
	mapsBtn.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) 
		{
		    SoundManager.play("click");
		    arrayIndex = 1;
		    repaint();
		}
	});
	mapsBtn.setFont(new Font("Tahoma", Font.PLAIN, 17));
	mapsBtn.setFocusTraversalKeysEnabled(false);
	mapsBtn.setFocusPainted(false);
	mapsBtn.setBackground(Color.GREEN);
	
	JButton startBtn = new JButton("Inicio");
	startBtn.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) 
		{
		    SoundManager.play("click");
		    if(arrayIndex != 0)
		    {
			arrayIndex = 0;
			repaint();			
		    }
		}
	});
	startBtn.setFont(new Font("Tahoma", Font.PLAIN, 17));
	startBtn.setFocusTraversalKeysEnabled(false);
	startBtn.setFocusPainted(false);
	startBtn.setBackground(Color.GREEN);

	GroupLayout groupLayout = new GroupLayout(this);
	groupLayout.setHorizontalGroup(
		groupLayout.createParallelGroup(Alignment.LEADING)
			.addGroup(groupLayout.createSequentialGroup()
				.addGap(156)
				.addComponent(backToMenuBtn, GroupLayout.PREFERRED_SIZE, 144, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(startBtn, GroupLayout.PREFERRED_SIZE, 144, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(mapsBtn, GroupLayout.PREFERRED_SIZE, 144, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(controlsBtn, GroupLayout.PREFERRED_SIZE, 144, GroupLayout.PREFERRED_SIZE)
				.addContainerGap(156, Short.MAX_VALUE))
	);
	groupLayout.setVerticalGroup(
		groupLayout.createParallelGroup(Alignment.TRAILING)
			.addGroup(groupLayout.createSequentialGroup()
				.addContainerGap(580, Short.MAX_VALUE)
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
					.addComponent(startBtn, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(mapsBtn, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
						.addComponent(controlsBtn, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE))
					.addComponent(backToMenuBtn, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE))
				.addContainerGap())
	);
	setLayout(groupLayout);

    }

    protected void setId(int id)
    {
	this.sizeId = id;
    }

    @Override
    protected void paintComponent(Graphics g)
    {
	super.paintComponent(g);
	int height;

	switch (sizeId)
	{
	case 0:
	    height = 529;
	    break;
	case 1:
	    height = 700;
	    break;
	case 2:
	    height = 900;
	    break;
	default:
	    height = 529;
	    break;
	}

	g.drawImage(instructions[arrayIndex], 0, 0, this.getWidth(), height, null);
    }
}
