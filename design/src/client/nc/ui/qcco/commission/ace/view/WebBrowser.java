package nc.ui.qcco.commission.ace.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class WebBrowser {
	public static void open(String url, String title) {
		Display display = new Display();
		Shell shell = new Shell(display, SWT.MIN | SWT.CLOSE | SWT.APPLICATION_MODAL);
		shell.setText(title);
		shell.setSize(800, 600);
		final Text text = new Text(shell, SWT.BORDER);
		text.setBounds(5, 5, 783, 25);
		text.setText(url);
		text.setEditable(false);
		final Browser browser = new Browser(shell, SWT.FILL);
		browser.setBounds(5, 35, 783, 530);
		browser.setUrl(url);

		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();

	}
}
