package web;

import pojo.Goods;
import pojo.Result;
import service.GoodsService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GoodsServlet extends BaseServlet{

    public void search(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        GoodsService goodsService = new GoodsService();
        Goods goods = (Goods) parseData(Goods.class, req, "查询商品");
        String name = goods.getName();
        List<Goods> infos;
        if("".equals(name) || name == null){
            System.out.println("查询全部商品");
            infos = goodsService.getAll();
        }
        else
            infos = goodsService.search(goods.getName());
        System.out.println(infos);
        boolean flag = infos.size() > 0;
        resp.getWriter().write(Result.resJson(flag, flag ? infos : "没有查询到任何结果"));
    }

    public void getAll(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        GoodsService goodsService = new GoodsService();
        System.out.println("获取全部商品");
        List<Goods> infos = goodsService.getAll();
        boolean flag = infos.size() > 0;
        resp.getWriter().write(Result.resJson(flag, flag ? infos : "仓库里没有任何商品"));
    }

    public void add(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        GoodsService goodsService = new GoodsService();
        Goods goods = (Goods)parseData(Goods.class, req, "添加商品");
        goods.setImg(null);
        boolean flag = goodsService.add(goods);
        if(flag)
            System.out.println("成功添加: " + goods.getName());
        resp.getWriter().write(Result.resJson(flag, flag ? null : "添加失败, 已经有相同的商品"));
    }

    public void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        GoodsService goodsService = new GoodsService();
        Goods goods = (Goods)parseData(Goods.class, req, "删除商品");
        boolean flag;
        try{
            flag = goodsService.delete(goods.getId());
        }catch(Exception e){
            // 如果由于外键约束无法删除
            System.out.println("由于外键约束, 无法删除" + goods.getName());
            resp.getWriter().write(Result.resJson(false, "仓库中已经有该种货品, 无法删除"));
            return;
        }
        resp.getWriter().write(Result.resJson(flag, flag ? null : "没有对应id的商品"));
    }

    public void update(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        GoodsService goodsService = new GoodsService();
        Goods goods = (Goods)parseData(Goods.class, req, "修改商品");
        boolean flag = goodsService.update(goods);
        resp.getWriter().write(Result.resJson(flag, flag ? null : "修改失败"));
    }
}