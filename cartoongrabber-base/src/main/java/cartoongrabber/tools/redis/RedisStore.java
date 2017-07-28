package cartoongrabber.tools.redis;

import cartoongrabber.model.CartoonStrip;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.net.URL;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class RedisStore {

    private final Logger log = LoggerFactory.getLogger(RedisStore.class);

    private static final String NEXT_CARTOON_ID = "next_cartoon_id";
    private static final String CARTOON_PREFIX = "cartoon:";
    private static final String NAME = "name";
    public static final String SOURCE_URL = "sourceUrl";
    private static final String DATE = "date";
    private static final String IMAGE_URL = "imageUrl";
    public static final String DATES = "dates";
    private static final String CARTOONS_FOR_PREFIX = "cartoons_for:";

    private static final int MAX_MEMBERS = 30;

    private final Jedis jedis;

    public RedisStore(Jedis jedis) {
        this.jedis = jedis;
    }

    public void store(CartoonStrip cartoon) {
        log.debug("storing cartoon [{}] for date [{}] in redis", cartoon.getName(), cartoon.getDate());
        String cartoonId = findCartoonId(cartoon);
        jedis.hset(cartoonId, NAME, cartoon.getName());
        jedis.hset(cartoonId, SOURCE_URL, cartoon.getSourceUrl().toString());
        jedis.hset(cartoonId, DATE, Long.toString(cartoon.getDate().toEpochDay()));
        jedis.hset(cartoonId, IMAGE_URL, cartoon.getImageUrl().toString());
        jedis.sadd(DATES, Long.toString(cartoon.getDate().toEpochDay()));
        jedis.sadd(CARTOONS_FOR_PREFIX + cartoon.getDate().toEpochDay(), cartoonId);
        garbageCollect();
    }

    private void garbageCollect() {
        if (jedis.scard(DATES) > MAX_MEMBERS) {
            SortedSet<String> sortedDates = new TreeSet<>(jedis.smembers(DATES));
            removeAllForDate(sortedDates.first());
        }
    }

    private void removeAllForDate(String date) {
        jedis.srem(DATES, date);
        Set<String> idsForDate = jedis.smembers(CARTOONS_FOR_PREFIX + date);
        for (String id : idsForDate) {
            jedis.del(id);
        }

        jedis.del(CARTOONS_FOR_PREFIX + date);
    }

    private String findCartoonId(CartoonStrip cartoon) {
        String date = Long.toString(cartoon.getDate().toEpochDay());
        if (!jedis.sismember(DATES, date)) {
            return createId();
        }
        for (String id : jedis.smembers(CARTOONS_FOR_PREFIX + date)) {
            String name = jedis.hget(id, NAME);
            if (name != null && name.equals(cartoon.getName())) {
                log.debug("cartoon [{}] already exists for date [{}], updating.", cartoon.getName(), cartoon.getDate());
                return id;
            }
        }
        return createId();
    }

    private String createId() {
        return CARTOON_PREFIX + jedis.incr(NEXT_CARTOON_ID);
    }

    public Collection<LocalDate> readDates() {
        log.debug("reading dates from redis");
        Set<String> dates = jedis.smembers(DATES);
        log.debug("found dates [{}]", dates);
        return dates.stream()
                .map(date -> LocalDate.ofEpochDay(Long.valueOf(date)))
                .collect(Collectors.toList());
    }

    public List<CartoonStrip> readCartoonsForDate(LocalDate date) {
        String key = CARTOONS_FOR_PREFIX + date.toEpochDay();
        if (!jedis.exists(key)) {
            return Collections.emptyList();
        }
        List<CartoonStrip> ret = new ArrayList<>();
        for (String id : jedis.smembers(key)) {
            try {
                ret.add(loadCartoon(id));
            } catch (Exception e) {
                log.warn("cannot load cartoon ID [{}]. Skipping!", id, e);
            }
        }
        return ret;
    }

    private CartoonStrip loadCartoon(String id) throws Exception {
        Map<String, String> cartoonMap = jedis.hgetAll(id);
        String name = cartoonMap.get(NAME);
        URL sourceUrl = new URL(cartoonMap.get(SOURCE_URL));
        URL imageUrl = new URL(cartoonMap.get(IMAGE_URL));
        LocalDate date = LocalDate.ofEpochDay(Long.valueOf(cartoonMap.get(DATE)));
        return new CartoonStrip(name, sourceUrl, imageUrl, date);
    }

}
