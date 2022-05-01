package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderVo {
    private int id;
    private String goods;
    private Date createDate;
    private Date ImDate;
    private float price;
    private int customerId;
}
