package actions;

import model.Entity;
import service.Database;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class SortButtonAction implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {

        JPanel mainPanel = new JPanel(new GridBagLayout());

        JLabel lblId = new JLabel("ID asc or desc:");
        JLabel lblType = new JLabel("Type asc or desc:");


        JPanel p = new JPanel(new FlowLayout());
        GridBagConstraints c = new GridBagConstraints();
        JTextField jfId = new JTextField(5);
        JTextField jfType = new JTextField(5);
        JCheckBox jcbId = new JCheckBox();
        JCheckBox jcbType = new JCheckBox();


        p.add(lblId);
        p.add(jcbId);
        p.add(jfId);
        p.add(lblType);
        p.add(jcbType);
        p.add(jfType);


        mainPanel.add(p);

        if( JOptionPane.showConfirmDialog(null,mainPanel,"Fill this form to delete",JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION)
        {
            String idToSortBy = ((JTextField)(p.getComponent(0))).getText();
            String typeToSortBy = ((JTextField)(p.getComponent(1))).getText();


        }
        //  MainFrame.getInstance().setJt(MainFrame.getInstance().getAppCore().loadTable(Database.getInstance().getEntities())); TODO UPDATE TABLE
    }

}
