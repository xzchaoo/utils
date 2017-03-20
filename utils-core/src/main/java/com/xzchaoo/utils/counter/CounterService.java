package com.xzchaoo.utils.counter;

import com.xzchaoo.utils.reflect.MethodUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * Created by Administrator on 2016/11/12.
 */
public class CounterService<KEY, TAG> {
	private final ConcurrentHashMap<KEY, Counter<TAG>> map = new ConcurrentHashMap<KEY, Counter<TAG>>();
	public static final boolean HAS_COMPUTE_IF_ABSENT = MethodUtils.hasPublicMethod(ConcurrentHashMap.class, "computeIfAbsent");

	public Map<KEY, Counter<TAG>> getMapCopy() {
		return new HashMap<KEY, Counter<TAG>>(map);
	}

	public Counter<TAG> getCounter(KEY key) {
		if (HAS_COMPUTE_IF_ABSENT) {
			return map.computeIfAbsent(key, new Function<KEY, Counter<TAG>>() {
				public Counter<TAG> apply(KEY key) {
					return new Counter<TAG>();
				}
			});
		} else {
			//双重锁定
			Counter<TAG> c = map.get(key);
			if (c == null) {
				synchronized (this) {
					c = map.get(key);
					if (c == null) {
						c = new Counter<TAG>();
						map.put(key, c);
					}
				}
			}
			return c;
		}
	}

	public int incrementAndGet(KEY key) {
		return getCounter(key).incrementAndGet();
	}

	public int incrementAndGet(KEY key, TAG tag) {
		return getCounter(key).incrementAndGet(tag);
	}

	public void clear() {
		map.clear();
	}
}
