import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import java.text.*;
import java.util.*;
import java.math.*;
public class EmployeeEmployeeIdExistsTestCase
{
public static void main(String gg[])
{
String EmployeeId=gg[0];
try
{
EmployeeDAOInterface employeeDAO;
employeeDAO=new EmployeeDAO();
System.out.println("Employee with Id: "+EmployeeId+" exists: "+employeeDAO.employeeIdExists(EmployeeId));
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}