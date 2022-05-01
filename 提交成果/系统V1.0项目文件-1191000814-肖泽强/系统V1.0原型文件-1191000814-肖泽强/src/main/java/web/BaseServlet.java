package web;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;

/*
所有Servlet程序的父类,封装了执行反射方法的共有操作
 */
public abstract class BaseServlet extends HttpServlet{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException{
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=utf-8");
        // 这两个都需要设置,一个请求端,一个是回复端
        String action = req.getParameter("action");
        // 写好与jsp页面参数同名的方法名,如login,register,然后通过反射调用
        Method method;
        try{
            method = this.getClass().getDeclaredMethod(action, HttpServletRequest.class, HttpServletResponse.class);
            System.out.println(action);
            method.invoke(this, req, resp);
        } catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
            // 一定要记得把异常抛给Filter过滤器
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        doPost(req, resp);
    }
}
