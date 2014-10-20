package com.mxgraph.gwt.client.view;

import com.google.gwt.core.client.JavaScriptObject;

public final class MxGraphJSO extends JavaScriptObject {

	protected MxGraphJSO() {
	}

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
}
