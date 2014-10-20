package com.mxgraph.impl.model;

import com.mxgraph.jso.model.MxGraphModelJSO;

public class MxGraphModel {
	private MxGraphModelJSO model;

	public native void beginUpdate()
	/*-{
		var model = this.@com.mxgraph.impl.model.MxGraphModel::model;
		model.beginUpdate();
		alert(model);
		$wnd.console.log('This is JS console fun model beginUpdate' + model);
	}-*/;

	public native void endUpdate()
	/*-{
		var model = this.@com.mxgraph.impl.model.MxGraphModel::model;
		model.endUpdate();
		alert(model);
		$wnd.console.log('This is JS console fun model endUpdate' + model);
	}-*/;

	public MxGraphModelJSO getModel() {
		return model;
	}

	public void setModel(MxGraphModelJSO model) {
		this.model = model;
	}
}
