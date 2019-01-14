package online;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Observable;

import menus.ErrorDialog;
import menus.MainWindow;

public class NetworkHandler extends Observable
{
    private Socket socket;
    private OutputStream outputStream;
    private MainWindow mainWindow;
    private boolean connected;

    public NetworkHandler(MainWindow mainWindow)
    {
	this.mainWindow = mainWindow;
    }

    @Override
    public void notifyObservers(Object arg)
    {
	super.setChanged();
	super.notifyObservers(arg);
    }

    public boolean InitSocket(InetAddress server, int port) throws IOException
    {
	socket = new Socket(server, port);
	outputStream = socket.getOutputStream();

	DataInputStream on = new DataInputStream(socket.getInputStream());

	socket.setSoTimeout(1000);
	if (on.read() != 99)
	{
	    connected = false;
	    return false;
	}
	socket.setSoTimeout(0);

	connected = true;

	Thread receivingThread = new Thread()
	{
	    @Override
	    public void run()
	    {
		try
		{
		    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		    String line;

		    while ((line = reader.readLine()) != null)
		    {
			notifyObservers(line);
		    }

		} catch (IOException ex)
		{
		    notifyObservers(ex);
		}
	    }
	};

	receivingThread.start();

	return true;
    }

    private static final String CRLF = "\r\n";

    public void send(String text)
    {
	try
	{
	    outputStream.write((text + CRLF).getBytes());
	    outputStream.flush();
	} catch (IOException ex)
	{
	    connected = false;

	    System.out.println(ex);
	    System.out.println("Se perdio la conexion con el servidor");

	    mainWindow.setVisible(true);
	    mainWindow.openOnline();

	    ErrorDialog errorDialog = new ErrorDialog(mainWindow, 6);
	    errorDialog.setVisible(true);
	}
    }

    public void close()
    {
	try
	{
	    socket.close();
	} catch (IOException ex)
	{
	    notifyObservers(ex);
	}
    }

    public boolean isConnected()
    {
	return connected;
    }
}