package service;

import lombok.SneakyThrows;
import mapper.CustomerMapper;
import org.apache.ibatis.session.SqlSession;
import pojo.Customer;
import utils.MyBatisUtils;

import java.util.List;

public class CustomerService{

    @SneakyThrows
    public boolean register(Customer customer){
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        CustomerMapper customerMapper = sqlSession.getMapper(CustomerMapper.class);
        if(customer == null)
            return false;
        if(customerMapper.getByFullName(customer.getName()) != null)
            return false;
        int maxId = customerMapper.getMaxId();
        customer.setId(maxId + 1);
        System.out.println(customer);
        customerMapper.insert(customer);
        sqlSession.close();
        return true;
    }

    public Customer login(String name, String password){
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        CustomerMapper customerMapper = sqlSession.getMapper(CustomerMapper.class);
        Customer customer = customerMapper.getByNameAndPassword(name, password);
        sqlSession.close();
        return customer;
    }

    public Customer getByFullName(String name){
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        CustomerMapper customerMapper = sqlSession.getMapper(CustomerMapper.class);
        Customer customer = customerMapper.getByFullName(name);
        sqlSession.close();
        return customer;
    }

    public Customer getById(int id){
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        CustomerMapper customerMapper = sqlSession.getMapper(CustomerMapper.class);
        Customer customer = customerMapper.getById(id);
        sqlSession.close();
        return customer;
    }

    // 注意这里name不能修改
    public boolean update(Customer customer){
        if(customer == null)
            return false;
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        CustomerMapper customerMapper = sqlSession.getMapper(CustomerMapper.class);
        boolean flag = customerMapper.updateById(customer) > 0;
        sqlSession.close();
        return flag;
    }

    public boolean delete(int id){
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        CustomerMapper customerMapper = sqlSession.getMapper(CustomerMapper.class);
        boolean flag = customerMapper.deleteById(id) > 0;
        sqlSession.close();
        return flag;
    }

    public List<Customer> search(String pattern){
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        CustomerMapper customerMapper = sqlSession.getMapper(CustomerMapper.class);
        List<Customer> customers = customerMapper.getByName("%" + pattern + "%");
        sqlSession.close();
        return customers;
    }

    public List<Customer> getAll(){
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        CustomerMapper customerMapper = sqlSession.getMapper(CustomerMapper.class);
        List<Customer> customers = customerMapper.getAll();
        sqlSession.close();
        return customers;
    }
}
