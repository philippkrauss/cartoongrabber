package cartoongrabber.tools.redis;

import cartoongrabber.model.CartoonStrip;
import com.fiftyonred.mock_jedis.MockJedis;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import static cartoongrabber.tools.TestTools.createCartoon;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

public class RedisStoreTest {

    MockJedis jedis = null;
    RedisStore store = null;
    CartoonStrip cartoon1 = null;
    CartoonStrip cartoon2 = null;

    @Before
    public void setUp() {
        jedis = new MockJedis("test");
        store = new RedisStore(jedis);
        cartoon1 = createCartoon(1);
        cartoon2 = createCartoon(2);
    }


    @Test
    public void testOneCartoonCreated() {
        store.store(cartoon1);
        assertTrue(jedis.exists("cartoon:1"));
        assertFalse(jedis.exists("cartoon:2"));
    }

    @Test
    public void testTwoCartoonsCreated() {
        store.store(cartoon1);
        store.store(cartoon2);
        assertTrue(jedis.exists("cartoon:1"));
        assertTrue(jedis.exists("cartoon:2"));
    }

    @Test
    public void testOneCartoonValues() {
        store.store(cartoon1);
        assertEquals(cartoon1.getName(), jedis.hget("cartoon:1", "name"));
        assertEquals(cartoon1.getSourceUrl().toString(), jedis.hget("cartoon:1", "sourceUrl"));
        assertEquals(Long.toString(cartoon1.getDate().toEpochDay()), jedis.hget("cartoon:1", "date"));
        assertEquals(cartoon1.getImageUrl().toString(), jedis.hget("cartoon:1", "imageUrl"));
    }

    @Test
    public void testTwoCartoonsValues() {
        store.store(cartoon1);
        store.store(cartoon2);
        assertCartoonInJedis(jedis, cartoon1, "cartoon:1");
        assertCartoonInJedis(jedis, cartoon2, "cartoon:2");
    }

    private void assertCartoonInJedis(Jedis jedis, CartoonStrip cartoonStrip, String id) {
        assertEquals(cartoonStrip.getName(), jedis.hget(id, "name"));
        assertEquals(cartoonStrip.getSourceUrl().toString(), jedis.hget(id, "sourceUrl"));
        assertEquals(Long.toString(cartoonStrip.getDate().toEpochDay()), jedis.hget(id, "date"));
        assertEquals(cartoonStrip.getImageUrl().toString(), jedis.hget(id, "imageUrl"));
    }

    @Test
    public void testDates() {
        store.store(cartoon1);
        store.store(cartoon2);
        assertEquals(2, jedis.smembers("dates").size());
        assertTrue(jedis.smembers("dates").contains(Long.toString(cartoon1.getDate().toEpochDay())));
        assertTrue(jedis.smembers("dates").contains(Long.toString(cartoon2.getDate().toEpochDay())));
    }

    @Test
    public void testCartoonsFor() {
        store.store(cartoon1);
        store.store(cartoon2);
        assertTrue(jedis.exists("cartoons_for:" + cartoon1.getDate().toEpochDay()));
        assertTrue(jedis.smembers("cartoons_for:" + cartoon1.getDate().toEpochDay()).contains("cartoon:1"));
        assertTrue(jedis.smembers("cartoons_for:" + cartoon2.getDate().toEpochDay()).contains("cartoon:2"));
    }

    @Test
    public void testDoNotStoreDuplicate() {
        store.store(cartoon1);
        store.store(cartoon1);
        assertFalse(jedis.exists("cartoon:2"));
        assertEquals(1, jedis.smembers("cartoons_for:" + cartoon1.getDate().toEpochDay()).size());
    }

    @Test
    public void testDatesCap() {
        CartoonStrip first = createCartoon(0);
        store.store(first);
        for (int i = 1; i < 39; i++) {
            store.store(createCartoon(i));
        }
        CartoonStrip last = createCartoon(40);
        store.store(last);
        assertEquals(30, jedis.smembers("dates").size());
        assertFalse(jedis.sismember("dates", Long.toString(first.getDate().toEpochDay())));
        assertTrue(jedis.sismember("dates", Long.toString(last.getDate().toEpochDay())));
        assertFalse(jedis.exists("cartoons_for:" + first.getDate().toEpochDay()));
        assertFalse(jedis.exists("cartoon:1"));
    }

}
