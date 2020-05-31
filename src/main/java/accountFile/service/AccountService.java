package main.java.accountFile.service;

import main.java.accountFile.dto.requestDto.PostRequestDto;
import main.java.accountFile.dto.requestDto.UserRequestDto;
import main.java.accountFile.dto.responseDto.PostResponseDto;
import main.java.accountFile.dto.responseDto.RegionResponseDto;
import main.java.accountFile.dto.responseDto.UserResponseDto;
import main.java.accountFile.model.Post;
import main.java.accountFile.model.Region;
import main.java.accountFile.model.Role;
import main.java.accountFile.model.User;
import main.java.accountFile.repository.ioImpl.PostRepositoryImpl;
import main.java.accountFile.repository.ioImpl.RegionRepositoryImpl;
import main.java.accountFile.repository.ioImpl.UserRepositoryImpl;

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
        if (userRepository.getById(userRequestDto.getId())!=null){
            throw new Exception("User with id: "+userRequestDto.getId()+" already exists");
        }
        Region region=regionRepository.getByName(userRequestDto.getRegion());
        if (region==null){
            long lastId=regionRepository.getLastId();
           region=regionRepository.add(new Region(lastId+1,userRequestDto.getRegion()));
        }
        User user=new User(userRequestDto.getId(),
                userRequestDto.getFirstName(),
                userRequestDto.getLastName(),
                region.getId(),
                Role.USER.toValue(),
                new ArrayList<>());
        
        userRepository.add(user);
        return new UserResponseDto(user.getId(),
                user.getFirstName(),
                user.getLastName(),
                new ArrayList<>(),
                toRegionResponseDto(region),
                Role.USER);
    }
    
    @Override
    public List<UserResponseDto> getAllUsers() throws Exception {
        List<User>users=userRepository.getAll();
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
        postRepository.add(post);
        User user=userRepository.getById(postRequestDto.getUserId());
        user.getPosts().add(post.getId());
        userRepository.update(user);
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
                getUserPosts(user.getPosts()),
                getUserRegion(user.getRegionId()),
                Role.fromValue(user.getRole()));
    }

    private List<PostResponseDto> getUserPosts(List<Long> postIds) {
        List<Post>postList=new ArrayList<>();
        for (Long postId : postIds) {
           Post post= postRepository.getById(postId);
           postList.add(post);
        }
        return toListPostResponseDto(postList);
    }

    private RegionResponseDto getUserRegion(long regionId) {
        Region region=regionRepository.getById(regionId);
        return toRegionResponseDto(region);
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
