import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import java.text.*;
import java.util.*;
import java.math.*;
public class EmployeeGetByDesignationCodeTestCase
{
public static void main(String gg[])
{
int designationCode=Integer.parseInt(gg[0]);
try
{
EmployeeDAOInterface employeeDAO;
employeeDAO=new EmployeeDAO();
Set<EmployeeDTOInterface> employees;
employees=employeeDAO.getByDesignationCode(designationCode);
SimpleDateFormat simpleDateFormat;
simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
for(EmployeeDTOInterface employeeDTO:employees)
{
System.out.println(employeeDTO.getEmployeeId());
System.out.println(employeeDTO.getName());
System.out.println(employeeDTO.getDesignationCode());
System.out.println(simpleDateFormat.format(employeeDTO.getDateOfBirth()));
System.out.println(employeeDTO.getGender());
System.out.println(employeeDTO.getIsIndian());
System.out.println(employeeDTO.getBasicSalary().toPlainString());
System.out.println(employeeDTO.getPANNumber());
System.out.println(employeeDTO.getAadharCardNumber());
System.out.println("*********************");
}
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}