package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemVo {
    private int goodId;
    private int num;
    private int id;
    private float discountRate;
}
