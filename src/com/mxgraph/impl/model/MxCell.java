package com.mxgraph.impl.model;

import java.io.Serializable;

import com.google.gwt.core.client.JavaScriptObject;
import com.mxgraph.jso.MxICell;

/**
 * Cells are the elements of the graph model. They represent the state of the groups, vertices and edges in a graph.
 *
 * <h4>Edge Labels</h4>
 *
 * Using the x- and y-coordinates of a cell's geometry it is possible to position the label on edges on a specific location on the actual edge shape as it
 * appears on the screen. The x-coordinate of an edge's geometry is used to describe the distance from the center of the edge from -1 to 1 with 0 being the
 * center of the edge and the default value. The y-coordinate of an edge's geometry is used to describe the absolute, orthogonal distance in pixels from that
 * point. In addition, the mxGeometry.offset is used as a absolute offset vector from the resulting point.
 *
 * The width and height of an edge geometry are ignored.
 *
 * To add more than one edge label, add a child vertex with a relative geometry. The x- and y-coordinates of that geometry will have the same semantic as the
 * above for edge labels.
 */
public class MxCell implements MxICell, Cloneable, Serializable {

	private static final long serialVersionUID = -3835896565174093540L;

	private JavaScriptObject jso;

	@Override public JavaScriptObject getJso() {
		return jso;
	}

	@Override public void setJso(JavaScriptObject jso) {
		this.jso = jso;
	}

	public MxCell() {
		jso = createJso(null, null, null);
	}

	private native JavaScriptObject createJso(Object value, JavaScriptObject geometry, String style) /*-{
		return new $wnd.mxCell(value, geometry, style);
	}-*/;

	protected MxCell(JavaScriptObject jso) {
		this.jso = jso;
	}

	//	public MxCell(Object value, MxGeometry geometry, String style) {
	//		jso = createJso(value, geometry.getJso(), style);
	//	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.mxgraph.gwt.client.model.MxICell#getId()
	 */
	@Override public native String getId() /*-{
		return @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).getId();
	}-*/;

	/*
	 * (non-Javadoc)
	 *
	 * @see com.mxgraph.gwt.client.model.MxICell#setId(java.lang.String)
	 */
	@Override public native void setId(String id) /*-{
		@com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).setId(id);
	}-*/;

	@Override public native Object getValue() /*-{
		return @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).getValue();
	}-*/;

	@Override public native void setValue(Object value) /*-{
		@com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).setValue(value);
	}-*/;

	/**
	 * Changes the user object after an in-place edit and returns the previous value. This implementation replaces the user object with the given value and
	 * returns the old user object.
	 *
	 * @param newValue new value for user object
	 * @return old user object
	 */
	public native Object valueChanged(Object newValue) /*-{
		return @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).valueChanged(newValue);
	}-*/;

	//	@Override public native mxGeometry getGeometry() /*-{
	//		var geometryJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).getGeometry();
	//		return @com.mxgraph.impl.util.WrapperUtils::wrap(Lcom/google/gwt/core/client/JavaScriptObject;)(geometryJS);
	//	}-*/;
	//
	//	@Override public native void setGeometry(mxGeometry geometry) /*-{
	//		var geometryJs = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(geometry);
	//		@com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).setGeometry(geometryJs);
	//	}-*/;

	/*
	 * (non-Javadoc)
	 *
	 * @see com.mxgraph.gwt.client.model.MxICell#getStyle()
	 */
	@Override public native String getStyle() /*-{
		return @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).getStyle();
	}-*/;

	/*
	 * (non-Javadoc)
	 *
	 * @see com.mxgraph.gwt.client.model.MxICell#setStyle(java.lang.String)
	 */
	@Override public native void setStyle(String style) /*-{
		@com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).setStyle(style);
	}-*/;

	/*
	 * (non-Javadoc)
	 *
	 * @see com.mxgraph.gwt.client.model.MxICell#isVertex()
	 */
	@Override public native boolean isVertex() /*-{
		var retvalJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).isVertex();
		return typeof retvalJS == "boolean" ? retvalJS : retvalJS == 0 ? false : true;
	}-*/;

	/*
	 * (non-Javadoc)
	 *
	 * @see com.mxgraph.gwt.client.model.MxICell#setVertex(boolean)
	 */
	public native void setVertex(boolean isVertex) /*-{
		@com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).setVertex(isVertex);
	}-*/;

	/*
	 * (non-Javadoc)
	 *
	 * @see com.mxgraph.gwt.client.model.MxICell#isEdge()
	 */
	@Override public native boolean isEdge() /*-{
		return @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).isEdge();
	}-*/;

	/*
	 * (non-Javadoc)
	 *
	 * @see com.mxgraph.gwt.client.model.MxICell#setEdge(boolean)
	 */
	public native void setEdge(boolean edge) /*-{
		return @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).setEdge(edge);
	}-*/;

	/*
	 * (non-Javadoc)
	 *
	 * @see com.mxgraph.gwt.client.model.MxICell#isConnectable()
	 */
	@Override public native boolean isConnectable() /*-{
		return @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).isConnectable();
	}-*/;

	/*
	 * (non-Javadoc)
	 *
	 * @see com.mxgraph.gwt.client.model.MxICell#setConnectable(boolean)
	 */
	@Override public native void setConnectable(boolean connectable) /*-{
		@com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).setConnectable(connectable);
	}-*/;

	/*
	 * (non-Javadoc)
	 *
	 * @see com.mxgraph.gwt.client.model.MxICell#isVisible()
	 */
	@Override public native boolean isVisible() /*-{
		return @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).isVisible();
	}-*/;

	/*
	 * (non-Javadoc)
	 *
	 * @see com.mxgraph.gwt.client.model.MxICell#setVisible(boolean)
	 */
	@Override public native void setVisible(boolean visible) /*-{
		@com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).setVisible(visible);
	}-*/;

	/*
	 * (non-Javadoc)
	 *
	 * @see com.mxgraph.gwt.client.model.MxICell#isCollapsed()
	 */
	@Override public native boolean isCollapsed() /*-{
		return @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).isCollapsed();
	}-*/;

	/*
	 * (non-Javadoc)
	 *
	 * @see com.mxgraph.gwt.client.model.MxICell#setCollapsed(boolean)
	 */
	@Override public native void setCollapsed(boolean collapsed) /*-{
		@com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).setCollapsed(collapsed);
	}-*/;

	/*
	 * (non-Javadoc)
	 *
	 * @see com.mxgraph.gwt.client.model.MxICell#getParent()
	 */
	@Override public native MxICell getParent() /*-{
		var parentJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).getParent();
		return new @com.mxgraph.impl.model.MxCell::new(Lcom/google/gwt/core/client/JavaScriptObject;)(parentJS);
	}-*/;

	/*
	 * (non-Javadoc)
	 *
	 * @see com.mxgraph.gwt.client.model.MxICell#setParent(com.mxgraph.gwt.client.model.MxICell)
	 */
	@Override public native void setParent(MxICell parent) /*-{
		var parentJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(parent);
		@com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).setParent(parentJS);
	}-*/;

	/*
	 * (non-Javadoc)
	 *
	 * @see com.mxgraph.gwt.client.model.MxICell#getTerminal(boolean)
	 */
	@Override public native MxICell getTerminal(boolean source) /*-{
		var terminalJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).getTerminal(source);
		return new @com.mxgraph.impl.model.MxCell::new(Lcom/google/gwt/core/client/JavaScriptObject;)(terminalJS);
	}-*/;

	/*
	 * (non-Javadoc)
	 *
	 * @see com.mxgraph.gwt.client.model.MxICell#setTerminal(com.mxgraph.gwt.client.model.MxICell, boolean)
	 */
	@Override public native MxICell setTerminal(MxICell terminal, boolean isSource) /*-{
		var terminalJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).setTerminal(terminal, isSource);
		return new @com.mxgraph.impl.model.MxCell::new(Lcom/google/gwt/core/client/JavaScriptObject;)(terminalJS);
	}-*/;

	/*
	 * (non-Javadoc)
	 *
	 * @see com.mxgraph.gwt.client.model.MxICell#getChildCount()
	 */
	@Override public native int getChildCount() /*-{
		return @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).getChildCount();
	}-*/;

	/*
	 * (non-Javadoc)
	 *
	 * @see com.mxgraph.gwt.client.model.MxICell#getIndex(com.mxgraph.gwt.client.model.MxICell)
	 */
	@Override public native int getIndex(MxICell child) /*-{
		var childJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(child);
		return @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).getIndex(childJS);
	}-*/;

	/*
	 * (non-Javadoc)
	 *
	 * @see com.mxgraph.gwt.client.model.MxICell#getChildAt(int)
	 */
	@Override public native MxICell getChildAt(int index) /*-{
		var childJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).getChildAt(index);
		return new @com.mxgraph.impl.model.MxCell::new(Lcom/google/gwt/core/client/JavaScriptObject;)(childJS);
	}-*/;

	/*
	 * (non-Javadoc)
	 *
	 * @see com.mxgraph.gwt.client.model.MxICell#insert(com.mxgraph.gwt.client.model.MxICell)
	 */
	@Override public native MxICell insert(MxICell child) /*-{
		var childJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(child);
		var insertedChildJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).insert(childJS);
		return new @com.mxgraph.impl.model.MxCell::new(Lcom/google/gwt/core/client/JavaScriptObject;)(insertedChildJS);
	}-*/;

	/*
	 * (non-Javadoc)
	 *
	 * @see com.mxgraph.gwt.client.model.MxICell#insert(com.mxgraph.gwt.client.model.MxICell, int)
	 */
	@Override public native MxICell insert(MxICell child, int index) /*-{
		var childJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(child);
		var insertedChildJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).insert(childJS, index);
		return new @com.mxgraph.impl.model.MxCell::new(Lcom/google/gwt/core/client/JavaScriptObject;)(insertedChildJS);
	}-*/;

	/*
	 * (non-Javadoc)
	 *
	 * @see com.mxgraph.gwt.client.model.MxICell#remove(int)
	 */
	@Override public native MxICell remove(int index) /*-{
		var removedJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).remove(index);
		return @com.mxgraph.impl.util.WrapperUtils::wrap(Lcom/google/gwt/core/client/JavaScriptObject;)(removedJS)
	}-*/;

	/*
	 * (non-Javadoc)
	 *
	 * @see com.mxgraph.gwt.client.model.MxICell#removeFromParent()
	 */
	@Override public native void removeFromParent() /*-{
		@com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).removeFromParent();
	}-*/;

	/*
	 * (non-Javadoc)
	 *
	 * @see com.mxgraph.gwt.client.model.MxICell#getEdgeCount()
	 */
	@Override public native int getEdgeCount() /*-{
		return @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).getEdgeCount();
	}-*/;

	/*
	 * (non-Javadoc)
	 *
	 * @see com.mxgraph.gwt.client.model.MxICell#getEdgeIndex(com.mxgraph.gwt.client.model.MxICell)
	 */
	@Override public native int getEdgeIndex(MxICell edge) /*-{
		var edgeJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(edge);
		return @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).getEdgeIndex(edgeJS);
	}-*/;

	/*
	 * (non-Javadoc)
	 *
	 * @see com.mxgraph.gwt.client.model.MxICell#getEdgeAt(int)
	 */
	@Override public native MxICell getEdgeAt(int index) /*-{
		var mxCellJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).getEdgeAt(index);
		return @com.mxgraph.impl.util.WrapperUtils::wrap(Lcom/google/gwt/core/client/JavaScriptObject;)(mxCellJS);
	}-*/;

	/*
	 * (non-Javadoc)
	 *
	 * @see com.mxgraph.gwt.client.model.MxICell#insertEdge(com.mxgraph.gwt.client.model.MxICell, boolean)
	 */
	@Override public native MxICell insertEdge(MxICell edge, boolean isOutgoing) /*-{
		var cellJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(edge);
		var insertedCellJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this)
				.insertEdge(cellJS, isOutgoing);
		return @com.mxgraph.impl.util.WrapperUtils::wrap(Lcom/google/gwt/core/client/JavaScriptObject;)(insertedCellJS);
	}-*/;

	/*
	 * (non-Javadoc)
	 *
	 * @see com.mxgraph.gwt.client.model.MxICell#removeEdge(com.mxgraph.gwt.client.model.MxICell, boolean)
	 */
	@Override public native MxICell removeEdge(MxICell edge, boolean isOutgoing) /*-{
		var cellJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(edge);
		var removedCellJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).removeEdge(cellJS, isOutgoing);
		return @com.mxgraph.impl.util.WrapperUtils::wrap(Lcom/google/gwt/core/client/JavaScriptObject;)(removedCellJS);
	}-*/;

	/*
	 * (non-Javadoc)
	 *
	 * @see com.mxgraph.gwt.client.model.MxICell#removeFromTerminal(boolean)
	 */
	@Override public native void removeFromTerminal(boolean isSource) /*-{
		@com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).removeFromTerminal(isSource);
	}-*/;

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#clone()
	 */
	@Override public native MxCell clone() /*-{
		var mxCellJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).clone();
		return new @com.mxgraph.impl.model.MxCell::new(Lcom/google/gwt/core/client/JavaScriptObject;)(mxCellJS);
	}-*/;

	/**
	 * Returns a clone of the cell's user object.
	 *
	 * @return clone
	 */
	public native Object cloneValue() /*-{
		var clonedJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).cloneValue();
		return clonedJS;
	}-*/;


	/* (non-Javadoc)
	 * @see com.mxgraph.gwt.client.model.MxICell#getAttribute(java.lang.String, java.lang.String)
	 */
	public native String getAttribute(String name, String defaultValue) /*-{
		return @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).getAttribute(name, defaultValue);
	}-*/;

	/* (non-Javadoc)
	 * @see com.mxgraph.gwt.client.model.MxICell#setAttribute(java.lang.String, java.lang.String)
	 */
	@Override public native void setAttribute(String name, String value) /*-{
		@com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).setAttribute(name, value);
	}-*/;



}
