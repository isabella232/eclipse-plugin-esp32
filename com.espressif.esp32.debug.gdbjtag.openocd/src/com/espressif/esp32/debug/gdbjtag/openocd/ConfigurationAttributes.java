/*******************************************************************************
 * Copyright (c) 2013 Liviu Ionescu.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Liviu Ionescu - initial version
 *     Espressif Systems Ltd — ESP32 support
 *******************************************************************************/

package com.espressif.esp32.debug.gdbjtag.openocd;

public interface ConfigurationAttributes {

	// ------------------------------------------------------------------------

	public static final String PREFIX = Activator.PLUGIN_ID;

	// ------------------------------------------------------------------------

	// TabDebugger

	// Must be in sync with plugin.xml definition
	public static final String JTAG_DEVICE = "ESP32 OpenOCD";

	public static final String DO_START_GDB_SERVER = PREFIX + ".doStartGdbServer"; //$NON-NLS-1$

	public static final String GDB_SERVER_EXECUTABLE = PREFIX + ".gdbServerExecutable"; //$NON-NLS-1$

	public static final String GDB_SERVER_CONNECTION_ADDRESS = PREFIX + ".gdbServerConnectionAddress"; //$NON-NLS-1$

	public static final String GDB_SERVER_GDB_PORT_NUMBER = PREFIX + ".gdbServerGdbPortNumber"; //$NON-NLS-1$

	public static final String GDB_SERVER_TELNET_PORT_NUMBER = PREFIX + ".gdbServerTelnetPortNumber"; //$NON-NLS-1$

	public static final String GDB_SERVER_LOG = PREFIX + ".gdbServerLog"; //$NON-NLS-1$

	public static final String GDB_SERVER_OTHER = PREFIX + ".gdbServerOther"; //$NON-NLS-1$

	public static final String DO_GDB_SERVER_ALLOCATE_CONSOLE = PREFIX + ".doGdbServerAllocateConsole"; //$NON-NLS-1$

	public static final String DO_GDB_SERVER_ALLOCATE_TELNET_CONSOLE = PREFIX + ".doGdbServerAllocateTelnetConsole"; //$NON-NLS-1$

	public static final String GDB_CLIENT_OTHER_OPTIONS = PREFIX + ".gdbClientOtherOptions"; //$NON-NLS-1$

	public static final String GDB_CLIENT_OTHER_COMMANDS = PREFIX + ".gdbClientOtherCommands"; //$NON-NLS-1$

	// ------------------------------------------------------------------------

	// TabStartup
	public static final String DO_FIRST_RESET = PREFIX + ".doFirstReset"; //$NON-NLS-1$
	public static final String FIRST_RESET_TYPE = PREFIX + ".firstResetType"; //$NON-NLS-1$

	public static final String ENABLE_SEMIHOSTING = PREFIX + ".enableSemihosting"; //$NON-NLS-1$

	public static final String OTHER_INIT_COMMANDS = PREFIX + ".otherInitCommands"; //$NON-NLS-1$

	public static final String DO_DEBUG_IN_RAM = PREFIX + ".doDebugInRam"; //$NON-NLS-1$

	public static final String DO_SECOND_RESET = PREFIX + ".doSecondReset"; //$NON-NLS-1$

	public static final String SECOND_RESET_TYPE = PREFIX + ".secondResetType"; //$NON-NLS-1$

	public static final String OTHER_RUN_COMMANDS = PREFIX + ".otherRunCommands"; //$NON-NLS-1$

	public static final String DO_CONTINUE = PREFIX + ".doContinue"; //$NON-NLS-1$

	public static final boolean DEFAULT_LOAD_IMAGE = false; // Just while flash loader not ready
	// ------------------------------------------------------------------------
	
	// System View
	
	public static final String SYSVIEW_PRO_CPU_FILE = PREFIX + ".SysviewProCpuFile"; //$NON-NLS-1$
	public static final String SYSVIEW_APP_CPU_FILE = PREFIX + ".SysviewAppCpuFile"; //$NON-NLS-1$
	
	public static final String SYSVIEW_POOL_PERIOD = PREFIX + ".SysviewPoolPeriod"; //$NON-NLS-1$
	public static final String SYSVIEW_TRACE_SIZE = PREFIX + ".SysviewTraceSize"; //$NON-NLS-1$
	public static final String SYSVIEW_STOP_TMO = PREFIX + ".SysviewStopTmo"; //$NON-NLS-1$
}
