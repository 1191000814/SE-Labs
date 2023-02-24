package mapper;

import pojo.Goods;

import java.util.List;

public interface GoodsMapper{

    Goods getById(int id);

    Goods getByFullName(String fullName);

    List<Goods> getByName(String name);

    int getMaxId();

    List<Goods> getAll();

    int deleteById(int id);

    int updateById(Goods goods);

    int updateProfit(int id, float profit);

    int insert(Goods goods);
}