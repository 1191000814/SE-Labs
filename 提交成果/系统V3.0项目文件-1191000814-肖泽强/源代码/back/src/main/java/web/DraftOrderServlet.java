package web;

import pojo.*;
import service.DraftOrderService;
import utils.OrderUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/*
删除订单方法已经被取消
 */
public class DraftOrderServlet extends BaseServlet{

    /**
     * 获取所有订单
     */
    public void getAll(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        System.out.println("\033[34;1m" + "\n-------> 获取全部订单: \033[0m\n");
        DraftOrderService draftOrderService = new DraftOrderService();
        List<DraftOrderVo> aList = draftOrderService.getAll();
        List<FrontDraftOrder> bList = OrderUtils.draftOrdersTran(aList);
        resp.getWriter().write(Result.resJson(true, bList));
    }

    /**
     * 支付订单
     */
    public void pay(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        DraftOrderService draftOrderService = new DraftOrderService();
        FrontPay frontPay = (FrontPay)parseData(FrontPay.class, req, "支付订单");
        DraftOrderVo draftOrder = draftOrderService.getById(frontPay.getId());
        if(draftOrder.getPay() == 1){
            resp.getWriter().write(Result.resJson(false, "不能重复支付"));
        }
        else if(draftOrderService.pay(frontPay.getId(), frontPay.getUserId())){
            resp.getWriter().write(Result.resJson(true, null));
        }
        else {
            resp.getWriter().write(Result.resJson(false, "库存不足，请先拨货"));
        }
    }

    /**
     * 订单退款
     */
    public void refund(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        DraftOrderService draftOrderService = new DraftOrderService();
        FrontPay frontPay = (FrontPay)parseData(FrontPay.class, req, "已支付的订单退款");
        DraftOrderVo draftOrder = draftOrderService.getById(frontPay.getId());
        if(draftOrder.getRefund() == 1){
            resp.getWriter().write(Result.resJson(false, "不能重复退款"));
        }else{
            boolean flag = draftOrderService.refund(frontPay.getId(), frontPay.getUserId());
            resp.getWriter().write(Result.resJson(flag, flag ? null : "还未支付不能退款"));
        }
    }

    /**
     * 订单删除
     */
    public void remove(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        DraftOrderService draftOrderService = new DraftOrderService();
        FrontId frontId = (FrontId)parseData(FrontId.class, req, "删除订单");
        boolean flag = draftOrderService.remove(frontId.getId());
        resp.getWriter().write(Result.resJson(flag, flag ? null : "有效订单不能删除"));
    }

    /**
     * 按客户id获取订单
     */
    public void getByCustomerId(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        DraftOrderService draftOrderService = new DraftOrderService();
        DraftOrderVo draftOrderVo = (DraftOrderVo)parseData(DraftOrderVo.class, req, "按客户id获取订单");
        List<DraftOrderVo> draftOrderVos = draftOrderService.getByCustomerId(draftOrderVo.getCustomerId());
        boolean flag = ! draftOrderVos.isEmpty();
        List<FrontDraftOrder> frontDraftOrders = OrderUtils.draftOrdersTran(draftOrderVos);
        resp.getWriter().write(Result.resJson(flag, flag ? frontDraftOrders : "目前没有订单"));
    }
}