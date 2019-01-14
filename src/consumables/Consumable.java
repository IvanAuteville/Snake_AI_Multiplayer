package consumables;

import java.util.Random;
import maps.GameMap;
import utils.JSONSerializable;
import utils.Vector2;

public abstract class Consumable implements JSONSerializable
{
    private Vector2 position = new Vector2(0, 0);

    private Random random = new Random();

    public Consumable()
    {
    }

    public Consumable(Vector2 position)
    {
	this.position = position;
    }

    public Vector2 getPosition()
    {
	return position;
    }

    public void spawn(GameMap map)
    {
	this.position = new Vector2(random.nextInt(map.getSize() - 1), random.nextInt(map.getSize() - 1));
	int aux = map.getPosition(this.position.getX(), this.position.getY());

	while (aux != 0)
	{
	    this.position = new Vector2(random.nextInt(map.getSize() - 1), random.nextInt(map.getSize() - 1));

	    aux = map.getPosition(this.position.getX(), this.position.getY());
	}

	map.setPosition(this.position.getX(), this.position.getY(), 99);
    }
}