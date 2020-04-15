package nc.ui.qcco.task.ace.view;
/**
 * 生成task_config需要注意的事
 * 1.需要在nc.ui.qcco.task.ace.view.Task_config.getSaveAction()上添加:getTaskBodyPasteToTailAction_XXXXX().setBillForm(getMainGrandbillFormEditor());
 * 
 * 2.需要在nc.ui.qcco.task.ace.view.Task_config.getSunlistView1() 上添加:bean.getBillListPanel().getBodyTabbedPane().setTabChangedListener(getMainGrandMediator());
 * 
 * 3.去掉TaskBodyPasteToTailAction()的构造参数
 * @author 91967
 *
 */
public @interface XML2JavaWarning {

}
