/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package form;

import Service.Service;
import event.EventLogin;
import event.EventMessage;
import event.PublicEvent;
import io.socket.client.Ack;
import javax.annotation.processing.Messager;
import model.Model_Login;
import model.Model_Message;
import model.Model_Register;
import model.Model_User_Account;


import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.oauth2.model.Userinfo;
import java.sql.ResultSet;
import com.google.api.services.oauth2.model.Userinfoplus;
import event.EventLeft;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;
import model.Model_Login_OAuth;
/**
 *
 * @author mrtru
 */
public class Login extends javax.swing.JPanel {

    private static final String CREDENTIALS_FILE_PATH = "src/path/to/client_secret.json";
    private static final List<String> SCOPES = Collections.singletonList("https://www.googleapis.com/auth/userinfo.email");
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private Service Service;
    private Credential credential;
    private Left left;
    /**
     * Creates new form Login
     */
    public Login() {
        initComponents();
        init();
    }
    
    private void init(){
        PublicEvent.getInstance().addEventLogin(new EventLogin() {
            @Override
            public void login(Model_Login data) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        PublicEvent.getInstance().getEventMain().showLoading(true);
                        Service.getInstance().getClient().emit("login", data.toJsonObject(),new Ack(){
                            @Override
                            public void call(Object... os) {
                                if(os.length>0){
                                    //System.out.printf(os[1].toString());
                                    boolean action = (boolean)os[0];
                                    if(action){
                                        Service.getInstance().setUser(new Model_User_Account(os[1]));                                     
                                        PublicEvent.getInstance().getEventMain().showLoading(false);
                                        PublicEvent.getInstance().getEventSetUser();
                                        PublicEvent.getInstance().getEventMain().initchat();
                                        PublicEvent.getInstance().getEventLeft().setImage(new Model_User_Account(os[1]));
                                        setVisible(false);
                                    }else{
                                        //sai mat khau
                                        lbError.setText("Sai mat khau hoac tai khoan khong dung");
                                        PublicEvent.getInstance().getEventMain().showLoading(false);
                                    }
                                }else{
                                    PublicEvent.getInstance().getEventMain().showLoading(false);
                                }
                            }
                            
                        });
                    }
                }).start();
            }
       
            @Override
            public void loginOauth() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Model_Login_OAuth data = new Model_Login_OAuth();
                            credential = getCredentials(new NetHttpTransport());

                            Userinfoplus userInfo = getUserInfo(credential);
                            String email = userInfo.getEmail(); 
                            String userName = email; 

                            data.setUserName(userName);
                            data.setPassword(credential.getAccessToken()); 

                            System.out.println("AccessToken: " + credential.getAccessToken());

                            PublicEvent.getInstance().getEventMain().showLoading(true);
                            Service.getInstance().getClient().emit("loginOAuth", data.toJsonObject(), new Ack() {
                                @Override
                                public void call(Object... os) {
                                    if (os.length > 0) {
                                        boolean action = (boolean) os[0];
                                        if (action) {
                                            Service.getInstance().setUser(new Model_User_Account(os[1]));
                                            PublicEvent.getInstance().getEventMain().showLoading(false);
                                            PublicEvent.getInstance().getEventMain().initchat();
                                            PublicEvent.getInstance().getEventLeft().setImage(new Model_User_Account(os[1]));
                                            setVisible(false); // Ẩn màn hình đăng nhập
                                        } else {                                        
                                            PublicEvent.getInstance().getEventMain().showLoading(false);
                                            System.err.println("Login OAuth failed");
                                        }
                                    } else {
                                        PublicEvent.getInstance().getEventMain().showLoading(false);
                                        System.err.println("No response from server");
                                    }
                                }
                            });
                        } catch (Exception e) {
                            PublicEvent.getInstance().getEventMain().showLoading(false);
                            e.printStackTrace(); 
                        }
                    }
                }).start();
            }
            
            @Override
            public void register(Model_Register data, EventMessage message) {           
                Service.getInstance().getClient().emit("register", data.toJsonObject(),new Ack(){
                    @Override
                    public void call(Object... os) {
                        if (os.length > 0) {
                            Model_Message ms = new Model_Message((boolean) os[0], os[1].toString());
                            if (ms.isAction()) {
                                Model_User_Account user = new Model_User_Account(os[2]);
                                Service.getInstance().setUser(user);
                            }
                            message.callMessage(ms);
                        }
                    }
                    
                });
            }

            @Override
            public void goRegister() {
                slide.show(1);
            }

            @Override
            public void goLogin() {
                slide.show(0);
            }
        });
        
        P_Login login = new P_Login();
        P_Register register = new P_Register();
        slide.init(login,register);
    }
    
    private Userinfoplus getUserInfo(Credential credential) throws IOException {
        Oauth2 oauth2 = new Oauth2.Builder(new NetHttpTransport(), JSON_FACTORY, credential)
                .setApplicationName("Java Chapapplication")
                .build();
        Userinfoplus userInfo = oauth2.userinfo().get().execute();
        return userInfo;
    }

    private Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws Exception {
        InputStream in = new FileInputStream(CREDENTIALS_FILE_PATH);
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setAccessType("offline") // Ensure a new token is fetched each time
                .build();

        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(12345).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        slide = new swing.PanelSlide();
        lbError = new javax.swing.JLabel();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setBackground(new java.awt.Color(47, 58, 153));
        jLabel1.setOpaque(true);

        jPanel1.setBackground(new java.awt.Color(102, 102, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        slide.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout slideLayout = new javax.swing.GroupLayout(slide);
        slide.setLayout(slideLayout);
        slideLayout.setHorizontalGroup(
            slideLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 270, Short.MAX_VALUE)
        );
        slideLayout.setVerticalGroup(
            slideLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 318, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(slide, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(slide, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        lbError.setForeground(new java.awt.Color(255, 51, 0));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/red login form.gif"))); // NOI18N

        jLayeredPane1.setLayer(jLabel3, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 764, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 511, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setText("CHAT APPLICATION");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbError, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(lbError, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lbError;
    private swing.PanelSlide slide;
    // End of variables declaration//GEN-END:variables
}
