package com.imooc.demo.model;

/**
 * Function:
 * Create date on 16/8/13.
 *
 * @author Conquer
 * @version 1.0
 */
public class ChatMessage {

    public ChatMessage(int myID, int friendID, String name, String date, String content, String time, String avatarUrl, boolean isComeMessage) {
        this.myID = myID;
        this.friendID = friendID;
        this.name = name;
        this.date = date;
        this.content = content;
        this.time = time;
        this.avatarUrl = avatarUrl;
        this.isComeMessage = isComeMessage;
    }

    private int myID;

    private int friendID;

    private String name;

    private String date;

    private String content;

    private String time;

    private String avatarUrl;

    private boolean isComeMessage = true;


    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean getMsgType() {
        return isComeMessage;
    }

    public void setMsgType(boolean isComMsg) {
        isComeMessage = isComMsg;
    }

    public ChatMessage() {
    }

    public int getMyID() {
        return myID;
    }

    public void setMyID(int myID) {
        this.myID = myID;
    }

    public int getFriendID() {
        return friendID;
    }

    public void setFriendID(int friendID) {
        this.friendID = friendID;
    }

    public boolean isComeMessage() {
        return isComeMessage;
    }

    public void setComeMessage(boolean isComMeg) {
        this.isComeMessage = isComMeg;
    }
}
