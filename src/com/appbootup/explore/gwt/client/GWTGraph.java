package com.appbootup.explore.gwt.client;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.LayoutPanel;

public class GWTGraph extends Composite {
	LayoutPanel divWrapper = new LayoutPanel();

	public GWTGraph() {
		setId(Document.get().createUniqueId());
		initWidget(divWrapper);
	}

	private void setId(String id) {
		divWrapper.getElement().setId(id);
	}

	private String getId() {
		return divWrapper.getElement().getId();
	}

	@Override
	protected void onLoad() {
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			public void execute() {
				drawGraph(getId());
			}
		});
	}

	public native void drawGraph(String graphContainer)/*-{
		function main(container) {
			// Checks if the browser is supported
			if (!$wnd.mxClient.isBrowserSupported()) {
				// Displays an error message if the browser is not supported.
				$wnd.mxUtils.error('Browser is not supported!', 200, false);
			} else {
				// Disables the built-in context menu
				$wnd.mxEvent.disableContextMenu(container);
				// Creates the graph inside the given container
				var graph = new $wnd.mxGraph(container);
				// Enables rubberband selection
				new $wnd.mxRubberband(graph);
				// Gets the default parent for inserting new cells. This
				// is normally the first child of the root (ie. layer 0).
				var parent = graph.getDefaultParent();
				// Adds cells to the model in a single step
				graph.getModel().beginUpdate();
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
		var element = $doc.getElementById(graphContainer);
		main(element);
	}-*/;
}