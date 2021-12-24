package com.thinking.machines.hr.bl.interfaces.managers;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import java.util.*;
public interface DesignationManagerInterface
{
public void addDesignation(DesignationInterface designation)throws BLException;
public void updateDesignation(DesignationInterface designation)throws BLException;
public void removeDesignation(int code)throws BLException;
public DesignationInterface getDesignationByCode(int code)throws BLException;
public DesignationInterface getDesignationByTitle(String title)throws BLException;
public int getDesignationCount();
public Boolean designationCodeExists(int code);
public Boolean designationTitleExists(String title);
public Set<DesignationInterface> getDesignations();
}