package com.putaria.bot;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.io.*;
import java.util.List;

public class Commands extends ListenerAdapter {

    public BufferedReader bfr;

    public Commands() {
        try {
            bfr = new BufferedReader(new FileReader("src/main/res/greetings.txt"));
        } catch (IOException e){ 
            System.err.println(e); 
        }
    }

    @Override
    public void onGuildMemberJoin(@Nonnull GuildMemberJoinEvent event) {
        User user = event.getUser();
        if(!user.isBot()){
            user.openPrivateChannel().queue((channel) -> {
                channel.sendMessage("").queue();
            });

        }
    }

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event){
        if(!event.getAuthor().isBot()){
            Message msg = event.getMessage();
            String content = msg.getContentRaw();
            String[] msgSplited = content.split("\\s");

            if(msgSplited[0].startsWith(Bot.config.prefix)){
                User author = event.getAuthor();
                List<Role> authorRoles = event.getMember().getRoles();
                String name = author.getName();
                msgSplited[0] = msgSplited[0].substring(1);
                TextChannel channel = msg.getTextChannel();

                if(msgSplited[0].equalsIgnoreCase("faction")){
                    factionCommand(channel, msgSplited);
                }
            }
            if(event.getMessage().getContentRaw().equalsIgnoreCase("greetings test")){

                try {
                    sendMessage(event.getChannel(), bfr.readLine());
                } catch (IOException e) {
                    sendMessage(event.getChannel(), "Arquivo não encontrado");
                }
            }
        }
    }

    public void factionCommand(TextChannel channel,String[] command){
        if(command[1].equalsIgnoreCase("create")){
            sendMessage(channel, "https://forms.gle/ZBQMHzM456XvV7wP6");
        }
    }

    public void sendMessage(TextChannel channelToSend, String messageToSend){
        channelToSend.sendMessage(messageToSend).queue();
    }
}
