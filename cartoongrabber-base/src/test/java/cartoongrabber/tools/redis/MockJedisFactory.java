package cartoongrabber.tools.redis;

import com.fiftyonred.mock_jedis.MockJedis;
import redis.clients.jedis.Jedis;

public class MockJedisFactory implements JedisFactoryService {

    public MockJedis jedis = null;

    @Override
    public Jedis createJedis() {
        jedis = new MockJedis("test");
        return jedis;
    }
}
