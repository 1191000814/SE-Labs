package mapper;

import pojo.OrderItemVo;
import java.util.List;

public interface OrderItemMapper{

    int add(OrderItemVo orderItemVo);

    int remove(int id);

    int modify(OrderItemVo orderItemVo);

    OrderItemVo getById(int id);

    List<OrderItemVo> getAll();

    int getMaxId();
}