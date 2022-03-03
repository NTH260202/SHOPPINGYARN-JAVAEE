<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 3/3/2022
  Time: 3:58 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Create Account</title>
    </head>
    <body>
        <h1>Create New Account</h1>
        <form action="DispatchController" method="POST">
            Username* <input type="text" name="txtUsername" value=""/> (e.g. 6-20 chars)<br/>
            Password* <input type="password" name="txtPassword" value=""/> (e.g. 6-30 chars)<br/>
            Confirm* <input type="password" name="txtConfirm" value=""/> <br/>
            First name <input type="text" name="txtFirstname"/> (e.g. 6-10 chars)<br/>
            Last name <input type="text" name="txtLastname"/> (e.g. 6-40 chars)<br/>
            <input type="submit" value="Create New Account" name="btAction"/>
        </form>
    </body>
</html>
