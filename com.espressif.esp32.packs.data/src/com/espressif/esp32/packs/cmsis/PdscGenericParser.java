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

package com.espressif.esp32.packs.cmsis;

import com.espressif.esp32.packs.core.data.GenericParser;
import com.espressif.esp32.packs.core.tree.Leaf;

/**
 * Specific implementation of the generic parser used to convert the messy PDSC
 * files into a more regular and compact representation.
 */
public class PdscGenericParser extends GenericParser {

	public PdscGenericParser() {
		;
	}

	/**
	 * Configure all elements that generate properties, mainly <description> for
	 * a list of nodes, and a few ones for <package> nodes, are automatically
	 * identified by the generic parser, no need to define them explicitly.
	 */
	@Override
	public boolean isProperty(String name, Leaf node) {
		return false;
	}
}
