package App;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public enum ConfiguraionManager {
    INSTANCE;

    public static final String CONFIG_FILE = "/application.properties";
    private final Properties properties;

    ConfiguraionManager() {
        try (InputStream is = getClass().getResourceAsStream(CONFIG_FILE)) {
            properties = new Properties();
            properties.load(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
