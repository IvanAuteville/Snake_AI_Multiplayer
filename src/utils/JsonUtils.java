package utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONObject;
import consumables.Apple;
import consumables.Banana;
import consumables.Cherry;
import consumables.Fruit;
import snake.Snake;

public class JsonUtils
{
    @SuppressWarnings("unchecked")
    public static JSONObject toJson(JSONSerializable obj, String[] atributes) throws Exception
    {
	JSONObject json = new JSONObject();
	Class<? extends JSONSerializable> clazz = obj.getClass();
	BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
	PropertyDescriptor[] propertyDesc = beanInfo.getPropertyDescriptors();
	obj.getClass().getDeclaredMethods();
	for (int i = 0; i < atributes.length; i++)
	{
	    Object value = null;
	    for (PropertyDescriptor pd : propertyDesc)
	    {
		if (pd.getName().equals(atributes[i]))
		{
		    Method getter = pd.getReadMethod();
		    if (getter != null)
			value = getter.invoke(obj);
		    if (value instanceof Collection<?>)
		    {
			Collection<JSONSerializable> col = ((Collection<JSONSerializable>) value);
			Iterator<JSONSerializable> it = col.iterator();
			JSONArray arr = new JSONArray();
			while (it.hasNext())
			{
			    JSONSerializable colObj = it.next();
			    arr.put(colObj.toJson());
			}
			if (!arr.isEmpty())
			    json.put(atributes[i], arr);
			else
			    json.put(atributes[i], "{}");
		    } else if (value instanceof JSONSerializable)
		    {
			json.put(atributes[i], ((JSONSerializable) value).toJson());
		    } else
		    {
			json.put(atributes[i], value);
		    }
		}
	    }
	}

	return json;
    }

    @SuppressWarnings("unchecked")
    public static JSONArray collectionToJson(Collection<?> col) throws Exception
    {
	JSONArray arr = new JSONArray();
	Collection<JSONSerializable> collection = (Collection<JSONSerializable>) col;
	for (JSONSerializable obj : collection)
	{
	    arr.put(obj.toJson());
	}
	return arr;
    }

    @SuppressWarnings("unchecked")
    public static <T, M> T fromJson(JSONObject json, T clazz) throws Exception
    {
	T resp = (T) clazz.getClass().newInstance();
	BeanInfo beanInfo = Introspector.getBeanInfo(clazz.getClass());
	PropertyDescriptor[] propertyDesc = beanInfo.getPropertyDescriptors();
	for (String key : json.keySet())
	{
	    for (PropertyDescriptor pd : propertyDesc)
	    {
		if (pd.getName().equals(key))
		{
		    if (isAttributeSerializable(pd))
		    {
			Method setter = pd.getWriteMethod();
			M m = (M) fromJson(json.getJSONObject(key), (M) pd.getPropertyType().newInstance());
			setter.invoke(resp, m);
		    } else
		    {
			getPrimitiveType(json, resp, key, pd);
		    }
		}
	    }
	}
	return resp;

    }

    @SuppressWarnings("unchecked")
    public static <T> Collection<T> fromJson(JSONArray arr, T clazz) throws Exception
    {
	Collection<T> resp = new ArrayList<T>();
	BeanInfo beanInfo = Introspector.getBeanInfo(clazz.getClass());
	PropertyDescriptor[] propertyDesc = beanInfo.getPropertyDescriptors();
	for (int i = 0; i < arr.length(); i++)
	{
	    JSONObject json = arr.getJSONObject(i);
	    T obj = (T) clazz.getClass().newInstance();
	    for (String key : json.keySet())
	    {
		for (PropertyDescriptor pd : propertyDesc)
		{
		    if (pd.getName().equals(key))
		    {
			Method setter = pd.getWriteMethod();
			setter.invoke(obj, json.get(key));
		    }
		}
	    }
	    resp.add(obj);
	}
	return resp;
    }

    @SuppressWarnings("rawtypes")
    private static boolean isAttributeSerializable(PropertyDescriptor pd)
    {
	for (Class clazz : pd.getReadMethod().getReturnType().getInterfaces())
	{
	    if (clazz.equals(JSONSerializable.class))
	    {
		return true;
	    }
	}
	return false;
    }

    private static <T> void getPrimitiveType(JSONObject json, T obj, String key, PropertyDescriptor pd)
	    throws IllegalAccessException, InvocationTargetException
    {
	try
	{
	    Method setter = pd.getWriteMethod();
	    if (setter != null)
	    {
		if (pd.getPropertyType().equals(String.class))
		{
		    setter.invoke(obj, json.getString(key));
		} else if (pd.getPropertyType().equals(Long.class))
		{
		    setter.invoke(obj, json.getLong(key));
		} else
		{
		    setter.invoke(obj, json.get(key));
		}
	    }
	} catch (Exception ex)
	{
	    throw ex;
	}
    }

    public static Snake[] fromJsonToSnake(JSONObject json)
    {
	JSONArray snakesJson = json.getJSONArray("players");
	Iterator<Object> iterator = snakesJson.iterator();

	Snake[] snakes = new Snake[snakesJson.length()];
	int snakeIndex = 0;

	while (iterator.hasNext())
	{
	    JSONObject snakeJSON = (JSONObject) iterator.next();

	    Snake snake = new Snake();
	    snake.setScore(snakeJSON.getInt("score"));
	    snake.setAlive(snakeJSON.getBoolean("alive"));
	    snake.setPlayerName(snakeJSON.getString("playerName"));
	    snake.setLength(snakeJSON.getInt("length"));

	    JSONArray snakeBodyJson = snakeJSON.getJSONArray("snakeBody");
	    Iterator<Object> it = snakeBodyJson.iterator();

	    int i = 0;
	    Vector2[] snakeBody = new Vector2[snakeBodyJson.length()];

	    while (it.hasNext())
	    {
		JSONObject obj = (JSONObject) it.next();
		snakeBody[i] = fromJsonToVector2(obj);
		i++;
	    }

	    snake.setSnakeBody(snakeBody);

	    snakes[snakeIndex] = snake;
	    snakeIndex++;
	}

	return snakes;
    }

    public static Fruit fromJsonToFruit(JSONObject json)
    {
	Fruit fruit = null;

	Vector2 vector = fromJsonToVector2(json.getJSONObject("fruit").getJSONObject("position"));

	switch (json.getJSONObject("fruit").getString("name"))
	{
	case "cherry":
	    fruit = new Cherry(vector);
	    break;
	case "banana":
	    fruit = new Banana(vector);
	    break;
	case "apple":
	    fruit = new Apple(vector);
	    break;
	default:
	    break;
	}

	return fruit;
    }

    public static Timer fromJsonToTimer(JSONObject json)
    {
	Timer timer = new Timer(json.getJSONObject("timer").getInt("min"), json.getJSONObject("timer").getInt("sec"));
	return timer;
    }

    public static boolean[] fromJsonToSounds(JSONObject json)
    {
	JSONArray soundsID = json.getJSONArray("sounds");
	boolean[] sounds = new boolean[soundsID.length()];

	for (int i = 0; i < sounds.length; i++)
	{
	    sounds[i] = soundsID.optBoolean(i);
	}

	return sounds;
    }

    private static Vector2 fromJsonToVector2(JSONObject json)
    {
	Vector2 vector = new Vector2(json.getInt("x"), json.getInt("y"));
	return vector;
    }
}