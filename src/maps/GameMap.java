package maps;

import utils.Vector2;

public class GameMap
{
    // Tiene paredes o no?
    private boolean walls;

    // Tamaño del mapa (ScreenSize/Grid)
    // Fixeado a 50, excepto que se implemente
    // una camara que siga al jugador en mapas
    // mas grandes
    private final int size = 50;

    // Matriz que mantiene el estado actual del mapa
    // y nos ahorra mucho calculo de colisiones
    protected int[][] collisionMap;

    // Vector que nos ahorra tiempo para renderizado del mapa
    protected Vector2[] renderMap;

    public GameMap(boolean walls)
    {
	this.walls = walls;
	this.collisionMap = new int[50][50];
    }

    public boolean hasWalls()
    {
	return walls;
    }

    public int getSize()
    {
	return size;
    }

    public void setPosition(int x, int y, int id)
    {
	collisionMap[y][x] = id;
    }

    public int getPosition(int x, int y)
    {
	return collisionMap[y][x];
    }

    public Vector2[] getRenderMap()
    {
	return renderMap;
    }
}