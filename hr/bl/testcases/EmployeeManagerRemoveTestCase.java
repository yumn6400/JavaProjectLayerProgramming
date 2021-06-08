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
public class EmployeeManagerRemoveTestCase
{
public static void main(String gg[])
{
String employeeId=gg[0];
try
{
EmployeeManagerInterface employeeManager;
employeeManager=EmployeeManager.getEmployeeManager();
employeeManager.removeEmployee(employeeId);
System.out.println("Employee removed");
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