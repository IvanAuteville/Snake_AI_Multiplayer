package tests;

import consumables.Consumable;
import consumables.Fruit;
import consumables.FruitFactory;
import maps.GameMap;
import maps.SimpleMap;

public class MapTest
{
    @SuppressWarnings("unused")
    public static void main(String[] args)
    {

	long startTime = System.nanoTime();
	long finishTime = startTime + (1 * 60 * (long) (1e9));

	long startTimeSeconds = startTime / (long) 1e9;
	long finishTimeSeconds = finishTime / (long) 1e9;

	GameMap map = new SimpleMap();
	FruitFactory fruitFactory = new FruitFactory();

	Consumable fruit;

	int apple = 0;
	int banana = 0;
	int cherry = 0;

	for (int i = 0; i < 100; i++)
	{
	    fruit = fruitFactory.makeFruit();

	    if (((Fruit) fruit).getName().equals("apple"))
	    {
		apple++;
	    }
	    if (((Fruit) fruit).getName().equals("banana"))
	    {
		banana++;
	    }
	    if (((Fruit) fruit).getName().equals("cherry"))
	    {
		cherry++;
	    }

	    System.out.println("TYPE: " + ((Fruit) fruit).getName());
	}

	System.out.println("Apple: " + apple);
	System.out.println("Banana: " + banana);
	System.out.println("Cherry: " + cherry);
	System.out.println("DEBUG");

	/*
	 * Snake snake = new Snake(2,2,1); map.setPosition(2, 2, 1); InputMock input =
	 * new InputMock(snake);
	 * 
	 * //snake.setDeadBodyPartWithMap(2, 2, map);
	 * 
	 * //System.out.println("DEBUG");
	 * 
	 * GameLogic game = new GameLogic(2, map); // Las serpientes inician bien en el
	 * mapa de colisiones
	 * 
	 * System.out.println("OK");
	 * 
	 * input.keyListener(KeyEvent.VK_D); input.keyListener(KeyEvent.VK_LEFT);
	 * game.update(); // Las serpientes se actualizan bien con longitud 1 en el mapa
	 * 
	 * 
	 * input.keyListener(KeyEvent.VK_S); input.keyListener(KeyEvent.VK_UP);
	 * game.update();
	 * 
	 * System.out.println("Debug");
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * map.setPosition(3, 2, 99);
	 * 
	 * boolean col = Collision.fruitCollision(snake, fruit, map);
	 * snake.shiftPositionsWithMap(map);
	 * 
	 * System.out.println("No como");
	 * 
	 * input.keyListener(KeyEvent.VK_D); snake.updateHeadPosition(); col =
	 * Collision.fruitCollision(snake, fruit, map); fruit.spawn(map);
	 * snake.shiftPositionsWithMap(map);
	 * 
	 * System.out.println("Comi");
	 * 
	 * input.keyListener(KeyEvent.VK_D); snake.updateHeadPosition(); col =
	 * Collision.fruitCollision(snake, fruit, map);
	 * snake.shiftPositionsWithMap(map);
	 * 
	 * System.out.println("No como");
	 */
    }
}
