package main.java.accountFile.model;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {
    private long id;
    private String firstName;
    private String lastName;
    private long regionId;
    private String role;
    private List<Long> posts;
    

    public User(long id, String firstName, String lastName, long regionId, String role, List<Long> posts) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.regionId = regionId;
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

    public List<Long> getPosts() {
        return posts;
    }

    public void setPosts(List<Long> posts) {
        this.posts = posts;
    }

    public long getRegionId() {
        return regionId;
    }

    public void setRegion(long region) {
        this.regionId = region;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    
}
