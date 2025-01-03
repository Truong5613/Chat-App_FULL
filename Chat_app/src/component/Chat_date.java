/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package component;

/**
 *
 * @author mrtru
 */
public class Chat_date extends javax.swing.JPanel {

    /**
     * Creates new form Chat_date
     */
    public Chat_date() {
        initComponents();
    }
    public void setDate(String date) {
        String formattedDate = convertDateFormat(date);
        lbdate.setText("Ngày "+formattedDate);
    }
    
    
    private String convertDateFormat(String date) {
    // Check if the input date is valid
    if (date != null && date.length() >= 10) {
        // Split the date into components
        String[] parts = date.split("-");
        if (parts.length == 3) {
            // Rearrange and return in DD-MM-YYYY format
            return parts[2] + "-" + parts[1] + "-" + parts[0]; // DD-MM-YYYY
        }
    }
    // Return the original date if the format is invalid
    return date;
}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbdate = new javax.swing.JLabel();
        line1 = new swing.Line();
        line2 = new swing.Line();

        setBackground(new java.awt.Color(255, 255, 255));

        lbdate.setForeground(new java.awt.Color(84, 72, 72));
        lbdate.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbdate.setText("00/00/0000");

        line1.setForeground(new java.awt.Color(84, 72, 72));

        line2.setForeground(new java.awt.Color(84, 72, 72));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(line1, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                .addGap(12, 12, 12)
                .addComponent(lbdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(line2, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lbdate)
                        .addGap(1, 1, 1))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(line2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(line1, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lbdate;
    private swing.Line line1;
    private swing.Line line2;
    // End of variables declaration//GEN-END:variables
}
