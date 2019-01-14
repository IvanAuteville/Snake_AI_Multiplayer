package menus;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import online.GameServer;
import online.NetworkHandler;
import online.Server;
import snake.GameMode;
import sound_engine.SoundManager;

public class HostPanel extends JPanel implements Observer
{
    private static final long serialVersionUID = 1L;

    // UI
    private JTextField inputTextField;
    private JTextArea textArea;
    private JTextField minutesTextField;
    private JTextField pointsTextField;
    private JRadioButton timeRdBtn;
    private JRadioButton pointsRdBtn;
    private JRadioButton survivalRdBtn;

    // Utilidad
    private JLabel[] playersLbl;
    private JButton[] kickBtns;
    private JCheckBox[] readyChkBoxs;
    private ArrayList<String> userNames;
    private ArrayList<Boolean> usersReady;

    // MAPAS
    private static final int LEVELS = 4;
    private BufferedImage[] mapsBuffered;
    private Image[] maps;
    private int mapID;
    // ---------------------------------

    private MainWindow mainWindow;
    private NetworkHandler network;

    private GameServer gameServer;

    public HostPanel(MainWindow mainWindow, NetworkHandler net, Server server, GameServer gameServer, String userName)
    {
	this.mainWindow = mainWindow;
	this.gameServer = gameServer;

	playersLbl = new JLabel[4];
	kickBtns = new JButton[3];
	readyChkBoxs = new JCheckBox[3];
	userNames = new ArrayList<String>(4);
	usersReady = new ArrayList<Boolean>(4);

	this.mapID = 0;
	mapsBuffered = new BufferedImage[LEVELS];
	maps = new Image[LEVELS];

	// Acceso a la Red
	this.network = net;
	network.addObserver(this);
	network.send(userName + " (ADMIN)");
	// ----------------------------------

	setPreferredSize(new java.awt.Dimension(800 + 106, 600 + 29));
	setBackground(Color.BLACK);
	setBorder(null);

	JButton backBtn = new JButton("Volver");
	backBtn.addActionListener(new ActionListener()
	{
	    public void actionPerformed(ActionEvent e)
	    {
		SoundManager.play("click");

		// Avisar a los clientes que se cerrara el server
		network.send("8");

		serverClosed();
	    }
	});
	backBtn.setFont(new Font("Tahoma", Font.PLAIN, 20));
	backBtn.setFocusTraversalKeysEnabled(false);
	backBtn.setFocusPainted(false);
	backBtn.setBackground(Color.GREEN);

	JButton readyBtn = new JButton("Iniciar");
	readyBtn.addActionListener(new ActionListener()
	{
	    public void actionPerformed(ActionEvent e)
	    {
		SoundManager.play("click");

		boolean ready = true;

		for (int i = 1; i < userNames.size(); i++)
		{
		    if (!usersReady.get(i))
		    {
			ready = false;
			break;
		    }
		}

		// TODO
		if (ready) //&& userNames.size() > 1)
		{
		    try
		    {
			startGame();
		    } catch (Exception e1)
		    {
			e1.printStackTrace();
		    }
		} else
		{
		    if (userNames.size() == 1)
		    {
			network.send("1" + "Esperando jugadores...");
		    } else
		    {
			network.send("1" + "Esperando que los jugadores esten listos...");
		    }
		}
	    }
	});
	readyBtn.setFont(new Font("Tahoma", Font.PLAIN, 20));
	readyBtn.setFocusTraversalKeysEnabled(false);
	readyBtn.setFocusPainted(false);
	readyBtn.setBackground(Color.GREEN);

	JPanel panel = new JPanel();

	JScrollPane scrollPane = new JScrollPane();
	scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
	GroupLayout gl_panel = new GroupLayout(panel);
	gl_panel.setHorizontalGroup(
		gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel.createSequentialGroup()
			.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 475, Short.MAX_VALUE).addGap(0)));
	gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addComponent(scrollPane,
		Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 455, Short.MAX_VALUE));

	textArea = new JTextArea();
	textArea.setEditable(false);
	textArea.setForeground(Color.GREEN);
	textArea.setBackground(Color.DARK_GRAY);
	scrollPane.setViewportView(textArea);
	panel.setLayout(gl_panel);

	JLabel p1Lbl = new JLabel("");
	p1Lbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
	p1Lbl.setHorizontalAlignment(SwingConstants.CENTER);
	p1Lbl.setForeground(Color.GREEN);

	JLabel p2Lbl = new JLabel("");
	p2Lbl.setHorizontalAlignment(SwingConstants.CENTER);
	p2Lbl.setForeground(Color.GREEN);
	p2Lbl.setFont(new Font("Tahoma", Font.PLAIN, 18));

	JLabel p3Lbl = new JLabel("");
	p3Lbl.setHorizontalAlignment(SwingConstants.CENTER);
	p3Lbl.setForeground(Color.GREEN);
	p3Lbl.setFont(new Font("Tahoma", Font.PLAIN, 18));

	JLabel p4Lbl = new JLabel("");
	p4Lbl.setHorizontalAlignment(SwingConstants.CENTER);
	p4Lbl.setForeground(Color.GREEN);
	p4Lbl.setFont(new Font("Tahoma", Font.PLAIN, 18));

	playersLbl[0] = p1Lbl;
	playersLbl[1] = p2Lbl;
	playersLbl[2] = p3Lbl;
	playersLbl[3] = p4Lbl;

	inputTextField = new JTextField();
	inputTextField.addKeyListener(new KeyAdapter()
	{
	    @Override
	    public void keyPressed(KeyEvent e)
	    {
		if (e.getKeyCode() == KeyEvent.VK_ENTER)
		{
		    String str = inputTextField.getText();

		    if (str != null && str.trim().length() > 0)
		    {
			network.send("1" + str);
		    }

		    inputTextField.selectAll();
		    inputTextField.requestFocus();
		    inputTextField.setText("");
		}
	    }
	});
	inputTextField.setForeground(Color.GREEN);
	inputTextField.setBackground(Color.DARK_GRAY);
	inputTextField.setColumns(10);

	JLabel lblMapa = new JLabel("Mapa");
	lblMapa.setHorizontalAlignment(SwingConstants.CENTER);
	lblMapa.setForeground(Color.GREEN);
	lblMapa.setFont(new Font("Tahoma", Font.PLAIN, 18));
	lblMapa.setBackground(Color.BLACK);

	JButton prevBtn = new JButton("<-----");
	prevBtn.setForeground(Color.BLACK);
	prevBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
	prevBtn.setFocusTraversalKeysEnabled(false);
	prevBtn.setFocusPainted(false);
	prevBtn.setEnabled(false);
	prevBtn.setBackground(Color.GREEN);

	JLabel mapsLbl = new JLabel();

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

	mapsLbl.setIcon(new ImageIcon(maps[0]));

	JButton nextBtn = new JButton("----->");
	nextBtn.setForeground(Color.BLACK);
	nextBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
	nextBtn.setFocusTraversalKeysEnabled(false);
	nextBtn.setFocusPainted(false);
	nextBtn.setBackground(Color.GREEN);

	nextBtn.addActionListener(new ActionListener()
	{
	    public void actionPerformed(ActionEvent arg0)
	    {
		mapID++;
		mapsLbl.setIcon(new ImageIcon(maps[mapID]));
		network.send("6" + mapID);

		if (mapID == (LEVELS - 1))
		{
		    nextBtn.setEnabled(false);
		}
		if (!prevBtn.isEnabled())
		{
		    prevBtn.setEnabled(true);
		}

		SoundManager.play("click");
	    }
	});

	prevBtn.addActionListener(new ActionListener()
	{
	    public void actionPerformed(ActionEvent arg0)
	    {
		mapID--;
		mapsLbl.setIcon(new ImageIcon(maps[mapID]));
		network.send("6" + mapID);

		if (mapID == 0)
		{
		    prevBtn.setEnabled(false);
		}
		if (!nextBtn.isEnabled())
		{
		    nextBtn.setEnabled(true);
		}

		SoundManager.play("click");
	    }
	});

	JLabel gameModeLbl = new JLabel("Modo Juego");
	gameModeLbl.setHorizontalAlignment(SwingConstants.CENTER);
	gameModeLbl.setForeground(Color.GREEN);
	gameModeLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
	gameModeLbl.setBackground(Color.BLACK);

	timeRdBtn = new JRadioButton("Tiempo");
	timeRdBtn.setHorizontalAlignment(SwingConstants.LEFT);
	timeRdBtn.setForeground(Color.GREEN);
	timeRdBtn.setFont(new Font("Tahoma", Font.PLAIN, 18));
	timeRdBtn.setFocusPainted(false);
	timeRdBtn.setBackground(Color.BLACK);

	minutesTextField = new JTextField();
	minutesTextField.setEnabled(false);
	minutesTextField.addKeyListener(new KeyAdapter()
	{
	    @Override
	    public void keyPressed(KeyEvent e)
	    {
		if (e.getKeyCode() == KeyEvent.VK_ENTER)
		{
		    network.send("7" + "TIEMPO: " + minutesTextField.getText() + " Minutos");
		}
	    }
	});

	minutesTextField.setForeground(Color.GREEN);
	minutesTextField.setColumns(10);
	minutesTextField.setBackground(Color.BLACK);

	pointsRdBtn = new JRadioButton("Puntaje");
	pointsRdBtn.setHorizontalAlignment(SwingConstants.LEFT);
	pointsRdBtn.setForeground(Color.GREEN);
	pointsRdBtn.setFont(new Font("Tahoma", Font.PLAIN, 18));
	pointsRdBtn.setFocusPainted(false);
	pointsRdBtn.setBackground(Color.BLACK);

	pointsTextField = new JTextField();

	pointsTextField.addKeyListener(new KeyAdapter()
	{
	    @Override
	    public void keyPressed(KeyEvent e)
	    {
		if (e.getKeyCode() == KeyEvent.VK_ENTER)
		{
		    network.send("7" + "PUNTAJE: " + pointsTextField.getText() + " Puntos");
		}
	    }
	});

	pointsTextField.setForeground(Color.GREEN);
	pointsTextField.setEnabled(false);
	pointsTextField.setColumns(10);
	pointsTextField.setBackground(Color.BLACK);

	survivalRdBtn = new JRadioButton("Supervivencia");
	survivalRdBtn.setSelected(true);
	survivalRdBtn.setHorizontalAlignment(SwingConstants.LEFT);
	survivalRdBtn.setForeground(Color.GREEN);
	survivalRdBtn.setFont(new Font("Tahoma", Font.PLAIN, 18));
	survivalRdBtn.setFocusPainted(false);
	survivalRdBtn.setBackground(Color.BLACK);

	pointsRdBtn.addActionListener(new ActionListener()
	{
	    public void actionPerformed(ActionEvent e)
	    {
		minutesTextField.setEnabled(false);
		pointsTextField.setEnabled(true);
		timeRdBtn.setSelected(false);
		survivalRdBtn.setSelected(false);
		SoundManager.play("click");
	    }
	});

	timeRdBtn.addActionListener(new ActionListener()
	{
	    public void actionPerformed(ActionEvent e)
	    {
		minutesTextField.setEnabled(true);
		pointsTextField.setEnabled(false);
		pointsRdBtn.setSelected(false);
		survivalRdBtn.setSelected(false);
		SoundManager.play("click");
	    }
	});

	survivalRdBtn.addActionListener(new ActionListener()
	{
	    public void actionPerformed(ActionEvent e)
	    {
		network.send("7" + "SUPERVIVENCIA");

		minutesTextField.setEnabled(false);
		pointsTextField.setEnabled(false);
		timeRdBtn.setSelected(false);
		pointsRdBtn.setSelected(false);
		SoundManager.play("click");
	    }
	});

	JCheckBox p2ReadyChkBox = new JCheckBox("");
	p2ReadyChkBox.setEnabled(false);
	p2ReadyChkBox.setVisible(false);
	p2ReadyChkBox.setForeground(Color.DARK_GRAY);
	p2ReadyChkBox.setBackground(Color.BLACK);

	JCheckBox p3ReadyChkBox = new JCheckBox("");
	p3ReadyChkBox.setEnabled(false);
	p3ReadyChkBox.setVisible(false);
	p3ReadyChkBox.setForeground(Color.DARK_GRAY);
	p3ReadyChkBox.setBackground(Color.BLACK);

	JCheckBox p4ReadyChkBox = new JCheckBox("");
	p4ReadyChkBox.setEnabled(false);
	p4ReadyChkBox.setVisible(false);
	p4ReadyChkBox.setForeground(Color.DARK_GRAY);
	p4ReadyChkBox.setBackground(Color.BLACK);

	JButton kickp2Btn = new JButton("Kick");
	kickp2Btn.addActionListener(new ActionListener()
	{
	    public void actionPerformed(ActionEvent e)
	    {
		network.send("5" + userNames.get(1));
	    }
	});
	kickp2Btn.setVisible(false);
	kickp2Btn.setBackground(Color.GREEN);

	JButton kickp3Btn = new JButton("Kick");
	kickp3Btn.addActionListener(new ActionListener()
	{
	    public void actionPerformed(ActionEvent e)
	    {
		network.send("5" + userNames.get(2));
	    }
	});
	kickp3Btn.setVisible(false);
	kickp3Btn.setBackground(Color.GREEN);

	JButton kickp4Btn = new JButton("Kick");
	kickp4Btn.addActionListener(new ActionListener()
	{
	    public void actionPerformed(ActionEvent e)
	    {
		network.send("5" + userNames.get(3));
	    }
	});
	kickp4Btn.setVisible(false);
	kickp4Btn.setBackground(Color.GREEN);

	kickBtns[0] = kickp2Btn;
	kickBtns[1] = kickp3Btn;
	kickBtns[2] = kickp4Btn;

	readyChkBoxs[0] = p2ReadyChkBox;
	readyChkBoxs[1] = p3ReadyChkBox;
	readyChkBoxs[2] = p4ReadyChkBox;

	GroupLayout groupLayout = new GroupLayout(this);
	groupLayout.setHorizontalGroup(
		groupLayout.createParallelGroup(Alignment.LEADING)
			.addGroup(groupLayout.createSequentialGroup()
				.addGap(19)
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
					.addComponent(inputTextField, GroupLayout.DEFAULT_SIZE, 497, Short.MAX_VALUE)
					.addGroup(groupLayout.createSequentialGroup()
						.addComponent(backBtn, GroupLayout.PREFERRED_SIZE, 144, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED, 209, Short.MAX_VALUE)
						.addComponent(readyBtn, GroupLayout.PREFERRED_SIZE, 144, GroupLayout.PREFERRED_SIZE))
					.addGroup(groupLayout.createSequentialGroup()
						.addComponent(panel, GroupLayout.DEFAULT_SIZE, 495, Short.MAX_VALUE)
						.addGap(2)))
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
					.addGroup(groupLayout.createSequentialGroup()
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addComponent(prevBtn)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(kickp4Btn, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(kickp3Btn, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(kickp2Btn, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
							.addGroup(groupLayout.createSequentialGroup()
								.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
									.addComponent(gameModeLbl, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE)
									.addComponent(mapsLbl, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
									.addComponent(lblMapa, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE))
								.addGap(44)
								.addComponent(nextBtn)
								.addGap(15))
							.addGroup(groupLayout.createSequentialGroup()
								.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
									.addComponent(p2Lbl, GroupLayout.PREFERRED_SIZE, 270, GroupLayout.PREFERRED_SIZE)
									.addComponent(p3Lbl, GroupLayout.PREFERRED_SIZE, 270, GroupLayout.PREFERRED_SIZE)
									.addComponent(p4Lbl, GroupLayout.PREFERRED_SIZE, 270, GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
									.addComponent(p4ReadyChkBox, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
									.addComponent(p3ReadyChkBox, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
									.addComponent(p2ReadyChkBox, Alignment.TRAILING)))
							.addGroup(groupLayout.createSequentialGroup()
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
									.addComponent(survivalRdBtn, GroupLayout.PREFERRED_SIZE, 259, GroupLayout.PREFERRED_SIZE)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
										.addGroup(groupLayout.createSequentialGroup()
											.addComponent(pointsRdBtn)
											.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
											.addComponent(pointsTextField, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE))
										.addGroup(groupLayout.createSequentialGroup()
											.addComponent(timeRdBtn, GroupLayout.PREFERRED_SIZE, 138, GroupLayout.PREFERRED_SIZE)
											.addGap(7)
											.addComponent(minutesTextField, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE))))
								.addGap(38))))
					.addGroup(groupLayout.createSequentialGroup()
						.addGap(58)
						.addComponent(p1Lbl, GroupLayout.PREFERRED_SIZE, 270, GroupLayout.PREFERRED_SIZE)))
				.addGap(18))
	);
	groupLayout.setVerticalGroup(
		groupLayout.createParallelGroup(Alignment.LEADING)
			.addGroup(groupLayout.createSequentialGroup()
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
					.addGroup(groupLayout.createSequentialGroup()
						.addGap(18)
						.addComponent(panel, GroupLayout.DEFAULT_SIZE, 483, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(inputTextField, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
						.addGap(48))
					.addGroup(groupLayout.createSequentialGroup()
						.addGap(12)
						.addComponent(p1Lbl, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(5)
								.addComponent(p2ReadyChkBox))
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(p2Lbl, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
								.addComponent(kickp2Btn)))
						.addGap(18)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(5)
								.addComponent(p3ReadyChkBox, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
							.addComponent(p3Lbl, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
							.addComponent(kickp3Btn))
						.addGap(18)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(2)
								.addComponent(p4ReadyChkBox, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
							.addComponent(p4Lbl, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
							.addComponent(kickp4Btn))
						.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(lblMapa, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
						.addGap(18)
						.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
							.addGroup(groupLayout.createSequentialGroup()
								.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
									.addGroup(groupLayout.createSequentialGroup()
										.addComponent(mapsLbl, GroupLayout.PREFERRED_SIZE, 116, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(gameModeLbl, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.UNRELATED))
									.addGroup(groupLayout.createSequentialGroup()
										.addComponent(nextBtn, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addGap(101)))
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
									.addComponent(timeRdBtn)
									.addGroup(groupLayout.createSequentialGroup()
										.addGap(3)
										.addComponent(minutesTextField, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)))
								.addGap(5)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
									.addComponent(pointsRdBtn)
									.addComponent(pointsTextField, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
								.addGap(5)
								.addComponent(survivalRdBtn)
								.addGap(2))
							.addGroup(groupLayout.createSequentialGroup()
								.addComponent(prevBtn, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
								.addGap(200)))))
				.addGap(35))
			.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
				.addContainerGap(581, Short.MAX_VALUE)
				.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
					.addComponent(backBtn, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
					.addComponent(readyBtn, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE))
				.addContainerGap())
	);
	setLayout(groupLayout);
    }

    protected void startGame() throws Exception
    {
	String[] playerNames = new String[userNames.size()];
	String amount;
	GameMode gameMode = null;
	boolean canPlay = true;

	// Seteo de jugadores
	userNames.toArray(playerNames);

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
		    textArea.append("No es un numero valido.\n");

		    pointsTextField.setText("");
		    canPlay = false;
		}
	    }

	    if (!amount.isEmpty() && (Integer.parseInt(amount) > 60 || Integer.parseInt(amount) <= 0))
	    {
		textArea.append("No es un numero valido.\n");
		canPlay = false;
	    }

	    if (amount.isEmpty())
	    {
		amount = "5";
	    }

	    if (canPlay)
	    {
		gameMode = new GameMode("TIME", 0, Integer.parseInt(amount));
	    }
	}

	if (pointsRdBtn.isSelected())
	{
	    amount = pointsTextField.getText();

	    try
	    {
		Integer.parseInt(amount);
	    } catch (NumberFormatException e)
	    {
		if (!amount.isEmpty())
		{
		    textArea.append("No es un numero valido.\n");

		    minutesTextField.setText("");
		    canPlay = false;
		}
	    }

	    if (!amount.isEmpty() && (Integer.parseInt(amount) > 5000 || Integer.parseInt(amount) < 10))
	    {
		textArea.append("No es un numero valido.\n");
		canPlay = false;
	    }

	    if (amount.isEmpty())
	    {
		amount = "300";
	    }

	    if (canPlay)
	    {
		gameMode = new GameMode("SCORE", Integer.valueOf(amount), 0);
	    }
	}

	if (canPlay)
	{
	    network.send("9" + gameMode.getGameMode() + "|" + gameMode.getGameScore() + "|" + gameMode.getGameTime());

	    network.send("1" + "El juego esta por comenzar...");

	    // CLIENT START GAME
	    startGame(gameMode);

	    // SERVER START GAME
	    gameServer.start();
	}
    }

    protected void startGame(GameMode gameMode)
    {
	mainWindow.startOnlineGame(this.network, mapID, gameMode);
    }

    @Override
    public void update(Observable o, Object arg)
    {
	final String command = arg.toString();
	SwingUtilities.invokeLater(new Runnable()
	{
	    public void run()
	    {
		switch (command.charAt(0))
		{
		case '1':
		    textArea.append(command.substring(1, command.length()));
		    textArea.append("\n");
		    break;
		case '2':
		    updatePlayers(1, command.substring(1, command.length()));
		    break;
		case '3':
		    updatePlayers(2, command.substring(1, command.length()));
		    break;
		case '4':
		    updatePlayers(3, command.substring(1, command.length()));
		    break;
		case '8':
		    serverClosed();
		default:
		    break;
		}
	    }
	});
    }

    protected void serverClosed()
    {
	network.send("8");

	// Cerrar la conexion
	network.close();

	// Cerrar el server
	mainWindow.stopServer();

	mainWindow.openOnline();
    }

    private void updatePlayers(int mode, String player)
    {
	// Agregar jugadores
	if (mode == 1)
	{
	    userNames.add(player);
	    usersReady.add(false);

	    if (usersReady.size() == 1)
	    {
		usersReady.set(0, true);
	    }
	}

	// Quitar jugadores
	if (mode == 2)
	{
	    int index = userNames.indexOf(player);
	    userNames.remove(index);
	    usersReady.remove(index);
	}

	if (mode == 1 || mode == 2)
	{
	    for (int i = 0; i < playersLbl.length; i++)
	    {
		if (i < userNames.size())
		{
		    playersLbl[i].setText(userNames.get(i).substring(0, userNames.get(i).length() - 1));

		    if (i > 0)
		    {
			readyChkBoxs[i - 1].setVisible(true);
			readyChkBoxs[i - 1].setSelected(usersReady.get(i));
			kickBtns[i - 1].setVisible(true);
		    }

		} else
		{
		    playersLbl[i].setText("");

		    if (i > 0)
		    {
			readyChkBoxs[i - 1].setVisible(false);
			readyChkBoxs[i - 1].setSelected(false);
			kickBtns[i - 1].setVisible(false);
		    }
		}
	    }
	}

	// Un jugador cambio su estado "listo - no listo"
	if (mode == 3)
	{
	    int index = userNames.indexOf(player);
	    usersReady.set(index, !usersReady.get(index));
	    readyChkBoxs[index - 1].setSelected(usersReady.get(index));
	}
    }
}