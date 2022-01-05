package com.thinking.machines.hr.server;
import com.thinking.machines.network.common.*;
import com.thinking.machines.network.server.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.pojo.*;
public class RequestHandler implements RequestHandlerInterface
{
private DesignationManagerInterface designationManager;
public RequestHandler()
{
try
{
designationManager=DesignationManager.getDesignationManager();
}catch(BLException blException)
{
// do nothing
}
}
public Response process(Request request)
{
Response response=new Response();
String manager=request.getManager();
String action=request.getAction();
Object []arguments=request.getArguments();
if(manager.equals("designationManager"))
{
if(designationManager==null)
{
//will implements later on
}
if(action.equals("getDesignations"))
{
Object result=designationManager.getDesignations();
response.setSuccess(true);
response.setResult(result);
response.setException(null);
}
if(action.equals("addDesignation"))
{
DesignationInterface d=(DesignationInterface)arguments[0];
try
{
designationManager.addDesignation(d);
}catch(BLException blException)
{
response.setSuccess(false);
response.setException(blException);
}
response.setResult(d);
response.setSuccess(true);
response.setException(null);
}
if(action.equals("updateDesignation"))
{
DesignationInterface d=(DesignationInterface)arguments[0];
try
{
designationManager.updateDesignation(d);
}catch(BLException blException)
{
response.setSuccess(false);
response.setException(blException);
}
response.setSuccess(true);
response.setException(null);
}
if(action.equals("removeDesignation"))
{
try
{
designationManager.removeDesignation((int)arguments[0]);
}catch(BLException blException)
{
response.setSuccess(false);
response.setException(blException);
}
response.setSuccess(true);
response.setException(null);
}
if(action.equals("getDesignationByCode"))
{
Object result=null;
try
{
result=designationManager.getDesignationByCode((int)arguments[0]);
}catch(BLException blException)
{
response.setSuccess(false);
response.setException(blException);
}
response.setSuccess(true);
response.setResult(result);
response.setException(null);
}
if(action.equals("getDesignationByTitle"))
{
Object result=null;
try
{
result=designationManager.getDesignationByTitle((String)arguments[0]);
}catch(BLException blException)
{
response.setSuccess(false);
response.setException(blException);
}
response.setSuccess(true);
response.setResult(result);
response.setException(null);
}
if(action.equals("getDesignationCount"))
{
Object result=designationManager.getDesignationCount();
response.setSuccess(true);
response.setResult(result);
response.setException(null);
}
if(action.equals("designationCodeExists"))
{
Object result=designationManager.designationCodeExists((int)arguments[0]);
response.setSuccess(true);
response.setResult(result);
response.setException(null);
}
if(action.equals("designationTitleExists"))
{
Object result=designationManager.designationTitleExists((String)arguments[0]);
response.setSuccess(true);
response.setResult(result);
response.setException(null);
}
}
return response;
}//Designation Manager parts ends here

}