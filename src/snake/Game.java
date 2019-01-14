package snake;

import maps.GameMap;
import maps.MapOne;
import maps.MapTwo;
import maps.SimpleMap;
import menus.GameOverDialog;
import menus.MainWindow;
import sound_engine.SoundManager;

//Implementa la interfaz Runnable para utilizar Threads
public class Game implements Runnable
{
    // Menu Pricipal
    MainWindow mainWindow;

    // Thread donde correr el juego
    private Thread thread;

    // Renderer encargado de renderizar
    private Renderer renderer;

    // Objeto donde vive la logica del juego
    private GameLogic gameLogic;

    // Mapa del juego
    private GameMap map;

    // -----------------------------------------------------------------
    // Input del teclado
    private Input inputManager;
    // ------------------------------------------------------------------

    // Constructor de la clase, preparamos el JFrame con el Canvas, el Input
    // y seteamos las variables necesarias
    public Game(int numberOfPlayers, int mapId, int screenSize, int gameSpeed, MainWindow mainWindow,
	    String[] playerNames, boolean[] bots, GameMode mode)
    {
	this.mainWindow = mainWindow;
	init(numberOfPlayers, mapId, screenSize, gameSpeed, playerNames, bots, mode);
    }

    private void init(int numberOfPlayers, int mapId, int screenSize, int gameSpeed, String[] playerNames,
	    boolean[] bots, GameMode mode)
    {
	switch (mapId)
	{
	case 0:
	    map = new GameMap(false);
	    break;

	case 1:
	    map = new SimpleMap();
	    break;

	case 2:
	    map = new MapOne();
	    break;

	case 3:
	    map = new MapTwo();
	    break;

	default:
	    map = new GameMap(false);
	    break;
	}

	gameLogic = new GameLogic(numberOfPlayers, map, playerNames, bots, mode);
	inputManager = new Input(gameLogic.players, gameLogic);
	renderer = new Renderer(screenSize, inputManager, gameLogic.timer, mapId);
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

	// Game loop, siempre corriendo hasta que se de GameOver
	while (gameLogic.isRunning())
	{
	    long current = System.nanoTime();
	    long elapsed = current - previous;
	    previous = current;

	    lag += elapsed;

	    if (lag >= frameNs)
	    {
		// Manejar la logica
		if (gameLogic.isRunning())
		{
		    // double updateTime = System.nanoTime();
		    gameLogic.update(); // 0.05 ms antes - 0.005 ms ahora
		    // updateTime = System.nanoTime() - updateTime;
		    // System.out.println("Update Time: " + updateTime / 1e6 + " ms.");

		    // double renderTime = System.nanoTime();
		    renderer.draw(gameLogic.players, gameLogic.fruit, map, gameLogic.gameMode); // Entre 1 - 0.8 ms
		    // renderTime = System.nanoTime() - renderTime;
		    // System.out.println("Render Time: " + renderTime / 1e6 + " ms.");

		    lag -= frameNs;
		}
	    }
	}

	if (!gameLogic.isRunning())
	{
	    SoundManager.stop("background");

	    GameOverDialog gameOver = new GameOverDialog(gameLogic.players, gameLogic.gameMode, renderer,
		    this.mainWindow);
	    gameOver.setLocationRelativeTo(renderer.getFrame());
	    gameOver.setVisible(true);
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
}