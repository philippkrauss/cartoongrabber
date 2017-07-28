package cartoongrabber.tools.redis;

import com.fiftyonred.mock_jedis.MockJedis;
import redis.clients.jedis.Jedis;

public class MockJedisFactory implements JedisFactoryService {

    public MockJedis jedis = new MockJedis("test");

    @Override
    public Jedis createJedis() {
        return jedis;
    }
}
