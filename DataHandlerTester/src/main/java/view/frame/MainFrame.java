package view.frame;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeListener;

import actions.ActionManager;
//import resource.implementation.InformationResource;
//import observer.Notification;
//import observer.Subscriber;
//import observer.enums.NotificationCode;
import start.AppCore;

// implements Subscriber
public class MainFrame extends JFrame{
	
	private static final long serialVersionUID = 1L;
	private static MainFrame instance = null;
	private AppCore appCore;
	private ActionManager actionManager;
	private JSplitPane horizontalScreenSplit;
	private JSplitPane rightVerticalSplit;
	private JScrollPane mid;
	private JTabbedPane upperPane;
	private JTabbedPane lowerPane;
	private JPanel upperPanel;
	private JPanel lowerPanel;
	private JButton btnRefresh;
	private JButton btnDelete;
	private JButton btnAdd;
	private JButton btnFilter;
	private JButton btnSort;
	private JButton btnDelete1;
	private JButton btnEdit;
	private JButton btnUpdate1;
	
	private MainFrame(){}

	private void initialize(){

	    actionManager = ActionManager.getInstance();
        JFileChooser chooser = new JFileChooser();
        JPanel p = new JPanel(new GridBagLayout());
        JButton confirmBtn  =  new JButton();
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 0);
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx = 0;
        c.gridy = 0;

        p.add(chooser,c);



        if( JOptionPane.showConfirmDialog(null,p,"Fill this form to add",JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION)
        {
            // neka load u table funkcija

        }
        initializeGUI();

    }
	
	public void initializeGUI() {
		btnSort = new JButton("Sort");
		btnFilter = new JButton("Filter");
		btnAdd = new JButton("Add");
		btnEdit = new JButton("Edit");
//		btnUpdate = new JButton("Update");
		btnDelete = new JButton("Delete");
//		btnRefresh = new JButton("Refresh");
//		btnAdd1 = new JButton("Add");
//		btnUpdate1= new JButton("Update");
//		btnDelete1 = new JButton("Delete");
//		btnRefresh1 = new JButton("Refresh");
//		upperPanel = new JPanel();
//		lowerPanel = new JPanel();
//
//		btnAdd.setName("add");
//		btnUpdate.setName("update");
		btnDelete.setName("delete");
//		btnAdd1.setName("add1");
//		btnUpdate1.setName("update1");
//		btnDelete1.setName("delete1");
//
		btnAdd.addActionListener(actionManager.getAddButtonAction());
        btnFilter.addActionListener(actionManager.getAddButtonAction());
		btnSort.addActionListener(actionManager.getUpdateButtonAction());
        btnEdit.addActionListener(actionManager.getUpdateButtonAction());
		btnRefresh.addActionListener(actionManager.getRefreshAction());
		btnDelete.addActionListener(actionManager.getDeleteButtonAction());

		Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        screenWidth = screenWidth - (screenWidth/2)/2;
        screenHeight = screenHeight - (screenHeight/2)/2+100;
        setSize(screenWidth , screenHeight );
        setLocationRelativeTo(null);

        String[] columnNames = {"First Name",
                "Last Name",
                "Sport",
                "# of Years",
                "Vegetarian"};

        Object[][] data = {
                {"Kathy", "Smith",
                        "Snowboarding", new Integer(5), new Boolean(false)},
                {"John", "Doe",
                        "Rowing", new Integer(3), new Boolean(true)},
                {"Sue", "Black",
                        "Knitting", new Integer(2), new Boolean(false)},
                {"Jane", "White",
                        "Speed reading", new Integer(20), new Boolean(true)},
                {"Joe", "Brown",
                        "Pool", new Integer(10), new Boolean(false)}
        };

        JPanel jp = new JPanel();
        JPanel tablePanel = new JPanel();
        upperPanel = new JPanel();
        mid = new JScrollPane();
        JTable jt = new JTable(data, columnNames);
        mid.add(jt);
        mid.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        upperPanel.setLayout(new GridBagLayout());
        tablePanel.setBorder(BorderFactory.createLineBorder(Color.black));
        GridBagConstraints c = new GridBagConstraints();
      //  c.insets = new Insets(10, 0, 10, 0);
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 10;
        upperPanel.add(jt,c);

        c.gridwidth = 1;

        c.gridx = 0;
        c.gridy = 1;
        upperPanel.add(btnAdd,c);

        c.weightx = c.weighty = 0;
        c.fill = GridBagConstraints.NONE;
        c.gridx = 1;
        c.gridy = 1;
        upperPanel.add(btnDelete,c);

        c.weightx = c.weighty = 0;
        c.fill = GridBagConstraints.NONE;
        c.gridx = 2;
        c.gridy = 1;
        upperPanel.add(btnSort,c);

        c.weightx = c.weighty = 0;
        c.fill = GridBagConstraints.NONE;
        c.gridx = 3;
        c.gridy = 1;
        upperPanel.add(btnFilter,c);

        c.weightx = c.weighty = 0;
        c.fill = GridBagConstraints.NONE;
        c.gridx = 4;
        c.gridy = 1;
        upperPanel.add(btnEdit,c);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(upperPanel);
        System.out.println(upperPanel);

        setVisible(true);
        
	}
	
	public void setAppCore(AppCore appCore) {
        this.appCore = appCore;
    //    this.appCore.addSubscriber(this);
        //this.jTable.setModel(appCore.getTableModel());
    }
	
	public AppCore getAppCore() {
		return appCore;
	}

	public static MainFrame getInstance(){
		   if (instance == null){
			  instance  = new MainFrame();
			  instance.initialize();
		   }
		   return instance;
	   }

//	@Override
//	public void update(Notification notification) {
//		if (notification.getCode().equals(NotificationCode.RESOURCE_LOADED))
//		{
//			dbTree.setModel(new DefaultTreeModel(((InformationResource)notification.getData())));
//			dbTree.repaint();
//			this.repaint();
//		}
//	}
	


	public ActionManager getActionManager() {
		return actionManager;
	}
	
	public JTabbedPane getUpperPane() {
		return upperPane;
	}
	
	public JTabbedPane getLowerPane() {
		return lowerPane;
	}
	
	public void closeLowerPane() {
		lowerPane.removeAll();
	}
	
}
