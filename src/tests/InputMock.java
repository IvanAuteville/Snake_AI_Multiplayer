package tests;

import java.awt.event.KeyEvent;

import snake.Snake;

public class InputMock
{
    private Snake snake1;
    private Snake snake2;

    public InputMock(Snake snake)
    {
	this.snake1 = snake;
    }

    public InputMock(Snake[] snakes)
    {
	this.snake1 = snakes[0];
	this.snake2 = snakes[1];
    }

    public void keyListener(int keyCode)
    {
	// SNAKE DE LA ESQUINA SUPERIOR IZQUIERDA
	{
	    if (keyCode == KeyEvent.VK_W)
	    {
		snake1.moveUp();
	    }

	    if (keyCode == KeyEvent.VK_S)
	    {
		snake1.moveDown();
	    }

	    if (keyCode == KeyEvent.VK_A)
	    {
		snake1.moveLeft();
	    }

	    if (keyCode == KeyEvent.VK_D)
	    {
		snake1.moveRight();
	    }
	}

	// SNAKE DE LA ESQUINA INFERIOR DERECHA
	{
	    if (keyCode == KeyEvent.VK_UP)
	    {
		snake2.moveUp();
	    }

	    if (keyCode == KeyEvent.VK_DOWN)
	    {
		snake2.moveDown();
	    }

	    if (keyCode == KeyEvent.VK_LEFT)
	    {
		snake2.moveLeft();
	    }

	    if (keyCode == KeyEvent.VK_RIGHT)
	    {
		snake2.moveRight();
	    }

	}
    }
}