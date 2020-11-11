package actions;

import Exceptions.IdentifierException;
import model.Database;
import model.Entity;
import service.Crawler;
import view.frame.MainFrame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class AddButtonAction implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {

		JPanel mainPanel = new JPanel(new GridBagLayout());
		JPanel p = new JPanel(new FlowLayout());
		JLabel lblAutoId = new JLabel("AutoID:");
		JLabel lblId = new JLabel("ID:");
		JLabel lblType = new JLabel("Type:");
		JLabel lblAttribute = new JLabel("Attribute:");
		JLabel lblNested = new JLabel("Nested ID:");
		JLabel lblKey = new JLabel("Nested Key:");
		JLabel lblPC = new JLabel("Parent");


		JTextField jfId = new JTextField(5);
		JTextField jfType = new JTextField(5);
		JTextField jfAttribute = new JTextField(5);
		JTextField jfNested = new JTextField(5);
		JTextField jfKey = new JTextField(5);
		JCheckBox jcbID = new JCheckBox();
		JCheckBox jcbNested = new JCheckBox();

		jcbID.addActionListener(new ActionListener() {
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
		p.add(jcbID);
		p.add(lblId);
		p.add(jfId);
		p.add(lblType);
		p.add(jfType);
		p.add(lblAttribute);
		p.add(jfAttribute);
		p.add(lblNested);
		p.add(jfNested);
		p.add(lblKey);
		p.add(jfKey);
		p.add(lblPC);
		p.add(jcbNested);
		mainPanel.add(p);

		if (JOptionPane.showConfirmDialog(null, mainPanel, "Fill this form to add", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
			Entity ent = new Entity();
			Map <String, Entity> nestedEntityMap = new HashMap <>();

			if (jfId.isEnabled()) {
				if(!jfId.getText().equals("")) ent.setId(jfId.getText());
				else{
					JOptionPane.showMessageDialog(MainFrame.getInstance(),"You can't create an entity without ID");
					return;
				}
			} else ent.setId(MainFrame.getInstance().getAppCore().getOrderProvider().autoID());

			if(jfType.getText().equals("")){
				JOptionPane.showMessageDialog(MainFrame.getInstance(),"You can't create an entity without type");
				return;
			}
			ent.setType(jfType.getText());


			String attributes = (jfAttribute).getText();
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
				if (jfNested.getText().isEmpty()){
					ent.setNestedEntities(nestedEntityMap);
					break;
				}
				if(!jfNested.getText().equals("")){
					if(!jcbNested.isSelected()){
						try {
							MainFrame.getInstance().getAppCore().getStorage().addNested(Database.getInstance().getPath()+
									MainFrame.getInstance().getAppCore().getOrderProvider().locateInFile(
											Crawler.getInstance().findById(jfNested.getText())),
									ent, jfNested.getText(), jfKey.getText());
						} catch (Exception identifierException) {
							JOptionPane.showMessageDialog(MainFrame.getInstance(),identifierException.getMessage());
						}
						MainFrame.getInstance().setJt(MainFrame.getInstance().getAppCore().loadTable(Database.getInstance().getEntities()));
						return;
					}
				}
				if (et.getId().contains(jfNested.getText())) {
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
		} catch (Exception e) {
			JOptionPane.showMessageDialog(MainFrame.getInstance(),e.getMessage());
		}

	}
}