package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FrontAdd {
    private String order_id;
    private String order_customer;
    private String order_goods;
    private String order_number;
}
