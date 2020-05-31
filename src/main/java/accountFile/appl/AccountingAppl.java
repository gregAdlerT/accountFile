package main.java.accountFile.appl;

import main.java.accountFile.dto.requestDto.PostRequestDto;
import main.java.accountFile.dto.requestDto.UserRequestDto;
import main.java.accountFile.dto.responseDto.RegionResponseDto;
import main.java.accountFile.dto.responseDto.UserResponseDto;
import main.java.accountFile.repository.ioImpl.PostRepositoryImpl;
import main.java.accountFile.repository.ioImpl.RegionRepositoryImpl;
import main.java.accountFile.repository.ioImpl.UserRepositoryImpl;
import main.java.accountFile.service.AccountService;

import java.io.File;
import java.util.List;

public class AccountingAppl {

    public static void main(String[] args) throws Exception {
        AccountService accountService =new AccountService(new UserRepositoryImpl(),
                                                           new PostRepositoryImpl(),
                                                           new RegionRepositoryImpl());

        UserRequestDto userRequestDto= new UserRequestDto(112,"Greg","Gregos","Israel");
        accountService.addUser(userRequestDto);
        
       // List<RegionResponseDto>regionResponseDtos=accountService.getAllRegions();
//        for (RegionResponseDto regionResponseDto : regionResponseDtos) {
//            System.out.println(regionResponseDto);
//        }
//        accountService.addPost(new PostRequestDto(112,"bla bla Foo65 5-"));

        List<UserResponseDto>userResponseDtoList=accountService.getAllUsers();
        for (UserResponseDto user:userResponseDtoList){
            System.out.println(user);
        }
        
    }
}
