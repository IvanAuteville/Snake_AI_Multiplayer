package utils;

import java.util.LinkedList;
import java.util.PriorityQueue;
import maps.GameMap;
import snake.Snake;

public class A_Star
{
    protected GameMap map = null;
    protected Node[][] nodes = null;
    protected Node startNode = null;
    protected Node endNode = null;
    protected LinkedList<Vector2> movements = null;
    protected Snake[] players = null;

    public A_Star(GameMap map, Snake[] players, Vector2 start, Vector2 end)
    {
	this.map = map;
	this.players = players;

	// Creamos los nodos seteando sus coordenadas y
	// si son obstaculos de acuerdo a lo que hay en el mapa
	nodes = new Node[map.getSize()][map.getSize()];
	for (int x = 0; x < nodes.length; x++)
	{
	    for (int y = 0; y < nodes.length; y++)
	    {
		boolean obstacle = isObstacle(x, y);
		nodes[y][x] = new Node(x, y, obstacle, false);
	    }
	}

	// Creamos las conexiones "Aristas"
	for (int x = 0; x < nodes.length; x++)
	{
	    for (int y = 0; y < nodes.length; y++)
	    {
		// Superior
		if (y > 0)
		{
		    nodes[y][x].adjacent.add(nodes[y - 1][x]);
		} else
		{
		    nodes[y][x].adjacent.add(nodes[nodes.length - 1][x]);
		}
		// Inferior
		if (y < nodes.length - 1)
		{
		    nodes[y][x].adjacent.add(nodes[y + 1][x]);
		} else
		{
		    nodes[y][x].adjacent.add(nodes[0][x]);
		}
		// Izquierda
		if (x > 0)
		{
		    nodes[y][x].adjacent.add(nodes[y][x - 1]);
		} else
		{
		    nodes[y][x].adjacent.add(nodes[y][nodes.length - 1]);
		}
		// Derecha
		if (x < nodes.length - 1)
		{
		    nodes[y][x].adjacent.add(nodes[y][x + 1]);
		} else
		{
		    nodes[y][x].adjacent.add(nodes[y][0]);
		}
	    }
	}

	// Seteamos el comienzo y el final
	this.startNode = nodes[start.getY()][start.getX()];
	this.endNode = nodes[end.getY()][end.getX()];
    }

    public void updateStartEnd(Vector2 start, Vector2 end)
    {
	// Reseteamos el comienzo y el final
	this.startNode = nodes[start.getY()][start.getX()];
	this.endNode = nodes[end.getY()][end.getX()];
    }

    public void resetGraph()
    {
	for (int x = 0; x < nodes.length; x++)
	{
	    for (int y = 0; y < nodes.length; y++)
	    {
		nodes[y][x].reset();
		nodes[y][x].obstacle = isObstacle(x, y);
	    }
	}

    }

    private float eulerDistance(Node start, Node end)
    {
	return (float) Math.sqrt((end.x - start.x) * (end.x - start.x) + (end.y - start.y) * (end.y - start.y));
    }

    public void solve()
    {
	Node currentNode = startNode;
	startNode.localDistance = 0f;
	startNode.globalDistance = eulerDistance(startNode, endNode);

	PriorityQueue<Node> notTested = new PriorityQueue<Node>();
	notTested.add(startNode);

	while (!notTested.isEmpty() && currentNode != endNode)
	{
	    // Removemos los visitados que hayan quedado
	    while (!notTested.isEmpty() && notTested.peek().visited)
	    {
		notTested.poll();
	    }

	    // Si la cola esta vacia en este punto, no pudimos encontrar un camino
	    if (notTested.isEmpty())
		break;

	    currentNode = notTested.poll();
	    currentNode.visited = true;

	    // Por cada nodo adyacente al nodo actual...
	    for (Node adjacentNode : currentNode.adjacent)
	    {
		if (!adjacentNode.visited && !adjacentNode.obstacle)
		{
		    notTested.add(adjacentNode);
		}

		// Todas las aristas tienen distancia 1 al ser una grilla...
		float auxLowerDistance = currentNode.localDistance + 1; // eulerDistance(currentNode, adjacentNode);

		if (auxLowerDistance < adjacentNode.localDistance)
		{
		    adjacentNode.parent = currentNode;
		    adjacentNode.localDistance = auxLowerDistance;
		    adjacentNode.globalDistance = adjacentNode.localDistance + eulerDistance(adjacentNode, endNode);
		}
	    }

	}

	getPath();
    }

    private void getPath()
    {
	movements = new LinkedList<Vector2>();

	Node traversal = endNode;

	while (traversal.parent != null)
	{
	    Node aux = traversal.parent;

	    // Condiciones para los bordes --> "Teletransportacion"
	    if (aux.x == nodes.length - 1 && traversal.x == 0)
	    {
		movements.add(new Vector2(1, 0));
	    } else if (aux.x == 0 && traversal.x == nodes.length - 1)
	    {
		movements.add(new Vector2(-1, 0));
	    }

	    else if (aux.y == nodes.length - 1 && traversal.y == 0)
	    {
		movements.add(new Vector2(0, 1));
	    }

	    else if (aux.y == 0 && traversal.y == nodes.length - 1)
	    {
		movements.add(new Vector2(0, -1));
	    } else
	    {
		// Caso contrario...
		movements.add(new Vector2(traversal.x - aux.x, traversal.y - aux.y));
	    }

	    traversal = traversal.parent;
	}

	if (movements.isEmpty())
	{
	    stall();
	}
    }

    private void stall()
    {
	if (startNode.x < nodes.length - 1 && !isObstacle(startNode.x + 1, startNode.y))
	{
	    movements.add(new Vector2(1, 0));
	} else if (!isObstacle(0, startNode.y))
	{
	    movements.add(new Vector2(1, 0));
	} else if (startNode.x > 0 && !isObstacle(startNode.x - 1, startNode.y))
	{
	    movements.add(new Vector2(-1, 0));
	} else if (!isObstacle(nodes.length - 1, startNode.y))
	{
	    movements.add(new Vector2(-1, 0));
	} else if (startNode.y < nodes.length - 1 && !isObstacle(startNode.x, startNode.y + 1))
	{
	    movements.add(new Vector2(0, 1));
	} else if (!isObstacle(startNode.x, 0))
	{
	    movements.add(new Vector2(0, 1));
	} else if (startNode.y > 0 && !isObstacle(startNode.x, startNode.y - 1))
	{
	    movements.add(new Vector2(0, -1));
	} else if (!isObstacle(startNode.x, nodes.length - 1))
	{
	    movements.add(new Vector2(0, -1));
	}
    }

    private boolean isObstacle(int x, int y)
    {
	int id = map.getPosition(x, y);

	// Paredes == -1
	if (id == -1)
	{
	    return true;
	} else if (id >= 1 && id <= 4)
	{
	    if (players[id - 1].isAlive())
	    {
		return true;
	    }
	}

	return false;
    }

    public Vector2 getNextMove()
    {
	if (!movements.isEmpty())
	{
	    return movements.removeLast();
	} else
	{
	    return null;
	}
    }
}