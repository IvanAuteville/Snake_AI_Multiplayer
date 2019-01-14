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
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import online.NetworkHandler;
import snake.GameMode;
import sound_engine.SoundManager;
import javax.swing.JCheckBox;

public class ClientPanel extends JPanel implements Observer
{
    private static final long serialVersionUID = 1L;
    private JTextField inputTextField;
    private NetworkHandler network;
    private JTextArea textArea;
    private JLabel[] playersLbl;
    private ArrayList<String> userNames;
    private ArrayList<Boolean> usersReady;
    private JCheckBox[] readyChkBoxs;
    private JLabel gameModeLbl;
    private MainWindow mainWindow;
    private int mapID = 0;

    // MAPAS
    private static final int LEVELS = 4;
    private BufferedImage[] mapsBuffered;
    private Image[] maps;
    private JLabel mapsLbl;

    public ClientPanel(MainWindow mainWindow, NetworkHandler network, String userName)
    {
	this.mainWindow = mainWindow;

	playersLbl = new JLabel[4];
	readyChkBoxs = new JCheckBox[3];

	userNames = new ArrayList<String>(4);
	usersReady = new ArrayList<Boolean>(4);

	mapsBuffered = new BufferedImage[LEVELS];
	maps = new Image[LEVELS];

	// Conexion con el server
	this.network = network;
	network.addObserver(this);
	network.send(userName); // Enviamos nuestro username
	// ------------------------

	setPreferredSize(new java.awt.Dimension(800 + 106, 600 + 29));
	setBackground(Color.BLACK);
	setBorder(null);

	JButton backBtn = new JButton("Volver");
	backBtn.addActionListener(new ActionListener()
	{
	    public void actionPerformed(ActionEvent e)
	    {
		SoundManager.play("click");

		// Cerrar la conexion
		network.close();
		mainWindow.openOnline();
	    }
	});
	backBtn.setFont(new Font("Tahoma", Font.PLAIN, 20));
	backBtn.setFocusTraversalKeysEnabled(false);
	backBtn.setFocusPainted(false);
	backBtn.setBackground(Color.GREEN);

	JButton readyBtn = new JButton("Estoy Listo");
	readyBtn.addActionListener(new ActionListener()
	{
	    public void actionPerformed(ActionEvent e)
	    {
		SoundManager.play("click");

		// Cambiar el estado
		network.send("4");
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

	JLabel p2Lbl = new JLabel("2");
	p2Lbl.setHorizontalAlignment(SwingConstants.CENTER);
	p2Lbl.setForeground(Color.GREEN);
	p2Lbl.setFont(new Font("Tahoma", Font.PLAIN, 18));

	JLabel p3Lbl = new JLabel("3");
	p3Lbl.setHorizontalAlignment(SwingConstants.CENTER);
	p3Lbl.setForeground(Color.GREEN);
	p3Lbl.setFont(new Font("Tahoma", Font.PLAIN, 18));

	JLabel p4Lbl = new JLabel("4");
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

	JCheckBox p4ReadyChkBox = new JCheckBox("");
	p4ReadyChkBox.setEnabled(false);
	p4ReadyChkBox.setForeground(Color.GREEN);
	p4ReadyChkBox.setRolloverEnabled(false);
	p4ReadyChkBox.setRequestFocusEnabled(false);
	p4ReadyChkBox.setFocusPainted(false);
	p4ReadyChkBox.setFocusTraversalKeysEnabled(false);
	p4ReadyChkBox.setFocusable(false);
	p4ReadyChkBox.setBackground(Color.BLACK);

	JCheckBox p3ReadyChkBox = new JCheckBox("");
	p3ReadyChkBox.setEnabled(false);
	p3ReadyChkBox.setForeground(Color.GREEN);
	p3ReadyChkBox.setRolloverEnabled(false);
	p3ReadyChkBox.setRequestFocusEnabled(false);
	p3ReadyChkBox.setFocusPainted(false);
	p3ReadyChkBox.setFocusTraversalKeysEnabled(false);
	p3ReadyChkBox.setFocusable(false);
	p3ReadyChkBox.setBackground(Color.BLACK);

	JCheckBox p2ReadyChkBox = new JCheckBox("");
	p2ReadyChkBox.setEnabled(false);
	p2ReadyChkBox.setForeground(Color.GREEN);
	p2ReadyChkBox.setRolloverEnabled(false);
	p2ReadyChkBox.setRequestFocusEnabled(false);
	p2ReadyChkBox.setFocusPainted(false);
	p2ReadyChkBox.setFocusTraversalKeysEnabled(false);
	p2ReadyChkBox.setFocusable(false);
	p2ReadyChkBox.setBackground(Color.BLACK);

	readyChkBoxs[0] = p2ReadyChkBox;
	readyChkBoxs[1] = p3ReadyChkBox;
	readyChkBoxs[2] = p4ReadyChkBox;

	JLabel mapLbl = new JLabel("Mapa");
	mapLbl.setHorizontalAlignment(SwingConstants.CENTER);
	mapLbl.setForeground(Color.GREEN);
	mapLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
	mapLbl.setBackground(Color.BLACK);

	mapsLbl = new JLabel();

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

	gameModeLbl = new JLabel("");
	gameModeLbl.setVerticalAlignment(SwingConstants.TOP);
	gameModeLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
	gameModeLbl.setHorizontalAlignment(SwingConstants.CENTER);
	gameModeLbl.setForeground(Color.GREEN);

	JLabel modeLbl = new JLabel("Modo de Juego");
	modeLbl.setHorizontalAlignment(SwingConstants.CENTER);
	modeLbl.setForeground(Color.GREEN);
	modeLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
	modeLbl.setBackground(Color.BLACK);

	GroupLayout groupLayout = new GroupLayout(this);
	groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
		.addGroup(groupLayout.createSequentialGroup().addContainerGap().addGroup(groupLayout
			.createParallelGroup(Alignment.LEADING)
			.addGroup(groupLayout.createSequentialGroup()
				.addComponent(backBtn, GroupLayout.PREFERRED_SIZE, 144, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.RELATED, 223, Short.MAX_VALUE)
				.addComponent(readyBtn, GroupLayout.PREFERRED_SIZE, 144, GroupLayout.PREFERRED_SIZE))
			.addComponent(panel, Alignment.TRAILING, 0, 0, Short.MAX_VALUE).addComponent(inputTextField,
				GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE))
			.addPreferredGap(ComponentPlacement.RELATED)
			.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addComponent(p1Lbl, GroupLayout.PREFERRED_SIZE, 308,
							GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(p2Lbl, GroupLayout.PREFERRED_SIZE, 308,
								GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(p2ReadyChkBox, GroupLayout.PREFERRED_SIZE, 21,
								GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(p3Lbl, GroupLayout.PREFERRED_SIZE, 308,
								GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(p3ReadyChkBox, GroupLayout.PREFERRED_SIZE, 21,
								GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(p4Lbl, GroupLayout.PREFERRED_SIZE, 308,
								GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(p4ReadyChkBox))
						.addComponent(gameModeLbl, GroupLayout.DEFAULT_SIZE,
							GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGap(46))
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(mapsLbl, GroupLayout.PREFERRED_SIZE, 116,
							GroupLayout.PREFERRED_SIZE)
						.addComponent(mapLbl, GroupLayout.PREFERRED_SIZE, 95,
							GroupLayout.PREFERRED_SIZE))
					.addGap(147))
				.addGroup(
					groupLayout
						.createSequentialGroup().addComponent(modeLbl,
							GroupLayout.PREFERRED_SIZE, 215, GroupLayout.PREFERRED_SIZE)
						.addGap(100)))));
	groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
		.addGroup(groupLayout.createSequentialGroup()
			.addGroup(groupLayout
				.createParallelGroup(Alignment.TRAILING)
				.addGroup(
					Alignment.LEADING,
					groupLayout.createSequentialGroup().addContainerGap()
						.addComponent(panel, GroupLayout.DEFAULT_SIZE, 488, Short.MAX_VALUE)
						.addGap(11)
						.addComponent(inputTextField, GroupLayout.PREFERRED_SIZE, 34,
							GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup().addGap(22)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(p1Lbl, GroupLayout.PREFERRED_SIZE, 26,
								GroupLayout.PREFERRED_SIZE)
							.addGap(18).addComponent(p2Lbl, GroupLayout.PREFERRED_SIZE, 26,
								GroupLayout.PREFERRED_SIZE))
						.addComponent(p2ReadyChkBox, GroupLayout.PREFERRED_SIZE, 21,
							GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(p3Lbl, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 26,
							GroupLayout.PREFERRED_SIZE)
						.addComponent(p3ReadyChkBox, Alignment.TRAILING,
							GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(p4Lbl, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 26,
							GroupLayout.PREFERRED_SIZE)
						.addComponent(p4ReadyChkBox, Alignment.TRAILING))
					.addGap(59)
					.addComponent(mapLbl, GroupLayout.PREFERRED_SIZE, 27,
						GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(mapsLbl, GroupLayout.PREFERRED_SIZE, 116,
						GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(modeLbl, GroupLayout.PREFERRED_SIZE, 27,
						GroupLayout.PREFERRED_SIZE)
					.addGap(24).addComponent(gameModeLbl, GroupLayout.PREFERRED_SIZE, 80,
						GroupLayout.PREFERRED_SIZE)))
			.addPreferredGap(ComponentPlacement.UNRELATED)
			.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
				.addComponent(backBtn, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
				.addComponent(readyBtn, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE))
			.addGap(32)));
	setLayout(groupLayout);
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
		    textArea.append(command.substring(1, command.length()) + "\n");
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
		case '5':
		    kicked();
		    break;
		case '6':
		    updateMap(command.charAt(1));
		    break;
		case '7':
		    updateMode(command.substring(1, command.length()));
		    break;
		case '8':
		    serverClosed();
		    break;
		case '9':
		    textArea.append("El juego esta por comenzar...\n");
		    startGame(command.substring(1, command.length()));
		default:
		    break;
		}

	    }
	});
    }

    protected void kicked()
    {
	SoundManager.play("click");
	// Cerrar la conexion
	network.close();
	mainWindow.openOnline();

	ErrorDialog errorDialog = new ErrorDialog(mainWindow, 4);
	errorDialog.setVisible(true);
    }

    protected void serverClosed()
    {
	SoundManager.play("click");
	// Cerrar la conexion
	network.close();
	mainWindow.openOnline();

	ErrorDialog errorDialog = new ErrorDialog(mainWindow, 5);
	errorDialog.setVisible(true);
    }

    protected void startGame(String data)
    {
	String[] gameModeData = new String[3];
	gameModeData = data.split("\\|");

	GameMode gameMode = new GameMode(gameModeData[0], Integer.parseInt(gameModeData[1]),
		Integer.parseInt(gameModeData[2]));

	mainWindow.startOnlineGame(this.network, mapID, gameMode);
    }

    private void updatePlayers(int mode, String player)
    {
	// Agregar jugadores
	if (mode == 1)
	{
	    userNames.add(player);
	    usersReady.add(false);
	}

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
		    }

		} else
		{
		    playersLbl[i].setText("");

		    if (i > 0)
		    {
			readyChkBoxs[i - 1].setVisible(false);
			readyChkBoxs[i - 1].setSelected(false);
		    }
		}
	    }
	}

	if (mode == 3)
	{
	    int index = userNames.indexOf(player);
	    usersReady.set(index, !usersReady.get(index));
	    readyChkBoxs[index - 1].setSelected(usersReady.get(index));
	}
    }

    private void updateMap(char option)
    {
	this.mapID = Character.getNumericValue(option);
	mapsLbl.setIcon(new ImageIcon(maps[mapID]));
    }

    private void updateMode(String data)
    {
	gameModeLbl.setText(data);
    }
}