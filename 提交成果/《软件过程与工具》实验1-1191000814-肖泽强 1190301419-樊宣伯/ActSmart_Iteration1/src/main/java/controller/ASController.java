package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class ASController{

    @GetMapping("create")
    public String create(HttpSession session){
        List<Map<String, String>> list = new ArrayList<>();
        Random r = new Random();
        for(int i = 0; i < 10; i ++){
            Map<String, String> map = new HashMap<>();
            int param1 = r.nextInt(100);
            int param2 = r.nextInt(100);
            String operator = r.nextBoolean() ? "+" : "-";
            int result = operate(param1, param2, operator);
            if(result > 100 || result < 0){
                // 结果超出100或者为负数,重新循环一次
                i --;
                continue;
            }
            map.put("param1", String.valueOf(param1));
            map.put("param2", String.valueOf(param2));
            map.put("operator", operator);
            map.put("result", String.valueOf(result));
            list.add(map);
        }
        session.setAttribute("list", list);
        // 计算式保存在session中,在一个会话中使用
        return "practise";
    }

    @PostMapping("check")
    public String check(HttpServletRequest req){
        List<Boolean> checkList = new ArrayList<>(); // 检查结果
        List<String> resultList = new ArrayList<>(); // 正确答案
        List<String> myList = new ArrayList<>(); // 我的答案
        for(int i = 0; i < 10; i ++){
            String myResult = req.getParameter("result" + i);
            String rightResult = req.getParameter("rightResult" + i);
            checkList.add(myResult.equals(rightResult));
            myList.add(myResult);
            resultList.add(rightResult);
        }
        req.setAttribute("checkList", checkList);
        req.setAttribute("resultList", resultList);
        req.setAttribute("myList", myList);
        return "check";
        // 跳转到练习页面
    }

    public int operate(int param1, int param2, String operator){
        if("+".equals(operator))
            return param1 + param2;
        else if("-".equals(operator))
            return param1 - param2;
        else throw new RuntimeException();
    }
}
