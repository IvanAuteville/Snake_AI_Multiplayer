package menus;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import sound_engine.SoundManager;

public class OptionsPanel extends JPanel
{
    private static final long serialVersionUID = 1L;

    public OptionsPanel(MainWindow mainWindow)
    {
	setBackground(Color.BLACK);

	setPreferredSize(new java.awt.Dimension(800 + 106, 600 + 29));
	setBorder(null);

	JLabel musicLb = new JLabel("M\u00FAsica:");
	musicLb.setForeground(Color.GREEN);
	musicLb.setAlignmentX(Component.CENTER_ALIGNMENT);
	musicLb.setHorizontalAlignment(SwingConstants.CENTER);
	musicLb.setFont(new Font("Tahoma", Font.PLAIN, 20));

	JLabel resolutionLb = new JLabel("Resoluci\u00F3n:");
	resolutionLb.setForeground(Color.GREEN);
	resolutionLb.setAlignmentX(Component.CENTER_ALIGNMENT);
	resolutionLb.setHorizontalAlignment(SwingConstants.CENTER);
	resolutionLb.setFont(new Font("Tahoma", Font.PLAIN, 20));

	JComboBox<String> resolutionComboBox = new JComboBox<String>();
	resolutionComboBox.setBackground(Color.GREEN);
	resolutionComboBox.setFont(new Font("Tahoma", Font.PLAIN, 15));
	resolutionComboBox.setModel(new DefaultComboBoxModel<String>(new String[]
	{ "600x600", "800x800", "1000X1000" }));
	resolutionComboBox.addActionListener(new java.awt.event.ActionListener()
	{
	    public void actionPerformed(java.awt.event.ActionEvent evt)
	    {
		switch (resolutionComboBox.getSelectedIndex())
		{
		case 0:
		    SoundManager.play("click");
		    mainWindow.resize(0);
		    break;
		case 1:
		    SoundManager.play("click");
		    mainWindow.resize(1);
		    break;
		case 2:
		    SoundManager.play("click");
		    mainWindow.resize(2);
		    break;
		default:
		    break;
		}
	    }
	});

	JButton returnBtn = new JButton("Volver");
	returnBtn.setBackground(Color.GREEN);
	returnBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
	returnBtn.addActionListener(new ActionListener()
	{
	    public void actionPerformed(ActionEvent e)
	    {
		SoundManager.play("click");
		mainWindow.returnToMainMenu();
	    }
	});
	returnBtn.setFont(new Font("Tahoma", Font.PLAIN, 18));

	JSlider volumeSlider = new JSlider();
	volumeSlider.setForeground(Color.GREEN);
	volumeSlider.setBackground(Color.BLACK);
	volumeSlider.setValue(100);
	volumeSlider.addChangeListener(new ChangeListener()
	{
	    public void stateChanged(ChangeEvent arg0)
	    {
		SoundManager.pause("menu", true);
		SoundManager.setVolume("menu", (float) volumeSlider.getValue() / 100);
		SoundManager.pause("menu", false);
	    }
	});

	JLabel volumeLb = new JLabel("Volumen:");
	volumeLb.setForeground(Color.GREEN);
	volumeLb.setAlignmentX(Component.CENTER_ALIGNMENT);
	volumeLb.setHorizontalAlignment(SwingConstants.CENTER);
	volumeLb.setFont(new Font("Tahoma", Font.PLAIN, 20));

	JRadioButton onRdbtn = new JRadioButton("On");
	onRdbtn.setForeground(Color.GREEN);
	onRdbtn.setBackground(Color.BLACK);
	onRdbtn.setAlignmentX(Component.CENTER_ALIGNMENT);
	onRdbtn.setSelected(true);
	onRdbtn.addActionListener(new ActionListener()
	{
	    public void actionPerformed(ActionEvent arg0)
	    {
		SoundManager.play("click");
		SoundManager.pause("menu", false);
	    }
	});
	onRdbtn.setHorizontalAlignment(SwingConstants.CENTER);
	onRdbtn.setFont(new Font("Tahoma", Font.PLAIN, 15));

	JRadioButton offRdbtn = new JRadioButton("Off");
	offRdbtn.setForeground(Color.GREEN);
	offRdbtn.setBackground(Color.BLACK);
	offRdbtn.setAlignmentX(Component.CENTER_ALIGNMENT);
	offRdbtn.addActionListener(new ActionListener()
	{
	    public void actionPerformed(ActionEvent e)
	    {
		SoundManager.play("click");
		SoundManager.pause("menu", true);
	    }
	});
	offRdbtn.setHorizontalAlignment(SwingConstants.CENTER);
	offRdbtn.setFont(new Font("Tahoma", Font.PLAIN, 15));

	ButtonGroup musicOnOff = new ButtonGroup();
	musicOnOff.add(offRdbtn);
	musicOnOff.add(onRdbtn);

	JLabel lblOpciones = new JLabel("Opciones");
	lblOpciones.setHorizontalAlignment(SwingConstants.CENTER);
	lblOpciones.setForeground(new Color(153, 0, 51));
	lblOpciones.setFont(new Font("Tahoma", Font.PLAIN, 50));
	lblOpciones.setBackground(Color.WHITE);
	GroupLayout groupLayout = new GroupLayout(this);
	groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
		.createSequentialGroup()
		.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
			.createSequentialGroup().addGap(260)
			.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
				.addComponent(musicLb, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
				.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
					.addComponent(resolutionLb, GroupLayout.PREFERRED_SIZE, 103,
						GroupLayout.PREFERRED_SIZE)
					.addComponent(volumeLb, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 103,
						GroupLayout.PREFERRED_SIZE)))
			.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(
				groupLayout.createSequentialGroup().addGap(32).addComponent(resolutionComboBox,
					GroupLayout.PREFERRED_SIZE, 151, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup().addGap(18).addComponent(volumeSlider,
					GroupLayout.PREFERRED_SIZE, 239, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup().addGap(23).addComponent(returnBtn,
					GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(onRdbtn, GroupLayout.PREFERRED_SIZE, 95,
						GroupLayout.PREFERRED_SIZE)
					.addGap(30).addComponent(offRdbtn, GroupLayout.PREFERRED_SIZE, 109,
						GroupLayout.PREFERRED_SIZE))))
			.addGroup(groupLayout.createSequentialGroup().addGap(145).addComponent(lblOpciones,
				GroupLayout.PREFERRED_SIZE, 617, GroupLayout.PREFERRED_SIZE)))
		.addContainerGap(144, Short.MAX_VALUE)));
	groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
		.createSequentialGroup().addGap(60)
		.addComponent(lblOpciones, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE).addGap(43)
		.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addComponent(musicLb)
			.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
				.addGroup(groupLayout.createSequentialGroup().addGap(2).addComponent(onRdbtn,
					GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
				.addComponent(offRdbtn, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)))
		.addPreferredGap(ComponentPlacement.UNRELATED)
		.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
			.addComponent(volumeLb, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
			.addComponent(volumeSlider, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE))
		.addPreferredGap(ComponentPlacement.UNRELATED)
		.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
			.addGroup(groupLayout.createSequentialGroup().addGap(92).addComponent(returnBtn,
				GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE))
			.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
				.addComponent(resolutionLb, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
				.addComponent(resolutionComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
					GroupLayout.PREFERRED_SIZE)))
		.addContainerGap(216, Short.MAX_VALUE)));
	setLayout(groupLayout);
    }
}