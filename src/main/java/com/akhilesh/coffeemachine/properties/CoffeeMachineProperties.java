package com.akhilesh.coffeemachine.properties;

import com.akhilesh.coffeemachine.properties.factory.JsonPropertySourceFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

/**
 * Machine configuration is read from configuration file
 * at /resources/input.json
 */

@Component
@PropertySource(value = "classpath:input.json",
        factory = JsonPropertySourceFactory.class)
@ConfigurationProperties
public class CoffeeMachineProperties {
   LinkedHashMap<String, ?> machine;

    public LinkedHashMap<String, ?> getMachine() {
        return machine;
    }

    public void setMachine(LinkedHashMap<String, ?> machine) {
        this.machine = machine;
    }
}
