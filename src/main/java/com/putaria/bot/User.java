package com.putaria.bot;

public class User {
    public String name;
    public long ID;
    public String minecraftNickname;
    
    public boolean is(User user){
        return this.ID == user.ID;
    }
}
