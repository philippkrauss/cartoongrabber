package cartoongrabber.backend;

import cartoongrabber.model.CartoonStrip;
import cartoongrabber.tools.redis.JedisFactoryService;
import cartoongrabber.tools.redis.RedisStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class RedisCollection implements CartoonCollectionService {

    private final Logger log = LoggerFactory.getLogger(RedisCollection.class);

    private final RedisStore redisStore;

    public RedisCollection(JedisFactoryService jedisFactoryService) {
        try {
            redisStore = new RedisStore(jedisFactoryService.createJedis());
        } catch (Exception e) {
            throw new RuntimeException("Could not connect to redis", e);
        }
    }

    @Override
    public void collect(List<CartoonStrip> cartoons) {
        log.debug("storing [{}] cartoons in redis", cartoons.size());
        for (CartoonStrip cartoon : cartoons) {
            redisStore.store(cartoon);
        }
    }
}
