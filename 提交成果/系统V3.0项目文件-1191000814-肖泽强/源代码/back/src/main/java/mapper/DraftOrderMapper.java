package mapper;

import pojo.DraftOrderVo;
import java.util.List;

public interface DraftOrderMapper{

    // 添加一个订单
    void add(DraftOrderVo draftOrderVo);

    // 按id删除订单
    void remove(int id);

    // 按id修改订单
    void modify(DraftOrderVo draftOrderVo);

    // 按id获取订单
    DraftOrderVo getById(int id);

    // 按顾客id获取订单
    List<DraftOrderVo> getByCustomerId(int customerId);

    // 获取所有订单
    List<DraftOrderVo> getAll();

    int getMaxId();
}
