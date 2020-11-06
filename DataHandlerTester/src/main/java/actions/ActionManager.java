package actions;

public class ActionManager {
	private static ActionManager instance = null;

	private AddButtonAction addButtonAction;
	private SortButtonAction sortButtonAction;
	private DeleteButtonAction deleteButtonAction;
	private FilterButtonAction filterButtonAction;
//	private EditAction editAction;

	private ActionManager() {
		initializeActions();
	}
	
	private void initializeActions() {

		addButtonAction = new AddButtonAction();
		deleteButtonAction = new DeleteButtonAction();
		filterButtonAction = new FilterButtonAction();
		sortButtonAction = new SortButtonAction();
//		editAction - new editAction();
	}
	
	public static ActionManager getInstance() {
		if (instance == null) {
			instance = new ActionManager();
		}
		return instance;
	}

	
	public FilterButtonAction getFilterButtonAction() {
		return filterButtonAction;
	}

	public AddButtonAction getAddButtonAction() {
		return addButtonAction;
	}

	public DeleteButtonAction getDeleteButtonAction() {
		return deleteButtonAction;
	}

	public SortButtonAction getSortButtonAction(){return sortButtonAction;}


}
