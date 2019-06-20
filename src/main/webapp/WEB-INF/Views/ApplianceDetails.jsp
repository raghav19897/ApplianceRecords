<%--
  Created by IntelliJ IDEA.
  User: raghav
  Date: 16/4/19
  Time: 4:12 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${name}</title>
</head>
<body onload="setInterval(function() {
      var dop = new Date(document.getElementById('sd').value);
      var month = dop.getMonth();
      var year = dop.getFullYear();
      month = parseInt(month + parseInt('${st}')+1);
      if(month>12){
          month = parseInt(month-12);
          year = parseInt(year+1);
      }
      if (month<10){
          month = '0' + month;
      }
      document.getElementById('n').value = year+'-'+month+'-'+dop.getDate();
 },1)">
<form action="/redordAdded">
    <table>
        <tr>
            <td>
                <label>Service Date
                    <input id="sd" type="date" name="subsequentService">
                </label>
            </td>
        </tr>
        <tr>
            <td>
                <label>Remarks
                    <input type="text" name="serviceRemarks">
                </label>
            </td>
        </tr>
        <tr>
            <td>
                <label>Next Service due on
                    <input id="n" type="date" name="nextService">
                </label>
            </td>
        </tr>
        <tr>
            <td><input type="submit" value="Add Record"></td>
        </tr>
    </table>
</form>
<table>
    <tr>
        <td>Id</td>
        <td>${id}</td>
    </tr>
    <tr>
        <td>Name</td>
        <td>${name}</td>
    </tr>
    <tr>
        <td>Bill</td>
        <td>${bill}</td>
    </tr>
    <tr>
        <td>DoP</td>
        <td>${dop}</td>
    </tr>
    <tr>
        <td>Service Required</td>
        <td>${sr}</td>
    </tr>
    <tr>
        <td>Service Time</td>
        <td>${st} Months</td>
    </tr>
    <tr>
        <td>AMC</td>
        <td>${amc}</td>
    </tr>
    <tr>
        <td>Duration</td>
        <td>${d} Years</td>
    </tr>
    <tr>
        <td>Expiry</td>
        <td>${ex}</td>
    </tr>
    <tr>
        <td>Next Service</td>
        <td>${ns}</td>
    </tr>
</table>
<table border="1px solid">
    <tr>
        <td>Service Date</td>
        <td>Service Remarks</td>
    </tr>
    ${sdate}
</table>
<br/>
<a href="/pdfs/details_records.pdf"><button>Download Report Of ${name}</button></a>
</body>
</html>
