package online;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import consumables.Consumable;
import consumables.Fruit;
import maps.GameMap;
import maps.MapOne;
import maps.MapTwo;
import maps.SimpleMap;
import snake.GameMode;
import snake.Snake;
import sound_engine.SoundManager;
import utils.Timer;
import utils.Vector2;

public class OnlineRenderer
{
    // Conexion
    NetworkHandler networkHandler = null;

    // Images
    private static Image snakeAppIcon;
    private static Image block;
    private static Image background;
    private static Image apple;
    private static Image banana;
    private static Image cherry;
    private static Image[] blocks;
    // Ventana y Canvas donde dibujar
    private JFrame frame;
    private JPanel panel;
    Canvas canvas;

    private int screenSize;
    private int grid;

    // Parte grafica para dibujar en el Canvas
    private BufferStrategy bs;
    private Graphics g;

    // Colores de los jugadores
    private ArrayList<Color> colors = new ArrayList<Color>();
    private boolean setPlayers = false;

    private GameMap map = null;
    private GameMode gameMode = null;

    public OnlineRenderer(NetworkHandler networkHandler, int screenSize, int mapId, GameMode gameMode)
    {
	this.networkHandler = networkHandler;
	this.gameMode = gameMode;

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

	this.screenSize = screenSize;
	this.grid = screenSize / 50;

	try
	{
	    blocks = new Image[3];
	    blocks[0] = ImageIO.read(getClass().getClassLoader().getResource("block_1.png"));
	    blocks[1] = ImageIO.read(getClass().getClassLoader().getResource("block_2.png"));
	    blocks[2] = ImageIO.read(getClass().getClassLoader().getResource("block_3.png"));

	    if (mapId > 0)
	    {
		block = blocks[mapId - 1];
	    } else
	    {
		block = null;
	    }

	    background = ImageIO.read(getClass().getClassLoader().getResource("background.png"));
	    snakeAppIcon = ImageIO.read(getClass().getClassLoader().getResource("snake.png"));

	    apple = ImageIO.read(getClass().getClassLoader().getResource("apple.png"));
	    banana = ImageIO.read(getClass().getClassLoader().getResource("banana.png"));
	    cherry = ImageIO.read(getClass().getClassLoader().getResource("cherry.png"));
	} catch (IOException e)
	{
	    e.printStackTrace();
	}

	frame = new JFrame("Snake");
	frame.addKeyListener(new KeyAdapter()
	{
	    @Override
	    public void keyPressed(KeyEvent e)
	    {
		sendInput(e);
	    }
	});
	frame.setIconImage(snakeAppIcon);
	frame.setResizable(false);
	frame.setMinimumSize(new Dimension(900, 600));
	frame.setSize(screenSize + 300 + 6, screenSize + 29);
	frame.setLocationRelativeTo(null);
	frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	frame.setVisible(true);

	frame.getContentPane().setLayout(null);

	canvas = new Canvas();
	canvas.setBounds(0, 0, screenSize, screenSize);
	frame.getContentPane().add(canvas);
	canvas.setPreferredSize(new Dimension(screenSize, screenSize));
	canvas.setMaximumSize(new Dimension(screenSize, screenSize));
	canvas.setMinimumSize(new Dimension(screenSize, screenSize));
	canvas.setFocusable(false);

	panel = new OnlineInfoPanel(screenSize);
	panel.setBounds(screenSize, 0, 300, screenSize);
	frame.getContentPane().add(panel);

	// Colores de los jugadores
	colors.add(new Color(0, 255, 0)); // VERDE CLARO
	colors.add(new Color(51, 204, 255)); // CELESTE
	colors.add(new Color(255, 51, 204)); // ROSADO
	colors.add(new Color(255, 102, 0)); // NARANJA
	Collections.shuffle(colors);
    }

    protected void sendInput(KeyEvent e)
    {
	if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
	{
	    networkHandler.send("°");
	}

	if (e.getKeyCode() == KeyEvent.VK_W)
	{
	    networkHandler.send("W");
	}

	if (e.getKeyCode() == KeyEvent.VK_S)
	{
	    networkHandler.send("S");
	}

	if (e.getKeyCode() == KeyEvent.VK_A)
	{
	    networkHandler.send("A");
	}

	if (e.getKeyCode() == KeyEvent.VK_D)
	{
	    networkHandler.send("D");
	}
    }

    protected void draw(Snake[] players, Consumable fruit, Timer timer, boolean[] sounds)
    {
	if (!setPlayers)
	{
	    ((OnlineInfoPanel) panel).setInfoAndPlayers(players, colors, this.gameMode);
	    setPlayers = true;
	}

	((OnlineInfoPanel) panel).setOnlineScores(players, this.gameMode, timer);

	bs = canvas.getBufferStrategy();

	if (bs == null)
	{
	    canvas.createBufferStrategy(2);
	    return;
	}

	g = bs.getDrawGraphics();

	// BACKGROUND ---------------------------------
	g.drawImage(background, 0, 0, screenSize, screenSize, null);
	// --------------------------------------------

	// MAP ----------------------------------------
	if (this.map.hasWalls())
	{
	    renderMap(this.map);
	}
	// --------------------------------------------

	// CONSUMABLES -------------------------------
	if (((Fruit) fruit).getName().equals("apple"))
	{
	    g.drawImage(apple, fruit.getPosition().getX() * grid, fruit.getPosition().getY() * grid, grid, grid, null);
	} else if (((Fruit) fruit).getName().equals("cherry"))
	{
	    g.drawImage(cherry, fruit.getPosition().getX() * grid, fruit.getPosition().getY() * grid, grid, grid, null);
	} else
	{
	    g.drawImage(banana, fruit.getPosition().getX() * grid, fruit.getPosition().getY() * grid, grid, grid, null);
	}
	// --------------------------------------------

	// SNAKES ------------------------------------
	int colPicker = 0;

	for (Snake snake : players)
	{
	    renderSnake(snake, colors.get(colPicker++));
	}
	// --------------------------------------------

	// Reproduccion de sonidos

	for (int i = 0; i < sounds.length; i++)
	{
	    if (sounds[i] == true)
	    {
		switch (i)
		{
		case 0:
		    SoundManager.play("eat");
		    break;
		case 1:
		    SoundManager.play("playereat");
		    break;
		case 2:
		    SoundManager.play("hit");
		    break;
		default:
		    break;
		}
	    }
	}

	bs.show();
	g.dispose();
    }

    private void renderMap(GameMap map)
    {
	for (Vector2 pos : map.getRenderMap())
	{
	    g.drawImage(block, pos.getX() * grid, pos.getY() * grid, grid, grid, null);
	}
    }

    private void renderSnake(Snake p, Color color)
    {
	if (p.isAlive())
	{
	    for (int i = 1; i < p.getLength(); i++)
	    {
		g.setColor(color);
		g.fillRect(p.getSnakeBody()[i].getX() * grid, p.getSnakeBody()[i].getY() * grid, grid, grid);

		g.setColor(Color.WHITE);
		g.drawRect(p.getSnakeBody()[i].getX() * grid, p.getSnakeBody()[i].getY() * grid, grid, grid);
	    }
	} else
	{
	    // El cuerpo fue comido en su totalidad si es null
	    if (p.getSnakeBody() != null)
	    {
		for (int i = 1; i < p.getLength(); i++)
		{
		    // Esa parte del cuerpo fue comida
		    if (p.getSnakeBody()[i] != null)
		    {
			if (!p.getSnakeBody()[i].equals(new Vector2(999, 999)))
			{
			    g.setColor(color);
			    g.fillOval(p.getSnakeBody()[i].getX() * grid, p.getSnakeBody()[i].getY() * grid,
				    (int) (grid * 0.8), (int) (grid * 0.8));

			    g.setColor(Color.WHITE);
			    g.drawOval(p.getSnakeBody()[i].getX() * grid, p.getSnakeBody()[i].getY() * grid,
				    (int) (grid * 0.8), (int) (grid * 0.8));
			}
		    }

		}
	    }

	}

    }

    public JFrame getFrame()
    {
	return this.frame;
    }

    public void dispose()
    {
	frame.dispose();
    }
}