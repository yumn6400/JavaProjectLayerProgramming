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
public class EmployeeManagerGetEmployeeCountTestCase
{
public static void main(String gg[])
{
try
{
EmployeeManagerInterface employeeManager;
employeeManager=EmployeeManager.getEmployeeManager();
System.out.println("Employee Count:"+employeeManager.getEmployeeCount());
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