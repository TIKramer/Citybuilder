package com.kramer.a18882652.citybiolder.Model;

public class Setting
{
    public static final int FLOAT = 0;
    public static final int INTERGER = 1;
    public static final int STRING = 2;

    private String name;
    private int type;
    public Object data;
    boolean valid;
    boolean lock;
  //  Validator errorMessage;

    public Setting(String name, int type)
    {

        this.name = name;
        this.type = type;
        this.lock = false;
        this.data = "";
    }

    public void setData(Object data)
    {
        this.data = data;
    }
    public Object getData()
    {
        return data;
    }

    public int getType()
    {
        return type;
    }

    public String getName() {
        return name;
    }

    public void setLock(Boolean lock)
    {
        this.lock = lock;
    }

    public boolean isLock() {
        return lock;
    }

    public String toString()
    {
        return "Name: " + this.name + " Type: " + this.type + " Data: " + this.data;
    }
}


