package actions;

import model.Entity;
import service.Database;
import view.frame.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class DeleteButtonAction implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {

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

        if( JOptionPane.showConfirmDialog(null,mainPanel,"Fill this form to delete",JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION)
        {
            String idToDelete = ((JTextField)(p.getComponent(0))).getText();
            String typeToDelete = ((JTextField)(p.getComponent(1))).getText();

          //  MainFrame.getInstance().getAppCore().getStorage().add("/Files/file",ent); TODO REMOVE
          //  MainFrame.getInstance().setJt(MainFrame.getInstance().getAppCore().loadTable(Database.getInstance().getEntities())); TODO UPDATE TABLE
        }


    }
}
