package generic;

import lombok.Data;

@Data
public class RunnerContext {

    public static ThreadLocal<Object> anyData = new ThreadLocal<>();

}
