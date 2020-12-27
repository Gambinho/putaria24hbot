package com.putaria.bot;

import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.io.*;

public class Commands extends ListenerAdapter {

    public BufferedReader bfr;

    public Commands() {
        try {
            bfr = new BufferedReader(new FileReader(new File("C:\\Users\\Matheus\\Pictures\\BotServer\\src\\main\\res\\greetings.txt")));
        } catch (IOException e){
            try{
                bfr = new BufferedReader(new FileReader(new File("greetings")));
            } catch (IOException e2){
                System.out.println(e + "\n" + e2);
            }
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
            String[] msgSplited = event.getMessage().getContentRaw().split("\\s");
            if(msgSplited[0].startsWith(Bot.prefix)){
                if(msgSplited[0].contains("faction")){
                    if(msgSplited[1].equalsIgnoreCase("create")){
                        sendMessage(event.getChannel(), "https://forms.gle/ZBQMHzM456XvV7wP6");
                    }
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

    public static void sendMessage(TextChannel channelToSend, String messageToSend){
        channelToSend.sendMessage(messageToSend).queue();
    }
}
