package snake;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Input implements KeyListener
{
    GameLogic gameLogic;

    Snake p1 = null;
    Snake p2 = null;

    Snake p3 = null;
    Snake p4 = null;

    public Input(Snake p1, GameLogic gameLogic)
    {
	this.p1 = p1;
	this.gameLogic = gameLogic;
    }

    public Input(Snake p1, Snake p2, GameLogic gameLogic)
    {
	this.p1 = p1;
	this.p2 = p2;

	this.gameLogic = gameLogic;
    }

    public Input(Snake[] snakes, GameLogic gameLogic)
    {
	if (snakes[0] != null)
	{
	    this.p1 = snakes[0];
	}

	if (snakes.length > 1)
	{
	    if (snakes[1] != null)
	    {
		this.p2 = snakes[1];
	    }
	}

	if (snakes.length > 2)
	{
	    if (snakes[2] != null)
	    {
		this.p3 = snakes[2];
	    }
	}

	if (snakes.length > 3)
	{
	    if (snakes[3] != null)
	    {
		this.p4 = snakes[3];
	    }
	}

	this.gameLogic = gameLogic;
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
	if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
	{
	    gameLogic.stopRequest();
	}

	if (e.getKeyCode() == KeyEvent.VK_P)
	{
	    gameLogic.pauseRequest();
	}

	if (p1 != null && !p1.bot)
	{
	    if (e.getKeyCode() == KeyEvent.VK_W)
	    {
		p1.moveUp();
	    }

	    if (e.getKeyCode() == KeyEvent.VK_S)
	    {
		p1.moveDown();
	    }

	    if (e.getKeyCode() == KeyEvent.VK_A)
	    {
		p1.moveLeft();
	    }

	    if (e.getKeyCode() == KeyEvent.VK_D)
	    {
		p1.moveRight();
	    }
	}

	if (p4 != null && !p4.bot)
	{
	    if (e.getKeyCode() == KeyEvent.VK_UP)
	    {
		p4.moveUp();
	    }

	    if (e.getKeyCode() == KeyEvent.VK_DOWN)
	    {
		p4.moveDown();
	    }

	    if (e.getKeyCode() == KeyEvent.VK_LEFT)
	    {
		p4.moveLeft();
	    }

	    if (e.getKeyCode() == KeyEvent.VK_RIGHT)
	    {
		p4.moveRight();
	    }
	}

	if (p3 != null && !p3.bot)
	{
	    if (e.getKeyCode() == KeyEvent.VK_I)
	    {
		p3.moveUp();
	    }

	    if (e.getKeyCode() == KeyEvent.VK_K)
	    {
		p3.moveDown();
	    }

	    if (e.getKeyCode() == KeyEvent.VK_J)
	    {
		p3.moveLeft();
	    }

	    if (e.getKeyCode() == KeyEvent.VK_L)
	    {
		p3.moveRight();
	    }
	}

	if (p2 != null && !p2.bot)
	{
	    if (e.getKeyCode() == KeyEvent.VK_NUMPAD8)
	    {
		p2.moveUp();
	    }

	    if (e.getKeyCode() == KeyEvent.VK_NUMPAD5)
	    {
		p2.moveDown();
	    }

	    if (e.getKeyCode() == KeyEvent.VK_NUMPAD4)
	    {
		p2.moveLeft();
	    }

	    if (e.getKeyCode() == KeyEvent.VK_NUMPAD6)
	    {
		p2.moveRight();
	    }
	}

    }

    // Necesarias por la inteface, pero sin uso.
    @Override
    public void keyTyped(KeyEvent e)
    {
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
    }
}