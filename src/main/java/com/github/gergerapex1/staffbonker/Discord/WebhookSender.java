package com.github.gergerapex1.staffbonker.Discord;

import com.github.gergerapex1.staffbonker.Config.Config;
import com.github.gergerapex1.staffbonker.Database.DatabaseConnection;
import com.github.gergerapex1.staffbonker.StaffBonker;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import org.slf4j.Logger;
import space.arim.libertybans.api.ConsoleOperator;
import space.arim.libertybans.api.Operator;
import space.arim.libertybans.api.PlayerOperator;
import space.arim.libertybans.api.event.PardonEvent;
import space.arim.libertybans.api.event.PostPardonEvent;
import space.arim.libertybans.api.punish.DraftPunishment;
import space.arim.libertybans.api.punish.Punishment;

import java.awt.*;
import java.util.UUID;

public class WebhookSender {
    private static final Logger logger = StaffBonker.getLogger();
    public static MessageEmbed buildEmbed(DraftPunishment punishment) {
        String operator = getOperator(punishment.getOperator());
        String report = String.format("%s Report", "e");
        String victimUsername = String.format("`%s`", new DatabaseConnection().getUsername(UUID.fromString(retrieveUUID(punishment.getVictim().toString()))));
        EmbedBuilder embedBuilder = new EmbedBuilder()
                .setTitle(report)
                .addField("Executed By", operator,true )
                .addField("Offender", victimUsername, true)
                .addField("Reason", punishment.getReason(), false);
        switch (punishment.getType()) {
            case BAN:
                embedBuilder.setTitle("Ban Report");
                embedBuilder.setColor(new Color(0,0,0));
                break;
            case MUTE:
                embedBuilder.setTitle("Mute Report");
                embedBuilder.setColor(new Color(255, 0, 0));
                break;
            case WARN:
                embedBuilder.setTitle("Warn Report");
                embedBuilder.setColor(new Color(135, 206, 235));
                break;
        }
        return embedBuilder.build();
    }
    public static MessageEmbed buildEmbedPardon(PostPardonEvent postPardonEvent) {
        Punishment punishment = postPardonEvent.getPunishment();
        String operator = getOperator(punishment.getOperator());
        String victimUsername = String.format("`%s`", new DatabaseConnection().getUsername(UUID.fromString(retrieveUUID(punishment.getVictim().toString()))));
        EmbedBuilder embedBuilder = new EmbedBuilder()
                .addField("Executed By", operator,true)
                .addField("Offender", victimUsername, true)
                .addField("Reason", punishment.getReason(), false);
        switch (punishment.getType()) {
            case BAN:
                embedBuilder.setTitle("Ban Pardon Punishment Report");
                embedBuilder.setColor(new Color(0,0,0));
                break;
            case MUTE:
                embedBuilder.setTitle("Mute Pardon Punishment Report");
                embedBuilder.setColor(new Color(255, 0, 0));
                break;
            case WARN:
                embedBuilder.setTitle("Warn Pardon Punishment Report");
                embedBuilder.setColor(new Color(135, 206, 235));
                break;
        }
        return embedBuilder.build();
    }
    public static void sendMessage(MessageEmbed embed, JDA client) {
        TextChannel textChannel = client.getTextChannelById(new Config().getChannelId());
        assert textChannel != null;
        if (textChannel.canTalk()) {
            logger.info("Sending");
            textChannel.sendMessageEmbeds(embed).queue();
        } else {
            logger.info(String.format("I can't send messages in channel %s .·´¯`(>▂<)´¯`·. Please enable SEND_MESSAGES on that channel.", textChannel.getName()));
        }
    }
    private static String retrieveUUID(String notFinalUUID) {
        if(notFinalUUID.contains("PlayerOperator [uuid=")) {
            return notFinalUUID.replace("}", "").replace("PlayerOperator [uuid=", "");
        } else {
            return notFinalUUID.replace("}", "").replace("PlayerVictim{uuid=", "");
        }
    }
    private static String getOperator(Operator operator) {
        if (operator.toString().equals("ConsoleOperator.INSTANCE")) {
            return "CONSOLE";
        } else if(operator instanceof PlayerOperator) {
            return new DatabaseConnection().getUsername(((PlayerOperator) operator).getUUID());
        }
        return null;
    }
}
