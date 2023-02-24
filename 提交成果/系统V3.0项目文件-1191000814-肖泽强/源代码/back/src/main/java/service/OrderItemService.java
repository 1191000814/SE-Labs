package service;

import mapper.OrderItemMapper;
import org.apache.ibatis.session.SqlSession;
import pojo.OrderItemVo;
import utils.MyBatisUtils;

public class OrderItemService {

    // 增加订单项并返回它的id
    public int add(int goodsId, int num, float discountRate){
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        OrderItemMapper mapper = sqlSession.getMapper(OrderItemMapper.class);
        int maxId = mapper.getMaxId();
        mapper.add(new OrderItemVo(maxId + 1, goodsId, num, discountRate));
        sqlSession.close();
        return maxId+1;
    }

    public boolean delete(int id){
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        OrderItemMapper mapper = sqlSession.getMapper(OrderItemMapper.class);
        return mapper.remove(id) > 0;
    }

    public OrderItemVo getById(int id){
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        OrderItemMapper mapper = sqlSession.getMapper(OrderItemMapper.class);
        OrderItemVo orderItem = mapper.getById(id);
        sqlSession.close();
        return orderItem;
    }

    // 修改订单项中商品的数量
    public boolean modifyGoodsNum(int id, int num){
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        OrderItemMapper mapper = sqlSession.getMapper(OrderItemMapper.class);
        OrderItemVo item = mapper.getById(id);
        item.setNum(num);
        boolean flag = mapper.modify(item) > 0;
        sqlSession.close();
        return flag;
    }
}