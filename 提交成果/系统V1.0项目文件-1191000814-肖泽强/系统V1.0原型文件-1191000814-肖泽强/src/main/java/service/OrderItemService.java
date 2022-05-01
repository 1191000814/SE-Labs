package service;

import dao.OrderItemDao;
import entity.OrderItemVo;
import utils.JdbcUtils;

import java.util.List;

public class OrderItemService {

    public int add(int good_id, int num){
        OrderItemDao orderItemDao = new OrderItemDao();
        orderItemDao.add(new OrderItemVo(good_id, num, 0, 1));
        JdbcUtils.commitAndClose(orderItemDao.getaConnection());
        OrderItemDao orderItemDao1 = new OrderItemDao();
        List<OrderItemVo> aList = orderItemDao1.getAll();
        JdbcUtils.commitAndClose(orderItemDao1.getaConnection());
        OrderItemVo orderItemVo = aList.get(aList.size()-1);
        return orderItemVo.getId();
    }

}
