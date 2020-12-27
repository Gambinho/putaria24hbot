package com.putaria.bot;

import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;
import java.io.FileNotFoundException;

public class Bot {

    public static JDA jda;
    public static String prefix = ">>";

    public static void main(String[] args) throws LoginException {

        jda = JDABuilder.createDefault(System.getenv("AUTH_TOKEN")).build();

        jda.getPresence().setActivity(Activity.playing("no Putaria24h"));
        jda.addEventListener(new Commands());

    }
}