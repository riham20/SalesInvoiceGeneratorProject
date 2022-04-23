/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sig.model;

/**
 *
 * @author RM02206
 */
public class InvoiceLine {
    
    
    private InvoiceHeader header; //to access invoice and get its num
    private String item;
    private double price;
    private int count;
    public InvoiceLine() {
    }

    public InvoiceLine(InvoiceHeader header, String item, double price, int count) {
        this.header = header;
        this.item = item;
        this.price = price;
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public InvoiceHeader getHeader() {
        return header;
    }

    public void setHeader(InvoiceHeader header) {
        this.header = header;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    
    public double getLineTotal(){
        return price * count;
    }

    @Override
    public String toString() {
        return header.getNum() + "," + item + "," + price + "," + count;
    }


      
    
}
