package service;

import dto.requestDto.PostRequestDto;
import dto.requestDto.UserRequestDto;
import dto.responswDto.PostResponseDto;
import dto.responswDto.RegionResponseDto;
import dto.responswDto.UserResponseDto;
import models.User;

import java.util.List;

public interface IAccountService {
    UserResponseDto addUser(UserRequestDto userRequestDto) throws Exception;
    List<UserResponseDto>getAllUsers() throws Exception;
    List<RegionResponseDto>getAllRegions();
    PostResponseDto addPost(PostRequestDto postRequestDto);
}
