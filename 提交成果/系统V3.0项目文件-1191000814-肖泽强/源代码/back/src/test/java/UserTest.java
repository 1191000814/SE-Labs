import mapper.UserMapper;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import pojo.User;
import service.UserService;
import utils.MyBatisUtils;

import java.util.List;

public class UserTest{

    UserService userService = new UserService();
    UserMapper userMapper;

    @Test
    public void testLogin(){
        User xzq = userService.login("xzq", "123456");
        System.out.println(xzq);
    }

    @Test
    public void testRegister(){
        User user = new User(1, "jty", "123456", "19hu@gmail", 2, 0);
        User register = userService.register(user);
        System.out.println(register);
    }

    @Test
    public void test(){
    }

    @Test
    public void test1(){
        List<User> users = userService.getByName("z");
        System.out.println("结果:" + users);
    }

    @Test
    public void test2(){
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        mapper.insert(new User(7, "tyu", "123", "zaza", 3, 0));
        sqlSession.close();
    }
}
