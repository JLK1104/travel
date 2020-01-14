package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {
    private CategoryDao categoryDao = new CategoryDaoImpl();

    @Override
    public List<Category> findAll() {
        Jedis jedis = JedisUtil.getJedis();
        Set<Tuple> category = jedis.zrangeWithScores("category", 0, -1);
        if (category == null || category.size() == 0) {
            //查询数据库
            //缓存中没有数据
            List<Category> list = categoryDao.findAll();
            for (Category category1 : list) {
                jedis.zadd("category", category1.getCid(), category1.getCname());
            }
            return list;
        } else {
            ArrayList<Category> categories = new ArrayList<>();
            for (Tuple tuple : category) {
                Category vo = new Category((int)tuple.getScore(),tuple.getElement());
                categories.add(vo);
            }
            return categories;
        }
    }
}
