package com.caprispine.caprispine.pojo.chat;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sunil on 15-04-2018.
 */

public class AllChatPOJO {
    @SerializedName("id")
    private String id;
    @SerializedName("user_id")
    private String userId;
    @SerializedName("fri_id")
    private String friId;
    @SerializedName("is_admin")
    private String isAdmin;
    @SerializedName("date")
    private String date;
    @SerializedName("time")
    private String time;
    @SerializedName("msg")
    private String msg;
    @SerializedName("type")
    private String type;
    @SerializedName("file")
    private String file;
    @SerializedName("thumb")
    private String thumb;
    @SerializedName("user")
    private ChatUserPOJO user;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFriId() {
        return friId;
    }

    public void setFriId(String friId) {
        this.friId = friId;
    }

    public String getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(String isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public ChatUserPOJO getUser() {
        return user;
    }

    public void setUser(ChatUserPOJO user) {
        this.user = user;
    }
}
