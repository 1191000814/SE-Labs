package service;

import mapper.RepoMapper;
import org.apache.ibatis.session.SqlSession;
import pojo.RepoInfo;
import utils.MyBatisUtils;

import java.util.List;

public class RepoService{

    /**
     * 改变商品数量
     * 不足则减少失败, 没有则新增一项
     * @param goodsId 商品id
     * @param changingNum 改变的数量 正为加, 负为减
     * @param repoId 仓库名称
     * @return 是否改成成功
     */
    public boolean modifyNum(int goodsId, int changingNum, int repoId){
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        RepoMapper repoMapper = sqlSession.getMapper(RepoMapper.class);
        RepoInfo infos = repoMapper.getByGoodsId(goodsId, repoId);
        boolean flag;
        if(infos == null){
            // 没有这一项, 不能减少
            if(changingNum < 0){
                System.out.println("没有这种商品");
                sqlSession.close();
                return false;
            }
            // 仓库没有这种商品,新增一项
            RepoInfo info = new RepoInfo(goodsId, changingNum, repoId);
            flag = repoMapper.insert(info) > 0;
            sqlSession.close();
            return flag;
        }
        // 仓库有这项货物, 改变数量即可
        if(infos.getGoodsNum() + changingNum == 0){
            // 刚好减少为0,就把这项删掉
            flag = repoMapper.deleteById(goodsId, repoId) > 0;
            sqlSession.close();
            return flag;
        }
        flag = repoMapper.modifyNumById(goodsId, changingNum, repoId) > 0;
        sqlSession.close();
        return flag;
    }

    public boolean dispatch(int oldRepoId, int newRepoId, int goodsId, int goodsNum){
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        RepoMapper repoMapper = sqlSession.getMapper(RepoMapper.class);
        RepoInfo info = repoMapper.getByGoodsId(goodsId, oldRepoId); // 查看原仓库有无这种商品
        if(info == null){
            System.out.println("仓库" + oldRepoId + "没有这种商品");
            sqlSession.close();
            return false;
        }
        if(info.getGoodsNum() - goodsNum < 0){
            System.out.println("仓库" + oldRepoId + "没有足够的数量");
            sqlSession.close();
            return false;
        }
        boolean flag = modifyNum(goodsId, - goodsNum, oldRepoId) && modifyNum(goodsId, goodsNum, newRepoId);
        sqlSession.close();
        return flag;
    }

    public List<RepoInfo> search(String pattern, int repoIds){
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        RepoMapper repoMapper = sqlSession.getMapper(RepoMapper.class);
        List<RepoInfo> repoInfos = repoMapper.getByName("%" + pattern + "%", repoIds);
        sqlSession.close();
        return repoInfos;
    }

    public List<RepoInfo> getAll(int repoId){
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        RepoMapper repoMapper = sqlSession.getMapper(RepoMapper.class);
        List<RepoInfo> repoInfos = repoMapper.getAll(repoId);
        sqlSession.close();
        return repoInfos;
    }

    public boolean insert(RepoInfo info){
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        RepoMapper repoMapper = sqlSession.getMapper(RepoMapper.class);
        boolean flag = repoMapper.insert(info) > 0;
        sqlSession.close();
        return flag;
    }
}