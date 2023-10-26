package utils.propertyReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertyUtil {

    /*
    * Used to get the value of a property from property file using it's key name
    * */
    public static String getValue(String filePath,String propertyName) throws IOException {
        FileReader reader=new FileReader(filePath);
        Properties p=new Properties();
        p.load(reader);
        return p.getProperty(propertyName);
    }

    /*
    * Used to get the properties object for a file so that it can be used throughout the project with the same objectxs
    * */
    public static Properties getProperty(String filePath) throws IOException {
        FileReader reader=new FileReader(filePath);
        Properties p=new Properties();
        p.load(reader);
        return p;
    }
}
