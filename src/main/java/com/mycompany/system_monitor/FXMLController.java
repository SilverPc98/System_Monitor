package com.mycompany.system_monitor;


import com.sun.jna.NativeLong;
import com.sun.jna.platform.unix.X11.Font;
import java.io.File;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import oshi.hardware.HWDiskStore;
import oshi.hardware.HWPartition;
import oshi.jna.platform.mac.DiskArbitration;
import oshi.util.platform.windows.WmiUtil;



public class FXMLController implements Initializable {
    SystemInfo si = new SystemInfo();
    ComputerSystem cs = si.getHardware().getComputerSystem();
    Baseboard bb = cs.getBaseboard();
    Firmware fw = cs.getFirmware();
    GlobalMemory ram = si.getHardware().getMemory();
    CentralProcessor cp = si.getHardware().getProcessor();
    Sensors sensor = si.getHardware().getSensors();
    
    
    InetAddress ip;
    Timeline timeline;
    Timeline timeline1;
    OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
    
    @FXML
    private ImageView btn_system, btn_cpu, btn_disk, btn_up, btn_exit, pic_logo, pic_cpu;
    @FXML
    private AnchorPane h_system, h_cpu, h_disk;
    @FXML
    private Label lbl_user, lbl_os, lbl_arch, lbl_cpname, lbl_baseb, lbl_firm, lbl_memory, lbl_ipaddr, lbl_macaddr, lbl_basebtype,
            lbl_cpuname, lbl_family, lbl_speed, lbl_cores, lbl_threads, lbl_id, lbl_usage, lbl_temp, lbl_voltage, lbl_fanspeed;
    
    
    @FXML
    private void handleButtonAction(MouseEvent event) {
        if (event.getTarget() == btn_system){
            h_system.setVisible(true);
            h_cpu.setVisible(false);
            h_disk.setVisible(false); 
            timeline1.play();
        }else{
            if (event.getTarget() == btn_cpu){
                h_cpu.setVisible(true);
                h_system.setVisible(false);
                h_disk.setVisible(false);
                if (cp.getName().contains("Intel")){
                timeline.play();
                }
                
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
        systemCheck();
        cpuInfo();
        hardDiskInfo();
/*VBox layout = new VBox(4);
        layout.setPadding(new Insets(20.0, 0.0, 10.0, 35.0));
        h_disk.getChildren().add(layout);
       File[] roots = File.listRoots(); 
       
        for (File root : roots) {
            
            float total = (((root.getTotalSpace()/1024)/1024)/1024);
            float free = (((root.getFreeSpace()/1024)/1024)/1024);
            float used = ((((root.getTotalSpace()/1024)/1024)/1024) - (((root.getFreeSpace()/1024)/1024)/1024));
            
            //particíó név hozzáadás
            Label lbl_main = new Label("Partíció: " + root.getAbsolutePath());
            lbl_main.setTextFill(Color.WHITE);
            lbl_main.setStyle("-fx-font-weight:bold;");
            layout.getChildren().add(lbl_main);
            //méretek hozzáadása
            Label lbl_total = new Label("Méret: " + total + " GB");
            lbl_total.setTextFill(Color.WHITE);
            lbl_total.setPadding(new Insets(0.0, 0.0, 0.0, 15.0));
            layout.getChildren().add(lbl_total);
            //szabad hely megjelenítése
            Label lbl_free = new Label("Szabad hely: " + free + " GB");
            lbl_free.setTextFill(Color.WHITE);
            lbl_free.setPadding(new Insets(0.0, 0.0, 0.0, 15.0));
            layout.getChildren().add(lbl_free);
            //A hsznált tárhely kiíratása
            Label lbl_used = new Label("Használatban: " + used + " GB");
            lbl_used.setTextFill(Color.WHITE);
            lbl_used.setPadding(new Insets(0.0, 0.0, 0.0, 15.0));
            layout.getChildren().add(lbl_used);
    
    
    }*/
   
  
  

      //System.out.println(disk.getModel());
     // System.out.println(disk.getPartitions());
      //System.out.println(disk.getSize());
 
      //HWPartition[] parts = disk.getPartitions();
        // for (HWPartition part : parts){
         
       //  }

        
        
    }
                
    public void systemCheck(){
       
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
            timeline1 = new Timeline(new KeyFrame(Duration.seconds(1), (ActionEvent event) ->{
            double umem = (((ram.getAvailable()/1024.0)/1024.0)/1024.0);
            String umemm = "" + (mem - umem);
            lbl_memory.setText("" + memm.substring(0, 3) + " GB" + " / " + umemm.substring(0, 3) + " GB"); 
            }));
            timeline1.setCycleCount(Timeline.INDEFINITE);
        }else{
            if(memm.length() > 5){
                timeline1 = new Timeline(new KeyFrame(Duration.seconds(1), (ActionEvent event) ->{
                double umem = (((ram.getAvailable()/1024.0)/1024.0)/1024.0);
                String umemm = "" + (mem - umem);
                lbl_memory.setText("" + memm.substring(0, 5) + " GB" + " / " + umemm.substring(0, 3) + " GB");
                }));
                timeline1.setCycleCount(Timeline.INDEFINITE);
            }
        }
        NetworkInterface network = null;
        try {
            network = NetworkInterface.getByInetAddress(ip);
        } catch (SocketException ex) { 
            System.out.println(ex);
        }
        byte[] mac = null;
        try {
            mac = network.getHardwareAddress();
        } catch (SocketException ex) {
            lbl_macaddr.setText("Nem található MAC cím");
        }
  
  
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mac.length; i++) {
            sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));     
        }
        lbl_macaddr.setText(sb.toString());
    }
    public void cpuInfo(){
    lbl_cpuname.setText(cp.getName());
    Image intel = new Image("images/intel.png");
        Image amd = new Image("images/amd.png");
        if (lbl_cpuname.getText().contains("Intel")){
            pic_cpu.setImage(intel);
            pic_cpu.setFitWidth(60);
            pic_cpu.setFitHeight(60);
        }else{
            if(lbl_cpuname.getText().contains("AMD")){
                pic_cpu.setImage(amd);
                pic_cpu.setFitWidth(60);
                pic_cpu.setFitHeight(60);
            }
        }
    if (lbl_cpuname.getText().contains("Intel")){
        lbl_family.setText(cp.getFamily() + ". generáció");
        double freq = (cp.getVendorFreq()/1000000000.00);
        lbl_speed.setText("" + freq + " GHz");
        
        lbl_cores.setText("" + cp.getPhysicalProcessorCount());
        lbl_threads.setText("" + cp.getLogicalProcessorCount());
        lbl_id.setText(cp.getProcessorID());
        timeline = new Timeline(new KeyFrame(Duration.millis(500), (ActionEvent event) ->{
            lbl_usage.setText(getCPUUsage().toString().substring(0, 4) + "%");
            lbl_temp.setText("" + sensor.getCpuTemperature() + " °C");
            lbl_voltage.setText(sensor.getCpuVoltage() + "V");
            int[] speeds = sensor.getFanSpeeds();
        for (int speed : speeds) {
            lbl_fanspeed.setText("" + speed + " RPM");
        }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
    }else{

        lbl_family.setText("Nem támogatott processzor!");
        lbl_speed.setText("Nem támogatott processzor!");
        lbl_cores.setText("Nem támogatott processzor!");
        lbl_threads.setText("Nem támogatott processzor!");
        lbl_id.setText("Nem támogatott processzor!");
        lbl_usage.setText("Nem támogatott processzor!");
        lbl_temp.setText("Nem támogatott processzor!");
        lbl_voltage.setText("Nem támogatott processzor!");
        lbl_fanspeed.setText("Nem támogatott processzor!");
    
    }
    }
    public void hardDiskInfo(){
        VBox layout = new VBox(5);
        layout.setPadding(new Insets(0.0, 0.0, 0.0, 0.0));
        layout.setAlignment(Pos.CENTER);
        layout.setMinWidth(380);
        h_disk.getChildren().add(layout);
        File[] roots = File.listRoots(); 
        Label title = new Label("Háttértárak");
        title.setTextFill(Color.WHITE);
        title.setStyle("-fx-font-weight:bold");
        title.setFont(new javafx.scene.text.Font("Arial", 15));
        layout.getChildren().add(title);
        Separator sep1 = new Separator();
        sep1.setMinWidth(100);
        sep1.setMaxWidth(100);
        layout.getChildren().add(sep1);
     for(HWDiskStore disk : si.getHardware().getDiskStores()){
         float total = ((disk.getSize()/1024)/1024)/1024;
         Label lbl_part = new Label(disk.getModel());
         lbl_part.setTextFill(Color.WHITE);
         lbl_part.setFont(new javafx.scene.text.Font("Arial", 12));
         layout.getChildren().add(lbl_part);
         Label lbl_size = new Label("Méret: " + total + " GB");
         lbl_size.setTextFill(Color.WHITE);
         lbl_size.setFont(new javafx.scene.text.Font("Arial", 12));
         layout.getChildren().add(lbl_size);
       }
     Label title2 = new Label("Partíciók");
     title2.setTextFill(Color.WHITE);
     title2.setStyle("-fx-font-weight:bold");
     title2.setFont(new javafx.scene.text.Font("Arial", 15));
     layout.getChildren().add(title2);
     Separator sep = new Separator();
     sep.setMinWidth(100);
     sep.setMaxWidth(100);
     layout.getChildren().add(sep);
     VBox lay = new VBox(4);
     lay.setMinWidth(380);
     lay.setPadding(new Insets(135.0, 0.0, 5.0, 0.0));
     lay.setAlignment(Pos.CENTER);
     h_disk.getChildren().add(lay);
     for (File root : roots) {
            
            float total = (((root.getTotalSpace()/1024)/1024)/1024);
            float free = (((root.getFreeSpace()/1024)/1024)/1024);
            float used = ((((root.getTotalSpace()/1024)/1024)/1024) - (((root.getFreeSpace()/1024)/1024)/1024));
            
            //particíó név hozzáadás
            Label lbl_main = new Label("Partíció: " + root.getAbsolutePath());
            lbl_main.setTextFill(Color.WHITE);
            lbl_main.setStyle("-fx-font-weight:bold;");
            lbl_main.setFont(new javafx.scene.text.Font("Arial", 12));
            lay.getChildren().add(lbl_main);
            //méretek hozzáadása
            Label lbl_total = new Label("Méret: " + total + " GB");
            lbl_total.setTextFill(Color.WHITE);
            lbl_total.setPadding(new Insets(0.0, 0.0, 0.0, 15.0));
            lbl_total.setFont(new javafx.scene.text.Font("Arial", 12));
            lay.getChildren().add(lbl_total);
            //szabad hely megjelenítése
            Label lbl_free = new Label("Szabad hely: " + free + " GB");
            lbl_free.setTextFill(Color.WHITE);
            lbl_free.setPadding(new Insets(0.0, 0.0, 0.0, 15.0));
            lbl_free.setFont(new javafx.scene.text.Font("Arial", 12));
            lay.getChildren().add(lbl_free);
            //A hsznált tárhely kiíratása
            Label lbl_used = new Label("Használatban: " + used + " GB");
            lbl_used.setTextFill(Color.WHITE);
            lbl_used.setPadding(new Insets(0.0, 0.0, 0.0, 15.0));
            lbl_used.setFont(new javafx.scene.text.Font("Arial", 12));
            lay.getChildren().add(lbl_used);
    }
     
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
                    System.out.println(e);
                    value = 0;
                }
            }
        }
        return value;
    }

}
