package com.mxgraph.impl.model;

import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.mxgraph.impl.util.WrapperUtils;
import com.mxgraph.impl.view.MxEventSource;
import com.mxgraph.jso.IJavaScriptWrapper;
import com.mxgraph.jso.MxICell;

/**
 * Extends {@link mxEventSource} to implement a graph model. The graph model acts as a wrapper around the cells which are in charge of storing the actual graph
 * datastructure. The model acts as a transactional wrapper with event notification for all changes, whereas the cells contain the atomic operations for
 * updating the actual datastructure.
 *
 * Layers:
 *
 * The cell hierarchy in the model must have a top-level root cell which contains the layers (typically one default layer), which in turn contain the top-level
 * cells of the layers. This means each cell is contained in a layer. If no layers are required, then all new cells should be added to the default layer.
 *
 * Layers are useful for hiding and showing groups of cells, or for placing groups of cells on top of other cells in the display. To identify a layer, the
 * <isLayer> function is used. It returns true if the parent of the given cell is the root of the model.
 *
 * Encoding the model:
 *
 * To encode a graph model, use the following code:
 *
 * (code) var enc = new mxCodec(); var node = enc.encode(graph.getModel()); (end)
 *
 * This will create an XML node that contains all the model information.
 *
 * Encoding and decoding changes:
 *
 * For the encoding of changes, a graph model listener is required that encodes each change from the given array of changes.
 *
 * (code) model.addListener(mxEvent.CHANGE, function(sender, evt) { var changes = evt.getProperty('edit').changes; var nodes = []; var codec = new mxCodec();
 *
 * for (var i = 0; i < changes.length; i++) { nodes.push(codec.encode(changes[i])); } // do something with the nodes }); (end)
 *
 * For the decoding and execution of changes, the codec needs a lookup function that allows it to resolve cell IDs as follows:
 *
 * (code) var codec = new mxCodec(); codec.lookup = function(id) { return model.getCell(id); } (end)
 *
 * For each encoded change (represented by a node), the following code can be used to carry out the decoding and create a change object.
 *
 * (code) var changes = []; var change = codec.decode(node); change.model = model; change.execute(); changes.push(change); (end)
 *
 * The changes can then be dispatched using the model as follows.
 *
 * (code) var edit = new mxUndoableEdit(model, false); edit.changes = changes;
 *
 * edit.notify = function() { edit.source.fireEvent(new mxEventObject(mxEvent.CHANGE, 'edit', edit, 'changes', edit.changes)); edit.source.fireEvent(new
 * mxEventObject(mxEvent.NOTIFY, 'edit', edit, 'changes', edit.changes)); }
 *
 * model.fireEvent(new mxEventObject(mxEvent.UNDO, 'edit', edit)); model.fireEvent(new mxEventObject(mxEvent.CHANGE, 'edit', edit, 'changes', changes)); (end)
 *
 * Event: mxEvent.CHANGE
 *
 * Fires when an undoable edit is dispatched. The <code>edit</code> property contains the <mxUndoableEdit>. The <code>changes</code> property contains the array
 * of atomic changes inside the undoable edit. The changes property is <strong>deprecated</strong>, please use edit.changes instead.
 *
 * Example:
 *
 * For finding newly inserted cells, the following code can be used:
 *
 * (code) graph.model.addListener(mxEvent.CHANGE, function(sender, evt) { var changes = evt.getProperty('edit').changes;
 *
 * for (var i = 0; i < changes.length; i++) { var change = changes[i];
 *
 * if (change.constructor == MxChildChange && change.change.previous == null) { graph.startEditingAtCell(change.child); break; } } }); (end)
 *
 *
 * Event: mxEvent.NOTIFY
 *
 * Same as <mxEvent.CHANGE>, this event can be used for classes that need to implement a sync mechanism between this model and, say, a remote model. In such a
 * setup, only local changes should trigger a notify event and all changes should trigger a change event.
 *
 * Event: mxEvent.EXECUTE
 *
 * Fires between begin- and endUpdate and after an atomic change was executed in the model. The <code>change</code> property contains the atomic change that was
 * executed.
 *
 * Event: mxEvent.BEGIN_UPDATE
 *
 * Fires after the <updateLevel> was incremented in <beginUpdate>. This event contains no properties.
 *
 * Event: mxEvent.END_UPDATE
 *
 * Fires after the <updateLevel> was decreased in <endUpdate> but before any notification or change dispatching. The <code>edit</code> property contains the
 * <currentEdit>.
 *
 * Event: mxEvent.BEFORE_UNDO
 *
 * Fires before the change is dispatched after the update level has reached 0 in <endUpdate>. The <code>edit</code> property contains the <curreneEdit>.
 *
 * Event: mxEvent.UNDO
 *
 * Fires after the change was dispatched in <endUpdate>. The <code>edit</code> property contains the <currentEdit>.
 */
public class MxGraphModel extends MxEventSource /*implements *MxIGraphModel*/ {

	/**
	 * Creates a JavaScript callback function that acts as a delegate to user created filter.
	 *
	 * @param filter user created filter that gets invoked by native filter delegate
	 * @return native filter delegate
	 */
	protected static native JavaScriptObject createNativeFilterDelegate(Filter filter) /*-{
		var nfd = function(cell) {
			var cellJ = @com.mxgraph.impl.util.WrapperUtils::wrap(Lcom/google/gwt/core/client/JavaScriptObject;)(cell);
			return filter.@com.mxgraph.impl.model.MxGraphModel.Filter::filter(Lcom/mxgraph/jso/MxICell;)(cellJ);
		};
		return nfd;
	}-*/;

	public static interface Filter {
		boolean filter(MxICell cell);
	}

	private native JavaScriptObject createJso(JavaScriptObject root) /*-{
		return new $wnd.MxGraphModel(root);
	}-*/;

	protected MxGraphModel() {
	}

	protected MxGraphModel(JavaScriptObject jso) {
		super(jso);
	}

	/**
	 * Constructs a new graph model. If no root is specified then a new root {@link MxICell} with a default layer is created.
	 *
	 * @param root {@link MxICell} that represents the root cell.
	 */
	public MxGraphModel(MxICell root) {
		jso = createJso(root != null ? root.getJso() : null);
	}

	/**
	 * Increments the <updateLevel> by one. The event notification is queued until <updateLevel> reaches 0 by use of <endUpdate>.
	 *
	 * All changes on <MxGraphModel> are transactional, that is, they are executed in a single undoable change on the model (without transaction isolation).
	 * Therefore, if you want to combine any number of changes into a single undoable change, you should group any two or more API calls that modify the graph
	 * model between <beginUpdate> and <endUpdate> calls as shown here:
	 *
	 * (code) var model = graph.getModel(); var parent = graph.getDefaultParent(); var index = model.getChildCount(parent); model.beginUpdate(); try {
	 * model.add(parent, v1, index); model.add(parent, v2, index+1); } finally { model.endUpdate(); } (end)
	 *
	 * Of course there is a shortcut for appending a sequence of cells into the default parent:
	 *
	 * (code) graph.addCells([v1, v2]). (end)
	 */
	public native void beginUpdate() /*-{
		@com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).beginUpdate();
	}-*/;

	/**
	 * * Decrements the <updateLevel> by one and fires an <undo> event if the <updateLevel> reaches 0. This function indirectly fires a <change> event by
	 * invoking the notify function on the <currentEdit> und then creates a new <currentEdit> using <createUndoableEdit>.
	 *
	 * The <undo> event is fired only once per edit, whereas the <change> event is fired whenever the notify function is invoked, that is, on undo and redo of
	 * the edit.
	 */
	public native void endUpdate() /*-{
		@com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).endUpdate();
	}-*/;

	public native MxICell getRoot() /*-{
		var rootJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).root;
		return @com.mxgraph.impl.util.WrapperUtils::wrap(Lcom/google/gwt/core/client/JavaScriptObject;)(rootJS);
	}-*/;

	private native JavaScriptObject getCellsJS() /*-{
		return @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).cells;
	}-*/;

	/**
	 * Gets a map of IDs mapped to cells
	 *
	 * @return map containing IDs and cells
	 */
	public Map<String, MxICell> getCells() {
		return WrapperUtils.wrapMap(getCellsJS());
	};

	/**
	 * Returns true if the model automatically update parents of edges so that the edge is contained in the nearest-common-ancestor of its terminals.
	 *
	 * @return Returns true if the model maintains edge parents.
	 */
	public native boolean isMaintainEdgeParent() /*-{
		return @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).maintainEdgeParent;
	}-*/;

	/**
	 * Specifies if the model automatically updates parents of edges so that the edge is contained in the nearest-common-ancestor of its terminals.
	 *
	 * @param maintainEdgeParent Boolean indicating if the model should maintain edge parents.
	 */
	public native void setMaintainEdgeParent(boolean maintainEdgeParent) /*-{
		@com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).maintainEdgeParent = maintainEdgeParent;
	}-*/;

	/**
	 * Returns true if the model automatically creates Ids and resolves Id collisions.
	 *
	 * @return Returns true if the model creates Ids.
	 */
	public native boolean isCreateIds() /*-{
		return @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).isCreateIds();
	}-*/;

	/**
	 * Specifies if the model automatically creates Ids for new cells and resolves Id collisions.
	 *
	 * @param value Boolean indicating if the model should created Ids.
	 */
	public native void setCreateIds(boolean createIds) /*-{
		@com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).setCreateIds(createIds);
	}-*/;

	/**
	 * Gets the prefix of new Ids. Default is an empty string.
	 *
	 * @return
	 */
	public native String getPrefix() /*-{
		return @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).prefix;
	}-*/;

	/**
	 * Sets the prefix of new Ids.
	 *
	 * @param prefix
	 */
	public native void setPrefix(String prefix) /*-{
		@com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).prefix = prefix;
	}-*/;

	/**
	 * Gets the postfix of new Ids. Default is an empty string.
	 *
	 * @return
	 */
	public native String getPostfix() /*-{
		return @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).postfix;
	}-*/;

	/**
	 * Sets the postfix of new Ids.
	 *
	 * @param postfix
	 */
	public native void setPostfix(String postfix) /*-{
		@com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).postfix = postfix;
	}-*/;

	/**
	 * Returns the counter for the depth of nested transactions. Each call to {@link #beginUpdate} will increment this number and each call to
	 * {@link $endUpdate} will decrement it. When the counter reaches 0, the transaction is closed and the respective events are fired. Initial value is 0.
	 *
	 * @return counter
	 */
	public native int getUpdateLevel() /*-{
		return @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).updateLevel;
	}-*/;

	/**
	 * Sets a new root using {@link #createRoot}.
	 */
	public native void clear() /*-{
		@com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).clear();
	}-*/;

	/**
	 * Creates a new root cell with a default layer (child 0).
	 */
	public native void createRoot() /*-{
		@com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).createRoot();
	}-*/;

	/**
	 * Returns the {@link MxICell} for the specified Id or null if no cell can be found for the given Id.
	 *
	 * @param id id of the cell
	 * @return cell
	 */
	public native MxICell getCell(String id) /*-{
		var cellJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).getCell(id);
		return @com.mxgraph.impl.util.WrapperUtils::wrap(Lcom/google/gwt/core/client/JavaScriptObject;)(cellJS);
	}-*/;

	public native List<MxICell> filterCells(List<MxICell> cells, Filter filter) /*-{
		var cellsJS = @com.mxgraph.impl.util.WrapperUtils::unwrapList(Ljava/util/List;)(cells);
		var nfd = @com.mxgraph.impl.model.MxGraphModel::createNativeFilterDelegate(Lcom/mxgraph/impl/model/MxGraphModel$Filter;)(filter);
		var filteredCellsJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).filterCells(cellsJS, nfd);
		return @com.mxgraph.impl.util.WrapperUtils::wrapList(Lcom/google/gwt/core/client/JavaScriptObject;)(filteredCellsJS);
	}-*/;

	private native JavaScriptObject getDescendantsJS(MxICell parent) /*-{
		var parentJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(parent);
		return @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).getDescendants(parentJS);
	}-*/;

	/**
	 * Returns all descendants of the given cell and the cell itself in an list.
	 *
	 * @param parent {@link MxICell} whose descendants should be returned.
	 * @return list of descendants
	 */
	public List<MxICell> getDescendants(MxICell parent) {
		return WrapperUtils.wrapList(getDescendantsJS(parent));
	}

	/**
	 * Visits all cells recursively and applies the specified filter to each cell. If the function returns true then the cell is added to the resulting array.
	 * The parent and result parameters are optional. If parent is not specified then the recursion starts at <root>.
	 *
	 * @param filter Java object with a method that takes an {@link MxICell} as an argument and returns a boolean.
	 * @param parent Optional {@link MxICell} that is used as the root of the recursion.
	 * @return
	 */
	public native List<MxICell> filterDescendants(Filter filter, MxICell parent) /*-{
		var parentJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(parent);
		var nfd = @com.mxgraph.impl.model.MxGraphModel::createNativeFilterDelegate(Lcom/mxgraph/impl/model/MxGraphModel$Filter;)(filter);
		var descendantsJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).filterDescendants(nfd,
				parentJS);
		return @com.mxgraph.impl.util.WrapperUtils::wrapList(Lcom/google/gwt/core/client/JavaScriptObject;)(descendantsJS);

	}-*/;

	/**
	 * Returns the root of the model or the topmost parent of the given cell.
	 *
	 * @param cell Optional @{link MxICell} that specifies the child.
	 *
	 * @return root cell
	 */
	public native MxICell getRoot(MxICell cell) /*-{
		var cellJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(cell);
		return @com.mxgraph.impl.util.WrapperUtils::wrap(Lcom/google/gwt/core/client/JavaScriptObject;)(cellJS);
	}-*/;

	/**
	 * Sets the <root> of the model using <mxRootChange> and adds the change to the current transaction. This resets all data structures in the model and is the
	 * preferred way of clearing an existing model. Returns the new root.
	 *
	 * @param root {@link MxICell} that specifies the new root.
	 */
	public native void setRoot(MxICell root) /*-{
		var rootJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(root).root;
		@com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).setRoot(rootJS);
	}-*/;

	/**
	 * Returns true if the given cell is the root of the model and a non-null value.
	 *
	 * @param root {@link MxICell} that represents the possible root.
	 * @return
	 */
	public native boolean isRoot(MxICell root) /*-{
		var rootJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(root).root;
		return @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).isRoot(rootJS);
	}-*/;

	/**
	 * Returns true if <isRoot> returns true for the parent of the given cell.
	 *
	 * @param cell {@link MxICell} that represents the possible layer.
	 * @return
	 */
	public native boolean isLayer(MxICell cell) /*-{
		var cellJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(root).cell;
		return @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).isLayer(cellJS);
	}-*/;

	/**
	 * Returns true if the given parent is an ancestor of the given child.
	 *
	 * @param parent {@link MxICell} that specifies the parent.
	 * @param child {@link MxICell} that specifies the child.
	 * @return
	 */
	public native boolean isAncestor(MxICell parent, MxICell child) /*-{
		var parentJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(parent).cell;
		var childJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(child).cell;
		return @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).isAncestor(parentJS, childJS);
	}-*/;

	/**
	 * Returns true if the model contains the given {@link MxICell}.
	 *
	 * @param cell {@link MxICell} that specifies the cell.
	 * @return
	 */
	public native boolean contains(MxICell cell) /*-{
		var cellJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(root).cell;
		return @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).contains(cellJS);
	}-*/;

	/**
	 * Returns the parent of the given cell.
	 *
	 * @param cell @{link MxICell} whose parent should be returned.
	 * @return parent cell
	 */
	public native MxICell getParent(MxICell cell) /*-{
		var cellJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(cell);
		var parentJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).getParent(cellJS);
		return @com.mxgraph.impl.util.WrapperUtils::wrap(Lcom/google/gwt/core/client/JavaScriptObject;)(parentJS);
	}-*/;

	/**
	 * Adds the specified child to the parent at the given index using @{link MxChildChange} and adds the change to the current transaction. If no index is
	 * specified then the child is appended to the parent's array of children. Returns the inserted child.
	 *
	 * @param parent
	 * @param child
	 * @param index
	 * @return
	 */
	public native MxICell add(MxICell parent, MxICell child, Integer index) /*-{
		var parentJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(parent).cell;
		var childJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(child).cell;
		var indexJS = index != null ? index.@java.lang.Integer::intValue() : null;
		@com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).isAncestor(parentJS, childJS, indexJS);
		return child;
	}-*/;

	/**
	 * Hook method to create an Id for the specified cell. This implementation concatenates <prefix>, id and <postfix> to create the Id and increments <nextId>.
	 * The cell is ignored by this implementation, but can be used in overridden methods to prefix the Ids with eg. the cell type.
	 *
	 * @param cell @{link MxICell} to create the Id for.
	 * @return created Id
	 */
	public native String createId(MxICell cell) /*-{
		var cellJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(cell);
		return @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).createId(cellJS);
	}-*/;

	/**
	 * Updates the parent for all edges that are connected to cell or one of its descendants
	 *
	 * @param cell
	 * @param root
	 */
	public native void updateEdgeParents(MxICell cell, MxICell root) /*-{
		var cellJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(cell);
		var rootJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(root);
		@com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).updateEdgeParents(cellJS, rootJS);
	}-*/;

	/**
	 * Returns the absolute, accumulated origin for the children inside the given parent as an {@link MxPoint}.
	 *
	 * @param cell
	 * @return
	 */
	//	public native MxPoint getOrigin(MxICell cell) /*-{
	//		var cellJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(cell);
	//		var pointJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).getOrigin(cellJS);
	//		return @com.mxgraph.impl.util.WrapperUtils::wrap(Lcom/google/gwt/core/client/JavaScriptObject;)(pointJS);
	//	}-*/;

	/**
	 * Returns the nearest common ancestor for the specified cells.
	 *
	 * @param cell1 {@link MxICell} that specifies the first cell in the tree.
	 * @param cell2 {@link MxICell} that specifies the second cell in the tree.
	 * @return
	 */
	public native MxICell getNearestCommonAncestor(MxICell cell1, MxICell cell2) /*-{
		var cell1JS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(cell1);
		var cell2JS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(cell2);
		var cellJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).getNearestCommonAncestor(cell1JS,
				cell2JS);
		return @com.mxgraph.impl.util.WrapperUtils::wrap(Lcom/google/gwt/core/client/JavaScriptObject;)(cellsJS);
	}-*/;

	/**
	 * Removes the specified cell from the model using <MxChildChange> and adds the change to the current transaction. This operation will remove the cell and
	 * all of its children from the model. Returns the removed cell.
	 *
	 * @param cell {@link MxICell} that should be removed.
	 * @return removed cell
	 */
	public native MxICell remove(MxICell cell) /*-{
		var cellJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(cell);
		@com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).remove(cellJS);
		return cell;
	}-*/;

	/**
	 * Returns the number of children in the given cell.
	 *
	 * @param cell {@link MxICell} whose number of children should be returned.
	 * @return child count
	 */
	public native int getChildCount(MxICell cell) /*-{
		var cellJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(cell);
		return @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).getChildCount(cellJS);
	}-*/;

	/**
	 * Returns the child of the given {@link MxICell} at the given index.
	 *
	 * @param index Integer that specifies the index of the child to be returned.
	 * @return MxICell at specified position or null
	 */
	public native MxICell getChildAt(int index) /*-{
		var childJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).getChildAt(index);
		return @com.mxgraph.impl.util.WrapperUtils::wrap(Lcom/google/gwt/core/client/JavaScriptObject;)(childJS);
	}-*/;

	/**
	 * Returns the child of the given {@link MxICell} at the given index.
	 *
	 * @param cell {@link MxICell} that represents the parent.
	 * @return
	 */
	public native List<MxICell> getChildren(MxICell cell) /*-{
		var cellJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(cell);
		var childrenJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).getChildren(cellJS);
		return @com.mxgraph.impl.util.WrapperUtils::wrapList(Lcom/google/gwt/core/client/JavaScriptObject;)(childrenJS);
	}-*/;

	/**
	 * Returns the child vertices of the given parent.
	 *
	 * @param parent {@link MxICell} whose child vertices should be returned.
	 * @return
	 */
	public native List<MxICell> getChildVertices(MxICell parent) /*-{
		var parentJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(parent);
		var childVerticesJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).getChildVertices(parentJS);
		return @com.mxgraph.impl.util.WrapperUtils::wrapList(Lcom/google/gwt/core/client/JavaScriptObject;)(childVerticesJS);
	}-*/;

	/**
	 * Returns the child edges of the given parent.
	 *
	 * @param parent {@link MxICell} whose child edges should be returned.
	 * @return child edges
	 */
	public native List<MxICell> getChildEges(MxICell parent) /*-{
		var parentJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(parent);
		var childEdgesJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).getChildEges(parentJS);
		return @com.mxgraph.impl.util.WrapperUtils::wrapList(Lcom/google/gwt/core/client/JavaScriptObject;)(childEdgesJS);
	}-*/;

	/**
	 * Returns the children of the given cell that are vertices and/or edges depending on the arguments.
	 *
	 * @param parent {@link MxICell} the represents the parent.
	 * @param vertices Boolean indicating if child vertices should be returned. Default is false.
	 * @param edges Boolean indicating if child edges should be returned. Default is false.
	 * @return list of child cells
	 */
	public native List<MxICell> getChildCells(MxICell parent, boolean vertices, boolean edges) /*-{
		var parentJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(parent);
		var childCellsJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).getChildCells(parentJS);
		return @com.mxgraph.impl.util.WrapperUtils::wrapList(Lcom/google/gwt/core/client/JavaScriptObject;)(childCellsJS);
	}-*/;

	/**
	 * Returns the source or target {@link MxICell} of the given edge depending on the value of the boolean parameter.
	 *
	 * @param edge {@link MxICell} that specifies the edge.
	 * @param isSource Boolean indicating which end of the edge should be returned.
	 * @return
	 */
	public native MxICell getTerminal(MxICell edge, boolean isSource) /*-{
		var edgeJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(edge);
		var terminalJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).getTerminal(edgeJS, isSource);
		return @com.mxgraph.impl.util.WrapperUtils::wrap(Lcom/google/gwt/core/client/JavaScriptObject;)(terminalJS);
	}-*/;

	/**
	 * Sets the source or target terminal of the given {@link MxICell} using MxTerminalChange and adds the change to the current transaction. This implementation
	 * updates the parent of the edge using {@link #updateEdgeParents(MxICell, MxICell)} if required.
	 *
	 * @param edge {@link MxICell} that specifies the edge.
	 * @param terminal {@link MxICell} that specifies the new terminal.
	 * @param isSource Boolean indicating if the terminal is the new source or target terminal of the edge.
	 */
	public native void setTerminal(MxICell edge, MxICell terminal, boolean isSource) /*-{
		var edgeJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(edge);
		var terminalJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(terminal);
		@com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).setTerminal(edgeJS, terminalJS, isSource);
	}-*/;

	/**
	 * Sets the source and target {@link MxICell} of the given MxICell in a single transaction using {@link #setTerminal(MxICell, MxICell, boolean)} for each end of
	 * the edge.
	 *
	 * @param edge <MxICell> that specifies the edge.
	 * @param source <MxICell> that specifies the new source terminal.
	 * @param target <MxICell> that specifies the new target terminal.
	 */
	public native void setTerminals(MxICell edge, MxICell source, MxICell target) /*-{
		var edgeJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(edge);
		var sourceJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(source);
		var targetJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(target);
		@com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).setTerminals(edgeJS, sourceJS, targetJS);
	}-*/;

	/**
	 * Returns the number of distinct edges connected to the given cell.
	 *
	 * @param cell {@link MxICell} that represents the vertex.
	 * @return
	 */
	public native int getEdgeCount(MxICell cell) /*-{
		var cellJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(cell);
		return @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).getEdgeCount(cellJS);
	}-*/;

	/**
	 * Returns the edge of cell at the given index.
	 *
	 * @param cell {@link MxICell} that specifies the vertex.
	 * @param index Integer that specifies the index of the edge
	 * @return
	 */
	public native MxICell getEdgeAt(MxICell cell, int index) /*-{
		var cellJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(cell);
		var edgeJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).getEdgeAt(cellJS);
		return @com.mxgraph.impl.util.WrapperUtils::wrap(Lcom/google/gwt/core/client/JavaScriptObject;)(edgeJS);
	}-*/;

	/**
	 * Returns the number of incoming or outgoing edges, ignoring the given edge.
	 *
	 * @param cell MxICell whose edge count should be returned.
	 * @param outgoing Boolean that specifies if the number of outgoing or incoming edges should be returned.
	 * @param ignoredEdge MxICell that represents an edge to be ignored.
	 * @return
	 */
	public native int getDirectedEdgeCount(MxICell cell, boolean outgoing, MxICell ignoredEdge) /*-{
		var cellJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(cell);
		var ignoredEdgeJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(ignoredEdge);
		return @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).getDirectedEdgeCount(cellJS, outgoing,
				ignoredEdgeJS);
	}-*/;

	/**
	 * Returns all edges of the given cell without loops.
	 *
	 * @param cell {@link MxICell} whose edges should be returned.
	 * @return
	 */
	public native List<MxICell> getConnections(MxICell cell) /*-{
		var cellJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(cell);
		var connectionsJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).getConnections(cellJS);
		return @com.mxgraph.impl.util.WrapperUtils::wrapList(Lcom/google/gwt/core/client/JavaScriptObject;)(connectionsJS);
	}-*/;

	/**
	 * Returns the incoming edges of the given cell without loops.
	 *
	 * @param cell {@link MxICell} whose incoming edges should be returned.
	 * @return
	 */
	public native List<MxICell> getIncomingEdges(MxICell cell) /*-{
		var cellJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(cell);
		var edgesJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).getIncomingEdges(cellJS);
		return @com.mxgraph.impl.util.WrapperUtils::wrapList(Lcom/google/gwt/core/client/JavaScriptObject;)(edgesJS);
	}-*/;

	/**
	 * Returns the outgoing edges of the given cell without loops.
	 *
	 * @param cell {@link MxICell} whose outgoing edges should be returned.
	 * @return
	 */
	public native List<MxICell> getOutgoingEdges(MxICell cell) /*-{
		var cellJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(cell);
		var edgesJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).getOutgoingEdges(cellJS);
		return @com.mxgraph.impl.util.WrapperUtils::wrapList(Lcom/google/gwt/core/client/JavaScriptObject;)(edgesJS);
	}-*/;

	/**
	 * Returns all edges connected to this cell including loops.
	 *
	 * @param cell cell whose edges should be returned.
	 * @return list of edges
	 */
	public List<MxICell> getEdges(MxICell cell) {
		return getEdges(cell, true, true, true);
	}

	/**
	 * Returns all distinct edges connected to this cell as a new array of {@link mxICellState}. If at least one of incoming or outgoing is true, then loops are
	 * ignored, otherwise if both are false, then all edges connected to the given cell are returned including loops.
	 *
	 * @param cell {@link MxICell} that specifies the cell.
	 * @param incoming boolean that specifies if incoming edges should be returned.
	 * @param outgoing boolean that specifies if outgoing edges should be returned.
	 * @param includeLoops boolean that specifies if loops should be returned.
	 * @return Returns the list of connected edges for the given cell.
	 */
	public native List<MxICell> getEdges(MxICell cell, boolean incoming, boolean outgoing, boolean includeLoops) /*-{
		var cellJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(cell);
		var edgesJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).getEdges(cellJS);
		return @com.mxgraph.impl.util.WrapperUtils::wrapList(Lcom/google/gwt/core/client/JavaScriptObject;)(edgesJS);
	}-*/;

	/**
	 * Returns all edges from the given source to the given target.
	 *
	 * @param source the source cell.
	 * @param target the target cell.
	 * @return Returns all edges from source to target.
	 */
	public List<MxICell> getEdgesBetween(MxICell source, MxICell target) {
		return getEdgesBetween(source, target, false);
	}

	/**
	 * Returns all edges between the given source and target pair. If directed is true, then only edges from the source to the target are returned, otherwise,
	 * all edges between the two cells are returned.
	 *
	 * @param source {@link MxICell} that defines the source terminal of the edge to be returned.
	 * @param target {@link MxICell} that defines the target terminal of the edge to be returned.
	 * @param directed boolean that specifies if the direction of the edge should be taken into account.
	 * @return list of edges
	 */
	public native List<MxICell> getEdgesBetween(MxICell source, MxICell target, boolean directed) /*-{
		var sourceJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(source);
		var targetJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(target);
		var edgesJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).getEdgesBetween(sourceJS, targetJS,
				directed);
		return @com.mxgraph.impl.util.WrapperUtils::wrapList(Lcom/google/gwt/core/client/JavaScriptObject;)(edgesJS);
	}-*/;

	/**
	 * Returns all opposite vertices wrt terminal for the given edges, only returning sources and/or targets as specified. The result is returned as a list of
	 * MxICell.
	 *
	 * @param edges list of mxICells that contain the edges to be examined.
	 * @param terminal {@link MxICell} that specifies the known end of the edges.
	 * @param sources Boolean that specifies if source terminals should be contained in the result.
	 * @param targets Boolean that specifies if target terminals should be contained in the result.
	 * @return list of cells
	 */
	public native List<MxICell> getOpposites(List<MxICell> edges, MxICell terminal, boolean sources, boolean targets) /*-{
		var edgesJS = @com.mxgraph.impl.util.WrapperUtils::unwrapList(Ljava/util/List;)(edges);
		var sourceJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(cell);
	}-*/;

	/**
	 * Returns the topmost cells of the hierarchy in an array that contains no descendants for each {@link MxICell} that it contains. Duplicates should be
	 * removed in the cells array to improve performance.
	 *
	 * @param cells list of cells whose topmost ancestors should be returned.
	 * @return
	 */
	public native List<MxICell> getTopmostCells(List<MxICell> cells) /*-{
		var cellsJS = @com.mxgraph.impl.util.WrapperUtils::unwrapList(Ljava/util/List;)(cells);
		var topCellsJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).getTopmostCells(cellsJS);
		return @com.mxgraph.impl.util.WrapperUtils::wrapList(Lcom/google/gwt/core/client/JavaScriptObject;)(topCellsJS);
	}-*/;

	/**
	 * Returns true if the given cell is a vertex.
	 *
	 * @param cell {@link MxICell} that represents the possible vertex.
	 * @return
	 */
	public native boolean isVertex(MxICell cell) /*-{
		var cellJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(cell);
		var retvalJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).isVertex(cellJS);
		return typeof retvalJS == "boolean" ? retvalJS : retvalJS == 0 ? false : true;
	}-*/;

	/**
	 * Returns true if the given cell is an edge.
	 *
	 * @param cell {@link MxICell} that represents the possible edge.
	 * @return
	 */
	public native boolean isEdge(MxICell cell) /*-{
		var cellJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(cell);
		var retvalJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).isEdge(cellJS);
		return typeof retvalJS == "boolean" ? retvalJS : retvalJS == 0 ? false : true;
	}-*/;

	/**
	 * Returns true if the given {@link MxICell} is connectable. If edgesConnectable is false, then this function returns false for all edges else it returns the
	 * return value of {@link MxICell#isConnectable()}.
	 *
	 * @param cell MxICell whose connectable state should be returned.
	 * @return
	 */
	public native boolean isConnectable(MxICell cell) /*-{
		var cellJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(cell);
		return @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).isConnectable(cellJS);
	}-*/;

	/**
	 * Returns the user object of the given {@link MxICell} using {@link MxICell#getValue()}.
	 *
	 * @param cell {@link MxICell} whose user object should be returned.
	 * @return Returns the user object of the given cell.
	 */
	public native Object getValue(MxICell cell) /*-{
		var cellJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(cell);
		return @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).getValue(cellsJS);
	}-*/;

	/**
	 * Sets the user object of then given {@link MxICell} using {@link MxValueChange} and adds the change to the current transaction.
	 *
	 * @param cell {@link MxICell} whose user object should be changed.
	 * @param value Object that defines the new user object.
	 * @return Returns the new value.
	 */
	public native String setValue(MxICell cell, Object value) /*-{
		var cellJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(cell);
		return @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).setValue(cellJS, value);
	}-*/;

	/**
	 * Returns the {@link MxGeometry} of the given {@link MxICell}.
	 *
	 * @param cell
	 * @return
	 */
	//	public native MxGeometry getGeometry(MxICell cell) /*-{
	//		var cellJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(cell);
	//		var geometryJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).getGeometry(cellJS);
	//		return @com.mxgraph.impl.util.WrapperUtils::wrap(Lcom/google/gwt/core/client/JavaScriptObject;)(geometryJS);
	//	}-*/;

	public static abstract class MxChange implements IJavaScriptWrapper {

		protected JavaScriptObject jso;

		protected MxChange() {
		}

		@Override public JavaScriptObject getJso() {
			return jso;
		}

		@Override public void setJso(JavaScriptObject jso) {
			this.jso = jso;
		}

		public native void execute() /*-{
			@com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).execute();
		}-*/;
	}

	public static class mxRootChange extends MxChange {

		private native JavaScriptObject createJso(JavaScriptObject model, JavaScriptObject root) /*-{
			return new $wnd.mxRootChange(model, root);
		}-*/;

		private mxRootChange() {
		}

		public mxRootChange(MxGraphModel model, MxICell root) {
			jso = createJso(model.getJso(), root.getJso());
		}
	}

	public static class MxChildChange extends MxChange {

		private native JavaScriptObject createJso(JavaScriptObject model, JavaScriptObject parent, JavaScriptObject child, int index) /*-{
			return new $wnd.MxChildChange(model, parent, child, index);
		}-*/;

		private MxChildChange() {
		}

		public MxChildChange(MxGraphModel model, MxICell parent, MxICell child, int index) {
			jso = createJso(model.getJso(), parent.getJso(), child.getJso(), index);
		}
	}

	public static class MxTerminalChange extends MxChange {

		private native JavaScriptObject createJso(JavaScriptObject model, JavaScriptObject cell, JavaScriptObject terminal, JavaScriptObject source) /*-{
			return new $wnd.MxTerminalChange(model, cell, terminal, source);
		}-*/;

		private MxTerminalChange() {
		}

		public MxTerminalChange(MxGraphModel model, MxICell cell, MxICell terminal, MxICell source) {
			jso = createJso(model.getJso(), cell.getJso(), terminal.getJso(), source.getJso());
		}
	}

	public static class MxValueChange extends MxChange {

		private native JavaScriptObject createJso(JavaScriptObject model, JavaScriptObject cell, Object value) /*-{
			return new $wnd.MxValueChange(model, cell, value);
		}-*/;

		private MxValueChange() {
		}

		public MxValueChange(MxGraphModel model, MxICell cell, Object value) {
			jso = createJso(model.getJso(), cell.getJso(), value);
		}
	}

	public static class MxStyleChange extends MxChange {

		private native JavaScriptObject createJso(JavaScriptObject model, JavaScriptObject cell, String style) /*-{
			return new $wnd.MxStyleChange(model, cell, style);
		}-*/;

		private MxStyleChange() {
		}

		public MxStyleChange(MxGraphModel model, MxICell cell, String style) {
			jso = createJso(model.getJso(), cell.getJso(), style);
		}
	}

	public static class MxGeometryChange extends MxChange {

		private native JavaScriptObject createJso(JavaScriptObject model, JavaScriptObject cell, JavaScriptObject geometry) /*-{
			return new $wnd.MxGeometryChange(model, cell, geometry);
		}-*/;

		private MxGeometryChange() {
		}

		//		public MxGeometryChange(MxGraphModel model, MxICell cell, MxGeometry geometry) {
		//			jso = createJso(model.getJso(), cell.getJso(), geometry.getJso());
		//		}
	}

	public static class mxCollapseChange extends MxChange {

		private native JavaScriptObject createJso(JavaScriptObject model, JavaScriptObject cell, boolean collapse) /*-{
			return new $wnd.mxCollapseChange(model, cell, collapse);
		}-*/;

		private mxCollapseChange() {
		}

		public mxCollapseChange(MxGraphModel model, MxICell cell, boolean collapse) {
			jso = createJso(model.getJso(), cell.getJso(), collapse);
		}
	}

	public static class mxVisibleChange extends MxChange {

		private native JavaScriptObject createJso(JavaScriptObject model, JavaScriptObject cell, boolean visible) /*-{
			return new $wnd.mxVisibleChange(model, cell, visible);
		}-*/;

		private mxVisibleChange() {
		}

		public mxVisibleChange(MxGraphModel model, MxICell cell, boolean visible) {
			jso = createJso(model.getJso(), cell.getJso(), visible);
		}
	}

	public static class MxCellAttributeChange extends MxChange {

		private native JavaScriptObject createJso(JavaScriptObject cell, String attribute, String value) /*-{
			return new $wnd.MxCellAttributeChange(cell, attribute, value);
		}-*/;

		private MxCellAttributeChange() {
		}

		public MxCellAttributeChange(MxICell cell, String attribute, String value) {
			jso = createJso(cell.getJso(), attribute, value);
		}
	}

	/**
	 * Returns the style of the given {@link MxICell}.
	 *
	 * @param cell
	 * @return
	 */
	public native String getStyle(MxICell cell) /*-{
		var cellJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(cell);
		return @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).getStyle(cellJS);
	}-*/;

	/**
	 * Sets the {@link MxGeometry} of the given {@link MxICell}. The actual update of the cell is carried out in <geometryForCellChanged>. The
	 * {@link MxGeometryChange} action is used to encapsulate the change.
	 *
	 * @param cell
	 * @param geometry
	 * @return
	 */
	//	public native MxGeometry setGeometry(MxICell cell, MxGeometry geometry) /*-{
	//		var cellJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(cell);
	//		var geometryJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(geometry);
	//		var retvalJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).setGeometry(cellJS, geometryJS);
	//		return @com.mxgraph.impl.util.WrapperUtils::wrap(Lcom/google/gwt/core/client/JavaScriptObject;)(retvalJS);
	//	}-*/;

	/**
	 * Returns true if the given {@link MxICell} is visible.
	 *
	 * @param cell {@link MxICell} whose visible state should be returned.
	 * @return
	 */
	public native boolean isVisible(MxICell cell) /*-{
		var cellJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(cell);
		return @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).isVisible(cellJS);
	}-*/;

	/**
	 * Sets the visible state of the given {@link MxICell} using {@link mxVisibleChange} and adds the change to the current transaction.
	 *
	 * @param cell {@link MxICell} whose visible state should be changed.
	 * @param visible Boolean that specifies the new visible state.
	 * @return
	 */
	public native boolean setVisible(MxICell cell, boolean visible) /*-{
		var cellJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(cell);
		return @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).setVisible(cellJS, visible);
	}-*/;

	/**
	 * Merges the children of the given cell into the given target cell inside this model. All cells are cloned unless there is a corresponding cell in the
	 * model with the same id, in which case the source cell is ignored and all edges are connected to the corresponding cell in this model. Edges are
	 * considered to have no identity and are always cloned unless the cloneAllEdges flag is set to false, in which case edges with the same id in the target
	 * model are reconnected to reflect the terminals of the source edges.
	 *
	 * @param from source {@link MxICell}
	 * @param to target {@link MxICell}
	 * @param cloneAllEdges optional boolean specifying if all edges should be cloned
	 */
	public native void mergeChildren(MxICell from, MxICell to, Boolean cloneAllEdges) /*-{
		var fromJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(from);
		var toJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(to);
		var cloneAllEdgesJS = cloneAllEdges != null ? cloneAllEdges.@java.lang.Boolean::booleanValue()() : null;
		@com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).mergeChildren(fromJS, toJS, cloneAllEdgesJS);

	}-*/;


	/**
	 * Executes the given edit and fires events if required. The edit object requires an execute function which is invoked. The edit is added to the
	 * currentEdit between beginUpdate and endUpdate calls, so that events will be fired if this execute is an individual transaction, that is, if no
	 * previous beginUpdate calls have been made without calling endUpdate. This implementation fires an execute event before executing the given change.
	 *
	 * @param change
	 */
	public native void execute(MxChange change) /*-{
		var changeJS = @com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(change);
		@com.mxgraph.impl.util.WrapperUtils::unwrap(Lcom/mxgraph/jso/IJavaScriptWrapper;)(this).execute(changeJS);
	}-*/;
}
