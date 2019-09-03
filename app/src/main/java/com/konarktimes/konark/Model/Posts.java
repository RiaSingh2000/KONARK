package com.konarktimes.konark.Model;

public class Posts {
 int id;
 String title;
 String image_url;
 String content,summary;
 String created_at;
 String name;

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImage_url() {
        return image_url;
    }

    public String getContent() {
        return content;
    }

    public String getSummary() {
        return summary;
    }

    public String getCreated_at(){
        return created_at;
    }

    public Posts(int id, String title, String summary, String content, String image_url, String created_at, String name) {
        this.id = id;
        this.title = title;
        this.image_url = image_url;
        this.content = content;
        this.summary = summary;
        this.created_at = created_at;
        this.name =name;
    }
}
