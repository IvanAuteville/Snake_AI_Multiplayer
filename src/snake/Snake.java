package snake;

import org.json.JSONArray;
import org.json.JSONObject;
import maps.GameMap;
import utils.AI;
import utils.JSONSerializable;
import utils.Vector2;

public class Snake implements JSONSerializable
{
    // ID del jugador
    private int id = 0;

    // Nombre de Jugador
    private String playerName;

    // Puntaje de la serpiente
    private int score = 0;

    // Estado de la serpiente
    private boolean alive = false;

    // Nos dice si la serpiente comio en este update o no
    private boolean ate = false;

    // Ya recibi movimiento en este frame (para evitar problemas de input)
    private boolean moveEnabled = false;

    // Posicion inicial de la cabeza del snake
    private Vector2 headPos = null;

    // Solucion para la colision entre snakes de 1 cuadro de longitud
    private Vector2 lastPos = null;

    // Variables para guardar el movimiento tomado por el input
    private Vector2 movePos = new Vector2(0, 0);

    // La serpiente puede tener 255 partes (lo podemos cambiar).
    private Vector2[] snakeBody = new Vector2[256];

    // Inicia en 2 porque la primera es una falsa cabeza
    // que sirve para la logica.
    private int length = 2;

    // Indica si ya se ingreso el primer input
    public boolean isReady = false;

    // Es un bot?
    public boolean bot = false;
    public AI AIManager = null;

    // CONSTRUCTORES
    public Snake(int x, int y, int id, String playerName, GameMap map)
    {
	this.id = id;
	this.playerName = playerName;
	this.setHeadPos(new Vector2(x, y));
	this.setLastPos(new Vector2(x, y));
	;

	// Iniciamos el cuerpo en el constructor
	this.snakeBody[0] = new Vector2(x, y);
	this.snakeBody[1] = new Vector2(x, y);

	map.setPosition(x, y, id);

	this.alive = true;
	this.moveEnabled = true;
    }

    public Snake(Vector2 pos, int id, String playerName, GameMap map)
    {
	this.id = id;
	this.playerName = playerName;
	this.setHeadPos(new Vector2(pos.getX(), pos.getY()));
	this.setLastPos(new Vector2(pos.getX(), pos.getY()));

	// Iniciamos el cuerpo en el constructor
	this.snakeBody[0] = new Vector2(pos.getX(), pos.getY());
	this.snakeBody[1] = new Vector2(pos.getX(), pos.getY());

	map.setPosition(pos.getX(), pos.getY(), id);

	this.alive = true;
	this.moveEnabled = true;
    }

    // Utilizado en el Online
    public Snake()
    {
    }

    // GETTERS Y SETTERS
    public String getPlayerName()
    {
	return playerName;
    }

    public void setPlayerName(String playerName)
    {
	this.playerName = playerName;
    }

    public int getLength()
    {
	return length;
    }

    public void setLength(int length)
    {
	this.length = length;
    }

    public int getId()
    {
	return id;
    }

    public void setId(int id)
    {
	this.id = id;
    }

    public Vector2[] getSnakeBody()
    {
	return snakeBody;
    }

    public int getScore()
    {
	return score;
    }

    public boolean isAlive()
    {
	return alive;
    }

    public void die(GameMap map)
    {
	alive = false;

	if (ate)
	{
	    map.setPosition(this.snakeBody[this.length - 2].getX(), this.snakeBody[this.length - 2].getY(), id);
	} else
	{
	    map.setPosition(this.snakeBody[this.length - 1].getX(), this.snakeBody[this.length - 1].getY(), id);
	}
    }

    public Vector2 getMovePosition()
    {
	return getMovePos();
    }

    // MOVIMIENTO
    public void moveUp()
    {
	if (!(getMovePos().getY() == 1) && moveEnabled)
	{
	    getMovePos().setX(0);
	    getMovePos().setY(-1);
	    moveEnabled = false;
	    isReady = true;
	}
    }

    public void moveDown()
    {
	if (!(getMovePos().getY() == -1) && moveEnabled)
	{
	    getMovePos().setX(0);
	    getMovePos().setY(1);
	    moveEnabled = false;
	    isReady = true;
	}
    }

    public void moveLeft()
    {
	if (!(getMovePos().getX() == 1) && moveEnabled)
	{
	    getMovePos().setX(-1);
	    getMovePos().setY(0);
	    moveEnabled = false;
	    isReady = true;
	}
    }

    public void moveRight()
    {
	if (!(getMovePos().getX() == -1) && moveEnabled)
	{
	    getMovePos().setX(1);
	    getMovePos().setY(0);
	    moveEnabled = false;
	    isReady = true;
	}
    }

    // FUNCIONAMIENTO INTERNO DEL CUERPO DE LA SERPIENTE
    public void updateHeadPosition(GameMap map)
    {
	if (bot)
	{
	    // Seteamos el proximo movimiento
	    move(this.AIManager.getNextMove());
	}

	this.getHeadPos().Sum(this.getMovePos());

	// Para evitar los bugs con una sola posicion
	if (this.length == 2)
	{
	    this.setLastPos(this.snakeBody[this.length - 1]);
	}

	// QUITAR LA COLA DEL MAPA
	map.setPosition(this.snakeBody[this.length - 1].getX(), this.snakeBody[this.length - 1].getY(), 0);
	// -------------------------------

	moveEnabled = true;
    }

    private void move(Vector2 nextMove)
    {
	int x = 0;
	int y = 0;

	if (nextMove != null)
	{
	    x = nextMove.getX();
	    y = nextMove.getY();
	}

	if (y == 1)
	{
	    this.moveDown();
	} else if (y == -1)
	{
	    this.moveUp();
	} else if (x == 1)
	{
	    this.moveRight();
	} else if (x == -1)
	{
	    this.moveLeft();
	}

    }

    public void eat(int score, GameMap map)
    {
	ate = true;
	length++;
	this.score += score;

	// AGREGAR LA COLA AL MAPA
	map.setPosition(this.snakeBody[this.length - 2].getX(), this.snakeBody[this.length - 2].getY(), id);
	// -------------------------
    }

    public void shiftPositionsWithMap(GameMap map)
    {
	this.snakeBody[0] = new Vector2(getHeadPos());

	// Limpiamos la cola si la serpiente no comio en este cuadro
	if (ate)
	{
	    ate = false;
	}

	// Agregamos la cabeza al mapa
	map.setPosition(getHeadPos().getX(), getHeadPos().getY(), this.id);

	// Desplazamiento de las posiciones
	// [0] y [1] quedan iguales
	// Inicia en la ultima posicion
	for (int i = this.length - 1; i >= 1; i--)
	{
	    this.snakeBody[i] = this.snakeBody[i - 1];
	}
    }

    public void setDeadBodyPartWithMap(int x, int y, GameMap map)
    {
	// Limpiamos el mapa en esa posicion para no colisionar nuevamente
	map.setPosition(x, y, 0);

	for (int i = 1; i < snakeBody.length; i++)
	{
	    if (snakeBody[i] != null && snakeBody[i].getX() == x && snakeBody[i].getY() == y)
	    {
		snakeBody[i] = new Vector2(999, 999);
		break;
	    }
	}
    }

    public void setSnakeBody(Vector2[] snakeBody)
    {
	this.snakeBody = snakeBody;
    }

    public void setHeadPos(Vector2 headPos)
    {
	this.headPos = headPos;
    }

    public void setScore(int score)
    {
	this.score = score;
    }

    public void setAlive(boolean alive)
    {
	this.alive = alive;
    }

    public Vector2 getHeadPos()
    {
	return headPos;
    }

    public JSONObject toJson() throws Exception
    {
	JSONObject data = new JSONObject();

	String[] attributes =
	{ "playerName", "score", "length", "alive" };
	data = utils.JsonUtils.toJson(this, attributes);

	JSONArray arr = new JSONArray();

	for (int i = 0; i < length; i++)
	{
	    arr.put(snakeBody[i].toJson());
	}

	data.put("snakeBody", arr);

	return data;
    }

    public Vector2 getMovePos()
    {
	return movePos;
    }

    public void setMovePos(Vector2 movePos)
    {
	this.movePos = movePos;
    }

    public Vector2 getLastPos()
    {
	return lastPos;
    }

    public void setLastPos(Vector2 lastPos)
    {
	this.lastPos = lastPos;
    }
}