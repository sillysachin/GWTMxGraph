package com.appbootup.explore.gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.user.client.ui.RootLayoutPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class GWTMxGraph implements EntryPoint
{
	public void onModuleLoad()
	{
		RootLayoutPanel rootLayoutPanel = RootLayoutPanel.get();
		final GWTGraph gwtGraph = new GWTGraph();
		rootLayoutPanel.add( gwtGraph );
		Scheduler.get().scheduleDeferred( new ScheduledCommand()
		{
			public void execute()
			{
				gwtGraph.drawGraph( "graphContainer" );
			}
		} );
	}
}
