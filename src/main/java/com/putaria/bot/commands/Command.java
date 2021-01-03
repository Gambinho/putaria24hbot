package com.putaria.bot.commands;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

public abstract class Command {
    
    protected String name;
    protected User caller;
    protected TextChannel channel;

    protected Command(User caller, TextChannel tc){
        this.name = caller.getName();
        this.caller = caller;
        this.channel = tc;
    }

    public abstract void execute(Message msg);

    public User getCaller(){
        return caller;
    }

    public String getCommandName(){
        return name;
    }

    public TextChannel getTextChannel(){
        return channel;
    }

    protected void sendMessage(String msg){
        channel.sendMessage(msg).queue();
    }

    public abstract void start(Message msg);

}