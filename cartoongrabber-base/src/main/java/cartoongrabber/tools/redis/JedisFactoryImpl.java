package cartoongrabber.tools.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import redis.clients.jedis.Jedis;

import java.net.URI;

public class JedisFactoryImpl implements JedisFactoryService {

    private final Logger log = LoggerFactory.getLogger(JedisFactoryImpl.class);

    @Value("${redis}")
    private String redisUrl;

    @Override
    public Jedis createJedis() {
        try {
            URI redisURI = new URI(redisUrl);
            log.info("redis URI: [{}]", redisURI);
            Jedis jedis = new Jedis(redisURI);
            String ping = jedis.ping();
            log.debug("pinged jedis. Result: [{}]", ping);
            return jedis;
        } catch (Exception e) {
            throw new RuntimeException("could not connect to redis", e);
        }
    }
}
