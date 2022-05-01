package utils;
// 封装了连接,关闭数据库的静态方法类

import com.alibaba.druid.pool.DruidDataSource;

import javax.sql.DataSource;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public abstract class JdbcUtils{

    static DruidDataSource dataSource = new DruidDataSource();
    static ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

    static {
        //Properties properties = getProperties();
        dataSource.setUrl("jdbc:mysql://localhost:3306/software_work?useSSL=true&useUnicode=true&characterEncoding=UTF-8");
        dataSource.setUsername("root");
        dataSource.setPassword("15387162218qq");
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
    }

    /**
     * 通过Druid连接数据库 version--5.3
     * @return 连接对象
     */
    public static Connection getConnection(){
        DataSource dds;
        // Druid连接池
        try{
            // 根据prop映射直接建立连接池
            Connection connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            return connection;
        } catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取本地原有的数据库连接,如果没有则新建一个保存到本地
     * 若使用此方法,正常情况下connection永不关闭,直到提交或者回退
     * version--6.0
     * @return 连接对象
     */
    public static Connection getLocalConnection(){
        Connection connection = threadLocal.get();
        if(connection == null){
            connection = getConnection();
            try{
                connection.setAutoCommit(false);
                // 一定要设置不能自动提交
            } catch(SQLException e){
                e.printStackTrace();
            }
            threadLocal.set(connection);
        }
        return connection;
    }

    /**
     * 提交数据库事务并关闭连接
     * version--6.0
     */
    public static void commitAndClose(){
        Connection connection = threadLocal.get();
        if(connection != null){
            try{
                connection.commit();
            } catch(SQLException e){
                e.printStackTrace();
            }
            close(connection, null);
        }
        threadLocal.remove();
        // 关闭connection之后一定要remove,否则会出错
    }

    /**
     * 回退数据库事务并关闭连接
     * version--6.0
     */
    public static void rollbackAndClose(){
        Connection connection = threadLocal.get();
        if(connection != null){
            try{
                connection.rollback();
            } catch(SQLException e){
                e.printStackTrace();
            }
            close(connection, null);
        }
        threadLocal.remove();
    }

    /**
     * 关闭 修改 语句的资源,关闭2个资源
     * @param connection 连接类
     * @param ps 执行sql语句类
     */
    public static void close(Connection connection, PreparedStatement ps){
        try{
            if(connection != null)
                connection.close();
            if(ps != null)
                ps.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * 关闭 查询 语句的资源,关闭3个资源
     * @param connection 连接类
     * @param ps 执行sql语句类
     * @param rs 查询结果集
     */
    public static void close(Connection connection, PreparedStatement ps, ResultSet rs){
        try{
            if(connection != null)
                connection.close();
            if(ps != null)
                ps.close();
            if(rs != null)
                rs.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * 将.properties文件中的信息加载到Map集合中,默认是prop/jdbc-config.properties
     * @return 对应的Properties集合
     */
    public static Properties getProperties(){
        Properties properties = new Properties();
        // 建立一个集合
        String path = "resources/jdbc-config.properties";
        try{
            FileReader fr = new FileReader(path);
            // 文件的读取流
            properties.load(fr);
            // 将文件中的信息录到集合中
        }catch(IOException e){
            e.printStackTrace();
        }
        return properties;
    }

    public static void commitAndClose(Connection connection){
        try {
            connection.commit();
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
