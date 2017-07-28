package cartoongrabber.backend;

import cartoongrabber.model.CartoonStrip;
import cartoongrabber.tools.redis.MockJedisFactory;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static cartoongrabber.tools.TestTools.createCartoon;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

public class RedisCollectionTest {

    MockJedisFactory mockJedisFactory;

    RedisCollection redisCollection;

    private final List<CartoonStrip> oneCartoon = Collections.singletonList(createCartoon());
    private final List<CartoonStrip> twoCartoons = Arrays.asList(createCartoon(1), createCartoon(2));

    @Before
    public void setUp() {
        mockJedisFactory = new MockJedisFactory();
        redisCollection = new RedisCollection(mockJedisFactory);
    }

    @Test
    public void testOneCartoonCreated() {
        redisCollection.collect(oneCartoon);
        assertTrue(mockJedisFactory.jedis.exists("cartoon:1"));
        assertFalse(mockJedisFactory.jedis.exists("cartoon:2"));
    }

    @Test
    public void testTwoCartoonsCreated() {
        redisCollection.collect(twoCartoons);
        assertTrue(mockJedisFactory.jedis.exists("cartoon:1"));
        assertTrue(mockJedisFactory.jedis.exists("cartoon:2"));
    }
}
