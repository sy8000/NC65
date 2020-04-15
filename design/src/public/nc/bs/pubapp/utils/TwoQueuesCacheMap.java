package nc.bs.pubapp.utils;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * FIFO+LRU 双队列缓存
 * 
 * @author tank 2019年7月10日21:19:55
 * 
 */
public class TwoQueuesCacheMap<K, V> {

	private int LRU_CACHE_SIZE = 1000;
	private int FIFO_CACHE_SIZE = 500;

	public TwoQueuesCacheMap(){
		
	}
	/**
	 * @param mainCacheSize 主缓存大小
	 * @param backUpCacheSize 副缓存大小
	 * 一般情况下,副缓存是主缓存的 0.75
	 */
	public TwoQueuesCacheMap(int mainCacheSize,int backUpCacheSize){
		this.LRU_CACHE_SIZE = mainCacheSize;
		this.FIFO_CACHE_SIZE = backUpCacheSize;
	}
	/**
	 * FIFO 缓存队列
	 */
	private Map<K, V> FIFO_MAP = new LinkedHashMap<K, V>() {
		private static final long serialVersionUID = -6247650998207974879L;

		@Override
		protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
			return size() > FIFO_CACHE_SIZE;
		}
	};

	/**
	 * LRU 缓存队列
	 */
	private Map<K, V> LRU_MAP = new LinkedHashMap<K, V>((int) Math.ceil(LRU_CACHE_SIZE / 0.75f) + 1, 0.75f, true) {
		private static final long serialVersionUID = 2480871906567805838L;

		@Override
		protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
			return size() > LRU_CACHE_SIZE;
		}
	};

	public V get(K key) {
		if (key == null) {
			return null;
		}
		V value = null;
		if (LRU_MAP.containsKey(key)) {
			value = LRU_MAP.get(key);
		} else if (FIFO_MAP.containsKey(key)) {
			// 数据二次访问,插入到LRU中
			value = FIFO_MAP.get(key);
			LRU_MAP.put(key, value);
		}
		return value;
	}

	public void put(K key, V value) {
		if (key == null) {
			return;
		}
		// 新插入的数据插入到FIFO
		if (!FIFO_MAP.containsKey(key)) {
			FIFO_MAP.put(key, value);
		} else {
			// 多次插入的数据移到LRU中
			LRU_MAP.put(key, value);
		}
	}

	public boolean containsKey(K key) {
		if (key != null && (FIFO_MAP.containsKey(key) || LRU_MAP.containsKey(key))) {
			//允许缓存穿透
			/*if(FIFO_MAP.get(key)==null || LRU_MAP.get(key)==null){
				return false;
			}*/
			return true;
		}
		return false;
	}

}
