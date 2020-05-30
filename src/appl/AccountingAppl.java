package appl;

import dto.requestDto.PostRequestDto;
import dto.requestDto.UserRequestDto;
import dto.responswDto.RegionResponseDto;
import dto.responswDto.UserResponseDto;
import repositories.implementations.PostRepositoryImpl;
import repositories.implementations.RegionRepositoryImpl;
import repositories.implementations.UserRepositoryImpl;
import service.AccountService;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

public class AccountingAppl {
    static final  File userFile=new File("users.txt");
    static final File postFile=new File("posts.txt");
    static final File regionFile=new File("regions.txt");

    public static void main(String[] args) throws Exception {
        AccountService accountService =new AccountService(new UserRepositoryImpl(userFile.toPath()),
                                                           new PostRepositoryImpl(postFile.toPath()),
                                                           new RegionRepositoryImpl(regionFile.toPath()));

        UserRequestDto userRequestDto= new UserRequestDto(112,"Greg","Gregos","Israel");
        accountService.addUser(userRequestDto);
        
        List<RegionResponseDto>regionResponseDtos=accountService.getAllRegions();
        for (RegionResponseDto regionResponseDto : regionResponseDtos) {
            System.out.println(regionResponseDto);
        }
        accountService.addPost(new PostRequestDto(112,"bla bla Foo65 5-"));

        List<UserResponseDto>userResponseDtoList=accountService.getAllUsers();
        for (UserResponseDto user:userResponseDtoList){
            System.out.println(user);
        }
        
    }
}
