package pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Goods{

    private int id;
    private String name;
    private float price;
    private float tradePrice;
    private float cost;
    private float profit; // 销售额
    private String img; // 图片url
}