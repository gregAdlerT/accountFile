package main.java.accountFile.dto.responseDto;

import main.java.accountFile.model.Role;

import java.util.List;

public class UserResponseDto {
    private long id;
    private String firstName;
    private String lastName;
    private List<PostResponseDto> posts;
    private RegionResponseDto region;
    private Role role;

    public UserResponseDto(long id, String firstName, String lastName, List<PostResponseDto> posts, RegionResponseDto region, Role role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.posts = posts;
        this.region = region;
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public List<PostResponseDto> getPosts() {
        return posts;
    }

    public RegionResponseDto getRegion() {
        return region;
    }

    public Role getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "UserResponseDto{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", posts=" + posts +
                ", region=" + region +
                ", role=" + role +
                '}';
    }
}
