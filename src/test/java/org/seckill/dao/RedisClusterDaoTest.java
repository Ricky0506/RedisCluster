package org.seckill.dao;

import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dao.cache.RedisClusterDao;
import org.seckill.entity.Seckill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import redis.clients.jedis.JedisCluster;

/**
 * 配置Spring和Junit整合,junit启动时加载springIOC容器
 * spring-test,junit
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-dao.xml")
public class RedisClusterDaoTest {

	@Autowired
	private JedisCluster jedisCluster;

	@Autowired
	private RedisClusterDao redisClusterDao;

	@Autowired
	private SeckillDao seckillDao;
	
	@Test
	public void publish() {
		
		redisClusterDao.getSeckill(1001L);
		Seckill seckill = seckillDao.queryById(1001L);
		redisClusterDao.putSeckill(seckill);
		Set<String> keySet = jedisCluster.getClusterNodes().keySet();
		for (String string : keySet) {
			System.out.println(string);
		}
		
	}

	public JedisCluster getJedisCluster() {
		return jedisCluster;
	}

	public void setJedisCluster(JedisCluster jedisCluster) {
		this.jedisCluster = jedisCluster;
	}
}

