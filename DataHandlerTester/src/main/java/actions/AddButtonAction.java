package actions;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Exceptions.IdentifierException;
import importexport.IImportExport;
import model.Entity;
import model.Database;
import service.Crawler;
import service.OrderProvider;
import start.AppCore;
import view.frame.MainFrame;

public class AddButtonAction implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {

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
		JTextField jfKey = new JTextField(5);
		JCheckBox jcbNested = new JCheckBox();

		autoID.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (jfId.isEnabled()) {
					jfId.setEnabled(false);
				} else jfId.setEnabled(true);
			}
		});

		jcbNested.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (jfKey.isEnabled()) {
					jfKey.setEnabled(false);
				} else jfKey.setEnabled(true);
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
		p.add(new JLabel("Key: "));
		p.add(jfKey);
		p.add(new JLabel(" parent  or child"));
		p.add(jcbNested);
		mainPanel.add(p);

		if (JOptionPane.showConfirmDialog(null, mainPanel, "Fill this form to add", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
			Entity ent = new Entity();
			Map <String, Entity> nestedEntityMap = new HashMap <>();

			if (jfId.isEnabled()) {
				ent.setId(((JTextField) (p.getComponent(3))).getText());
			} else ent.setId(MainFrame.getInstance().getAppCore().getOrderProvider().autoID()); //TODO Auto Generate ID

			ent.setType(((JTextField) (p.getComponent(5))).getText());

			String attributes = ((JTextField) (p.getComponent(7))).getText();
			String[] res = attributes.split("[,]", 0);

			if (res.length > 0 && !res[0].equals("")) {
				int k = 0;
				for (String myStr : res) {
					String[] kv = res[k].split("[.]", 0);
					if (kv[1].contains("*")) ent.addAttribute(kv[0], "");
					else ent.addAttribute(kv[0], kv[1]);
					k++;
				}
			}

			for (Entity et : Database.getInstance().getEntities()) {
				if (((JTextField) (p.getComponent(9))).getText().isEmpty()){
					ent.setNestedEntities(nestedEntityMap);
					break;
				}
				if(!jfNested.getText().equals("")){
					if(!jcbNested.isSelected()){
						try {
							MainFrame.getInstance().getAppCore().getStorage().addnested(Database.getInstance().getPath()+
									MainFrame.getInstance().getAppCore().getOrderProvider().locateInFile(
											Crawler.getInstance().findById(jfNested.getText())),
									ent, jfNested.getText(), jfKey.getText());
						} catch (IdentifierException identifierException) {
							identifierException.printStackTrace();
						}
						MainFrame.getInstance().setJt(MainFrame.getInstance().getAppCore().loadTable(Database.getInstance().getEntities()));
						return;
					}
				}
				if (et.getId().contains(((JTextField) (p.getComponent(9))).getText())) {
					nestedEntityMap.put(et.getId(), et);
					ent.setNestedEntities(nestedEntityMap);
					break;
				}
			}

			tryAdd(ent);
			MainFrame.getInstance().setJt(MainFrame.getInstance().getAppCore().loadTable(Database.getInstance().getEntities()));

		}

	}

	public void tryAdd(Entity ent) {
		try {
			MainFrame.getInstance().getAppCore().getStorage().add(Database.getInstance().getPath(), ent);
		} catch (IdentifierException e) {
			JOptionPane.showMessageDialog(MainFrame.getInstance(),e.getMessage());
		}

	}
}