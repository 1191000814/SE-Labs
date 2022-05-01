package dao;

import entity.DraftOrderVo;
import entity.OrderItemVo;
import utils.JdbcUtils;
import utils.MyJdbcUtils;
import utils.Utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderItemDao {

    private Connection aConnection;

    public OrderItemDao(){
        this.aConnection = JdbcUtils.getConnection();
    }


    public void add(OrderItemVo orderItemVo) {
        int goodId = orderItemVo.getGoodId();
        int num = orderItemVo.getNum();
        int id = orderItemVo.getId();
        float discountRate = orderItemVo.getDiscountRate();
        MyJdbcUtils.executeUpdate("INSERT INTO order_item VALUES ('"+goodId+"','"+num+"','"+0+"','"+discountRate+"')", aConnection);
    }


    public void remove(int id) {
        MyJdbcUtils.executeUpdate("DELETE FROM order_item WHERE id='"+id+"'", aConnection);
    }


    public void modify(OrderItemVo orderItemVo) {
        int goodId = orderItemVo.getGoodId();
        int num = orderItemVo.getNum();
        int id = orderItemVo.getId();
        float discountRate = orderItemVo.getDiscountRate();
        MyJdbcUtils.executeUpdate("update order_item set good_id = '" + goodId + "', num = '" + num + "', discount_rate = '" + discountRate + "' where id = '" + id + "'", aConnection);
    }


    public OrderItemVo getById(int id) {
        ResultSet aResultSet = MyJdbcUtils.executeQuery("select * from order_item where id = '" + id + "'", aConnection);
        try {
            aResultSet.next();
            OrderItemVo orderItemVo = new OrderItemVo(aResultSet.getInt(1),aResultSet.getInt(2),aResultSet.getInt(3),aResultSet.getFloat(4));
            aResultSet.close();
            return orderItemVo;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }


    public List<OrderItemVo> getAll(){
        List<OrderItemVo> aList = new ArrayList<>();
        ResultSet aResultSet = MyJdbcUtils.executeQuery("select * from order_item", aConnection);
        try {
            while(aResultSet.next()) {
                aList.add(new OrderItemVo(aResultSet.getInt(1),aResultSet.getInt(2),aResultSet.getInt(3),aResultSet.getFloat(4)));
            }
            aResultSet.close();
            return aList;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public Connection getaConnection() {
        return aConnection;
    }
}
