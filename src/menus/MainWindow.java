package menus;

import java.awt.EventQueue;
import java.awt.Image;
import java.io.IOException;
import java.net.InetAddress;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import online.GameServer;
import online.NetworkHandler;
import online.OnlineGame;
import online.Server;
import snake.Game;
import snake.GameMode;
import sound_engine.SoundManager;

public class MainWindow extends JFrame
{
    private static final long serialVersionUID = 1L;
    private static Image snakeAppIcon;

    protected JPanel mainPanel;
    protected JPanel optionsPanel;
    protected JPanel gameConfigPanel;
    protected JPanel howToPanel;
    protected JPanel onlinePanel;
    protected JPanel hostPanel;
    protected JPanel joinPanel;
    protected JPanel clientPanel;

    private Server server = null;
    private GameServer gameServer = null;

    private int screenSize = 600;

    public static void main(String[] args)
    {
	EventQueue.invokeLater(new Runnable()
	{
	    public void run()
	    {
		try
		{
		    MainWindow frame = new MainWindow();

		    try
		    {
			snakeAppIcon = ImageIO.read(getClass().getClassLoader().getResource("snake.png"));
		    } catch (IOException e1)
		    {
			e1.printStackTrace();
		    }

		    frame.setIconImage(snakeAppIcon);
		    frame.setLocationRelativeTo(null);
		    frame.setVisible(true);
		} catch (Exception e)
		{
		    e.printStackTrace();
		}
	    }
	});
    }

    public MainWindow()
    {
	SoundManager.loadFromProject("background", "background.wav");
	SoundManager.loadFromProject("menu", "menu.wav");
	SoundManager.loadFromProject("click", "click.wav");
	SoundManager.play("menu", true);
	
	//SoundManager.play("menu");

	setResizable(false);
	setTitle("Snake");
	setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	setBounds(0, 0, 800 + 106, 629);

	mainPanel = new MainPanel(this);
	optionsPanel = new OptionsPanel(this);
	gameConfigPanel = new GameConfigPanel(this);
	howToPanel = new HowToPanel(this);
	onlinePanel = new OnlinePanel(this);
	joinPanel = new JoinPanel(this);

	setContentPane(mainPanel);
    }

    protected void openGameConfig()
    {
	mainPanel.setVisible(false);
	setContentPane(gameConfigPanel);
	gameConfigPanel.setVisible(true);
    }

    protected void openOptions()
    {
	mainPanel.setVisible(false);
	setContentPane(optionsPanel);
	optionsPanel.setVisible(true);
    }

    protected void openHowTo()
    {
	mainPanel.setVisible(false);
	setContentPane(howToPanel);
	howToPanel.setVisible(true);
    }

    public void openOnline()
    {
	setContentPane(mainPanel);
	mainPanel.setVisible(true);
	mainPanel.setVisible(false);

	setContentPane(onlinePanel);
	onlinePanel.setVisible(true);
    }

    protected void joinGame(NetworkHandler access, String userName)
    {
	clientPanel = new ClientPanel(this, access, userName);

	setContentPane(mainPanel);
	mainPanel.setVisible(true);
	mainPanel.setVisible(false);
	clientPanel.setVisible(true);
	setContentPane(clientPanel);
    }

    protected void openJoinPanel(String userName)
    {
	((JoinPanel) joinPanel).setUserName(userName);

	setContentPane(mainPanel);
	mainPanel.setVisible(true);
	joinPanel.setVisible(true);
	setContentPane(joinPanel);
    }

    protected void openHostPanel(String userName) throws Exception
    {
	// Crear el server
	startServer();

	// Crear el acceso a la red
	NetworkHandler networkHandler = new NetworkHandler(this);

	try
	{
	    // Me conecte correctamente al server...
	    if (networkHandler.InitSocket(InetAddress.getByName("localhost"), 9995) == true)
	    {
		// Crear el hostPanel
		hostPanel = new HostPanel(this, networkHandler, server, gameServer, userName);

		setContentPane(mainPanel);
		mainPanel.setVisible(true);
		mainPanel.setVisible(false);
		hostPanel.setVisible(true);
		setContentPane(hostPanel);
	    } else
	    {
		ErrorDialog errorDialog = new ErrorDialog(this, 3);
		errorDialog.setVisible(true);
	    }

	} catch (IOException ex)
	{
	    System.out.println(ex);
	}
    }

    protected void returnToMainMenu()
    {
	setContentPane(mainPanel);
	mainPanel.setVisible(true);
    }

    protected void resize(int option)
    {
	switch (option)
	{
	case 0:
	    setSize(800 + 106, 600 + 29);
	    screenSize = 600;
	    setLocationRelativeTo(null);
	    optionsPanel.setBorder(null);
	    mainPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
	    gameConfigPanel.setBorder(new EmptyBorder(15, 55, 0, 0));
	    howToPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
	    onlinePanel.setBorder(null);
	    joinPanel.setBorder(null);
	    ((HowToPanel) howToPanel).setId(0);
	    break;
	case 1:
	    setSize(1000 + 106, 800 + 29);
	    screenSize = 800;
	    setLocationRelativeTo(null);
	    optionsPanel.setBorder(new EmptyBorder(100, 100, 0, 0));
	    mainPanel.setBorder(new EmptyBorder(100, 100, 0, 0));
	    gameConfigPanel.setBorder(new EmptyBorder(100 + 15, 100 + 55, 0, 0));
	    howToPanel.setBorder(new EmptyBorder(100, 100, 0, 0));
	    onlinePanel.setBorder(new EmptyBorder(0, 100, 0, 0));
	    joinPanel.setBorder(new EmptyBorder(0, 100, 0, 0));
	    ((HowToPanel) howToPanel).setId(1);
	    break;
	case 2:
	    setSize(1200 + 106, 1000 + 29);
	    screenSize = 1000;
	    setLocationRelativeTo(null);
	    optionsPanel.setBorder(new EmptyBorder(200, 200, 0, 0));
	    mainPanel.setBorder(new EmptyBorder(200, 200, 0, 0));
	    gameConfigPanel.setBorder(new EmptyBorder(200 + 15, 200 + 55, 0, 0));
	    howToPanel.setBorder(new EmptyBorder(200, 200, 0, 0));
	    onlinePanel.setBorder(new EmptyBorder(0, 200, 0, 0));
	    joinPanel.setBorder(new EmptyBorder(0, 200, 0, 0));
	    ((HowToPanel) howToPanel).setId(2);
	    break;
	default:
	    break;
	}
    }

    protected void exit()
    {
	SoundManager.stopAll();
	this.dispose();
	System.exit(EXIT_ON_CLOSE);
    }

    protected void startGame(int numberOfPlayers, int mapId, String[] playerNames, boolean[] bots, GameMode mode)
    {
	SoundManager.stopAll();
	this.setVisible(false);

	Game game = new Game(numberOfPlayers, mapId, screenSize, 1, (MainWindow) this, playerNames, bots, mode);
	game.start();
    }

    protected void startOnlineGame(NetworkHandler networkHandler, int mapId, GameMode gameMode)
    {
	SoundManager.stopAll();
	this.setVisible(false);

	OnlineGame game = new OnlineGame((MainWindow) this, networkHandler, this.screenSize, mapId, gameMode);
	game.start();
    }

    protected void startServer() throws Exception
    {
	if (server == null)
	{
	    server = new Server();
	    gameServer = new GameServer(server.getThreads());
	    
	    server.start();
	}	
    }

    protected void stopServer()
    {
	if (server != null)
	{
	    server.stop();
	    server = null;
	}

	gameServer = null;
    }
}
