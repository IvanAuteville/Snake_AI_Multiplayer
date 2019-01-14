package snake;

import consumables.Consumable;
import consumables.FruitFactory;
import maps.GameMap;
import sound_engine.SoundManager;
import utils.AI;
import utils.Timer;
import utils.Vector2;

public class GameLogic
{
    // Condicion de corte
    private boolean running = false;

    // Todos los jugadores dieron un input inicial
    private boolean started = false;
    private boolean paused = false;

    // Jugadores muertos
    private int deadPlayers = 0;
    private int deadSound = 0;

    private FruitFactory fruitFactory;
    protected Consumable fruit;

    protected GameMode gameMode;
    protected Timer timer;

    // Mapa que se va a recibir para jugar
    private GameMap map;

    // Posiciones predefinidas para spawnear
    private Vector2[] spawnPositions =
    { (new Vector2(2, 2)), (new Vector2(47, 47)), (new Vector2(47, 2)), (new Vector2(2, 47)) };

    // Vector de jugadores
    protected Snake[] players;

    public GameLogic(int totalPlayers, GameMap map, String[] playerNames, boolean[] bots, GameMode gameMode)
    {
	this.map = map;
	this.gameMode = gameMode;
	this.fruitFactory = new FruitFactory();

	// SNAKES --------------------------------------------------
	this.players = new Snake[totalPlayers];
	for (int i = 0; i < totalPlayers; i++)
	{
	    this.players[i] = new Snake(this.spawnPositions[i], i + 1, playerNames[i], map);

	    if (bots[i])
	    {
		this.players[i].bot = true;
		this.players[i].isReady = true;
	    }
	}
	// -------------------------------------------------------------

	// CONSUMABLES ------------------------------------------------
	fruit = fruitFactory.makeFruit();
	fruit.spawn(map);
	// -------------------------------------------------------------

	if (gameMode.gameMode.equals("TIME"))
	{
	    timer = new Timer(gameMode.time);
	}

	running = true;
    }

    // Logica del juego
    public void update()
    {
	if (!started)
	{
	    started = allPlayersReady();

	    if (started)
	    {
		// Calcular para los BOTS
		for (Snake snake : players)
		{
		    if (snake.bot)
		    {
			snake.AIManager = new AI(map, players, snake.getSnakeBody()[0], fruit.getPosition());
		    }
		}
	    }

	}

	if (started)
	{
	    if (!paused)
	    {

		deadSound = deadPlayers;

		// Input update
		for (int i = 0; i < this.players.length; i++)
		{
		    if (players[i].isAlive())
		    {
			this.players[i].updateHeadPosition(map);
		    }
		}

		// Colisiones con el mapa o entre si mismas
		for (int i = 0; i < this.players.length; i++)
		{
		    if (players[i].isAlive())
		    {
			if (Collision.mapCollision(this.players[i], this.map))
			{
			    deadPlayers++;
			} else if (Collision.selfCollision(this.players[i], map))
			{
			    deadPlayers++;
			}
		    }
		}

		// Colisiones con la/s fruta/s
		for (int i = 0; i < this.players.length; i++)
		{
		    if (players[i].isAlive())
		    {
			if (Collision.fruitCollision(this.players[i], this.fruit, map))
			{
			    fruit = fruitFactory.makeFruit();
			    fruit.spawn(map);
			    // fruitRespawned = true;
			    break;
			}
		    }
		}

		// Colisiones entre serpientes
		deadPlayers += Collision.snakesCollision(this.players, map);

		// Shifteo de posiciones internas de las serpientes
		for (int i = 0; i < this.players.length; i++)
		{
		    if (players[i].isAlive())
		    {
			this.players[i].shiftPositionsWithMap(map);
		    }
		}

		if (deadPlayers != deadSound)
		{
		    SoundManager.play("hit");
		}

		switch (gameMode.gameMode)
		{
		case "SURVIVAL":

		    if (deadPlayers == players.length - 1 && players.length > 1)
		    {
			running = false;
		    }

		    break;

		case "SCORE":

		    for (Snake snake : players)
		    {
			if (snake.getScore() >= gameMode.score)
			{
			    running = false;
			    break;
			}
		    }

		    break;

		case "TIME":
		    timer.tick();
		    if (timer.getMinutes() <= 0 && timer.getSeconds() <= 0)
		    {
			running = false;
		    }

		    break;

		default:

		    break;
		}

		if (deadPlayers == players.length)
		{
		    running = false;
		}

		// Recalcular para los BOTS
		for (Snake snake : players)
		{
		    if (snake.bot && snake.isAlive())
		    {
			// Recalcular los caminos
			// long start = System.nanoTime();
			snake.AIManager.recalculate(snake.getSnakeBody()[0], fruit.getPosition());
			// long end = System.nanoTime() - start;
		    }
		}

	    }
	}

    }

    private boolean allPlayersReady()
    {
	for (int i = 0; i < players.length; i++)
	{
	    if (!players[i].isReady)
	    {
		return false;
	    }
	}

	if (gameMode.gameMode.equals("TIME"))
	{
	    timer.start();
	}

	return true;
    }

    public boolean isRunning()
    {
	return running;
    }

    protected void stopRequest()
    {
	running = false;
    }

    protected void pauseRequest()
    {
	paused = !paused;
    }
}