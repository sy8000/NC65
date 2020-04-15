package nc.vo.qcco.commission;

import java.util.ArrayList;
import java.util.List;

import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.ISuperVO;
import nc.vo.pub.IVOMeta;
import nc.vo.pubapp.pattern.model.entity.bill.AbstractBill;
import nc.vo.pubapp.pattern.model.meta.entity.bill.BillMetaFactory;
import nc.vo.pubapp.pattern.model.meta.entity.bill.IBillMeta;

import org.mozilla.javascript.edu.emory.mathcs.backport.java.util.Arrays;

@nc.vo.annotation.AggVoInfo(parentVO = "nc.vo.qcco.commission.CommissionHVO")
public class AggCommissionHVO extends AbstractBill {

	@Override
	public IBillMeta getMetaData() {
		IBillMeta billMeta = BillMetaFactory.getInstance().getBillMeta(AggCommissionHVOMeta.class);
		return billMeta;
	}

	@Override
	public CommissionHVO getParentVO() {
		return (CommissionHVO) this.getParent();
	}

	@Override
	public CircularlyAccessibleValueObject[] getChildrenVO() {
		IBillMeta billMeta = getMetaData();

		if (billMeta.getChildren().length == 0) {
			return null;
		}
		IVOMeta[] childMetas = billMeta.getChildren();

		List<CircularlyAccessibleValueObject> rtn = new ArrayList<CircularlyAccessibleValueObject>();
		for (IVOMeta childMeta : childMetas) {
			ISuperVO[] vos = getChildren(childMeta);
			if (vos != null) {
				rtn.addAll(Arrays.asList(vos));
			}
		}

		return rtn.toArray(new CircularlyAccessibleValueObject[0]);
	}

}