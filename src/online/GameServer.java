package online;

import java.util.HashMap;
import java.util.Set;
import maps.GameMap;
import maps.MapOne;
import maps.MapTwo;
import maps.SimpleMap;
import snake.GameMode;

public class GameServer implements Runnable
{
    // Thread donde correr el juego
    private Thread thread;

    private OnlineGameLogic gameLogic;
    private GameMap map;

    private ClientThread[] threads;

    public GameServer(ClientThread[] threads) throws Exception
    {
	this.threads = threads;
    }

    private void init() throws Exception
    {
	int mapId = -1;
	synchronized (this)
	{
	    while (mapId == -1)
	    {
		if (threads[0] != null)
		{
		    mapId = threads[0].getMap();
		}
	    }
	}

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

	GameMode gameMode = null;

	synchronized (this)
	{
	    while (gameMode == null)
	    {
		if (threads[0] != null)
		{
		    gameMode = threads[0].getGameMode();
		}
	    }
	}

	int numberOfPlayers = 0;
	HashMap<String, Integer> players = new HashMap<>();

	for (int j = 0; j < threads.length; j++)
	{
	    if (threads[j] != null)
	    {

		if (j == 0)
		{
		    String aux = threads[j].getUserName();
		    aux = aux.substring(0, aux.indexOf("(") - 1);
		    players.put(aux, j);
		} else
		{
		    players.put(threads[j].getUserName(), j);
		}

		numberOfPlayers++;
	    }
	}

	Set<String> playerSet = players.keySet();
	String[] playerNames = new String[playerSet.size()];
	playerNames = playerSet.toArray(playerNames);

	synchronized (this)
	{
	    gameLogic = new OnlineGameLogic(threads, numberOfPlayers, map, playerNames, new boolean[4], gameMode);
	}
    }

    // Inicio del Thread pasandole el objeto Game.
    public synchronized void start() throws Exception
    {
	init();

	thread = new Thread(this);
	thread.start();
    }

    @Override
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
		    try
		    {
			gameLogic.update();
		    } catch (Exception e)
		    {
			e.printStackTrace();
		    }

		    lag -= frameNs;
		}
	    }
	}

	// GAME OVER
	if (!gameLogic.isRunning())
	{
	    for (ClientThread client : this.threads)
	    {
		if (client != null)
		{
		    client.gameOver();
		}
	    }
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