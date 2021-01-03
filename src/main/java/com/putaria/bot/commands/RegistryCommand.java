package com.putaria.bot.commands;

import java.util.ArrayList;
import java.util.List;

import com.putaria.bot.Bot;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

public class RegistryCommand extends Command {

    public static final List<Member> fila = new ArrayList<>();
    public static boolean inExecution = false;
    public static Member registeringUser = null;
    public static List<Role> rolesHolder;
    public static final long ID = 795039831368663060L;

    public RegistryCommand(User caller, TextChannel tc) {
        super(caller, tc);
    }

    public static void staticExecute(Member user, TextChannel channelToExecute) {
        Guild guild = user.getGuild();
        RegistryCommand.inExecution = true;
        rolesHolder = user.getRoles();
        guild.modifyMemberRoles(user, guild.getRoleById(ID)).queue();
        staticSendMessage("<@" + user.getId() + "> Qual nick vc usa no Minecraft?", channelToExecute);
        RegistryCommand.registeringUser = user;

        //TODO

    }

    @Override
    public void start(Message msg) {
        var localCaller = msg.getMember();
        if(userAlreadyRegistered(localCaller)){
            sendMessage("Vc ja foi registrado");
            return;
        }
        RegistryCommand.fila.add(localCaller);
        if(fila.get(0) != localCaller){
            sendMessage("Alguns usuarios estao na sua frente, mas ja te coloquei na fila. Voce sera marcado quando sua vez chegar");
        }
        if(!inExecution) staticExecute(msg.getMember(), msg.getGuild().getTextChannelById(795039831507730453L));
    }

    /**
     * Ignored, execution moved to {@code staticExecute()}
     */
    @Override
    public void execute(Message msg) {
        //Ignored
    }
    
    public static void staticSendMessage(String msg, TextChannel tc){

        tc.sendMessage(msg).queue();
    }

    public boolean userAlreadyRegistered(Member user){
        for(int i = 0; i < Bot.config.users.size(); i++){
            if(user.getIdLong() == Bot.config.users.get(i).ID) return true;
        }
        return false;
    }
}