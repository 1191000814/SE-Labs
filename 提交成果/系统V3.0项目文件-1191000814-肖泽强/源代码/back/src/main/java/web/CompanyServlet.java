package web;

import pojo.Result;
import service.CompanyService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CompanyServlet extends BaseServlet{

    public void getInfo(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        System.out.println("\033[34;1m" + "\n-------> 获取公司经营状况: \033[0m");
        CompanyService companyService = new CompanyService();
        resp.getWriter().write(Result.resJson(true, companyService.getInfo()));
    }
}