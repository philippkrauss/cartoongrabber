package cartoongrabber.web.service;

import cartoongrabber.tools.redis.MockJedisFactory;
import cartoongrabber.tools.redis.RedisStore;
import org.junit.Before;
import org.junit.Test;

import static cartoongrabber.tools.TestTools.createCartoon;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertTrue;

public class RedisPersistenceServiceTest {

    MockJedisFactory factory;
    RedisPersistenceService service = null;

    @Before
    public void setUp() {
        factory = new MockJedisFactory();
        service = new RedisPersistenceService(factory);
    }

    @Test
    public void testGetDatesInitial() {
        assertNotNull(service.getDates());
        assertTrue(service.getDates().isEmpty());
    }

    @Test
    public void testGetDates() {
        RedisStore store = new RedisStore(factory.jedis);
        store.store(createCartoon(0));
        assertFalse(service.getDates().isEmpty());
    }
}
