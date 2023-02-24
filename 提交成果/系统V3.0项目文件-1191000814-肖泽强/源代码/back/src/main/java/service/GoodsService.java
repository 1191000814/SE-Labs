package service;

import mapper.CustomerMapper;
import mapper.GoodsMapper;
import mapper.OrderItemMapper;
import org.apache.ibatis.session.SqlSession;
import pojo.Goods;
import pojo.OrderItemVo;
import utils.MyBatisUtils;

import java.util.List;

public class GoodsService{

    public boolean add(Goods goods){
        // 先检查是否商品是否已经存在(不区分大小写)
        if(goods == null)
            return false;
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        GoodsMapper goodsMapper = sqlSession.getMapper(GoodsMapper.class);
        Goods byFullName = goodsMapper.getByFullName(goods.getName());
        if(byFullName != null)
            return false;
        int maxId = goodsMapper.getMaxId();
        goods.setId(maxId + 1);
        boolean flag = goodsMapper.insert(goods) > 0;
        sqlSession.close();
        return flag;
    }

    public boolean update(Goods goods){
        if(goods == null)
            return false;
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        GoodsMapper goodsMapper = sqlSession.getMapper(GoodsMapper.class);
        boolean flag = goodsMapper.updateById(goods) > 0;
        sqlSession.close();
        return flag;
    }

    public boolean updateProfit(int id, float profit){
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        GoodsMapper goodsMapper = sqlSession.getMapper(GoodsMapper.class);
        boolean flag = goodsMapper.updateProfit(id, profit) > 0;
        sqlSession.close();
        return flag;
    }

    public List<Goods> search(String pattern){
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        GoodsMapper goodsMapper = sqlSession.getMapper(GoodsMapper.class);
        List<Goods> goods = goodsMapper.getByName("%" + pattern + "%");
        for(Goods goods1: goods)
            goods1.setProfit(getProfitById(goods1.getId()));
        sqlSession.close();
        return goods;
    }

    public List<Goods> getAll(){
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        GoodsMapper goodsMapper = sqlSession.getMapper(GoodsMapper.class);
        List<Goods> goods = goodsMapper.getAll();
        for(Goods goods1: goods)
            goods1.setProfit(getProfitById(goods1.getId()));
        sqlSession.close();
        return goods;
    }

    public boolean delete(int id){
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        GoodsMapper goodsMapper = sqlSession.getMapper(GoodsMapper.class);
        boolean flag = goodsMapper.deleteById(id) > 0;
        sqlSession.close();
        return flag;
    }

    public Goods getById(int id){
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        GoodsMapper goodsMapper = sqlSession.getMapper(GoodsMapper.class);
        Goods goods = goodsMapper.getById(id);
        sqlSession.close();
        return goods;
    }

    public float getProfitById(int id){
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        GoodsMapper goodsMapper = sqlSession.getMapper(GoodsMapper.class);
        OrderItemMapper itemMapper = sqlSession.getMapper(OrderItemMapper.class);
        float profit = 0;
        Goods goods = goodsMapper.getById(id);
        List<OrderItemVo> items = itemMapper.getAll();
        for(OrderItemVo item: items){
            if(item.getGoodsId() == id)
                profit += item.getNum() * (goods.getPrice() * item.getDiscountRate() - goods.getCost());
        }
        sqlSession.close();
        return profit;
    }
}