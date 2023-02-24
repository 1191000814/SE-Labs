package utils;

import mapper.OrderItemMapper;
import pojo.*;
import service.CustomerService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class OrderUtils{

    // 将字符串解析成日期
    public static String dateToString(Date date){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }

    // 将日期转化成字符串
    public static Date stringToDate(String str) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            return sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 从items字符串中解析出orderItem的id列表
     */
    public static List<Integer> getItemsIdList(String items){
        List<Integer> aList = new ArrayList<>();
        System.out.println("当前购物车/草稿单项: " + items);
        if(items == null || "".equals(items))
            return aList; // 如果没有商品, 返回空列表
        String[] itemsId = items.trim().split(" ");
        for(String itemId : itemsId){
            aList.add(Integer.parseInt(itemId));
        }
        return aList;
    }

    /**
     * 将草稿单转化为返回给前端的草稿数据
     * 就是把空格分隔的字符串那一部分转化为OrderItems数组,其他不变
     */
    public static FrontOrder orderTran(OrderVo orderVo){
        FrontOrder frontOrder = new FrontOrder();
        List<OrderItemVo> orderItemVos = new ArrayList<>();

        frontOrder.setId(orderVo.getId());
        frontOrder.setCreateDate(orderVo.getCreateDate());
        frontOrder.setCustomerId(orderVo.getCustomerId());
        frontOrder.setPrice(orderVo.getPrice());
        frontOrder.setImDate(orderVo.getImDate());
        frontOrder.setProfit(orderVo.getProfit());
        OrderItemMapper orderItemMapper = MyBatisUtils.getSqlSession().getMapper(OrderItemMapper.class);
        List<Integer> orderItemIdList = getItemsIdList(orderVo.getItems());
        // 获取的带空格的商品id字符串
        for(Integer a : orderItemIdList){
            OrderItemVo orderItemVo = orderItemMapper.getById(a);
            orderItemVos.add(orderItemVo);
        }
        frontOrder.setGoods(orderItemVos);

        return frontOrder;
    }

    /**
     * 把草稿单列表转化为前端需要的草稿数据列表
     */
    public static List<FrontOrder> ordersTran(List<OrderVo> aList){
        List<FrontOrder> bList = new ArrayList<>();
        for(OrderVo order: aList){
            bList.add(orderTran(order));
        }
        return bList;
    }

    /**
     * 转化订单,用草稿单
     */
    public static FrontDraftOrder draftOrderTran(DraftOrderVo draftOrderVo){
        FrontDraftOrder frontDraftOrder = new FrontDraftOrder();
        List<OrderItemVo> orderItemVos = new ArrayList<>();

        CustomerService customerService = new CustomerService();
        Customer customer = customerService.getById(draftOrderVo.getCustomerId());

        frontDraftOrder.setId(draftOrderVo.getId());
        frontDraftOrder.setCheckDate(draftOrderVo.getCheckDate());
        frontDraftOrder.setCustomerName(customer.getName());
        frontDraftOrder.setPrice(draftOrderVo.getPrice());
        frontDraftOrder.setPay(draftOrderVo.getPay());
        frontDraftOrder.setProfit(draftOrderVo.getProfit());
        frontDraftOrder.setRefund(draftOrderVo.getRefund());
        OrderItemMapper orderItemMapper = MyBatisUtils.getSqlSession().getMapper(OrderItemMapper.class);

        List<Integer> orderItemIdList = getItemsIdList(draftOrderVo.getGoods());
        for(Integer a : orderItemIdList){
            OrderItemVo orderItemVo = orderItemMapper.getById(a);
            orderItemVos.add(orderItemVo);
        }
        frontDraftOrder.setGoods(orderItemVos);

        return frontDraftOrder;
    }

    /**
     * 转化订单数组, 同草稿单
     */
    public static List<FrontDraftOrder> draftOrdersTran(List<DraftOrderVo> aList){
        List<FrontDraftOrder> bList = new ArrayList<>();
        for(DraftOrderVo draftOrderVo : aList){
            bList.add(draftOrderTran(draftOrderVo));
        }
        return bList;
    }

    /**
     * 删除[订单项items]中的一项
     * 并返回删除后的items
     */
    public static String deleteItem(String items, String itemId){
        if(! items.contains(itemId)) // 没有这一项,无需删除
            return items;
        if(items.equals(itemId)) // 只有这一项返回空字符串即可
            return "";
        String[] split = items.split(itemId);
        if(split.length == 1)
            return split[0].trim();
        else
            return (split[0].trim() + " " + split[1].trim()).trim();
    }

    /**
     * 往[订单项items]中的增加一项
     * 并返回增加后的items
     */
    public static String addItem(String items, String itemId){
        if(items.trim().equals(""))
            return itemId;
        else
            return items + " " + itemId;
    }
}
