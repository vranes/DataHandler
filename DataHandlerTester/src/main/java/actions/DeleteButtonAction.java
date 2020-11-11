package actions;

import Exceptions.IdentifierException;
import model.Entity;
import model.Database;
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
import java.util.concurrent.atomic.AtomicReference;

public class DeleteButtonAction implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {

        JPanel mainPanel = new JPanel(new GridBagLayout());

        JLabel lblId = new JLabel("ID:");
        JLabel lblType = new JLabel("Type:");
        JLabel lblAttribute = new JLabel("Attribute:");


        JPanel p = new JPanel(new FlowLayout());
        GridBagConstraints c = new GridBagConstraints();
        JTextField jfId = new JTextField(5);
        JTextField jfType = new JTextField(5);
        JTextField jfAttribute = new JTextField(5);
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

        mainPanel.add(p);

        if( JOptionPane.showConfirmDialog(null,mainPanel,"Fill this form to delete",JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION)
        {
            String idToDelete = ((JTextField)(p.getComponent(1))).getText();
            String typeToDelete = ((JTextField)(p.getComponent(3))).getText();
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
            List <Entity> toDelete  = new ArrayList <>();

            if(!jfId.getText().equals("")){
                Entity byident = MainFrame.getInstance().getAppCore().getCrawler().findById(idToDelete);
                if(byident != null)
                    toDelete.add(byident);            }
            else{
                int typeFlag,attrFlag;
                typeFlag = 0;
                attrFlag = 0;

                if(!jfType.getText().equals("")) typeFlag = 1;
                if(!jfAttribute.getText().equals("")) attrFlag = 1;
                if(typeFlag == 1 && attrFlag == 1){
                    toDelete = MainFrame.getInstance().getAppCore().getCrawler().findByType(typeToDelete);
                    toDelete = checkAttribute(attributesMap,toDelete);
                    toDelete = checkValue(attributesMap,toDelete);
                }
                else if(typeFlag == 0 && attrFlag == 1){
                    toDelete = Database.getInstance().getEntities();
                    toDelete = checkAttribute(attributesMap,toDelete);
                    toDelete = checkValue(attributesMap,toDelete);
                    }
                else if(typeFlag == 1 && attrFlag == 0){
                    toDelete = MainFrame.getInstance().getAppCore().getCrawler().findByType(typeToDelete);
                }
                else if(typeFlag == 0 && attrFlag == 0){
                    JOptionPane.showMessageDialog(MainFrame.getInstance(),"Nista nije odabrano za brisanje");
                }
            }
            for(int i = 0;i<toDelete.size();i++){
                try {
                    MainFrame.getInstance().getAppCore().getStorage().delete(Database.getInstance()
                            .getPath(),toDelete.get(i));
                } catch (Exception identifierException) {
                    JOptionPane.showMessageDialog(MainFrame.getInstance(),identifierException.getMessage());
                }
            }

            MainFrame.getInstance().setJt(MainFrame.getInstance().getAppCore().loadTable(Database.getInstance().getEntities()));
        }


    }

    public List<Entity> checkAttribute(Map<String, String> attributesMap, List<Entity> toDelete){
        List<String> keys = new ArrayList <>();
        for(Map.Entry<String,String> pair : attributesMap.entrySet()){
           keys.add(pair.getKey());
        }
        toDelete = MainFrame.getInstance().getAppCore().getCrawler().findByAttribute(toDelete, keys);
        return toDelete;
    }

    public List<Entity> checkValue(Map<String, String> attributesMap, List<Entity> toDelete){
        Map<String,String> valMap = new HashMap <String, String>();
        for(Map.Entry<String,String> pair : attributesMap.entrySet()){
            if(!pair.getValue().equals("")){
                valMap.put(pair.getKey(),pair.getValue());
            }
        }
        toDelete = MainFrame.getInstance().getAppCore().getCrawler().findByValue(toDelete, valMap);
        return toDelete;
    }
}
