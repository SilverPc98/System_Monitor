package com.mycompany.system_monitor;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import oshi.SystemInfo;
import oshi.hardware.Baseboard;
import oshi.hardware.ComputerSystem;
import oshi.hardware.CentralProcessor;
import oshi.hardware.Sensors;
import oshi.hardware.Firmware;
import oshi.hardware.GlobalMemory;

public class FXMLController implements Initializable {
    SystemInfo si = new SystemInfo();
    ComputerSystem cs = si.getHardware().getComputerSystem();
    Baseboard bb = cs.getBaseboard();
    Firmware fw = cs.getFirmware();
    GlobalMemory ram = si.getHardware().getMemory();
    
    
    InetAddress ip;
    
    @FXML
    private ImageView btn_system, btn_cpu, btn_disk, btn_up, btn_exit, pic_logo;
    @FXML
    private AnchorPane h_system, h_cpu, h_disk;
    @FXML
    private Label lbl_user, lbl_os, lbl_arch, lbl_cpname, lbl_baseb, lbl_firm, lbl_memory, lbl_ipaddr, lbl_macaddr;
    
    
    @FXML
    private void handleButtonAction(MouseEvent event) {
        if (event.getTarget() == btn_system){
            h_system.setVisible(true);
            h_cpu.setVisible(false);
            h_disk.setVisible(false);
        }else{
            if (event.getTarget() == btn_cpu){
                h_cpu.setVisible(true);
                h_system.setVisible(false);
                h_disk.setVisible(false);
            }else{
                if(event.getTarget() == btn_disk){
                    h_disk.setVisible(true);
                    h_cpu.setVisible(false);
                    h_system.setVisible(false);
                }else{
                    if(event.getTarget() == btn_up){
                    h_disk.setVisible(false);
                    h_cpu.setVisible(false);
                    h_system.setVisible(false);
                    }else{
                        if(event.getTarget() == btn_exit){
                            System.exit(0);
                        }
                    
                    }
                
                }
            }
        
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lbl_user.setText(System.getProperty("user.name"));
        lbl_os.setText(System.getProperty("os.name"));
        Image windows = new Image("images/windows.png");//this.getClass().getResourceAsStream("../src/main/resources/images/windows.png")
        Image mac_logo = new Image("images/mac.png");//this.getClass().getResourceAsStream("../src/main/resources/images/mac.png")
        Image linux = new Image("images/linux.png");//this.getClass().getResourceAsStream("../src/main/resources/images/linux.png")
        
        if (lbl_os.getText().contains("Windows")){
            pic_logo.setImage(windows);
            pic_logo.setFitHeight(60);
            pic_logo.setFitWidth(60);
            
        }else{
            if (lbl_os.getText().contains("Mac")){
                pic_logo.setImage(mac_logo);
                pic_logo.setFitHeight(60);
                pic_logo.setFitWidth(60);
            }else{
                if (lbl_os.getText().contains("Linux")){
                pic_logo.setImage(linux);
                pic_logo.setFitHeight(60);
                pic_logo.setFitWidth(60);
                }
            
            }
        
        }
        lbl_arch.setText(System.getProperty("sun.arch.data.model") + " bites operációs rendszer");
        try{
            ip = InetAddress.getLocalHost();
            lbl_cpname.setText(ip.getHostName());
            lbl_ipaddr.setText(ip.getHostAddress());
        }catch(Exception ex){
            System.err.println(ex);
        }
   
        lbl_baseb.setText(bb.getManufacturer());//Baseboard type :cs.getModel()
        lbl_firm.setText(fw.getManufacturer());
        double mem = (((ram.getTotal()/1024.0)/1024.0)/1024.0);
        String memm = "" + mem;
        lbl_memory.setText("" + memm.substring(0, 5) + " GB");
        
        
        
        NetworkInterface network = null;
        try {
            network = NetworkInterface.getByInetAddress(ip);
        } catch (SocketException ex) {
            Logger.getLogger(Scene.class.getName()).log(Level.SEVERE, null, ex);
        }
        byte[] mac = null;
        try {
            mac = network.getHardwareAddress();
        } catch (SocketException ex) {
            //Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            lbl_macaddr.setText("Nem található MAC cím");
        }
  
  
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mac.length; i++) {
            sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));     
        }
        lbl_macaddr.setText(sb.toString());
        
        
    }    
}
