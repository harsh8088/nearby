package com.hrawat.nearby.activity.model.searchModel;

import java.util.List;

/**
 * Created by hrawat on 9/28/2017.
 */
public class PhotosModel {

    public Integer height;

    public List<String> html_attributions;

    public String photo_reference;

    public Integer width;

    public Integer getHeight() {
        return height;
    }

    public List<String> getHtmlAttributions() {
        return html_attributions;
    }

    public String getPhotoReference() {
        return photo_reference;
    }

    public Integer getWidth() {
        return width;
    }
}
