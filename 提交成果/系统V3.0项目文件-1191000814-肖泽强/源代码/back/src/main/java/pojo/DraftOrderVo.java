package pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DraftOrderVo {

    private int id;
    private Date checkDate;
    private int pay;
    private int refund;
    private String goods; // 用空格分隔的一系列订单的id
    private float price;
    private int customerId;
    private float profit;
}