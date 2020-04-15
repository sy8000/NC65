package nc.ui.pub.qcco.writeback.utils.LIMSVO;

import java.util.HashMap;
import java.util.Map;

import nc.vo.pub.SuperVO;

public class LIMSCommonVO extends SuperVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3543051869777977546L;

	private Map<String,Object> atrrMap = new HashMap<>();

	@Override
	public String[] getAttributeNames() {
		return atrrMap.keySet().toArray(new String[0]);
	}

	@Override
	public Object getAttributeValue(String name) {
		if(null != name){
			return atrrMap.get(name.toLowerCase());
		}else{
			return null;
		}
		
	}

	@Override
	public void setAttributeValue(String name, Object value) {
		if(null != name){
			atrrMap.put(name.toLowerCase(), value);
		}else{
			atrrMap.put(name, value);
		}
		
	}
	
	/**
	 * É¾³ýÄ³¸ö×Ö¶Î
	 * @param name
	 */
	public void removeAttributeValue(String name) {
		if(null != name){
			atrrMap.remove(name.toLowerCase());
		}else{
			atrrMap.remove(name);
		}
		
	}
}
