package com.putaria.bot;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.restaction.RoleAction;

import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

import com.google.gson.Gson;
import com.putaria.bot.commands.RegistryCommand;

import java.awt.Color;
import java.io.*;
import java.lang.System.Logger;
import java.util.List;

public class Commands extends ListenerAdapter {

    public BufferedReader bfr;
    public FileWriter fw;

    public Commands() {
        try {
            bfr = new BufferedReader(new FileReader("src/main/res/greetings.txt"));
        } catch (IOException e) {
            Bot.logger.log(Logger.Level.ERROR, e);
        }
    }

    @Override
    public void onGuildMemberJoin(@Nonnull GuildMemberJoinEvent event) {
        User user = event.getUser();
        if (!user.isBot()) {
            user.openPrivateChannel().queue((channel) -> channel.sendMessage("").queue());

        }
    }

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        if (!event.getAuthor().isBot()) {
            Message msg = event.getMessage();
            String content = msg.getContentRaw();
            String[] msgSplited = content.split("\\s");

            if (event.getMessage().getTextChannel().getIdLong() == 795039831507730453L
                    && RegistryCommand.registeringUser != null
                    && RegistryCommand.registeringUser.getIdLong() == event.getAuthor().getIdLong()) {
                PutariaUser user = new PutariaUser(msg.getAuthor().getName(), msg.getAuthor().getIdLong(),
                        event.getMessage().getContentRaw());
                Bot.config.users.add(user);
                String gsonWrite = new Gson().toJson(Bot.config).toString();
                try {
                    fw = new FileWriter("src/main/res/config.json");
                    fw.write(gsonWrite);
                    fw.flush();
                } catch (IOException e) {
                    //ignored
                }
                RegistryCommand.fila.remove(0);
                RegistryCommand.inExecution = false;
                RegistryCommand.registeringUser = null;
                if (!RegistryCommand.fila.isEmpty())
                    RegistryCommand.staticExecute(RegistryCommand.fila.get(0),
                            event.getGuild().getTextChannelById(795039831507730453L));
            }

            if (msgSplited[0].startsWith(Bot.config.prefix)) {
                User author = event.getAuthor();
                msgSplited[0] = msgSplited[0].substring(1);
                TextChannel channel = msg.getTextChannel();

                if (msgSplited[0].equalsIgnoreCase("registrar")) {
                    new RegistryCommand(author, channel).start(msg);
                } else if (msgSplited[0].equalsIgnoreCase("faction")) {
                    factionCommand(channel, msgSplited);
                } else if (author.getIdLong() == 262280704178651136L){ //Comandos proprios de Gambinho
                    if(msgSplited[0].equalsIgnoreCase("shutdown")) {
                        sendMessage(channel, "Desligando bot");
                       try {
                           fw.close();
                        } catch (IOException e) {
                           e.printStackTrace();
                        }
                       System.exit(0);
                    } else if(msgSplited[0].equalsIgnoreCase("clear")){
                        List<Message> messages = channel.getHistory().retrievePast(Integer.parseInt(msgSplited[1]) + 1).complete();
                        channel.deleteMessages(messages).complete();
                    }
                } else if(msg.getMember().getRoles().contains(msg.getGuild().getRoleById(720085917099884605L))){
                    //TODO comandos de admins
                }
            }
            if(event.getMessage().getContentRaw().equalsIgnoreCase("greetings test")){

                try {
                    sendMessage(event.getChannel(), bfr.readLine());
                } catch (IOException e) {
                    sendMessage(event.getChannel(), "Arquivo não encontrado");
                }
            }
            if(event.getMessage().getContentRaw().equalsIgnoreCase("rc")){
                RoleAction ac = event.getGuild().createRole();
                ac.setColor(Color.RED);
                ac.setName("Registering");
                Role roleToAdd = ac.complete();
                ac.queue();

                var ca = event.getGuild().createTextChannel("Registros").complete().getManager().getChannel();
                ca.createPermissionOverride(roleToAdd).setAllow(Permission.VIEW_CHANNEL, Permission.MESSAGE_WRITE, Permission.MESSAGE_HISTORY);
                ca.createPermissionOverride(event.getGuild().getRoleById("559201287728529422")).setDeny(Permission.ALL_CHANNEL_PERMISSIONS);
                ca.getManager().queue();
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
