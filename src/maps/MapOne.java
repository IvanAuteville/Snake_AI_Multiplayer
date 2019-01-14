package maps;

import utils.Vector2;

public class MapOne extends GameMap
{
    public MapOne()
    {
	// El mapa tiene paredes (true)
	super(true);

	// Este mapa tiene 210 "bloques"
	super.renderMap = new Vector2[210];

	int index = 0;
	// PAREDES HORIZONTALES
	for (int i = 5; i < 44; i += 38)
	{
	    for (int j = 5; j < super.collisionMap.length - 6; j++)
	    {
		if (j == 23 || j == 24 || j == 25)
		{
		    continue;
		}

		super.collisionMap[i][j] = -1;
		super.renderMap[index] = new Vector2(j, i);
		index++;
	    }
	}

	for (int j = 12; j < super.collisionMap.length - 6; j++)
	{
	    if (j == 23 || j == 24 || j == 25)
	    {
		continue;
	    }

	    super.collisionMap[11][j] = -1;
	    super.renderMap[index] = new Vector2(j, 11);
	    index++;
	}

	for (int j = 12; j < super.collisionMap.length - 12; j++)
	{
	    if (j == 23 || j == 24 || j == 25)
	    {
		continue;
	    }

	    super.collisionMap[37][j] = -1;
	    super.renderMap[index] = new Vector2(j, 37);
	    index++;
	}

	// PAREDES VERTICALES
	for (int i = 5; i < 43; i++)
	{
	    if (i == 23 || i == 24 || i == 25)
	    {
		continue;
	    }

	    super.collisionMap[i][5] = -1;
	    super.renderMap[index] = new Vector2(5, i);
	    index++;
	}

	for (int i = 12; i < 43; i++)
	{
	    if (i == 23 || i == 24 || i == 25)
	    {
		continue;
	    }

	    super.collisionMap[i][43] = -1;
	    super.renderMap[index] = new Vector2(43, i);
	    index++;
	}

	for (int i = 12; i < 38; i++)
	{
	    if (i == 23 || i == 24 || i == 25)
	    {
		continue;
	    }

	    super.collisionMap[i][12] = -1;
	    super.renderMap[index] = new Vector2(12, i);
	    index++;
	}
    }
}