import org.junit.Test;
import pojo.Goods;
import service.GoodsService;

import java.util.List;

public class GoodsTest{

    GoodsService goodsService = new GoodsService();

    @Test
    public void test(){
    }

    @Test
    public void testSearch(){
        List<Goods> n = goodsService.search("n");
        System.out.println(n);
    }

    @Test
    public void testAll(){
        List<Goods> n = goodsService.getAll();
        System.out.println(n);
    }

    @Test
    public void testAdd(){
        boolean isAdd = goodsService.add(new Goods(1, "Mybatis", 12, 23, 34, 789, "hzuba"));
        System.out.println(isAdd);
    }

    @Test
    public void testDel(){
        boolean delete = goodsService.delete(11);
        System.out.println(delete);
    }

    @Test
    public void testUpdate(){
        boolean isUpdate = goodsService.update(new Goods(10, "Mybatis", 12, 23, 34, 711, "uuza"));
        System.out.println(isUpdate);
    }
}
