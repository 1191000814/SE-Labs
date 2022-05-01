package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FronOrder {

    private int id;
    private String name;
    private String goodsname;
    private int number;
}
