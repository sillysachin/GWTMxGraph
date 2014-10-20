package com.appbootup.explore.gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootLayoutPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class GWTMxGraph implements EntryPoint {
	public void onModuleLoad() {
		RootLayoutPanel rootLayoutPanel = RootLayoutPanel.get();
		final GWTGraph gwtGraph = new GWTGraph();
		rootLayoutPanel.add(gwtGraph);
	}
}
