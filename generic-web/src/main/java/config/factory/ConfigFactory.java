package config.factory;


import config.FrameworkConfig;
import org.aeonbits.owner.ConfigCache;

public final class ConfigFactory {

    private ConfigFactory(){}

    public static FrameworkConfig getConfig(){
        //return ConfigCache.getOrCreate(FrameworkConfig.class);
        return org.aeonbits.owner.ConfigFactory.create(FrameworkConfig.class);
    }
}
