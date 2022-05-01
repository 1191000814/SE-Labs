package entity;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result{

    boolean flag;
    Object data;

    public static String resJson(boolean flag, Object data){
        return new Gson().toJson(new Result(flag, data), Result.class);
    }
}
