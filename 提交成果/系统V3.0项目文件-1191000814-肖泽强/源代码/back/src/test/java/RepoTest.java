import mapper.RepoMapper;
import org.junit.Test;
import pojo.RepoInfo;
import service.RepoService;
import utils.MyBatisUtils;

import java.util.List;

public class RepoTest{

    RepoService repoService = new RepoService();
    RepoMapper repoMapper = MyBatisUtils.getSqlSession().getMapper(RepoMapper.class);

    @Test
    public void test(){
        repoService.modifyNum(4, 70, 1);
    }

    @Test
    public void test1(){
        List<RepoInfo> infos = repoService.getAll(1);
        System.out.println(infos);
    }

    @Test
    public void test2(){
        List<RepoInfo> n = repoService.search("i", 7);
        System.out.println(n);
    }

    @Test
    public void testAll(){
    }

    @Test
    public void testByName(){
        boolean flag = repoService.dispatch(1,3, 1 ,10);
        System.out.println(flag);
    }

    @Test
    public void testUpdate(){
        repoMapper.updateById(new RepoInfo(1, 12, 1));
    }
}
