package com.thinking.machines.hr.bl.managers;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.network.common.*;
import com.thinking.machines.network.common.exceptions.*;
import com.thinking.machines.network.client.*;
import java.util.*;
public class DesignationManager implements DesignationManagerInterface
{
private static DesignationManager designationManager=null;
private DesignationManager() throws BLException
{
}
public static DesignationManagerInterface getDesignationManager() throws BLException
{
if(designationManager==null)designationManager=new DesignationManager();
return designationManager;
}
public void addDesignation(DesignationInterface designation)throws BLException
{
BLException blException;
blException=new BLException();
if(designation==null)
{
blException.setGenericException("Designation required");
throw blException;
}
int code=designation.getCode();
String title=designation.getTitle();
if(code!=0)
{
blException.addException("code","Code should be zero");
}
if(title==null)
{
blException.addException("title","Title required");
title="";
}
else
{
title=title.trim();
if(title.length()==0)
{
blException.addException("title","Title required");
}
}
if(blException.hasExceptions())
{
throw blException;
}
Request request=new Request();
request.setManager("DesignationManager");
request.setAction("add");
request.setArguments(designation);
NetworkClient client=new NetworkClient();
try
{
Response response=client.send(request);
if(response.hasException())
{
blException=(BLException)response.getException();
throw blException;
}
DesignationInterface d=(DesignationInterface)response.getResult();
designation.setCode(d.getCode());
}catch(NetworkException networkException)
{
blException=new BLException();
blException.setGenericException(networkException.getMessage());
throw blException;
}



}
public void updateDesignation(DesignationInterface designation)throws BLException
{
BLException blException;
blException=new BLException();
if(designation==null)
{
blException.setGenericException("Designation required");
throw blException;
}
int code=designation.getCode();
String title=designation.getTitle();
if(code<=0)
{
blException.addException("code","Invalid code:"+code);
}
if(title==null)
{
blException.addException("title","Title required");
title="";
}
else
{
title=title.trim();
if(title.length()==0)
{
blException.addException("title","Title required");
}
}
if(blException.hasExceptions())
{
throw blException;
}
Request request=new Request();
request.setManager("DesignationManager");
request.setAction("update");
request.setArguments(designation);
try
{
NetworkClient client=new NetworkClient();
Response response=client.send(request);
if(response.hasException())
{
blException=(BLException)response.getException();
throw blException;
}
}catch(NetworkException networkException)
{
blException.setGenericException(networkException.getMessage());
throw blException;
}
}
public void removeDesignation(int code)throws BLException
{
if(code<=0)
{
blException.addException("code","Invalid code:"+code);
}
if(blException.hasExceptions())
{
throw blException;
}
Request request=new Request();
request.setManager("DesignationManager");
request.setAction("remove");
request.setArguments(code);
try
{
NetworkClient client=new NetworkClient();
Response response=client.send(request);
if(response.hasException())
{
blException=(BLException)response.getException();
throw blException;
}
}catch(NetworkException networkException)
{
blException.setGenericException(networkException.getMessage());
throw blException;
}
}
public DesignationInterface getDesignationByCode(int code)throws BLException
{
if(code<=0)
{
blException.addException("code","Invalid code:"+code);
}
if(blException.hasExceptions())
{
throw blException;
}
Request request=new Request();
request.setManager("DesignationManager");
request.setAction("getDesignationByCode");
request.setArguments(code);
try
{
NetworkClient client=new NetworkClient();
Response response=client.send(request);
if(response.hasException())
{
blException=(BLException)response.getException();
throw blException;
}
DesignationInterface d=(DesignationInterface)response.getResult();
return d;
}catch(NetworkException networkException)
{
blException.setGenericException(networkException.getMessage());
throw blException;
}
}
public DesignationInterface getDesignationByTitle(String title)throws BLException
{
if(title==null)
{
blException.addException("title","Title required");
title="";
}
else
{
title=title.trim();
if(title.length()==0)
{
blException.addException("title","Title required");
}
}
if(blException.hasExceptions())
{
throw blException;
}
Request request=new Request();
request.setManager("DesignationManager");
request.setAction("getDesignationByTitle");
request.setArguments(title);
try
{
NetworkClient client=new NetworkClient();
Response response=client.send(request);
if(response.hasException())
{
blException=(BLException)response.getException();
throw blException;
}
DesignationInterface d=(DesignationInterface)response.getResult();
return d;
}catch(NetworkException networkException)
{
blException.setGenericException(networkException.getMessage());
throw blException;
}
}
public int getDesignationCount()
{
Request request=new Request();
request.setManager("DesignationManager");
request.setAction("getDesignationCount");
//request.setArguments(code);
try
{
NetworkClient client=new NetworkClient();
Response response=client.send(request);
if(response.hasException())
{
blException=(BLException)response.getException();
throw blException;
}
int count=(int)response.getResult();
return count;
}catch(NetworkException networkException)
{
blException.setGenericException(networkException.getMessage());
throw blException;
}
}
public Boolean designationCodeExists(int code)
{
if(code<=0)
{
return false;
}
Request request=new Request();
request.setManager("DesignationManager");
request.setAction("designationCodeExists");
request.setArguments(code);
try
{
NetworkClient client=new NetworkClient();
Response response=client.send(request);
if(response.hasException())
{
return false;
}
return true;
}catch(NetworkException networkException)
{
return false;
}
}
public Boolean designationTitleExists(String title)
{
if(title==null)
{
return false;
}
else
{
title=title.trim();
if(title.length()==0)
{
return false;
}
}
Request request=new Request();
request.setManager("DesignationManager");
request.setAction("designationTitleExists");
request.setArguments(title);
try
{
NetworkClient client=new NetworkClient();
Response response=client.send(request);
if(response.hasException())
{
return false;
}
return true;
}catch(NetworkException networkException)
{
return false;
}
}
public Set<DesignationInterface> getDesignations()
{
return null;
}
}