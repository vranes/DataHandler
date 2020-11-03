package actions;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import start.AppCore;
import view.frame.MainFrame;

public class AddButtonAction implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) 
	{

		JPanel p = new JPanel(new GridBagLayout());
		JButton confirmBtn  =  new JButton();
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(10, 10, 10, 0);
		c.fill = GridBagConstraints.HORIZONTAL;

		c.gridx = 0;
		c.gridy = 0;

		p.add(chooser,c);



		if( JOptionPane.showConfirmDialog(null,p,"Fill this form to add",JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION)
		{
			// neka load u table funkcija

		}

			
		
		}
		
	}
	

