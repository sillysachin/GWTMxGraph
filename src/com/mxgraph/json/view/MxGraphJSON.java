package com.mxgraph.json.view;

import com.mxgraph.api.view.IsMxGraph;

public final class MxGraphJSON implements IsMxGraph {

	// private List<MouseListeners> mouseListeners;
	private boolean isMouseDown;
	// private MxGraphModelJSON model;
	// private MxGraphViewJSON view;
	// private MxStylesheet stylesheet;
	// private MxGraphSelectionModel selectionModel;
	// private MxCellEditor cellEditor;
	// private MxCellRenderer cellRenderer;
	// private MxMultiplicities multiplicities;
	private String renderHint;
	private String dialect;
	private boolean allowAutoPanning;
	private int gridSize;

	/**
	 * Specifies if the grid is enabled. Default is true.
	 */
	protected boolean gridEnabled = true;

	/**
	 * Specifies if ports are enabled. This is used in <cellConnected> to update the respective style. Default is true.
	 */
	protected boolean portsEnabled = true;

	/**
	 * Value returned by getOverlap if isAllowOverlapParent returns true for the given cell. getOverlap is used in keepInside if isKeepInsideParentOnMove returns true. The value specifies the portion of the child which is allowed to overlap the parent.
	 */
	protected double defaultOverlap = 0.5;

	/**
	 * Specifies the default parent to be used to insert new cells. This is used in getDefaultParent. Default is null.
	 */
	protected Object defaultParent;

	/**
	 * Specifies the alternate edge style to be used if the main control point on an edge is being doubleclicked. Default is null.
	 */
	protected String alternateEdgeStyle;

	/**
	 * Specifies the return value for isEnabled. Default is true.
	 */
	protected boolean enabled = true;

	/**
	 * Specifies the return value for isCell(s)Locked. Default is false.
	 */
	protected boolean cellsLocked = false;

	/**
	 * Specifies the return value for isCell(s)Editable. Default is true.
	 */
	protected boolean cellsEditable = true;

	/**
	 * Specifies the return value for isCell(s)Sizable. Default is true.
	 */
	protected boolean cellsResizable = true;

	/**
	 * Specifies the return value for isCell(s)Movable. Default is true.
	 */
	protected boolean cellsMovable = true;

	/**
	 * Specifies the return value for isCell(s)Bendable. Default is true.
	 */
	protected boolean cellsBendable = true;

	/**
	 * Specifies the return value for isCell(s)Selectable. Default is true.
	 */
	protected boolean cellsSelectable = true;

	/**
	 * Specifies the return value for isCell(s)Deletable. Default is true.
	 */
	protected boolean cellsDeletable = true;

	/**
	 * Specifies the return value for isCell(s)Cloneable. Default is true.
	 */
	protected boolean cellsCloneable = true;

	/**
	 * Specifies the return value for isCellDisconntableFromTerminal. Default is true.
	 */
	protected boolean cellsDisconnectable = true;

	/**
	 * Specifies the return value for isLabel(s)Clipped. Default is false.
	 */
	protected boolean labelsClipped = false;

	/**
	 * Specifies the return value for edges in isLabelMovable. Default is true.
	 */
	protected boolean edgeLabelsMovable = true;

	/**
	 * Specifies the return value for vertices in isLabelMovable. Default is false.
	 */
	protected boolean vertexLabelsMovable = false;

	/**
	 * Specifies the return value for isDropEnabled. Default is true.
	 */
	protected boolean dropEnabled = true;

	/**
	 * Specifies if dropping onto edges should be enabled. Default is true.
	 */
	protected boolean splitEnabled = true;

	/**
	 * Specifies if the graph should automatically update the cell size after an edit. This is used in isAutoSizeCell. Default is false.
	 */
	protected boolean autoSizeCells = false;

	/**
	 * <mxRectangle> that specifies the area in which all cells in the diagram should be placed. Uses in getMaximumGraphBounds. Use a width or height of 0 if you only want to give a upper, left corner.
	 */
	// protected MxRectangle maximumGraphBounds = null;

	/**
	 * mxRectangle that specifies the minimum size of the graph canvas inside the scrollpane.
	 */
	// protected MxRectangle minimumGraphSize = null;

	/**
	 * Border to be added to the bottom and right side when the container is being resized after the graph has been changed. Default is 0.
	 */
	protected int border = 0;

	/**
	 * Specifies if edges should appear in the foreground regardless of their order in the model. This has precendence over keepEdgeInBackground Default is false.
	 */
	protected boolean keepEdgesInForeground = false;

	/**
	 * Specifies if edges should appear in the background regardless of their order in the model. Default is false.
	 */
	protected boolean keepEdgesInBackground = false;

	/**
	 * Specifies if the cell size should be changed to the preferred size when a cell is first collapsed. Default is true.
	 */
	protected boolean collapseToPreferredSize = true;

	/**
	 * Specifies if negative coordinates for vertices are allowed. Default is true.
	 */
	protected boolean allowNegativeCoordinates = true;

	/**
	 * Specifies the return value for isConstrainChildren. Default is true.
	 */
	protected boolean constrainChildren = true;

	/**
	 * Specifies if a parent should contain the child bounds after a resize of the child. Default is true.
	 */
	protected boolean extendParents = true;

	/**
	 * Specifies if parents should be extended according to the <extendParents> switch if cells are added. Default is true.
	 */
	protected boolean extendParentsOnAdd = true;

	/**
	 * Specifies if the scale and translate should be reset if the root changes in the model. Default is true.
	 */
	protected boolean resetViewOnRootChange = true;

	/**
	 * Specifies if loops (aka self-references) are allowed. Default is false.
	 */
	protected boolean resetEdgesOnResize = false;

	/**
	 * Specifies if edge control points should be reset after the move of a connected cell. Default is false.
	 */
	protected boolean resetEdgesOnMove = false;

	/**
	 * Specifies if edge control points should be reset after the the edge has been reconnected. Default is true.
	 */
	protected boolean resetEdgesOnConnect = true;

	/**
	 * Specifies if loops (aka self-references) are allowed. Default is false.
	 */
	protected boolean allowLoops = false;

	/**
	 * Specifies the default style for loops.
	 */
	// protected MxEdgeStyle.mxEdgeStyleFunction defaultLoopStyle = MxEdgeStyle.Loop;

	/**
	 * Specifies if multiple edges in the same direction between the same pair of vertices are allowed. Default is true.
	 */
	protected boolean multigraph = true;

	/**
	 * Specifies if edges are connectable. Default is false. This overrides the connectable field in edges.
	 */
	protected boolean connectableEdges = false;

	/**
	 * Specifies if edges with disconnected terminals are allowed in the graph. Default is false.
	 */
	protected boolean allowDanglingEdges = true;

	/**
	 * Specifies if edges that are cloned should be validated and only inserted if they are valid. Default is true.
	 */
	protected boolean cloneInvalidEdges = false;

	/**
	 * Specifies if edges should be disconnected from their terminals when they are moved. Default is true.
	 */
	protected boolean disconnectOnMove = true;

	/**
	 * Specifies if labels should be visible. This is used in getLabel. Default is true.
	 */
	protected boolean labelsVisible = true;

	/**
	 * Specifies the return value for isHtmlLabel. Default is false.
	 */
	protected boolean htmlLabels = false;

	/**
	 * Specifies if nesting of swimlanes is allowed. Default is true.
	 */
	protected boolean swimlaneNesting = true;

	/**
	 * Specifies the maximum number of changes that should be processed to find the dirty region. If the number of changes is larger, then the complete grah is repainted. A value of zero will always compute the dirty region for any number of changes. Default is 1000.
	 */
	protected int changesRepaintThreshold = 1000;

	/**
	 * Specifies if the origin should be automatically updated.
	 */
	protected boolean autoOrigin = false;

	/**
	 * Holds the current automatic origin.
	 */
	// protected MxPoint origin = new MxPoint();

	/**
	 * Holds the list of bundles.
	 */
	// protected static List<MxImageBundle> imageBundles = new LinkedList<MxImageBundle>();

	protected MxGraphJSON() {
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#isMouseDown()
	 */
	@Override
	public boolean isMouseDown() {
		return isMouseDown;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#setMouseDown(boolean)
	 */
	@Override
	public void setMouseDown(boolean isMouseDown) {
		this.isMouseDown = isMouseDown;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#getRenderHint()
	 */
	@Override
	public String getRenderHint() {
		return renderHint;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#setRenderHint(java.lang.String)
	 */
	@Override
	public void setRenderHint(String renderHint) {
		this.renderHint = renderHint;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#getDialect()
	 */
	@Override
	public String getDialect() {
		return dialect;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#setDialect(java.lang.String)
	 */
	@Override
	public void setDialect(String dialect) {
		this.dialect = dialect;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#isAllowAutoPanning()
	 */
	@Override
	public boolean isAllowAutoPanning() {
		return allowAutoPanning;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#setAllowAutoPanning(boolean)
	 */
	@Override
	public void setAllowAutoPanning(boolean allowAutoPanning) {
		this.allowAutoPanning = allowAutoPanning;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#getGridSize()
	 */
	@Override
	public int getGridSize() {
		return gridSize;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#setGridSize(int)
	 */
	@Override
	public void setGridSize(int gridSize) {
		this.gridSize = gridSize;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#isGridEnabled()
	 */
	@Override
	public boolean isGridEnabled() {
		return gridEnabled;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#setGridEnabled(boolean)
	 */
	@Override
	public void setGridEnabled(boolean gridEnabled) {
		this.gridEnabled = gridEnabled;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#isPortsEnabled()
	 */
	@Override
	public boolean isPortsEnabled() {
		return portsEnabled;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#setPortsEnabled(boolean)
	 */
	@Override
	public void setPortsEnabled(boolean portsEnabled) {
		this.portsEnabled = portsEnabled;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#getDefaultOverlap()
	 */
	@Override
	public double getDefaultOverlap() {
		return defaultOverlap;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#setDefaultOverlap(double)
	 */
	@Override
	public void setDefaultOverlap(double defaultOverlap) {
		this.defaultOverlap = defaultOverlap;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#getDefaultParent()
	 */
	@Override
	public Object getDefaultParent() {
		return defaultParent;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#setDefaultParent(java.lang.Object)
	 */
	@Override
	public void setDefaultParent(Object defaultParent) {
		this.defaultParent = defaultParent;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#getAlternateEdgeStyle()
	 */
	@Override
	public String getAlternateEdgeStyle() {
		return alternateEdgeStyle;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#setAlternateEdgeStyle(java.lang.String)
	 */
	@Override
	public void setAlternateEdgeStyle(String alternateEdgeStyle) {
		this.alternateEdgeStyle = alternateEdgeStyle;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#isEnabled()
	 */
	@Override
	public boolean isEnabled() {
		return enabled;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#setEnabled(boolean)
	 */
	@Override
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#isCellsLocked()
	 */
	@Override
	public boolean isCellsLocked() {
		return cellsLocked;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#setCellsLocked(boolean)
	 */
	@Override
	public void setCellsLocked(boolean cellsLocked) {
		this.cellsLocked = cellsLocked;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#isCellsEditable()
	 */
	@Override
	public boolean isCellsEditable() {
		return cellsEditable;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#setCellsEditable(boolean)
	 */
	@Override
	public void setCellsEditable(boolean cellsEditable) {
		this.cellsEditable = cellsEditable;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#isCellsResizable()
	 */
	@Override
	public boolean isCellsResizable() {
		return cellsResizable;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#setCellsResizable(boolean)
	 */
	@Override
	public void setCellsResizable(boolean cellsResizable) {
		this.cellsResizable = cellsResizable;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#isCellsMovable()
	 */
	@Override
	public boolean isCellsMovable() {
		return cellsMovable;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#setCellsMovable(boolean)
	 */
	@Override
	public void setCellsMovable(boolean cellsMovable) {
		this.cellsMovable = cellsMovable;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#isCellsBendable()
	 */
	@Override
	public boolean isCellsBendable() {
		return cellsBendable;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#setCellsBendable(boolean)
	 */
	@Override
	public void setCellsBendable(boolean cellsBendable) {
		this.cellsBendable = cellsBendable;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#isCellsSelectable()
	 */
	@Override
	public boolean isCellsSelectable() {
		return cellsSelectable;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#setCellsSelectable(boolean)
	 */
	@Override
	public void setCellsSelectable(boolean cellsSelectable) {
		this.cellsSelectable = cellsSelectable;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#isCellsDeletable()
	 */
	@Override
	public boolean isCellsDeletable() {
		return cellsDeletable;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#setCellsDeletable(boolean)
	 */
	@Override
	public void setCellsDeletable(boolean cellsDeletable) {
		this.cellsDeletable = cellsDeletable;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#isCellsCloneable()
	 */
	@Override
	public boolean isCellsCloneable() {
		return cellsCloneable;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#setCellsCloneable(boolean)
	 */
	@Override
	public void setCellsCloneable(boolean cellsCloneable) {
		this.cellsCloneable = cellsCloneable;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#isCellsDisconnectable()
	 */
	@Override
	public boolean isCellsDisconnectable() {
		return cellsDisconnectable;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#setCellsDisconnectable(boolean)
	 */
	@Override
	public void setCellsDisconnectable(boolean cellsDisconnectable) {
		this.cellsDisconnectable = cellsDisconnectable;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#isLabelsClipped()
	 */
	@Override
	public boolean isLabelsClipped() {
		return labelsClipped;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#setLabelsClipped(boolean)
	 */
	@Override
	public void setLabelsClipped(boolean labelsClipped) {
		this.labelsClipped = labelsClipped;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#isEdgeLabelsMovable()
	 */
	@Override
	public boolean isEdgeLabelsMovable() {
		return edgeLabelsMovable;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#setEdgeLabelsMovable(boolean)
	 */
	@Override
	public void setEdgeLabelsMovable(boolean edgeLabelsMovable) {
		this.edgeLabelsMovable = edgeLabelsMovable;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#isVertexLabelsMovable()
	 */
	@Override
	public boolean isVertexLabelsMovable() {
		return vertexLabelsMovable;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#setVertexLabelsMovable(boolean)
	 */
	@Override
	public void setVertexLabelsMovable(boolean vertexLabelsMovable) {
		this.vertexLabelsMovable = vertexLabelsMovable;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#isDropEnabled()
	 */
	@Override
	public boolean isDropEnabled() {
		return dropEnabled;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#setDropEnabled(boolean)
	 */
	@Override
	public void setDropEnabled(boolean dropEnabled) {
		this.dropEnabled = dropEnabled;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#isSplitEnabled()
	 */
	@Override
	public boolean isSplitEnabled() {
		return splitEnabled;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#setSplitEnabled(boolean)
	 */
	@Override
	public void setSplitEnabled(boolean splitEnabled) {
		this.splitEnabled = splitEnabled;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#isAutoSizeCells()
	 */
	@Override
	public boolean isAutoSizeCells() {
		return autoSizeCells;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#setAutoSizeCells(boolean)
	 */
	@Override
	public void setAutoSizeCells(boolean autoSizeCells) {
		this.autoSizeCells = autoSizeCells;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#getBorder()
	 */
	@Override
	public int getBorder() {
		return border;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#setBorder(int)
	 */
	@Override
	public void setBorder(int border) {
		this.border = border;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#isKeepEdgesInForeground()
	 */
	@Override
	public boolean isKeepEdgesInForeground() {
		return keepEdgesInForeground;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#setKeepEdgesInForeground(boolean)
	 */
	@Override
	public void setKeepEdgesInForeground(boolean keepEdgesInForeground) {
		this.keepEdgesInForeground = keepEdgesInForeground;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#isKeepEdgesInBackground()
	 */
	@Override
	public boolean isKeepEdgesInBackground() {
		return keepEdgesInBackground;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#setKeepEdgesInBackground(boolean)
	 */
	@Override
	public void setKeepEdgesInBackground(boolean keepEdgesInBackground) {
		this.keepEdgesInBackground = keepEdgesInBackground;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#isCollapseToPreferredSize()
	 */
	@Override
	public boolean isCollapseToPreferredSize() {
		return collapseToPreferredSize;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#setCollapseToPreferredSize(boolean)
	 */
	@Override
	public void setCollapseToPreferredSize(boolean collapseToPreferredSize) {
		this.collapseToPreferredSize = collapseToPreferredSize;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#isAllowNegativeCoordinates()
	 */
	@Override
	public boolean isAllowNegativeCoordinates() {
		return allowNegativeCoordinates;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#setAllowNegativeCoordinates(boolean)
	 */
	@Override
	public void setAllowNegativeCoordinates(boolean allowNegativeCoordinates) {
		this.allowNegativeCoordinates = allowNegativeCoordinates;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#isConstrainChildren()
	 */
	@Override
	public boolean isConstrainChildren() {
		return constrainChildren;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#setConstrainChildren(boolean)
	 */
	@Override
	public void setConstrainChildren(boolean constrainChildren) {
		this.constrainChildren = constrainChildren;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#isExtendParents()
	 */
	@Override
	public boolean isExtendParents() {
		return extendParents;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#setExtendParents(boolean)
	 */
	@Override
	public void setExtendParents(boolean extendParents) {
		this.extendParents = extendParents;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#isExtendParentsOnAdd()
	 */
	@Override
	public boolean isExtendParentsOnAdd() {
		return extendParentsOnAdd;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#setExtendParentsOnAdd(boolean)
	 */
	@Override
	public void setExtendParentsOnAdd(boolean extendParentsOnAdd) {
		this.extendParentsOnAdd = extendParentsOnAdd;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#isResetViewOnRootChange()
	 */
	@Override
	public boolean isResetViewOnRootChange() {
		return resetViewOnRootChange;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#setResetViewOnRootChange(boolean)
	 */
	@Override
	public void setResetViewOnRootChange(boolean resetViewOnRootChange) {
		this.resetViewOnRootChange = resetViewOnRootChange;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#isResetEdgesOnResize()
	 */
	@Override
	public boolean isResetEdgesOnResize() {
		return resetEdgesOnResize;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#setResetEdgesOnResize(boolean)
	 */
	@Override
	public void setResetEdgesOnResize(boolean resetEdgesOnResize) {
		this.resetEdgesOnResize = resetEdgesOnResize;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#isResetEdgesOnMove()
	 */
	@Override
	public boolean isResetEdgesOnMove() {
		return resetEdgesOnMove;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#setResetEdgesOnMove(boolean)
	 */
	@Override
	public void setResetEdgesOnMove(boolean resetEdgesOnMove) {
		this.resetEdgesOnMove = resetEdgesOnMove;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#isResetEdgesOnConnect()
	 */
	@Override
	public boolean isResetEdgesOnConnect() {
		return resetEdgesOnConnect;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#setResetEdgesOnConnect(boolean)
	 */
	@Override
	public void setResetEdgesOnConnect(boolean resetEdgesOnConnect) {
		this.resetEdgesOnConnect = resetEdgesOnConnect;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#isAllowLoops()
	 */
	@Override
	public boolean isAllowLoops() {
		return allowLoops;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#setAllowLoops(boolean)
	 */
	@Override
	public void setAllowLoops(boolean allowLoops) {
		this.allowLoops = allowLoops;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#isMultigraph()
	 */
	@Override
	public boolean isMultigraph() {
		return multigraph;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#setMultigraph(boolean)
	 */
	@Override
	public void setMultigraph(boolean multigraph) {
		this.multigraph = multigraph;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#isConnectableEdges()
	 */
	@Override
	public boolean isConnectableEdges() {
		return connectableEdges;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#setConnectableEdges(boolean)
	 */
	@Override
	public void setConnectableEdges(boolean connectableEdges) {
		this.connectableEdges = connectableEdges;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#isAllowDanglingEdges()
	 */
	@Override
	public boolean isAllowDanglingEdges() {
		return allowDanglingEdges;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#setAllowDanglingEdges(boolean)
	 */
	@Override
	public void setAllowDanglingEdges(boolean allowDanglingEdges) {
		this.allowDanglingEdges = allowDanglingEdges;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#isCloneInvalidEdges()
	 */
	@Override
	public boolean isCloneInvalidEdges() {
		return cloneInvalidEdges;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#setCloneInvalidEdges(boolean)
	 */
	@Override
	public void setCloneInvalidEdges(boolean cloneInvalidEdges) {
		this.cloneInvalidEdges = cloneInvalidEdges;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#isDisconnectOnMove()
	 */
	@Override
	public boolean isDisconnectOnMove() {
		return disconnectOnMove;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#setDisconnectOnMove(boolean)
	 */
	@Override
	public void setDisconnectOnMove(boolean disconnectOnMove) {
		this.disconnectOnMove = disconnectOnMove;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#isLabelsVisible()
	 */
	@Override
	public boolean isLabelsVisible() {
		return labelsVisible;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#setLabelsVisible(boolean)
	 */
	@Override
	public void setLabelsVisible(boolean labelsVisible) {
		this.labelsVisible = labelsVisible;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#isHtmlLabels()
	 */
	@Override
	public boolean isHtmlLabels() {
		return htmlLabels;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#setHtmlLabels(boolean)
	 */
	@Override
	public void setHtmlLabels(boolean htmlLabels) {
		this.htmlLabels = htmlLabels;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#isSwimlaneNesting()
	 */
	@Override
	public boolean isSwimlaneNesting() {
		return swimlaneNesting;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#setSwimlaneNesting(boolean)
	 */
	@Override
	public void setSwimlaneNesting(boolean swimlaneNesting) {
		this.swimlaneNesting = swimlaneNesting;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#getChangesRepaintThreshold()
	 */
	@Override
	public int getChangesRepaintThreshold() {
		return changesRepaintThreshold;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#setChangesRepaintThreshold(int)
	 */
	@Override
	public void setChangesRepaintThreshold(int changesRepaintThreshold) {
		this.changesRepaintThreshold = changesRepaintThreshold;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#isAutoOrigin()
	 */
	@Override
	public boolean isAutoOrigin() {
		return autoOrigin;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.json.view.IsMxGraph#setAutoOrigin(boolean)
	 */
	@Override
	public void setAutoOrigin(boolean autoOrigin) {
		this.autoOrigin = autoOrigin;
	}
}