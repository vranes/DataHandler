package actions;

import model.Entity;
import model.Database;
import view.frame.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

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
        JComboBox jcmbId= new JComboBox();
        JComboBox jcmbType= new JComboBox();

        jcmbId.addItem("Ascending");
        jcmbId.addItem("Descending");
        jcmbType.addItem("Ascending");
        jcmbType.addItem("Descending");

        p.add(lblId);
        p.add(jcmbId);
        p.add(jcbId);
        p.add(lblType);
        p.add(jcmbType);
        p.add(jcbType);
        ArrayList<Entity> ents = (ArrayList <Entity>) Database.getInstance().getEntities();
        mainPanel.add(p);

        if( JOptionPane.showConfirmDialog(null,mainPanel,"Fill this form to sort",JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION)
        {
            if(jcbId.isSelected() && jcbType.isSelected()) {
                if(jcmbId.getSelectedItem().equals("Ascending") && jcmbType.getSelectedItem().equals("Ascending"))
                    Collections.sort(ents, Comparator.comparing(Entity::abcd).thenComparing(Entity::getType));
                else if(jcmbId.getSelectedItem().equals("Descending") && jcmbType.getSelectedItem().equals("Descending")){
                    Collections.sort(ents, Comparator.comparing(Entity::abcd).thenComparing(Entity::getType));
                    Collections.reverse(ents);
                }
                else if(jcmbId.getSelectedItem().equals("Ascending") && jcmbType.getSelectedItem().equals("Descending")){
                    Collections.sort(ents, Comparator.comparing(Entity::getType));
                    Collections.reverse(ents);
                    Collections.sort(ents, Comparator.comparing(Entity::abcd));

                }
                else if(jcmbId.getSelectedItem().equals("Descending") && jcmbType.getSelectedItem().equals("Ascending")){
                    Collections.sort(ents, Comparator.comparing(Entity::abcd));
                    Collections.reverse(ents);
                    Collections.sort(ents, Comparator.comparing(Entity::getType));
                }
            }
            else if(jcbId.isSelected() && !jcbType.isSelected()){
                if(jcmbId.getSelectedItem().equals("Ascending") )
                    Collections.sort(ents, Comparator.comparing(Entity::abcd));
                else if(jcmbId.getSelectedItem().equals("Descending") ){
                    Collections.sort(ents, Comparator.comparing(Entity::abcd));
                    Collections.reverse(ents);
                }
            }
            else if(!jcbId.isSelected() && jcbType.isSelected()){
                if(jcmbType.getSelectedItem().equals("Ascending") )
                    Collections.sort(ents, Comparator.comparing(Entity::getType));
                else if(jcmbType.getSelectedItem().equals("Descending") ){
                    Collections.sort(ents, Comparator.comparing(Entity::getType));
                    Collections.reverse(ents);
                }
            }
        }

        MainFrame.getInstance().setJt(MainFrame.getInstance().getAppCore().loadTable(ents));

    }

}
