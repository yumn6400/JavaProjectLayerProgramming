package com.thinking.machines.hr.dl.dao;
import java.util.*;
import java.math.*;
import java.text.*;
import java.sql.*;
import com.thinking.machines.enums.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
public class EmployeeDAO implements EmployeeDAOInterface
{
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
Connection connection=null;
PreparedStatement preparedStatement;
ResultSet resultSet;
try
{
connection=DAOConnection.getConnection();
preparedStatement=connection.prepareStatement("select code from designation where code=?");
preparedStatement.setInt(1,designationCode);
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid designation code:"+designationCode);
}
resultSet.close();
preparedStatement.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
java.util.Date dateOfBirth=employeeDTO.getDateOfBirth();
if(dateOfBirth==null)
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("Date of birth is null");
}
char gender=employeeDTO.getGender();
if(gender==' ')
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("Gender not set to male/female");
}
boolean isIndian=employeeDTO.getIsIndian();
BigDecimal basicSalary=employeeDTO.getBasicSalary();
if(basicSalary==null)
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("Basic salary is null");
}
if(basicSalary.signum()==-1)
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("Basic salary is negative");
}
String panNumber=employeeDTO.getPANNumber();
if(panNumber==null)
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("PAN number is null");
}
panNumber=panNumber.trim();
if(panNumber.length()==0)
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("length of pan number is zero");
}
String aadharCardNumber=employeeDTO.getAadharCardNumber();
if(aadharCardNumber==null)
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("Aadhar card number is null");
}
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0)
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("Length of aadhar card is zero");
}
try
{
boolean panNumberExists;
preparedStatement=connection.prepareStatement("select gender from employee where pan_number=?");
preparedStatement.setString(1,panNumber);
resultSet=preparedStatement.executeQuery();
panNumberExists=resultSet.next();
resultSet.close();
preparedStatement.close();
boolean aadharCardNumberExists;
preparedStatement=connection.prepareStatement("select gender from employee where aadhar_card_number=?");
preparedStatement.setString(1,aadharCardNumber);
resultSet=preparedStatement.executeQuery();
aadharCardNumberExists=resultSet.next();
resultSet.close();
preparedStatement.close();
if(panNumberExists&&aadharCardNumberExists)
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("PAN number ("+panNumber+") and aadhar card number ("+aadharCardNumber+") exists");
}
if(panNumberExists)
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("PAN number ("+panNumber+") exists");
}
if(aadharCardNumberExists)
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("Aadhar card number ("+aadharCardNumber+") exists");
}
preparedStatement=connection.prepareStatement("insert into employee(name,designation_code,date_of_birth,gender,is_indian,basic_salary,pan_number,aadhar_card_number) values(?,?,?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
preparedStatement.setString(1,name);
preparedStatement.setInt(2,designationCode);
java.sql.Date sqlDateOfBirth=new java.sql.Date(dateOfBirth.getYear(),dateOfBirth.getMonth(),dateOfBirth.getDate());
preparedStatement.setDate(3,sqlDateOfBirth);
preparedStatement.setString(4,String.valueOf(gender));
preparedStatement.setBoolean(5,isIndian);
preparedStatement.setBigDecimal(6,basicSalary);
preparedStatement.setString(7,panNumber);
preparedStatement.setString(8,aadharCardNumber);
preparedStatement.executeUpdate();
resultSet=preparedStatement.getGeneratedKeys();
resultSet.next();
int generatedEmployeeId=resultSet.getInt(1);
resultSet.close();
preparedStatement.close();
connection.close();
employeeDTO.setEmployeeId("A"+(1000000+generatedEmployeeId));
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
public void update(EmployeeDTOInterface employeeDTO)throws DAOException
{
if(employeeDTO==null)throw new DAOException("employee is null");
String employeeId=employeeDTO.getEmployeeId();
if(employeeId==null)throw new DAOException("Employee Id is null");
employeeId=employeeId.trim();
if(employeeId.length()==0)throw new DAOException("Length of employee id is zero");
Connection connection=null;
PreparedStatement preparedStatement;
ResultSet resultSet;
int actualEmployeeId;
try
{
actualEmployeeId=Integer.parseInt(employeeId.substring(1))-1000000;
}catch(Exception exception)
{
throw new DAOException("Invalid employee Id");
}
try
{
connection=DAOConnection.getConnection();
preparedStatement=connection.prepareStatement("select gender from employee where employee_id=?");
preparedStatement.setInt(1,actualEmployeeId);
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid Employee Id:"+ employeeId);
}
resultSet.close();
preparedStatement.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
String name=employeeDTO.getName();
if(name==null)throw new DAOException("Name is null");
name=name.trim();
if(name.length()==0)throw new DAOException("Length of name is zero");
int designationCode=employeeDTO.getDesignationCode();
if(designationCode<=0)throw new DAOException("Invalid designation code:"+designationCode);
try
{
preparedStatement=connection.prepareStatement("select code from designation where code=?");
preparedStatement.setInt(1,designationCode);
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid designation code:"+designationCode);
}
resultSet.close();
preparedStatement.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
java.util.Date dateOfBirth=employeeDTO.getDateOfBirth();
if(dateOfBirth==null)
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("Date of birth is null");
}
char gender=employeeDTO.getGender();
if(gender==' ')
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("Gender not set to male/female");
}
boolean isIndian=employeeDTO.getIsIndian();
BigDecimal basicSalary=employeeDTO.getBasicSalary();
if(basicSalary==null)
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("Basic salary is null");
}
if(basicSalary.signum()==-1)
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("Basic salary is negative");
}
String panNumber=employeeDTO.getPANNumber();
if(panNumber==null)
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("PAN number is null");
}
panNumber=panNumber.trim();
if(panNumber.length()==0)
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("length of pan number is zero");
}
String aadharCardNumber=employeeDTO.getAadharCardNumber();
if(aadharCardNumber==null)
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("Aadhar card number is null");
}
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0)
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("Length of aadhar card is zero");
}
try
{
boolean panNumberExists;
preparedStatement=connection.prepareStatement("select gender from employee where pan_number=? and employee_id<>?");
preparedStatement.setString(1,panNumber);
preparedStatement.setInt(2,actualEmployeeId);
resultSet=preparedStatement.executeQuery();
panNumberExists=resultSet.next();
resultSet.close();
preparedStatement.close();
boolean aadharCardNumberExists;
preparedStatement=connection.prepareStatement("select gender from employee where aadhar_card_number=? and employee_id<>?");
preparedStatement.setString(1,aadharCardNumber);
preparedStatement.setInt(2,actualEmployeeId);
resultSet=preparedStatement.executeQuery();
aadharCardNumberExists=resultSet.next();
resultSet.close();
preparedStatement.close();
if(panNumberExists&&aadharCardNumberExists)
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("PAN number ("+panNumber+") and aadhar card number ("+aadharCardNumber+") exists");
}
if(panNumberExists)
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("PAN number ("+panNumber+") exists");
}
if(aadharCardNumberExists)
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("Aadhar card number ("+aadharCardNumber+") exists");
}
preparedStatement=connection.prepareStatement("update employee set name=?,designation_code=?,date_of_birth=?,gender=?,is_indian=?,basic_salary=?,pan_number=?,aadhar_card_number=? where employee_id=?");
preparedStatement.setString(1,name);
preparedStatement.setInt(2,designationCode);
java.sql.Date sqlDateOfBirth=new java.sql.Date(dateOfBirth.getYear(),dateOfBirth.getMonth(),dateOfBirth.getDate());
preparedStatement.setDate(3,sqlDateOfBirth);
preparedStatement.setString(4,String.valueOf(gender));
preparedStatement.setBoolean(5,isIndian);
preparedStatement.setBigDecimal(6,basicSalary);
preparedStatement.setString(7,panNumber);
preparedStatement.setString(8,aadharCardNumber);
preparedStatement.setInt(9,actualEmployeeId);
preparedStatement.executeUpdate();
resultSet.close();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
public void delete(String employeeId)throws DAOException
{
if(employeeId==null)throw new DAOException("Employee Id is null");
employeeId=employeeId.trim();
if(employeeId.length()==0)throw new DAOException("Length of employee id is zero");
Connection connection=null;
PreparedStatement preparedStatement;
ResultSet resultSet;
int actualEmployeeId;
try
{
actualEmployeeId=Integer.parseInt(employeeId.substring(1))-1000000;
}catch(Exception exception)
{
throw new DAOException("Invalid employee Id");
}
try
{
connection=DAOConnection.getConnection();
preparedStatement=connection.prepareStatement("select gender from employee where employee_id=?");
preparedStatement.setInt(1,actualEmployeeId);
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid Employee Id:"+ employeeId);
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("delete from employee where employee_id=?");
preparedStatement.setInt(1,actualEmployeeId);
preparedStatement.executeUpdate();
resultSet.close();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
public Set<EmployeeDTOInterface> getAll() throws DAOException
{
Set<EmployeeDTOInterface> employees=new TreeSet<>();
try
{
Connection connection=DAOConnection.getConnection();
Statement statement=connection.createStatement();
ResultSet resultSet;
resultSet=statement.executeQuery("select * from employee");
EmployeeDTOInterface employeeDTO;
java.sql.Date sqlDateOfBirth;
java.util.Date utilDateOfBirth;
String gender;
while(resultSet.next())
{
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId("A"+(resultSet.getInt("employee_id")+1000000));
employeeDTO.setName(resultSet.getString("name").trim());
employeeDTO.setDesignationCode(resultSet.getInt("designation_code"));
sqlDateOfBirth=resultSet.getDate("date_of_birth");
utilDateOfBirth=new java.util.Date(sqlDateOfBirth.getYear(),sqlDateOfBirth.getMonth(),sqlDateOfBirth.getDate());
employeeDTO.setDateOfBirth(utilDateOfBirth);
gender=resultSet.getString("gender");
if(gender.equals("M"))employeeDTO.setGender(GENDER.MALE);
if(gender.equals("F"))employeeDTO.setGender(GENDER.FEMALE);
employeeDTO.setIsIndian(resultSet.getBoolean("is_indian"));
employeeDTO.setBasicSalary(resultSet.getBigDecimal("basic_salary"));
employeeDTO.setPANNumber(resultSet.getString("pan_number").trim());
employeeDTO.setAadharCardNumber(resultSet.getString("aadhar_card_number").trim());
employees.add(employeeDTO);
}
resultSet.close();
statement.close();
connection.close();
return employees;
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
public Set<EmployeeDTOInterface> getByDesignationCode(int designationCode) throws DAOException
{
Set<EmployeeDTOInterface> employees=new TreeSet<>();
Connection connection;
PreparedStatement preparedStatement;
ResultSet resultSet;
try
{
connection=DAOConnection.getConnection();
preparedStatement=connection.prepareStatement("select code from designation where code=?");
preparedStatement.setInt(1,designationCode);
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid designation code:"+designationCode);
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("select * from employee where designation_code=?");
preparedStatement.setInt(1,designationCode);
resultSet=preparedStatement.executeQuery();
EmployeeDTOInterface employeeDTO;
java.sql.Date sqlDateOfBirth;
java.util.Date utilDateOfBirth;
String gender;
while(resultSet.next())
{
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId("A"+(resultSet.getInt("employee_id")+1000000));
employeeDTO.setName(resultSet.getString("name").trim());
employeeDTO.setDesignationCode(resultSet.getInt("designation_code"));
sqlDateOfBirth=resultSet.getDate("date_of_birth");
utilDateOfBirth=new java.util.Date(sqlDateOfBirth.getYear(),sqlDateOfBirth.getMonth(),sqlDateOfBirth.getDate());
employeeDTO.setDateOfBirth(utilDateOfBirth);
gender=resultSet.getString("gender");
if(gender.equals("M"))employeeDTO.setGender(GENDER.MALE);
if(gender.equals("F"))employeeDTO.setGender(GENDER.FEMALE);
employeeDTO.setIsIndian(resultSet.getBoolean("is_indian"));
employeeDTO.setBasicSalary(resultSet.getBigDecimal("basic_salary"));
employeeDTO.setPANNumber(resultSet.getString("pan_number").trim());
employeeDTO.setAadharCardNumber(resultSet.getString("aadhar_card_number").trim());
employees.add(employeeDTO);
}
resultSet.close();
preparedStatement.close();
connection.close();
return employees;
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
public boolean isDesignationAlloted(int designationCode)throws DAOException
{
Connection connection;
PreparedStatement preparedStatement;
ResultSet resultSet;
try
{
connection=DAOConnection.getConnection();
preparedStatement=connection.prepareStatement("select code from designation where code=?");
preparedStatement.setInt(1,designationCode);
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid designation code:"+designationCode);
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("select gender from employee where designation_code=?");
preparedStatement.setInt(1,designationCode);
resultSet=preparedStatement.executeQuery();
boolean designationAlloted=resultSet.next();
resultSet.close();
preparedStatement.close();
connection.close();
return designationAlloted;
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
public EmployeeDTOInterface getByEmployeeId(String employeeId)throws DAOException
{
if(employeeId==null)throw new DAOException("Invalid employeeId: "+employeeId);
employeeId=employeeId.trim();
if(employeeId.length()==0)throw new DAOException("Length of employee Id is zero");
EmployeeDTOInterface employeeDTO;
Connection connection;
PreparedStatement preparedStatement;
ResultSet resultSet;
try
{
connection=DAOConnection.getConnection();
preparedStatement=connection.prepareStatement("select * from employee where employee_id=?");
preparedStatement.setInt(1,Integer.parseInt(employeeId.substring(1))-1000000);
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid Employee Id: "+employeeId);
}
java.sql.Date sqlDateOfBirth;
java.util.Date utilDateOfBirth;
String gender;
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId("A"+(resultSet.getInt("employee_id")+1000000));
employeeDTO.setName(resultSet.getString("name").trim());
employeeDTO.setDesignationCode(resultSet.getInt("designation_code"));
sqlDateOfBirth=resultSet.getDate("date_of_birth");
utilDateOfBirth=new java.util.Date(sqlDateOfBirth.getYear(),sqlDateOfBirth.getMonth(),sqlDateOfBirth.getDate());
employeeDTO.setDateOfBirth(utilDateOfBirth);
gender=resultSet.getString("gender");
if(gender.equals("M"))employeeDTO.setGender(GENDER.MALE);
if(gender.equals("F"))employeeDTO.setGender(GENDER.FEMALE);
employeeDTO.setIsIndian(resultSet.getBoolean("is_indian"));
employeeDTO.setBasicSalary(resultSet.getBigDecimal("basic_salary"));
employeeDTO.setPANNumber(resultSet.getString("pan_number").trim());
employeeDTO.setAadharCardNumber(resultSet.getString("aadhar_card_number").trim());
resultSet.close();
preparedStatement.close();
connection.close();
return employeeDTO;
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
public EmployeeDTOInterface getByPANNumber(String panNumber)throws DAOException
{
if(panNumber==null)throw new DAOException("Invalid PAN Number:"+panNumber);
panNumber=panNumber.trim();
if(panNumber.length()==0)throw new DAOException("Length of PAN Number is zero");
EmployeeDTOInterface employeeDTO;
Connection connection;
PreparedStatement preparedStatement;
ResultSet resultSet;
try
{
connection=DAOConnection.getConnection();
preparedStatement=connection.prepareStatement("select * from employee where pan_number=?");
preparedStatement.setString(1,panNumber);
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid PAN number: "+panNumber);
}
java.sql.Date sqlDateOfBirth;
java.util.Date utilDateOfBirth;
String gender;
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId("A"+(resultSet.getInt("employee_id")+1000000));
employeeDTO.setName(resultSet.getString("name").trim());
employeeDTO.setDesignationCode(resultSet.getInt("designation_code"));
sqlDateOfBirth=resultSet.getDate("date_of_birth");
utilDateOfBirth=new java.util.Date(sqlDateOfBirth.getYear(),sqlDateOfBirth.getMonth(),sqlDateOfBirth.getDate());
employeeDTO.setDateOfBirth(utilDateOfBirth);
gender=resultSet.getString("gender");
if(gender.equals("M"))employeeDTO.setGender(GENDER.MALE);
if(gender.equals("F"))employeeDTO.setGender(GENDER.FEMALE);
employeeDTO.setIsIndian(resultSet.getBoolean("is_indian"));
employeeDTO.setBasicSalary(resultSet.getBigDecimal("basic_salary"));
employeeDTO.setPANNumber(resultSet.getString("pan_number").trim());
employeeDTO.setAadharCardNumber(resultSet.getString("aadhar_card_number").trim());
resultSet.close();
preparedStatement.close();
connection.close();
return employeeDTO;
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
public EmployeeDTOInterface getByAadharCardNumber(String aadharCardNumber)throws DAOException
{
if(aadharCardNumber==null)throw new DAOException("Invalid Aadhar card Number:"+aadharCardNumber);
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0)throw new DAOException("Length of Aadhar card Number is zero");
EmployeeDTOInterface employeeDTO;
Connection connection;
PreparedStatement preparedStatement;
ResultSet resultSet;
try
{
connection=DAOConnection.getConnection();
preparedStatement=connection.prepareStatement("select * from employee where aadhar_card_number=?");
preparedStatement.setString(1,aadharCardNumber);
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid Aadhar card number: "+aadharCardNumber);
}
java.sql.Date sqlDateOfBirth;
java.util.Date utilDateOfBirth;
String gender;
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId("A"+(resultSet.getInt("employee_id")+1000000));
employeeDTO.setName(resultSet.getString("name").trim());
employeeDTO.setDesignationCode(resultSet.getInt("designation_code"));
sqlDateOfBirth=resultSet.getDate("date_of_birth");
utilDateOfBirth=new java.util.Date(sqlDateOfBirth.getYear(),sqlDateOfBirth.getMonth(),sqlDateOfBirth.getDate());
employeeDTO.setDateOfBirth(utilDateOfBirth);
gender=resultSet.getString("gender");
if(gender.equals("M"))employeeDTO.setGender(GENDER.MALE);
if(gender.equals("F"))employeeDTO.setGender(GENDER.FEMALE);
employeeDTO.setIsIndian(resultSet.getBoolean("is_indian"));
employeeDTO.setBasicSalary(resultSet.getBigDecimal("basic_salary"));
employeeDTO.setPANNumber(resultSet.getString("pan_number").trim());
employeeDTO.setAadharCardNumber(resultSet.getString("aadhar_card_number").trim());
resultSet.close();
preparedStatement.close();
connection.close();
return employeeDTO;
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
public boolean employeeIdExists(String employeeId)throws DAOException
{
boolean employeeIdExists=false;
if(employeeId==null)return employeeIdExists;
employeeId=employeeId.trim();
if(employeeId.length()==0)return employeeIdExists;
Connection connection;
PreparedStatement preparedStatement;
ResultSet resultSet;
try
{
connection=DAOConnection.getConnection();
preparedStatement=connection.prepareStatement("select gender from employee where employee_id=?");
preparedStatement.setInt(1,Integer.parseInt(employeeId.substring(1))-1000000);
resultSet=preparedStatement.executeQuery();
employeeIdExists=resultSet.next();
resultSet.close();
preparedStatement.close();
connection.close();
return employeeIdExists;
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
public boolean panNumberExists(String panNumber)throws DAOException
{
boolean panNumberExists=false;
if(panNumber==null)return panNumberExists;
panNumber=panNumber.trim();
if(panNumber.length()==0)return panNumberExists;
Connection connection;
PreparedStatement preparedStatement;
ResultSet resultSet;
try
{
connection=DAOConnection.getConnection();
preparedStatement=connection.prepareStatement("select gender from employee where pan_number=?");
preparedStatement.setString(1,panNumber);
resultSet=preparedStatement.executeQuery();
panNumberExists=resultSet.next();
resultSet.close();
preparedStatement.close();
connection.close();
return panNumberExists;
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
public boolean aadharCardNumberExists(String aadharCardNumber)throws DAOException
{
boolean aadharCardNumberExists=false;
if(aadharCardNumber==null)return aadharCardNumberExists;
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0)return aadharCardNumberExists;
Connection connection;
PreparedStatement preparedStatement;
ResultSet resultSet;
try
{
connection=DAOConnection.getConnection();
preparedStatement=connection.prepareStatement("select gender from employee where aadhar_card_number=?");
preparedStatement.setString(1,aadharCardNumber);
resultSet=preparedStatement.executeQuery();
aadharCardNumberExists=resultSet.next();
resultSet.close();
preparedStatement.close();
connection.close();
return aadharCardNumberExists;
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
public int getCount()throws DAOException
{
try
{
Connection connection=DAOConnection.getConnection();
Statement statement=connection.createStatement();
ResultSet resultSet;
resultSet=statement.executeQuery("select count(*) as cnt from employee");
resultSet.next();
int count=resultSet.getInt(1);
resultSet.close();
statement.close();
connection.close();
return count;
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
public int getCountByDesignation(int designationCode)throws DAOException
{
if(designationCode<=0)return 0;
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select code from designation where code=?");
preparedStatement.setInt(1,designationCode);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid Designation code: "+designationCode);
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("select count(*) as cnt from employee where designation_code=?");
preparedStatement.setInt(1,designationCode);
resultSet=preparedStatement.executeQuery();
resultSet.next();
int count=resultSet.getInt("cnt");
resultSet.close();
preparedStatement.close();
connection.close();
return count;
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
};