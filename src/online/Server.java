package online;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable
{
    // El Thread del server.
    private Thread thread = null;

    // Señal de corte
    private boolean online = true;

    // El socket del server.
    private static ServerSocket serverSocket = null;

    // El socket cliente.
    private static Socket clientSocket = null;

    // Las salas son de un maximo de 4 jugadores
    private static final int maxClientsCount = 4;
    private static final ClientThread[] threads = new ClientThread[maxClientsCount];

    public void closeServerSocket()
    {
	if (clientSocket != null && !clientSocket.isClosed())
	{
	    try
	    {
		clientSocket.close();
	    } catch (IOException e)
	    {
		System.out.println(e);
	    }
	}

	if (serverSocket != null && !serverSocket.isClosed())
	{
	    try
	    {
		serverSocket.close();
	    } catch (IOException e)
	    {
		e.printStackTrace();
	    }
	}
    }

    public synchronized void start()
    {
	thread = new Thread(this);
	thread.start();
    }

    @Override
    public void run()
    {
	// Puerto usado por el servidor.
	int portNumber = 9995;

	try
	{
	    serverSocket = new ServerSocket(portNumber);
	} catch (IOException e)
	{
	    System.out.println(e);
	}

	System.out.println("SERVER INICIADO... \n");

	while (online)
	{
	    try
	    {
		clientSocket = serverSocket.accept();

		int i = 0;
		for (i = 0; i < maxClientsCount; i++)
		{
		    if (getThreads()[i] == null)
		    {

			PrintStream os = new PrintStream(clientSocket.getOutputStream());

			// Confirmacion de conexion
			os.write(99);
			os.flush();
			// ----------------

			(getThreads()[i] = new ClientThread(clientSocket, getThreads(), i)).start();
			System.out.println("Thread i = " + i + " joined.");
			break;
		    }
		}

		if (i == maxClientsCount)
		{
		    clientSocket.close();
		}

	    } catch (Exception e)
	    {
		System.out.println(e);
	    }
	}
    }

    public synchronized void stop()
    {
	try
	{
	    online = false;
	    closeServerSocket();
	    thread.join();
	    System.out.println("Server closed");
	} catch (InterruptedException e)
	{
	    System.out.println(e);
	}
    }

    public ClientThread[] getThreads()
    {
	return threads;
    }
}