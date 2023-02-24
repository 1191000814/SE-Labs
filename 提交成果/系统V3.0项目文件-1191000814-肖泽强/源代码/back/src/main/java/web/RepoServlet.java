package web;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pojo.Goods;
import pojo.RepoInfo;
import pojo.Result;
import service.GoodsService;
import service.RepoService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RepoServlet extends BaseServlet{

    // 不仅是接收前端数据的类, 也是回传数据给前端的类
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class FRepoInfo{
        private int goodsId;
        private String goodsName;
        private int goodsNum;
        private int repoId;
        private int oldRepo;
        private int newRepo;
        private float remain;
    }

    // 按名字搜索某个仓库的货物信息
    // 接收: goodsName, repoId
    public void search(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        RepoService repoService = new RepoService();
        FRepoInfo fRepoInfo = (FRepoInfo) parseData(FRepoInfo.class, req, "搜索仓库");
        System.out.println("搜索的仓库:" + fRepoInfo.repoId);
        System.out.println("搜索的字符串: " + fRepoInfo.goodsName);
        List<RepoInfo> infos;
        if("".equals(fRepoInfo.goodsName) || fRepoInfo.goodsName == null) // 查询所有
            infos = repoService.getAll(fRepoInfo.repoId); // 原RepoInfo仓库类(不含名称)
        else
            infos = repoService.search(fRepoInfo.goodsName, fRepoInfo.repoId); // 原RepoInfo仓库类(不含名称)
        List<FRepoInfo> info2s = getFRepoInfos(infos); // 这才是要返回前端的类
        System.out.println(info2s);
        boolean flag = info2s.size() > 0;
        resp.getWriter().write(Result.resJson(flag, flag ? info2s : "没有查询到任何结果"));
    }

    // 获取某个仓库全部货物信息
    // 前端数据: repoId
    public void getAll(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        RepoService repoService = new RepoService();
        FRepoInfo fRepoInfo = (FRepoInfo) parseData(FRepoInfo.class, req, "搜索仓库");
        List<RepoInfo> repoInfos = repoService.getAll(fRepoInfo.repoId);
        // 获取数据中的数组
        List<FRepoInfo> fRepoInfos = getFRepoInfos(repoInfos);
        boolean flag = fRepoInfos.size() > 0;
        resp.getWriter().write(Result.resJson(flag, flag ? fRepoInfos : "仓库里没有任何商品"));
    }

    // 调拨货物
    // 前端数据: goodsId, goodsNum, newRepo, oldRepo
    public void dispatch(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        RepoService repoService = new RepoService();
        FRepoInfo fRepoInfo = (FRepoInfo) parseData(FRepoInfo.class, req, "调拨货物");
        boolean flag = repoService.dispatch(fRepoInfo.oldRepo,fRepoInfo.newRepo, fRepoInfo.goodsId, fRepoInfo.goodsNum);
        resp.getWriter().write(Result.resJson(flag, flag ? null : "转移失败, 没有足够多的货物"));
    }

    // 进货
    // 前端数据: goodsId, goodsNum
    public void add(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        RepoService repoService = new RepoService();
        RepoInfo info = (RepoInfo)parseData(RepoInfo.class, req, "进货");
        boolean flag = repoService.modifyNum(info.getGoodsId(), info.getGoodsNum(), info.getRepoId());
        resp.getWriter().write(Result.resJson(flag, flag ? null : "进货失败, 没有足够的资金"));
    }

    // 私有方法,仅在类中调用
    // 将仓库项封装成要返回前端的类
    // 目的是加上两项: 货物名称,积压金额
    private List<FRepoInfo> getFRepoInfos(List<RepoInfo> infos){
        List<FRepoInfo> fRepoInfos = new ArrayList<>(); // 要返回的类(含货物名称)
        GoodsService goodsService = new GoodsService();
        Gson gson = new Gson();
        for(RepoInfo info : infos){
            Goods goods = goodsService.getById(info.getGoodsId()); // 先查出id对应的货物信息
            // 把info中的信息都加到fInfo中, 包括name和remain
            FRepoInfo fRepoInfo = gson.fromJson(gson.toJson(info), FRepoInfo.class);
            fRepoInfo.setRemain(goods.getCost() * info.getGoodsNum()); // 积压金额=数量*成本
            fRepoInfo.setGoodsName(goods.getName()); // 设置商品名
            fRepoInfos.add(fRepoInfo); // 最后加到要返回的集合中
        }
        return fRepoInfos;
    }
}