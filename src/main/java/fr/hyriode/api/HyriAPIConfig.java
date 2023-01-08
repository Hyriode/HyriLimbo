package fr.hyriode.api;

import fr.hyriode.api.config.IHyriAPIConfig;
import fr.hyriode.api.config.MongoDBConfig;
import fr.hyriode.api.config.RedisConfig;

/**
 * Created by AstFaster
 * on 05/01/2023 at 21:20
 */
public class HyriAPIConfig implements IHyriAPIConfig {

    private final boolean devEnvironment;
    private final boolean hyggdrasil;
    private final RedisConfig redis;
    private final MongoDBConfig mongoDB;

    public HyriAPIConfig(boolean devEnvironment, boolean hyggdrasil, RedisConfig redis, MongoDBConfig mongoDB) {
        this.devEnvironment = devEnvironment;
        this.hyggdrasil = hyggdrasil;
        this.redis = redis;
        this.mongoDB = mongoDB;
    }

    @Override
    public boolean isDevEnvironment() {
        return this.devEnvironment;
    }

    @Override
    public boolean withHyggdrasil() {
        return this.hyggdrasil;
    }

    @Override
    public RedisConfig getRedisConfig() {
        return this.redis;
    }

    @Override
    public MongoDBConfig getMongoDBConfig() {
        return this.mongoDB;
    }

}
