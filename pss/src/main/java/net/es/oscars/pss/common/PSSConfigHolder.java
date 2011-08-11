package net.es.oscars.pss.common;

import java.util.HashMap;
import java.util.Map;

import net.es.oscars.utils.config.ConfigHelper;

public class PSSConfigHolder {
    @SuppressWarnings("rawtypes")
    private HashMap config;
    private static PSSConfigHolder instance;

    @SuppressWarnings("rawtypes")
    private PSSConfigHolder() {
        this.config = new HashMap();
    }
    public static PSSConfigHolder getInstance() {
        if (instance == null) {
            instance = new PSSConfigHolder();
        }
        return instance;
    }

    @SuppressWarnings("rawtypes")
    public HashMap getConfig() {
        return config;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void loadConfig(String filename) {
        Map tempConfig = ConfigHelper.getConfiguration(filename);
        this.config = new HashMap();
        this.config.putAll(tempConfig);

    }

}
