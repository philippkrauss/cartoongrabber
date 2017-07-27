package cartoongrabber.tools.redis;

import redis.clients.jedis.Jedis;

public interface JedisFactoryService {

    Jedis createJedis();
}
