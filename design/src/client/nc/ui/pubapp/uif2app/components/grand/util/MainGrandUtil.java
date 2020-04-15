package nc.ui.pubapp.uif2app.components.grand.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import nc.bs.logging.Logger;
import nc.md.model.IBusinessEntity;
import nc.ui.pub.bill.BillModel;
import nc.ui.uif2.UIState;
import nc.ui.uif2.editor.BillForm;
import nc.ui.uif2.editor.BillListView;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.SuperVO;
import nc.vo.pub.bill.BillTabVO;
import nc.vo.qcco.task.TaskBVO;

/**
 * 主子孙常用方法的工具类
 * @author hulianga
 *
 */
public class MainGrandUtil {
	/**
     * 去掉删除VO
     */
    public static ArrayList<Object> processList(ArrayList<Object> grandOrigList, ArrayList<Object> delList) {
        Set<String> delSet = new HashSet<String>();
        for (Object obj : delList) {
            SuperVO vo = (SuperVO) obj;
            if(null!=vo.getPrimaryKey())//新增状态null过滤掉
            	delSet.add(vo.getPrimaryKey());
        }
        ArrayList<Object> newList = new ArrayList<Object>();
        if (delSet.size() > 0) {
            for (Object item : grandOrigList) {
                SuperVO vo = (SuperVO) item;
                String pkValue = vo.getPrimaryKey();
                if (!delSet.contains(pkValue)) {
                    newList.add(item);
                }
            }
            return newList;
        }
        else {
            return grandOrigList;
        }
    }
	
	/**
     * 获得表体的VO数组
     */
    public static CircularlyAccessibleValueObject[] getBodyVOsByTabCode(BillForm mainForm, String tableCode) {

        String className = MainGrandUtil.getClassNameByTabCode(mainForm, tableCode);
        CircularlyAccessibleValueObject[] bodyVos = null;
        if(className != null){
        	bodyVos = mainForm.getBillCardPanel().getBillModel(tableCode).getBodyValueVOs(className);
        }
        if(bodyVos != null ){
        	int i = 0;
        	for (CircularlyAccessibleValueObject circularlyAccessibleValueObject : bodyVos) {
				if (circularlyAccessibleValueObject.getAttributeValue("rowno") == null && circularlyAccessibleValueObject.getAttributeValue("precolumn") == null) {
					circularlyAccessibleValueObject.setAttributeValue("rowno", ""+bodyVos.length+"");
					circularlyAccessibleValueObject.setAttributeValue("precolumn", ""+bodyVos.length+"");
				}else if(circularlyAccessibleValueObject.getAttributeValue("rowno") != null && circularlyAccessibleValueObject.getAttributeValue("precolumn") == null) {
					circularlyAccessibleValueObject.setAttributeValue("rowno", ""+(i+1)+"");
					circularlyAccessibleValueObject.setAttributeValue("precolumn", ""+(i+1)+"");
					i++;
				}
			}
        }
        return bodyVos;
    }

    /**
     * 获得表体变化的VO数组
     */
    public static CircularlyAccessibleValueObject[] getBodyChangeVoByTabCode(BillForm mainForm, String tableCode) {

        String className = MainGrandUtil.getClassNameByTabCode(mainForm, tableCode);
        CircularlyAccessibleValueObject[] bodyVos = null;
        if(className != null){
        	bodyVos = mainForm.getBillCardPanel().getBillModel(tableCode).getBodyValueChangeVOs(className);
        }
        return bodyVos;
    }
	
    /**
     * 根据tablecode取得类名
     */
    public static String getClassNameByTabCode(BillForm mainForm, String tableCode) {

    	String className = null;
    	
        if(MainGrandUtil.getBusinessEntityByTabCode(mainForm, tableCode) != null){
        	
        	className = MainGrandUtil.getBusinessEntityByTabCode(mainForm, tableCode).getFullClassName();
        }
        
        return className;
    }
    
    private static IBusinessEntity getBusinessEntityByTabCode(BillForm mainForm, String tableCode){
    	
    	IBusinessEntity billMetaDataBusinessEntity = null;
    	
    	BillModel model = mainForm.getBillCardPanel().getBillModel(tableCode);
    	
    	BillTabVO bodyTabVo = model.getTabvo();
    	
    	if(bodyTabVo != null){
    		billMetaDataBusinessEntity = bodyTabVo.getBillMetaDataBusinessEntity();
    	}
    	return billMetaDataBusinessEntity;
    }
    
    
    
    /**
     * 根据tablecode取得类名
     */
    public static String getClassNameByListTabCode(BillListView mainListView, String tableCode) {

        BillModel model = mainListView.getBillListPanel().getBodyBillModel(tableCode);
        
        BillTabVO bodyTabVo = model.getTabvo();
        
        IBusinessEntity billMetaDataBusinessEntity = null;
        
        String className = null;
    	
    	if(bodyTabVo != null){
    		billMetaDataBusinessEntity = bodyTabVo.getBillMetaDataBusinessEntity();
    	}
    	if(billMetaDataBusinessEntity != null){
    		className = billMetaDataBusinessEntity.getFullClassName();
    	}
        return className;
    }
    
    /**
     * 获得界面的PK值（增加状态获得伪列值）
     */
    public static String getPkFormPanel(BillForm billform){
    	
    	String childPK = null;
    	
    	String tabCode = billform.getBillCardPanel().getCurrentBodyTableCode();
    	
    	int selrow = billform.getBillCardPanel().getBillTable().getSelectedRow();
        CircularlyAccessibleValueObject[] bodyVOs = MainGrandUtil.getBodyVOsByTabCode(billform, tabCode);
        if(bodyVOs == null || bodyVOs.length == 0){
        	return null;
        }
        if(billform.getModel().getUiState().equals(UIState.EDIT)){        	
        	try {
        		childPK = bodyVOs[selrow].getPrimaryKey();
			} catch (BusinessException e) {
				Logger.error(e.getMessage(), e);
			}
        }
        if(billform.getModel().getUiState().equals(UIState.ADD)){
        	childPK = (String) bodyVOs[selrow].getAttributeValue("precolumn");
        }
        return childPK;
    }
}
