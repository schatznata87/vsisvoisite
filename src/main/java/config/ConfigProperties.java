package config;

import org.aeonbits.owner.Config;


@Config.Sources(value = "file:.\\src\\main\\resources\\config.properties")
public interface ConfigProperties extends Config {

    String BASE_URL();

    long IMPLICIT_WAIT_TIME();
    long EXPLICIT_WAIT_TIME_LOW();
    long EXPLICIT_WAIT_TIME_HIGH();

}
