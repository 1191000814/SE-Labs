package pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FrontDraftOrder {

    private int id;
    private Date checkDate;
    private int pay;
    private int refund;
    private List<OrderItemVo> goods;
    private float price;
    private String customerName;
    private float profit;
}