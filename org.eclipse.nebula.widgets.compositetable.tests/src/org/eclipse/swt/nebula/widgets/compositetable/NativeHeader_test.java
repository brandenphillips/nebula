/*******************************************************************************
 * Copyright (c) 2009 compeople AG and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     compeople AG - initial API and implementation
 ******************************************************************************/
package org.eclipse.swt.nebula.widgets.compositetable;

import junit.framework.TestCase;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class NativeHeader_test extends TestCase {

    private static class Header extends AbstractNativeHeader {
	public Header(Composite parent, int style) {
	    super(parent, style);
	    setWeights(new int[] { 100, 200 });
	    setColumnText(new String[] { "A", "B" });
	}
    }

    private Display display;
    private Shell shell;

    @Override
    protected void setUp() throws Exception {
	display = Display.getDefault();
	shell = new Shell(display);
	shell.setLayout(new FillLayout());
    }

    @Override
    protected void tearDown() throws Exception {
	shell.dispose();
	display.dispose();
    }

    // test methods
    // /////////////

    public void testGetColumns() {
	Header header = new Header(shell, SWT.NONE);

	assertEquals(2, header.getColumns().length);
    }

    public void testSetSortColumn() {
	Header header = new Header(shell, SWT.NONE);

	assertEquals(-1, header.indexOfSortColumn());

	header.setSortColumn(0);

	assertEquals(0, header.indexOfSortColumn());

	header.setSortColumn(1);

	assertEquals(1, header.indexOfSortColumn());

	header.setSortColumn(-1);

	assertEquals(-1, header.indexOfSortColumn());

	try {
	    header.setSortColumn(42);
	    fail();
	} catch (RuntimeException rex) {
	    // expected
	}
    }

    public void testSetSortDirection() {
	Header header = new Header(shell, SWT.NONE);

	assertEquals(SWT.NONE, header.getSortDirection());

	int[] values = { SWT.UP, SWT.DOWN, SWT.NONE };
	for (int value : values) {
	    header.setSortDirection(value);

	    assertEquals(value, header.getSortDirection());
	}

	try {
	    header.setSortDirection(-1);
	    fail();
	} catch (RuntimeException rex) {
	    // expected
	}
    }

}
