package service;

import dao.DraftOrderDao;
import dao.OrderDao;
import dao.OrderItemDao;
import entity.DraftOrderVo;
import entity.OrderItemVo;
import entity.OrderVo;
import utils.JdbcUtils;
import utils.Utils;

import java.util.Date;
import java.util.List;

public class OrderService {

    public void addOrder(String goods, int customerId){
        Date date = new Date();
        float price = 0;
        List<Integer> aList = Utils.getItemsIdFromGoods(goods);
        for(Integer i : aList){
            OrderItemDao orderItemDao = new OrderItemDao();
            OrderItemVo orderItemVo = orderItemDao.getById(i);
            JdbcUtils.commitAndClose(orderItemDao.getaConnection());
            float price_temp = getPriceOfOrderItem(orderItemVo);
            price = price + price_temp;
        }
        OrderVo orderVo = new OrderVo(0, goods, date, date, price, customerId);
        OrderDao orderDao = new OrderDao();
        orderDao.add(orderVo);
        JdbcUtils.commitAndClose(orderDao.getaConnection());
    }

    public void modify(String goods, int customerId, int id){
        Date modifyDate = new Date();
        float price = 0;
        List<Integer> aList = Utils.getItemsIdFromGoods(goods);
        for(Integer i : aList){
            OrderItemDao orderItemDao = new OrderItemDao();
            OrderItemVo orderItemVo = orderItemDao.getById(i);
            JdbcUtils.commitAndClose(orderItemDao.getaConnection());
            float price_temp = getPriceOfOrderItem(orderItemVo);
            price = price + price_temp;
        }
        OrderDao orderDao = new OrderDao();
        OrderVo last = orderDao.getById(id);
        Date date = last.getCreateDate();
        OrderVo orderVo = new OrderVo(id, goods, date, modifyDate, price, customerId);
        orderDao.modify(orderVo);
        JdbcUtils.commitAndClose(orderDao.getaConnection());
    }

    public void confirm(int id){
        //System.out.println(1);
        OrderDao orderDao = new OrderDao();
        OrderVo orderVo = orderDao.getById(id);
        String goods = orderVo.getGoods();
        float price = orderVo.getPrice();
        int customerId = orderVo.getCustomerId();
        orderDao.remove(id);
        DraftOrderDao draftOrderDao = new DraftOrderDao();
        DraftOrderVo draftOrderVo = new DraftOrderVo(0, new Date(), 0, 0, goods, price, customerId);
        draftOrderDao.add(draftOrderVo);
        JdbcUtils.commitAndClose(draftOrderDao.getaConnection());
        JdbcUtils.commitAndClose(orderDao.getaConnection());
    }

    public List<OrderVo> getAll(){
        OrderDao orderDao = new OrderDao();
        List<OrderVo> aList= orderDao.getAll();
        JdbcUtils.commitAndClose(orderDao.getaConnection());
        return aList;
    }

    public void remove(int id){
        OrderDao orderDao = new OrderDao();
        orderDao.remove(id);
        JdbcUtils.commitAndClose(orderDao.getaConnection());
    }

    public OrderVo getById(int id){
        OrderDao orderDao = new OrderDao();
        OrderVo orderVo = orderDao.getById(id);
        JdbcUtils.commitAndClose(orderDao.getaConnection());
        return orderVo;
    }

    public static float getPriceOfOrderItem(OrderItemVo orderItemVo){
        return 1;
    }


}
