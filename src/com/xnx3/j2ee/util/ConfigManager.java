package com.xnx3.j2ee.util;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.FileConfiguration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.apache.commons.io.IOUtils;
import java.io.StringWriter;
import java.util.HashMap;

public class ConfigManager {
    private static HashMap hashMap = new HashMap();
    private FileConfiguration config;

    private ConfigManager(String configFileName) {
        try {
            this.config = null;
            if (configFileName.toLowerCase().endsWith("xml")) {
                this.config = new XMLConfiguration(configFileName);
            } else if (configFileName.toLowerCase().endsWith("properties")) {
                this.config = new PropertiesConfiguration(configFileName);
            }
            this.config.setReloadingStrategy(new FileChangedReloadingStrategy());
            hashMap.put(configFileName, this);
        } catch (Exception e) {
        	e.printStackTrace();
        }
    }

    public static ConfigManager getSingleton(String configFileName) {
        if (hashMap.get(configFileName) == null) {
            hashMap.put(configFileName, new ConfigManager(configFileName));
        }
        return (ConfigManager) hashMap.get(configFileName);
    }

    public String selectValue(String path) {
        return this.config.getString(path);
    }

    public void setValue(String path, String value) {
        this.config.setProperty(path, value);
    }

    public String[] selectValues(String path) {
        return this.config.getStringArray(path);
    }

    public void reload() {
        this.config.reload();
    }

    public String toFileContent()  throws ConfigurationException {
        StringWriter stringWriter = new StringWriter();
        this.config.save(stringWriter);
        String returnValue = stringWriter.toString();
        IOUtils.closeQuietly(stringWriter);
        return returnValue;
    }

    public String getFilePath() {
        return this.config.getFile().getAbsolutePath();
    }

    public void save()
            throws ConfigurationException {
        this.config.save();
    }

    public static void main(String[] args) {
    	System.out.println(ConfigManager.getSingleton("systemConfig.xml").selectValue("useMessage"));
    }
}
