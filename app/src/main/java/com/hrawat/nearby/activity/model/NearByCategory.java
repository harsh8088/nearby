package com.hrawat.nearby.activity.model;

/**
 * Created by hrawat on 9/25/2017.
 */
public class NearByCategory {
    private String icon;
    private String name;
    private String backgroundImage;

    public NearByCategory(String icon, String name, String backgroundImage) {
        this.icon = icon;
        this.name = name;
        this.backgroundImage = backgroundImage;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }
}
