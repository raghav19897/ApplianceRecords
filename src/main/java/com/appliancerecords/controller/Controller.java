package com.appliancerecords.controller;


import com.appliancerecords.Home;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.stream.Stream;

@org.springframework.stereotype.Controller
public class Controller {

    private Home home = new Home();
    private ModelAndView detailsModel = new ModelAndView("ApplianceDetails","home",home);
    private ModelAndView homeModel = new ModelAndView("Home","home",home);
    private DataLink dataLink = new DataLink();
    private String id;

    private ArrayList<String> AllNames;
    private ArrayList<String> Alldops;
    private ArrayList<String> Allsrs;
    private ArrayList<String> Allamc;
    private ArrayList<String> Allex;

    private String n;
    private String a;
    private String d;
    private String e;
    private String dp;
    private String ser;
    private String sertime;
    private String nex;

    @RequestMapping("/")
    public ModelAndView index() throws SQLException {
        addToPage();
        return homeModel;
    }

    @RequestMapping(value = "/added", method = RequestMethod.POST)
    public ModelAndView added(@RequestParam("file") MultipartFile file, @ModelAttribute Home home) throws SQLException, IOException {
        String name = home.getApplianceName();
        Date doP = home.getDateOfPurchase();
        Date expiry = (Date) home.getExpiry();
        boolean sr = home.isServiceRequired();
        int st = home.getServiceTime();
        Date fs = (Date) home.getFirstService();
        boolean amc = home.isAMC();
        int duration = home.getAmcDuration();
        int time = Integer.parseInt(LocalDateTime.now().getHour()+""+LocalDateTime.now().getMinute());
        String bill = "src/main/resources/" + (name.toLowerCase()).replaceAll(" ", "") + time + ".pdf";
        MultipartFile bill1 = file;
        File file1 = new File(bill);
        FileWriter fw =new FileWriter(file1);
        fw.write(bill1.getInputStream().read());

        id = (name.toLowerCase()).replaceAll(" ", "") + time;
        dataLink.insert(id, name, bill);
        dataLink.insert(id, doP, sr, st, fs);
        dataLink.insert(id, amc, duration, expiry);
        addToPage();
        return homeModel;
    }

    private void addToPage() throws SQLException {
        getDetails();
        StringBuilder Alldetails = new StringBuilder();
        for (int i=0; i<AllNames.size(); i++){
            Alldetails.append("<tr><td  onclick=\"document.getElementById('name').value ='").append(AllNames.get(i)).append("'\">").append(AllNames.get(i)).append("</td><td>").append(Alldops.get(i)).append("</td><td>").append(Allsrs.get(i)).append("</td><td>").append(Allamc.get(i)).append("</td><td>").append(Allex.get(i)).append("</td></tr>");
        }
        homeModel.addObject("alldetails", Alldetails.toString());
    }

    @RequestMapping("/details")
    public ModelAndView details(@ModelAttribute Home home) throws SQLException {
        id = dataLink.getIdfromName(home.getApplianceName());
        dataLink.getRemarks(id);
        ArrayList<String> s = dataLink.getServices();
        ArrayList<String> r = dataLink.getRem();
        StringBuilder s1= new StringBuilder();
        for(int i=0; i<s.size(); i++){
            s1.append("<tr><td>").append(s.get(i)).append("</td><td>").append(r.get(i)).append("</td></tr>");
        }
        detailsModel.addObject("id",id);
        n=home.getApplianceName();
        a=dataLink.getAmc(id);
        d= String.valueOf(dataLink.getD(id));
        e=dataLink.getEx(id);
        dp=dataLink.getDoP(id);
        ser=dataLink.getService(id);
        sertime= String.valueOf(dataLink.getSTime(id));
        nex=dataLink.getNs(id);
        detailsModel.addObject("name",home.getApplianceName());
        detailsModel.addObject("amc",dataLink.getAmc(id));
        detailsModel.addObject("d",dataLink.getD(id));
        detailsModel.addObject("ex",dataLink.getEx(id));
        detailsModel.addObject("bill",dataLink.getBill(id));
        detailsModel.addObject("dop",dataLink.getDoP(id));
        detailsModel.addObject("sr",dataLink.getService(id));
        detailsModel.addObject("st",dataLink.getSTime(id));
        detailsModel.addObject("ns",dataLink.getNs(id));
        detailsModel.addObject("sdate", s1.toString());
        return detailsModel;
    }

    @RequestMapping("/redordAdded")
    public ModelAndView record(@ModelAttribute Home home) throws SQLException {
        Date ss = (Date) home.getSubsequentService();
        String remarks = home.getServiceRemarks();
        Date ns = home.getNextService();
        dataLink.insert(id, ss, remarks, ns);
        return detailsModel;
    }

    /*private void sendEmail(){
        try {
            String phoneNumber = "+919958574605";
            String appKey = "0e47c31b-f773-4207-9e64-76c3f9ca0138";
            String appSecret = "Pjn6tiAKoESbqd6oeARIZA==";
            String message = dataLink.getReminder();

            URL url = new URL("https://messagingapi.sinch.com/v1/sms/" + phoneNumber);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");

            String userCredentials = "application\\" + appKey + ":" + appSecret;
            byte[] encoded = Base64.encodeBase64(userCredentials.getBytes());
            String basicAuth = "Basic " + new String(encoded);
            connection.setRequestProperty("Authorization", basicAuth);

            String postData = "{\"Message\":\"" + message + "\"}";
            System.out.println(message);
            OutputStream os = connection.getOutputStream();
            os.write(postData.getBytes());

            StringBuilder response = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line;
            while ( (line = br.readLine()) != null)
                response.append(line);

            br.close();
            os.close();

            System.out.println(response.toString());

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }*/

    private void getDetails() throws SQLException {
        dataLink.getDetails();
        AllNames = dataLink.getName();
        Alldops = dataLink.getDop();
        Allsrs = dataLink.getSr();
        Allamc = dataLink.getAmc();
        Allex = dataLink.getExp();
    }

    @RequestMapping(value = "/pdf/{fileName:.+}")
    public ModelAndView createReport(HttpServletResponse response, HttpServletRequest request, @PathVariable("fileName") String records) throws FileNotFoundException, DocumentException {
        File file = new File("/home/raghav/IdeaProjects/ApplianceRecords/src/main/webapp/WEB-INF/downloads/all_details.pdf");
        Document doc = new Document();
        PdfWriter.getInstance(doc, new FileOutputStream(file));
        doc.open();
        PdfPTable table = new PdfPTable(5);
        addTableHeader(table);
        for (int i=0; i<Allamc.size(); i++) {
            addRows(table, AllNames.get(i), Alldops.get(i), Allsrs.get(i), Allamc.get(i), Allex.get(i));
        }
        doc.add(table);
        doc.close();
        fileDownload(response, request, records);
        return homeModel;
    }

    private void fileDownload(HttpServletResponse response, HttpServletRequest request, String name){
        String downloadFolder = request.getServletContext().getRealPath("/WEB-INF/downloads/");
        Path file1 = Paths.get(downloadFolder, name);
        if(Files.exists(file1)) {
            response.setContentType("application/pdf");
            response.addHeader("Content-Disposition", "attachement; filename=" + name);
            try {
                Files.copy(file1, response.getOutputStream());
                response.getOutputStream().flush();
                response.getOutputStream().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @RequestMapping(value = "/pdfs/{report:.+}")
    public ModelAndView createIndependentReport(HttpServletRequest request, HttpServletResponse response, @PathVariable("report") String details) throws SQLException, DocumentException, FileNotFoundException {
        //code to produce file and download it
        id = dataLink.getIdfromName(home.getApplianceName());
        dataLink.getRemarks(id);
        File file = new File("/home/raghav/IdeaProjects/ApplianceRecords/src/main/webapp/WEB-INF/downloads/details_records.pdf");
        Document doc = new Document();
        PdfWriter.getInstance(doc, new FileOutputStream(file));
        doc.open();
        PdfPTable table = new PdfPTable(2);
        dataLink.getRemarks(id);
        Paragraph p = new Paragraph();
        p.add(new Chunk("Name: "+n));
        p.add(Chunk.NEWLINE);
        p.add(new Chunk("AMC: "+ a));
        p.add(Chunk.NEWLINE);
        p.add(new Chunk("Duration: "+ d + " Years"));
        p.add(Chunk.NEWLINE);
        p.add(new Chunk("Expiry: "+ e));
        p.add(Chunk.NEWLINE);
        p.add(new Chunk("DoP: "+ dp));
        p.add(Chunk.NEWLINE);
        p.add(new Chunk("Servicing: "+ ser));
        p.add(Chunk.NEWLINE);
        p.add(new Chunk("Service Time: "+sertime + " Months"));
        p.add(Chunk.NEWLINE);
        p.add(new Chunk("Next Service : "+ nex));
        p.add(Chunk.NEWLINE);
        p.add(Chunk.NEWLINE);
        doc.add(p);
        addTableHeader2(table);
        for (int i=0; i<dataLink.getServices().size(); i++){
            addRows(table, dataLink.getServices().get(i),dataLink.getRem().get(i));
        }
        doc.add(table);
        doc.close();
        fileDownload(response, request, details);
        return detailsModel;
    }

    private void addRows(PdfPTable table, String name, String dop, String srs, String amc, String exp) {
        table.addCell(name);
        table.addCell(dop);
        table.addCell(srs);
        table.addCell(amc);
        table.addCell(exp);
    }

    private void addRows(PdfPTable table, String service, String remarks) {
        table.addCell(service);
        table.addCell(remarks);
    }


    private void addTableHeader(PdfPTable table) {
        Stream.of("Name","DoP","Service Requirement","AMC","Expiry Date").forEach(columnTitle -> pdfcells(table, columnTitle));
    }

    private void pdfcells(PdfPTable table, String h){
        PdfPCell header = new PdfPCell();
        header.setBackgroundColor(BaseColor.LIGHT_GRAY);
        header.setBorderWidth(2);
        header.setPhrase(new Phrase(h));
        table.addCell(header);
    }

    private void addTableHeader2(PdfPTable table) {
        Stream.of("Service Date","Remarks").forEach(columnTitle -> pdfcells(table, columnTitle));
    }
}