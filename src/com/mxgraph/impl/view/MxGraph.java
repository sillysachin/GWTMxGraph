package com.mxgraph.impl.view;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.mxgraph.impl.model.MxGraphModel;
import com.mxgraph.jso.IJavaScriptWrapper;
import com.mxgraph.jso.view.MxGraphJSO;

public class MxGraph extends MxEventSource implements IJavaScriptWrapper
{
	JavaScriptObject graph;

	LayoutPanel divWrapper = new LayoutPanel();

	public MxGraph( String graphContainerId )
	{
		setId( graphContainerId );
		initWidget( divWrapper );
		jso = createGraph( graphContainerId );
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
		String containerId = getId();
		if ( jso == null && containerId != null )
		{
			graph = jso = createJso( containerId );
		}
	}

	public native MxGraphJSO createGraph( String containerId )
	/*-{
		var container = $doc.getElementById(containerId);
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
		alert('This is createGraph  graph.getModel()-' + model);
		alert('This is createGraph  graph ' + graph);
		return graph;
	}-*/;

	private native JavaScriptObject createJso( String containerId ) /*-{
		var container = $doc.getElementById(containerId);
		return new $wnd.mxGraph(container);
	}-*/;

	public native Object getDefaultParent()
	/*-{
		var parent = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this)
				.getDefaultParent();
		var wrapParent = @com.mxgraph.impl.util.WrapperUtils::wrap(Lcom/google/gwt/core/client/JavaScriptObject;)(parent);
		return wrapParent;

		//var graph = this.@com.mxgraph.impl.view.MxGraph::graph;
		//return graph.getDefaultParent();
	}-*/;

	public native MxGraphModel getModel() /*-{
		var mxGraphModel = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this)
				.getModel();
		return @com.mxgraph.impl.util.WrapperUtils::wrap(Lcom/google/gwt/core/client/JavaScriptObject;)(mxGraphModel);
	}-*/;

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

		var mxCell = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this)
				.insertVertex(
						@com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(parent),
						id, value, x, y, width, height, style);
		return @com.mxgraph.impl.util.WrapperUtils::wrap(Lcom/google/gwt/core/client/JavaScriptObject;)(mxCell);

//		var vertex = this.@com.mxgraph.impl.view.MxGraph::graph.createVertex(
//				parent, id, value, x, y, width, height, style, relative);
//		var cell = this.@com.mxgraph.impl.view.MxGraph::graph.addCell(vertex,
//				parent);
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
		var mxCell = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).insertEdge(
				@com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(parent), id, value,
				@com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(source),
				@com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(target), style);
		return @com.mxgraph.impl.util.WrapperUtils::wrap(Lcom/google/gwt/core/client/JavaScriptObject;)(mxCell);
		return cell;
	}-*/;

}
