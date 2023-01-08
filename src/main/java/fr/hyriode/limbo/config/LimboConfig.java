package fr.hyriode.limbo.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.hyriode.api.HyriAPIConfig;
import fr.hyriode.api.config.MongoDBConfig;
import fr.hyriode.api.config.RedisConfig;
import fr.hyriode.limbo.util.IOUtil;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by AstFaster
 * on 06/01/2023 at 07:09
 */
public record LimboConfig(HyriAPIConfig hyriAPI) {

    public static final Path CONFIG_FILE = Paths.get("config.json");

    public static LimboConfig load() {
        System.out.println("Loading configuration...");

        final Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .serializeNulls()
                .create();

        final String json = IOUtil.loadFile(CONFIG_FILE);

        if (!json.equals("")) {
            return gson.fromJson(json, LimboConfig.class);
        } else {
            final LimboConfig config = new LimboConfig(new HyriAPIConfig(true, false, new RedisConfig("127.0.0.1", 6379, ""), new MongoDBConfig("", "", "127.0.0.1", 27017)));

            IOUtil.saveFile(CONFIG_FILE, gson.toJson(config));

            System.err.println("Please fill the configuration file!");
            System.exit(0);

            return config;
        }
    }

}
