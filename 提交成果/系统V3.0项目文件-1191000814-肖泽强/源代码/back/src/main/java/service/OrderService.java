package service;

import mapper.*;
import org.apache.ibatis.session.SqlSession;
import pojo.*;
import utils.MyBatisUtils;
import utils.OrderUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class OrderService {

    // 增加草稿
    public int addOrder(String goods, int customerId){
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        Date date = new Date();
        float price = 0;
        float cost = 0;
        List<Integer> aList = OrderUtils.getItemsIdList(goods);
        for(Integer i : aList){
            OrderItemMapper orderItemMapper = sqlSession.getMapper(OrderItemMapper.class);
            OrderItemVo orderItemVo = orderItemMapper.getById(i);
            float price_temp = orderItemVo.getPriceOfOrderItem(customerId);
            float cost_temp = orderItemVo.getCostOfOrderItem();
            price = price + price_temp;
            cost = cost_temp + cost;
        }
        float profit = price - cost;
        OrderVo orderVo = new OrderVo(0, goods, date, date, price, customerId, profit);
        OrderMapper orderMapper = sqlSession.getMapper(OrderMapper.class);
        orderMapper.add(orderVo);
        int orderId = orderMapper.getMaxId();
        sqlSession.close();
        return orderId;
    }

    // 修改
    public void modify(String items, int customerId, int id){
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        Date modifyDate = new Date();
        float price = 0;
        float cost = 0;
        List<Integer> aList = OrderUtils.getItemsIdList(items);
        System.out.println("Integer "+ aList);
        for(Integer i : aList){
            System.out.println(i);
            OrderItemMapper orderItemMapper = sqlSession.getMapper(OrderItemMapper.class);
            OrderItemVo orderItemVo = orderItemMapper.getById(i);
            float price_temp = orderItemVo.getPriceOfOrderItem(customerId);
            float cost_temp = orderItemVo.getCostOfOrderItem();
            price = price + price_temp;
            cost = cost_temp + cost;
        }
        float profit = price - cost;
        OrderMapper orderMapper = sqlSession.getMapper(OrderMapper.class);
        OrderVo last = orderMapper.getById(id);
        Date date = last.getCreateDate();
        OrderVo orderVo = new OrderVo(id, items, date, modifyDate, price, customerId, profit);
        orderMapper.modify(orderVo);
        sqlSession.close();
    }

    // 审核之后返回生成的订单id(如果审核失败返回-1)
    public int confirm(int id){
        SqlSession sqlSession = MyBatisUtils.getSqlSession();

        OrderMapper orderMapper = sqlSession.getMapper(OrderMapper.class);
        OrderVo orderVo = orderMapper.getById(id);
        String goods = orderVo.getItems();

        List<Integer> orderItemsID = OrderUtils.getItemsIdList(goods);
        HashMap<Integer,Integer> map = new HashMap<>();
        boolean flag = true;
        for (int i : orderItemsID){
            OrderItemMapper orderItemMapper = sqlSession.getMapper(OrderItemMapper.class);
            OrderItemVo orderItemVo = orderItemMapper.getById(i);
            int goodId = orderItemVo.getGoodsId();
            int num = orderItemVo.getNum();
            RepoService repoService = new RepoService();
            if(!repoService.modifyNum(goodId,-num,1)){
                flag = false;
                break;
            }
            map.put(goodId,num);
        }
        for(int i : map.keySet()){
            int num = map.get(i);
            RepoService repoService = new RepoService();
            repoService.modifyNum(i, num,1);
        }
        if(!flag){
            sqlSession.close();
            return -1;
        }

        float price = orderVo.getPrice();
        int customerId = orderVo.getCustomerId();
        float profit = orderVo.getProfit();
        orderMapper.remove(id); // 审核之后删除草稿单
        DraftOrderMapper draftOrderMapper = sqlSession.getMapper(DraftOrderMapper.class);
        DraftOrderVo draftOrderVo = new DraftOrderVo(0, new Date(), 0, 0, goods, price, customerId, profit);
        draftOrderMapper.add(draftOrderVo);
        int draftOrderId = draftOrderMapper.getMaxId();
        sqlSession.close();
        return draftOrderId;
    }

    // 获取所有
    public List<OrderVo> getAll(){
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        OrderMapper orderMapper = sqlSession.getMapper(OrderMapper.class);
        List<OrderVo> orderList = orderMapper.getAll();
        sqlSession.close();
        return orderList;
    }

    // 移除订单
    public void remove(int id){
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        OrderMapper orderMapper = sqlSession.getMapper(OrderMapper.class);
        orderMapper.remove(id);
        sqlSession.close();
    }

    // 根据id获取
    public OrderVo getById(int id){
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        OrderMapper orderMapper = sqlSession.getMapper(OrderMapper.class);
        OrderVo orderVo = orderMapper.getById(id);
        sqlSession.close();
        return orderVo;
    }

    // 找到利润为 0 的草稿 (购物车)
    // 没找到返回null，找到了返回该草稿
    public OrderVo getMyCart(int customerId){
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        OrderMapper orderMapper = sqlSession.getMapper(OrderMapper.class);
        List<OrderVo> orderVos = orderMapper.getAll();

        for(OrderVo a : orderVos){
            if(a.getProfit()==0 && a.getCustomerId()==customerId){
                // 找到了
                sqlSession.close();
                return a;
            }
        }
        // 没找到
        sqlSession.close();
        return null;
    }

    //新增空白购物车
    //返回草稿id
    public int getBlankCart(int customerId){
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        OrderMapper orderMapper = sqlSession.getMapper(OrderMapper.class);
        Date date = new Date();
        float price = 0;
        float profit = 0;
        OrderVo orderVo = new OrderVo(0, "", date, date, price, customerId, profit);
        orderMapper.add(orderVo);
        return orderMapper.getMaxId();
    }

    /**
     在[购物车]中改变一个商品的数量
     num == -1 给商品数量加一
     num == 0 删除该商品
     num >= 1 改为商品数量为该值
     先考虑购物车中有没有同种商品, 若有: 找到该订单项, 直接在原数量上变化
     如果没有: 在购物车中添加订单项, 同时订单项数据库中也要添加
     */
    public boolean changeItemNum(int orderId, int goodsId, int num, float discountRate){
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        OrderMapper orderMapper = sqlSession.getMapper(OrderMapper.class);
        OrderItemMapper itemMapper = sqlSession.getMapper(OrderItemMapper.class);
        OrderVo cart = orderMapper.getById(orderId); // 获取购物车
        if(cart == null){
            System.out.println("找不到该id的购物车");
            return false;
        }
        List<Integer> itemsIdList = OrderUtils.getItemsIdList(cart.getItems());
        for(int itemId: itemsIdList){
            if(itemMapper.getById(itemId).getGoodsId() == goodsId){ // 找到了相同商品的项
                System.out.println("购物车有这种商品, 无需新增订单项, 直接在购物车中的订单项中修改数量");
                OrderItemVo changeItem = itemMapper.getById(itemId);
                if(num == -1){
                    System.out.println("购物车中商品[" + goodsId + "]的数量+1");
                    changeItem.setNum(changeItem.getNum() + 1); // 原有订单项的数量+1
                }
                else if(num == 0){ // 删除订单项
                    System.out.println("删除了购物车中商品[" + goodsId + "]的订单项");
                    itemMapper.remove(itemId);
                    // 在购物车中移除该订单项
                    cart.setItems(OrderUtils.deleteItem(cart.getItems(), String.valueOf(itemId)));
                    cart.updatePrice();
                    // 在数据库中删除该订单项
                    orderMapper.modify(cart);
                    // 将购物车保存至数据库
                    sqlSession.close();
                    return true;
                }
                else if(num > 0){
                    System.out.println("修改了购物车中商品[" + goodsId + "]的数量为" + num);
                    changeItem.setNum(num); // 设置订单项的数量为num
                }
                itemMapper.modify(changeItem);
                cart.setImDate(new Date()); // 修改日期
                cart.setProfit(0); // 利润置为0
                cart.updatePrice(); // 修改价格
                orderMapper.modify(cart); // 保存至数据库

                sqlSession.close();
                return true;
            }
        }
        // 原购物车中没有这号商品, 需要新增订单项
        if(num == 0){
            System.out.println("购物车中没有商品[" + goodsId + "], 删除失败");
            return false;
        }
        itemMapper.add(new OrderItemVo(0, goodsId, num == -1 ? 1 : num, discountRate));
        cart.setItems(OrderUtils.addItem(cart.getItems(), String.valueOf(itemMapper.getMaxId())));
        // 修改购物车的items: 增加新的itemId
        cart.setImDate(new Date());
        // 修改最后修改日期
        cart.updatePrice();
        // 修改价格
        cart.setProfit(0);
        // 利润置为0
        orderMapper.modify(cart);
        // 保存修改
        System.out.println("原购物车中没有商品[" + goodsId + "], 新增了订单项, 数量为" + num);
        sqlSession.close();
        return true;
    }
}
