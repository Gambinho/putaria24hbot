package com.putaria.bot.commands;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

public class PrisonCommand extends Command {

    Member memberToPrison;

    public PrisonCommand(User caller, TextChannel tc, Member member) {
        super(caller, tc);
        memberToPrison = member;
    }

    @Override
    public void execute(Message msg) {
        msg.getGuild().modifyMemberRoles(memberToPrison, msg.getGuild().getRoleById(795106088604663818L)).queue();
        msg.getChannel().sendMessage("<@" + memberToPrison.getId() + "> foi preso por ser odacu, furry ou brony :ninjadasnevestraficante:").queue();
    }

    @Override
    public void start(Message msg) {
        // TODO Auto-generated method stub

    }
    
}
