package com.mxgraph.impl.view;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.mxgraph.impl.model.MxGraphModel;
import com.mxgraph.jso.view.MxGraphJSO;

public class MxGraph extends Composite
{
	MxGraphJSO mxGraphJSO;

	JavaScriptObject graph;

	MxGraphModel model = new MxGraphModel();

	LayoutPanel divWrapper = new LayoutPanel();

	public MxGraph( String graphContainerId )
	{
		setId( graphContainerId );
		initWidget( divWrapper );
		mxGraphJSO = createGraph( graphContainerId );
	}

	public MxGraph()
	{
		setId( Document.get().createUniqueId() );
		initWidget( divWrapper );
	}

	private void setId( String id )
	{
		divWrapper.getElement().setId( id );
	}

	public String getId()
	{
		return divWrapper.getElement().getId();
	}

	@Override
	protected void onLoad()
	{
		String id = getId();
		if ( mxGraphJSO == null && id != null )
		{
			mxGraphJSO = createGraph( id );
		}
	}

	public native MxGraphJSO createGraph( String graphContainer )
	/*-{
		var container = $doc.getElementById(graphContainer);
		var graph = null;
		// Checks if the browser is supported
		if (!$wnd.mxClient.isBrowserSupported()) {
			// Displays an error message if the browser is not supported.
			$wnd.mxUtils.error('Browser is not supported!', 200, false);
		} else {
			// Disables the built-in context menu
			$wnd.mxEvent.disableContextMenu(container);
			// Creates the graph inside the given container
			graph = new $wnd.mxGraph(container);
		}
		this.@com.mxgraph.impl.view.MxGraph::graph = graph;
		//this.@com.mxgraph.impl.view.MxGraph::model.setModel(graph.getModel());
		var model = graph.getModel();
		alert('This is createGraph  graph.getModel()-'+model);
		alert('This is createGraph  graph '+graph);
		return graph;
	}-*/;

	public native Object getDefaultParent()
	/*-{
		var graph = this.@com.mxgraph.impl.view.MxGraph::graph;
		alert('This is getDefaultParent - '+graph);
		return graph.getDefaultParent();
	}-*/;

	public MxGraphModel getModel()
	{
		return this.model;
	}

	/**
	 * Creates and adds a new vertex with an empty style.
	 */
	public Object insertVertex( Object parent, String id, Object value, double x, double y, double width, double height )
	{
		return insertVertex( parent, id, value, x, y, width, height, null );
	}

	/**
	 * Adds a new vertex into the given parent using value as the user object and the given coordinates as the geometry of the new vertex. The id and style are used for the respective properties of the new cell, which is returned.
	 *
	 * @param parent
	 *            Cell that specifies the parent of the new vertex.
	 * @param id
	 *            Optional string that defines the Id of the new vertex.
	 * @param value
	 *            Object to be used as the user object.
	 * @param x
	 *            Integer that defines the x coordinate of the vertex.
	 * @param y
	 *            Integer that defines the y coordinate of the vertex.
	 * @param width
	 *            Integer that defines the width of the vertex.
	 * @param height
	 *            Integer that defines the height of the vertex.
	 * @param style
	 *            Optional string that defines the cell style.
	 * @return Returns the new vertex that has been inserted.
	 */
	public Object insertVertex( Object parent, String id, Object value, double x, double y, double width, double height, String style )
	{
		return insertVertex( parent, id, value, x, y, width, height, style, false );
	}

	/**
	 * Adds a new vertex into the given parent using value as the user object and the given coordinates as the geometry of the new vertex. The id and style are used for the respective properties of the new cell, which is returned.
	 *
	 * @param parent
	 *            Cell that specifies the parent of the new vertex.
	 * @param id
	 *            Optional string that defines the Id of the new vertex.
	 * @param value
	 *            Object to be used as the user object.
	 * @param x
	 *            Integer that defines the x coordinate of the vertex.
	 * @param y
	 *            Integer that defines the y coordinate of the vertex.
	 * @param width
	 *            Integer that defines the width of the vertex.
	 * @param height
	 *            Integer that defines the height of the vertex.
	 * @param style
	 *            Optional string that defines the cell style.
	 * @param relative
	 *            Specifies if the geometry should be relative.
	 * @return Returns the new vertex that has been inserted.
	 */
	public native Object insertVertex( Object parent, String id, Object value, double x, double y, double width, double height, String style, boolean relative )
	/*-{
		var vertex = this.@com.mxgraph.impl.view.MxGraph::graph.createVertex(
				parent, id, value, x, y, width, height, style, relative);
		var cell = this.@com.mxgraph.impl.view.MxGraph::graph.addCell(vertex,
				parent);
		alert('This is insertVertex - ' + cell);
		return cell;

	}-*/;

	/**
	 * Creates and adds a new edge with an empty style.
	 */
	public Object insertEdge( Object parent, String id, Object value, Object source, Object target )
	{
		return insertEdge( parent, id, value, source, target, null );
	}

	/**
	 * Adds a new edge into the given parent using value as the user object and the given source and target as the terminals of the new edge. The Id and style are used for the respective properties of the new cell, which is returned.
	 *
	 * @param parent
	 *            Cell that specifies the parent of the new edge.
	 * @param id
	 *            Optional string that defines the Id of the new edge.
	 * @param value
	 *            Object to be used as the user object.
	 * @param source
	 *            Cell that defines the source of the edge.
	 * @param target
	 *            Cell that defines the target of the edge.
	 * @param style
	 *            Optional string that defines the cell style.
	 * @return Returns the new edge that has been inserted.
	 */
	public native Object insertEdge( Object parent, String id, Object value, Object source, Object target, String style )
	/*-{
		var edge = this.@com.mxgraph.impl.view.MxGraph::graph.createEdge(
				parent, id, value, source, target, style);
		var cell = this.@com.mxgraph.impl.view.MxGraph::graph.addEdge(edge,
				parent, source, target, null)
		alert('This is insertEdge - ' + cell);
		return cell;
	}-*/;

}
