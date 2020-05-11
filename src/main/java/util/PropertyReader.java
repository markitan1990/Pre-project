package util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertyReader {
    public static String readProperty(String key){
        Properties properties = new Properties();
        String daotype = "";
        try {
            String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
            String appConfigPath = rootPath + "config.properties";

            FileInputStream fileInputStream = new FileInputStream(appConfigPath);
            properties.load(fileInputStream);
            daotype = properties.getProperty(key);
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return daotype;
    }
}
