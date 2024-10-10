package com.huajia.venusdesktop.service.application.album;

/**
 * @Author: HuaJ1a
 * @Emal: 821759439@qq.com
 * @Date: 2024/3/3
 * @Description: 相册bean
 */
public class AlbumBean {

    /**
     * 创建时间
     */
    private String time;

    /**
     * 本地存储链接
     */
    private String fileUrl;

    /**
     * 数据类型 - 1、image 2、video
     */
    private String type;

    public AlbumBean(){
    }

    public AlbumBean(String time, String fileUrl) {
        this.time = time;
        this.fileUrl = fileUrl;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "AlbumBean{" +
                "time='" + time + '\'' +
                ", fileUrl='" + fileUrl + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
