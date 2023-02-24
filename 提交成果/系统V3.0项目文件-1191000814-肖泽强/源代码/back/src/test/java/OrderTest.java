import mapper.OrderMapper;
import org.junit.Test;
import pojo.OrderVo;
import utils.MyBatisUtils;

import java.util.List;

public class OrderTest{

    OrderMapper mapper = MyBatisUtils.getSqlSession().getMapper(OrderMapper.class);

    @Test
    public void test(){
        List<OrderVo> all = mapper.getAll();
        System.out.println(all);
    }
}
