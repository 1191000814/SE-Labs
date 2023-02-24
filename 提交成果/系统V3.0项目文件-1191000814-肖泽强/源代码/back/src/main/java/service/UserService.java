package service;

import mapper.UserMapper;
import org.apache.ibatis.session.SqlSession;
import pojo.User;
import utils.MyBatisUtils;

import java.util.List;

public class UserService{

    public User register(User user){
        // 先检查是否用户名已经存在(不区分大小写)
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        if(userMapper.getByFullName(user.getUsername()) != null)
            return null;
        int maxId = userMapper.getMaxId();
        user.setId(maxId + 1);
        userMapper.insert(user);
        sqlSession.close();
        return user;
    }

    public User login(String username, String password){
        if(username == null || password == null)
            return null;
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = userMapper.getByNameAndPassword(username, password);
        sqlSession.close();
        return user;
    }

    public List<User> getAllCustomer(){
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        List<User> users = userMapper.getAllCustomer();
        sqlSession.close();
        return users;
    }

    // 按名字模糊查询
    public List<User> getByName(String username){
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        List<User> users = userMapper.getByName("%" + username + "%");
        sqlSession.close();
        return users;
    }

    public User getByFullName(String username){
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = userMapper.getByFullName(username);
        sqlSession.close();
        return user;
    }

    public User getById(int id){
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = userMapper.getById(id);
        sqlSession.close();
        return user;
    }
}