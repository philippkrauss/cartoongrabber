package cartoongrabber.web.service;

import cartoongrabber.tools.redis.JedisFactoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class PooledJedisFactory implements JedisFactoryService {

    Logger log = LoggerFactory.getLogger(PooledJedisFactory.class);

    private final JedisPool jedisPool;

    @Autowired
    public PooledJedisFactory(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    @Override
    public Jedis createJedis() {
        log.debug("retrieving jedis connection from pool");
        return jedisPool.getResource();
    }
}
