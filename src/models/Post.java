package models;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

public class Post implements Serializable {
    private long id;
    private String content;
    private Date created;
    private Date updated;
    public static SimpleDateFormat formatter=new SimpleDateFormat("dd/MM/yyyy");

    public Post(long id, String content, Date created, Date updated) {
        this.id = id;
        this.content = content;
        this.created = created;
        this.updated = updated;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    @Override
    public String toString() {
        return "UserPost{" +
                "id= " + id +
                " , content='" + content + " '" +
                ", created= " + formatter.format(created) +
                ", updated= " + formatter.format(updated) + " }\n";
    }
    
    public static Post parseToPost(String post) {
        StringTokenizer tokenizer=new StringTokenizer(post);
        boolean buildContent=false;
        long id=0;
        StringBuilder stringBuilder=new StringBuilder();
        Date created=null;
        Date updated=null;
        while (tokenizer.hasMoreTokens()){
            if (tokenizer.nextToken().equals("id=")){
                id= Long.parseLong(tokenizer.nextToken(tokenizer.nextToken()));
            }
            if (tokenizer.nextToken().equals("'")){
                buildContent=false;
            }
            if (buildContent){
                stringBuilder.append(tokenizer.nextToken()+" ");
            }
            if (tokenizer.nextToken().equals("content='")){
                buildContent=true;
            }
            try {
                if (tokenizer.nextToken().equals("created=")){
                    created=formatter.parse(tokenizer.nextToken(tokenizer.nextToken()));
                }
                if (tokenizer.nextToken().equals("updated=")){
                    updated=formatter.parse(tokenizer.nextToken(tokenizer.nextToken()));
                }
            } catch (ParseException e) {
                e.getMessage();
            }

        }
        return new Post(id,stringBuilder.toString(),created,updated);
    }
}
