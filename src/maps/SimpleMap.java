package maps;

import utils.Vector2;

//   MAP
// -------
// -	 -
// -	 -
// -------

public class SimpleMap extends GameMap
{
    public SimpleMap()
    {
	// El mapa tiene paredes (true)
	super(true);

	// Este mapa tiene 196 "bloques"
	super.renderMap = new Vector2[196];

	int index = 0;
	// Creacion de las paredes, cambia en cada subclass (segun la forma del mapa).
	// PAREDES HORIZONTALES
	for (int i = 0; i < super.collisionMap.length; i += 49)
	{
	    for (int j = 0; j < super.collisionMap.length; j++)
	    {
		super.collisionMap[i][j] = -1;
		super.renderMap[index] = new Vector2(j, i);
		index++;
	    }
	}

	// PAREDES VERTICALES
	for (int j = 1; j < super.collisionMap.length - 1; j++)
	{
	    for (int i = 0; i < super.collisionMap.length; i += 49)
	    {
		super.collisionMap[j][i] = -1;
		super.renderMap[index] = new Vector2(i, j);
		index++;
	    }
	}
    }
}