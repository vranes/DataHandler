package actions;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import model.Entity;
import service.Database;
import start.AppCore;
import view.frame.MainFrame;

public class AddButtonAction implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) 
	{

		JPanel mainPanel = new JPanel(new GridBagLayout());
		JLabel lblAutoId = new JLabel("AutoID:");
		JLabel lblId = new JLabel("ID:");
		JLabel lblType = new JLabel("Type:");
		JLabel lblAttribute = new JLabel("Attribute:");
		JLabel lblNested = new JLabel("Nested:");


		JCheckBox autoID = new JCheckBox();

		JPanel p = new JPanel(new FlowLayout());
		JTextField jfId = new JTextField(5);
		JTextField jfType = new JTextField(5);
		JTextField jfAttribute = new JTextField(5);
		JTextField jfNested = new JTextField(5);

		autoID.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(jfId.isEnabled()){
					jfId.setEnabled(false);
				}else jfId.setEnabled(true);
			}
		});

		p.add(lblAutoId);
		p.add(autoID);
		p.add(lblId);
		p.add(jfId);
		p.add(lblType);
		p.add(jfType);
		p.add(lblAttribute);
		p.add(jfAttribute);
		p.add(lblNested);
		p.add(jfNested);

		mainPanel.add(p);

		if( JOptionPane.showConfirmDialog(null,mainPanel,"Fill this form to add",JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION)
		{
			Entity ent = new Entity();
			Map <String,Entity> nestedEntityMap = new HashMap <>();

			if(jfId.isEnabled()){
				ent.setId(((JTextField)(p.getComponent(3))).getText());
			}else ent.setId(((JTextField)(p.getComponent(3))).getText()); //TODO Auto Generate ID

			ent.setType(((JTextField)(p.getComponent(5))).getText());

			String attributes = ((JTextField)(p.getComponent(7))).getText();
			String[] res = attributes.split("[,]", 0);

			if(res.length != 1){
				int k = 0;
				for(String myStr: res) {
					String[] kv = res[k].split("[.]", 0);
					if(kv[1].contains("*")) ent.addAttribute(kv[0]," ");
					else ent.addAttribute(kv[0],kv[1]);
					k++;
				}
			}

			for(Entity et :Database.getInstance().getEntities()){
				if(((JTextField)(p.getComponent(9))).getText().isEmpty())break;
				if(et.getId().contains(((JTextField)(p.getComponent(9))).getText())){
					nestedEntityMap.put(et.getId(), et);
					ent.setNestedEntities(nestedEntityMap);

					break;
				}
			}
			MainFrame.getInstance().getAppCore().getStorage().add("/Files/file",ent);
			MainFrame.getInstance().setJt(MainFrame.getInstance().getAppCore().loadTable(Database.getInstance().getEntities()));
		}

			
		
		}
		
	}
	

