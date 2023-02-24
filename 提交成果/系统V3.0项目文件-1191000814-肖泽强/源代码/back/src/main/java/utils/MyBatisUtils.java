package utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/*
MyBatis的基本使用步骤:
1.用MyBatisUtils返回一个sqlSession对象,(包含全局配置文件）
2.sql映射文件(Mapper),配置了每一个sql，以及sql的封装规则等(用id唯一识别)
3.将sql映射文件注册在全局配置文件中
4.用返回的sqlSession对象,传入id和参数,调用对应的sql进行增删改查
5.使用结束之后记得把sqlSession关闭
 */

/*
 * 总结:
 * 1、接口式编程
 * 	原生:    	Dao		====>  DaoImpl
 * 	mybatis:	Mapper	====>  xxMapper.xml
 *
 * 2、SqlSession代表和数据库的一次会话；用完必须关闭；
 * 3、SqlSession和connection一样她都是非线程安全。每次使用都应该去获取新的对象。
 * 4、mapper接口没有实现类，但是mybatis会为这个接口生成一个代理对象。
 * 		（将接口和xml进行绑定）
 * 		EmployeeMapper empMapper =	sqlSession.getMapper(EmployeeMapper.class);
 * 5、两个重要的配置文件：
 * 		mybatis的全局配置文件：包含数据库连接池信息，事务管理器信息等...系统运行环境信息
 * 		sql映射文件：保存了每一个sql语句的映射信息：
 * 			将sql抽取出来。
 * @author lfy
 *
 */
public abstract class MyBatisUtils{

    private static final SqlSessionFactory sqlSessionFactory;

    static {
        String resource = "mybatis-config.xml";
        // 全局配置文件的地址
        InputStream inputStream = null;
        try{
            inputStream = Resources.getResourceAsStream(resource);
        } catch(IOException e){
            e.printStackTrace();
        }
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    public static SqlSession getSqlSession(){
        return sqlSessionFactory.openSession(true);
        // 设置了自动提交
    }
}
