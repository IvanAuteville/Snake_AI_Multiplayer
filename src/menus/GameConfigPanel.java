package menus;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import snake.GameMode;
import sound_engine.SoundManager;
import javax.swing.JCheckBox;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class GameConfigPanel extends JPanel
{
    private static final long serialVersionUID = 1L;
    private static final int LEVELS = 4;
    private BufferedImage[] mapsBuffered;
    private Image[] maps;
    private int mapID;

    private JTextField minutesTextField;
    private JTextField pointstextField;

    private JLabel playerOneLb;
    private JLabel playerTwoLb;
    private JLabel playerThreeLb;
    private JLabel playerFourLb;

    private JTextField playerOneNameField;
    private JTextField playerTwoNameField;
    private JTextField playerThreeNameField;
    private JTextField playerFourNameField;

    private JTextField[] pJTextFields;
    private JLabel[] pLabels;
    private JCheckBox[] botsArray;

    @SuppressWarnings(
    { "unchecked", "rawtypes" })
    public GameConfigPanel(MainWindow mainWindow)
    {
	setBackground(Color.BLACK);
	this.mapID = 0;
	mapsBuffered = new BufferedImage[LEVELS];
	maps = new Image[LEVELS];

	// setTitle("Configuracion del Juego");
	setPreferredSize(new Dimension(800, 600));
	setBorder(new EmptyBorder(15, 55, 0, 0));
	setFocusable(false);

	JLabel powerUpsLb = new JLabel("Power-Ups:");
	powerUpsLb.setEnabled(false);
	powerUpsLb.setForeground(Color.GREEN);
	powerUpsLb.setBackground(Color.BLACK);
	powerUpsLb.setFont(new Font("Tahoma", Font.PLAIN, 18));
	powerUpsLb.setHorizontalAlignment(SwingConstants.CENTER);
	powerUpsLb.setVisible(false);

	JRadioButton powerUpsOnBtn = new JRadioButton("Activados");
	powerUpsOnBtn.setEnabled(false);
	powerUpsOnBtn.setForeground(Color.GREEN);
	powerUpsOnBtn.setBackground(Color.BLACK);
	powerUpsOnBtn.setFont(new Font("Tahoma", Font.PLAIN, 18));
	powerUpsOnBtn.setVisible(false);

	JRadioButton powerUpsOffBtn = new JRadioButton("Desactivados");
	powerUpsOffBtn.setForeground(Color.GREEN);
	powerUpsOffBtn.setBackground(Color.BLACK);
	powerUpsOffBtn.setEnabled(false);
	powerUpsOffBtn.setFont(new Font("Tahoma", Font.PLAIN, 18));
	powerUpsOffBtn.setVisible(false);

	JComboBox<Integer> playersComboBox = new JComboBox<Integer>();
	playersComboBox.setForeground(Color.GREEN);
	playersComboBox.setBackground(Color.BLACK);
	playersComboBox.setFont(new Font("Tahoma", Font.PLAIN, 14));
	playersComboBox.setModel(new DefaultComboBoxModel(new Integer[]
	{ 1, 2, 3, 4 }));
	playersComboBox.setSelectedIndex(0);
	playersComboBox.addActionListener(new java.awt.event.ActionListener()
	{
	    public void actionPerformed(java.awt.event.ActionEvent evt)
	    {
		switch (playersComboBox.getSelectedIndex())
		{
		case 0:
		    SoundManager.play("click");
		    disableEnablePlayers(1);
		    break;
		case 1:
		    SoundManager.play("click");
		    disableEnablePlayers(2);
		    break;
		case 2:
		    SoundManager.play("click");
		    disableEnablePlayers(3);
		    break;
		case 3:
		    SoundManager.play("click");
		    disableEnablePlayers(4);
		    break;
		default:
		    break;
		}
	    }
	});

	ButtonGroup powerUpGroup = new ButtonGroup();
	powerUpGroup.add(powerUpsOnBtn);
	powerUpGroup.add(powerUpsOffBtn);

	JButton backToMenuBtn = new JButton("Menu");
	backToMenuBtn.setFocusTraversalKeysEnabled(false);
	backToMenuBtn.setFocusPainted(false);
	backToMenuBtn.setBackground(Color.GREEN);
	backToMenuBtn.addActionListener(new ActionListener()
	{
	    public void actionPerformed(ActionEvent arg0)
	    {
		SoundManager.play("click");
		mainWindow.returnToMainMenu();
	    }
	});
	backToMenuBtn.setFont(new Font("Tahoma", Font.PLAIN, 20));

	playerOneLb = new JLabel("Jugador 1:");
	playerOneLb.setForeground(Color.GREEN);
	playerOneLb.setBackground(Color.BLACK);
	playerOneLb.setHorizontalAlignment(SwingConstants.CENTER);
	playerOneLb.setFont(new Font("Tahoma", Font.PLAIN, 18));

	playerOneNameField = new JTextField();
	playerOneNameField.setForeground(Color.GREEN);
	playerOneNameField.setBackground(Color.BLACK);
	playerOneNameField.setFont(new Font("Tahoma", Font.PLAIN, 15));
	playerOneNameField.setColumns(10);

	JLabel playersLb = new JLabel("Jugadores:");
	playersLb.setForeground(Color.GREEN);
	playersLb.setBackground(Color.BLACK);
	playersLb.setFont(new Font("Tahoma", Font.PLAIN, 18));

	JLabel mapsLabel = new JLabel();

	try
	{
	    this.mapsBuffered[0] = ImageIO.read(getClass().getClassLoader().getResource("map1.png"));
	    this.maps[0] = mapsBuffered[0].getScaledInstance(100, 100, Image.SCALE_SMOOTH);

	    this.mapsBuffered[1] = ImageIO.read(getClass().getClassLoader().getResource("map2.png"));
	    this.maps[1] = mapsBuffered[1].getScaledInstance(100, 100, Image.SCALE_SMOOTH);

	    this.mapsBuffered[2] = ImageIO.read(getClass().getClassLoader().getResource("map3.png"));
	    this.maps[2] = mapsBuffered[2].getScaledInstance(100, 100, Image.SCALE_SMOOTH);

	    this.mapsBuffered[3] = ImageIO.read(getClass().getClassLoader().getResource("map4.png"));
	    this.maps[3] = mapsBuffered[3].getScaledInstance(100, 100, Image.SCALE_SMOOTH);

	} catch (IOException e)
	{
	    e.printStackTrace();
	}

	mapsLabel.setIcon(new ImageIcon(maps[0]));

	JLabel mapLb = new JLabel("Mapa:");
	mapLb.setForeground(Color.GREEN);
	mapLb.setBackground(Color.BLACK);
	mapLb.setHorizontalAlignment(SwingConstants.CENTER);
	mapLb.setFont(new Font("Tahoma", Font.PLAIN, 18));

	JButton btnSiguiente = new JButton("------->");
	btnSiguiente.setFocusPainted(false);
	btnSiguiente.setFocusTraversalKeysEnabled(false);
	btnSiguiente.setForeground(Color.BLACK);
	btnSiguiente.setBackground(Color.GREEN);
	btnSiguiente.setFont(new Font("Tahoma", Font.PLAIN, 14));
	JButton btnAnter = new JButton("<------");
	btnAnter.setFocusTraversalKeysEnabled(false);
	btnAnter.setFocusPainted(false);
	btnAnter.setEnabled(false);
	btnAnter.setForeground(Color.BLACK);
	btnAnter.setBackground(Color.GREEN);
	btnAnter.setFont(new Font("Tahoma", Font.PLAIN, 14));

	minutesTextField = new JTextField();
	minutesTextField.setBackground(Color.BLACK);
	minutesTextField.setForeground(Color.GREEN);
	minutesTextField.setColumns(10);

	pointstextField = new JTextField();
	pointstextField.setForeground(Color.GREEN);
	pointstextField.setBackground(Color.BLACK);
	pointstextField.setEnabled(false);
	pointstextField.setColumns(10);

	JLabel gameModeLb = new JLabel("Modo Juego:");
	gameModeLb.setBackground(Color.BLACK);
	gameModeLb.setForeground(Color.GREEN);
	gameModeLb.setHorizontalAlignment(SwingConstants.CENTER);
	gameModeLb.setFont(new Font("Tahoma", Font.PLAIN, 18));

	JRadioButton survivalRdBtn = new JRadioButton("Supervivencia");
	survivalRdBtn.setFocusPainted(false);
	survivalRdBtn.setBackground(Color.BLACK);
	survivalRdBtn.setForeground(Color.GREEN);
	survivalRdBtn.addActionListener(new ActionListener()
	{
	    public void actionPerformed(ActionEvent arg0)
	    {
		minutesTextField.setEnabled(false);
		pointstextField.setEnabled(false);

		SoundManager.play("click");
	    }
	});
	survivalRdBtn.setFont(new Font("Tahoma", Font.PLAIN, 18));
	survivalRdBtn.setHorizontalAlignment(SwingConstants.LEFT);

	JRadioButton pointsRdBtn = new JRadioButton("Puntaje");
	pointsRdBtn.setFocusPainted(false);
	pointsRdBtn.setForeground(Color.GREEN);
	pointsRdBtn.setBackground(Color.BLACK);
	pointsRdBtn.addActionListener(new ActionListener()
	{
	    public void actionPerformed(ActionEvent e)
	    {
		minutesTextField.setEnabled(false);
		pointstextField.setEnabled(true);

		SoundManager.play("click");
	    }
	});
	pointsRdBtn.setFont(new Font("Tahoma", Font.PLAIN, 18));
	pointsRdBtn.setHorizontalAlignment(SwingConstants.LEFT);

	JRadioButton timeRdBtn = new JRadioButton("Tiempo");
	timeRdBtn.setFocusPainted(false);
	timeRdBtn.setForeground(Color.GREEN);
	timeRdBtn.setBackground(Color.BLACK);
	timeRdBtn.setSelected(true);
	timeRdBtn.addActionListener(new ActionListener()
	{
	    public void actionPerformed(ActionEvent e)
	    {
		minutesTextField.setEnabled(true);
		pointstextField.setEnabled(false);

		SoundManager.play("click");
	    }
	});
	timeRdBtn.setHorizontalAlignment(SwingConstants.LEFT);
	timeRdBtn.setFont(new Font("Tahoma", Font.PLAIN, 18));

	ButtonGroup modeButtonGroup = new ButtonGroup();
	modeButtonGroup.add(survivalRdBtn);
	modeButtonGroup.add(pointsRdBtn);
	modeButtonGroup.add(timeRdBtn);

	btnSiguiente.addActionListener(new ActionListener()
	{
	    public void actionPerformed(ActionEvent arg0)
	    {
		mapID++;
		mapsLabel.setIcon(new ImageIcon(maps[mapID]));

		if (mapID == (LEVELS - 1))
		{
		    btnSiguiente.setEnabled(false);
		}
		if (!btnAnter.isEnabled())
		{
		    btnAnter.setEnabled(true);
		}

		SoundManager.play("click");
	    }
	});

	btnAnter.addActionListener(new ActionListener()
	{
	    public void actionPerformed(ActionEvent arg0)
	    {
		mapID--;
		mapsLabel.setIcon(new ImageIcon(maps[mapID]));

		if (mapID == 0)
		{
		    btnAnter.setEnabled(false);
		}
		if (!btnSiguiente.isEnabled())
		{
		    btnSiguiente.setEnabled(true);
		}

		SoundManager.play("click");
	    }
	});

	JLabel error1Lb = new JLabel("");
	error1Lb.setForeground(Color.RED);
	error1Lb.setBackground(Color.BLACK);

	JButton playBtn = new JButton("Jugar");
	playBtn.setFocusTraversalKeysEnabled(false);
	playBtn.setFocusPainted(false);
	playBtn.setBackground(Color.GREEN);
	playBtn.setFont(new Font("Tahoma", Font.PLAIN, 20));

	playerTwoLb = new JLabel("Jugador 2:");
	playerTwoLb.setHorizontalAlignment(SwingConstants.CENTER);
	playerTwoLb.setForeground(Color.GREEN);
	playerTwoLb.setFont(new Font("Tahoma", Font.PLAIN, 18));
	playerTwoLb.setBackground(Color.BLACK);
	playerTwoLb.setVisible(false);

	playerTwoNameField = new JTextField();
	playerTwoNameField.setForeground(Color.GREEN);
	playerTwoNameField.setFont(new Font("Tahoma", Font.PLAIN, 15));
	playerTwoNameField.setColumns(10);
	playerTwoNameField.setBackground(Color.BLACK);
	playerTwoNameField.setVisible(false);

	playerThreeLb = new JLabel("Jugador 3:");
	playerThreeLb.setHorizontalAlignment(SwingConstants.CENTER);
	playerThreeLb.setForeground(Color.GREEN);
	playerThreeLb.setFont(new Font("Tahoma", Font.PLAIN, 18));
	playerThreeLb.setBackground(Color.BLACK);
	playerThreeLb.setVisible(false);

	playerThreeNameField = new JTextField();
	playerThreeNameField.setForeground(Color.GREEN);
	playerThreeNameField.setFont(new Font("Tahoma", Font.PLAIN, 15));
	playerThreeNameField.setColumns(10);
	playerThreeNameField.setBackground(Color.BLACK);
	playerThreeNameField.setVisible(false);

	playerFourLb = new JLabel("Jugador 4:");
	playerFourLb.setHorizontalAlignment(SwingConstants.CENTER);
	playerFourLb.setForeground(Color.GREEN);
	playerFourLb.setFont(new Font("Tahoma", Font.PLAIN, 18));
	playerFourLb.setBackground(Color.BLACK);
	playerFourLb.setVisible(false);

	playerFourNameField = new JTextField();
	playerFourNameField.setForeground(Color.GREEN);
	playerFourNameField.setFont(new Font("Tahoma", Font.PLAIN, 15));
	playerFourNameField.setColumns(10);
	playerFourNameField.setBackground(Color.BLACK);
	playerFourNameField.setVisible(false);

	pLabels = new JLabel[4];
	pLabels[0] = playerOneLb;
	pLabels[1] = playerTwoLb;
	pLabels[2] = playerThreeLb;
	pLabels[3] = playerFourLb;

	pJTextFields = new JTextField[4];
	pJTextFields[0] = playerOneNameField;
	pJTextFields[1] = playerTwoNameField;
	pJTextFields[2] = playerThreeNameField;
	pJTextFields[3] = playerFourNameField;

	boolean[] bots = new boolean[4];

	JCheckBox bot1Chckbx = new JCheckBox("BOT");
	bot1Chckbx.setForeground(Color.GREEN);
	bot1Chckbx.setBackground(Color.BLACK);
	bot1Chckbx.addChangeListener(new ChangeListener()
	{
	    public void stateChanged(ChangeEvent e)
	    {
		bots[0] = bot1Chckbx.isSelected();
	    }
	});

	JCheckBox bot2Chckbx = new JCheckBox("BOT");
	bot2Chckbx.setVisible(false);
	bot2Chckbx.setForeground(Color.GREEN);
	bot2Chckbx.setBackground(Color.BLACK);
	bot2Chckbx.addChangeListener(new ChangeListener()
	{
	    public void stateChanged(ChangeEvent e)
	    {
		bots[1] = bot2Chckbx.isSelected();
	    }
	});

	JCheckBox bot3Chckbx = new JCheckBox("BOT");
	bot3Chckbx.setVisible(false);
	bot3Chckbx.setForeground(Color.GREEN);
	bot3Chckbx.setBackground(Color.BLACK);
	bot3Chckbx.addChangeListener(new ChangeListener()
	{
	    public void stateChanged(ChangeEvent e)
	    {
		bots[2] = bot3Chckbx.isSelected();
	    }
	});

	JCheckBox bot4Chckbx = new JCheckBox("BOT");
	bot4Chckbx.setVisible(false);
	bot4Chckbx.setForeground(Color.GREEN);
	bot4Chckbx.setBackground(Color.BLACK);
	bot4Chckbx.addChangeListener(new ChangeListener()
	{
	    public void stateChanged(ChangeEvent e)
	    {
		bots[3] = bot4Chckbx.isSelected();
	    }
	});

	botsArray = new JCheckBox[4];
	botsArray[0] = bot1Chckbx;
	botsArray[1] = bot2Chckbx;
	botsArray[2] = bot3Chckbx;
	botsArray[3] = bot4Chckbx;

	playBtn.addActionListener(new ActionListener()
	{
	    public void actionPerformed(ActionEvent arg0)
	    {
		String[] playerNames = new String[4];
		String amount;
		GameMode gameMode = null;
		// boolean powers = false;
		boolean canPlay = true;

		// if (powerUpsOnBtn.isSelected())
		// {
		// powers = true;
		// }

		for (int i = 0; i < playersComboBox.getSelectedIndex() + 1; i++)
		{
		    playerNames[i] = pJTextFields[i].getText();

		    if (playerNames[i].isEmpty())
		    {
			playerNames[i] = "Jugador " + (i + 1);
		    }
		}

		if (survivalRdBtn.isSelected())
		{
		    gameMode = new GameMode("SURVIVAL", 0, 0);
		}

		if (timeRdBtn.isSelected())
		{
		    amount = minutesTextField.getText();

		    try
		    {
			Integer.parseInt(amount);
		    } catch (NumberFormatException e)
		    {
			if (!amount.isEmpty())
			{
			    error1Lb.setText("No es un numero valido.");
			    pointstextField.setText("");
			    canPlay = false;
			}
		    }

		    if (!amount.isEmpty() && (Integer.parseInt(amount) > 60 || Integer.parseInt(amount) <= 0))
		    {
			error1Lb.setText("No es un numero valido.");
			canPlay = false;
		    }

		    if (amount.isEmpty())
		    {
			amount = "5";
		    }

		    if (canPlay)
		    {
			gameMode = new GameMode("TIME", 0, Integer.valueOf(amount));
			error1Lb.setText("");
		    }
		}

		if (pointsRdBtn.isSelected())
		{
		    amount = pointstextField.getText();

		    try
		    {
			Integer.parseInt(amount);
		    } catch (NumberFormatException e)
		    {
			if (!amount.isEmpty())
			{
			    error1Lb.setText("No es un numero valido.");
			    minutesTextField.setText("");
			    canPlay = false;
			}
		    }

		    if (!amount.isEmpty() && (Integer.parseInt(amount) > 5000 || Integer.parseInt(amount) < 10))
		    {
			error1Lb.setText("No es un numero valido.");
			canPlay = false;
		    }

		    if (amount.isEmpty())
		    {
			amount = "300";
		    }

		    if (canPlay)
		    {
			gameMode = new GameMode("SCORE", Integer.valueOf(amount), 0);
			error1Lb.setText("");
		    }
		}

		if (canPlay)
		{
		    mainWindow.startGame(playersComboBox.getSelectedIndex() + 1, mapID, playerNames, bots, gameMode);
		}
	    }
	});

	GroupLayout groupLayout = new GroupLayout(this);
	groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addGroup(groupLayout
		.createSequentialGroup()
		.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
			.createSequentialGroup().addGap(115)
			.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(gameModeLb, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE)
				.addComponent(powerUpsLb, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE))
			.addGap(18)
			.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(survivalRdBtn, GroupLayout.PREFERRED_SIZE, 259,
					GroupLayout.PREFERRED_SIZE)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(pointsRdBtn, GroupLayout.PREFERRED_SIZE, 109,
						GroupLayout.PREFERRED_SIZE)
					.addGap(36).addComponent(pointstextField, GroupLayout.PREFERRED_SIZE, 101,
						GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(timeRdBtn, GroupLayout.PREFERRED_SIZE, 138,
							GroupLayout.PREFERRED_SIZE)
						.addComponent(powerUpsOnBtn, GroupLayout.PREFERRED_SIZE, 101,
							GroupLayout.PREFERRED_SIZE))
					.addGap(7)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(powerUpsOffBtn).addComponent(minutesTextField,
							GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE))))
			.addGap(172)
			.addComponent(error1Lb, GroupLayout.PREFERRED_SIZE, 177, GroupLayout.PREFERRED_SIZE))
			.addGroup(groupLayout.createSequentialGroup().addGap(158).addGroup(groupLayout
				.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(playersLb, GroupLayout.PREFERRED_SIZE, 93,
						GroupLayout.PREFERRED_SIZE)
					.addGap(30).addComponent(playersComboBox, GroupLayout.PREFERRED_SIZE, 135,
						GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup().addGap(59)
					.addComponent(playBtn, GroupLayout.PREFERRED_SIZE, 144,
						GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(backToMenuBtn,
						GroupLayout.PREFERRED_SIZE, 144, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(mapLb, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnAnter, GroupLayout.PREFERRED_SIZE, 87,
						GroupLayout.PREFERRED_SIZE)
					.addGap(31)
					.addComponent(mapsLabel, GroupLayout.PREFERRED_SIZE, 116,
						GroupLayout.PREFERRED_SIZE)
					.addGap(18).addComponent(btnSiguiente, GroupLayout.PREFERRED_SIZE, 89,
						GroupLayout.PREFERRED_SIZE))))
			.addGroup(groupLayout.createSequentialGroup().addGap(178)
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
					.addGroup(groupLayout.createSequentialGroup()
						.addComponent(playerTwoLb, GroupLayout.PREFERRED_SIZE, 93,
							GroupLayout.PREFERRED_SIZE)
						.addGap(18)
						.addComponent(playerTwoNameField, GroupLayout.PREFERRED_SIZE, 246,
							GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(bot2Chckbx,
							GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE))
					.addGroup(groupLayout.createSequentialGroup()
						.addComponent(playerThreeLb, GroupLayout.PREFERRED_SIZE, 93,
							GroupLayout.PREFERRED_SIZE)
						.addGap(18)
						.addComponent(playerThreeNameField, GroupLayout.PREFERRED_SIZE, 246,
							GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(bot3Chckbx,
							GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
							Short.MAX_VALUE))
					.addGroup(groupLayout.createSequentialGroup()
						.addComponent(playerFourLb, GroupLayout.PREFERRED_SIZE, 93,
							GroupLayout.PREFERRED_SIZE)
						.addGap(18)
						.addComponent(playerFourNameField, GroupLayout.PREFERRED_SIZE, 246,
							GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(bot4Chckbx,
							GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
							Short.MAX_VALUE))
					.addGroup(groupLayout.createSequentialGroup()
						.addComponent(playerOneLb, GroupLayout.PREFERRED_SIZE, 93,
							GroupLayout.PREFERRED_SIZE)
						.addGap(18)
						.addComponent(playerOneNameField, GroupLayout.PREFERRED_SIZE, 246,
							GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(bot1Chckbx, GroupLayout.DEFAULT_SIZE,
							GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
		.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
	groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addGroup(groupLayout
		.createSequentialGroup().addGap(40)
		.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
			.addComponent(mapsLabel, GroupLayout.PREFERRED_SIZE, 116, GroupLayout.PREFERRED_SIZE)
			.addGroup(groupLayout.createSequentialGroup()
				.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
					.addComponent(btnAnter, GroupLayout.PREFERRED_SIZE, 41,
						GroupLayout.PREFERRED_SIZE)
					.addComponent(mapLb, GroupLayout.PREFERRED_SIZE, 27,
						GroupLayout.PREFERRED_SIZE))
				.addGap(42))
			.addGroup(
				groupLayout.createSequentialGroup()
					.addComponent(btnSiguiente, GroupLayout.PREFERRED_SIZE, 40,
						GroupLayout.PREFERRED_SIZE)
					.addGap(41)))
		.addGap(18)
		.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
			.addGroup(groupLayout.createSequentialGroup().addGap(64).addComponent(error1Lb,
				GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
			.addGroup(groupLayout.createSequentialGroup().addGap(18).addGroup(groupLayout
				.createParallelGroup(Alignment.BASELINE)
				.addComponent(powerUpsLb, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
				.addComponent(powerUpsOnBtn, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
				.addComponent(powerUpsOffBtn, GroupLayout.PREFERRED_SIZE, 23,
					GroupLayout.PREFERRED_SIZE))
				.addGap(6)
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
					.addComponent(gameModeLb, GroupLayout.PREFERRED_SIZE, 27,
						GroupLayout.PREFERRED_SIZE)
					.addGroup(groupLayout.createSequentialGroup().addGroup(groupLayout
						.createParallelGroup(Alignment.LEADING)
						.addComponent(timeRdBtn, GroupLayout.PREFERRED_SIZE, 23,
							GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup().addGap(3).addComponent(
							minutesTextField, GroupLayout.PREFERRED_SIZE, 21,
							GroupLayout.PREFERRED_SIZE)))
						.addGap(5)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addComponent(pointsRdBtn, GroupLayout.PREFERRED_SIZE, 23,
								GroupLayout.PREFERRED_SIZE)
							.addComponent(pointstextField, GroupLayout.PREFERRED_SIZE, 21,
								GroupLayout.PREFERRED_SIZE))))
				.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(survivalRdBtn,
					GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)))
		.addGap(26)
		.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
			.addComponent(playersComboBox, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
			.addComponent(playersLb, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
		.addGap(18)
		.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
			.addComponent(playerOneNameField, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
			.addComponent(playerOneLb, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
			.addComponent(bot1Chckbx))
		.addPreferredGap(ComponentPlacement.RELATED)
		.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
			.addComponent(playerTwoLb, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
			.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
				.addComponent(playerTwoNameField, GroupLayout.PREFERRED_SIZE, 26,
					GroupLayout.PREFERRED_SIZE)
				.addComponent(bot2Chckbx)))
		.addPreferredGap(ComponentPlacement.RELATED)
		.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
			.addComponent(playerThreeLb, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
			.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
				.addComponent(playerThreeNameField, GroupLayout.PREFERRED_SIZE, 26,
					GroupLayout.PREFERRED_SIZE)
				.addComponent(bot3Chckbx)))
		.addPreferredGap(ComponentPlacement.RELATED)
		.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
			.addComponent(playerFourLb, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
			.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
				.addComponent(playerFourNameField, GroupLayout.PREFERRED_SIZE, 26,
					GroupLayout.PREFERRED_SIZE)
				.addComponent(bot4Chckbx)))
		.addPreferredGap(ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
		.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
			.addComponent(playBtn, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
			.addComponent(backToMenuBtn, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE))
		.addGap(26)));
	setLayout(groupLayout);
    }

    private void disableEnablePlayers(int option)
    {
	for (int i = 0; i < option; i++)
	{
	    pLabels[i].setVisible(true);
	    pJTextFields[i].setVisible(true);
	    botsArray[i].setVisible(true);
	}

	for (int i = 3; i >= option; i--)
	{
	    pLabels[i].setVisible(false);
	    pJTextFields[i].setVisible(false);
	    pJTextFields[i].setText("");
	    botsArray[i].setVisible(false);
	}
    }
}
