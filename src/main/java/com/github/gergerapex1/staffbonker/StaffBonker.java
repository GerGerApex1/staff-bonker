package com.github.gergerapex1.staffbonker;

import com.github.gergerapex1.staffbonker.Database.DatabaseConnection;
import com.github.gergerapex1.staffbonker.Discord.Client;
import com.google.inject.Inject;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import net.dv8tion.jda.api.JDA;
import org.slf4j.Logger;

import java.nio.file.Path;

@Plugin(
        id = "staff-bonker",
        name = "Staff Bonker",
        version = "1.0-SNAPSHOT"
)
public class StaffBonker {
    private static Logger logger;
    private static JDA client;
    private static Path dataDir;
    @Inject
    public StaffBonker(ProxyServer server, Logger logger, @DataDirectory Path dataDirectory) {
        this.logger = logger;
        logger.info("StaffBonker is now ready to report punishments by staff. Woohoo!");
        dataDir = dataDirectory;
        new LibertyBansListener();
        client = Client.loginClient();
    }
    public static Logger getLogger() {
        return logger;
    }
    public static JDA getClient() {
        return client;
    }
    public static Path getDataDir() {
        return dataDir;
    }
}
