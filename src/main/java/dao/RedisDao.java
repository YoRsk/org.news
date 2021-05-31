package dao;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisDao {
    private final Logger logger= LoggerFactory.getLogger(this.getClass());
    private JedisPool jedisPool;
    //ip and port于Xml->constructor-arg
    public RedisDao(String ip,int port) {
        jedisPool=new JedisPool(ip,port);
    }

    //使用第三方的protostuff序列化，需要先指定需要序列化的类接口，如下，但是这个类必须是pojo型。
    private RuntimeSchema<User> schema=RuntimeSchema.createFrom(User.class);

    public User getUser(String redisKey,long userId) {
        //缓存Redis操作。
        try{
            //根据jedisPool获得他们的资源 .getResource();
            Jedis jedis=jedisPool.getResource();
            try{
                //构建key，作用于key-value存储
                String key=redisKey+userId;
                /*
                    Redis并没有实现内部序列化操作。
                    我们需要在获取redis资源的时候要进行反序列化操作
                    get->byte[]->反序列化->Object(Seckill);
                    关于序列化的解决方案：①入门级：在需要传递的entity的对象类实现 Serializable接口；这个Serializable是
                    使用的是JDK的自己的序列化。   ②大师级：既然是在优化这个暴露接口，那么就把他做到最好，使用JDk原生的
                    序列化的速度，效率，占的空间，转化的字节数(最少,在网络中传输的字节数就会最少，效率最高)等都不是最好
                    的。采用自定义序列化；我们使用的是protostuff,那么在Pom.xml中添加 protostuff-core和protostuff-runtime
                    系列化依赖，实现序列化。
                */
                byte[]bytes=jedis.get(key.getBytes());
                if(bytes!=null){
                    //创建一个空对象,在Redis中赋值给这个空对象。
                    User user=schema.newMessage();
                    ProtostuffIOUtil.mergeFrom(bytes,user,schema);
                    //空对象secKill，在调用上面语句之后被赋值。
                    return user;
                }
            }finally {
                jedis.close();
            }
        }catch(Exception e){
            logger.error(e.getMessage(),e);
        }
        return null;
    }


    public String setUser(String redisKey,User user) {
        //首先拿到Seckill，然后转换成字节数组，然后给redis
        try{
            //获得资源
            try (Jedis jedis = jedisPool.getResource()) {
                //封装Key
                String key = redisKey + user.getUsername();
                //系列化
                byte[] bytes = ProtostuffIOUtil.toByteArray(user, schema,
                        LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
                int timeout = 60 * 60;
                return jedis.setex(key.getBytes(), timeout, bytes);
            }
        }catch(Exception e){
            logger.error(e.getMessage(),e);
        }
        return "";
    }

}
