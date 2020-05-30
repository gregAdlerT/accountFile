package repositories.interfaces;

import models.Post;

import java.util.List;

public interface IPostRepo {
    Post addPost(Post post);
    Post updatePost(Post post);
    Post getById(long id);
    List<Post> getAll();
    boolean deleteById(long id);
    long getLastId();
}
