package com.order.machine.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 基于spring集成Jedis template，在applicationContext-redis中配置
 * 在RedisTemplate外又封装了一层，实现了RedisAPIs接口
 * @param <String>
 * @param <Object>
 */

@Component
@SuppressWarnings("unchecked")
public class RedisRepository implements IRedisRepository {

    @Autowired
    private RedisTemplate redisTemplate;

    private BoundValueOperations<String,Object> getBoundValueOps(String key) {
        return redisTemplate.boundValueOps(key);
    }

    private BoundZSetOperations<String,Object> getBoundZSetOps(String key) {
        return redisTemplate.boundZSetOps(key);
    }

    private BoundSetOperations<String,Object> getBoundSetOps(String key) {
        return redisTemplate.boundSetOps(key);
    }

    private BoundListOperations<String,Object> getBoundListOps(String key) {
        return redisTemplate.boundListOps(key);
    }

    private <HK, HV> BoundHashOperations<String, HK, HV> getBoundHashOps(String key) {
        return redisTemplate.boundHashOps(key);
    }

    // Key

    @Override
    public void del(final String key) {
        redisTemplate.delete(key);
    }

    public void del(final Collection<String> keys) {
        redisTemplate.delete(keys);
    }

    public Boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }

    public Boolean expire(final String key,final long timeout,final TimeUnit unit) {
        return redisTemplate.expire(key, timeout, unit);
    }

    public void expireAt(final String key,Date date) {
        redisTemplate.expireAt(key, date);
    }

    public Set<String> keys(final String pattern) {
        return redisTemplate.keys(pattern);
    }

    public String type(final String key) {
        return redisTemplate.type(key).code();
    }

    public Object get(final String key) {
        BoundValueOperations<String,Object> ops  = this.getBoundValueOps(key);
        return ops.get();
    }

    public Object getSet(final String key,final Object value) {
        BoundValueOperations<String,Object> ops  = this.getBoundValueOps(key);
        return ops.getAndSet(value);
    }

    public Long incr(final String key,final long delta) {
        BoundValueOperations<String,Object> ops  = this.getBoundValueOps(key);
        return ops.increment(delta);
    }

    public void set(final String key,final Object value) {
        BoundValueOperations<String,Object> ops  = this.getBoundValueOps(key);
        ops.set(value);
    }

    public void set(final String key,final Object value,final long timeout,final TimeUnit unit) {
        BoundValueOperations<String,Object> ops  = this.getBoundValueOps(key);
        ops.set(value, timeout, unit);
    }

    // Hash
    public void hDel(final String key,final Object... hKeys) {
        BoundHashOperations<String, String, Object> ops  = this.getBoundHashOps(key);
        ops.delete(hKeys);
    }

    public Boolean hExists(final String key,final String hKeys) {
        BoundHashOperations<String, String, Object> ops  = this.getBoundHashOps(key);
        return ops.hasKey(hKeys);
    }

    public Map<String,Object> hGet(final String key) {
        BoundHashOperations<String, String, Object> ops  = this.getBoundHashOps(key);
        return ops.entries();
    }

    @Override
    public Map<String,Object> hGetSort(String key) {
        Map<String, Object> map = new HashMap<>();
        Set<String> keys = hKeys(key);
//        List<String> values = (List<String>) hVals(key);
        List<Object> values = hVals(key);
        int i = 0;
        for(String String : keys){
            map.put(String,values.get(i));
            i++;
        }
        return map;
    }

    public Object hGet(final String key,final String hKey) {
        BoundHashOperations<String, String, Object> ops  = this.getBoundHashOps(key);
        return ops.get(hKey);
    }

    public Set<String> hKeys(final String key) {
        BoundHashOperations<String, String, Object> ops  = this.getBoundHashOps(key);
        return ops.keys();
    }

    public Long hLen(final String key) {
        BoundHashOperations<String, String, Object> ops  = this.getBoundHashOps(key);
        return ops.size();
    }

    public void hSet(final String key,final String hk, final Object hv) {
        BoundHashOperations<String, String, Object> ops  = this.getBoundHashOps(key);
        ops.put(hk, hv);
    }

    public void hSet(final String key,final Map<String,Object> map) {
        BoundHashOperations<String, String, Object> ops  = this.getBoundHashOps(key);
        ops.putAll(map);
    }

    public List<Object> hVals(final String key) {
        BoundHashOperations<String, String, Object> ops  = this.getBoundHashOps(key);
        return ops.values();
    }

    // List

    public Object lIndex(final String key,final long index) {
        BoundListOperations<String,Object> ops  = this.getBoundListOps(key);
        return ops.index(index);
    }

    public void lInsert(final String key,final long index,Object value) {
        BoundListOperations<String,Object> ops  = this.getBoundListOps(key);
        ops.set(index, value);
    }

    public Long lLen(final String key) {
        BoundListOperations<String,Object> ops  = this.getBoundListOps(key);
        return ops.size();
    }

    public Object lPop(final String key) {
        BoundListOperations<String,Object> ops  = this.getBoundListOps(key);
        return ops.leftPop();
    }

    public Object lPop(final String key,long timeout,TimeUnit unit) {
        BoundListOperations<String,Object> ops  = this.getBoundListOps(key);
        return ops.leftPop(timeout, unit);
    }

    public Long lPush(final String key,final Object value) {
        BoundListOperations<String,Object> ops  = this.getBoundListOps(key);
        return ops.leftPush(value);
    }

    public List<Object> lRange(final String key,final long start,final long end) {
        BoundListOperations<String,Object> ops  = this.getBoundListOps(key);
        return ops.range(start, end);
    }

    public Long lRem(final String key,final long index,final Object value) {
        BoundListOperations<String,Object> ops  = this.getBoundListOps(key);
        return ops.remove(index, value);
    }

    public void lSet(final String key,final long index,final Object value) {
        BoundListOperations<String,Object> ops  = this.getBoundListOps(key);
        ops.set(index, value);
    }

    public void ltrim(final String key,final long start,final long end) {
        BoundListOperations<String,Object> ops  = this.getBoundListOps(key);
        ops.trim(start, end);
    }

    public Long rPush(final String key,final Object value) {
        BoundListOperations<String,Object> ops  = this.getBoundListOps(key);
        return ops.rightPush(value);
    }

    public Object rPop(final String key) {
        BoundListOperations<String,Object> ops  = this.getBoundListOps(key);
        return ops.rightPop();
    }

    // Set

    public Long sAdd(final String key,final Object value) {
        BoundSetOperations<String,Object> ops  = this.getBoundSetOps(key);
        return ops.add(value);
    }

    public Set<Object> sDiff(final String key, final String otherkey) {
        BoundSetOperations<String,Object> ops  = this.getBoundSetOps(otherkey);
        return ops.diff(key);
    }

    public Set<Object> sMembers(final String key) {
        BoundSetOperations<String,Object> ops  = this.getBoundSetOps(key);
        return ops.members();
    }

    public Boolean sIsMember(final String key,final Object value ){
        BoundSetOperations<String,Object> ops  = this.getBoundSetOps(key);
        return ops.isMember(value);
    }

    public Object sPop(final String key) {
        BoundSetOperations<String,Object> ops  = this.getBoundSetOps(key);
        return ops.pop();
    }

    public Long sRem(final String key, final Object value ) {
        BoundSetOperations<String,Object> ops  = this.getBoundSetOps(key);
        return ops.remove(value);
    }

    public Long sCard(String key) {
        BoundSetOperations<String,Object> ops  = this.getBoundSetOps(key);
        return ops.size();
    }

    // SortedSet

    public void zAdd(final String key,final Object value,final double score) {
        BoundZSetOperations<String,Object> ops  = this.getBoundZSetOps(key);
        ops.add(value, score);
    }

    public Set<Object> zRange(final String key,final long start,final long end) {
        BoundZSetOperations<String,Object> ops  = this.getBoundZSetOps(key);
        return ops.range(start, end);
    }

    public Long zRem(final String key,final Object... values) {
        BoundZSetOperations<String,Object> ops  = this.getBoundZSetOps(key);
        return ops.remove(values);
    }

    public Long zCard(String key) {
        BoundZSetOperations<String,Object> ops  = this.getBoundZSetOps(key);
        return ops.zCard();
    }

    public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }


    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}
