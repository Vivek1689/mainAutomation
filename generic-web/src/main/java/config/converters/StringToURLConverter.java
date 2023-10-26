package config.converters;

import org.aeonbits.owner.Converter;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

public class StringToURLConverter implements Converter<URL> {

    public URL convert(Method method, String stringURL) {
        try {
            return new URL(stringURL);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
