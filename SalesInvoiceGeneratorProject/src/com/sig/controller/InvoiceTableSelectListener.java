/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sig.controller;

import com.sig.model.InvoiceHeader;
import com.sig.model.InvoiceLine;
import com.sig.model.InvoiceLineTableModel;
import com.sig.view.InvoiceForm;
import java.util.ArrayList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author RM02206
 */
public class InvoiceTableSelectListener implements ListSelectionListener{
    private InvoiceForm form;
    
    public InvoiceTableSelectListener(InvoiceForm form){
    this.form = form;
    
    }
    @Override
    public void valueChanged(ListSelectionEvent e) {
        int selectedInvoiceIndex = form.getInvoicesTable().getSelectedRow();
        System.out.println("Invoice Selected: " + selectedInvoiceIndex);
        if(selectedInvoiceIndex != -1){
        InvoiceHeader selectedInvoice = form.getInvoicsesArray().get(selectedInvoiceIndex);
        ArrayList<InvoiceLine> lines = selectedInvoice.getLines();
        InvoiceLineTableModel lineTableModel = new InvoiceLineTableModel(lines);
        form.setLinesArray(lines);
        form.getInvoiceItemsTable().setModel(lineTableModel);
        form.getInvNumLbl().setText(""+selectedInvoice.getNum());
        form.getInvDateLbl().setText(InvoiceForm.dateFormat.format(selectedInvoice.getInvDate()));
        form.getCustNameLbl().setText(selectedInvoice.getCustomer());
        form.getInvTotalLbl().setText(""+selectedInvoice.getInvoiceTotal());  }
    }
    
}
