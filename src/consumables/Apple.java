package consumables;

import java.awt.Color;
import org.json.JSONObject;
import utils.JsonUtils;
import utils.Vector2;

public class Apple extends Fruit
{
    private final static int value = 10;
    private final static String name = "apple";

    public Apple(Vector2 position)
    {
	super(value, position, Color.RED, name);
    }

    public Apple()
    {
	super(value, Color.RED, name);
    }

    @Override
    public JSONObject toJson() throws Exception
    {
	JSONObject data = new JSONObject();
	String[] attributes =
	{ "name", "position" };
	data = JsonUtils.toJson(this, attributes);

	return data;
    }
}