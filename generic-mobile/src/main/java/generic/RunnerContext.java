package generic;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RunnerContext {

    static RunnerContext obj;
    public static RunnerContext getInstance(){
        if(obj == null){
            obj = new RunnerContext();
        }
        return obj;
    }

    private RunnerContext(){}
    private List<String> items =  new ArrayList<>();
    public void setItems(List<String> item ){
        items.addAll(item);
    }

}
