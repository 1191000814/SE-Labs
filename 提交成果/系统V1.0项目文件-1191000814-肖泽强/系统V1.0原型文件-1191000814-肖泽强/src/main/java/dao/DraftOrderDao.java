package dao;

import entity.DraftOrderVo;
import utils.JdbcUtils;
import utils.MyJdbcUtils;
import utils.Utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DraftOrderDao {

    private Connection aConnection;

    public DraftOrderDao(){
        this.aConnection = JdbcUtils.getConnection();
    }


    public void add(DraftOrderVo draftOrderVo) {
        Date checkDate = draftOrderVo.getCheckDate();
        int pay = draftOrderVo.getPay();
        int refund = draftOrderVo.getRefund();
        String goods = draftOrderVo.getGoods();
        float price = draftOrderVo.getPrice();
        int customerId = draftOrderVo.getCustomerId();
        String checkDateStr = Utils.dateToString(checkDate);
        MyJdbcUtils.executeUpdate("INSERT INTO draft_order VALUES ('"+0+"','"+checkDateStr+"','"+pay+"','"+refund+"','"+goods+"','"+price+"','"+customerId+"')", aConnection);
    }


    public void remove(int id) {
        MyJdbcUtils.executeUpdate("DELETE FROM draft_order WHERE id='"+id+"'", aConnection);
    }


    public void modify(DraftOrderVo draftOrderVo) {
        int id = draftOrderVo.getId();
        Date checkDate = draftOrderVo.getCheckDate();
        int pay = draftOrderVo.getPay();
        int refund = draftOrderVo.getRefund();
        String goods = draftOrderVo.getGoods();
        float price = draftOrderVo.getPrice();
        int customerId = draftOrderVo.getCustomerId();
        String checkDateStr = Utils.dateToString(checkDate);
        MyJdbcUtils.executeUpdate("update draft_order set check_date = '" + checkDateStr + "', pay = '" + pay + "', refund = '" + refund + "', goods = '" + goods + "', price = '" + price +  "', customer_id = '" + customerId + "' where id = '" + id + "'", aConnection);
    }


    public DraftOrderVo getById(int id) {
        ResultSet aResultSet = MyJdbcUtils.executeQuery("select * from draft_order where id = '" + id + "'", aConnection);
        try {
            aResultSet.next();
            DraftOrderVo draftOrderVo = new DraftOrderVo(aResultSet.getInt(1),Utils.stringToDate(aResultSet.getString(2)),aResultSet.getInt(3),aResultSet.getInt(4),aResultSet.getString(5), aResultSet.getFloat(6), aResultSet.getInt(7));
            aResultSet.close();
            return draftOrderVo;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public DraftOrderVo getByCustomer(int customerId) {
        ResultSet aResultSet = MyJdbcUtils.executeQuery("select * from draft_order where customer_id = '" + customerId + "'", aConnection);
        try {
            aResultSet.next();
            DraftOrderVo draftOrderVo = new DraftOrderVo(aResultSet.getInt(1),Utils.stringToDate(aResultSet.getString(2)),aResultSet.getInt(3),aResultSet.getInt(4),aResultSet.getString(5), aResultSet.getFloat(6), aResultSet.getInt(7));
            aResultSet.close();
            return draftOrderVo;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }


    public List<DraftOrderVo> getAll(){
        List<DraftOrderVo> aList = new ArrayList<>();
        ResultSet aResultSet = MyJdbcUtils.executeQuery("select * from draft_order", aConnection);
        try {
            while(aResultSet.next()) {
                aList.add(new DraftOrderVo(aResultSet.getInt(1),Utils.stringToDate(aResultSet.getString(2)),aResultSet.getInt(3),aResultSet.getInt(4),aResultSet.getString(5), aResultSet.getFloat(6), aResultSet.getInt(7)));
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
