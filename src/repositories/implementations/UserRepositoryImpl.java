package repositories.implementations;

import models.Post;
import models.Region;
import models.User;
import repositories.interfaces.IUserRepo;

import java.io.*;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

public class UserRepositoryImpl implements IUserRepo {
    
    Path filePath;
    
    public UserRepositoryImpl(Path filePath){
        this.filePath=filePath;
    }
    @Override
    public User getUserById(long id) {
        List<User>users=getAllUsers();
        User user=users.stream().filter(u->u.getId()==id).findFirst().orElse(null);
        return user;
    }
    
    @Override
    public List<User> getAllUsers() {
        if (isFileEmpty()){
            return new ArrayList<>();
        }
        ArrayList<String>users = null;
        
        try (FileInputStream in = new FileInputStream(filePath.toString());
             ObjectInputStream objectInputStream=new ObjectInputStream(in)){
            
            users= (ArrayList<String>) objectInputStream.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return parseToListUsers(users);
    }

    @Override
    public User addNewUser(User user) {
        ArrayList<String>users= (ArrayList<String>) getAllUsers().stream().map(User::toString).collect(Collectors.toList());
        users.add(user.toString());
        cleanFile();
        try(ObjectOutputStream outputStream=new ObjectOutputStream(new FileOutputStream(filePath.toString(),true))) {
            outputStream.writeObject(users);
        }  catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
    

    @Override
    public boolean removeUserById(long id) {
        List<User>users=getAllUsers();
        User userToRemove = users.stream().filter(u -> u.getId() == id).findFirst().orElse(null);
        users.remove(userToRemove);
        ArrayList<String>usersStr= (ArrayList<String>) users.stream().map(User::toString).collect(Collectors.toList());
        try(ObjectOutputStream outputStream=new ObjectOutputStream(new FileOutputStream(filePath.toString()))) {
            cleanFile();
            outputStream.writeObject(usersStr);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public User updateUser(User user) {
        List<User>users=getAllUsers();
        User userToRemove = users.stream().filter(u -> u.getId() == user.getId()).findFirst().orElse(null);
        users.remove(userToRemove);
        users.add(user);
        ArrayList<String>usersStr= (ArrayList<String>) users.stream().map(User::toString).collect(Collectors.toList());
        try(ObjectOutputStream outputStream=new ObjectOutputStream(new FileOutputStream(filePath.toString()))) {
            cleanFile();
            outputStream.writeObject(usersStr);
            return user;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void cleanFile() {
        try {
            new FileOutputStream(filePath.toString()).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isFileEmpty() {
        File file=filePath.toFile();
        return file.length()<10;
    }

    private List<User> parseToListUsers(List<String> users) {
        return users.stream().map(str->parseToUser(str)).collect(Collectors.toList());
    }

    private User parseToUser(String str){
        String userData=str.substring(5,str.length());
        String[] userDataArr = userData.split(",");
        System.out.println(userData);/////////////////////////////////////////////////////////////////////
        long id=User.idToValue(userDataArr[0]);
        String firstName=User.firstNameToValue(userDataArr[1]);
        String lastName=User.lastNameToValue(userDataArr[2]);
        Region region=new Region(Region.idToValue(userDataArr[3]),Region.nameToValue(userDataArr[4]));
        String role=User.roleToValue(userDataArr[5]);
        ArrayList<Post>posts=getPostList(userData);
        return new User(id,firstName,lastName,region,role,posts);
    }

    private ArrayList<Post> getPostList(String userData) {
        String strArray=userData.substring(userData.indexOf('[')+1,userData.indexOf(']'));
        if (strArray.length()<=0){
            return new ArrayList<>();
        }
        String[]posts=strArray.split("UserPost");
        ArrayList<Post>postList=new ArrayList<>();
        for (int i=0;i<posts.length;i++){
            Post post= null;
            post = Post.parseToPost(posts[i]);
            postList.add(post);
        }
        return postList;
    }

 
}
