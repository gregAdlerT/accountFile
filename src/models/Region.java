package models;

import java.io.Serializable;

public class Region implements Serializable {
    private long id;
    private String name;

    public Region(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "{ " +
                "id=" + id +
                ", name='" + name + " }";
    }
    public static long idToValue(String str){
        String[]arr=str.split("=");
        return Long.parseLong(arr[2]);
    }
    public static String nameToValue(String str){
        String[]arr=str.split("=");
        return arr[1];
    }
}
