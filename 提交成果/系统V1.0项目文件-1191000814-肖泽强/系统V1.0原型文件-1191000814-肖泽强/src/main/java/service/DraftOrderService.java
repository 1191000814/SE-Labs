package service;

import dao.DraftOrderDao;
import entity.DraftOrderVo;
import utils.JdbcUtils;

import java.util.List;

public class DraftOrderService {

    public void addDraftOrder(DraftOrderVo draftOrderVo){

    }

    public DraftOrderVo getById(int id){
        DraftOrderDao draftOrderDao = new DraftOrderDao();
        DraftOrderVo draftOrderVo = draftOrderDao.getById(id);
        JdbcUtils.commitAndClose(draftOrderDao.getaConnection());
        return draftOrderVo;
    }

    public void remove(int id){
        DraftOrderDao draftOrderDao = new DraftOrderDao();
        draftOrderDao.remove(id);
        JdbcUtils.commitAndClose(draftOrderDao.getaConnection());
    }


    public void pay(int id){
        DraftOrderDao draftOrderDao = new DraftOrderDao();
        DraftOrderVo draftOrderVo = draftOrderDao.getById(id);
        draftOrderVo.setPay(1);
        draftOrderDao.modify(draftOrderVo);
        JdbcUtils.commitAndClose(draftOrderDao.getaConnection());
    }

    public void refund(int id){
        DraftOrderDao draftOrderDao = new DraftOrderDao();
        DraftOrderVo draftOrderVo = draftOrderDao.getById(id);
        draftOrderVo.setRefund(1);
        draftOrderDao.modify(draftOrderVo);
        JdbcUtils.commitAndClose(draftOrderDao.getaConnection());
    }

    public List<DraftOrderVo> getAll(){
        DraftOrderDao draftOrderDao = new DraftOrderDao();
        List<DraftOrderVo> aList = draftOrderDao.getAll();
        JdbcUtils.commitAndClose(draftOrderDao.getaConnection());
        return aList;
    }

}
