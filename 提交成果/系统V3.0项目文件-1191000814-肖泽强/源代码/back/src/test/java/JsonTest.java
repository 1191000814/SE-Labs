import com.google.gson.Gson;
import org.junit.Test;
import pojo.FrontAdd;
import pojo.FrontAddCart;
import pojo.User;

public class JsonTest{

    Gson gson = new Gson();

    @Test
    public void test(){
        User user = new User(1, "xzq", "020111", "wer", 3, 789);
        String s = gson.toJson(user);
        System.out.println(s);
        User user1 = gson.fromJson(s, User.class);
        System.out.println(user1);
        System.out.println(user1.equals(user));
    }

    @Test
    public void test1(){
        String s = "{\"goodItems\":{\"goodsId\":1,\"num\":1,\"discountRate\":1,\"goodsName\":\"鞋子\"},\"orderId\":\"50\"}";
        FrontAddCart cart = gson.fromJson(s, FrontAddCart.class);
        System.out.println(cart);
    }

    @Test
    public void test3(){

    }
}
