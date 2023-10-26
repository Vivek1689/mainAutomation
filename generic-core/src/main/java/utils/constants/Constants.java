package utils.constants;

import org.openqa.selenium.WebDriver;

public class Constants {

    /*This is used for local db connection*/
    public static final String DB_NAME = "sys";
    public static final String USER_TABLE = "users";
    public static final String hostname = "localhost";
    public static final String dbUrl = "jdbc:mysql://localhost:3306/";
    public static final String port_number = "3306";
    public static final String username = "root";
    public static final String password = "P@ssw0rd";

    public static ThreadLocal<WebDriver> currentInstance= new ThreadLocal<>();

    public static final String DEFAULT_BUILD_NUMBER = "1";

}
