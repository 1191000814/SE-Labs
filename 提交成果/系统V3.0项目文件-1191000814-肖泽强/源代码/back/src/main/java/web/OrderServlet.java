package web;

import pojo.*;
import service.OrderItemService;
import service.OrderService;
import utils.OrderUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import pojo.FrontAddCart;

public class OrderServlet extends BaseServlet{

    /**
     * 获取全部草稿
     */
    public void getAll(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        System.out.println("\033[34;1m" + "\n-------> 获取全部草稿: \033[0m\n");
        OrderService orderService = new OrderService();
        List<OrderVo> aList = orderService.getAll();
        List<FrontOrder> bList = OrderUtils.ordersTran(aList);
        resp.getWriter().write(Result.resJson(true, bList));
    }

    /**
     * 增加一条草稿
     */
    public void add(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        OrderService orderService = new OrderService();
        OrderItemService orderItemService = new OrderItemService();
        FrontAdd frontAdd = (FrontAdd) parseData(FrontAdd.class, req, "添加草稿");
        int customerId = frontAdd.getCustomerId();
        StringBuilder stringBuilder = new StringBuilder();
        List<FrontAddItem> addItems = frontAdd.getGoodItems();
        for(FrontAddItem addItem : addItems){
            int goodsId = addItem.getGoodsId();
            int num = addItem.getNum();
            float discountRate = addItem.getDiscountRate();
            int orderItemId = orderItemService.add(goodsId, num, discountRate);
            stringBuilder.append(orderItemId).append(" ");
        }
        String goods = stringBuilder.toString();

        int a = orderService.addOrder(goods, customerId);
        FrontId frontId = new FrontId(a);
        resp.getWriter().write(Result.resJson(true, frontId));
    }

    /**
     * 审核草稿/购物车
     */
    public void confirm(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        OrderService orderService = new OrderService();
        FrontId frontId = (FrontId) parseData(FrontId.class, req, "审核草稿单/购物车");
        OrderVo order = orderService.getById(frontId.getId());
        if(order.getItems().trim().equals("")){
            resp.getWriter().write(Result.resJson(false, "空草稿单/购物车不能审核"));
            return;
        }
        int confirmId = orderService.confirm(frontId.getId()); // 订单的id
        if(confirmId == -1){
            resp.getWriter().write(Result.resJson(false, "库存不足，请先拨货"));
            return;
        }
        FrontId frontId2 = new FrontId(confirmId); // 传给前端的数据
        if(order.getProfit() == 0){
            orderService.getBlankCart(order.getCustomerId());
            System.out.println("购物车已经提交, 创建新购物车");
        }
        resp.getWriter().write(Result.resJson(true, frontId2));
    }

    /**
     * 修改草稿
     */
    public void modify(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        OrderService orderService = new OrderService();
        OrderItemService orderItemService = new OrderItemService();
        FrontModify frontModify = (FrontModify)parseData(FrontModify.class, req, "修改草稿");
        int id = frontModify.getId();
        int customerId = orderService.getById(id).getCustomerId();
        System.out.println("草稿id:" + id);
        System.out.println("客户id:" + customerId);
        StringBuilder stringBuilder = new StringBuilder();
        List<FrontAddItem> addItems = frontModify.getGoodItems();
        for(FrontAddItem addItem : addItems){
            int goodsId = addItem.getGoodsId();
            int num = addItem.getNum();
            float discountRate = addItem.getDiscountRate();
            int orderItemId = orderItemService.add(goodsId, num, discountRate);
            stringBuilder.append(orderItemId).append(" ");
        }
        String goods = stringBuilder.toString();
        orderService.modify(goods, customerId, id);
        resp.getWriter().write(Result.resJson(true, null));
    }

    /**
     * 删除草稿
     */
    public void remove(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        OrderService orderService = new OrderService();
        FrontId frontId = (FrontId)parseData(FrontId.class, req, "删除草稿");
        orderService.remove(frontId.getId());
        resp.getWriter().write(Result.resJson(true, null));
    }

    /**
     * 改变购物车中的商品数量
     */
    public void changeItemNum(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        OrderService orderService = new OrderService();
        FrontAddCart frontAddCart = (FrontAddCart) parseData(FrontAddCart.class, req, "改变购物车中的商品数量");
        // 获取前端信息: 购物车id, 货物id, 数量
        int orderId = frontAddCart.getOrderId(); // 购物车id
        int goodsId = frontAddCart.getGoodItems().getGoodsId(); // 商品id
        int num = frontAddCart.getGoodItems().getNum(); // 改变之后的的num
        float discountRate = frontAddCart.getGoodItems().getDiscountRate(); // 折扣
        boolean flag = orderService.changeItemNum(orderId, goodsId, num, discountRate);
        resp.getWriter().write(Result.resJson(flag, flag ? "修改商品数量成功" : "修改商品数量失败"));
    }

    /**
     * 获取用户的购物车
     */
    public void getMyCart(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        OrderService orderService = new OrderService();
        FrontId frontId = (FrontId) parseData(FrontId.class, req, "根据客户Id获取购物车");
        int customerId = frontId.getId();
        OrderVo orderVo = orderService.getMyCart(customerId);
        FrontOrder frontOrder = OrderUtils.orderTran(orderVo);
        resp.getWriter().write(Result.resJson(true, frontOrder));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        doPost(req, resp);
    }
}
