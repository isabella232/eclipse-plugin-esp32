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

package ilg.esp32_eclipse.managedbuild.cross.properties;

import ilg.esp32_eclipse.core.EclipseUtils;
import ilg.esp32_eclipse.core.ScopedPreferenceStoreWithoutDefaults;
import ilg.esp32_eclipse.core.preferences.DirectoryNotStrictFieldEditor;
import ilg.esp32_eclipse.core.preferences.LabelFakeFieldEditor;
import ilg.esp32_eclipse.core.ui.FieldEditorPropertyPage;
import ilg.esp32_eclipse.managedbuild.cross.Activator;
import ilg.esp32_eclipse.managedbuild.cross.Option;
import ilg.esp32_eclipse.managedbuild.cross.ui.DefaultPreferences;
import ilg.esp32_eclipse.managedbuild.cross.ui.Messages;
import ilg.esp32_eclipse.managedbuild.cross.ui.PersistentPreferences;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.cdt.managedbuilder.core.BuildException;
import org.eclipse.cdt.managedbuilder.core.IConfiguration;
import org.eclipse.cdt.managedbuilder.core.IOption;
import org.eclipse.cdt.managedbuilder.core.IToolChain;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.preference.IPreferenceStore;

public class ProjectToolsPathPropertyPage extends FieldEditorPropertyPage {

	// ------------------------------------------------------------------------

	public static final String ID = "ilg.esp32_eclipse.managedbuild.cross.properties.toolsPage";

	// ------------------------------------------------------------------------

	public ProjectToolsPathPropertyPage() {
		super(GRID);

		setDescription(Messages.ProjectToolsPathsPropertyPage_description);
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
		FieldEditor buildToolsPathField = new DirectoryNotStrictFieldEditor(PersistentPreferences.BUILD_TOOLS_PATH_KEY,
				Messages.ToolsPaths_label, getFieldEditorParent(), isStrict);
		addField(buildToolsPathField);

		Set<String> toolchainNames = new HashSet<String>();

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
			FieldEditor toolchainPathField = new DirectoryNotStrictFieldEditor(key, Messages.ToolchainPaths_label, getFieldEditorParent(), isStrict);
			addField(toolchainPathField);

			FieldEditor toolchainIdfPathField = new DirectoryNotStrictFieldEditor(key, Messages.ToolchainIdfPaths_label,
					getFieldEditorParent(), isStrict);
			addField(toolchainIdfPathField);
		}
	}

	// ------------------------------------------------------------------------
}
