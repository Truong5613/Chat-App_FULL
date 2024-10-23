/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package event;

/**
 *
 * @author mrtru
 */
public class PublicEvent {
    private static PublicEvent instance;
    private EventImageView eventImageView;
    private EventChat eventChat;
    private EventLogin eventLogin;
    private EventMain eventMain;
    private EventMenuLeft eventMenuLeft;  
   
    private EventSetUser eventGetImage;
    private EventUpdateUser eventUpdateUser;
    private EventLeft eventLeft;
    private EventUserUpdate eventUserUpdate;
    private EventOverpanel eventOverpanel;
    
    public static PublicEvent getInstance(){
        if(instance==null){
            instance = new PublicEvent();
        }
        return instance;
    }
    private PublicEvent(){
        
    }

    public void addEventOverpanel(EventOverpanel eventOverpanel) {
        this.eventOverpanel = eventOverpanel;
    }
    
    public void addEventUserUpdate(EventUserUpdate eventUserUpdate){
        this.eventUserUpdate = eventUserUpdate;
    }
    
    public void addEventUpdateUser(EventUpdateUser eventUpdateUser) {
        this.eventUpdateUser = eventUpdateUser;
    }
    
    public void addEventSetUser(EventSetUser eventGetImage) {
        this.eventGetImage = eventGetImage;
    }
    
    public void addEventMain(EventMain event){
        this.eventMain = event;
    }
    
    public void addEventImageView(EventImageView event){
        this.eventImageView=event;
    }
    
    public void addEventLogin(EventLogin event){
        this.eventLogin= event;
    }
    
    public void addEventChat(EventChat event) {
        this.eventChat = event;
    }
    
    public void addEventMenuLeft(EventMenuLeft event){
        this.eventMenuLeft = event;
    }
    
    public void addEventLeft(EventLeft event){
        this.eventLeft = event;
    }
    
    
    
    public EventOverpanel getEventOverpanel() {
        return eventOverpanel;
    }
    public EventUserUpdate getEventUserUpdate(){
        return eventUserUpdate;
    }
    public EventMain getEventMain(){
        return eventMain;
    }
    public EventChat getEventChat() {
        return eventChat;
    }
    public EventLogin getEventLogin(){
        return eventLogin;
    }
    public EventImageView getEventImageView(){
        return eventImageView;
    }
    public EventMenuLeft getEventMenuLeft(){
        return eventMenuLeft;
    }
    public EventSetUser getEventSetUser() {
        return eventGetImage;
    }
    public EventUpdateUser getEventUpdateUser() {
        return eventUpdateUser;
    }
    public EventLeft getEventLeft(){
        return eventLeft;
    }
}
