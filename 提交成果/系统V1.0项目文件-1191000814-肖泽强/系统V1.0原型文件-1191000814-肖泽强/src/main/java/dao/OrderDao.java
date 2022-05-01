package dao;

import entity.DraftOrderVo;
import entity.OrderVo;
import utils.JdbcUtils;
import utils.MyJdbcUtils;
import utils.Utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderDao {

    private Connection aConnection;

    public OrderDao(){
        this.aConnection = JdbcUtils.getConnection();
    }


    public void add(OrderVo orderVo) {
        int id = orderVo.getId();
        String goods = orderVo.getGoods();
        Date createDate = orderVo.getCreateDate();
        Date ImDate = orderVo.getImDate();
        float price = orderVo.getPrice();
        int customerId = orderVo.getCustomerId();
        String createDateStr = Utils.dateToString(createDate);
        String ImDateStr = Utils.dateToString(ImDate);
        MyJdbcUtils.executeUpdate("INSERT INTO `order` VALUES ('"+0+"','"+goods+"','"+createDateStr+"','"+ImDateStr+"','"+price+"','"+customerId+"')", aConnection);
    }


    public void remove(int id) {
        MyJdbcUtils.executeUpdate("DELETE FROM `order` WHERE id='"+id+"'", aConnection);
    }


    public void modify(OrderVo orderVo) {
        int id = orderVo.getId();
        String goods = orderVo.getGoods();
        Date createDate = orderVo.getCreateDate();
        Date ImDate = orderVo.getImDate();
        float price = orderVo.getPrice();
        int customerId = orderVo.getCustomerId();
        String createDateStr = Utils.dateToString(createDate);
        String ImDateStr = Utils.dateToString(ImDate);
        MyJdbcUtils.executeUpdate("update `order` set goods = '" + goods + "', create_date = '" + createDateStr + "', Im_date = '" + ImDateStr + "', price = '" + price +  "', customer_id = '" + customerId + "' where id = '" + id + "'", aConnection);
    }


    public OrderVo getById(int id) {
        ResultSet aResultSet = MyJdbcUtils.executeQuery("select * from `order` where id = '" + id + "'", aConnection);
        try {
            aResultSet.next();
            OrderVo orderVo = new OrderVo(aResultSet.getInt(1), aResultSet.getString(2), Utils.stringToDate(aResultSet.getString(3)), Utils.stringToDate(aResultSet.getString(4)), aResultSet.getFloat(5),aResultSet.getInt(6));
            aResultSet.close();
            return orderVo;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public OrderVo getByCustomer(int customer_id) {
        ResultSet aResultSet = MyJdbcUtils.executeQuery("select * from `order` where customer_id = '" + customer_id + "'", aConnection);
        try {
            aResultSet.next();
            OrderVo orderVo = new OrderVo(aResultSet.getInt(1), aResultSet.getString(2), Utils.stringToDate(aResultSet.getString(3)), Utils.stringToDate(aResultSet.getString(4)), aResultSet.getFloat(5),aResultSet.getInt(6));
            aResultSet.close();
            return orderVo;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }


    public List<OrderVo> getAll(){
        List<OrderVo> orderVos = new ArrayList<>();
        ResultSet aResultSet = MyJdbcUtils.executeQuery("select * from `order`", aConnection);
        try {
            while(aResultSet.next()) {
                orderVos.add(new OrderVo(aResultSet.getInt(1), aResultSet.getString(2), Utils.stringToDate(aResultSet.getString(3)), Utils.stringToDate(aResultSet.getString(4)), aResultSet.getFloat(5),aResultSet.getInt(6)));
            }
            aResultSet.close();
            return orderVos;
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
