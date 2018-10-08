package ilg.esp32_eclipse.debug.gdbjtag.esp32.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.jface.dialogs.MessageDialog;
import ilg.esp32_eclipse.debug.gdbjtag.dsf.GnuArmFinalLaunchSequence;
import ilg.esp32_eclipse.debug.gdbjtag.openocd.Configuration;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class StartSVHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		String start_cmd = "monitor esp32 sysview start "; 
		
		start_cmd += Configuration.getSysveiwStartCommand();
		
		GnuArmFinalLaunchSequence.Exec_queueCommand(start_cmd);
				
		return null;
	}
}
