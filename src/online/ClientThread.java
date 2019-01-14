package online;

import java.io.BufferedReader;
//import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

import org.json.JSONObject;

import snake.GameMode;

public class ClientThread extends Thread
{
    protected String userName = "";
    private Socket clientSocket = null;
    private int index = -1;
    private boolean ready = false;

    private BufferedReader is = null;
    private PrintStream os = null;

    private ClientThread[] threads = null;
    private int maxClientsCount = 0;

    private int mapID = 0;
    private String gameModeStr = null;
    private GameMode gameMode = null;

    private boolean room = true;
    private boolean change = false;

    protected Queue<Character> movements = null;

    public ClientThread(Socket clientSocket, ClientThread[] threads, int index)
    {
	this.clientSocket = clientSocket;
	this.threads = threads;
	this.index = index;
	maxClientsCount = threads.length;

	// Solo el server tiene gameMode iniciado
	if (index == 0)
	{
	    gameModeStr = "SUPERVIVENCIA";
	}

	movements = new LinkedList<Character>();
    }

    public void run()
    {
	int maxClientsCount = this.maxClientsCount;
	ClientThread[] threads = this.threads;

	try
	{
	    is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	    os = new PrintStream(clientSocket.getOutputStream());

	    // Lo primero que manda cada usuario es su userName para ser seteado ya que
	    // viene
	    // desde la interface grafica
	    userName = is.readLine();

	    synchronized (this)
	    {
		for (int i = 0; i < maxClientsCount; i++)
		{
		    if (threads[i] != null && i != index)
		    {
			// 2 - Le avisamos a todos los clientes (menos yo mismo)
			// que me agreguen a la lista de usuarios conectados
			threads[i].os.println("2" + userName + index);
			threads[i].os.println("1" + userName + " se unio a la sala.");

			// A la vez, yo voy agregando a los demas a mi lista de conectados
			threads[index].os.println("2" + threads[i].userName + threads[i].index);

			// 4 - Recibo el estado de "estoy listo" para actualizar la interfaz
			// (para los clientes usuarios, el host siempre lo tiene correctamente)
			if (threads[i].getReadyState())
			{
			    threads[index].os.println("4" + threads[i].userName + threads[i].index);
			}
		    }
		}

		if (index != 0)
		{
		    // Recibo el mapa actual
		    // Threads[0] es el Host(ADMIN)
		    threads[index].os.println("6" + threads[0].getMap());

		    // Recibo el game mode
		    threads[index].os.println("7" + threads[0].getMode());
		}

		// Me agrego a mi mismo a la lista de conectados
		threads[index].os.println("2" + userName + index);
		threads[index].os.println("1" + "Te has unido a la sala.");
	    }

	    String line = "";

	    // Mientras este conectado...
	    while (line != null)
	    {
		line = is.readLine();

		synchronized (this)
		{
		    for (int i = 0; i < maxClientsCount; i++)
		    {
			if (threads[i] != null && threads[i].userName != null && line != null)
			{
			    if (room)
			    {
				// 1 significa mensaje
				if (line.charAt(0) == '1')
				{
				    threads[i].os.println("1<" + userName + ">: " + line.substring(1));
				}

				// 4 es cambio de estado "ready"
				if (line.charAt(0) == '4')
				{
				    // Le aviso a todos que cambie de estado
				    threads[i].os.println("4" + userName + index);

				    // Y me cambio a mi mismo (a nivel red - no interfaz)
				    // (para que puedan pedir mi estado los clientes que se conectan luego)
				    if (i == index)
					ready = !ready;
				}

				// 5 Kicked
				if (line.charAt(0) == '5')
				{
				    if (line.substring(1).equals(threads[i].userName + threads[i].index))
				    {
					threads[i].os.println("5");
				    } else
				    {
					threads[i].os.println("1" + line.substring(1, line.length() - 1)
						+ " fue kickeado por el administrador.");
				    }
				}

				// 6 Cambio de mapa
				if (line.charAt(0) == '6')
				{
				    if (index == 0)
				    {
					this.mapID = Character.getNumericValue(line.charAt(1));
				    }

				    threads[i].os.println(line);
				}

				// 7 Cambio de modo
				if (line.charAt(0) == '7')
				{
				    if (index == 0)
				    {
					this.gameModeStr = line.substring(1);
				    }

				    threads[i].os.println(line);
				}

				if (line.charAt(0) == '8')
				{
				    threads[i].os.println(line);
				}

				// 9 Empezar el juego
				if (line.charAt(0) == '9')
				{
				    threads[i].os.println(line);
				    threads[i].change = true;

				    // Host
				    if (index == 0 && i == 0)
				    {
					String[] gameModeData = new String[3];
					gameModeData = line.substring(1).split("\\|");

					gameMode = new GameMode(gameModeData[0], Integer.parseInt(gameModeData[1]),
						Integer.parseInt(gameModeData[2]));
				    }
				}
			    }
			}
		    }

		    if (!room)
		    {
			movements.add(line.charAt(0));
		    }

		    if (change)
		    {
			room = !room;
			change = !change;
		    }
		}

	    }

	    // Me desconecte...
	    synchronized (this)
	    {
		for (int i = 0; i < maxClientsCount; i++)
		{
		    if (threads[i] != null && threads[i] != this && threads[i].userName != null)
		    {
			threads[i].os.println("3" + userName + index);
			threads[i].os.println("1" + userName + " abandono la sala.");
		    }
		}
	    }

	    synchronized (this)
	    {
		for (int i = 0; i < maxClientsCount; i++)
		{
		    if (threads[i] == this)
		    {
			threads[i] = null;
			System.out.println("Thread i = " + i + " left.");
		    }
		}
	    }

	    is.close();
	    os.close();
	    clientSocket.close();

	} catch (IOException e)
	{
	    System.out.println("Un Cliente se desconecto de manera abrupta");
	    System.out.println(e);

	    synchronized (this)
	    {
		for (int i = 0; i < maxClientsCount; i++)
		{
		    if (threads[i] != null && threads[i] != this && threads[i].userName != null)
		    {
			threads[i].os.println("3" + userName + index);
			threads[i].os.println("1" + userName + " abandono la sala.");
		    }
		}
	    }

	    synchronized (this)
	    {
		for (int i = 0; i < maxClientsCount; i++)
		{
		    if (threads[i] == this)
		    {
			threads[i] = null;
			System.out.println("Thread i = " + i + " left.");
		    }
		}
	    }

	    try
	    {
		is.close();
		os.close();
		clientSocket.close();
	    } catch (IOException e1)
	    {
		System.out.println(e1);
	    }

	}
    }

    public String getMode()
    {
	return gameModeStr;
    }

    public synchronized int getMap()
    {
	return mapID;
    }

    public synchronized GameMode getGameMode()
    {
	return gameMode;
    }

    public synchronized boolean getReadyState()
    {
	return ready;
    }

    protected synchronized void send(JSONObject data)
    {
	this.os.println("J" + data.toString());
    }

    protected synchronized void gameOver()
    {
	change = true;
	this.os.println("G");
    }

    protected synchronized char getMovement()
    {
	if (movements.peek() != null)
	{
	    return movements.poll();
	} else
	{
	    return 'E';
	}
    }

    protected synchronized void resetMovements()
    {
	movements.clear();
    }

    protected synchronized String getUserName()
    {
	return this.userName;
    }

}