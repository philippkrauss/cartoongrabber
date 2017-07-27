package cartoongrabber.tools.redis;

import cartoongrabber.model.CartoonStrip;
import cartoongrabber.tools.redis.RedisStore;
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
    CartoonStrip cartoon1 = createCartoon("1");
    CartoonStrip cartoon2 = createCartoon("2");

    @Before
    public void setUp() {
        jedis = new MockJedis("test");
        store = new RedisStore(jedis);
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
        assertEquals(cartoon1.getDate().toString(), jedis.hget("cartoon:1", "date"));
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
        assertEquals(cartoonStrip.getDate().toString(), jedis.hget(id, "date"));
        assertEquals(cartoonStrip.getImageUrl().toString(), jedis.hget(id, "imageUrl"));
    }

    @Test
    public void testDates() {
        store.store(cartoon1);
        store.store(cartoon2);
        assertEquals(new Long(2), jedis.llen("dates"));
        assertEquals(cartoon1.getDate().toString(), jedis.lrange("dates", 0, 1).get(0));
        assertEquals(cartoon2.getDate().toString(), jedis.lrange("dates", 1, 2).get(0));
    }

    @Test
    public void testCartoonsFor() {
        store.store(cartoon1);
        store.store(cartoon2);
        assertTrue(jedis.exists("cartoons_for:" + cartoon1.getDate().toString()));
        assertEquals("cartoon:1", jedis.lrange("cartoons_for:" + cartoon1.getDate().toString(), 0, 1).get(0));
        assertEquals("cartoon:2", jedis.lrange("cartoons_for:" + cartoon2.getDate().toString(), 0, 1).get(0));
    }

}
