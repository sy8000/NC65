package nc.bs.pubapp.utils;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * FIFO+LRU ˫���л���
 * 
 * @author tank 2019��7��10��21:19:55
 * 
 */
public class TwoQueuesCacheMap<K, V> {

	private int LRU_CACHE_SIZE = 1000;
	private int FIFO_CACHE_SIZE = 500;

	public TwoQueuesCacheMap(){
		
	}
	/**
	 * @param mainCacheSize �������С
	 * @param backUpCacheSize �������С
	 * һ�������,��������������� 0.75
	 */
	public TwoQueuesCacheMap(int mainCacheSize,int backUpCacheSize){
		this.LRU_CACHE_SIZE = mainCacheSize;
		this.FIFO_CACHE_SIZE = backUpCacheSize;
	}
	/**
	 * FIFO �������
	 */
	private Map<K, V> FIFO_MAP = new LinkedHashMap<K, V>() {
		private static final long serialVersionUID = -6247650998207974879L;

		@Override
		protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
			return size() > FIFO_CACHE_SIZE;
		}
	};

	/**
	 * LRU �������
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
			// ���ݶ��η���,���뵽LRU��
			value = FIFO_MAP.get(key);
			LRU_MAP.put(key, value);
		}
		return value;
	}

	public void put(K key, V value) {
		if (key == null) {
			return;
		}
		// �²�������ݲ��뵽FIFO
		if (!FIFO_MAP.containsKey(key)) {
			FIFO_MAP.put(key, value);
		} else {
			// ��β���������Ƶ�LRU��
			LRU_MAP.put(key, value);
		}
	}

	public boolean containsKey(K key) {
		if (key != null && (FIFO_MAP.containsKey(key) || LRU_MAP.containsKey(key))) {
			//�����洩͸
			/*if(FIFO_MAP.get(key)==null || LRU_MAP.get(key)==null){
				return false;
			}*/
			return true;
		}
		return false;
	}

}
