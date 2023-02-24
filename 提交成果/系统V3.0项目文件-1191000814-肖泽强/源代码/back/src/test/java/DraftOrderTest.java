import mapper.DraftOrderMapper;
import org.junit.Test;
import pojo.DraftOrderVo;
import service.DraftOrderService;
import utils.MyBatisUtils;

import java.util.List;

public class DraftOrderTest{

    static DraftOrderMapper mapper = MyBatisUtils.getSqlSession().getMapper(DraftOrderMapper.class);
    DraftOrderService service = new DraftOrderService();

    @Test
    public void test(){
        List<DraftOrderVo> all = service.getAll();
        System.out.println(all == null);
    }

    @Test
    public void test1(){
    }

    @Test
    public void test2(){
    }
}
