package utils;

import org.json.JSONObject;

public interface JSONSerializable
{
    public JSONObject toJson() throws Exception;
}