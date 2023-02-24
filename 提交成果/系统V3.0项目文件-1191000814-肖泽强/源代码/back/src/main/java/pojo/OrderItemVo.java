package pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mapper.CustomerMapper;
import mapper.GoodsMapper;
import org.apache.ibatis.session.SqlSession;
import utils.MyBatisUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemVo {

    private int id;
    private int goodsId;
    private int num;
    private float discountRate;

    // 获取订单打折之后的价格
    public float getPriceOfOrderItem(int customerId){

        SqlSession sqlSession = MyBatisUtils.getSqlSession();

        GoodsMapper goodsMapper = sqlSession.getMapper(GoodsMapper.class);
        CustomerMapper customerMapper = sqlSession.getMapper(CustomerMapper.class);

        Goods goods = goodsMapper.getById(goodsId);
        Customer customer = customerMapper.getById(customerId);
        float price;
        if(customer.isMethod()){
            price = goods.getTradePrice();
        }else {
            price = goods.getPrice();
        }
        sqlSession.close();
        return num * price * discountRate;
    }

    // 获取订单的成本
    public float getCostOfOrderItem(){
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        GoodsMapper goodsMapper = sqlSession.getMapper(GoodsMapper.class);
        Goods goods = goodsMapper.getById(goodsId);
        sqlSession.close();
        return num * goods.getCost();
    }
}