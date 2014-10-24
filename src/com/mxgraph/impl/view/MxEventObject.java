package com.mxgraph.impl.view;

import com.google.gwt.core.client.JavaScriptObject;
import com.mxgraph.jso.IJavaScriptWrapper;

public class MxEventObject implements IJavaScriptWrapper {

	protected JavaScriptObject jso;

	private native JavaScriptObject createJso(String name) /*-{
		return new $wnd.MxEventObject(name);
	}-*/;

	private MxEventObject() {
	}

	protected MxEventObject(JavaScriptObject jso) {
		this.jso = jso;
	}

	@Override public JavaScriptObject getJso() {
		return jso;
	}

	@Override public void setJso(JavaScriptObject jso) {
		this.jso = jso;
	}

	/**
	 * Constructs a new event for the given name.
	 */
	public MxEventObject(String name) {
		jso = createJso(name);
	}

	/**
	 * Constructs a new event for the given name and properties. The optional properties are specified using a sequence of keys and values, eg.
	 * <code>new MxEventObject("eventName", key1, val1, .., keyN, valN))</code>
	 */
	public MxEventObject(String name, Object... args) {

	}

	public native String getName() /*-{
		@com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).getName();
	}-*/;

	/**
	 * Returns the property for the given key.
	 *
	 * @param key property key
	 * @return property value
	 */
	//TODO allow method to return something other than wrapper instance
	public native Object getProperty(String key) /*-{
		var valueJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).getProperty(key);

		return @com.mxgraph.impl.util.WrapperUtils::wrap(Lcom/google/gwt/core/client/JavaScriptObject;)(valueJS);
	}-*/;

}
