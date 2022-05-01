package web;

import com.google.gson.Gson;
import entity.*;
import service.OrderItemService;
import service.OrderService;
import utils.Utils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.List;

public class OrderServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=utf-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");
        // 这两个都需要设置,一个请求端,一个是回复端
        String action = req.getParameter("action");
        // 写好与jsp页面参数同名的方法名,如login,register,然后通过反射调用
        if(action.equals("getAll")){
            OrderService orderService = new OrderService();
            List<OrderVo> aList = orderService.getAll();
            List<FronOrder> bList = Utils.ordersTran(aList);
            try {
                resp.getWriter().write(Result.resJson(true, bList));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(action.equals("add")){
            OrderService orderService = new OrderService();
            OrderItemService orderItemService = new OrderItemService();
            String data = req.getParameter("data");
            FrontAdd frontAdd = new Gson().fromJson(data, FrontAdd.class);
            String num = frontAdd.getOrder_number();
            int customerId = 1;
            int good_str = 1;
            int orderItemId = orderItemService.add(good_str,Integer.parseInt(num));
            orderService.addOrder(""+orderItemId, customerId);
            try {
                resp.getWriter().write(Result.resJson(true, null));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(action.equals("confirm")){
            OrderService orderService = new OrderService();
            String data = req.getParameter("data");
            System.out.println(data);
            FrontId frontId = new Gson().fromJson(data, FrontId.class);
            String id = frontId.getOrder_id();
            orderService.confirm(Integer.parseInt(id));
            try {
                resp.getWriter().write(Result.resJson(true, null));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        doPost(req, resp);
    }

}
