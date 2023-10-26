package mapper;

import io.cucumber.datatable.DataTable;
import model.LoginModel;

import java.util.List;

public class LoginMapper {

    public static LoginModel map(DataTable dataTable){
        LoginModel loginModel = new LoginModel();
        List<String> list=dataTable.asList();
        return loginModel;
    }
}
