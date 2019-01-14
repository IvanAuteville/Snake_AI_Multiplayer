package online;

import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONObject;
import consumables.Consumable;
import consumables.FruitFactory;
import maps.GameMap;
import snake.GameMode;
import snake.Snake;
import utils.JSONSerializable;
import utils.JsonUtils;
import utils.Timer;
import utils.Vector2;

public class OnlineGameLogic implements JSONSerializable
{
    // Condicion de corte
    private boolean running = false;

    // Todos los jugadores dieron un input inicial
    private boolean started = false;
    private boolean paused = false;

    // Jugadores muertos
    private int deadPlayers = 0;

    private FruitFactory fruitFactory;
    protected Consumable fruit;

    protected GameMode gameMode;
    protected Timer timer = new Timer(0);

    // Mapa que se va a recibir para jugar
    private GameMap map;

    private boolean[] sounds = new boolean[3];

    // Posiciones predefinidas para spawnear (EVITAMOS CALCULOS EXTRAÑOS)
    private Vector2[] spawnPositions =
    { (new Vector2(2, 2)), (new Vector2(47, 47)), (new Vector2(47, 2)), (new Vector2(2, 47)) };

    // Array de jugadores
    protected Snake[] players;

    private ClientThread[] threads;

    private HashMap<String, Integer> playerIndex;

    public OnlineGameLogic(ClientThread[] threads, int totalPlayers, GameMap map, String[] playerNames, boolean[] bots,
	    GameMode gameMode) throws Exception
    {
	this.map = map;
	this.gameMode = gameMode;
	this.threads = threads;
	this.fruitFactory = new FruitFactory();
	this.playerIndex = new HashMap<>();

	// SNAKES --------------------------------------------------
	this.players = new Snake[totalPlayers];
	for (int i = 0; i < totalPlayers; i++)
	{
	    this.players[i] = new Snake(this.spawnPositions[i], i + 1, playerNames[i], map);
	    playerIndex.put(playerNames[i], i);

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

	updateClients();
    }

    // Logica del juego
    public void update() throws Exception
    {
	if (!started)
	{
	    updateInput();

	    started = allPlayersReady();
	}

	if (started)
	{
	    if (!paused)
	    {
		// Input update
		updateInput();

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
			if (OnlineCollision.mapCollision(this.players[i], this.map, sounds))
			{
			    deadPlayers++;
			} else if (OnlineCollision.selfCollision(this.players[i], map, sounds))
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
			if (OnlineCollision.fruitCollision(this.players[i], this.fruit, map, sounds))
			{
			    fruit = fruitFactory.makeFruit();
			    fruit.spawn(map);

			    break;
			}
		    }
		}

		// Colisiones entre serpientes
		deadPlayers += OnlineCollision.snakesCollision(this.players, map, sounds);

		// Shifteo de posiciones internas de las serpientes
		for (int i = 0; i < this.players.length; i++)
		{
		    if (players[i].isAlive())
		    {
			this.players[i].shiftPositionsWithMap(map);
		    }
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

		// Envio de JSON
		updateClients();

		// Reset de sonidos
		for (int i = 0; i < sounds.length; i++)
		{
		    sounds[i] = false;
		}
	    }
	}

    }

    private void updateInput()
    {
	for (int i = 0; i < threads.length; i++)
	{
	    if (threads[i] != null)
	    {
		char move = threads[i].getMovement();
		String aux = threads[i].getUserName();

		if (i == 0)
		{
		    aux = aux.substring(0, aux.indexOf("(") - 1);
		}

		int index = playerIndex.get(aux);

		switch (move)
		{
		case '°':
		    if (i == 0)
		    {
			running = false;
		    }
		    break;
		case 'W':
		    players[index].moveUp();
		    break;
		case 'S':
		    players[index].moveDown();
		    break;
		case 'D':
		    players[index].moveRight();
		    break;
		case 'A':
		    players[index].moveLeft();
		    break;
		default:
		    break;
		}

		threads[i].resetMovements();
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

    @Override
    public JSONObject toJson() throws Exception
    {
	String[] atributes =
	{ "players", "fruit", "timer", "sounds" };

	JSONObject data = JsonUtils.toJson(this, atributes);

	JSONArray arr = new JSONArray();
	for (Snake snake : players)
	{
	    arr.put(snake.toJson());
	}
	data.put("players", arr);

	arr = new JSONArray();
	for (Boolean sound : sounds)
	{
	    arr.put(sound);
	}
	data.put("sounds", arr);

	data.put("fruit", fruit.toJson());

	data.put("timer", timer.toJson());

	return data;
    }

    private void updateClients() throws Exception
    {
	// SERVER
	JSONObject data = this.toJson();

	for (ClientThread client : this.threads)
	{
	    if (client != null)
	    {
		client.send(data);
	    }
	}
    }
}