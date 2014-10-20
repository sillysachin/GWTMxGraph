package com.appbootup.explore.gwt.client;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.LayoutPanel;

public class GWTGraph extends Composite
{
	LayoutPanel divWrapper = new LayoutPanel();

	public GWTGraph()
	{
		divWrapper.getElement().setId( "graphContainer" );
		initWidget( divWrapper );
	}

	public native void drawGraph( String graphContainer )/*-{
		function main(container) {
			// Checks if the browser is supported
			if (!$wnd.mxClient.isBrowserSupported()) {
				// Displays an error message if the browser is not supported.
				$wnd.mxUtils.error('Browser is not supported!', 200, false);
			} else {
				alert('0');
				// Disables the built-in context menu
				$wnd.mxEvent.disableContextMenu(container);
				alert('1');
				// Creates the graph inside the given container
				var graph = new $wnd.mxGraph(container);
				alert('2');
				// Enables rubberband selection
				new $wnd.mxRubberband(graph);
				alert('3');
				// Gets the default parent for inserting new cells. This
				// is normally the first child of the root (ie. layer 0).
				var parent = graph.getDefaultParent();
				alert('4');
				// Adds cells to the model in a single step
				graph.getModel().beginUpdate();
				alert('5');
				try {
					var v1 = graph.insertVertex(parent, null, 'Hello,', 20, 20,
							80, 30);
					var v2 = graph.insertVertex(parent, null, 'World!', 200,
							150, 80, 30);
					var e1 = graph.insertEdge(parent, null, '', v1, v2);
				} finally {
					// Updates the display
					graph.getModel().endUpdate();
				}
			}
		}
		var element = document.getElementById('htmlGraphContainer');
		alert(element);
		main(element);
	}-*/;
}