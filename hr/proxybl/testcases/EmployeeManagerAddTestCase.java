import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.enums.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import java.text.*;
import java.util.*;
import java.math.*;
public class EmployeeManagerAddTestCase
{
public static void main(String gg[])
{
try
{
String name="Harish";
DesignationInterface designation;
designation=new Designation();
designation.setCode(3);
Date dateOfBirth=new Date();
boolean isIndian=true;
BigDecimal basicSalary=new BigDecimal(99999);
String panNumber="pan999";
String aadharCardNumber="addr999";

EmployeeInterface employee;
employee=new Employee();
employee.setName(name);
employee.setDesignation(designation);
employee.setDateOfBirth(dateOfBirth);
employee.setGender(GENDER.MALE);
employee.setIsIndian(isIndian);
employee.setBasicSalary(basicSalary);
employee.setPANNumber(panNumber);
employee.setAadharCardNumber(aadharCardNumber);
EmployeeManagerInterface employeeManager;
employeeManager=EmployeeManager.getEmployeeManager();
employeeManager.addEmployee(employee);
System.out.println("Employee added with employee id as:("+employee.getEmployeeId()+")");
}catch(BLException blException)
{
if(blException.hasGenericException())System.out.println(blException.getGenericException());
if(blException.hasExceptions())
{
List<String> list;
list=blException.getProperties();
list.forEach((property)->{
System.out.println(blException.getException(property));
});
}
}
}
}