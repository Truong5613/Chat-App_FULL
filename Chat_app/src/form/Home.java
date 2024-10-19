
package form;

import event.EventProfileUserChat;
import event.PublicEvent;
import model.Model_Receive_Message;
import model.Model_Send_Message;
import model.Model_User_Account;
import net.miginfocom.swing.MigLayout;


public class Home extends javax.swing.JLayeredPane {
    private Chat chat;
    private Menu_Left menuLeft;
    private Menu_Right menuRight;
    private Model_User_Account user;
    public Home() {
        initComponents();
        init();
    }
    
    private void init() {
        setLayout(new MigLayout("fillx, filly", "0[fill,200!]5[fill,100%]0", "0[fill]0"));
        menuLeft = new Menu_Left();
        chat = new Chat();
        menuRight = new Menu_Right();
        
        this.add(menuLeft);
        this.add(chat);
        
        chat.setVisible(false);
        chat.setHome(this);
        
        
    }

    public void toggleMenuRight(boolean show) {
        if (show) {        
            PublicEvent.getInstance().addEventProfileUserChat( new EventProfileUserChat(){
            @Override
            public void openMenuRight() {
                setLayout(new MigLayout("fillx, filly", "0[fill,200!]5[fill,100%]5[fill,450!]0", "0[fill]0"));
                menuRight.setUserName(user);             
            }
            @Override
            public void closeMenuRight(){
               setLayout(new MigLayout("fillx, filly", "0[fill,200!]5[fill,100%]0", "0[fill]0"));               
            }
            });
            this.add(menuRight);
        } else {
            this.remove(menuRight);
        }
        revalidate();
        repaint();
    }

    public void setUser(Model_User_Account user) {
        chat.setUser(user);
        this.user = user;
        chat.setVisible(true);
    }

    public void updateUser(Model_User_Account user) {
        chat.updateUser(user);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
