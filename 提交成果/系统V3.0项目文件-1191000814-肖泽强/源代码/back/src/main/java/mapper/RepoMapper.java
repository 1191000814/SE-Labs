package mapper;

import org.apache.ibatis.annotations.Param;
import pojo.RepoInfo;

import java.util.List;

/**
 参数里面的
 repoId 是单个仓库的序号(1,2,3)
 repoIds 是三个仓库的序号(三位二进制数的值)
 */
public interface RepoMapper{

    RepoInfo getByGoodsId(int id, int repoId);

    List<RepoInfo> getByName(@Param("name") String name, @Param("repoId") int repoId);

    List<RepoInfo> getAll(int repoId);

    int deleteById(int id, int repoId);

    int updateById(RepoInfo info);

    int modifyNumById(int goodsId, int num, int repoId);

    int insert(RepoInfo info);
}