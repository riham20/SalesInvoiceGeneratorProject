/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sig.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author RM02206
 */
public class InvoiceHeader {
    
    
    private int num;
    private Date invDate;
    private String customer;
    private ArrayList<InvoiceLine> lines;
    private DateFormat datef = new SimpleDateFormat("dd-MM-yyyy");
    
    public InvoiceHeader(int num, Date invDate, String customer) {
        this.num = num;
        this.invDate = invDate;
        this.customer = customer;
        
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public Date getInvDate() {
        return invDate;
    }

    public void setInvDate(Date invDate) {
        this.invDate = invDate;
    }

    public ArrayList<InvoiceLine> getLines() {
        if(lines == null){
         lines = new ArrayList<>();
        }
        return lines;
    }

    public void setLines(ArrayList<InvoiceLine> lines) {
        this.lines = lines;
    }
    
    public double getInvoiceTotal(){
        
        double total = 0.0;
        for(int i = 0; i<getLines().size(); i++){
            total += getLines().get(i).getLineTotal();
        }
        return total;
    }

    @Override
    public String toString() {
        return num + "," + datef.format(invDate) + "," + customer;
    }
    
    
}
