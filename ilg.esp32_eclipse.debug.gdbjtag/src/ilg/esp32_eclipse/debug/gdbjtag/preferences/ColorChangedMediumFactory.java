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

package ilg.esp32_eclipse.debug.gdbjtag.preferences;

import ilg.esp32_eclipse.debug.gdbjtag.PeripheralsColorFactory;
import ilg.esp32_eclipse.debug.gdbjtag.PersistentPreferences;
import ilg.esp32_eclipse.debug.gdbjtag.render.peripheral.PeripheralColumnLabelProvider;

public class ColorChangedMediumFactory extends PeripheralsColorFactory {

	public ColorChangedMediumFactory() {
		super(PeripheralColumnLabelProvider.COLOR_CHANGED_MEDIUM,
				PersistentPreferences.PERIPHERALS_COLOR_CHANGED_MEDIUM);
	}
}
