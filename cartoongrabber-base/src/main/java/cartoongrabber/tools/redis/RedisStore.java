package cartoongrabber.tools.redis;

import cartoongrabber.model.CartoonStrip;
import redis.clients.jedis.Jedis;

public class RedisStore {

    private static final String NEXT_CARTOON_ID = "next_cartoon_id";
    private static final String CARTOON_PREFIX = "cartoon:";
    private static final String NAME = "name";
    private static final String SOURCE_URL = "sourceUrl";
    private static final String DATE = "date";
    private static final String IMAGE_URL = "imageUrl";
    private static final String DATES = "dates";
    private static final String CARTOONS_FOR_PREFIX = "cartoons_for:";

    private final Jedis jedis;

    public RedisStore(Jedis jedis) {
        this.jedis = jedis;
    }

    public void store(CartoonStrip cartoon) {
        Long id = jedis.incr(NEXT_CARTOON_ID);
        String cartoonId = CARTOON_PREFIX + id;
        jedis.hset(cartoonId, NAME, cartoon.getName());
        jedis.hset(cartoonId, SOURCE_URL, cartoon.getSourceUrl().toString());
        jedis.hset(cartoonId, DATE, cartoon.getDate().toString());
        jedis.hset(cartoonId, IMAGE_URL, cartoon.getImageUrl().toString());
        jedis.lpush(DATES, cartoon.getDate().toString());
        jedis.lpush(CARTOONS_FOR_PREFIX + cartoon.getDate(), cartoonId);
    }
}
