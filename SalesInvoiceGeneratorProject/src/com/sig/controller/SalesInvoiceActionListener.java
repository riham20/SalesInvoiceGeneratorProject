/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sig.controller;

import com.sig.model.InvoiceHeader;
import com.sig.model.InvoiceHeaderTableModel;
import com.sig.model.InvoiceLine;
import com.sig.model.InvoiceLineTableModel;
import com.sig.view.HeaderDialog;
import com.sig.view.InvoiceForm;
import com.sig.view.LineDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author RM02206
 */
public class SalesInvoiceActionListener implements ActionListener{
    
    private InvoiceForm form;
    private HeaderDialog headerDialog;
    private LineDialog lineDialog;
    
    public SalesInvoiceActionListener(InvoiceForm form) {
         this.form = form;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       switch (e.getActionCommand()){
           
           
            case "Load File":
               loadFile();
                   break;                
                   
            case "Save File":
               saveFile();
                   break;  
   
            case "Delete Invoice":
               deleteInvoice();
                   break;                    
                            
            case "Create New Invoice":
               createNewInvoice();
                   break;     
                   
            case "New Line":
               createNewLine();
                   break;  

            case "Delete Line":
               deleteLine();
                   break;                      
                   
            case "newAddedInvoiceCancel":     
               newAddedInvoiceDialogCancel();
                   break;
             
            case "newAddedInvoiceOk":    
               newAddedInvoiceDialogOk();
                   break;
                   
            case "newAddedLineOK":       
                NewLineDialogOk();
                break;   
                
                
            case "newAddedLineCancel":    
                NewLineDialogCancel();
                break;
                
       }
       
    }


    private void saveFile() {
        
        ArrayList<InvoiceHeader> invoicesArray = form.getInvoicsesArray();
        JFileChooser filec = new JFileChooser();
        try {
            int result = filec.showSaveDialog(form);
            if (result == JFileChooser.APPROVE_OPTION) {
                File headerFile = filec.getSelectedFile();
                FileWriter headerfw = new FileWriter(headerFile);
                String headers = "";
                String lines = "";
                for (InvoiceHeader invoice : invoicesArray) {
                    headers = headers + invoice.toString();
                    headers = headers + "\n";
                    for (InvoiceLine line : invoice.getLines()) {
                        lines = lines + line.toString();
                        lines = lines + "\n";
                    }
                }


                headers = headers.substring(0, headers.length()-1);
                lines = lines.substring(0, lines.length()-1);
                result = filec.showSaveDialog(form);
                File lineFile = filec.getSelectedFile();
                FileWriter linefw = new FileWriter(lineFile);
                headerfw.write(headers);
                linefw.write(lines);
                headerfw.close();
                linefw.close();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(form, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }  
        
        
    }

    private void loadFile() {
        JFileChooser fileChooser = new JFileChooser();
        try {
            int result = fileChooser.showOpenDialog(form);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedfile = fileChooser.getSelectedFile();
                Path filePath = Paths.get(selectedfile.getAbsolutePath());
                List<String> selectedfilelines = Files.readAllLines(filePath);
                ArrayList<InvoiceHeader> invoiceHeaders = new ArrayList<>();
                for (String fileLine : selectedfilelines) {
                    String[] items = fileLine.split(",");
                    String id = items[0];
                    String date = items[1];
                    String name = items[2];
                    int code = Integer.parseInt(id);
                    Date invoiceDate = InvoiceForm.dateFormat.parse(date);
                    InvoiceHeader header = new InvoiceHeader(code,invoiceDate,name);
                    invoiceHeaders.add(header);
                    
                }
                
                form.setInvoicesArray(invoiceHeaders);
                
                result = fileChooser.showOpenDialog(form);
                if (result == JFileChooser.APPROVE_OPTION) {
                File lineFile = fileChooser.getSelectedFile();
                Path linePath = Paths.get(lineFile.getAbsolutePath());
                List<String> lineLines = Files.readAllLines(linePath);
                ArrayList<InvoiceLine> invoiceLines = new ArrayList<>();
                
                int x = 1;
                for(String lineLine : lineLines){
                    String[] arry =  lineLine.split(",");
                    String invoiceId = arry[0];
                    String itemName = arry[1];
                    String itemPrice = arry[2];
                    String linecount = arry[3];
                    int invCode = Integer.parseInt(invoiceId);
                    double price = Double.parseDouble(itemPrice);
                    int count = Integer.parseInt(linecount);
                    
                    InvoiceHeader invoice = form.getInvObject(invCode);
                    InvoiceLine line = new InvoiceLine(invoice,itemName,price,count);
                    invoice.getLines().add(line);            
                  }
                }
                  
                InvoiceHeaderTableModel invoiceHeaderTable = new InvoiceHeaderTableModel(invoiceHeaders);
                form.setHeaderTableModel(invoiceHeaderTable);
                form.getInvoicesTable().setModel(invoiceHeaderTable);
                
                }
  
        } catch (IOException e) {
            JOptionPane.showMessageDialog(form, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(form, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        form.displayInvoicesAndLines();
    }
   
    private void createNewInvoice() {
 
        headerDialog = new HeaderDialog(form);
        headerDialog.setVisible(true);
    }

    
    //Delete invoice button functionality

    private void deleteInvoice() {
        
        int selectedInvoiceIndex = form.getInvoicesTable().getSelectedRow();
        if (selectedInvoiceIndex != -1) {
            form.getInvoicsesArray().remove(selectedInvoiceIndex);
            form.getHeaderTableModel().fireTableDataChanged();
            
            form.getInvoiceItemsTable().setModel(new InvoiceLineTableModel(null));
            form.setLinesArray(null);
            form.getCustNameLbl().setText("");
            form.getInvNumLbl().setText("");
            form.getInvTotalLbl().setText("");
            form.getInvDateLbl().setText("");
        }
    }

    private void createNewLine() {
        
        lineDialog = new LineDialog(form);
        lineDialog.setVisible(true);
    }

    
    //Delete line button functionality
    
    private void deleteLine() {
        
        int selectedLineIndex = form.getInvoiceItemsTable().getSelectedRow();
        int selectedInvoiceIndex = form.getInvoicesTable().getSelectedRow();
        if (selectedLineIndex != -1) {
            form.getLinesArray().remove(selectedLineIndex);
            InvoiceLineTableModel lineTableModel = (InvoiceLineTableModel) form.getInvoiceItemsTable().getModel();
            lineTableModel.fireTableDataChanged();
            form.getInvTotalLbl().setText("" + form.getInvoicsesArray().get(selectedInvoiceIndex).getInvoiceTotal());
            form.getHeaderTableModel().fireTableDataChanged();
            form.getInvoicesTable().setRowSelectionInterval(selectedInvoiceIndex, selectedInvoiceIndex);
        }
    }
    
    //Invoice Dialog "ok" button functionality
    
    private void newAddedInvoiceDialogOk(){
        headerDialog.setVisible(false);

        String custName = headerDialog.getCustomerName().getText();
        String str = headerDialog.getDate().getText();
        Date d = new Date();
        try {
            d = InvoiceForm.dateFormat.parse(str);
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(form, "Cannot parse date, resetting to today's date.", "Invalid date format", JOptionPane.ERROR_MESSAGE);
        }

        int invNum = 0;
        for (InvoiceHeader inv : form.getInvoicsesArray()) {
            if (inv.getNum() > invNum) {
                invNum = inv.getNum();
            }
        }
        invNum++;
        InvoiceHeader newInv = new InvoiceHeader(invNum, d, custName);
        form.getInvoicsesArray().add(newInv);
        form.getHeaderTableModel().fireTableDataChanged();
        headerDialog.dispose();
        headerDialog = null;
    }

    
    //Invoice Dialog "cancel" button functionality
    
    private void newAddedInvoiceDialogCancel(){
        headerDialog.setVisible(false);
        headerDialog.dispose();
        headerDialog = null;
    }

    
    //Invoice Dialog "ok" button functionality
    
    private void NewLineDialogOk() {
        lineDialog.setVisible(false);

        String name = lineDialog.getItemName().getText();
        String str1 = lineDialog.getItemCount().getText();
        String str2 = lineDialog.getItemPrice().getText();
        int count = 1;
        double price = 1;
        try {
            count = Integer.parseInt(str1);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(form, "Cannot convert number", "Invalid number format", JOptionPane.ERROR_MESSAGE);
        }

        try {
            price = Double.parseDouble(str2);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(form, "Cannot convert price", "Invalid number format", JOptionPane.ERROR_MESSAGE);
        }
        int selectedInvHeader = form.getInvoicesTable().getSelectedRow();
        if (selectedInvHeader != -1) {
            InvoiceHeader invHeader = form.getInvoicsesArray().get(selectedInvHeader);
            InvoiceLine line = new InvoiceLine(invHeader,name, price, count);

            form.getLinesArray().add(line);
            InvoiceLineTableModel lineTableModel = (InvoiceLineTableModel) form.getInvoiceItemsTable().getModel();
            lineTableModel.fireTableDataChanged();
            form.getHeaderTableModel().fireTableDataChanged();
        }
        form.getInvoicesTable().setRowSelectionInterval(selectedInvHeader, selectedInvHeader);
        lineDialog.dispose();
        lineDialog = null;
    }

    
    //Line Dialog "cancel" button functionality
    private void NewLineDialogCancel() {
        
        lineDialog.setVisible(false);
        lineDialog.dispose();
        lineDialog = null;
    }
    
}
