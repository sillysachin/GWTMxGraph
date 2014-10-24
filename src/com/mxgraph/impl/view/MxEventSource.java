package com.mxgraph.impl.view;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.ui.Composite;
import com.mxgraph.jso.IJavaScriptWrapper;

/**
 * Base class for objects that dispatch named events.
 */
public class MxEventSource extends Composite implements IJavaScriptWrapper {

	protected JavaScriptObject jso;

	private native JavaScriptObject createJso(JavaScriptObject eventSource) /*-{
		return new $wnd.MxEventSource(eventSource);
	}-*/;

	public static interface MxIEventListener<C> {
		/**
		 * Called when the graph model has changed.
		 *
		 * @param sender Reference to the source of the event.
		 * @param evt Event object to be dispatched.
		 */
		public void invoke(C sender, MxEventObject evt);

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.mxgraph.gwt.client.IJavaScriptWrapper#getJso()
	 */
	@Override
	public JavaScriptObject getJso() {
		return jso;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.mxgraph.gwt.client.IJavaScriptWrapper#setJso(com.google.gwt.core.client.JavaScriptObject)
	 */
	@Override
	public void setJso(JavaScriptObject jso) {
		this.jso = jso;
	}

	protected MxEventSource(JavaScriptObject jso) {
		this.jso = jso;
	}

	public MxEventSource() {
	}

	public MxEventSource(IJavaScriptWrapper eventSource) {
		jso = createJso(eventSource.getJso());
	}

	/**
	 * Returns true if events can be fired. Default is true.
	 *
	 * @return
	 */
	public native boolean isEventsEnabled() /*-{
		return @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).eventsEnabled;
	}-*/;

	/**
	 * Specifies if events can be fired
	 *
	 * @param eventsEnabled
	 */
	public native void setEventsEnabled(boolean eventsEnabled) /*-{
		@com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).eventsEnabled = eventsEnabled;
	}-*/;

	/**
	 * Gets event source.
	 *
	 * @return event source
	 */
	public native IJavaScriptWrapper getEventSource() /*-{
		var eventSourceJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).eventSource;
		return @com.mxgraph.impl.util.WrapperUtils::wrap(Lcom/google/gwt/core/client/JavaScriptObject;)(eventSourceJS);
	}-*/;

	/**
	 * Sets event source.
	 *
	 * @param eventSource
	 */
	public native void setEventSource(IJavaScriptWrapper eventSource) /*-{
		var eventSourceJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(eventSource);
		@com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).eventSource = eventSourceJS;
	}-*/;

	/**
	 * Binds the specified listener to the given event name. If no event name is given, then the listener is registered for all events.
	 *
	 * @param eventName event to which to attach the listener
	 * @param listener listener
	 */
	public native void addListener(String eventName, MxIEventListener<?> listener) /*-{
		//Creates a native event listener (function) which really is just a delegate for Java listener
		var nativeListener = @com.mxgraph.impl.util.WrapperUtils::wrapCallbackInterface(Lcom/mxgraph/impl/view/MxEventSource$MxIEventListener;)(listener);

		//Binds native JavaScript function to Java listener. Needed when removing listeners
		nativeListener.listenerHash = listener.@java.lang.Object::hashCode()();
		//Adds listener to native graph model instance
		@com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).addListener(eventName, nativeListener);
	}-*/;

	/**
	 * Removes all occurrences of the given listener from the list of listeners.
	 *
	 * @param listener listener for removal
	 */
	public native void removeListener(MxIEventListener<?> listener) /*-{
		var listenerHash = listener.@java.lang.Object::hashCode()();
		var eventListenersJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).eventListeners;

		for ( var i = 0; i < eventListenersJS.length; i++) {
			//only remove native listeners whose hash sum matches the hash sum of Java listeners
			if (eventListenersJS[i].listenerHash != null && eventListenersJS[i].listenerHash == listenerHash) {
				@com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).removeListener(eventListenersJS[i]);
			}
		}

	}-*/;

	/**
	 * Dispatches the given event name with this object as the event source.
	 * <code>fireEvent(new MxEventObject("eventName", key1, val1, .., keyN, valN))</code>
	 */
	public void fireEvent(MxEventObject evt) {
		fireEvent(evt, null);
	}

	/**
	 * Dispatches the given event name, passing all arguments after the given
	 * name to the registered listeners for the event.
	 */
	public native void fireEvent(MxEventObject eventObject, MxEventSource eventSource) /*-{
		var eventObjectJS = eventObject != null ? @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(eventObject) : null;
		var eventSourceJS = eventSource != null ? @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(eventSource) : null;
		@com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).fireEvent(eventObjectJS, eventSourceJS);
	}-*/;

}
