package actions;

public class ActionManager {
	private static ActionManager instance = null;

//	private AddAction addAction;
//	private SortFilterAction sortAction;
//	private DeleteAction deleteActioin;
//	private FilterAction filterActionL;
//	private EditAction editAction;

	private ActionManager() {
		initializeActions();
	}
	
	private void initializeActions() {

//		addButtonAction = new AddButtonAction();
//		sortFilterAction = new SortFilterAction();
//		deleteActioin = new DeleteAction();
//		filterAction = new filterAction();
//		editAction - new editAction();
	}
	
	public static ActionManager getInstance() {
		if (instance == null) {
			instance = new ActionManager();
		}
		return instance;
	}

	
	/* public SortFilterAction getSortFilterAction() {
		return sortFilterAction;
	}

	
	public DeleteAction getDeleteAction() {
		return deleteAction;
	}
	

	public AddButtonAction getAddButtonAction() {
		return addButtonAction;
	}


	*/
}
