package com.thinking.machines.hr.dl.interfaces.dao;
import java.util.*;
import java.math.*;
import java.text.*;
import java.io.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
public class EmployeeDAO implements EmployeeDAOInterface
{
private final static String FILE_NAME="employee.data";
public void add(EmployeeDTOInterface employeeDTO)throws DAOException
{
if(employeeDTO==null)throw new DAOException("employee is null");
String employeeId;
String name=employeeDTO.getName();
if(name==null)throw new DAOException("Name is null");
name=name.trim();
if(name.length()==0)throw new DAOException("Length of name is zero");
int designationCode=employeeDTO.getDesignationCode();
if(designationCode<=0)throw new DAOException("Invalid designation code:"+designationCode);
DesignationDAOInterface designationDAO;
designationDAO=new DesignationDAO();
boolean isDesignationCodeValid=designationDAO.codeExists(designationCode);
if(isDesignationCodeValid==false)throw new DAOException("Invalid designation code:"+designationCode);
Date dateOfBirth=employeeDTO.getDateOfBirth();
if(dateOfBirth==null)throw new DAOException("Date of birth is null");
char gender=employeeDTO.getGender();
boolean isIndian=employeeDTO.getIsIndian();
BigDecimal basicSalary=employeeDTO.getBasicSalary();
if(basicSalary==null)throw new DAOException("Basic salary is null");
if(basicSalary.signum()==-1)throw new DAOException("Basic salary is negative");
String panNumber=employeeDTO.getPANNumber();
if(panNumber==null)throw new DAOException("PAN number is null");
panNumber=panNumber.trim();
if(panNumber.length()==0)throw new DAOException("length of pan number is zero");
String aadharCardNumber=employeeDTO.getAadharCardNumber();
if(aadharCardNumber==null)throw new DAOException("Aadhar card number is null");
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0)throw new DAOException("Length of aadhar card is zero");
try
{
int lastGeneratedEmployeeId=10000000;
int recordCount=0;
String lastGeneratedEmployeeIdString="";
String recordCountString="";
File file=new File(FILE_NAME);
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
lastGeneratedEmployeeIdString=String.format("%-10s","10000000");
recordCountString=String.format("%-10s","0");
randomAccessFile.writeBytes(lastGeneratedEmployeeIdString+"\n");
randomAccessFile.writeBytes(recordCountString+"\n");
}
else
{
lastGeneratedEmployeeId=Integer.parseInt(randomAccessFile.readLine().trim());
recordCount=Integer.parseInt(randomAccessFile.readLine().trim());
}
boolean panNumberExists=false;
boolean aadharCardNumberExists=false;
String fPANNumber,fAadharCardNumber;
int x;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
for(x=1;x<=7;x++)randomAccessFile.readLine();
fPANNumber=randomAccessFile.readLine();
fAadharCardNumber=randomAccessFile.readLine();
if(panNumberExists==false&&fPANNumber.equalsIgnoreCase(panNumber))
{
panNumberExists=true;
}
if(aadharCardNumberExists==false&&fAadharCardNumber.equalsIgnoreCase(aadharCardNumber))
{
aadharCardNumberExists=true;
}
if(panNumberExists&&aadharCardNumberExists)break;
}
if(panNumberExists&&aadharCardNumberExists)
{
randomAccessFile.close();
throw new DAOException("PAN number ("+panNumber+") and aadhar card number ("+aadharCardNumber+") exists");
}
if(panNumberExists)
{
randomAccessFile.close();
throw new DAOException("PAN number ("+panNumber+") exists");
}
if(aadharCardNumberExists)
{
randomAccessFile.close();
throw new DAOException("Aadhar card number ("+aadharCardNumber+") exists");
}
lastGeneratedEmployeeId++;
recordCount++;
employeeId="A"+String.format("%-10d",lastGeneratedEmployeeId);
randomAccessFile.writeBytes(employeeId+"\n");
randomAccessFile.writeBytes(name+"\n");
randomAccessFile.writeBytes(designationCode+"\n");
SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
randomAccessFile.writeBytes(simpleDateFormat.format(dateOfBirth)+"\n");
randomAccessFile.writeBytes(gender+"\n");
randomAccessFile.writeBytes(isIndian+"\n");
randomAccessFile.writeBytes(basicSalary.toPlainString()+"\n");
randomAccessFile.writeBytes(panNumber+"\n");
randomAccessFile.writeBytes(aadharCardNumber+"\n");
randomAccessFile.seek(0);
lastGeneratedEmployeeIdString=String.format("%-10d",lastGeneratedEmployeeId);
recordCountString=String.format("%-10d",recordCount);
randomAccessFile.writeBytes(lastGeneratedEmployeeIdString+"\n");
randomAccessFile.writeBytes(recordCountString+"\n");
randomAccessFile.close();
employeeDTO.setEmployeeId(employeeId); 
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public void update(EmployeeDTOInterface employeeDTO)throws DAOException
{
throw new DAOException("Not yet implemented");
}
public void delete(int code)throws DAOException
{
throw new DAOException("Not yet implemented");
}
public Set<EmployeeDTOInterface> getAll() throws DAOException
{
throw new DAOException("Not yet implemented");
}
public Set<EmployeeDTOInterface> getByDesignationCode(int designationCode) throws DAOException
{
throw new DAOException("Not yet implemented");
}
public boolean isDesignationAllocated(int designationCode)throws DAOException
{
throw new DAOException("Not yet implemented");
}
public EmployeeDTOInterface getByEmployeeId(String employeeId)throws DAOException
{
throw new DAOException("Not yet implemented");
}
public EmployeeDTOInterface getByPANNumber(String panNumber)throws DAOException
{
throw new DAOException("Not yet implemented");
}
public EmployeeDTOInterface getByAadharCardNumber(String aadharCardNumber)throws DAOException
{
throw new DAOException("Not yet implemented");
}
public boolean employeeIdExists(String employeeId)throws DAOException
{
throw new DAOException("Not yet implemented");
}
public boolean panNumberExists(String panNumber)throws DAOException
{
throw new DAOException("Not yet implemented");
}
public boolean aadharCardNumberExists(String aadharCardNumber)throws DAOException
{
throw new DAOException("Not yet implemented");
}
public int getCount()throws DAOException
{
throw new DAOException("Not yet implemented");
}
public int getCountByDesignation(int designationCode)throws DAOException
{
throw new DAOException("Not yet implemented");
}
}