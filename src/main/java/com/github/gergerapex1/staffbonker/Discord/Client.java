package com.github.gergerapex1.staffbonker.Discord;

import com.github.gergerapex1.staffbonker.Config.Config;
import com.github.gergerapex1.staffbonker.StaffBonker;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import org.slf4j.Logger;

import javax.security.auth.login.LoginException;

public class Client {
    private static final Logger logger = StaffBonker.getLogger();
    static JDABuilder builder = JDABuilder.createDefault(new Config().getToken())
            .setChunkingFilter(ChunkingFilter.NONE)
            .disableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGE_TYPING)
            .setLargeThreshold(50);
    private static JDA client;
    public static JDA loginClient() {
        try {
            client = builder.build();
            logger.info(String.format("Successfully logged in as %s.", client.getSelfUser().getName()));
            return client;
        } catch (LoginException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void shutdownClient() {
        client.shutdown();
        logger.info("Shutting down JDA Client. Hope I get started soon :=)");
    }
}
