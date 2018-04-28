package com.kiana.sjt.myinfocollecter.book.model;

import java.io.Serializable;

/**
 * 一本书
 * Created by taodi on 2018/4/28.
 */

public class OneBookModel implements Serializable{

    /**
     * id
     */
    private String id;

    /**
     * 书名
     */
    private String name;

    /**
     * 作者
     */
    private String author;

    /**
     * 描述
     */
    private String description;

    /**
     * 封面
     */
    private String cover;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }
}
