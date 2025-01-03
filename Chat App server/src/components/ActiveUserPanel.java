/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package components;

import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import model.Model_User_Account;

/**
 *
 * @author Admin
 */
public class ActiveUserPanel extends javax.swing.JPanel {
  
   public ActiveUserPanel(List<Model_User_Account> activeUsers) {
        // Define column names
        String[] columnNames = {"UserID", "UserName", "Gender"};

        // Create a table model and add the rows of data
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        
        // Populate table model with activeUsers data
        for (Model_User_Account user : activeUsers) {
            Object[] row = {user.getUserID(), user.getUserName(), user.getGender()};
            tableModel.addRow(row);
        }

        // Create the JTable with the table model
        JTable table = new JTable(tableModel);

        // Add the table to a scroll pane (in case there are too many rows)
        JScrollPane scrollPane = new JScrollPane(table);

        // Add the scroll pane to the panel
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Use vertical box layout
        add(scrollPane);
    }

    public void updateActiveUsers(List<Model_User_Account> activeUsers) {
        // Remove all components before updating
        removeAll();

        // Define column names
        String[] columnNames = {"UserID", "UserName", "Gender"};

        // Create a table model and add the rows of data
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        
        // Populate table model with activeUsers data
        for (Model_User_Account user : activeUsers) {
            Object[] row = {user.getUserID(), user.getUserName(), user.getGender()};
            tableModel.addRow(row);
        }

        // Create the JTable with the table model
        JTable table = new JTable(tableModel);

        // Add the table to a scroll pane (in case there are too many rows)
        JScrollPane scrollPane = new JScrollPane(table);

        // Add the scroll pane to the panel
        add(scrollPane);

        // Refresh the panel
        revalidate();
        repaint();
    }
    /**
     * Creates new form ActiveUserPanel
     */
    public ActiveUserPanel() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();

        jScrollPane1.setViewportView(jList1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList<String> jList1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
