package maps;

import utils.Vector2;

public class MapTwo extends GameMap
{
    public MapTwo()
    {
	// El mapa tiene paredes (true)
	super(true);

	// Este mapa tiene 202 "bloques"
	super.renderMap = new Vector2[202];

	int index = 0;
	// PAREDES HORIZONTALES
	for (int i = 5; i < 44; i += 38)
	{
	    for (int j = 9; j < super.collisionMap.length - 9; j++)
	    {
		super.collisionMap[i][j] = -1;
		super.renderMap[index] = new Vector2(j, i);
		index++;
	    }
	}

	for (int i = 12; i < 37; i += 24)
	{
	    for (int j = 15; j < super.collisionMap.length - 14; j++)
	    {
		super.collisionMap[i][j] = -1;
		super.renderMap[index] = new Vector2(j, i);
		index++;
	    }
	}

	// VERTICALES
	for (int j = 4; j < 46; j += 41)
	{
	    for (int i = 10; i < 39; i++)
	    {
		super.collisionMap[i][j] = -1;
		super.renderMap[index] = new Vector2(j, i);
		index++;
	    }
	}

	for (int j = 9; j < 41; j += 31)
	{
	    for (int i = 17; i < 32; i++)
	    {
		super.collisionMap[i][j] = -1;
		super.renderMap[index] = new Vector2(j, i);
		index++;
	    }
	}

	// Puntos aislados
	super.collisionMap[12][9] = -1;
	super.renderMap[index] = new Vector2(9, 12);
	index++;
	super.collisionMap[12][40] = -1;
	super.renderMap[index] = new Vector2(40, 12);
	index++;
	super.collisionMap[36][9] = -1;
	super.renderMap[index] = new Vector2(9, 36);
	index++;
	super.collisionMap[36][40] = -1;
	super.renderMap[index] = new Vector2(40, 36);
	index++;

	super.collisionMap[5][4] = -1;
	super.renderMap[index] = new Vector2(4, 5);
	index++;
	super.collisionMap[5][45] = -1;
	super.renderMap[index] = new Vector2(45, 5);
	index++;
	super.collisionMap[43][4] = -1;
	super.renderMap[index] = new Vector2(4, 43);
	index++;
	super.collisionMap[43][45] = -1;
	super.renderMap[index] = new Vector2(45, 43);
	index++;
    }
}