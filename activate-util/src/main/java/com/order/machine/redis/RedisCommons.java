package com.order.machine.redis;

import com.wd.componetutil.SpringUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * @author miou
 * @date 2018-08-20
 */
@SuppressWarnings("unchecked")
public class RedisCommons {

    private static IRedisRepository redisRepository;

    static {
        redisRepository = SpringUtil.getBean(IRedisRepository.class);
    }

    @Autowired
    private static RedisUtil redisUtil;

    public static void delKey(String key){
        redisUtil.del(key);
    }

    public static boolean isExistsKey(String key) {
        return redisRepository.exists(key);
    }

    public static void addCheckId(String key, String id) {
        redisRepository.sAdd(key, id);
    }

    public static void addBatchCheckId(String key, ArrayList<String> list) {
        for (String id : list) {
            addCheckId(key, id);
        }
    }

    public static void removeCheckId(String key, String value) {
        redisRepository.sRem(key, value);
    }

    public static void removeBatchCheckId(String key, ArrayList<String> list) {
        for (String id : list) {
            removeCheckId(key, id);
        }
    }

    public static boolean isExistsCheckId(String key, String id) {
        boolean bool = false;
        if (redisRepository.exists(key)) {
            bool = redisRepository.sIsMember(key, id);
        }
        return bool;
    }

    public static void setHashIdValue(String key, String field, String value) {
        redisRepository.hSet(key, field, value);
    }

    public static String getHashValue(String key, String field) {
        if (redisRepository.hExists(key, field))
            return (String) redisRepository.hGet(key, field);
        else
            return null;
    }

    public static boolean existsHashField(String key, String field) {
        if (redisRepository.exists(key))
            return redisRepository.hExists(key, field);
        return false;
    }

    public static HashMap getHashValue(String key) {
        if (redisRepository.exists(key)) {
            return (HashMap) redisRepository.hGet(key);
        } else {
            return null;
        }
    }

    public static HashMap getHashAll(String key) {
        HashMap<String, String> hashMap = (HashMap) redisRepository.hGetSort(key);
        return hashMap;
    }

    public static void setStringValue(String key, String value) {
        redisRepository.set(key, value);
    }

    public static String getStringValue(String key) {
        return (String) redisRepository.get(key);
    }

    public static void addIdSet(String key, String value) {
        redisRepository.sAdd(key, value);
    }

    public static void batchAddIdSet(String key, Set<String> set) {
        for (Iterator it = set.iterator(); it.hasNext(); ) {
            redisRepository.sAdd(key, it.next());
        }
    }

    public static Set<Object> getSetValue(String key) {
        if (redisRepository.exists(key))
            return redisRepository.sMembers(key);
        else
            return null;
    }

    public static HashSet<String> getKeys(String key) {
        HashSet<String> result = (HashSet<String>) redisRepository.keys(key);
        return result;
    }

    public static boolean isMemberByValue(String key, String value) {
        return redisRepository.sIsMember(key, value);
    }

    /**
     * hash值根据value获取key
     *
     * @param key   redis key
     * @param value 待查值
     * @return
     */
    public static String GetKeyByValue(String key, String value) {
        String result = "";
        Map<String, Object> map = redisRepository.hGet(key);
        if (map != null) {
            Iterator iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                if (entry.getValue().equals(value))
                    result = String.valueOf(entry.getKey());
            }
        }
        return result;
    }

}
