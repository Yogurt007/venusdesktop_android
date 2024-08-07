package com.huajia.venusdesktop.service.application.album;

/**
 * @Author: HuaJ1a
 * @Emal: 821759439@qq.com
 * @Date: 2024/3/3
 * @Description:
 */
public class AlbumBean {

    /**
     * 根据时间分组
     */
    private String id;

    /**
     * 照片列表
     */
    private String photoUrl;

    public AlbumBean(){
    }

    public AlbumBean(String id, String photoUrl) {
        this.id = id;
        this.photoUrl = photoUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
