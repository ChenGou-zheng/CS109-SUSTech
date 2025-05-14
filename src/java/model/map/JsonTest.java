package model.map;

import org.json.JSONObject;

public class JsonTest {
    public static void main(String[] args) {
        JSONObject obj = new JSONObject();
        obj.put("name", "HuarongDao");
        System.out.println(obj.toString());
    }
}
