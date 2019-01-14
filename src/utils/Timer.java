package utils;

import org.json.JSONObject;

public class Timer implements JSONSerializable
{
    private int min = 0;
    private int sec = 0;
    private long previousT = 0;

    public Timer(int minutes)
    {
	this.min = minutes;
    }

    public Timer(int minutes, int seconds)
    {
	this.min = minutes;
	this.sec = seconds;
    }

    public void start()
    {
	previousT = System.nanoTime();
	min--;
	sec = 59;
    }

    public void tick()
    {
	long nowT = System.nanoTime();

	if (nowT - previousT >= 1000000000)
	{
	    previousT = nowT;

	    if (sec == 0 && min != 0)
	    {
		min--;
		sec = 59;
	    } else
	    {
		sec--;
	    }
	}

    }

    public int getMinutes()
    {
	return min;
    }

    public int getSeconds()
    {
	return sec;
    }

    @Override
    public JSONObject toJson() throws Exception
    {
	JSONObject data = new JSONObject();
	
	data.put("min", min);
	data.put("sec", sec);

	return data;
    }
}