<%--
  Created by IntelliJ IDEA.
  User: raghav
  Date: 16/4/19
  Time: 3:28 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Records</title>
</head>
<body onload="document.getElementById('dop').value = new Date().getFullYear() +'-0'+ parseInt(new Date().getMonth()+1) +'-'+ new Date().getDate();
setInterval(function() {
  if(document.getElementById('sr').value === 'true' && document.getElementById('st').value>0){
      var dop = new Date(document.getElementById('dop').value);
      var month = dop.getMonth();
      var year = dop.getFullYear();
      month = parseInt(month + parseInt(document.getElementById('st').value)+1);
      if(month>12){
          month = parseInt(month-12);
          year = parseInt(year+1);
      }
      if (month<10){
          month = '0' + month;
      }
      document.getElementById('fs').value = year+'-'+month+'-'+dop.getDate();
  }
  if(document.getElementById('amc').value === 'true' && document.getElementById('d').value>0){
      var dop1 = new Date(document.getElementById('dop').value);
      var month1 = dop1.getMonth();
      var year1 = dop1.getFullYear();
      year1 = parseInt(year1 + parseInt(document.getElementById('d').value));
      if (month1<10){
          month1 = '0' + month1;
      }
      document.getElementById('ex').value = year1+'-'+month1+'-'+dop1.getDate();
  }
},1);
setInterval(function() {
    if(document.getElementById('sr').value==='false'){
        document.getElementById('st').disabled=true;
        document.getElementById('fs').disabled=true;
    }
    else {
        document.getElementById('st').disabled=false;
        document.getElementById('fs').disabled=false;
    }

    if(document.getElementById('amc').value==='false'){
        document.getElementById('d').disabled=true;
        document.getElementById('ex').disabled=true;
    }
    else{
        document.getElementById('d').disabled=false;
        document.getElementById('ex').disabled=false;
    }
},1)">
<form action="/added" method="post">
    <table>
        <tr>
            <td>
                <label>Name of Appliance
                    <input type="text" name="applianceName">
                </label>
            </td>
        </tr>

        <tr>
            <td>
                <label>Date of Purchase
                    <input id="dop" type="date" name="dateOfPurchase">
                </label>
            </td>
        </tr>

        <tr>
            <td>
                <abel path="file">Upload Bill
                    <input type="file" name="multipartFile">
                </abel>
            </td>
        </tr>

        <tr>
            <td>
                <label>Service Required
                    <select id="sr" name="serviceRequired">
                        <option value="false">No</option>
                        <option value="true">Yes</option>
                    </select>
                </label>
            </td>
        </tr>

        <tr>
            <td>
                <label> Service time
                    <input id="st" type="number" name="serviceTime"> Months
                </label>
            </td>
        </tr>

        <tr>
            <td>
                <label>First Service
                    <input id="fs" type="date" name="firstService">
                </label>
            </td>
        </tr>

        <tr>
            <td>
                <label>AMC Done
                    <select id="amc" name="AMC">
                        <option value="false">No</option>
                        <option value="true">Yes</option>
                    </select>
                </label>
            </td>
            <td>
                <label>
                    <input id="d" type="number" name="AmcDuration"> Years
                </label>
            </td>
        </tr>

        <tr>
            <td>
                <label>Amc Expiry
                    <input id="ex" type="date" name="expiry">
                </label>
            </td>
        </tr>

        <tr>
            <td><input type="submit" value="Add appliance"></td>
        </tr>
    </table>
</form>
<form action="/details">
    <input id="name" type="text" name="applianceName">
    <input type="submit" value="Search">
</form>
<table border="1px solid">
    <tr>
        <td>Name</td>
        <td>DoP</td>
        <td>Servicing Required</td>
        <td>AMC Done</td>
        <td>AMC expiry</td>
    </tr>
    ${alldetails}
</table>
<br/>
<a href="/pdf/all_details.pdf"><button>Download Full Report</button></a>
</body>
</html>
