package view.frame;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import actions.ActionManager;
import model.Entity;
import model.Database;
import start.AppCore;

public class MainFrame extends JFrame{
	
	private static final long serialVersionUID = 1L;
	private static MainFrame instance = null;
	private AppCore appCore;
	private ActionManager actionManager;
	private JScrollPane jscroll;
	private JPanel mainPanel;
	private JPanel scrollPanel;
	private JButton btnDelete;
	private JButton btnEdit;
	private JButton btnAdd;
	private JButton btnFilter;
	private JButton btnSort;
    private JTable jt;


    private MainFrame(){}

	public static MainFrame getInstance(){
		if (instance == null){
			instance  = new MainFrame();
			instance.initialize();
		}
		return instance;
	}

	private void initialize(){
		appCore = new AppCore();
	    actionManager = ActionManager.getInstance();
	    JFileChooser chooser = new JFileChooser("./Files");
        JPanel p = new JPanel(new GridBagLayout());
        JButton confirmBtn  =  new JButton();
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 0);
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx = 0;
        c.gridy = 0;

            chooser.showDialog(null,"Open");
            Database.getInstance().setPath(chooser.getCurrentDirectory().getPath()+"/file");
        try {
            appCore.getStorage().loadDatabase(chooser.getCurrentDirectory().getPath());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e.getMessage());
        }

        initializeGUI();
           // File selectedFile = jfc.getCurrentDirectory();
    }


	public void initializeGUI() {
        btnAdd = new JButton("Add");
        btnDelete = new JButton("Delete");
        btnFilter = new JButton("Filter");
        btnSort = new JButton("Sort");
        btnEdit = new JButton("Edit");

		mainPanel = new JPanel();

		btnAdd.addActionListener(actionManager.getAddButtonAction());
        btnDelete.addActionListener(actionManager.getDeleteButtonAction());
        btnFilter.addActionListener(actionManager.getFilterButtonAction());
        btnSort.addActionListener(actionManager.getSortButtonAction());
        btnEdit.addActionListener(actionManager.getEditButtonAction());

		Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        screenWidth = screenWidth - (screenWidth/2)/2;
        screenHeight = screenHeight - (screenHeight/2)/2+100;
        setSize(screenWidth , screenHeight );
        setLocationRelativeTo(null);


        jt = appCore.loadTable(Database.getInstance().getEntities());
        jt.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        jt.setDefaultEditor(Object.class, null);


        jscroll = new JScrollPane(jt);
        jscroll.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jscroll.setPreferredSize(new Dimension(900,500));

        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        scrollPanel = new JPanel();
        scrollPanel.add(jscroll);
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 25;
        mainPanel.add(scrollPanel,c);

        c.gridwidth = 1;

        c.gridx = 0;
        c.gridy = 1;
        mainPanel.add(btnAdd,c);

        c.weightx = c.weighty = 0;
        c.fill = GridBagConstraints.NONE;
        c.gridx = 1;
        c.gridy = 1;
        mainPanel.add(btnDelete,c);

        c.weightx = c.weighty = 0;
        c.fill = GridBagConstraints.NONE;
        c.gridx = 2;
        c.gridy = 1;
        mainPanel.add(btnSort,c);

        c.weightx = c.weighty = 0;
        c.fill = GridBagConstraints.NONE;
        c.gridx = 3;
        c.gridy = 1;
        mainPanel.add(btnFilter,c);

        c.weightx = c.weighty = 0;
        c.fill = GridBagConstraints.NONE;
        c.gridx = 4;
        c.gridy = 1;
        mainPanel.add(btnEdit,c);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(mainPanel);

        setVisible(true);
        repaint();
        revalidate();
	}
	
	public void setAppCore(AppCore appCore) {
        this.appCore = appCore;
    }
	
	public AppCore getAppCore() {
		return appCore;
	}

    public void setJt(JTable jt) {

        this.jt = jt;
        jt.setDefaultEditor(Object.class, null);
        GridBagConstraints c = new GridBagConstraints();
        jt.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        mainPanel.remove(scrollPanel);
        scrollPanel = new JPanel();
        jscroll = new JScrollPane(jt);
        jscroll.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jscroll.setPreferredSize(new Dimension(900,500));
        jt.setAutoCreateRowSorter(true);
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 25;
        scrollPanel.add(jscroll);
        mainPanel.add(scrollPanel,c);
        revalidate();
        repaint();
    }

    public ActionManager getActionManager() {
		return actionManager;
	}

}
