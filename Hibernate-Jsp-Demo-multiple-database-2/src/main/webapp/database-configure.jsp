<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
 <title>User Management Application</title>
</head>
<body>
    <div align="center">
    <h2>Please configure a database for <c:out value="${domain.toUpperCase()}" /></h2>
    
   <form action="insertDatabase" method="post">
        <table border="1" cellpadding="5">            
            <tr>
                <th>Database Type: </th>
                <td>
                 	<select name="database">
					  <option value="PSQL">Postgres</option>
					  <option value="MYSQL">MySQL</option>
					 </select>
                </td>
            </tr>
            <tr>
                <th>Database URL: </th>
                <td>
                 <input type="text" name="url" size="45"/>
                </td>
            </tr>
            <tr>
                <th>Username: </th>
                <td>
                 <input type="text" name="username" size="45"/>
                </td>
            </tr>
            <tr>
                <th>Password: </th>
                <td>
                 <input type="text" name="password" size="45"/>
                </td>
            </tr>
            <tr>
             <td colspan="2" align="center">
              <input type="submit" value="Save" />
             </td>
            </tr>
        </table>
        </form>
    </div> 
</body>
</html>