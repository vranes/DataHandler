package actions;

import Exceptions.IdentifierException;
import model.Database;
import model.Entity;
import service.Crawler;
import service.OrderProvider;
import view.frame.MainFrame;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditButtonAction implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        JLabel lblId = new JLabel("ID:");
        JLabel lblType = new JLabel("Type:");
        JLabel lblAttribute = new JLabel("Attribute:");
        JLabel lblNested = new JLabel("Nested:");



        JPanel p = new JPanel(new FlowLayout());
        JTextField jfId = new JTextField(5);
        JTextField jfType = new JTextField(5);
        JTextField jfAttribute = new JTextField(5);
        JTextField jfNested = new JTextField(5);
        JTextField jfKey = new JTextField(5);





        p.add(lblId);
        p.add(jfId);
        p.add(lblType);
        p.add(jfType);
        p.add(lblAttribute);
        p.add(jfAttribute);
        p.add(lblNested);
        p.add(jfNested);
        mainPanel.add(p);

        if (JOptionPane.showConfirmDialog(null, mainPanel, "Fill this form to add", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            Entity ent = new Entity();
            Map <String, Entity> nestedEntityMap = new HashMap <>();

            if (jfId.isEnabled()) {
                ent.setId(((JTextField) (p.getComponent(1))).getText());
            } else ent.setId(MainFrame.getInstance().getAppCore().getOrderProvider().autoID()); //TODO Auto Generate ID

            ent.setType(((JTextField) (p.getComponent(3))).getText());

            String attributes = ((JTextField) (p.getComponent(5))).getText();
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
                if (((JTextField) (p.getComponent(7))).getText().isEmpty()){
                    ent.setNestedEntities(nestedEntityMap);
                    break;
                }
                if (et.getId().contains(((JTextField) (p.getComponent(7))).getText())) {
                    nestedEntityMap.put(et.getId(), et);
                    ent.setNestedEntities(nestedEntityMap);

                    break;
                }
            }
            Entity toEdit = Crawler.getInstance().findById(ent.getId());


            List <Entity> entities = Database.getInstance().getFiles().get(
                    OrderProvider.getInstance().locateInFile(toEdit)
            );
            String path  = Database.getInstance().getPath()+MainFrame.getInstance().getAppCore().getOrderProvider().locateInFile(toEdit);
            System.out.println(path);
            Database.getInstance().getEntities().remove(toEdit);
            entities.remove(toEdit);
            toEdit.setType(ent.getType());
            toEdit.setNestedEntities(ent.getNestedEntities());
            toEdit.setAttributes(ent.getAttributes());
            entities.add(toEdit);
            Database.getInstance().getEntities().add(toEdit);
            try {

                MainFrame.getInstance().getAppCore().getStorage().refresh(path, entities);
            } catch (IdentifierException identifierException) {
                identifierException.printStackTrace();
            }

            MainFrame.getInstance().setJt(MainFrame.getInstance().getAppCore().loadTable(Database.getInstance().getEntities()));

           // tryAdd(ent);

        }
    }
}
