import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import java.util.*;
public class DesignationGetAllTestCase
{
public static void main(String gg[])
{
Set<DesignationDTOInterface> designations;
try
{
DesignationDAOInterface designationDAO;
designationDAO=new DesignationDAO();
designations=designationDAO.getAll();
designations.forEach((designationDTO)->{
System.out.println("Code:"+designationDTO.getCode()+" , Title:"+designationDTO.getTitle());
});
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}



}
}