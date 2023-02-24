import mapper.OrderItemMapper;
import org.junit.Test;
import pojo.OrderItemVo;
import utils.MyBatisUtils;

import java.util.List;

public class OrderItemTest{

    OrderItemMapper orderItemMapper = MyBatisUtils.getSqlSession().getMapper(OrderItemMapper.class);

    @Test
    public void test(){
        List<OrderItemVo> all = orderItemMapper.getAll();
        System.out.println(all);
    }
}
