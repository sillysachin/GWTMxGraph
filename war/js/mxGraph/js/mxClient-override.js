//Overriden methods for selenium

var mxCellRendererInitializeShape = mxCellRenderer.prototype.initializeShape;
mxCellRenderer.prototype.initializeShape = function(state)
{
  mxCellRendererInitializeShape.apply(this, arguments);
  if (state.shape.node != null && state.cell.id != null)
  {
    state.shape.node.setAttribute('id', 'shape-' + state.cell.id);
	state.shape.node.setAttribute('name', state.cell.value);
  }
};

var mxCellRendererInitializeLabel = mxCellRenderer.prototype.initializeLabel;
mxCellRenderer.prototype.initializeLabel = function(state)
{
  mxCellRendererInitializeLabel.apply(this, arguments);
  if (state.text.node != null && state.cell.id != null)
  {
    state.text.node.setAttribute('id', 'label-' + state.cell.id);
  }
};

var mxCellRenderercreateCellOverlays = mxCellRenderer.prototype.createCellOverlays; 
mxCellRenderer.prototype.createCellOverlays = function(state)
{
  mxCellRenderercreateCellOverlays.apply(this, arguments);
  if(state.overlays)
  {
  for(var i=0;i<state.overlays.length;i++)
 {
	var overlay = state.overlays[i];
	  if (overlay.node != null && state.cell.overlays != null)
	  {
		//overlay.node.id = state.cell.id + '-ol-' + i;
		overlay.node.setAttribute('id', state.cell.id + '-ol-' + i);
	  }
  }
  }
};

var mxCellRendererinitControl  = mxCellRenderer.prototype.initControl; 
mxCellRenderer.prototype.initControl = function(state,control,handleEvents,clickHandler)
{
  mxCellRendererinitControl.apply(this, arguments);
  if( control.node != null  && state.cell.id != null )
  {
	control.node.setAttribute('id', state.cell.id + '-ec');
  }
};

var vertexHandlerUnion = mxVertexHandler.prototype.union;
mxVertexHandler.prototype.union = function(bounds, dx, dy, index, gridEnabled, scale, tr)
{
	var result = vertexHandlerUnion.apply(this, arguments);
		
	result.width = Math.max(result.width, mxUtils.getNumber(this.state.style, 'minWidth', 0));
	result.height = Math.max(result.height, mxUtils.getNumber(this.state.style, 'minHeight', 0));
		
	return result;
};

var vertexLabelOffset = mxGraphView.prototype.updateVertexLabelOffset;
mxGraphView.prototype.updateVertexLabelOffset = function(state)
{
	vertexLabelOffset.apply(this, arguments);
	var vertical=mxUtils.getValue(state.style,mxConstants.STYLE_VERTICAL_LABEL_POSITION,mxConstants.ALIGN_MIDDLE);
	if(vertical==mxConstants.ALIGN_BOTTOM)
	{
		state.absoluteOffset.y-= parseInt(3*state.height/4);
	}
};


mxCellRenderer.prototype.getLabelBounds = function(a) {
    var b = a.view.graph,
        c = a.view.scale,
        d = b.getModel().isEdge(a.cell),
        e = new mxRectangle(a.absoluteOffset.x, a.absoluteOffset.y);
    if (d) {
        var f = a.text.getSpacing();
        e.x += f.x * c;
        e.y += f.y * c;
        b = b.getCellGeometry(a.cell);
        null != b && (e.width = Math.max(0, b.width * c), e.height = Math.max(0, b.height * c))
    } else a.text.isPaintBoundsInverted() && (c = e.x, e.x = e.y, e.y = c), e.x += a.x, e.y += a.y, e.width = Math.max(1, a.width), e.height = Math.max(1, a.height);
    a.text.isPaintBoundsInverted() && (c = (a.width -
        a.height) / 2, e.x += c, e.y -= c, c = e.width, e.width = e.height, e.height = c);
    ((a.style.shape == "Card" || a.style.shape == "ManualInput") &&  a.style.verticalLabelPosition == "center" ) && ( e.y += ((40 /100) * a.height), e.height -= ((40 /100) * a.height)  )
    null != a.shape && (e = a.shape.getLabelBounds(e));
    d || this.rotateLabelBounds(a, e);
    return e
};

