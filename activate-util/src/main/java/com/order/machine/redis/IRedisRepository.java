package com.order.machine.redis;

import java.util.*;
import java.util.concurrent.TimeUnit;

public interface IRedisRepository {

    // Key
    public void del(final String key);
    public void del(final Collection<String> keys);
    public Boolean exists(final String key);
    public Boolean expire(final String key, final long timeout, final TimeUnit unit) ;
    public void expireAt(final String key, Date date);
    public Set<String> keys(final String pattern);
    public String type(final String key);
    public Object get(final String key) ;
    public Object getSet(final String key, final Object value) ;
    public Long incr(final String key, final long delta) ;
    public void set(final String key, final Object value) ;
    public void set(final String key, final Object value, final long timeout, final TimeUnit unit);

    // Hash
    public void hDel(final String key, final Object... hKeys) ;
    public Boolean hExists(final String key, final String hKeys) ;
    public Map<String,Object> hGet(final String key) ;
    public Map<String,Object> hGetSort(final String key);
    public Object hGet(final String key, final String hKey) ;
    public Set<String> hKeys(final String key) ;
    public Long hLen(final String key) ;
    public void hSet(final String key, final String hk, final Object hv);
    public void hSet(final String key, final Map<String, Object> map);
    public List<Object> hVals(final String key) ;

    // List
    public Object lIndex(final String key, final long index) ;
    public void lInsert(final String key, final long index, Object value) ;
    public Long lLen(final String key) ;
    public Object lPop(final String key) ;
    public Object lPop(final String key, long timeout, TimeUnit unit) ;
    public Long lPush(final String key, final Object value) ;
    public List<Object> lRange(final String key, final long start, final long end) ;
    public Long lRem(final String key, final long index, final Object value) ;
    public void lSet(final String key, final long index, final Object value) ;
    public void ltrim(final String key, final long start, final long end) ;
    public Long rPush(final String key, final Object value) ;
    public Object rPop(final String key) ;

    // Set
    public Long sAdd(final String key, final Object value) ;
    public Set<Object> sDiff(final String key, final String otherkey) ;
    public Set<Object> sMembers(final String key) ;
    public Boolean sIsMember(final String key, final Object value);
    public Object sPop(final String key) ;
    public Long sRem(final String key, final Object value) ;
    public Long sCard(final String key) ;

    // SortedSet
    public void zAdd(final String key, final Object value, final double score) ;
    public Set<Object> zRange(final String key, final long start, final long end) ;
    public Long zRem(final String key, final Object... values) ;
    public Long zCard(final String key) ;

}
