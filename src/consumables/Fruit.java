package consumables;

import java.awt.Color;
import utils.Vector2;

public abstract class Fruit extends Consumable
{
    private int value;
    private String name;
    private Color color;

    public Fruit(int value, Vector2 position, Color color, String name)
    {
	super(position);
	this.value = value;
	this.color = color;
	this.name = name;
    }

    public Fruit(int value, Color color, String name)
    {
	this.value = value;
	this.color = color;
	this.name = name;
    }

    public int getValue()
    {
	return this.value;
    }

    public Color getColor()
    {
	return this.color;
    }

    public String getName()
    {
	return this.name;
    }
}