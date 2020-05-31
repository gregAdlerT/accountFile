package main.java.accountFile.repository.ioImpl;

import main.java.accountFile.model.User;
import main.java.accountFile.repository.interfaces.GenericRepository;
import main.java.accountFile.utils.FileManager;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class UserRepositoryImpl implements GenericRepository<User,Long> {
    
    private static final File filePath=new File("resources/users.txt");
    
    @Override
    public User getById(Long id) {
        List<User>users= getAll();
        User user=users.stream().filter(u->u.getId()==id).findFirst().orElse(null);
        return user;
    }
    
    @Override
    public List<User> getAll() {
        if (isFileEmpty()){
            return new ArrayList<>();
        }
        try {
            String text= FileManager.read(filePath);
            String[] userArrWithEmptyFirst = text.split("USER_DATA");
            List<String>users=new ArrayList<>();
            for (int i = 1; i < userArrWithEmptyFirst.length; i++) {
                users.add(userArrWithEmptyFirst[i]);
            }
            return parseToListUsers(users);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public User add(User user) {
        ArrayList<String>users= (ArrayList<String>) getAll().stream().map(u->toString(u)).collect(Collectors.toList());
        users.add(toString(user));
        deleteAll();
        try {
            FileManager.write(users,filePath);
        }  catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
    

    @Override
    public boolean deleteById(Long id) {
        List<User>users= getAll();
        User userToRemove = users.stream().filter(u -> u.getId() == id).findFirst().orElse(null);
        users.remove(userToRemove);
        ArrayList<String>usersStr= (ArrayList<String>) users.stream().map(u->toString(u)).collect(Collectors.toList());
        deleteAll();
        try{
            FileManager.write(usersStr,filePath);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public User update(User user) {
        List<User>users= getAll();
        User userToRemove = users.stream().filter(u -> u.getId() == user.getId()).findFirst().orElse(null);
        users.remove(userToRemove);
        users.add(user);
        ArrayList<String>usersStr= (ArrayList<String>) users.stream().map(u->toString(u)).collect(Collectors.toList());
        deleteAll();
        try{
            FileManager.write(usersStr,filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public boolean deleteAll() {
        try {
            new FileOutputStream(filePath.toString()).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    private boolean isFileEmpty() {
        return filePath.length()<10;
    }

    private List<User> parseToListUsers(List<String> users) {
        return users.stream().map(str->parseToUser(str)).collect(Collectors.toList());
    }

    private User parseToUser(String userData){
        String[] userDataArr = userData.split(",");
        long id=idToValue(userDataArr[0]);
        String firstName=firstNameToValue(userDataArr[1]);
        String lastName=lastNameToValue(userDataArr[2]);
        long regionId=Long.parseLong(userDataArr[3].split("=")[1].trim());
        String role=roleToValue(userDataArr[4].trim());
        List<Long>posts=getPostList(userData);
        return new User(id,firstName,lastName,regionId,role,posts);
    }

    private List<Long> getPostList(String userData) {
        String strArray=userData.substring(userData.indexOf('[')+1,userData.indexOf(']'));
        if (strArray.length()<=0){
            return new ArrayList<>();
        }
        String[]posts=strArray.split(",");
        List<Long>postList=new ArrayList<>();
        for (int i=0;i<posts.length;i++){
            long id=Long.parseLong(posts[i].trim());
            postList.add(id);
        }
        return postList;
    }

    private String toString(User user) {
        return "USER_DATA{ id=" + user.getId() +
                ", firstName=" + user.getFirstName().trim() +
                ", lastName=" + user.getLastName().trim() + 
                ", regionId=" + user.getRegionId() +
                ", role=" + user.getRole().trim() +
                ", posts= " + user.getPosts() +
                "}\n";
    }


    private long idToValue(String str){
        String[]arr=str.split("=");
        return Long.parseLong(arr[1]);
    }
    private String firstNameToValue(String str){
        String[]arr=str.split("=");
        return arr[1];
    }
    private  String lastNameToValue(String str){
        String[]arr=str.split("=");
        return arr[1];
    }
    private  String roleToValue(String str){
        String[]arr=str.split("=");
        return arr[1];
    }



}
