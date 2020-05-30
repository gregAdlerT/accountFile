package dto.requestDto;

import java.util.PrimitiveIterator;

public class UserRequestDto {
    private long id;
    private String firstName;
    private String lastName;
    private String region;

    public UserRequestDto(long id, String firstName, String lastName, String region) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.region = region;
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

    public String getRegion() {
        return region;
    }
}
