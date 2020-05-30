package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {
    private long id;
    private String firstName;
    private String lastName;
    private Region region;
    private String role;
    private ArrayList<Post>posts;
    public User(long id, String firstName, String lastName, Region region, String role, ArrayList<Post> posts) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.region = region;
        this.role = role;
        this.posts = posts;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }

    public void setPosts(ArrayList<Post> posts) {
        this.posts = posts;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", region= " + region + ", role='" + role + '\'' +
                ", posts=" + posts + "}\n";
    }

    public static long idToValue(String str){
        String[]arr=str.split("=");
        return Long.parseLong(arr[1]);
    }
    public static String firstNameToValue(String str){
        String[]arr=str.split("=");
        return arr[1];
    }
    public static String lastNameToValue(String str){
        String[]arr=str.split("=");
        return arr[1];
    }
    public static String roleToValue(String str){
        String[]arr=str.split("=");
        return arr[1];
    }
    
}
