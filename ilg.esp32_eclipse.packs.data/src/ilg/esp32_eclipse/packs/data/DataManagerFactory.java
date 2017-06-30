/*******************************************************************************
 * Copyright (c) 2014 Liviu Ionescu.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Liviu Ionescu - initial implementation.
 *     Espressif Systems Ltd — ESP32 support
 *******************************************************************************/

package ilg.esp32_eclipse.packs.data;

import ilg.esp32_eclipse.packs.core.data.IPacksDataManager;
import ilg.esp32_eclipse.packs.core.data.IPacksDataManagerFactory;

public class DataManagerFactory implements IPacksDataManagerFactory {

	public DataManagerFactory() {
		;
	}

	@Override
	public IPacksDataManager createDataManager() {

		// Return the static manager object
		return DataManager.getInstance();
	}

	@Override
	public void dispose() {
		;
	}

}
