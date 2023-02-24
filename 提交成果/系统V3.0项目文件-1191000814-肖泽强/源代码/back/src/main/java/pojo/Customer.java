package pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer{

    private int id;
    private String name; // 客户姓名
    private String password; // 密码
    private String phone; // 联系方式
    private String company; // 公司单位
    private boolean method; // 销售方式, true批发, false零售
}