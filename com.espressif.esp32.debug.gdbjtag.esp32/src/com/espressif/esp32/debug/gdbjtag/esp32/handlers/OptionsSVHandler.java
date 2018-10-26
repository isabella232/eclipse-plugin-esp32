package com.espressif.esp32.debug.gdbjtag.esp32.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.jface.dialogs.MessageDialog;

import com.espressif.esp32.debug.gdbjtag.dsf.GnuArmFinalLaunchSequence;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class OptionsSVHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		MessageDialog.openInformation(
				window.getShell(),
				"Esp32",
				"Options SV");
		
		String stop_cmd = "monitor esp32 sysview status"; 
		GnuArmFinalLaunchSequence.Exec_queueCommand(stop_cmd);


		return null;
	}
}
