package com.putaria.bot;

public class PutariaUser {
    public String name;
    public long ID;
    public String minecraftNickname;

    public PutariaUser(String name, long ID, String minecraftNickname){
        this.name = name;
        this.ID = ID;
        this.minecraftNickname = minecraftNickname;
    }
    
    public boolean is(PutariaUser user){
        return this.ID == user.ID;
    }
}
