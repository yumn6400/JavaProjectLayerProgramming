package com.thinking.machines.hr.server;
import com.thinking.machines.network.common.*;
import com.thinking.machines.network.server.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.managers.*;
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
}
return response;
}//Designation Manager parts ends here

}