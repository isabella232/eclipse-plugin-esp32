/*******************************************************************************
 * Copyright (c) 2015 Liviu Ionescu.
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

import com.espressif.esp32.core.EclipseUtils;

import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;

public class DefaultPreferences {

	// ------------------------------------------------------------------------

	// Constants
	public static final String REMOTE_IP_ADDRESS_LOCALHOST = "localhost"; //$NON-NLS-1$

	// ------------------------------------------------------------------------

	// Preferences
	protected static final boolean TAB_MAIN_CHECK_PROGRAM_DEFAULT = false;

	protected static final String GDB_SERVER_EXECUTABLE_DEFAULT = "${openocd_path}/src/${openocd_executable}";

	public static final String GDB_SERVER_EXECUTABLE_DEFAULT_NAME = "openocd";
	protected static final String GDB_CLIENT_EXECUTABLE_DEFAULT = "${xtensa_tools}/bin/xtensa-esp32-elf-gdb.exe";

    // ------------------------------------------------------------------------
	protected static final String SYSVIEW_PRO_CPU_FILE_DEFAULT = "pro-cpu.SVDat";
    private static final String  SYSVIEW_PRO_CPU_FILE_DEPRECATED = "pro-cpu.SVDat";
	protected static final String SYSVIEW_APP_CPU_FILE_DEFAULT = "app-cpu.SVDat";
    private static final String  SYSVIEW_APP_CPU_FILE_DEPRECATED = "app-cpu.SVDat";

	protected static final String SYSVIEW_POOL_PERIOD_DEFAULT = "";
    private static final String  SYSVIEW_POOL_PERIOD_DEPRECATED = "";
	protected static final String SYSVIEW_TRACE_SIZE_DEFAULT = "";
    private static final String  SYSVIEW_TRACE_SIZE_DEPRECATED = "";
	protected static final String SYSVIEW_STOP_TMO_DEFAULT = "";
    private static final String  SYSVIEW_STOP_TMO_DEPRECATED = "";

    

    // SysView Preferences


	// Not yet preferences
	public static final boolean DO_START_GDB_SERVER_DEFAULT = true;
	public static final String GDB_SERVER_CONNECTION_ADDRESS_DEFAULT = "";
	public static final int GDB_SERVER_GDB_PORT_NUMBER_DEFAULT = 3333;
	public static final int GDB_SERVER_TELNET_PORT_NUMBER_DEFAULT = 4444;
	public static final String GDB_SERVER_LOG_DEFAULT = ""; //$NON-NLS-1$
	public static final String GDB_SERVER_OTHER_DEFAULT = ""; //$NON-NLS-1$
	public static final boolean DO_GDB_SERVER_ALLOCATE_CONSOLE_DEFAULT = true;
	public static final boolean DO_GDB_SERVER_ALLOCATE_TELNET_CONSOLE_DEFAULT = false;

	public static final String GDB_CLIENT_OTHER_OPTIONS_DEFAULT = "";

	public static final boolean USE_REMOTE_TARGET_DEFAULT = true;
	public static final String REMOTE_IP_ADDRESS_DEFAULT = REMOTE_IP_ADDRESS_LOCALHOST; // $NON-NLS-1$
	public static final int REMOTE_PORT_NUMBER_DEFAULT = GDB_SERVER_GDB_PORT_NUMBER_DEFAULT;

	public static final boolean UPDATE_THREAD_LIST_DEFAULT = false;

	public static final boolean DO_FIRST_RESET_DEFAULT = true;
	public static final String FIRST_RESET_TYPE_DEFAULT = "init";

	public static final boolean ENABLE_SEMIHOSTING_DEFAULT = true;

	public static final boolean DO_DEBUG_IN_RAM_DEFAULT = false;

	public static final boolean DO_SECOND_RESET_DEFAULT = true;

	public static final String SECOND_RESET_TYPE_DEFAULT = "halt";

	public static final boolean DO_STOP_AT_DEFAULT = true;
	public static final String STOP_AT_NAME_DEFAULT = "app_main";

	public static final boolean DO_CONTINUE_DEFAULT = true;

	// ------------------------------------------------------------------------

	// Debugger commands
	public static final String GDB_CLIENT_OTHER_COMMANDS_DEFAULT = "set mem inaccessible-by-default off\n";
	public static final String DO_FIRST_RESET_COMMAND = "monitor reset ";
	public static final String HALT_COMMAND = "monitor halt";
	public static final String ENABLE_SEMIHOSTING_COMMAND = "monitor arm semihosting enable";
	public static final String DO_SECOND_RESET_COMMAND = "monitor reset ";
	public static final String DO_CONTINUE_COMMAND = "continue";
	public static final String OTHER_INIT_COMMANDS_DEFAULT = "";
	public static final String OTHER_RUN_COMMANDS_DEFAULT = "";

	// ------------------------------------------------------------------------

	// TODO: remove DEPRECATED
	// These values are deprecated. Use the definitions in PersistentValues.
	private static final String GDB_SERVER_EXECUTABLE_DEPRECATED = "gdb.server.executable.default";
	private static final String GDB_CLIENT_EXECUTABLE_DEPRECATED = "gdb.client.executable.default";

	private static final String OPENOCD_CONFIG_DEPRECATED = "openocd.config.default";

	private static final String OPENOCD_EXECUTABLE_DEPRECATED = "openocd_executable.default";
	private static final String OPENOCD_PATH_DEPRECATED = "openocd_path.default";

	// ------------------------------------------------------------------------

	/**
	 * The DefaultScope preference store.
	 */
	private static IEclipsePreferences fgPreferences;

	// ------------------------------------------------------------------------

	public static IEclipsePreferences getPreferences() {

		if (fgPreferences == null) {
			fgPreferences = DefaultScope.INSTANCE.getNode(Activator.PLUGIN_ID);
		}

		return fgPreferences;
	}

	/**
	 * Get a string preference value, or the default.
	 * 
	 * @param key
	 *            a string with the key to search.
	 * @param defaulValue
	 *            a string with the default, possibly null.
	 * @return a trimmed string, or a null default.
	 */
	private static String getString(String key, String defaulValue) {

		String value;
		value = getPreferences().get(key, defaulValue);

		if (value != null) {
			value = value.trim();
		}

		return value;
	}

	public static boolean getBoolean(String key, boolean defaultValue) {

		return getPreferences().getBoolean(key, defaultValue);
	}

	public static void putString(String key, String value) {
		getPreferences().put(key, value);
	}

	public static void putInt(String key, int value) {
		getPreferences().putInt(key, value);
	}

	public static void putBoolean(String key, boolean value) {
		getPreferences().putBoolean(key, value);
	}

	// ------------------------------------------------------------------------

	public static String getGdbServerExecutable() {
		String value = getString(PersistentPreferences.GDB_SERVER_EXECUTABLE, null);
		if (value != null) {
			return value;
		}
		return getString(GDB_SERVER_EXECUTABLE_DEPRECATED, GDB_SERVER_EXECUTABLE_DEFAULT);
	}

	public static String getGdbClientExecutable() {
		String value = getString(PersistentPreferences.GDB_CLIENT_EXECUTABLE, null);
		if (value != null) {
			return value;
		}
		return getString(GDB_CLIENT_EXECUTABLE_DEPRECATED, GDB_CLIENT_EXECUTABLE_DEFAULT);
	}

	// ------------------------------------------------------------------------
	public static String getSysviewProCpuFile() {
		String value = getString(PersistentPreferences.SYSVIEW_PRO_CPU_FILE, null);
		if (value != null) {
			return value;
		}
		return getString(SYSVIEW_PRO_CPU_FILE_DEPRECATED, SYSVIEW_PRO_CPU_FILE_DEFAULT);
	}

	public static String getSysviewAppCpuFile() {
		String value = getString(PersistentPreferences.SYSVIEW_APP_CPU_FILE, null);
		if (value != null) {
			return value;
		}
		return getString(SYSVIEW_APP_CPU_FILE_DEPRECATED, SYSVIEW_APP_CPU_FILE_DEFAULT);
	}

	public static String getSysviewPoolPeriod() {
		String value = getString(PersistentPreferences.SYSVIEW_POOL_PERIOD, null);
		if (value != null) {
			return value;
		}
		return getString(SYSVIEW_POOL_PERIOD_DEPRECATED, SYSVIEW_POOL_PERIOD_DEFAULT);
	}

	public static String getSysviewTraceSize() {
		String value = getString(PersistentPreferences.SYSVIEW_TRACE_SIZE, null);
		if (value != null) {
			return value;
		}
		return getString(SYSVIEW_TRACE_SIZE_DEPRECATED, SYSVIEW_TRACE_SIZE_DEFAULT);
	}

	public static String getSysviewStopTmo() {
		String value = getString(PersistentPreferences.SYSVIEW_STOP_TMO, null);
		if (value != null) {
			return value;
		}
		return getString(SYSVIEW_STOP_TMO_DEPRECATED, SYSVIEW_STOP_TMO_DEFAULT);
	}

    // ------------------------------------------------------------------------

	public static String getOpenocdConfig() {
		String value = getString(PersistentPreferences.GDB_SERVER_OTHER_OPTIONS, null);
		if (value != null) {
			return value;
		}
		return getString(OPENOCD_CONFIG_DEPRECATED, DefaultPreferences.GDB_SERVER_OTHER_DEFAULT);
	}

	// ------------------------------------------------------------------------

	public static boolean getTabMainCheckProgram() {
		return getBoolean(PersistentPreferences.TAB_MAIN_CHECK_PROGRAM,
				PersistentPreferences.TAB_MAIN_CHECK_PROGRAM_DEFAULT);
	}

	// ------------------------------------------------------------------------

	public static String getExecutableName() {

		String key = PersistentPreferences.EXECUTABLE_NAME;
		String value = getString(key, null);
		if (value == null) {

			// TODO: remove DEPRECATED
			value = getString(PersistentPreferences.OPENOCD_EXECUTABLE_DEPRECATED, null);
			if (value == null) {
				value = getString(OPENOCD_EXECUTABLE_DEPRECATED, "");
			}
		}

		if (Activator.getInstance().isDebugging()) {
			System.out.println("getExecutableName()=\"" + value + "\"");
		}
		return value;
	}

	public static String getExecutableNameOs() {

		String key = EclipseUtils.getKeyOs(PersistentPreferences.EXECUTABLE_NAME_OS);

		String value = getString(key, "");
		if (Activator.getInstance().isDebugging()) {
			System.out.println("getExecutableNameOs()=\"" + value + "\" (" + key + ")");
		}
		return value;
	}

	public static void putExecutableName(String value) {

		String key = PersistentPreferences.EXECUTABLE_NAME;

		if (Activator.getInstance().isDebugging()) {
			System.out.println("Default " + key + "=" + value);
		}
		putString(key, value);
	}

	// ------------------------------------------------------------------------

	public static String getInstallFolder() {

		String key = PersistentPreferences.INSTALL_FOLDER;
		String value = getString(key, null);
		if (value == null) {

			// TODO: remove DEPRECATED
			value = getString(PersistentPreferences.OPENOCD_PATH_DEPRECATED, null);
			if (value == null) {
				value = getString(OPENOCD_PATH_DEPRECATED, "");
			}
		}

		if (Activator.getInstance().isDebugging()) {
			System.out.println("getInstallFolder()=\"" + value + "\"");
		}
		return value;
	}

	public static void putInstallFolder(String value) {

		String key = PersistentPreferences.INSTALL_FOLDER;

		if (Activator.getInstance().isDebugging()) {
			System.out.println("Default " + key + "=" + value);
		}
		putString(key, value);
	}

	// ------------------------------------------------------------------------

	public static String getSearchPath() {

		String key = PersistentPreferences.SEARCH_PATH;
		String value = getString(key, "");
		if (Activator.getInstance().isDebugging()) {
			System.out.println("getSearchPath()=\"" + value + "\"");
		}
		return value;
	}

	public static String getSearchPathOs() {

		String key = EclipseUtils.getKeyOs(PersistentPreferences.SEARCH_PATH_OS);
		String value = getString(key, "");
		if (Activator.getInstance().isDebugging()) {
			System.out.println("getSearchPathOs()=\"" + value + "\" (" + key + ")");
		}
		return value;
	}

	public static void putSearchPath(String value) {

		String key = PersistentPreferences.SEARCH_PATH;

		if (Activator.getInstance().isDebugging()) {
			System.out.println("Default " + key + "=" + value);
		}
		putString(key, value);
	}

	// ------------------------------------------------------------------------
	public static final String XTENSATOOLS_PATH_DEPRECATED = "xtensa.tools";

	public static String getXtensaPath() 
	{
		String key = EclipseUtils.getKeyOs(PersistentPreferences.XTENSA_PATH_OS);
		String value = getString(key, null);
		if (value == null) {

			// TODO: remove DEPRECATED
			value = getString(PersistentPreferences.XTENSATOOLS_PATH_DEPRECATED, null);
			if (value == null) {
				value = getString(XTENSATOOLS_PATH_DEPRECATED, "");
			}
		}

		if (Activator.getInstance().isDebugging()) {
			System.out.println("getXtensaPath()=\"" + value + "\"");
		}
		return value;
	}
	public static void putXtensaPath(String value)
	{
		String key = PersistentPreferences.XTENSA_PATH;

		if (Activator.getInstance().isDebugging()) {
			System.out.println("Default " + key + "=" + value);
		}
		putString(key, value);		
	}	

	// ------------------------------------------------------------------------
	public static final String IDF_PATH_DEPRECATED = "idf.path";

	public static String getIdfPath() 
	{
		String key = EclipseUtils.getKeyOs(PersistentPreferences.IDF_PATH_OS);
		String value = getString(key, null);
		if (value == null) {

			// TODO: remove DEPRECATED
			value = getString(PersistentPreferences.IDF_PATH_DEPRECATED, null);
			if (value == null) {
				value = getString(IDF_PATH_DEPRECATED, "");
			}
		}

		if (Activator.getInstance().isDebugging()) {
			System.out.println("getIdfPath()=\"" + value + "\"");
		}
		return value;
	}
	public static void putIdfPath(String value)
	{
		String key = PersistentPreferences.IDF_PATH;

		if (Activator.getInstance().isDebugging()) {
			System.out.println("Default " + key + "=" + value);
		}
		putString(key, value);		
	}	

	// ------------------------------------------------------------------------
}
