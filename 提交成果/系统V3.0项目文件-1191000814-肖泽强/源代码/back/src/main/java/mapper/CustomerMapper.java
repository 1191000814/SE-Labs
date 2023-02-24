package mapper;

import pojo.Customer;

import java.util.List;

public interface CustomerMapper{

    Customer getById(int id);

    Customer getByFullName(String username);

    Customer getByNameAndPassword(String name, String password);

    List<Customer> getByName(String pattern);

    List<Customer> getAll();

    int deleteById(int id);

    int updateById(Customer customer);

    int insert(Customer customer);

    int getMaxId();
}
