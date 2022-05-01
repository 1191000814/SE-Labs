
import dao.DraftOrderDao;
import dao.OrderDao;
import dao.OrderItemDao;
import entity.DraftOrderVo;
import entity.OrderItemVo;
import entity.OrderVo;
import utils.JdbcUtils;
import utils.Utils;

import java.util.Date;
import java.util.Properties;

public class Test {
    public static void main(String[] args) {
        OrderItemDao orderItemDao = new OrderItemDao();
        orderItemDao.add(new OrderItemVo(1, 10, 1, 1));
        orderItemDao.remove(3);
        JdbcUtils.commitAndClose();
    }
}
