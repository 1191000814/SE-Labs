package entity;

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
    private String goods;
    private float price;
    private int customerId;
}
