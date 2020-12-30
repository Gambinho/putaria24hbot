package com.putaria.bot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.security.auth.login.LoginException;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

public class Bot {

    public static JDA jda;
    public static String prefix = ">";
    public static Config config;

    public static void main(String[] args) throws LoginException {

        try {
            config = new Gson().fromJson(new FileReader("src/main/res/config.json"), Config.class);
        } catch (JsonSyntaxException | JsonIOException | FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        System.out.println("Version: " + config.version);
        jda = JDABuilder.createDefault(System.getenv("AUTH_TOKEN")).build();

        jda.getPresence().setActivity(Activity.playing("no Putaria24h"));
        jda.addEventListener(new Commands());

    }
}