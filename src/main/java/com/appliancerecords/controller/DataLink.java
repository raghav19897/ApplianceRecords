package com.appliancerecords.controller;

import java.sql.*;
import java.util.ArrayList;

class DataLink {
    private Connection conn;
    private Statement stmt;

    private void connect() throws SQLException {
        String url = "jdbc:sqlite:src/main/resources/ApplianceRecords.db";
        conn = DriverManager.getConnection(url);
        stmt = conn.createStatement();
    }

    void insert(String Id, String Name, String Bill) throws SQLException {
        connect();
        stmt.execute("insert into ApplianceDetails values(\"" + Id + "\",\"" + Name + "\",\"" + Bill + "\");");
        conn.close();
    }

    void insert(String Id, Date DoP, boolean Sr, int St, Date Fs) throws SQLException {
        connect();
        String s;
        if(!Sr){
            s = "No";
        }
        else {
            s="Yes";
        }
        stmt.execute("insert into ApplianceProperties values(\"" + Id + "\",\"" + DoP + "\",\"" + s + "\","+St+",\""+Fs+"\");");
        conn.close();
    }

    void insert(String Id, Date Service, String Remarks, Date NextService) throws SQLException {
        connect();
        stmt.execute("insert into ServiceRecords values(\"" + Id + "\",\"" + Service + "\",\"" + Remarks + "\",\""+NextService+"\");");
        conn.close();
    }

    void insert(String Id, boolean Amc, int duration, Date Expiry) throws SQLException {
        connect();
        String s;
        if(!Amc){
            s = "No";
        }
        else {
            s="Yes";
        }
        stmt.execute("insert into AmcDetails values(\"" + Id + "\",\"" + s + "\"," + duration + ",\""+Expiry+"\");");
        conn.close();
    }

    //name dop sr amc ex

    private ArrayList<String> services = new ArrayList<>();
    private ArrayList<String> rem = new ArrayList<>();
    private ArrayList<String> name = new ArrayList<>();
    private ArrayList<String> dop = new ArrayList<>();
    private ArrayList<String> sr = new ArrayList<>();
    private ArrayList<String> amc = new ArrayList<>();
    private ArrayList<String> exp = new ArrayList<>();

    String getIdfromName(String Name) throws SQLException {
        String id="";
        connect();
        ResultSet rs = stmt.executeQuery("select Id from ApplianceDetails where Name=\""+Name+"\"");
        while (rs.next()){
            id=rs.getString("Id");
        }
        conn.close();
        return id;
    }

    String getBill(String id) throws SQLException {
        String bill;
        connect();
        ResultSet rs = stmt.executeQuery("select Bill from ApplianceDetails where Id=\""+id+"\"");
        bill = rs.getString("Bill");
        conn.close();
        return bill;
    }

    String getDoP(String id) throws SQLException {
        String dop;
        connect();
        ResultSet rs = stmt.executeQuery("select DoP from ApplianceProperties where Id=\""+id+"\"");
        dop = rs.getString("DoP");
        conn.close();
        return dop;
    }

    String getService(String id) throws SQLException {
        String s;
        connect();
        ResultSet rs = stmt.executeQuery("select ServiceRequired from ApplianceProperties where Id=\""+id+"\"");
        s = rs.getString("ServiceRequired");
        conn.close();
        return s;
    }

    int getSTime(String id) throws SQLException {
        int t;
        connect();
        ResultSet rs = stmt.executeQuery("select ServiceTime from ApplianceProperties where Id=\""+id+"\"");
        t = rs.getInt("ServiceTime");
        conn.close();
        return t;
    }

    String getAmc(String id) throws SQLException {
        String amc;
        connect();
        ResultSet rs = stmt.executeQuery("select AMC from AmcDetails where Id=\""+id+"\"");
        amc = rs.getString("AMC");
        conn.close();
        return amc;
    }

    int getD(String id) throws SQLException {
        int d;
        connect();
        ResultSet rs = stmt.executeQuery("select Duration from AmcDetails where Id=\""+id+"\"");
        d = rs.getInt("Duration");
        conn.close();
        return d;
    }

    String getEx(String id) throws SQLException {
        String ex;
        connect();
        ResultSet rs = stmt.executeQuery("select Expiry from AmcDetails where Id=\""+id+"\"");
        ex = rs.getString("Expiry");
        conn.close();
        return ex;
    }

    String getNs(String id) throws SQLException {
        String ns="";
        connect();
        ResultSet rs = stmt.executeQuery("select NextService from ServiceRecords where Id=\""+id+"\"");
        while (rs.next()) {
            ns = rs.getString("NextService");
        }
        conn.close();
        return ns;
    }

    void getDetails() throws SQLException {
        connect();
        ResultSet rs;
        rs = stmt.executeQuery("select Name from ApplianceDetails");
        ArrayList<String> n = new ArrayList<>();
        while (rs.next()){
            n.add(rs.getString("Name"));
        }
        setName(n);

        rs = stmt.executeQuery("select DoP from ApplianceProperties");
        ArrayList<String> d = new ArrayList<>();
        while (rs.next()){
            d.add(rs.getString("DoP"));
        }
        setDop(d);

        rs = stmt.executeQuery("select ServiceRequired from ApplianceProperties");
        ArrayList<String> s = new ArrayList<>();
        while (rs.next()){
            s.add(rs.getString("ServiceRequired"));
        }
        setSr(s);

        rs = stmt.executeQuery("select AMC from AmcDetails");
        ArrayList<String> a = new ArrayList<>();
        while (rs.next()){
            a.add(rs.getString("AMC"));
        }
        setAmc(a);

        rs = stmt.executeQuery("select Expiry from AmcDetails");
        ArrayList<String> ex = new ArrayList<>();
        while (rs.next()){
            ex.add(rs.getString("Expiry"));
        }
        setExp(ex);
    }


    void getRemarks(String id) throws SQLException {
        connect();
        ResultSet rs = stmt.executeQuery("select Service, ServiceRemarks from ServiceRecords where Id=\""+id+"\"");
        while (rs.next()){
            services.add(rs.getString("Service"));
            rem.add(rs.getString("ServiceRemarks"));
        }
        setServices(services);
        setRem(rem);
    }

    String getReminder() throws SQLException {
        connect();
        ArrayList<String> id = new ArrayList<>();
        ResultSet resultSet = stmt.executeQuery("select * from ToBeReminded where DaysLeft<7 and Reminded=\"N\"");
        while (resultSet.next()){
            id.add(resultSet.getString("Name"));
        }
        StringBuilder all = new StringBuilder();
        for (String s : id) {
            all.append(s).append(" ");
        }
        return all.toString();
    }

    ArrayList<String> getName() {
        return name;
    }

    private void setName(ArrayList<String> name) {
        this.name = name;
    }

    ArrayList<String> getDop() {
        return dop;
    }

    private void setDop(ArrayList<String> dop) {
        this.dop = dop;
    }

    ArrayList<String> getSr() {
        return sr;
    }

    private void setSr(ArrayList<String> sr) {
        this.sr = sr;
    }

    ArrayList<String> getAmc() {
        return amc;
    }

    private void setAmc(ArrayList<String> amc) {
        this.amc = amc;
    }

    ArrayList<String> getExp() {
        return exp;
    }

    private void setExp(ArrayList<String> exp) {
        this.exp = exp;
    }

    ArrayList<String> getServices() {
        return services;
    }

    private void setServices(ArrayList<String> services) {
        this.services = services;
    }

    ArrayList<String> getRem() {
        return rem;
    }

    private void setRem(ArrayList<String> rem) {
        this.rem = rem;
    }
}
