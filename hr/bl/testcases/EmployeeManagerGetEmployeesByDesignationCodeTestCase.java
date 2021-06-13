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
public class EmployeeManagerGetEmployeesByDesignationCodeTestCase
{
public static void main(String gg[])
{
int designationCode=Integer.parseInt(gg[0]);
try
{
Set<EmployeeInterface> employees;
EmployeeManagerInterface employeeManager;
employeeManager=EmployeeManager.getEmployeeManager();
employees=employeeManager.getEmployeesByDesignationCode(designationCode);
employees.forEach((employee)->{
System.out.println(employee.getEmployeeId());
System.out.println(employee.getName());
DesignationInterface designation;
designation=employee.getDesignation();
System.out.println(designation.getCode());
SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
System.out.println(simpleDateFormat.format(employee.getDateOfBirth()));
System.out.println(employee.getGender());
System.out.println(employee.getIsIndian());
System.out.println(employee.getBasicSalary());
System.out.println(employee.getPANNumber());
System.out.println(employee.getAadharCardNumber());
System.out.println("***********************");
});
}catch(BLException blException)
{
if(blException.hasGenericException())
{
System.out.println(blException.getGenericException());
}
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