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

package com.espressif.esp32.managedbuild.cross.properties;

import com.espressif.esp32.core.EclipseUtils;
import com.espressif.esp32.core.ScopedPreferenceStoreWithoutDefaults;
import com.espressif.esp32.core.preferences.DirectoryNotStrictFieldEditor;
import com.espressif.esp32.core.preferences.LabelFakeFieldEditor;
import com.espressif.esp32.core.ui.FieldEditorPropertyPage;
import com.espressif.esp32.managedbuild.cross.Activator;
import com.espressif.esp32.managedbuild.cross.Option;
import com.espressif.esp32.managedbuild.cross.ui.DefaultPreferences;
import com.espressif.esp32.managedbuild.cross.ui.ProjectStorage;
import com.espressif.esp32.managedbuild.cross.ui.Messages;
import com.espressif.esp32.managedbuild.cross.ui.PersistentPreferences;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.cdt.managedbuilder.core.BuildException;
import org.eclipse.cdt.managedbuilder.core.IConfiguration;
import org.eclipse.cdt.managedbuilder.core.IOption;
import org.eclipse.cdt.managedbuilder.core.IToolChain;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.preference.IPreferenceStore;


public class ProjectToolsPathPropertyPage extends FieldEditorPropertyPage {

	// ------------------------------------------------------------------------

	public static final String ID = "com.espressif.esp32.managedbuild.cross.properties.toolsPage";

	// ------------------------------------------------------------------------

	public ProjectToolsPathPropertyPage() {
		super(GRID);

//		setPreferenceStore(new ScopedPreferenceStore(ConfigurationScope.INSTANCE, Activator.PLUGIN_ID));
//		setPreferenceStore(new ScopedPreferenceStoreWithoutDefaults(
//				InstanceScope.INSTANCE, Activator.PLUGIN_ID));
//		setPreferenceStore(new ScopedPreferenceStoreWithoutDefaults(
//		InstanceScope.INSTANCE, Activator.PLUGIN_ID));

		setDescription(Messages.ProjectToolsPathsPropertyPage_description);

		// setPreferenceStore(com.espressif.esp32.packs.core.Preferences.getPreferenceStore());
		//doGetPreferenceStore();
	}

	// ------------------------------------------------------------------------

	protected IPreferenceStore doGetPreferenceStore() {

		Object element = getElement();
		if (element instanceof IProject) {
			return new ScopedPreferenceStoreWithoutDefaults(new ProjectScope((IProject) element), Activator.PLUGIN_ID);
		}
		return null;
	}

	@Override
	protected void createFieldEditors() {
		boolean isStrict = DefaultPreferences.getBoolean(PersistentPreferences.PROJECT_BUILDTOOLS_PATH_STRICT, true);
		// FieldEditor buildToolsPathField = new DirectoryNotStrictFieldEditor(PersistentPreferences.BUILD_TOOLS_PATH_KEY,
		// 		Messages.ToolsPaths_label, getFieldEditorParent(), isStrict);
		// addField(buildToolsPathField);

		Set<String> toolchainNames = new HashSet<String>();
		IConfiguration main_config = null;
		Object element = getElement();
		if (element instanceof IProject) {
			// TODO: get project toolchain name. How?
			IProject project = (IProject) element;
			IConfiguration[] configs = EclipseUtils.getConfigurationsForProject(project);
			if (configs != null) {
				for (int i = 0; i < configs.length; ++i) {
					IToolChain toolchain = configs[i].getToolChain();
					if (toolchain == null) {
						continue;
					}
					IOption option = toolchain.getOptionBySuperClassId(Option.OPTION_TOOLCHAIN_NAME);
					if (option == null) {
						continue;
					}
					try {
						String name = option.getStringValue();
						if (!name.isEmpty()) {
							toolchainNames.add(name);
							main_config = configs[i];
						}
					} catch (BuildException e) {
						;
					}
				}
			}
		}
		if (toolchainNames.isEmpty()) {
			toolchainNames.add(PersistentPreferences.getToolchainName());
		}

		for (String toolchainName : toolchainNames) {

			FieldEditor labelField = new LabelFakeFieldEditor(toolchainName, Messages.ToolsPaths_ToolchainName_label, getFieldEditorParent());
			addField(labelField);

			isStrict = DefaultPreferences.getBoolean(PersistentPreferences.PROJECT_TOOLCHAIN_PATH_STRICT, true);
			String key = PersistentPreferences.getToolchainKey(toolchainName);
			IProject project = null;
			if (main_config != null) project = (IProject) main_config.getManagedProject().getOwner();
			String tools_Path = PersistentPreferences.getToolchainPath(toolchainName, project);
            String key_Path = getPreferenceStore().getString(key);
			if (key_Path == "") 
			{
				getPreferenceStore().setValue(key, tools_Path);
			}
			FieldEditor toolchainPathField = new DirectoryNotStrictFieldEditor(key, Messages.ToolchainPaths_label, getFieldEditorParent(), isStrict);
			addField(toolchainPathField);
			
			String key_idf = PersistentPreferences.getToolchainIdfKey(toolchainName);
			
			String tools_IdfPath = PersistentPreferences.getToolchainIdfPath(toolchainName, project);
            String key_IdfPath = getPreferenceStore().getString(key_idf);
			if (key_IdfPath == "") 
			{
				getPreferenceStore().setValue(key_idf, tools_IdfPath);
			}
			
			FieldEditor toolchainIdfPathField = new DirectoryNotStrictFieldEditor(key_idf, Messages.ToolchainIdfPaths_label,
					getFieldEditorParent(), isStrict);
			addField(toolchainIdfPathField);
		}
		//doGetPreferenceStore();
	}

	// ------------------------------------------------------------------------
}
