package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Controller
public class ASController{

    @GetMapping("create")
    public String create(HttpServletRequest req, @RequestParam("count") int count, @RequestParam("range") int range,
                         String add, String sub, String mul, String div){
        List<Map<String, String>> list = new ArrayList<>();
        List<String> operatorList = new ArrayList<>(); // 运算符号列表
        HttpSession session = req.getSession();
        add = add == null? (String) session.getAttribute("add") : add;
        sub = sub == null? (String) session.getAttribute("sub") : sub;
        mul = mul == null? (String) session.getAttribute("mul") : mul;
        div = div == null? (String) session.getAttribute("div") : div;
        for(int i = 0; i < 4; i++){
            if("1".equals(add))
                operatorList.add("+");
            if("1".equals(sub))
                operatorList.add("-");
            if("1".equals(mul))
                operatorList.add("*");
            if("1".equals(div))
                operatorList.add("/");
        }
        for(int i = 0; i < count; i++){
            Map<String, String> map = new HashMap<>();
            List<Integer> equationList = buildEquation(operatorList, range);
            String operator = operatorList.get(equationList.get(3));
            map.put("param1", String.valueOf(equationList.get(0)));
            map.put("param2", String.valueOf(equationList.get(1)));
            map.put("result", String.valueOf(equationList.get(2)));
            if("*".equals(operator))
                // * 和 /需要换成 ✕ 和 ÷
                map.put("operator", "✕");
            else if("/".equals(operator))
                map.put("operator", "÷");
            else
                map.put("operator", operator);
            list.add(map);
        }
        session.setAttribute("list", list);
        session.setAttribute("count", count);
        session.setAttribute("range", range);
        session.setAttribute("add", add);
        session.setAttribute("sub", sub);
        session.setAttribute("mul", mul);
        session.setAttribute("div", div);
        // 因为计算式需要在check,jsp中使用,所以需要保存在session中
        return "practise";
    }

    @PostMapping("check")
    public String check(HttpServletRequest req){
        List<Boolean> checkList = new ArrayList<>(); // 检查结果
        List<String> resultList = new ArrayList<>(); // 正确答案
        List<String> myList = new ArrayList<>(); // 我的答案
        int count = (int) req.getSession().getAttribute("count");
        int wrongCount = 0;
        for(int i = 0; i < count; i++){
            String myResult = req.getParameter("result" + i);
            String rightResult = req.getParameter("rightResult" + i);
            boolean check = myResult.trim().equals(rightResult.trim());
            if(! check)
                wrongCount ++;
            checkList.add(check);
            myList.add(myResult);
            resultList.add(rightResult);
        }
        double accuracy = BigDecimal.valueOf(1 - (float) wrongCount / checkList.size()).setScale(4, RoundingMode.HALF_UP).doubleValue();
        req.setAttribute("checkList", checkList);
        req.setAttribute("resultList", resultList);
        req.setAttribute("myList", myList);
        req.setAttribute("wrongCount", wrongCount);
        req.setAttribute("accuracy", (accuracy * 100) + "%");
        return "check";
        // 跳转到练习页面
    }

    @GetMapping("reset")
    public String reset(HttpSession session){
        session.invalidate();
        return "index";
    }

    /**
     * 生成一个等式,要求使用每个运算符的概率相等
     *
     * @param operatorList 可用的运算符列表
     * @return 等式的参数和结果列表
     */
    public List<Integer> buildEquation(List<String> operatorList, int range){
        Random r = new Random();
        int index = r.nextInt(operatorList.size());
        String operator = operatorList.get(index);
        int param1;
        int param2;
        int result;
        if("+".equals(operator)){
            do{
                param1 = r.nextInt(range);
                param2 = r.nextInt(range);
            } while(param1 + param2 > range);
            result = param1 + param2;
        } else if("-".equals(operator)){
            do{
                param1 = r.nextInt(range);
                param2 = r.nextInt(range);
            } while(param1 - param2 < 0);
            result = param1 - param2;
        } else if("*".equals(operator)){
            do{
                param1 = r.nextInt(range / 2 + 1);
                param2 = r.nextInt(range / 2 + 1);
            } while(param1 * param2 > range);
            result = param1 * param2;
        } else{
            do{
                param1 = r.nextInt(range - 1) + 1; // 范围是1-range
            } while(getDivisors(param1).size() == 0);
            List<Integer> divisors = getDivisors(param1);
            param2 = divisors.get(r.nextInt(divisors.size())); // 随机选一个参数1的约数
            result = param1 / param2;
        }
        List<Integer> equationList = new ArrayList<>();
        equationList.add(param1);
        equationList.add(param2);
        equationList.add(result);
        equationList.add(index);
        return equationList;
    }

    /**
     * @param num 一个数
     * @return 这个数的所有大于1的约数
     */
    public List<Integer> getDivisors(int num){
        List<Integer> divisors = new ArrayList<>();
        for(int i = 2; i < num; i++){
            if(num % i == 0)
                divisors.add(i);
        }
        return divisors;
    }
}