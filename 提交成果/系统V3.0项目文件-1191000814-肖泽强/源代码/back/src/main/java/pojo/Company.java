package pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Company{

    private Float cost; // 进货金额
    private Float sales; // 销售金额
    private Float remain; // 积压金额
    private Float capital; // 公司资产
}