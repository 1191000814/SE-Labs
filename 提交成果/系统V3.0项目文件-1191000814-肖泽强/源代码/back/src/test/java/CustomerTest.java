import mapper.CustomerMapper;
import org.junit.Test;
import pojo.Customer;
import service.CustomerService;
import utils.MyBatisUtils;

import java.util.List;

public class CustomerTest{

    CustomerMapper customerMapper = MyBatisUtils.getSqlSession().getMapper(CustomerMapper.class);
    CustomerService service = new CustomerService();

    @Test
    public void test(){
        customerMapper.deleteById(1);
    }

    @Test
    public void test1(){
        List<Customer> all = customerMapper.getAll();
        System.out.println(all);
    }

    @Test
    public void testAdd(){
        service.register(new Customer(1, "wer", "123", "zj", "67", false));
    }
}