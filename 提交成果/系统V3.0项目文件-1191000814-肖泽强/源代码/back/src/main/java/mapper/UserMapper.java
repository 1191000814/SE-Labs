package mapper;

import pojo.User;

import java.util.List;

public interface UserMapper{

    User getById(int id);

    User getByFullName(String username);

    List<User> getByName(String pattern);

    List<User> getAll();

    User getByNameAndPassword(String username, String password);

    void deleteById(int id);

    void updateById(User user);

    void insert(User user);

    int getMaxId();

    List<User> getAllCustomer();
}
