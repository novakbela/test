package main.java.challenge.util;

import main.java.challenge.IntegrationImpl;
import main.java.challenge.exception.IntegrationException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

public class IntegrationUtil {

    Properties properties = loadProperties();

    public void logError(HttpURLConnection conn) throws IOException {
        logError(new IntegrationException("HTTP call failed: error code : " + conn.getResponseCode()
                + " message: " + conn.getResponseMessage()));
    }

    public void logError(Exception e) {
        e.printStackTrace();
    }

    public Properties loadProperties() {
        Properties properties = new Properties();
        try {
            properties.load(IntegrationImpl.class.getResourceAsStream("../../resources/integration.properties"));
        } catch (IOException e) {
            logError(e);
        }
        return properties;
    }

    public URL assembleUrl(String extraPath) throws MalformedURLException {
        return new URL(properties.getProperty("baseUrl")
                + "/" + properties.getProperty("developerId.value")
                + "/" + extraPath);
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public boolean isNullOrBlank(String text) {
        return text == null || text.isBlank();
    }
}
