package org.seckill.dao.cache;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.seckill.entity.Seckill;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

import redis.clients.jedis.BinaryJedisCluster;

public class RedisClusterDao {

    private final Log LOG = LogFactory.getLog(this.getClass());

	private BinaryJedisCluster jedisCluster;
	
    public BinaryJedisCluster getJedisCluster() {
		return jedisCluster;
	}

	public void setJedisCluster(BinaryJedisCluster jedisCluster) {
		this.jedisCluster = jedisCluster;
	}

	private RuntimeSchema<Seckill> schema = RuntimeSchema.createFrom(Seckill.class);

//    public RedisClusterDao(String ip, int port) {
//        jedisPool = new JedisPool(ip, port);
//
//    }

    public Seckill getSeckill(long seckillId) {
        //redis逻辑操作
//      Jedis jedis = jedisPool.getResource();
        try {
            String key = "seckill:" + seckillId;
            //并没有实现内部序列化操作
            //get:byte[]->反序列化->Object(Seckill)
            //采用自定义序列化
            byte[] bytes = jedisCluster.get(key.getBytes());
            if (bytes != null) {
                //空对象
                Seckill seckill = schema.newMessage();
                ProtostuffIOUtil.mergeFrom(bytes, seckill, schema);
                //seckill 被反序列化
                return seckill;
            }
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
        return null;
    }

    /**
     * Seckill 对象传递到redis中
     *
     * @param seckill
     * @return
     */
    public String putSeckill(Seckill seckill) {
        //set:Object(Seckill)->序列化->byte[] ->发送给redis
        try {
//            Jedis jedis = jedisPool.getResource();
            try {
                String key = "seckill:" + seckill.getSeckillId();
                byte[] bytes = ProtostuffIOUtil.toByteArray(seckill, schema, LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
                //超时缓存
                int timeOut = 60 * 60;
                String result = jedisCluster.setex(key.getBytes(), timeOut, bytes);
                return result;
            } finally {
            	jedisCluster.close();
            }
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
        return null;
    }

}
