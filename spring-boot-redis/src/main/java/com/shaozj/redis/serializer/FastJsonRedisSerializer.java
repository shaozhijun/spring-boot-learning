package com.shaozj.redis.serializer;

import java.nio.charset.Charset;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * 
 * RedisTemplate默认使用的是JdkSerializationRedisSerializer，
 * StringRedisTemplate默认使用的是StringRedisSerializer。
 * 
 * Spring Data JPA为我们提供了下面的Serializer：GenericToStringSerializer、Jackson2JsonRedisSerializer、JacksonJsonRedisSerializer、JdkSerializationRedisSerializer、OxmSerializer、StringRedisSerializer。
 * 
 * JdkSerializationRedisSerializer：使用JDK提供的序列化功能。有点是反序列化时，不需要提供类信息，
 * 但是缺点是需要实现Serializable接口，还有序列化后的结果非常庞大，是Json格式的5倍左右，这样会redis服务器的大量内存。
 * 
 * Jackson2JsonRedisSerializer:使用JackJson库将对象序列化为JSON字符串。优点是速度快，序列化后的字符短小精悍，不需要实现Serializable接口。
 * 但是缺点也非常知名，那就是此类的构造函数只有一个类型参数，必须提供要序列化的类型信息。
 * 
 * 问题描述：
 * 	1、RedisTemplate的key指定成StringRedisSerializer序列化会报类型转换错误，如XXX类不能转换成String。
	2、使用Jackson2JsonRedisSerializer序列化的时候,如果实体类上没有set方法反序列化会报错
 * 
 * 
 * 放弃用jackjson来做value的序列化，使用FastJson来做。重写一些序列化器，并实现RedisSerializer接口
 * @author szj
 *
 * @param <T>
 */
public class FastJsonRedisSerializer<T> implements RedisSerializer<T> {

	public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

	private Class<T> clazz;

	public FastJsonRedisSerializer(Class<T> clazz) {
		super();
		this.clazz = clazz;
	}

	@Override
	public byte[] serialize(T t) throws SerializationException {
		if (t == null)
			return new byte[0];
		return JSON.toJSONString(t, SerializerFeature.WriteClassName).getBytes(DEFAULT_CHARSET);
	}

	@Override
	public T deserialize(byte[] bytes) throws SerializationException {
		if (null == bytes || bytes.length <= 0) {
            return null;
        }
        String str = new String(bytes, DEFAULT_CHARSET);
        return (T) JSON.parseObject(str, clazz);
	}

}
