package com.mycompany.system_monitor;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import oshi.SystemInfo;
import oshi.hardware.Baseboard;
import oshi.hardware.ComputerSystem;
import oshi.hardware.CentralProcessor;
import oshi.hardware.Sensors;
import oshi.hardware.Firmware;
import oshi.hardware.GlobalMemory;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.util.Duration;


public class FXMLController implements Initializable {
    SystemInfo si = new SystemInfo();
    ComputerSystem cs = si.getHardware().getComputerSystem();
    Baseboard bb = cs.getBaseboard();
    Firmware fw = cs.getFirmware();
    GlobalMemory ram = si.getHardware().getMemory();
    CentralProcessor cp = si.getHardware().getProcessor();
    
    
    InetAddress ip;
    Timeline timeline;
    OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
    
    @FXML
    private ImageView btn_system, btn_cpu, btn_disk, btn_up, btn_exit, pic_logo, pic_cpu;
    @FXML
    private AnchorPane h_system, h_cpu, h_disk;
    @FXML
    private Label lbl_user, lbl_os, lbl_arch, lbl_cpname, lbl_baseb, lbl_firm, lbl_memory, lbl_ipaddr, lbl_macaddr, lbl_basebtype,
            lbl_cpuname, lbl_family, lbl_speed, lbl_cores, lbl_threads, lbl_id, lbl_usage;
    
    
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
    
    @FXML
    private void cpuAction(ActionEvent events){
        switch(timeline.getStatus())
        {
            case PAUSED:
            case STOPPED:
                timeline.play();
                break;
            case RUNNING:
                timeline.pause();
                break;
        }
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        systemcheck();
        lbl_cpuname.setText(cp.getName());
        Image intel = new Image("images/intel.png");
        Image amd = new Image("images/amd.png");
        if (lbl_cpuname.getText().contains("Intel")){
            pic_cpu.setImage(intel);
            pic_cpu.setFitWidth(60);
            pic_cpu.setFitHeight(60);
        }else{
            if(lbl_cpuname.getText().contains("Amd")){
                pic_cpu.setImage(amd);
                pic_cpu.setFitWidth(60);
                pic_cpu.setFitHeight(60);
            }
        }
        lbl_family.setText(cp.getFamily() + ". generáció");
        double freq = (cp.getVendorFreq()/1000000000.00);
        lbl_speed.setText("" + freq + " GHz");
        lbl_cores.setText("" + cp.getPhysicalProcessorCount());
        lbl_threads.setText("" + cp.getLogicalProcessorCount());
        lbl_id.setText(cp.getProcessorID());
        timeline = new Timeline(new KeyFrame(Duration.millis(10),(ActionEvent events) ->             
            lbl_usage.setText(getCPUUsage().toString())), 0);
        timeline.setCycleCount(Timeline.INDEFINITE);
        
        
        
        
        
    }
                
    
    public void systemcheck(){
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
        lbl_basebtype.setText(cs.getModel());
        double mem = (((ram.getTotal()/1024.0)/1024.0)/1024.0);
        String memm = "" + mem;
        
        if (memm.length() < 4){
            lbl_memory.setText("" + memm.substring(0, 3) + " GB"); 
        }else{
            if(memm.length() > 5){
                lbl_memory.setText("" + memm.substring(0, 5) + " GB");
            }
        }
        
        
        //lbl_memory.setText("" + memm.substring(0, 5) + " GB");
        
        
        
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
    public Double getCPUUsage() {

        double value = 0;
        for (Method method : operatingSystemMXBean.getClass().getDeclaredMethods()) {
            method.setAccessible(true);
            if (method.getName().startsWith("getSystemCpuLoad")
                    && Modifier.isPublic(method.getModifiers())) {
                try {
                     return (double) method.invoke(operatingSystemMXBean)*100;
                } catch (Exception e) {
                    value = 0;
                }
            }
        }
        return value;
    }
}
