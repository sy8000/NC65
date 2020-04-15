package nc.ui.qcco.task.ace.handler;

import nc.ui.pub.LinkEvent;
import nc.ui.pub.LinkListener;

public class Commission2TaskListener implements LinkListener {

	@Override
	public void dealLinkEvent(LinkEvent paramLinkEvent) {
		Object obj = paramLinkEvent.getSource();
		System.out.println(obj);
	}

}
