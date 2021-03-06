package cartoongrabber.web.service;

import cartoongrabber.model.CartoonStrip;
import cartoongrabber.tools.redis.JedisFactoryService;
import cartoongrabber.tools.redis.RedisStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Service
public class RedisPersistenceService implements CartoonPersistenceService {

    private Logger log = LoggerFactory.getLogger(RedisPersistenceService.class);
    
    private final JedisFactoryService jedisFactory;


    @Autowired
    public RedisPersistenceService(JedisFactoryService jedisFactory) {
        this.jedisFactory = jedisFactory;
    }

    @Override
    public Collection<LocalDate> getDates() {
        try(Jedis jedis = jedisFactory.createJedis()) {
            RedisStore store = new RedisStore(jedis);
            return store.readDates();
        }
    }

    @Override
    public List<CartoonStrip> getCartoonsForDate(LocalDate date) {
        try(Jedis jedis = jedisFactory.createJedis()) {
            log.debug("Getting cartoons for date [{}]", date);
            RedisStore store = new RedisStore(jedis);
            return store.readCartoonsForDate(date);
        }
    }
}
