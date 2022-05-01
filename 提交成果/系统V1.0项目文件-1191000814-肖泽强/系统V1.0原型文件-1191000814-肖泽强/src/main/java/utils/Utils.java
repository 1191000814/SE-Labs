package utils;

import com.mysql.cj.x.protobuf.MysqlxCrud;
import dao.OrderItemDao;
import entity.FronOrder;
import entity.OrderItemVo;
import entity.OrderVo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class Utils {

    public static String dateToString(Date date){
        String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        return dateStr;
    }

    public static Date stringToDate(String str) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            Date date = sdf.parse(str);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Integer> getItemsIdFromGoods(String goods){
        String[] aStrings = goods.split(" ");
        List<Integer> aList = new ArrayList<Integer>();
        for(String item : aStrings){
            int id = Integer.parseInt(item);
            aList.add(id);
        }
        return aList;
    }

    public static FronOrder orderTran(OrderVo orderVo){
        FronOrder fronOrder = new FronOrder();
        fronOrder.setId(orderVo.getId());

        fronOrder.setName("张三");

        fronOrder.setGoodsname("洋娃娃");

        OrderItemDao orderItemDao = new OrderItemDao();
        int num = orderItemDao.getById(Utils.getItemsIdFromGoods(orderVo.getGoods()).get(0)).getNum();

        fronOrder.setNumber(num);
        return fronOrder;

    }

    public static List<FronOrder> ordersTran(List<OrderVo> aList){
        List<FronOrder> bList = new ArrayList<FronOrder>();
        for(OrderVo order: aList){
            bList.add(orderTran(order));
        }
        return bList;
    }

}
