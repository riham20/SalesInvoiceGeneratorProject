/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sig.model;

import com.sig.view.InvoiceForm;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author RM02206
 */
public class InvoiceHeaderTableModel extends AbstractTableModel{
    
    
      
    private ArrayList<InvoiceHeader> invoicesArray;
    private String[] columns = {"No.", "Date", "Customer", "Total"};

    public InvoiceHeaderTableModel(ArrayList<InvoiceHeader> invoicesArray) {
        this.invoicesArray = invoicesArray;
    }
    
    
    @Override
    public int getRowCount() {
        
    return invoicesArray.size();    
    }

    @Override
    public int getColumnCount() {
        
    return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
    InvoiceHeader invH = invoicesArray.get(rowIndex);
    switch(columnIndex){
        case 0: 
            return invH.getNum();
        case 1:
            return InvoiceForm.dateFormat.format(invH.getInvDate());
        case 2:    
            return invH.getCustomer();
        case 3:    
            return invH.getInvoiceTotal();
    }
    return "";
    }
    
    @Override
    public String getColumnName(int column) {
        
        return columns[column];
    } 
    
}
