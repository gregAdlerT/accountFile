package main.java.accountFile.repository.ioImpl;

import main.java.accountFile.model.Post;
import main.java.accountFile.repository.interfaces.GenericRepository;
import main.java.accountFile.utils.FileManager;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class PostRepositoryImpl implements GenericRepository<Post,Long> {

    static final File postFile=new File("resources/posts.txt");
     static final SimpleDateFormat formatter=new SimpleDateFormat("dd/MM/yyyy");

    
    @Override
    public Post add(Post post) {
        List<String>posts= (ArrayList<String>) getAll().stream().map(p->toString(p)).collect(Collectors.toList());
        posts.add(toString(post));
        deleteAll();
        try{
            FileManager.write(posts,postFile);
        } catch (IOException e) {
            e.getMessage();
        }
        return post;
    }

    @Override
    public Post update(Post post) {
        List<Post>posts=getAll();
        Post postToUpdate = posts.stream().filter(r -> r.getId() == post.getId()).findFirst().orElse(null);
        postToUpdate.setContent(post.getContent());
        Date now=new Date();
        postToUpdate.setUpdated(now);
        posts.add(post);
        deleteAll();
        ArrayList<String>postsStr= (ArrayList<String>) posts.stream().map(p->toString(p)).collect(Collectors.toList());
        try{
            FileManager.write(postsStr,postFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return post;
    }

    @Override
    public Post getById(Long id) {
        Post post=getAll().stream().filter(p->p.getId()==id).findAny().orElse(null);
        return post;
    }

    @Override
    public List<Post> getAll() {
        List<String>posts=new ArrayList<>();
        if (isFileEmpty()){
            return new ArrayList<>();
        }
        try {
            String text= FileManager.read(postFile);
            String[] regionArr = text.split("POST_DATA");
            for (int i = 1; i < regionArr.length; i++) {
                posts.add(regionArr[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return toListPost(posts);
    }
    
    @Override
    public boolean deleteById(Long id) {
        List<Post>posts=getAll();
        Post postToRemove = posts.stream().filter(r -> r.getId() == id).findFirst().orElse(null);
        posts.remove(postToRemove);
        deleteAll();
        ArrayList<String>postsStr= (ArrayList<String>) posts.stream().map(p->toString(p)).collect(Collectors.toList());
        try{
            FileManager.write(postsStr,postFile);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    @Override
    public boolean deleteAll() {
        try {
            new FileOutputStream(postFile).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public long getLastId() {
        List<Post>posts=getAll();
        long lastId=0;
        if (posts.isEmpty()){
            return lastId;
        }
        lastId=posts.stream().max(Comparator.comparing(Post::getId)).get().getId();
        return lastId;
    }

    private boolean isFileEmpty() {
        return postFile.length()<10;
    }

    public String toString(Post post) {
        return "POST_DATA{" +
                "id= " + post.getId() +
                ",content=<'" + post.getContent() + "'>content" +
                ", created= " + formatter.format(post.getCreated()) +
                ", updated= " + formatter.format(post.getUpdated()) + "}\n";
    }

    private List<Post> toListPost(List<String> posts) {
        return posts.stream().map(s->parseToPost(s)).collect(Collectors.toList());
    }

    public static Post parseToPost(String post) {
        long id=Long.parseLong(post.split(",")[0].split("=")[1].trim());
        String content=post.substring(post.indexOf("content=<")+10,post.indexOf(">content")-1);
        Date created= null;
        Date updated= null;
        try {
            created = formatter.parse(post.substring(post.indexOf("created= ")+9,post.indexOf("created= ")+19));
            updated = formatter.parse(post.substring(post.indexOf("updated= ")+9,post.length()-1));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return new Post(id,content,created,updated);
    }
}
