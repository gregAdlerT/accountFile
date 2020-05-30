package repositories.implementations;

import models.Post;
import repositories.interfaces.IPostRepo;

import java.io.*;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class PostRepositoryImpl implements IPostRepo {

    Path filePath;

    public PostRepositoryImpl(Path filePath){
        this.filePath=filePath;
    }
    
    @Override
    public Post addPost(Post post) {
        ArrayList<String>posts= (ArrayList<String>) getAll().stream().map(Post::toString).collect(Collectors.toList());
        cleanFile();
        posts.add(post.toString());
        try (ObjectOutputStream outputStream=new ObjectOutputStream(
                new FileOutputStream(filePath.toString(),true))){
            outputStream.writeObject(posts);
        } catch (IOException e) {
            e.getMessage();
        }
        return post;
    }

    @Override
    public Post updatePost(Post post) {
        List<Post>posts=getAll();
        cleanFile();
        Post postToUpdate = posts.stream().filter(r -> r.getId() == post.getId()).findFirst().orElse(null);
        postToUpdate.setContent(post.getContent());
        Date now=new Date();
        postToUpdate.setUpdated(now);
        posts.add(post);
        ArrayList<String>postsStr= (ArrayList<String>) posts.stream().map(Post::toString).collect(Collectors.toList());
        try(ObjectOutputStream outputStream=new ObjectOutputStream(new FileOutputStream(filePath.toString()))) {
            outputStream.writeObject(postsStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return post;
    }

    @Override
    public Post getById(long id) {
        Post post=getAll().stream().filter(p->p.getId()==id).findAny().orElse(null);
        return post;
    }

    @Override
    public List<Post> getAll() {
        ArrayList<String>posts=null;
        if (isFileEmpty()){
            return new ArrayList<>();
        }
        try (ObjectInputStream objectInputStream=new ObjectInputStream(new FileInputStream(filePath.toString()))){
            posts= (ArrayList<String>) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return toListPost(posts);
    }

    private List<Post> toListPost(List<String> posts) {
        return posts.stream().map(Post::parseToPost).collect(Collectors.toList());
    }

    @Override
    public boolean deleteById(long id) {
        List<Post>posts=getAll();
        Post postToRemove = posts.stream().filter(r -> r.getId() == id).findFirst().orElse(null);
        posts.remove(postToRemove);
        cleanFile();
        ArrayList<String>postsStr= (ArrayList<String>) posts.stream().map(Post::toString).collect(Collectors.toList());
        try(ObjectOutputStream outputStream=new ObjectOutputStream(new FileOutputStream(filePath.toString()))) {
            outputStream.writeObject(postsStr);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public long getLastId() {
        List<Post>posts=getAll();
        long lastId=0;
        if (posts.isEmpty()){
            return lastId;
        }
        lastId=posts.stream().max(Comparator.comparing(Post::getId)).get().getId();
        return lastId;
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

}
