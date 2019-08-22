package com.mycompany.system_monitor;

import java.awt.Image;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventTarget;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import oshi.SystemInfo;
import oshi.hardware.Baseboard;
import oshi.hardware.ComputerSystem;
import org.slf4j.Logger;
import oshi.hardware.CentralProcessor;
import oshi.hardware.Sensors;

public class FXMLController implements Initializable {
    
    @FXML
    private ImageView btn_system, btn_cpu, btn_disk, btn_up, btn_exit;
    @FXML
    private AnchorPane h_system, h_cpu, h_disk;
    
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
        // TODO
        System.out.println("You clicked me!");
        SystemInfo si = new SystemInfo();
        ComputerSystem cs = si.getHardware().getComputerSystem();
        Baseboard bb = cs.getBaseboard();
        CentralProcessor cp = si.getHardware().getProcessor();
        Sensors sensor = si.getHardware().getSensors();
        System.out.println();

        
    }    
}
