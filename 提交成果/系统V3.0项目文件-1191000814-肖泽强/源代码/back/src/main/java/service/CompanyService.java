package service;

import mapper.CompanyMapper;
import mapper.GoodsMapper;
import org.apache.ibatis.session.SqlSession;
import pojo.Company;
import utils.MyBatisUtils;

public class CompanyService{

    // 获取公司信息
    public Company getInfo(){
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        CompanyMapper companyMapper = sqlSession.getMapper(CompanyMapper.class);
        Company info = companyMapper.getInfo();
        sqlSession.close();
        return info;
    }

    // 更新公司信息
    public boolean update(Company company){
        if(company == null)
            return false;
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        CompanyMapper companyMapper = sqlSession.getMapper(CompanyMapper.class);
        Company myCompany = getInfo();
        if(company.getCapital() == null)
            company.setCapital(myCompany.getCapital());
        if(company.getCost() == null)
            company.setCost(myCompany.getCost());
        if(company.getSales() == null)
            company.setSales(myCompany.getSales());
        if(company.getRemain() == null)
            company.setRemain(myCompany.getRemain());
        boolean flag = companyMapper.update(company) > 0;
        sqlSession.close();
        return flag;
    }
}