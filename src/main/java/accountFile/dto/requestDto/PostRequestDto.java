package main.java.accountFile.dto.requestDto;

public class PostRequestDto {
    private long userId;
    private String content;

    public PostRequestDto(long userId, String content) {
        this.userId = userId;
        this.content = content;
    }

    public long getUserId() {
        return userId;
    }

    public String getContent() {
        return content;
    }
}
