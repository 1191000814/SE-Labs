package pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// 封装的用户信息类
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User{
    private int id;
    private String username;
    private String password;
    private String email;
    private int status;
    private float performance;
}