package com.caiji.caijifilm.pojo;

public class Result {
    /**返回值**/
    private int returnvalue;
    /**返回信息**/
    private String returnmessage;
    /**对象信息**/
    private Object object;

    public void setObject(Object object) {
        this.object = object;
    }

    public void setReturnMessage(String returnmessage) {
        this.returnmessage = returnmessage;
    }

    public void setReturnValue(int returnvalue) {
        this.returnvalue = returnvalue;
    }

    public Object getObject() {
        return object;
    }

    public String getReturnmessage() {
        return returnmessage;
    }

    public int getReturnValue() {
        return returnvalue;
    }
}
