/*******************************************************************************
 * Copyright (c) 2014 Liviu Ionescu.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Liviu Ionescu - initial version
 *    Espressif Systems Ltd — ESP32 support
 *******************************************************************************/

package com.espressif.esp32.packs.core.data;

import com.espressif.esp32.packs.core.ConsoleStream;
import com.espressif.esp32.packs.core.Utils;

import org.eclipse.ui.console.MessageConsoleStream;

public class DurationMonitor {

	private int fDepth;
	private MessageConsoleStream fOut;

	public DurationMonitor() 
	{

		fDepth = 0;
		fOut = ConsoleStream.getConsoleOut();
	}

	public void displayTimeAndRun(Runnable runnable) 
	{

		fDepth++;
		long beginTime = System.currentTimeMillis();

		if (fDepth == 1) {
			fOut.println();
			fOut.println(Utils.getCurrentDateTime());
		}

		runnable.run();

		long endTime = System.currentTimeMillis();
		long duration = endTime - beginTime;
		if (duration == 0) {
			duration = 1;
		}
		fOut.print("Completed in ");
		fOut.println(duration + "ms.");

		fDepth--;

	}

}
