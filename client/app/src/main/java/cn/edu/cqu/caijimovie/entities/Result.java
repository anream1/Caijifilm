package cn.edu.cqu.caijimovie.entities;

import com.google.gson.JsonObject;

public class Result {
    private int returnValue;
    private String returnMessage;
    private JsonObject object;


    public int getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(int returnValue) {
        this.returnValue = returnValue;
    }

    public String getReturnMessage() {
        return returnMessage;
    }

    public void setReturnmessage(String returnMessage) {
        this.returnMessage = returnMessage;
    }

    public JsonObject getObject() {
        return object;
    }

    public void setObject(JsonObject object) {
        this.object = object;
    }


}
