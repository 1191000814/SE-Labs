package mapper;

import pojo.OrderVo;

import java.util.List;

public interface OrderMapper{

    // 添加一个草稿单
    void add(OrderVo orderVo);

    // 按id删除草稿单
    void remove(int id);

    // 按id修改草稿单
    void modify(OrderVo orderVo);

    // 按id获取
    OrderVo getById(int id);

    // 按顾客id获取草稿单
    List<OrderVo> getByCustomer(int customerId);

    // 获取所有草稿单
    List<OrderVo> getAll();

    int getMaxId();
}
