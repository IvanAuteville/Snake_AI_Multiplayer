package utils;

import java.util.ArrayList;

public class Node implements Comparable<Node>
{
    protected int x = 0;
    protected int y = 0;
    protected boolean obstacle = false;
    protected boolean visited = false;

    protected float localDistance = Float.MAX_VALUE;
    protected float globalDistance = Float.MAX_VALUE;

    protected Node parent = null;
    protected ArrayList<Node> adjacent = null;

    public Node(int x, int y, boolean obstacle, boolean visited)
    {
	this.x = x;
	this.y = y;
	this.obstacle = obstacle;
	this.visited = visited;
	this.adjacent = new ArrayList<Node>();
    }

    public void reset()
    {
	parent = null;
	visited = false;
	localDistance = Float.MAX_VALUE;
	globalDistance = Float.MAX_VALUE;
    }

    @Override
    public int compareTo(Node another)
    {
	if (this.globalDistance < another.globalDistance)
	{
	    return -1;
	} else if (this.globalDistance == another.globalDistance)
	{
	    return 0;
	} else
	{
	    return 1;
	}
    }
}
