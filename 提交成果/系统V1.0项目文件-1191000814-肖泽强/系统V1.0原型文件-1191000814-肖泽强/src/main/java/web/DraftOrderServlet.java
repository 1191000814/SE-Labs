package web;

import com.google.gson.Gson;
import entity.DraftOrderVo;
import entity.FrontId;
import entity.OrderVo;
import entity.Result;
import service.DraftOrderService;
import service.OrderService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class DraftOrderServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=utf-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");
        // 这两个都需要设置,一个请求端,一个是回复端
        String action = req.getParameter("action");
        // 写好与jsp页面参数同名的方法名,如login,register,然后通过反射调用
        if(action.equals("getAll")){
            DraftOrderService draftOrderService = new DraftOrderService();
            List<DraftOrderVo> aList = draftOrderService.getAll();
            try {
                resp.getWriter().write(Result.resJson(true, aList));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(action.equals("pay")){
            DraftOrderService draftOrderService = new DraftOrderService();
            String data = req.getParameter("data");
            FrontId frontId = new Gson().fromJson(data, FrontId.class);
            String id = frontId.getOrder_id();
            draftOrderService.pay(Integer.parseInt(id));
            try {
                resp.getWriter().write(Result.resJson(true, null));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(action.equals("refund")){
            DraftOrderService draftOrderService = new DraftOrderService();
            String data = req.getParameter("data");
            FrontId frontId = new Gson().fromJson(data, FrontId.class);
            String id = frontId.getOrder_id();
            draftOrderService.refund(Integer.parseInt(id));
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
