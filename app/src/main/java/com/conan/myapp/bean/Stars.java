package com.conan.myapp.bean;

/**
 * Author        conan
 * PublishDate   2017-03-25
 * Description   功能描述
 * Version       1.0
 * Updated       conan
 */
public class Stars {

    private String name;
    private String imageUrl;

    public Stars(String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
