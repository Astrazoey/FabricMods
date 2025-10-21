package com.astrazoey.scorch;

import net.fabricmc.loader.api.FabricLoader;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class Config {
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("scorch.properties");

    private static final String THINNER_NETHER_FOG_KEY = "thinner-nether-fog";
    public static boolean thinnerNetherFogValue = true;

    private static final String LOW_PYRACK_SPAWN_RATE = "low-pyrack-spawn-rate";
    public static int lowPyrackSpawnRate = 2;

    private static final String HIGH_PYRACK_SPAWN_RATE = "high-pyrack-spawn-rate";
    public static int highPyrackSpawnRate = 8;

    private static final String BEACON_RANGE_MODIFIER = "beacon-range-modifier";
    public static int beaconRangeModifier = 30;

    public static void save() {
        Properties props = new Properties();
        read(props);

        if (!Files.exists(CONFIG_PATH)) {
            try {
                Files.createFile(CONFIG_PATH);
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }

        try (OutputStream out = Files.newOutputStream(CONFIG_PATH)) {
            props.store(out, "Scorch Configuration");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void load() {
        Properties props = new Properties();
        if (!Files.exists(CONFIG_PATH)) {
            try {
                Files.createFile(CONFIG_PATH);
                save();
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }

        try (InputStream stream = Files.newInputStream(CONFIG_PATH)) {
            props.load(stream);
            assign(props);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void read(Properties props) {
        props.setProperty(THINNER_NETHER_FOG_KEY, String.valueOf(thinnerNetherFogValue));
        props.setProperty(LOW_PYRACK_SPAWN_RATE, String.valueOf(lowPyrackSpawnRate));
        props.setProperty(HIGH_PYRACK_SPAWN_RATE, String.valueOf(highPyrackSpawnRate));
        props.setProperty(BEACON_RANGE_MODIFIER, String.valueOf(beaconRangeModifier));
    }

    public static void assign(Properties props) {
        thinnerNetherFogValue = defaultBoolean(props.getProperty(THINNER_NETHER_FOG_KEY), true);
        lowPyrackSpawnRate = defaultInteger(props.getProperty(LOW_PYRACK_SPAWN_RATE), 2);
        highPyrackSpawnRate = defaultInteger(props.getProperty(HIGH_PYRACK_SPAWN_RATE), 8);
        beaconRangeModifier = defaultInteger(props.getProperty(BEACON_RANGE_MODIFIER), 30);
    }

    private static boolean defaultBoolean(String bool, boolean defaultOption) {
        return bool == null ? defaultOption : Boolean.parseBoolean(bool);
    }

    private static int defaultInteger(String amount, int defaultOption) {
        return amount == null ? defaultOption : Integer.parseInt(amount);
    }
}
