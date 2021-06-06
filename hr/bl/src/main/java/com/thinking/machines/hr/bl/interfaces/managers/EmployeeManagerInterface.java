package com.thinking.machines.hr.bl.interfaces.managers;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;
import java.util.*;
public interface EmployeeManagerInterface
{
public void addEmployee(EmployeeInterface employee)throws BLException;
public void updateEmployee(EmployeeInterface employee)throws BLException;
public void removeEmployee(String employeeId)throws BLException;
public EmployeeInterface getEmployeeByEmployeeId(String employeeId)throws BLException;
public EmployeeInterface getEmployeeByPANNumber(String panNumber)throws BLException;
public EmployeeInterface getEmployeeByAadharCardNumber(String aadharCardNumber)throws BLException;
public int getEmployeeCount()throws BLException;
public boolean employeeIdExists(String employeeId)throws BLException;
public boolean employeePANNumberExists(String panNumber)throws BLException;
public boolean employeeAadharCardNumberExists(String aadharCardNumber)throws BLException;
public Set<EmployeeInterface> getEmployees()throws BLException;
public Set<EmployeeInterface>getEmployeesByEmployeeCode(int employeeCode)throws BLException;
public boolean EmployeeAlloted(int employeeCode)throws BLException;
}