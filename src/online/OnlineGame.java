package online;

import java.util.Observable;
import java.util.Observer;
import org.json.JSONObject;
import consumables.Consumable;
import menus.MainWindow;
import snake.GameMode;
import snake.Snake;
import sound_engine.SoundManager;
import utils.JsonUtils;
import utils.Timer;

//Implementa la interfaz Runnable para utilizar Threads
public class OnlineGame implements Runnable, Observer
{
    // Thread donde correr el juego
    private Thread thread;

    // Menu Pricipal
    MainWindow mainWindow;

    // Renderer encargado de renderizar
    private OnlineRenderer renderer;

    // Estado del juego
    private boolean running = true;

    // Variables Online
    private Snake[] players = null;
    private Consumable fruit = null;
    private Timer timer = null;
    private boolean[] sounds = null;

    private NetworkHandler networkHandler = null;
    private GameMode gameMode = null;

    public OnlineGame(MainWindow mainWindow, NetworkHandler networkHandler, int screenSize, int mapId,
	    GameMode gameMode)
    {
	this.mainWindow = mainWindow;
	this.networkHandler = networkHandler;
	this.networkHandler.addObserver(this);

	this.gameMode = gameMode;

	renderer = new OnlineRenderer(networkHandler, screenSize, mapId, gameMode);

	loadSounds();
    }

    private void loadSounds()
    {
	SoundManager.loadFromProject("background", "background.wav");
	SoundManager.loadFromProject("gameover", "gameover.wav");
	SoundManager.loadFromProject("eat", "eat.wav");
	SoundManager.loadFromProject("playereat", "playereat.wav");
	SoundManager.loadFromProject("hit", "hit.wav");
    }

    // Inicio del Thread pasandole el objeto Game.
    public synchronized void start()
    {
	thread = new Thread(this);
	thread.start();

	// Iniciamos la musica de fondo del juego en modo Loop
	SoundManager.play("background", true);
    }

    // Run se ejecuta luego de iniciar un Thread
    public void run()
    {
	long previous = System.nanoTime();
	double lag = 0.0;
	double frameNs = 1000000000 / 12;

	while (running && networkHandler.isConnected())
	{
	    long current = System.nanoTime();
	    long elapsed = current - previous;
	    previous = current;
	    lag += elapsed;

	    if (lag >= frameNs)
	    {
		if (running)
		{
		    if (players != null && fruit != null && timer != null && sounds != null)
			renderer.draw(players, fruit, timer, sounds);

		    lag -= frameNs;
		}
	    }
	}

	if (!running && networkHandler.isConnected())
	{
	    // GAME OVER
	    networkHandler.send("1");

	    SoundManager.stop("background");
	    OnlineGameOver gameOver = new OnlineGameOver(players, gameMode, renderer, this.mainWindow);
	    gameOver.setLocationRelativeTo(renderer.getFrame());
	    gameOver.setVisible(true);
	}
	// Desconexion
	else
	{
	    renderer.dispose();
	}
    }

    // Join espera a que el Thread muera.
    public synchronized void stop()
    {
	try
	{
	    thread.join();
	} catch (InterruptedException e)
	{
	    e.printStackTrace();
	}
    }

    @Override
    public void update(Observable o, Object arg)
    {
	final String data = arg.toString();

	if (data.charAt(0) == 'G')
	{
	    running = false;
	}

	// Recibir un paquete Json y transformarlo a las variables necesarias
	if (data.charAt(0) == 'J')
	{
	    JSONObject jsonObject = new JSONObject(data.substring(1));

	    timer = JsonUtils.fromJsonToTimer(jsonObject);
	    fruit = JsonUtils.fromJsonToFruit(jsonObject);
	    sounds = JsonUtils.fromJsonToSounds(jsonObject);
	    players = JsonUtils.fromJsonToSnake(jsonObject);
	}
    }
}