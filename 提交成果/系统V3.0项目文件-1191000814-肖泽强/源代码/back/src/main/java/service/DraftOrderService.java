package service;

import mapper.CompanyMapper;
import mapper.DraftOrderMapper;
import mapper.OrderItemMapper;
import mapper.UserMapper;
import org.apache.ibatis.session.SqlSession;
import pojo.Company;
import pojo.DraftOrderVo;
import pojo.OrderItemVo;
import pojo.User;
import utils.MyBatisUtils;
import utils.OrderUtils;

import java.util.HashMap;
import java.util.List;

public class DraftOrderService {

    public void addDraftOrder(DraftOrderVo draftOrderVo){
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        DraftOrderMapper mapper = sqlSession.getMapper(DraftOrderMapper.class);
        // 获取连接

        draftOrderVo.setId(mapper.getMaxId() + 1);
        mapper.add(draftOrderVo);
        sqlSession.close();
    }

    public DraftOrderVo getById(int id){
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        DraftOrderMapper mapper = sqlSession.getMapper(DraftOrderMapper.class);
        DraftOrderVo draftOrderVo = mapper.getById(id);
        sqlSession.close();
        return draftOrderVo;
    }

    public boolean remove(int id) {
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        DraftOrderMapper mapper = sqlSession.getMapper(DraftOrderMapper.class);
        // 获取连接

        DraftOrderVo draftOrderVo = mapper.getById(id);
        if(draftOrderVo.getRefund() == 1){
            mapper.remove(id);
            sqlSession.close();
            return true;
        }
        else {
            sqlSession.close();
            return false;
        }
    }

    public boolean pay(int id, int userId){
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        DraftOrderMapper mapper = sqlSession.getMapper(DraftOrderMapper.class);
        OrderItemMapper orderItemMapper = sqlSession.getMapper(OrderItemMapper.class);
        // 获取连接

        DraftOrderVo draftOrderVo = mapper.getById(id);
        HashMap<Integer,Integer> map = new HashMap<>();
        boolean flag = true;

        //减少仓库中货物数量
        String goods = draftOrderVo.getGoods();
        List<Integer> orderItemsID = OrderUtils.getItemsIdList(goods);
        for (int i : orderItemsID){
            OrderItemVo orderItemVo = orderItemMapper.getById(i);
            int goodId = orderItemVo.getGoodsId();
            int num = orderItemVo.getNum();
            RepoService repoService = new RepoService();
            if(!repoService.modifyNum(goodId,-num,1)){
                flag = false;
                break;
            }
            map.put(goodId, -num);
        }
        if(! flag){
            for(int i : map.keySet()){
                int num = - map.get(i);
                RepoService repoService = new RepoService();
                repoService.modifyNum(i, num,1);
            }
            sqlSession.close();
            return false;
        }

        //计算业绩
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = userMapper.getById(userId);
        if(user != null){
            user.setPerformance(user.getPerformance() + draftOrderVo.getProfit());
            userMapper.updateById(user);
        }

        //公司金额变化
        CompanyMapper companyMapper = sqlSession.getMapper(CompanyMapper.class);
        Company company = companyMapper.getInfo();
        company.setCapital(company.getCapital()+draftOrderVo.getPrice());
        company.setRemain(company.getRemain()-(draftOrderVo.getPrice()-draftOrderVo.getProfit()));
        company.setSales(company.getSales()+draftOrderVo.getPrice());
        companyMapper.update(company);

        //设置已支付
        draftOrderVo.setPay(1);
        mapper.modify(draftOrderVo);

        sqlSession.close();
        return true;
    }

    // 退款
    public boolean refund(int id, int userId){
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        DraftOrderMapper mapper = sqlSession.getMapper(DraftOrderMapper.class);
        OrderItemMapper orderItemMapper = sqlSession.getMapper(OrderItemMapper.class);
        // 获取连接
        DraftOrderVo draftOrderVo = mapper.getById(id);
        if(draftOrderVo.getPay() == 0) // 还未支付
            return false;

        draftOrderVo.setRefund(1);
        mapper.modify(draftOrderVo);

        //增加仓库数量
        String goods = draftOrderVo.getGoods();
        List<Integer> orderItemsID = OrderUtils.getItemsIdList(goods);
        for (int i : orderItemsID){
            OrderItemVo orderItemVo = orderItemMapper.getById(i);
            int goodId = orderItemVo.getGoodsId();
            int num = orderItemVo.getNum();
            RepoService repoService = new RepoService();
            if(!repoService.modifyNum(goodId,num,1)){
                System.out.println("发生异常状况...");
            }
        }

        //计算业绩
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = userMapper.getById(userId);
        user.setPerformance(user.getPerformance()-draftOrderVo.getProfit());
        userMapper.updateById(user);

        //公司金额变化
        CompanyMapper companyMapper = sqlSession.getMapper(CompanyMapper.class);
        Company company = companyMapper.getInfo();
        company.setCapital(company.getCapital()-draftOrderVo.getPrice());
        company.setRemain(company.getRemain()+(draftOrderVo.getPrice()-draftOrderVo.getProfit()));
        company.setSales(company.getSales()-draftOrderVo.getPrice());
        companyMapper.update(company);
        sqlSession.close();

        return true;
    }

    public List<DraftOrderVo> getAll(){
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        DraftOrderMapper mapper = sqlSession.getMapper(DraftOrderMapper.class);
        // 获取连接
        List<DraftOrderVo> draftOrderVos = mapper.getAll();
        sqlSession.close();
        return draftOrderVos;
    }

    public List<DraftOrderVo> getByCustomerId(int customerId){
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        DraftOrderMapper mapper = sqlSession.getMapper(DraftOrderMapper.class);
        // 获取连接
        List<DraftOrderVo> draftOrderVos = mapper.getByCustomerId(customerId);
        sqlSession.close();
        return draftOrderVos;
    }

}
