package pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mapper.OrderItemMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.defaults.DefaultSqlSession;
import utils.MyBatisUtils;
import utils.OrderUtils;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderVo {

    private int id;
    private String items;
    private Date createDate;
    private Date imDate;
    private float price;
    private int customerId;
    private float profit;

    /**
     更新草稿/购物车的价格
     */
    public void updatePrice(){
        List<Integer> itemsIdList = OrderUtils.getItemsIdList(items);
        price = 0;
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        OrderItemMapper mapper = sqlSession.getMapper(OrderItemMapper.class);
        for(int itemId: itemsIdList)
            price += mapper.getById(itemId).getPriceOfOrderItem(customerId);
        sqlSession.close();
    }
}