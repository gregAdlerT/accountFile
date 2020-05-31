package main.java.accountFile.service;

import main.java.accountFile.dto.requestDto.PostRequestDto;
import main.java.accountFile.dto.requestDto.UserRequestDto;
import main.java.accountFile.dto.responseDto.PostResponseDto;
import main.java.accountFile.dto.responseDto.RegionResponseDto;
import main.java.accountFile.dto.responseDto.UserResponseDto;

import java.util.List;

public interface IAccountService {
    UserResponseDto addUser(UserRequestDto userRequestDto) throws Exception;
    List<UserResponseDto>getAllUsers() throws Exception;
    List<RegionResponseDto>getAllRegions();
    PostResponseDto addPost(PostRequestDto postRequestDto);
}
