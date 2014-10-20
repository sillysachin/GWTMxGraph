package com.mxgraph.jso.view;

import com.google.gwt.core.client.JavaScriptObject;

public final class MxGraphJSO extends JavaScriptObject {

	protected MxGraphJSO() {
	}

	// private List<MouseListeners> mouseListeners;
	// private MxGraphModelJSON model;
	// private MxGraphViewJSON view;
	// private MxStylesheet stylesheet;
	// private MxGraphSelectionModel selectionModel;
	// private MxCellEditor cellEditor;
	// private MxCellRenderer cellRenderer;
	// private MxMultiplicities multiplicities;

	public native boolean getAllowAutoPanning()
	/*-{
		return this.allowAutoPanning;
	}-*/;

	public native void setAllowAutoPanning(boolean allowAutoPanning)
	/*-{
		this.allowAutoPanning = allowAutoPanning;
	}-*/;

	public native String getDialect()
	/*-{
		return this.dialect;
	}-*/;

	public native void setDialect(String dialect)
	/*-{
		this.dialect = dialect;
	}-*/;

	public native boolean isMouseDown()
	/*-{
		return this.isMouseDown;
	}-*/;

	public native void setMouseDown(boolean isMouseDown)
	/*-{
		this.isMouseDown = isMouseDown;
	}-*/;

	public native String getRenderHint()
	/*-{
		return this.renderHint;
	}-*/;

	public native void setRenderHint(String renderHint)
	/*-{
		this.renderHint = renderHint;
	}-*/;

	public native int getGridSize()
	/*-{
		return this.gridSize;
	}-*/;

	public native void setGridSize(int gridSize)
	/*-{
		this.gridSize = gridSize;
	}-*/;
}
