package com.github.gergerapex1.staffbonker.Config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.github.gergerapex1.staffbonker.StaffBonker;

import java.io.File;

public class Config {
    private StaffBonker main;
    private static ConfigClass variables;

    public Config() {
        File file = new File(StaffBonker.getDataDir().toString(), "config.yml");
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            if (!file.exists()) {
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                DatabaseAuthConfig databaseAuth = new DatabaseAuthConfig("localhost", 3306, "libertybans", "root", "");
                ConfigClass defaultClass = new ConfigClass(1, "notyourbottokenpleasechangeitlol", "1234567891011121314151617181920", databaseAuth);
                mapper.writeValue(file, defaultClass);
            } else {
                variables = mapper.readValue(file, ConfigClass.class);
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public String getToken() {
        return variables.getToken();
    }
    public String getChannelId() {
        return variables.getChannelid();
    }
    public DatabaseAuthConfig getAuth() {
        return variables.getAuth();
    }
}
