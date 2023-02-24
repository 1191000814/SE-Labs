package pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FrontOrder {

    private int id;
    private List<OrderItemVo> goods;
    private Date createDate;
    private Date imDate;
    private float price;
    private int customerId;
    private float profit;
}