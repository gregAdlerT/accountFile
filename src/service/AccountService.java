package service;

import dto.requestDto.PostRequestDto;
import dto.requestDto.UserRequestDto;
import dto.responswDto.PostResponseDto;
import dto.responswDto.RegionResponseDto;
import dto.responswDto.UserResponseDto;
import models.Post;
import models.Region;
import models.Roles;
import models.User;
import repositories.implementations.PostRepositoryImpl;
import repositories.implementations.RegionRepositoryImpl;
import repositories.implementations.UserRepositoryImpl;

import java.io.Serializable;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class AccountService implements IAccountService{
    UserRepositoryImpl userRepository;
    PostRepositoryImpl postRepository;
    RegionRepositoryImpl regionRepository;

    public AccountService(UserRepositoryImpl userRepository, PostRepositoryImpl postRepository, RegionRepositoryImpl regionRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.regionRepository = regionRepository;
    }

    @Override
    public UserResponseDto addUser(UserRequestDto userRequestDto) throws Exception {
        if (userRepository.getUserById(userRequestDto.getId())!=null){
            throw new Exception("User with id: "+userRequestDto.getId()+" already exists");
        }
        Region region=regionRepository.getByName(userRequestDto.getRegion());
        if (region==null){
            long lastId=regionRepository.getLastId();
           region=regionRepository.save(new Region(lastId+1,userRequestDto.getRegion()));
        }
        User user=new User(userRequestDto.getId(),
                userRequestDto.getFirstName(),
                userRequestDto.getLastName(),
                region, Roles.USER.toValue(),
                new ArrayList<Post>());
        
        userRepository.addNewUser(user);
        return toUserResponseDto(user);
    }
    
    @Override
    public List<UserResponseDto> getAllUsers() throws Exception {
        List<User>users=userRepository.getAllUsers();
        if (users.isEmpty()){
            throw new Exception("No users not found");
        }
        return toListUserResponseDto(users);
    }

    @Override
    public List<RegionResponseDto> getAllRegions() {
        List<Region>regions=regionRepository.getAll();
        return toListRegionResponseDto(regions);
    }

    @Override
    public PostResponseDto addPost(PostRequestDto postRequestDto) {
        long lastPostId=postRepository.getLastId();
        Date date=new Date();
        Post post=new Post(lastPostId+1,postRequestDto.getContent(),date,date);
        postRepository.addPost(post);
        User user=userRepository.getUserById(postRequestDto.getUserId());
        user.getPosts().add(post);
        userRepository.updateUser(user);
        return new PostResponseDto(post.getId(),post.getContent(),post.getCreated(),post.getUpdated());
    }

    private List<RegionResponseDto> toListRegionResponseDto(List<Region> regions) {
        return regions.stream().map(r->toRegionResponseDto(r)).collect(Collectors.toList());
    }

    private List<UserResponseDto> toListUserResponseDto(List<User> users) {
        return users.stream().map(u->toUserResponseDto(u)).collect(Collectors.toList());
    }

    private UserResponseDto toUserResponseDto(User user) {
        return new UserResponseDto(user.getId(),
                user.getFirstName(),
                user.getLastName(),
                toListPostResponseDto(user.getPosts()),toRegionResponseDto(user.getRegion()),Roles.fromValue(user.getRole()));
    }

    private RegionResponseDto toRegionResponseDto(Region region) {
        return new RegionResponseDto(region.getId(),region.getName());
    }

    private List<PostResponseDto> toListPostResponseDto(List<Post> posts) {
        return posts.stream().map(p->toPostResponseDto(p)).collect(Collectors.toList());
    }

    private PostResponseDto toPostResponseDto(Post post) {
        return new PostResponseDto(post.getId(),post.getContent(),post.getCreated(),post.getUpdated());
    }



}
