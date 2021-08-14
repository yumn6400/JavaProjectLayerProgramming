package com.thinking.machines.hr.pl.model;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.*;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.*;
import com.itextpdf.io.image.*;
import com.itextpdf.kernel.font.*;
import com.itextpdf.io.font.constants.*;
import com.itextpdf.layout.borders.*;
import java.util.*;
import java.io.*;
import javax.swing.table.*;
public class DesignationModel extends AbstractTableModel
{
private java.util.List<DesignationInterface> designations;
private DesignationManagerInterface designationManager;
private String[] columnTitle;
public DesignationModel()
{
populateDataStructure();
}
private void populateDataStructure()
{
this.columnTitle=new String[2];
this.columnTitle[0]="S.no";
this.columnTitle[1]="Designation";
try
{
this.designationManager=DesignationManager.getDesignationManager();
}catch(BLException blException)
{
//????? what to do
}
Set<DesignationInterface> blDesignations=this.designationManager.getDesignations();
this.designations=new LinkedList<>();
for(DesignationInterface designation:blDesignations)
{
this.designations.add(designation);
}
Collections.sort(this.designations,new Comparator<DesignationInterface>(){
public int compare(DesignationInterface left,DesignationInterface right)
{
return left.getTitle().toUpperCase().compareTo(right.getTitle().toUpperCase());
}
});
}
public int getRowCount()
{
return designations.size();
}
public int getColumnCount()
{
return columnTitle.length;
}
public String getColumnName(int columnIndex)
{
return this.columnTitle[columnIndex];
}
public Object getValueAt(int rowIndex,int columnIndex)
{
if(columnIndex==0)return rowIndex+1;
return this.designations.get(rowIndex).getTitle();
}
public Class getColumnClass(int columnIndex)
{
if(columnIndex==0)return Integer.class;//Class.forName(java.lang.Integer)
return String.class;
}
public boolean isCellEditable(int rowIndex,int columnIndex)
{
return false;
}
public void add(DesignationInterface designation)throws BLException
{
designationManager.addDesignation(designation);
this.designations.add(designation);
Collections.sort(this.designations,new Comparator<DesignationInterface>(){
public int compare(DesignationInterface left,DesignationInterface right)
{
return left.getTitle().toUpperCase().compareTo(right.getTitle().toUpperCase());
}
});
fireTableDataChanged();
}
public int indexOfDesignation(DesignationInterface designation)throws BLException
{
Iterator<DesignationInterface> iterator=this.designations.iterator();
DesignationInterface d;
int index=0;
while(iterator.hasNext())
{
d=iterator.next();
if(d.equals(designation))
{
return index;
}
index++;
}
BLException blException=new BLException();
blException.setGenericException("Invalid designation: "+designation.getTitle());
throw blException;
}
public int indexOfTitle(String title,boolean partialLeftSearch)throws BLException
{
Iterator<DesignationInterface> iterator=this.designations.iterator();
DesignationInterface d;
int index=0;
while(iterator.hasNext())
{
d=iterator.next();
if(d.getTitle().toUpperCase().startsWith(title.toUpperCase()))
{
return index;
}
else
{
if(d.getTitle().equalsIgnoreCase(title))
{
return index;
}
}
index++;
}
BLException blException=new BLException();
blException.setGenericException("Invalid title :"+title);
throw blException;
}
public void update(DesignationInterface designation)throws BLException
{
designationManager.updateDesignation(designation);
this.designations.remove(indexOfDesignation(designation));
this.designations.add(designation);
Collections.sort(this.designations,new Comparator<DesignationInterface>(){
public int compare(DesignationInterface left,DesignationInterface right)
{
return left.getTitle().toUpperCase().compareTo(right.getTitle().toUpperCase());
}
});
fireTableDataChanged();
}



public void remove(int code)throws BLException
{
designationManager.removeDesignation(code);
Iterator<DesignationInterface> iterator=this.designations.iterator();
int index=0;
while(iterator.hasNext())
{
if(iterator.next().getCode()==code)break;
index++;
}
if(index==this.designations.size())
{
BLException blException=new BLException();
blException.setGenericException("Invalid designation code: "+code);
throw blException;
}
this.designations.remove(index);
fireTableDataChanged();
}
public DesignationInterface getDesignationAt(int index)throws BLException
{
if(index<0||index>=this.designations.size())
{
BLException blException=new BLException();
blException.setGenericException("Invalid index: "+index);
throw blException;
}
return this.designations.get(index);
}
public void exportToPDF(File file)throws BLException
{
try
{
if(file.exists())file.delete();
PdfWriter pdfWriter=new PdfWriter(file);
PdfDocument pdfDocument=new PdfDocument(pdfWriter);
Document doc=new Document(pdfDocument);
Image logo=new Image(ImageDataFactory.create(this.getClass().getResource("/icons/logo_icon.png")));
Paragraph logoPara=new Paragraph();
logoPara.add(logo);
Paragraph companyNamePara=new Paragraph();
companyNamePara.add("ABCD Corporation");
PdfFont companyNameFont=PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
companyNamePara.setFont(companyNameFont);
companyNamePara.setFontSize(18);
Paragraph reportTitlePara=new Paragraph("List of Designations");
PdfFont reportTitleFont=PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
reportTitlePara.setFont(reportTitleFont);
reportTitlePara.setFontSize(15);
PdfFont pageNumberFont=PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
PdfFont columnTitleFont=PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
PdfFont dataFont=PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
Paragraph columnTitle1=new Paragraph("S.no");
columnTitle1.setFont(columnTitleFont);
columnTitle1.setFontSize(14);
Paragraph columnTitle2=new Paragraph("Designations");
columnTitle2.setFont(columnTitleFont);
columnTitle2.setFontSize(14);
Paragraph pageNumberParagraph;
Paragraph dataParagraph;
float topTableColumnWidths[]={1,5};
float dataTableColumnWidths[]={1,5};
int sno,x,pageSize;
pageSize=5;
boolean newPage=true;
Table topTable;
Table pageNumberTable;
Table dataTable=null;
Cell cell;
int numberOfPages=designations.size()/pageSize;
if((designations.size()%pageSize)!=0)numberOfPages++;
int pageNumber=0;
DesignationInterface designation;
sno=0;
x=0;
while(x<designations.size())
{
if(newPage==true)
{
//create new page header
pageNumber++;
topTable=new Table(UnitValue.createPercentArray(topTableColumnWidths));
cell=new Cell();
cell.setBorder(Border.NO_BORDER);
cell.add(logoPara);
topTable.addCell(cell);
cell=new Cell();
cell.setBorder(Border.NO_BORDER);
cell.add(companyNamePara);
cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
topTable.addCell(cell);
doc.add(topTable);
pageNumberParagraph=new Paragraph("Page: "+pageNumber+"/"+numberOfPages);
pageNumberParagraph.setFont(pageNumberFont);
pageNumberParagraph.setFontSize(13);
pageNumberTable=new Table(1);
pageNumberTable.setWidth(UnitValue.createPercentValue(100));
cell=new Cell();
cell.setBorder(Border.NO_BORDER);
cell.add(pageNumberParagraph);
cell.setTextAlignment(TextAlignment.RIGHT);
pageNumberTable.addCell(cell);
doc.add(pageNumberTable);
dataTable=new Table(UnitValue.createPercentArray(dataTableColumnWidths));
dataTable.setWidth(UnitValue.createPercentValue(100));
cell=new Cell(1,2);
cell.add(reportTitlePara);
cell.setTextAlignment(TextAlignment.CENTER);
dataTable.addHeaderCell(cell);
dataTable.addHeaderCell(columnTitle1);
dataTable.addHeaderCell(columnTitle2);
newPage=false;
}
designation=this.designations.get(x);
sno++;
cell=new Cell();
dataParagraph=new Paragraph(String.valueOf(sno));
dataParagraph.setFont(dataFont);
dataParagraph.setFontSize(14);
cell.add(dataParagraph);
cell.setTextAlignment(TextAlignment.RIGHT);
dataTable.addCell(cell);
cell=new Cell();
dataParagraph=new Paragraph(designation.getTitle());
dataParagraph.setFont(dataFont);
dataParagraph.setFontSize(14);
cell.add(dataParagraph);
dataTable.addCell(cell);
x++;
if(sno%pageSize==0||x==designations.size())
{
//create footer
doc.add(dataTable);
doc.add(new Paragraph("Software By: Pradhyumn Patidar"));
if(x<designations.size())
{
//add new page to document
doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
newPage=true;
}
}
}
doc.close();
System.out.println("PDF file created");
}catch(Exception e)
{
BLException blException=new BLException();
blException.setGenericException(e.getMessage());
throw blException;
}
}
}
