package utils;

import org.json.JSONObject;

public class Vector2 implements JSONSerializable
{
    private int x;
    private int y;

    public Vector2(int x, int y)
    {
	this.setX(x);
	this.setY(y);
    }

    public Vector2(Vector2 otherVector)
    {
	this.setX(otherVector.getX());
	this.setY(otherVector.getY());
    }

    protected void setPosition(int x, int y)
    {
	this.setX(x);
	this.setY(y);
    }

    public double getDistance(Vector2 other)
    {
	return Math.sqrt(Math.pow(this.getX() - other.getX(), 2) + Math.pow(this.getY() - other.getY(), 2));
    }

    public void Sum(Vector2 otherVector)
    {
	this.setX(this.getX() + otherVector.getX());
	this.setY(this.getY() + otherVector.getY());
    }

    @Override
    public int hashCode()
    {
	final int prime = 31;
	int result = 1;
	result = prime * result + getX();
	result = prime * result + getY();
	return result;
    }

    @Override
    public boolean equals(Object obj)
    {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Vector2 other = (Vector2) obj;
	if (getX() != other.getX())
	    return false;
	if (getY() != other.getY())
	    return false;
	return true;
    }

    @Override
    public String toString()
    {
	return this.getX() + " " + this.getY();
    }

    public int getX()
    {
	return x;
    }

    public void setX(int x)
    {
	this.x = x;
    }

    public int getY()
    {
	return y;
    }

    public void setY(int y)
    {
	this.y = y;
    }

    public JSONObject toJson() throws Exception
    {
	JSONObject json = new JSONObject();
	String[] attributes =
	{ "x", "y" };

	json = JsonUtils.toJson(this, attributes);
	return json;
    }
}