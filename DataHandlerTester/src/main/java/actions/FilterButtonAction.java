package actions;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;

import model.Entity;
import service.Database;
import start.AppCore;
import start.Main;
import view.frame.MainFrame;

public class FilterButtonAction implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		JPanel mainPanel = new JPanel(new GridBagLayout());

		JLabel lblId = new JLabel("ID:");
		JLabel lblType = new JLabel("Type:");
		JLabel lblAttribute = new JLabel("Attribute:");
		JLabel lblNested = new JLabel("Nested:");


		JPanel p = new JPanel(new FlowLayout());
		GridBagConstraints c = new GridBagConstraints();
		JTextField jfId = new JTextField(5);
		JTextField jfType = new JTextField(5);
		JTextField jfAttribute = new JTextField(5);
		JTextField jfNested = new JTextField(5);


		p.add(lblId);
		p.add(jfId);
		p.add(lblType);
		p.add(jfType);
		p.add(lblAttribute);
		p.add(jfAttribute);
		p.add(lblNested);
		p.add(jfNested);

		mainPanel.add(p);

		if( JOptionPane.showConfirmDialog(null,mainPanel,"Fill this form to filter",JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION)
		{
			String idToFilter = ((JTextField)(p.getComponent(0))).getText();
			String typeToFilter = ((JTextField)(p.getComponent(1))).getText();
			Map <String,String> attributesToFilter = new HashMap <String,String>();
			Map <String, Entity> NestedEntityToFilter = new HashMap <>();

			String attributes = ((JTextField)(p.getComponent(2))).getText();
			String[] res = attributes.split("[,]", 0);

			for(String myStr: res) {
				String[] kv = attributes.split("[.]", 0);
				attributesToFilter.put(kv[0],kv[1]);
			}
			for(Entity et : Database.getInstance().getEntities()){
				if(et.getId() == ((JTextField)(p.getComponent(3))).getText()){
					NestedEntityToFilter.put(et.getId(), et);
					break;
				}
			}
			//  MainFrame.getInstance().setJt(MainFrame.getInstance().getAppCore().loadTable(Database.getInstance().getEntities())); TODO UPDATE TABLE
		}




	}
		
}
	

