/*******************************************************************************
 * Copyright (c) 2009, 2013 Wind River Systems, Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Doug Schaefer - initial API and implementation
 *     Liviu Ionescu - ARM version
 *     Espressif Systems Ltd — ESP32 support
 *******************************************************************************/

package ilg.esp32_eclipse.managedbuild.cross;

import ilg.esp32_eclipse.core.EclipseUtils;
import ilg.esp32_eclipse.managedbuild.cross.ui.ProjectStorage;
import ilg.esp32_eclipse.managedbuild.cross.ui.PersistentPreferences;

import java.io.File;

import org.eclipse.cdt.core.CCorePlugin;
import org.eclipse.cdt.core.envvar.IEnvironmentVariable;
import org.eclipse.cdt.core.settings.model.ICConfigurationDescription;
//import org.eclipse.cdt.internal.core.Cygwin;
import org.eclipse.cdt.managedbuilder.core.IConfiguration;
import org.eclipse.cdt.managedbuilder.envvar.IBuildEnvironmentVariable;
import org.eclipse.cdt.managedbuilder.envvar.IConfigurationEnvironmentVariableSupplier;
import org.eclipse.cdt.managedbuilder.envvar.IEnvironmentVariableProvider;
import org.eclipse.cdt.managedbuilder.internal.envvar.BuildEnvVar;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Path;

/**
 * @noextend This class is not intended to be subclassed by clients.
 */
public class EnvironmentVariableSupplier implements IConfigurationEnvironmentVariableSupplier {
	private static final String ENV_PATH = "PATH"; //$NON-NLS-1$
	private static final String ENV_LANG = "LANG"; //$NON-NLS-1$
	private static final String ENV_LC_ALL = "LC_ALL"; //$NON-NLS-1$
	private static final String ENV_LC_MESSAGES = "LC_MESSAGES"; //$NON-NLS-1$

	private static final String PROPERTY_OSNAME = "os.name"; //$NON-NLS-1$
	private static final String BACKSLASH = java.io.File.separator;

	private static final String ENV_CYGWIN_HOME = "TOOLCHAIN_HOME_PATH";
	private static final String ENV_BATCH_BUILD = "BATCH_BUILD";
	private static final String ENV_IDF_PATH = "IDF_PATH";
	
	@Override
	public IBuildEnvironmentVariable getVariable(String variableName, IConfiguration configuration, IEnvironmentVariableProvider provider) {
		if (variableName == null) {
			return null;
		}

		if (!System.getProperty(PROPERTY_OSNAME).toLowerCase().startsWith("windows ")) { //$NON-NLS-1$
			return null;
		}

		if (variableName.equalsIgnoreCase(ENV_PATH)) {
			@SuppressWarnings("nls")
			// Add MSys32 path to the main path....C:\\msys32\\usr\bin;C:\\msys32\mingw32\\bin;C:\\msys32\\opt\\xtensa-esp32-elf\\bin
			String path = "";
			 path += "${" + ENV_CYGWIN_HOME + "}" + "\\usr\\bin;";
			 path += "${" + ENV_CYGWIN_HOME + "}" + "\\mingw32\\bin;";
			 path += "${" + ENV_CYGWIN_HOME + "}" + "\\opt\\xtensa-esp32-elf\\bin;";

			return new BuildEnvVar(ENV_PATH, path, IBuildEnvironmentVariable.ENVVAR_PREPEND);
		} else if (variableName.equals(ENV_CYGWIN_HOME)) {
			IEnvironmentVariable varCygwincppHome = CCorePlugin.getDefault().getBuildEnvironmentManager().getVariable(ENV_CYGWIN_HOME, (ICConfigurationDescription) null, false);
			if (varCygwincppHome == null) {
				String fSelectedToolchainName = PersistentPreferences.getToolchainName();
				IProject project = (IProject) configuration.getManagedProject().getOwner();
				String cross_Path = PersistentPreferences.getToolchainPath(fSelectedToolchainName, project);
				String project_Path = ProjectStorage.getToolchainPath(configuration);
				if (project_Path == "") project_Path = cross_Path;
				String home = project_Path;
				if (home == null) {
					// If the variable is not defined still show it in the environment variables list as a hint to user
					home = ""; //$NON-NLS-1$
				}
				return new BuildEnvVar(ENV_CYGWIN_HOME, new Path(home).toOSString());
			}
			return null;

		} else if (variableName.equalsIgnoreCase(ENV_LANG)) {
			// Workaround for not being able to select encoding for CDT console -> change codeset to Latin1
			String langValue = System.getenv(ENV_LANG);
			if (langValue == null || langValue.length() == 0) {
				langValue = System.getenv(ENV_LC_ALL);
			}
			if (langValue == null || langValue.length() == 0) {
				langValue = System.getenv(ENV_LC_MESSAGES);
			}
			if (langValue != null && langValue.length() > 0) {
				// langValue is [language[_territory][.codeset][@modifier]], i.e. "en_US.UTF-8@dict"
				// we replace codeset with Latin1 as CDT console garbles UTF
				// and ignore modifier which is not used by LANG
				langValue = langValue.replaceFirst("([^.@]*)(\\..*)?(@.*)?", "$1.ISO-8859-1"); //$NON-NLS-1$ //$NON-NLS-2$
			} else {
				langValue = "C.ISO-8859-1"; //$NON-NLS-1$
			}
			return new BuildEnvVar(ENV_LANG, langValue);
		} 
		else if (variableName.equals(ENV_BATCH_BUILD)) 
		{	
			String bbuildValue = "1";
			return new BuildEnvVar(ENV_BATCH_BUILD, bbuildValue);
		}
		else if (variableName.equals(ENV_IDF_PATH)) 
		{	
			String fSelectedToolchainName = PersistentPreferences.getToolchainName();
			IProject project = (IProject) configuration.getManagedProject().getOwner();
			String cross_IdfPath = PersistentPreferences.getToolchainIdfPath(fSelectedToolchainName, project);
			String project_IdfPath = ProjectStorage.getToolchainIdfPath(configuration);
			if (project_IdfPath == "") project_IdfPath = cross_IdfPath;
			return new BuildEnvVar(ENV_IDF_PATH, project_IdfPath);
		}
		return null;
	}

	@Override
	public IBuildEnvironmentVariable[] getVariables(IConfiguration configuration, IEnvironmentVariableProvider provider) {
		IBuildEnvironmentVariable varHome = getVariable(ENV_CYGWIN_HOME, configuration, provider);
		IBuildEnvironmentVariable varLang = getVariable(ENV_LANG, configuration, provider);
		IBuildEnvironmentVariable varPath = getVariable(ENV_PATH, configuration, provider);
		IBuildEnvironmentVariable varBatch = getVariable(ENV_BATCH_BUILD, configuration, provider);
		IBuildEnvironmentVariable varIdfPath = getVariable(ENV_IDF_PATH, configuration, provider);

		return new IBuildEnvironmentVariable[] {varHome, varLang, varPath, varBatch, varIdfPath};
	}
}
