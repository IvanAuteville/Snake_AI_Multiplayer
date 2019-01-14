package tests;

/*
import java.awt.event.KeyEvent;
import org.junit.Assert;
import org.junit.Test;
import consumables.Apple;
import consumables.Fruit;
import snake.Snake;
import snake.Vector2;
*/

public class SnakeTest {

    /*
	@Test
	public void snakeKillsSnake() {
		Snake snake = new Snake(0,0,1,"");
		snake.setLength(10);
		snake.shiftPositionsOld();
		Snake snake2 = new Snake(1,10,1,"");
		snake2.setLength(15);
		snake2.shiftPositionsOld();
		InputMock inputManager = new InputMock(snake);
		inputManager.keyListener(KeyEvent.VK_UP);
		snake2.moveUp();
		Assert.assertEquals(false,snake.isAlive());
	}

	@Test
	public void snakeSizeOne() {
		Snake snake = new Snake(0,0,1,"");
		Assert.assertEquals(2, snake.getLength(), 0.0);
	}

	@Test // Arreglar
	public void serpienteEatsFruit() {
		Snake snake = new Snake(0,0,1,"");
		Fruit fruit = new Apple(new Vector2(0,0));
		//Collision.fruitCollision(snake, fruit);
		Assert.assertEquals(fruit.getValue(),snake.getScore(), 0.0);		
	}

	@Test
	public void snakesHeadCollision() {
		Snake snake = new Snake(0,0,1,"");
		snake.setLength(3);
		Snake snake2 = new Snake(5,1,2,"");
		snake2.setLength(4);
		
	}
	
	@Test
	public void snakeDie() {
		Snake snake = new Snake(0,0,1,"");
		snake.setLength(20);
		snake.shiftPositionsOld();
		Snake snake2 = new Snake(1,1,1,"");
		snake2.setLength(20);
		snake2.shiftPositionsOld();
		
	}
	
	@Test
	public void serpienteChocaCabezaConSuCuerpo() {
		Snake snake = new Snake(0,0,1,"");
		snake.setLength(20);
		InputMock inputManager = new InputMock(snake);
		inputManager.keyListener(KeyEvent.VK_RIGHT);
		snake.moveRight();
		inputManager.keyListener(KeyEvent.VK_DOWN);
		snake.moveDown();
		inputManager.keyListener(KeyEvent.VK_LEFT);
		snake.moveLeft();
		inputManager.keyListener(KeyEvent.VK_UP);
		snake.moveUp();
		Assert.assertEquals(false,snake.isAlive());
	}

	@Test
	public void snakeMoveUp() {
		Snake snake = new Snake(0,0,1,"");
		InputMock inputManager = new InputMock(snake);
		inputManager.keyListener(KeyEvent.VK_UP);
		snake.moveUp();
		Assert.assertEquals(new Vector2(0, -1),snake.getMovePosition());
	}
	
	@Test
	public void snakeMoveDown() {
		Snake snake = new Snake(0,0,1,"");
		InputMock inputManager = new InputMock(snake);
		inputManager.keyListener(KeyEvent.VK_DOWN);
		snake.moveDown();
		Assert.assertEquals(new Vector2(0, 1),snake.getMovePosition());
	}
	
	@Test
	public void snakeMoveLeft() {
		Snake snake = new Snake(0,0,1,"");
		InputMock inputManager = new InputMock(snake);
		inputManager.keyListener(KeyEvent.VK_LEFT);
		snake.moveLeft();
		Assert.assertEquals(new Vector2(-1, 0),snake.getMovePosition());
	}
	
	@Test
	public void snakeMoveRight() {
		Snake snake = new Snake(0,0,1,"");
		InputMock inputManager = new InputMock(snake);
		inputManager.keyListener(KeyEvent.VK_RIGHT);
		snake.moveRight();
		Assert.assertEquals(new Vector2(1, 0),snake.getMovePosition());
	}
	*/
}
