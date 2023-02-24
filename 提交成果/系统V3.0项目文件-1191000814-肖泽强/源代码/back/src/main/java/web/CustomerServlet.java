package web;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mapper.DraftOrderMapper;
import pojo.Customer;
import pojo.DraftOrderVo;
import pojo.OrderVo;
import pojo.Result;
import service.CustomerService;
import service.OrderService;
import utils.MyBatisUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class CustomerServlet extends BaseServlet{

    Gson gson = new Gson();

    // 返回给前端的类
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class FCustomer{
        private int id;
        private String name; // 客户姓名
        private String phone; // 练习方式
        private String company; // 公司单位
        private boolean method; // 销售方式, true批发, false零售

        /* 新增字段 */
        private int unpaidNum; // 未付订单数量
        private float unpaidAmount; // 未付金额
        private int paidNum; // 已付订单数量
        private float paidAmount; // 已付金额
        private int refundedNum; // 已退订单数量
        private float refundedAmount; // 已退金额
    }

    // 登录
    protected void login(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        CustomerService customerService = new CustomerService();
        System.out.println("正在登录...");
        Customer customer = (Customer)parseData(Customer.class, req, "客户登录");
        PrintWriter writer = resp.getWriter();
        // 将参数注入到user的属性中
        Customer login = customerService.login(customer.getName(), customer.getPassword());
        if(login == null){
            // 登录失败
            System.out.println("登录失败,用户名或密码错误");
            // 设置提示信息
            writer.write(Result.resJson(false, "登录失败,客户名或密码错误"));
        }
        else{
            int loginId = login.getId(); // 登录之后的id
            // 登录成功
            System.out.println("登录成功");
            OrderService orderService = new OrderService();
            OrderVo cart = orderService.getMyCart(loginId);
            if(cart == null)
                // 如果用户没有购物车,就新建一个空购物车
                orderService.getBlankCart(loginId);
            writer.write(Result.resJson(true, login));
            // 同时保存用户信息
        }
    }

    // 注册
    public void register(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        CustomerService customerService = new CustomerService();
        Customer customer = (Customer) parseData(Customer.class, req, "客户注册");
        boolean flag = customerService.register(customer);
        resp.getWriter().write(Result.resJson(flag, flag ? null : "注册失败, 用户名已经存在"));
    }

    // 搜索
    public void search(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        CustomerService customerService = new CustomerService();
        Customer customer = (Customer) parseData(Customer.class, req, "查询客户信息");
        String name = customer.getName();
        List<Customer> customers;
        if(("").equals(name) || name == null)
            customers = customerService.getAll();
        else
            customers = customerService.search(customer.getName());
        List<FCustomer> fCustomers = getFCustomers(customers);
        boolean flag = fCustomers.size() > 0;
        resp.getWriter().write(Result.resJson(flag, flag ? fCustomers : "没有查询到任何结果"));
    }

    /* 已弃用*/
    // 获取全部
    public void getAll(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        CustomerService customerService = new CustomerService();
        List<Customer> customers = customerService.getAll();
        List<FCustomer> fCustomers = getFCustomers(customers);
        boolean flag = fCustomers.size() > 0;
        resp.getWriter().write(Result.resJson(flag, flag ? fCustomers : "仓库里没有任何商品"));
    }

    /* 已弃用*/
    // 删除一项
    public void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        CustomerService customerService = new CustomerService();
        Customer customer = (Customer) parseData(Customer.class, req, "删除客户信息");
        boolean flag = true;
        try{
            customerService.delete(customer.getId());
        }catch(Exception e){
            flag = false;
            System.out.println("由于外键约束,无法删除该客户");
        }
        resp.getWriter().write(Result.resJson(flag, flag ? null : "该客户已经下单, 无法删除"));
    }

    /* 已弃用*/
    // 改变一项
    public void update(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        CustomerService customerService = new CustomerService();
        Customer customer = (Customer) parseData(Customer.class, req, "修改客户信息");
        boolean flag = customerService.update(customer);
        resp.getWriter().write(Result.resJson(flag, flag ? null : "修改失败"));
    }

    // 由客户集合获取客户属性与其他六项组成的'加强版客户'集合
    private List<FCustomer> getFCustomers(List<Customer> customers){
        DraftOrderMapper draftOrderMapper = MyBatisUtils.getSqlSession().getMapper(DraftOrderMapper.class);
        // 订单相关方法
        List<FCustomer> fCustomers = new ArrayList<>();
        for(Customer customer: customers){ // 遍历每个客户
            FCustomer fCustomer = gson.fromJson(gson.toJson(customer), FCustomer.class); // 将原有的同名属性注入
            List<DraftOrderVo> orders = draftOrderMapper.getByCustomerId(customer.getId()); // 遍历该客户的每个订单
            for(DraftOrderVo order: orders){
                if(order.getPay() == 0){ // 未付款
                    fCustomer.unpaidNum ++;
                    fCustomer.unpaidAmount += order.getPrice();
                }
                else if(order.getPay() == 1 && order.getRefund() == 0){ // 已付款
                    fCustomer.paidNum ++;
                    fCustomer.paidAmount += order.getPrice();
                }
                else if(order.getPay() == 1 && order.getRefund() == 1){ // 已退款
                    fCustomer.refundedNum ++;
                    fCustomer.refundedAmount += order.getPrice();
                }
            }
            fCustomers.add(fCustomer);
        }
        return fCustomers;
    }
}