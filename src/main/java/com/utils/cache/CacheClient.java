package com.utils.cache;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import com.utils.logging.MyLogger;

import net.spy.memcached.MemcachedClient;


public class CacheClient {

	private static CacheClient instance = null;
	private static MemcachedClient[] client = null;
	private static MyLogger logger = new MyLogger(CacheClient.class);
	private static String memCachedServerAddress;
	
	private CacheClient() {
		try {
			memCachedServerAddress = "url//";
			client = new MemcachedClient[5];
			for (int i = 0; i <= 4; i++) {
				MemcachedClient c = new MemcachedClient(
						 new InetSocketAddress(memCachedServerAddress, Integer.parseInt("port")));
				client[i] = c;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public synchronized void storeKeyasValue (Long accountId, String key, String value, int time){
		CacheClient client = get();
		String uniqueKey = accountId + key;
		logger.logInfo("Storing Key as value: class " + uniqueKey +" key: "+key);
		List<String> keys = (List<String>) client.get(uniqueKey);
		if (keys == null){
			keys = new ArrayList<String>();
			keys.add(value);
		} else {
		if (!keys.contains(value))
			keys.add(value);
		}
		client.set(uniqueKey, time, keys);
		
	}
	
	public synchronized void expireKeyasValue (Long accountId,String key){
		CacheClient client = get();
		String uniqueKey = accountId + key;
		logger.logInfo("Expiring key : " + uniqueKey);
		List<String> keys = (List<String>) client.get(uniqueKey);
		if (keys == null) return;
		for (String l : keys) {
			logger.logInfo("delete memcached key: "+l);
			client.delete(l);
		}
	}
	
	public static synchronized CacheClient get() {
		logger.logInfo("Get Cache Client Instance: " + instance);
		if (instance == null) {
			logger.logInfo("Creating a new instance");
			instance = new CacheClient();
		}
		return instance;
	}

	public void set(String key, int ttl, final Object o) {
		logger.logInfo("Set value : " +o +" for key: "+key);
		getCache().set(key, ttl, o);
	}

	public Object getFuture (String key){
		Future<Object> f=getCache().asyncGet(key);
		try {
		   return f.get(10, TimeUnit.SECONDS);
		} catch(TimeoutException e) {
		   f.cancel(false);
		   e.printStackTrace();
		   return null;
		} catch (InterruptedException e) {
			return null;
		} catch (ExecutionException e) {
			return null;
		}
	}
	public synchronized Object get(String key) {
		Object o = getCache().get(key);
		if (o == null) {
			logger.logInfo("Cache MISS for KEY: " + key);
		} else {
			logger.logInfo("Cache HIT for KEY: " + key);
		}
		return o;
	}
	
	public Object get(String key, String url) {
		Object o = getCache().get(key);
		if (o == null) {
			logger.logInfo("Cache MISS for KEY: " + key);
		} else {
			logger.logInfo("Cache HIT for KEY: " + key);
		}
		return o;
	}
	
	public Object delete(String key) {
		return getCache().delete(key);
	}

	private MemcachedClient getCache() {
		MemcachedClient c = null;
		try {
			int i = (int) (Math.random() * 5);
			c = client[i];
		} catch (Exception e) {

		}
		return c;
	}
	
}
