package com.mxgraph.api.view;

public interface IsMxGraph {

	boolean isMouseDown();

	void setMouseDown(boolean isMouseDown);

	String getRenderHint();

	void setRenderHint(String renderHint);

	String getDialect();

	void setDialect(String dialect);

	boolean isAllowAutoPanning();

	void setAllowAutoPanning(boolean allowAutoPanning);

	int getGridSize();

	void setGridSize(int gridSize);

	boolean isGridEnabled();

	void setGridEnabled(boolean gridEnabled);

	boolean isPortsEnabled();

	void setPortsEnabled(boolean portsEnabled);

	double getDefaultOverlap();

	void setDefaultOverlap(double defaultOverlap);

	Object getDefaultParent();

	void setDefaultParent(Object defaultParent);

	String getAlternateEdgeStyle();

	void setAlternateEdgeStyle(String alternateEdgeStyle);

	boolean isEnabled();

	void setEnabled(boolean enabled);

	boolean isCellsLocked();

	void setCellsLocked(boolean cellsLocked);

	boolean isCellsEditable();

	void setCellsEditable(boolean cellsEditable);

	boolean isCellsResizable();

	void setCellsResizable(boolean cellsResizable);

	boolean isCellsMovable();

	void setCellsMovable(boolean cellsMovable);

	boolean isCellsBendable();

	void setCellsBendable(boolean cellsBendable);

	boolean isCellsSelectable();

	void setCellsSelectable(boolean cellsSelectable);

	boolean isCellsDeletable();

	void setCellsDeletable(boolean cellsDeletable);

	boolean isCellsCloneable();

	void setCellsCloneable(boolean cellsCloneable);

	boolean isCellsDisconnectable();

	void setCellsDisconnectable(boolean cellsDisconnectable);

	boolean isLabelsClipped();

	void setLabelsClipped(boolean labelsClipped);

	boolean isEdgeLabelsMovable();

	void setEdgeLabelsMovable(boolean edgeLabelsMovable);

	boolean isVertexLabelsMovable();

	void setVertexLabelsMovable(boolean vertexLabelsMovable);

	boolean isDropEnabled();

	void setDropEnabled(boolean dropEnabled);

	boolean isSplitEnabled();

	void setSplitEnabled(boolean splitEnabled);

	boolean isAutoSizeCells();

	void setAutoSizeCells(boolean autoSizeCells);

	int getBorder();

	void setBorder(int border);

	boolean isKeepEdgesInForeground();

	void setKeepEdgesInForeground(boolean keepEdgesInForeground);

	boolean isKeepEdgesInBackground();

	void setKeepEdgesInBackground(boolean keepEdgesInBackground);

	boolean isCollapseToPreferredSize();

	void setCollapseToPreferredSize(
			boolean collapseToPreferredSize);

	boolean isAllowNegativeCoordinates();

	void setAllowNegativeCoordinates(
			boolean allowNegativeCoordinates);

	boolean isConstrainChildren();

	void setConstrainChildren(boolean constrainChildren);

	boolean isExtendParents();

	void setExtendParents(boolean extendParents);

	boolean isExtendParentsOnAdd();

	void setExtendParentsOnAdd(boolean extendParentsOnAdd);

	boolean isResetViewOnRootChange();

	void setResetViewOnRootChange(boolean resetViewOnRootChange);

	boolean isResetEdgesOnResize();

	void setResetEdgesOnResize(boolean resetEdgesOnResize);

	boolean isResetEdgesOnMove();

	void setResetEdgesOnMove(boolean resetEdgesOnMove);

	boolean isResetEdgesOnConnect();

	void setResetEdgesOnConnect(boolean resetEdgesOnConnect);

	boolean isAllowLoops();

	void setAllowLoops(boolean allowLoops);

	boolean isMultigraph();

	void setMultigraph(boolean multigraph);

	boolean isConnectableEdges();

	void setConnectableEdges(boolean connectableEdges);

	boolean isAllowDanglingEdges();

	void setAllowDanglingEdges(boolean allowDanglingEdges);

	boolean isCloneInvalidEdges();

	void setCloneInvalidEdges(boolean cloneInvalidEdges);

	boolean isDisconnectOnMove();

	void setDisconnectOnMove(boolean disconnectOnMove);

	boolean isLabelsVisible();

	void setLabelsVisible(boolean labelsVisible);

	boolean isHtmlLabels();

	void setHtmlLabels(boolean htmlLabels);

	boolean isSwimlaneNesting();

	void setSwimlaneNesting(boolean swimlaneNesting);

	int getChangesRepaintThreshold();

	void setChangesRepaintThreshold(int changesRepaintThreshold);

	boolean isAutoOrigin();

	void setAutoOrigin(boolean autoOrigin);

}