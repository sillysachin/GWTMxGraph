package com.appbootup.explore.gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.mxgraph.impl.model.MxGraphModel;
import com.mxgraph.impl.view.MxGraph;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class GWTMxGraph implements EntryPoint {
	public void onModuleLoad() {
		RootLayoutPanel rootLayoutPanel = RootLayoutPanel.get();
		final GWTGraph gwtGraph = new GWTGraph();
		rootLayoutPanel.add(gwtGraph);
		MxGraph mxGraph = new MxGraph();
		rootLayoutPanel.add(mxGraph);
		Object parent = mxGraph.getDefaultParent();
		MxGraphModel model = mxGraph.getModel();
		//model.beginUpdate();
		Object v1 = mxGraph.insertVertex(parent, null, "Hell,", 40, 40, 160, 60);
		Object v2 = mxGraph.insertVertex(parent, null, "Word!", 400, 300, 160, 60);
		Object e1 = mxGraph.insertEdge(parent, null, "", v1, v2);
		//model.endUpdate();
	}
}