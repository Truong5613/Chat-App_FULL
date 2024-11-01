/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package component;

import Service.Service;
import event.EventFileReceiver;
import event.EventFileSender;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import model.Model_File_Sender;
import model.Model_Receive_File;

/**
 *
 * @author mrtru
 */
public class Chat_File extends javax.swing.JPanel {

    /**
     * Creates new form Chat_file
     */
    public Chat_File() {
        initComponents();
        setOpaque(false);
    }

    public void setFile(Model_File_Sender fileSender) {
        lbFileName.setText(fileSender.getFile().getName());
        String readsize = formatFileSize(fileSender.getFileSize());
        lbFileSize.setText(readsize);
        addEvent();
        fileSender.addEvent(new EventFileSender() {
            @Override
            public void onSending(double percentage) {

                progress.setValue((int) percentage);
            }

            @Override
            public void onStartSending() {
            }

            @Override
            public void onFinish() {
                progress.setValue(0);
            }

        });
    }

    public void setFile(Model_Receive_File dataFile) {
        try {
            addEvent();
            Service.getInstance().addFileReceiver(dataFile.getFileID(), new EventFileReceiver() {
                @Override
                public void onReceiving(double percentage) {
//                    lbFileName.setText(String.valueOf(dataFile.getFileID()));
                }

                @Override
                public void onStartReceiving(long filesize, String filename, String fileExtension) {
                    String readsize = formatFileSize(filesize);
                    lbFileName.setText(filename + fileExtension);
                    lbFileSize.setText(readsize);
                }

                @Override
                public void onFinish(File file) {
                    progress.setValue(0);

                }

            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addEvent() {
        jPanel2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {  // Single-click
                    File file = new File("client_data/" + lbFileName.getText());
                    openFile(file);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                jPanel2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));  // Set hand cursor
            }

            @Override
            public void mouseExited(MouseEvent e) {
                jPanel2.setCursor(Cursor.getDefaultCursor());  // Reset to default cursor
            }
        });
        progress.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {  // Single-click
                    File file = new File("client_data/" + lbFileName.getText());
                    openFile(file);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                jPanel2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));  // Set hand cursor
            }

            @Override
            public void mouseExited(MouseEvent e) {
                jPanel2.setCursor(Cursor.getDefaultCursor());  // Reset to default cursor
            }
        });
    }

    private String formatFileSize(long size) {
        if (size < 1024) {
            return size + " B";
        } else if (size < 1024 * 1024) {
            return String.format("%.2f KB", size / 1024.0);
        } else if (size < 1024 * 1024 * 1024) {
            return String.format("%.2f MB", size / (1024.0 * 1024));
        } else {
            return String.format("%.2f GB", size / (1024.0 * 1024 * 1024));
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        progress = new swing.Progress();
        jPanel1 = new javax.swing.JPanel();
        lbFileName = new javax.swing.JLabel();
        lbFileSize = new javax.swing.JLabel();

        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });

        jPanel2.setOpaque(false);

        progress.setProgresstype(swing.Progress.ProgressType.FILE);
        progress.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                progressMouseClicked(evt);
            }
        });

        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.GridLayout(2, 1));

        lbFileName.setText("My File Name.file");
        jPanel1.add(lbFileName);

        lbFileSize.setForeground(new java.awt.Color(51, 102, 255));
        lbFileSize.setText("5mb");
        jPanel1.add(lbFileSize);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(progress, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(progress, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void openFile(File file) {
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().open(file);
            } catch (IOException e) {
                e.printStackTrace();  // Handle error (e.g., file not found)
            }
        } else {
            System.out.println("Desktop is not supported");
        }
    }
    private void progressMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_progressMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_progressMouseClicked

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_formMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lbFileName;
    private javax.swing.JLabel lbFileSize;
    private swing.Progress progress;
    // End of variables declaration//GEN-END:variables
}
