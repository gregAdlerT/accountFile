package dto.responswDto;

public class RegionResponseDto {
    private long id;
    private String name;

    public RegionResponseDto(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "RegionResponseDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
