package utils;

import maps.GameMap;
import snake.Snake;

public class AI
{
    A_Star a_Star = null;

    public AI(GameMap map, Snake[] players, Vector2 start, Vector2 end)
    {
	a_Star = new A_Star(map, players, start, end);
	a_Star.solve();
    }

    public void recalculate(Vector2 start, Vector2 end)
    {
	a_Star.resetGraph();
	a_Star.updateStartEnd(start, end);
	a_Star.solve();
    }

    public Vector2 getNextMove()
    {
	return a_Star.getNextMove();
    }
}