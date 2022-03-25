package api.services;

import io.javalin.http.Context;
import org.json.JSONObject;

public class Response {

    public int status = 0;
    public boolean error = false;
    public String message = "";
    public JSONObject result = new JSONObject();

    public static Response fromJSON(JSONObject json){
        Response response = new Response();
        response.status = json.getInt("status");
        response.message = json.getString("message");
        response.error = json.getBoolean("error");
        response.result = json.getJSONObject("result");
        return response;
    }

    public static Response fromJSONString(String jsonString){
        return fromJSON(new JSONObject(jsonString));
    }

    public JSONObject toJSON(){
        JSONObject json = new JSONObject();
        json.put("status", status);
        json.put("error", error);
        json.put("message", message);
        json.put("result", result);
        return json;
    }

    public String toJSONString(){
        return toJSON().toString();
    }

    public static void setError(Context ctx, int status, String errorMessage){
        Response response = Response.fromJSONString(ctx.resultString());
        response.error = true;
        response.status = status;
        response.message = errorMessage;
        ctx.status(status);
        ctx.result(response.toJSONString());
    }

    public static void setResult(Context ctx, int status, String message, JSONObject result){
        Response response = Response.fromJSONString(ctx.resultString());
        response.message = message;
        response.status = status;
        ctx.status(status);
        if( result != null ){
            response.result = result;
        }
        ctx.result(response.toJSONString());
    }

}
