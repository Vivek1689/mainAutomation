package utils.random;

import net.datafaker.Faker;
import net.datafaker.providers.base.Name;
import net.datafaker.providers.base.Number;

import java.util.Locale;

public class RandomUtil {

    private RandomUtil() {}

    //https://www.datafaker.net/
    /**
     * faker can generate lots of different types random data, based on the requirements.
     * Few examples like name(), number(), address(), color(), date(), time(), etc.
     */

    private static Faker faker = new Faker(new Locale("en-GB"));

    /**
     * Faker can generate lots of different types random data, based on the requirements.
     * <pre> * Few examples like name(), number(), address(), color(), date(), time(), etc. </pre>
     */
    public static Faker getFakerObject(){
        return faker;
    }

    public static int getRandomNumberWithInRange(int min, int max) {
        return faker.number().numberBetween(min, max);
    }

    /**
     * Generate AlphaNumeric string based on pattern
     * @param pattern '?' is Alphabet character & '#' is Number character
     * <pre>
     * {@code Eg1: "ABC##EFG" could be replaced like "ABC99EFG".
     * Eg2: "????##@xyz.com" could be replaced like "lkrz48@xyz.com".}
     * </pre>
     * @param returnAsUpperCase true or false
     * @return - String
     */
    public static String getRandomAlphaNumericChars(String pattern, boolean returnAsUpperCase){
        return faker.bothify(pattern, returnAsUpperCase);
        //return faker.expression("#{bothify '"+pattern+"', "+String.valueOf(returnAsUpperCase).toLowerCase()+"}");
    }

    public static Number getFakerNumber(){
        return faker.number();
    }

    public static Name getFakerName(){
        return faker.name();
    }

}
