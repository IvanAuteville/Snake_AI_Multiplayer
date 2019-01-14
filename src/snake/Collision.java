package snake;

import consumables.Consumable;
import consumables.Fruit;
import maps.GameMap;
import sound_engine.SoundManager;

public class Collision
{
    public static boolean mapCollision(Snake snake, GameMap map)
    {
	int snakeX = snake.getHeadPos().getX();
	int snakeY = snake.getHeadPos().getY();

	if (map.hasWalls())
	{
	    if (snakeX > -1 && snakeX < map.getSize() && snakeY > -1 && snakeY < map.getSize())
	    {
		if (map.getPosition(snake.getHeadPos().getX(), snake.getHeadPos().getY()) == -1)
		{
		    snake.die(map);
		    return true;
		}
	    }
	}

	// Si me voy del mapa por arriba
	if (snakeX <= -1)
	{
	    snake.getHeadPos().setX(map.getSize() - 1);
	}
	// Si me voy del mapa por debajo
	if (snakeX >= map.getSize())
	{
	    snake.getHeadPos().setX(0);
	}

	// Si me voy del mapa por la izquierda
	if (snakeY <= -1)
	{
	    snake.getHeadPos().setY(map.getSize() - 1);
	}

	// Si me voy del mapa por la derecha
	if (snakeY >= map.getSize())
	{
	    snake.getHeadPos().setY(0);
	}

	return false;
    }

    public static boolean selfCollision(Snake snake, GameMap map)
    {
	if (snake.getLength() >= 6)
	{
	    if (map.getPosition(snake.getHeadPos().getX(), snake.getHeadPos().getY()) == snake.getId())
	    {
		snake.die(map);
		return true;
	    }
	}

	return false;
    }

    public static boolean fruitCollision(Snake snake, Consumable fruit, GameMap map)
    {
	if (snake.getHeadPos().equals(fruit.getPosition()))
	{
	    snake.eat(((Fruit) fruit).getValue(), map);
	    SoundManager.play("eat");
	    return true;
	}

	return false;
    }

    public static int snakesCollision(Snake[] players, GameMap map)
    {
	int totalPlayers = players.length;

	// Cantidad de muertos
	int dead = 0;

	// Map points guarda que hay en el mapa en el lugar al que apuntan las cabezas
	// de las serpientes
	int[] mapPoints = new int[totalPlayers];

	// Jugadores marcados para morir
	boolean[] deadPlayers = new boolean[totalPlayers];

	for (int i = 0; i < players.length; i++)
	{
	    mapPoints[i] = map.getPosition(players[i].getHeadPos().getX(), players[i].getHeadPos().getY());
	}

	// Checkeo entre vivos por cabezas - cabezas
	// Todos con todos
	for (int i = 0; i < players.length; i++)
	{
	    for (int j = 0; j < players.length; j++)
	    {
		// Chequeamos solo si ambos estan vivos
		if (players[i].getId() != players[j].getId() && players[i].isAlive() && players[j].isAlive())
		{
		    // Si chocan sus cabezas...
		    if (players[i].getHeadPos().getX() == players[j].getHeadPos().getX()
			    && players[i].getHeadPos().getY() == players[j].getHeadPos().getY())
		    {
			deadPlayers[i] = true;
			deadPlayers[j] = true;
		    }

		    // Caso de adyacencia entre una serpiente de longitud 2 y otra que no lo es
		    if (players[j].getLength() == 2)
		    {
			// Si se estan moviendo en el mismo eje...
			if ((players[i].getMovePos().getX() != 0 && players[j].getMovePos().getX() != 0)
				|| (players[i].getMovePos().getY() != 0 && players[j].getMovePos().getY() != 0))
			{
			    // Y su ultima posicion es la actual de la cabeza del otro...
			    if (players[i].getHeadPos().equals(players[j].getLastPos()))
			    {
				deadPlayers[i] = true;
				deadPlayers[j] = true;
			    }
			}

		    }

		    // Casos de adyacencia entre AMBAS serpientes de longitud 2 (1 cuadro a nivel
		    // grafico)
		    // En este caso especial las cabezas no chocan y si no se revisa aca, en las
		    // colisiones
		    // con el mapa tampoco es detectado
		    if (players[i].getLength() == 2 && players[j].getLength() == 2)
		    {
			// Si se estan moviendo en el mismo eje...
			if ((players[i].getMovePos().getX() != 0 && players[j].getMovePos().getX() != 0)
				|| (players[i].getMovePos().getY() != 0 && players[j].getMovePos().getY() != 0))
			{
			    // Y su ultima posicion es la actual de la cabeza del otro...
			    if (players[i].getHeadPos().equals(players[j].getLastPos()))
			    {
				deadPlayers[i] = true;
			    }
			}

		    }
		}
	    }
	}

	// Checkeo entre vivos por cabezas - puntos del mapa (o sea cabezas contra
	// cuerpos)
	for (int i = 0; i < players.length; i++)
	{
	    // && mapPoints[i] != -1
	    // Choque algo que no es pared, no es vacio, no es fruta, no soy yo mismo
	    if (players[i].isAlive() && mapPoints[i] != 0 && mapPoints[i] != 99 && mapPoints[i] != players[i].getId())
	    {
		// Ese jugador esta vivo
		if (players[mapPoints[i] - 1].isAlive())
		{
		    deadPlayers[i] = true;
		}
	    }
	}

	// Seteamos muertos a los jugadores que colisionaron en el frame
	for (int i = 0; i < deadPlayers.length; i++)
	{
	    if (deadPlayers[i] == true)
	    {
		dead++;
		players[i].die(map);
	    }

	}

	// Parte del checkeo - comida
	// Checkeo entre vivos por puntos del mapa para comer a los muertos
	for (int i = 0; i < players.length; i++)
	{
	    // Choque algo que no es pared, no es vacio, no es fruta, no soy yo mismo
	    if (players[i].isAlive() && mapPoints[i] != 0 && mapPoints[i] != 99 && mapPoints[i] != -1
		    && mapPoints[i] != players[i].getId())
	    {
		// Ese jugador esta vivo
		if (!players[mapPoints[i] - 1].isAlive())
		{
		    players[mapPoints[i] - 1].setDeadBodyPartWithMap(players[i].getHeadPos().getX(),
			    players[i].getHeadPos().getY(), map);

		    players[i].eat(5, map);

		    SoundManager.play("playereat");
		}
	    }
	}

	return dead;
    }
}