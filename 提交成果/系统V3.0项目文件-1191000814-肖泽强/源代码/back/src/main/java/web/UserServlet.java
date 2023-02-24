package web;

import com.google.gson.Gson;
import pojo.Result;
import pojo.User;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/*
/user
综合login和register的功能,一个模块中一般只能有一个Servlet程序
 */
public class UserServlet extends BaseServlet{

    private final Gson gson = new Gson();

    /**
     * 用户登录
     * 修改1:增加cookie,可以免用户名登录
     * 修改2:增加session,显示登录的用户状态,而不是直接在属性中设置
     *
     * @param req 请求对象
     * @param resp 反应对象
     * @throws ServletException Servlet异常
     * @throws IOException IO异常
     */
    protected void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserService userService = new UserService();
        User user = (User) parseData(User.class, req, "用户登录");
        PrintWriter writer = resp.getWriter();
        // 将参数注入到user的属性中
        System.out.println("登录的用户信息:" + user);
        User user1 = userService.login(user.getUsername(), user.getPassword());
        if(user1 == null){
            // 登录失败
            System.out.println("登录失败,用户名或密码错误");
            // 设置提示信息
            writer.write(Result.resJson(false, "登录失败,用户名或密码错误"));
        }
        else{
            // 登录成功
            System.out.println("登录成功");
            writer.write(Result.resJson(true, user1));
            // 同时保存用户信息
        }
    }

    /**
     * 用户注册
     * @param req 请求对象
     * @param resp 反应对象
     * @throws ServletException Servlet异常
     * @throws IOException IO异常
     */
    protected void register(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserService userService = new UserService();
        PrintWriter writer = resp.getWriter();
        User user = (User) parseData(User.class, req, "用户注册");
        user.setEmail(user.getUsername() + "@se.com");
        User registerUser = userService.register(user);
        // 检查用户名是否可用
        if(registerUser == null){
            // 添加失败,说明用户名已经存在
            System.out.println("注册失败,用户名'" + user.getUsername() + "'已经存在");
            writer.write(Result.resJson(false, "注册失败,用户名'" + user.getUsername() + "'已经存在"));
        }
        // 可用,调用service保存到数据库
        else{
            // 添加成功,已经保存到数据库
            System.out.println("注册成功");
            writer.write(Result.resJson(true, registerUser));
        }
    }

    protected void get(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        UserService userService = new UserService();
        User user = (User) parseData(User.class, req, "获取用户信息");
        User user1 = userService.getByFullName(user.getUsername());
        resp.getWriter().write(Result.resJson(user1 != null, user1));
    }

    protected void getAllCustomer(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        UserService userService = new UserService();
        PrintWriter writer = resp.getWriter();
        System.out.println("查询所有客户...");
        List<User> users = userService.getAllCustomer();
        writer.write(Result.resJson(users != null, users != null ? users : "暂无客户"));
    }
}