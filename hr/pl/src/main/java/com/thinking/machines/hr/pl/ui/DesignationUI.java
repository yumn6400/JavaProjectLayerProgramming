package com.thinking.machines.hr.pl.ui;
import com.thinking.machines.hr.pl.model.*;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.pojo.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.event.*;
import javax.swing.table.*;
public class DesignationUI extends JFrame
{
private JLabel titleLabel;
private JLabel searchLabel;
private JTextField searchTextField;
private JButton clearSearchTextFieldButton;
private JTable designationTable;
private JLabel searchErrorLabel;
private JScrollPane scrollPane;
private DesignationModel designationModel;
private Container container;
private DesignationPanel designationPanel;
public DesignationUI()
{
initComponents();
setApperence();
addListeners();
}
private void initComponents()
{
designationModel=new DesignationModel();
titleLabel=new JLabel("Designations");
searchLabel=new JLabel("Search");
searchTextField=new JTextField();
clearSearchTextFieldButton=new JButton("X");
searchErrorLabel=new JLabel("Not Found");
searchErrorLabel.setForeground(Color.red);
designationTable=new JTable(designationModel);
scrollPane=new JScrollPane(designationTable,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
container=getContentPane();
}
private void setApperence()
{
Font titleFont=new Font("Verdana",Font.BOLD,18);
Font captionFont=new Font("Verdana",Font.BOLD,16);
Font dataFont=new Font("Verdana",Font.PLAIN,16);
Font columnHeaderFont=new Font("Verdana",Font.BOLD,16);
Font searchErrorFont=new Font("Verdana",Font.BOLD,12);
titleLabel.setFont(titleFont);
searchLabel.setFont(captionFont);
searchTextField.setFont(dataFont);
searchErrorLabel.setFont(searchErrorFont);
designationTable.setFont(dataFont);
designationTable.setRowHeight(35);
designationTable.getColumnModel().getColumn(0).setPreferredWidth(20);
designationTable.getColumnModel().getColumn(1).setPreferredWidth(400);
JTableHeader header=designationTable.getTableHeader();
header.setFont(columnHeaderFont);
designationTable.setRowSelectionAllowed(true);
designationTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
header.setReorderingAllowed(false);
header.setResizingAllowed(false);
designationPanel=new DesignationPanel();
container.setLayout(null);
int lm,tm; //leftMargin,topMargin
lm=0;
tm=0;
titleLabel.setBounds(lm+10,tm+10,200,40);
searchErrorLabel.setBounds(lm+10+100+400+10-75,tm+10+10+20,100,20);
searchLabel.setBounds(lm+10,tm+10+40,100,30);
searchTextField.setBounds(lm+10+100+5,tm+10+40+10,400,30);
clearSearchTextFieldButton.setBounds(lm+10+100+400+10,tm+10+40+10,30,30);
scrollPane.setBounds(lm+10,tm+10+40+10+30+10,565,300);
designationPanel.setBounds(lm+10,tm+10+40+10+30+10+300+10,565,180);
container.add(titleLabel);
container.add(searchErrorLabel);
container.add(searchLabel);
container.add(searchTextField);
container.add(clearSearchTextFieldButton); 
container.add(scrollPane);
container.add(designationPanel);
int w,h;
w=600;
h=650;
setSize(w,h);
Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
setLocation((d.width/2)-(w/2),(d.height/2)-(h/2));
}
private void addListeners()
{
}
//Inner class starts
class DesignationPanel extends JPanel
{
DesignationPanel()
{
setBorder(BorderFactory.createLineBorder(new Color(175,175,175)));
}
} 
}