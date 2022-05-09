package com.github.gergerapex1.staffbonker.Config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.LoggerFactory;

import java.util.logging.Logger;

public class DatabaseAuthConfig {
    public String host;
    public Integer port;
    public String databaseName;
    public String user;
    public String password;
    @JsonCreator
    public DatabaseAuthConfig(
            @JsonProperty("host") String host,
            @JsonProperty("port") int port,
            @JsonProperty("database") String databaseName,
            @JsonProperty("user") String user,
            @JsonProperty("password") String password
    ) {
        this.host = host;
        this.port = port;
        this.databaseName = databaseName;
        this.user = user;
        this.password = password;
    }
    public String getURI() {
        return String.format("jdbc:mariadb://%s:%d/%s", host, port, databaseName);
    }

    public String getPassword() {
        return password;
    }

    public String getUser() {
        return user;
    }
}