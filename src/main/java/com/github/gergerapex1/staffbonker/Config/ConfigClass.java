package com.github.gergerapex1.staffbonker.Config;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


public class ConfigClass {
    private final int version;
    private final String botToken;
    private final String channelId;
    private final DatabaseAuthConfig auth;
    @JsonCreator
    public ConfigClass(
            @JsonProperty("version") int version,
            @JsonProperty("botToken") String botToken,
            @JsonProperty("channelId") String channelId,
            @JsonProperty("databaseAuth") DatabaseAuthConfig auth
            ) {
        this.version = version;
        this.botToken = botToken;
        this.channelId = channelId;
        this.auth = auth;
    }
    public String getToken() {
        return botToken;
    }
    public String getChannelid() {
        return channelId;
    }
    public DatabaseAuthConfig getAuth() {
        return auth;
    }
}
