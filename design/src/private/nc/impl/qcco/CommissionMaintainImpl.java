package nc.impl.qcco;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nc.bs.dao.BaseDAO;
import nc.bs.dao.DAOException;
import nc.bs.framework.common.InvocationInfoProxy;
import nc.bs.logging.Logger;
import nc.hr.utils.InSQLCreator;
import nc.impl.pub.ace.AceCommissionPubServiceImpl;
import nc.itf.qcco.ICommissionMaintain;
import nc.jdbc.framework.processor.ColumnProcessor;
import nc.jdbc.framework.processor.MapListProcessor;
import nc.jdbc.framework.processor.ResultSetProcessor;
import nc.ui.pub.beans.constenum.IConstEnum;
import nc.ui.querytemplate.querytree.IQueryScheme;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.ISuperVO;
import nc.vo.pub.lang.UFDateTime;
import nc.vo.pubapp.pattern.exception.ExceptionUtils;
import nc.vo.qcco.commission.AggCommissionHVO;
import nc.vo.qcco.commission.CommissionBVO;
import nc.vo.qcco.commission.CommissionCVO;
import nc.vo.qcco.commission.CommissionHVO;
import nc.vo.qcco.commission.CommissionRVO;
import nc.vo.qcco.qccommission.DocStates;

public class CommissionMaintainImpl extends AceCommissionPubServiceImpl implements ICommissionMaintain {

	private BaseDAO dao = null;

	public BaseDAO getDao() {
		if (dao == null) {
			dao = new BaseDAO();
		}
		return dao;
	}

	/*
	 * @Override public AggCommissionHVO[] insert(AggCommissionHVO[]
	 * clientFullVOs, AggCommissionHVO[] originBills) throws BusinessException {
	 * return super.pubinsertBills(clientFullVOs, originBills); }
	 * 
	 * @Override public AggCommissionHVO[] update(AggCommissionHVO[]
	 * clientFullVOs, AggCommissionHVO[] originBills) throws BusinessException {
	 * return super.pubupdateBills(clientFullVOs, originBills); }
	 */
	@Override
	public AggCommissionHVO[] query(IQueryScheme queryScheme) throws BusinessException {
		return super.pubquerybills(queryScheme);
	}

	@Override
	public AggCommissionHVO[] save(AggCommissionHVO[] clientFullVOs, AggCommissionHVO[] originBills)
			throws BusinessException {
		check(clientFullVOs);
		return super.pubsendapprovebills(clientFullVOs, originBills);
	}

	@Override
	public AggCommissionHVO[] unsave(AggCommissionHVO[] clientFullVOs, AggCommissionHVO[] originBills)
			throws BusinessException {
		return super.pubunsendapprovebills(clientFullVOs, originBills);
	}

	@Override
	public AggCommissionHVO[] approve(AggCommissionHVO[] clientFullVOs, AggCommissionHVO[] originBills)
			throws BusinessException {
		return super.pubapprovebills(clientFullVOs, originBills);
	}

	@Override
	public AggCommissionHVO[] unapprove(AggCommissionHVO[] clientFullVOs, AggCommissionHVO[] originBills)
			throws BusinessException {
		return super.pubunapprovebills(clientFullVOs, originBills);
	}

	@Override
	public AggCommissionHVO[] insert(AggCommissionHVO[] vos) throws BusinessException {
		check(vos);
		return super.pubinsertBills(vos, null);
	}

	@Override
	public void delete(AggCommissionHVO[] vos) throws BusinessException {
		super.pubdeleteBills(vos);

	}

	@SuppressWarnings("unchecked")
	@Override
	public AggCommissionHVO[] update(AggCommissionHVO[] vos) throws BusinessException {
		check(vos);
		List<String> list = new ArrayList<String>();
		for (AggCommissionHVO vo : vos) {
			list.add((String) vo.getParent().getAttributeValue("pk_commission_h"));
		}
		InSQLCreator insql = new InSQLCreator();
		String commissionInSQL = insql.getInSQL(list.toArray(new String[0]));
		List<CommissionHVO> lists = (List<CommissionHVO>) this.getDao().retrieveByClause(CommissionHVO.class,
				"pk_commission_h in(" + commissionInSQL + ")");
		if (lists.size() > 0) {
			for (AggCommissionHVO vo : vos) {
				CircularlyAccessibleValueObject[] bvos = vo.getChildrenVO();
				for (CommissionHVO hvo : lists) {
					if (null != hvo.getAttributeValue("pk_commission_h")
							&& vo.getParentVO().getAttributeValue("pk_commission_h") != null
							&& hvo.getAttributeValue("pk_commission_h").equals(
									vo.getParentVO().getAttributeValue("pk_commission_h"))) {
						for (CircularlyAccessibleValueObject bvo : bvos) {
							if (bvo instanceof CommissionBVO) {
								((CommissionBVO) bvo).setTs(hvo.getTs());
								CommissionRVO[] rvos = ((CommissionBVO) bvo).getPk_commission_r();
								if (null != rvos) {
									for (CommissionRVO rvo : rvos) {
										rvo.setTs(hvo.getTs());
									}
								}
							} else if (bvo instanceof CommissionCVO) {
								((CommissionCVO) bvo).setTs(hvo.getTs());
							}
						}
					}
				}

			}
		}

		return super.pubupdateBills(vos);
	}

	private void check(AggCommissionHVO[] vos) throws BusinessException {
		checkMail(vos);
		checkBody(vos);
		checkTelNO(vos);
		checkBatchnumber(vos);
	}

	private void checkBatchnumber(AggCommissionHVO[] vos) throws BusinessException {
		if (vos != null) {
			for (AggCommissionHVO aggvo : vos) {
				if (aggvo != null && aggvo.getParentVO() != null && aggvo.getParentVO().getBatchnumber() != null
						&& !aggvo.getParentVO().getBatchnumber().equals("")) {
					String email = aggvo.getParentVO().getBatchnumber();
					boolean isMatch = email
							.matches("^[0-9]*$");
					if (!isMatch) {
						throw new BusinessException("生产批量只能为数字!");
					}
				}
			}
		}
	}

	private void checkTelNO(AggCommissionHVO[] vos) throws BusinessException {
		if (vos != null) {
			for (AggCommissionHVO aggvo : vos) {
				if (aggvo != null && aggvo.getParentVO() != null && aggvo.getParentVO().getTeleno() != null
						&& !aggvo.getParentVO().getTeleno().equals("")) {
					String email = aggvo.getParentVO().getTeleno();
					boolean isMatch = email
							.matches("^[0-9]*$");
					if (!isMatch) {
						throw new BusinessException("联系电话只能为数字!");
					}
				}
			}
		}
	}
	private void checkMail(AggCommissionHVO[] vos) throws BusinessException {
		if (vos != null) {
			for (AggCommissionHVO aggvo : vos) {
				if (aggvo != null && aggvo.getParentVO() != null && aggvo.getParentVO().getEmail() != null
						&& !aggvo.getParentVO().getEmail().equals("")) {
					String email = aggvo.getParentVO().getEmail();
					boolean isMatch = email
							.matches("^[a-z0-9A-Z]+[- | a-z0-9A-Z . _]+@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-z]{2,}$");
					if (!isMatch) {
						throw new BusinessException("电子邮箱不合法!");
					}
				}
			}
		}
	}

	/*
	 * @Override public void delete(AggCommissionHVO[] clientFullVOs,
	 * AggCommissionHVO[] originBills) throws BusinessException { 
	 * Auto-generated method stub
	 * 
	 * }
	 */

	/**
	 * 校验表体是否为空,组别不能重复
	 * 
	 * @param clientFullVOs
	 * @throws BusinessException
	 */
	private void checkBody(AggCommissionHVO[] clientFullVOs) throws BusinessException {
		if (null != clientFullVOs) {
			for (AggCommissionHVO aggvo : clientFullVOs) {
				if (aggvo != null) {
					ISuperVO[] bvos = aggvo.getChildren(CommissionBVO.class);
					if (null == bvos || bvos.length == 0) {
						throw new BusinessException("表体不能为空!");
					} else {
						Set<String> groupSet = new HashSet();
						for (ISuperVO superVO : bvos) {
							CommissionBVO bvo = (CommissionBVO) superVO;
							if (bvo != null && bvo.getPk_samplegroup() != null) {
								if (groupSet.contains(bvo.getPk_samplegroup())) {
									throw new BusinessException("表体组别不能重复!");
								} else {
									if(3!=bvo.getStatus()){
										groupSet.add(bvo.getPk_samplegroup());	
									}
									
								}
							}
						}
					}
				}
			}
		}
	}

	/**
	 * 处理参照的值
	 * 
	 * @throws BusinessException
	 */
	@SuppressWarnings("unchecked")
	@Override
	public IConstEnum[] getRefName(IConstEnum[] o, Map<String, Map<String, String>> refNamePkMap,
			Map<String, Object[]> realPksMap) throws BusinessException {
		if (o == null || o.length <= 0 || refNamePkMap == null || refNamePkMap.size() <= 0) {
			return o;
		}
		// 去数据库搜索参照的值
		Set<String> sqlSet = new HashSet();
		for (String refField : refNamePkMap.keySet()) {
			if (refField != null && refField.equals("pk_maincategory") && refNamePkMap.get("pk_maincategory") != null
					&& refNamePkMap.get("pk_maincategory").size() > 0) {
				InSQLCreator insql = new InSQLCreator();
				String refInSQL = insql.getInSQL(refNamePkMap.get("pk_maincategory").keySet().toArray(new String[0]));
				// 产品大类
				String sql = " SELECT 'pk_maincategory' reffield, TRIM(NC_FIRST_NAME) refname, "
						+ " PK_FIRST_TYPE  refpk FROM NC_FIRST_TYPE " + " WHERE IS_ENABLE=1 AND pk_first_type IN ("
						+ refInSQL + ") ";
				sqlSet.add(sql);
			} else if (refField != null && refField.equals("pk_subcategory")
					&& refNamePkMap.get("pk_subcategory") != null && refNamePkMap.get("pk_subcategory").size() > 0) {
				// 二级分类
				InSQLCreator insql = new InSQLCreator();
				String refInSQL = insql.getInSQL(refNamePkMap.get("pk_subcategory").keySet().toArray(new String[0]));
				String sql = " select 'pk_subcategory' reffield, "
						+ " TRIM(NC_DESCRIPT) refname, PK_SECOND_TYPE refpk from NC_SECOND_TYPE "
						+ " where IS_ENABLE=1 and PK_SECOND_TYPE in (" + refInSQL + ") ";
				sqlSet.add(sql);
			} else if (refField != null && refField.equals("pk_commissiontype")
					&& refNamePkMap.get("pk_commissiontype") != null
					&& refNamePkMap.get("pk_commissiontype").size() > 0) {
				// 委托单类型
				InSQLCreator insql = new InSQLCreator();
				String refInSQL = insql.getInSQL(refNamePkMap.get("pk_commissiontype").keySet().toArray(new String[0]));
				String sql = " select 'pk_commissiontype' reffield, NAME refname,  "
						+ " PK_PROJ_TYPE refpk from NC_PROJ_TYPE  " + " WHERE ISENABLE=1 and PK_PROJ_TYPE in ("
						+ refInSQL + ") ";

				sqlSet.add(sql);
			} else if (refField != null && refField.equals("codeprefix") && refNamePkMap.get("codeprefix") != null
					&& refNamePkMap.get("codeprefix").size() > 0) {
				// 委托单编码前缀
				InSQLCreator insql = new InSQLCreator();
				String refInSQL = insql.getInSQL(refNamePkMap.get("codeprefix").keySet().toArray(new String[0]));
				String sql = " select 'codeprefix' reffield, NC_SAFE_NAME refname,  "
						+ " PK_SAFE_TYPE refpk from NC_PROJ_PREFIX WHERE ISENABLE=1  " + " and PK_SAFE_TYPE in ("
						+ refInSQL + ") ";

				sqlSet.add(sql);
			} else if (refField != null && refField.equals("pk_lastcategory")
					&& refNamePkMap.get("pk_lastcategory") != null && refNamePkMap.get("pk_lastcategory").size() > 0) {
				// 三级分类
				InSQLCreator insql = new InSQLCreator();
				String refInSQL = insql.getInSQL(refNamePkMap.get("pk_lastcategory").keySet().toArray(new String[0]));
				String sql = " select 'pk_lastcategory' reffield, TRIM(NC_DESCRIPT) refname,  "
						+ " PK_THIRD_TYPE refpk from NC_THIRD_TYPE WHERE IS_ENABLE=1  " + " and PK_THIRD_TYPE in ("
						+ refInSQL + ") ";

				sqlSet.add(sql);
			} else if (refField != null && refField.equals("reportformat") && refNamePkMap.get("reportformat") != null
					&& refNamePkMap.get("reportformat").size() > 0) {
				// 报告格式
				InSQLCreator insql = new InSQLCreator();
				String refInSQL = insql.getInSQL(refNamePkMap.get("reportformat").keySet().toArray(new String[0]));
				String sql = " select 'reportformat' reffield, RP_REPORT_NAME refname,  "
						+ " PK_REPORT_TYPE refpk from NC_REPORT_TYPE WHERE ISENABLE=1  " + " and PK_REPORT_TYPE in ("
						+ refInSQL + ") ";

				sqlSet.add(sql);
			} else if (refField != null && refField.equals("reportlang") && refNamePkMap.get("reportlang") != null
					&& refNamePkMap.get("reportlang").size() > 0) {
				// 报告语言
				InSQLCreator insql = new InSQLCreator();
				String refInSQL = insql.getInSQL(refNamePkMap.get("reportlang").keySet().toArray(new String[0]));
				String sql = " select 'reportlang' reffield, RP_REPORT_NAME refname,  "
						+ " PK_REPORT_LANG refpk from NC_REPORT_LANG WHERE ISENABLE=1  " + " and PK_REPORT_LANG in ("
						+ refInSQL + ") ";

				sqlSet.add(sql);
			} else if (refField != null && refField.equals("sampledealtype")
					&& refNamePkMap.get("sampledealtype") != null && refNamePkMap.get("sampledealtype").size() > 0) {
				// 检后样品处理
				InSQLCreator insql = new InSQLCreator();
				String refInSQL = insql.getInSQL(refNamePkMap.get("sampledealtype").keySet().toArray(new String[0]));
				String sql = " select  'sampledealtype' reffield, NC_RATAIN_NAME refname,  "
						+ " PK_RATAIN_HANDLE refpk from NC_RATAIN_HANDLE WHERE ISENABLE=1 and  "
						+ " PK_RATAIN_HANDLE in (" + refInSQL + ") ";

				sqlSet.add(sql);
			} else if (refField != null && refField.equals("productproperty")
					&& refNamePkMap.get("productproperty") != null && refNamePkMap.get("productproperty").size() > 0) {
				// 产品属性
				InSQLCreator insql = new InSQLCreator();
				String refInSQL = insql.getInSQL(refNamePkMap.get("productproperty").keySet().toArray(new String[0]));
				String sql = " select 'productproperty' reffield, NC_STATUS_NAME refname,  "
						+ " PK_STATUS_HANDLE refpk from NC_STATUS_TYPE WHERE ISENABLE=1 and "
						+ " PK_STATUS_HANDLE in (" + refInSQL + ") ";

				sqlSet.add(sql);
			} else if (refField != null && refField.equals("customertype") && refNamePkMap.get("customertype") != null
					&& refNamePkMap.get("customertype").size() > 0) {
				// 客户类型
				InSQLCreator insql = new InSQLCreator();
				String refInSQL = insql.getInSQL(refNamePkMap.get("customertype").keySet().toArray(new String[0]));
				String sql = " select 'customertype' reffield, NC_CUSTOMER_NAME refname,  "
						+ " PK_CUSTOMER_TYPE refpk from NC_CUSTOMER_TYPE WHERE ISENABLE=1 and  "
						+ " PK_CUSTOMER_TYPE in (" + refInSQL + ") ";

				sqlSet.add(sql);
			} else if (refField != null && refField.equals("testrequirement")
					&& refNamePkMap.get("testrequirement") != null && refNamePkMap.get("testrequirement").size() > 0) {
				// 测试需求
				InSQLCreator insql = new InSQLCreator();
				String refInSQL = insql.getInSQL(refNamePkMap.get("testrequirement").keySet().toArray(new String[0]));
				String sql = " select 'testrequirement' reffield, NC_TESTREQUEST_NAME refname,  "
						+ " PK_TESTREQUEST_TYPE refpk from NC_TESTREQUEST_TYPE WHERE ISENABLE=1 and  "
						+ " PK_TESTREQUEST_TYPE in (" + refInSQL + ") ";

				sqlSet.add(sql);
			} else if (refField != null && refField.equals("checkingproperty")
					&& refNamePkMap.get("checkingproperty") != null && refNamePkMap.get("checkingproperty").size() > 0) {
				// 检测性质
				InSQLCreator insql = new InSQLCreator();
				String refInSQL = insql.getInSQL(refNamePkMap.get("checkingproperty").keySet().toArray(new String[0]));
				String sql = " select 'checkingproperty' reffield, NC_RATAIN_NAME refname,  "
						+ " PK_RATAIN_HANDLE refpk from NC_TEST_TYPE WHERE ISENABLE=1 and  " + " PK_RATAIN_HANDLE in ("
						+ refInSQL + ") ";

				sqlSet.add(sql);
			} else if (refField != null && refField.equals("identificationtype")
					&& refNamePkMap.get("identificationtype") != null
					&& refNamePkMap.get("identificationtype").size() > 0) {
				// 产品鉴定类型
				InSQLCreator insql = new InSQLCreator();
				String refInSQL = insql
						.getInSQL(refNamePkMap.get("identificationtype").keySet().toArray(new String[0]));
				String sql = " select 'identificationtype' reffield, NC_PRODAUTH_NAME refname, "
						+ " PK_PRODAUTH_TYPE refpk from NC_PRODAUTH_TYPE WHERE ISENABLE=1 and  "
						+ " PK_PRODAUTH_TYPE in (" + refInSQL + ") ";

				sqlSet.add(sql);
			} else if (refField != null && refField.equals("certificationtype")
					&& refNamePkMap.get("certificationtype") != null
					&& refNamePkMap.get("certificationtype").size() > 0) {
				// 认证类型
				InSQLCreator insql = new InSQLCreator();
				String refInSQL = insql.getInSQL(refNamePkMap.get("certificationtype").keySet().toArray(new String[0]));
				String sql = " select 'certificationtype' reffield, NC_SAFE_NAME refname,  "
						+ " PK_SAFE_TYPE refpk from NC_SAFE_TYPE WHERE ISENABLE=1 and  " + " PK_SAFE_TYPE in ("
						+ refInSQL + ") ";

				sqlSet.add(sql);
			}
		}
		StringBuilder sqlSb = new StringBuilder();
		for (String sql : sqlSet) {
			sqlSb.append(" (").append(sql).append(") union all ");
		}
		sqlSb.delete(sqlSb.length() - 11, sqlSb.length());

		List<Map<String, Object>> result = null;
		try {
			result = (List<Map<String, Object>>) getDao().executeQuery(sqlSb.toString(), new MapListProcessor());
		} catch (Exception e) {
			Logger.error(e.getCause());
		}
		if (result != null && result.size() > 0) {
			for (Map<String, Object> rowMap : result) {
				if (rowMap != null && rowMap.get("reffield") != null && rowMap.get("refpk") != null) {
					if (refNamePkMap.get(rowMap.get("reffield")) != null) {
						refNamePkMap.get(rowMap.get("reffield")).put(String.valueOf(rowMap.get("refpk")),
								String.valueOf(rowMap.get("refname")));
					}
				}
			}
		}
		for (IConstEnum constEnum : o) {
			if (constEnum != null && constEnum.getName() != null) {
				String itemKey = constEnum.getName();
				if (itemKey.equals("pk_commission_h.pk_maincategory")) {
					// 产品大类
					getItem(refNamePkMap, constEnum, "pk_maincategory", realPksMap.get("pk_maincategory"));
				} else if (itemKey.equals("pk_commission_h.pk_subcategory")) {
					// 二级分类
					getItem(refNamePkMap, constEnum, "pk_subcategory", realPksMap.get("pk_subcategory"));
				} else if (itemKey.equals("pk_commission_h.pk_commissiontype")) {
					// 委托单类型
					getItem(refNamePkMap, constEnum, "pk_commissiontype", realPksMap.get("pk_commissiontype"));
				} else if (itemKey.equals("pk_commission_h.codeprefix")) {
					// 委托单编码前缀
					getItem(refNamePkMap, constEnum, "codeprefix", realPksMap.get("codeprefix"));
				} else if (itemKey.equals("pk_commission_h.pk_lastcategory")) {
					// 三级分类
					getItem(refNamePkMap, constEnum, "pk_lastcategory", realPksMap.get("pk_lastcategory"));
				} else if (itemKey.equals("pk_commission_h.reportformat")) {
					// 报告格式
					getItem(refNamePkMap, constEnum, "reportformat", realPksMap.get("reportformat"));
				} else if (itemKey.equals("pk_commission_h.reportlang")) {
					// 报告语言
					getItem(refNamePkMap, constEnum, "reportlang", realPksMap.get("reportlang"));
				} else if (itemKey.equals("pk_commission_h.sampledealtype")) {
					// 检后样品处理
					getItem(refNamePkMap, constEnum, "sampledealtype", realPksMap.get("sampledealtype"));
				} else if (itemKey.equals("pk_commission_h.productproperty")) {
					// 产品属性
					getItem(refNamePkMap, constEnum, "productproperty", realPksMap.get("productproperty"));
				} else if (itemKey.equals("pk_commission_h.customertype")) {
					// 客户类型
					getItem(refNamePkMap, constEnum, "customertype", realPksMap.get("customertype"));
				} else if (itemKey.equals("pk_commission_h.testrequirement")) {
					// 测试需求
					getItem(refNamePkMap, constEnum, "testrequirement", realPksMap.get("testrequirement"));
				} else if (itemKey.equals("pk_commission_h.checkingproperty")) {
					// 检测性质
					getItem(refNamePkMap, constEnum, "checkingproperty", realPksMap.get("checkingproperty"));
				} else if (itemKey.equals("pk_commission_h.identificationtype")) {
					// 产品鉴定类型
					getItem(refNamePkMap, constEnum, "identificationtype", realPksMap.get("identificationtype"));
				} else if (itemKey.equals("pk_commission_h.certificationtype")) {
					// 认证类型
					getItem(refNamePkMap, constEnum, "certificationtype", realPksMap.get("certificationtype"));
				}

			}
		}
		return o;
	}

	private void getItem(Map<String, Map<String, String>> refNamePk, IConstEnum constEnum, String reffield,
			Object[] realPk) {
		if (refNamePk == null || constEnum == null || reffield == null || realPk == null) {
			return;
		}
		Object[] names = (Object[]) constEnum.getValue();

		if (names != null && names.length > 0) {
			for (int i = 0; i < names.length; i++) {
				if (refNamePk.get(reffield) != null) {
					try {
						names[i] = refNamePk.get(reffield).get(String.valueOf(realPk[i]));
					} catch (Exception e) {
						Logger.error(e.getCause());
					}

				}
			}
		}

	}

	
	/**
	 * 正式报告驳回
	 * @param parentVO
	 * @param txtMessage
	 * @throws BusinessException 
	 */
	@Override
	public void officialReject(CommissionHVO parentVO, String txtMessage) throws BusinessException {
		if (parentVO == null || parentVO.getBillno() == null 
				|| parentVO.getReportformat() == null) {
			return;
		}
		String reportFormat = parentVO.getReportformat();
		String sql = "select PK_REPORT_TYPE,RP_REPORT_NAME  from NC_REPORT_TYPE";
		@SuppressWarnings("unchecked")
		Map<String,String> pk2UnitNameMap = 
				(Map<String,String>)getDao().executeQuery(sql, new ResultSetProcessor() {

			private static final long serialVersionUID = -7409984761901560761L;
			private Map<String,String> rsMap = new HashMap<>();
			@Override
			public Object handleResultSet(ResultSet rs) throws SQLException {
				while(rs.next()){
					rsMap.put(rs.getString(1), rs.getString(2));
				}
				return rsMap;
			}
		});
		
		txtMessage = (txtMessage==null?"":txtMessage);
		String reportFormatName = pk2UnitNameMap.get(reportFormat);
		if(reportFormatName==null){
			return ;
		}
		if(reportFormatName.equals("单项")){
			//单项驳回
			sql = "update C_PROJ_TASK set RPT_AUTHORIZED_BY = null,RPT_AUTHORIZED_ON = null,RPT_AUTHORIZED = 'F', "
				+" RPT_REJECT_COMMENT_C = '"+txtMessage+"' where project = '"+parentVO.getBillno()+"' ";
			getDao().executeUpdate(sql);
			
			sql = "update PROJECT set C_RPT_AUTHORIZED = 'F',C_ALLTASK_COA_AUTHORIZED = 'F' "
			+" ,C_INVOICE_CREATED = 'F', C_INVOICE_CREATED_ON = null,C_INVOICE_CREATED_BY = null "
			+" where name = '"+parentVO.getBillno()+"' ";
				getDao().executeUpdate(sql);
			
		}else if(reportFormatName.equals("成套")){
			//成套驳回
			sql = "update PROJECT set C_RPT_AUTHORIZED_BY = null,C_RPT_AUTHORIZED_ON = null,C_RPT_AUTHORIZED = 'F', "
					+" C_ALLTASK_COA_AUTHORIZED = 'F',C_RPT_REJECT_COMMENT_C = '"+txtMessage+"' "
					+" ,C_INVOICE_CREATED = 'F', C_INVOICE_CREATED_ON = null, C_INVOICE_CREATED_BY = null "
					+" where name = '"+parentVO.getBillno()+"'";
			
			getDao().executeUpdate(sql);
		}
		changeStatues(parentVO.getPk_commission_h(),DocStates.GENERATE_BILLING.toIntValue());
	}
	/**
	 * 正式报告确认
	 * @param parentVO
	 * @param txtMessage
	 * @throws BusinessException 
	 */
	@Override
	public void officialComfirm(CommissionHVO parentVO, String txtMessage) throws BusinessException {
		if (parentVO == null || parentVO.getBillno() == null 
				|| parentVO.getReportformat() == null) {
			return;
		}
		changeStatues(parentVO.getPk_commission_h(),DocStates.SATISFACTION_EVALUATION.toIntValue());
	}
	
	
	/**
	 * 收费单确认
	 * @param parentVO
	 * @throws DAOException 
	 */
	@Override
	public void payDemandComfirtm(CommissionHVO parentVO) throws BusinessException {
		if (parentVO == null || parentVO.getBillno() == null) {
			return;
		}
		String sql = " update project set C_INVOICE_verifyed = 'T', " 
				+ " C_INVOICE_verifyed_on = " + "to_timestamp('"
				+ new UFDateTime().toStdString() + "','yyyy-mm-dd hh24:mi:ss.ff')" + ", " 
				+ " C_INVOICE_verifyed_by = '"
				+ InvocationInfoProxy.getInstance().getUserCode() + "' " 
				+ " WHERE NAME = '" + parentVO.getBillno() + "' ";
		getDao().executeUpdate(sql);
		changeStatues(parentVO.getPk_commission_h(),DocStates.REPORT_CONFIRMED.toIntValue());
	}
	
	
	
	/**
	 * 报价单确认
	 * @param commissionHVO
	 * @throws DAOException 
	 */
	@Override
	public void quotationConfirtm(CommissionHVO commissionHVO) throws BusinessException {
		if(commissionHVO==null || commissionHVO.getBillno() == null){
			return;
		}
		String sql = " update project set c_quotes_verifyed = 'T', "
       +" c_quotes_verifyed_on = "+"to_timestamp('"+ new UFDateTime().toStdString() +"','yyyy-mm-dd hh24:mi:ss.ff')"+" , " 
       +" c_quotes_verifyed_by = '"+InvocationInfoProxy.getInstance().getUserCode()+"' "
       +" WHERE NAME = '"+commissionHVO.getBillno()+"' ";
		getDao().executeUpdate(sql);
		changeStatues(commissionHVO.getPk_commission_h(),DocStates.SAMPLE_RECEIVED.toIntValue());
	}
	
	
	private void changeStatues(String pk_commission_h, int intValue) throws BusinessException {
		if(pk_commission_h!=null){
			getDao().executeUpdate("update qc_commission_h set docstatus = "+intValue+" where pk_commission_h = '"+pk_commission_h+"'");
		}
	}
	/**
	 * 满意度评价
	 * @throws BusinessException 
	 */
	@Override
	public void satisfactComfirtm(CommissionHVO parentVO) throws BusinessException {
		if(parentVO==null || parentVO.getBillno() == null){
			return;
		}
		changeStatues(parentVO.getPk_commission_h(),DocStates.END_PROCESS.toIntValue());
		
	}

	@Override
	public boolean isEditAble(String pk_commission_h)  {
		//查询对应任务单的状态
		Integer status = null;
		try {
			status = (Integer)getDao().executeQuery("select approvestatus  from QC_TASK_H where PK_COMMISSION_H = '"
						+pk_commission_h +"' and dr = 0", new ColumnProcessor());
		} catch (DAOException e) {
			ExceptionUtils.wrappException(e);
		}
		//如果已经提交,则不能修改
		if(status!=null && (status==1 ||status==2 ||status==3)){
			return false;
		}
		return true;
	}
	
	
	
	

}
