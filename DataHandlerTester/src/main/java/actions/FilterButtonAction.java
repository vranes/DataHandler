package actions;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import Exceptions.IdentifierException;
import model.Entity;
import model.Database;
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


		JPanel p = new JPanel(new FlowLayout());
		GridBagConstraints c = new GridBagConstraints();
		JTextField jfId = new JTextField(5);
		JTextField jfType = new JTextField(5);
		JTextField jfAttribute = new JTextField(5);
		JTextField jfNested = new JTextField(5);
		jfId.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				if(jfId.getText().equals("")){
					jfType.setEnabled(true);
					jfType.setText("");
					jfAttribute.setEnabled(true);
					jfAttribute.setText("");
				}else{
					jfType.setEnabled(false);
					jfAttribute.setEnabled(false);
				}
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				if(jfId.getText().equals("")){
					jfType.setEnabled(true);
					jfType.setText("");
					jfAttribute.setEnabled(true);
					jfAttribute.setText("");
				}else{
					jfType.setEnabled(false);
					jfAttribute.setEnabled(false);
				}
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
			}
		});

		p.add(lblId);
		p.add(jfId);
		p.add(lblType);
		p.add(jfType);
		p.add(lblAttribute);
		p.add(jfAttribute);
		p.add(new JLabel("Nested:"));
		p.add(jfNested);

		mainPanel.add(p);

		if( JOptionPane.showConfirmDialog(null,mainPanel,"Fill this form to delete",JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION)
		{
			String idToFilter = ((JTextField)(p.getComponent(1))).getText();
			String typeToFilter = ((JTextField)(p.getComponent(3))).getText();
			Map <String,String> attributesMap = new HashMap <>();
			String attributes = ((JTextField) (p.getComponent(5))).getText();
			String[] res = attributes.split("[,]", 0);

			if (res.length > 0 && !res[0].equals("")) {
				int k = 0;
				for (String myStr : res) {
					String[] kv = res[k].split("[.]", 0);
					if (kv[1].contains("*")) attributesMap.put(kv[0], " ");
					else attributesMap.put(kv[0], kv[1]);
					k++;
				}
			}
			List <Entity> toFilter  = new ArrayList <>();
			if(jfId.getText().equals("") && jfAttribute.getText().equals("") && jfType.getText().equals("") && jfAttribute.getText().equals("") && jfNested.getText().equals("")){
				MainFrame.getInstance().setJt(MainFrame.getInstance().getAppCore().loadTable(Database.getInstance().getEntities()));
				return;
			}

			if(!jfId.getText().equals("")){
				Entity byident = MainFrame.getInstance().getAppCore().getCrawler().findById(idToFilter);
				if(byident != null)
					toFilter.add(byident);
			}else{
				int typeFlag,attrFlag,nestedFlag;
				typeFlag = 0;
				attrFlag = 0;
				nestedFlag = 0;

				if(!jfType.getText().equals("")) typeFlag = 1;
				if(!jfAttribute.getText().equals("")) attrFlag = 1;
				if(!jfNested.getText().equals("")) nestedFlag = 1;
				System.out.println(typeFlag +" "+ attrFlag+" "+ nestedFlag);

				if(typeFlag == 1 && attrFlag == 1 && nestedFlag == 1){


					toFilter = MainFrame.getInstance().getAppCore().getCrawler().findByType(typeToFilter);
					toFilter = checkAttribute(attributesMap,toFilter);
					toFilter = checkValue(attributesMap,toFilter);
					toFilter = checkNested(jfNested.getText(), toFilter);
				}
				else if(typeFlag == 0 && attrFlag == 1  && nestedFlag == 1){


					toFilter = Database.getInstance().getEntities();
					toFilter = checkAttribute(attributesMap,toFilter);
					toFilter = checkValue(attributesMap,toFilter);
					toFilter = checkNested(jfNested.getText(), toFilter);
				}
				else if(typeFlag == 1 && attrFlag == 0 && nestedFlag == 1){
					toFilter = MainFrame.getInstance().getAppCore().getCrawler().findByType(typeToFilter);
					toFilter = checkNested(jfNested.getText(), toFilter);

				}
				else if(typeFlag == 0 && attrFlag == 0 && nestedFlag == 1){
					System.out.println("??");
					toFilter = checkNested(jfNested.getText(), Database.getInstance().getEntities());
				}
				else if(typeFlag == 0 && attrFlag == 0 && nestedFlag == 0){
					JOptionPane.showMessageDialog(MainFrame.getInstance(),"Nista nije odabrano za brisanje");
				}
			}
			System.out.println(toFilter);

			if(!toFilter.isEmpty() )
				MainFrame.getInstance().setJt(MainFrame.getInstance().getAppCore().loadTable(toFilter));
		}


	}
	public List<Entity> checkNested(String nested, List<Entity> toFilter){
		String[] res = nested.split("[,]", 0);

		if (res.length > 0 && !res[0].equals("")) {
			int k = 0;
			for (String myStr : res) {
				String[] kv = res[k].split("[.]", 0);
				String parentKey = kv[0];
				String childKey = kv[1];
				String childValue = kv[2];
				List<Entity> ToFilternew = new ArrayList <>();
				for(Entity entity: toFilter){

					if(entity.getNestedEntities().get(parentKey) != null){
						System.out.println(entity.getNestedEntities().get(parentKey)+"----");
						if(entity.getNestedEntities().get(parentKey).getAttributes().get(childKey) != null){
							System.out.println(entity.getNestedEntities().get(parentKey).getAttributes().get(childKey)+" 22222");

							if(entity.getNestedEntities().get(parentKey).getAttributes().get(childKey).equals(childValue) ){

								System.out.println(entity.getNestedEntities().get(parentKey).getAttributes().get(childKey).equals(childValue));
								ToFilternew.add(entity);

							}
						}
					}
				}
				toFilter = ToFilternew;
				k++;
			}
		}
		return toFilter;
	}

	public List<Entity> checkAttribute(Map<String, String> attributesMap, List<Entity> toFilter){
		List<String> keys = new ArrayList <>();
		System.out.println(attributesMap);
		for(Map.Entry<String,String> pair : attributesMap.entrySet()){
			keys.add(pair.getKey());
		}
		toFilter = MainFrame.getInstance().getAppCore().getCrawler().findByAttribute(toFilter, keys);
		return toFilter;
	}

	public List<Entity> checkValue(Map<String, String> attributesMap, List<Entity> toFilter){
		Map<String,String> valMap = new HashMap <String, String>();
		for(Map.Entry<String,String> pair : attributesMap.entrySet()){
			if(!pair.getValue().equals("")){
				valMap.put(pair.getKey(),pair.getValue());
			}
		}
		toFilter = MainFrame.getInstance().getAppCore().getCrawler().findByValue(toFilter, valMap);
		return toFilter;
	}
}
	

