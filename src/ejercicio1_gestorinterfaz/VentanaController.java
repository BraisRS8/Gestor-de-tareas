/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejercicio1_gestorinterfaz;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * 
 */
public class VentanaController implements Initializable {

    @FXML
    private ListView<?> listTasks;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnModify;
    @FXML
    private Button btnCreate;
    @FXML
    private TextField txtTask;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        listTask(); // Update the listView with the tasks in the file at the start of th application
    }    

    /**
     * Method that takes the task that the user selected in the listView and deletes it from the file.
     * @param event Delete button pressed
     */
    @FXML
    private void deleteTask(ActionEvent event) {
        
        File inputFile = new File("F:\\Users\\Equipo\\Desktop\\Tasks.txt");
        File tempFile = new File("F:\\Users\\Equipo\\Desktop\\TempTasks.txt");
            
        String strCurrentLine;
        
        
        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
            
            
            String lineToRemove = (String) listTasks.getSelectionModel().getSelectedItem();
            
            while((strCurrentLine = reader.readLine()) != null) {
 
                if(strCurrentLine.equalsIgnoreCase(lineToRemove)){
                    continue;                      
                }
                
                writer.write(strCurrentLine+"\n");    
                
            }
            
            writer.close();
            reader.close();
            
            inputFile.delete();   
            tempFile.renameTo(inputFile);
            txtTask.setText("");
            listTask();
             
            
        }   
        catch (FileNotFoundException ex) {
            Logger.getLogger(VentanaController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(VentanaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Method that takes what the user wrote on the fieldText and modifies the task that was selected in the listView,
     * checking first if the new task was already in the file.
     * @param event Modify button pressed
     */
    @FXML
    private void modifyTask(ActionEvent event) {
        
        String strCurrentLine;
        File inputFile = new File("F:\\Users\\Equipo\\Desktop\\Tasks.txt");
        File tempFile = new File("F:\\Users\\Equipo\\Desktop\\TempTasks.txt");
        
        
        
        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
            BufferedReader reader2 = new BufferedReader(new FileReader(inputFile));
            Boolean same = false; 
            
            String lineToModify = (String) listTasks.getSelectionModel().getSelectedItem();
            
            while((strCurrentLine = reader.readLine()) != null) {
                
                if(strCurrentLine.equalsIgnoreCase(lineToModify)){
                    String task = txtTask.getText();
                    
                    while((strCurrentLine = reader2.readLine()) != null){
                        if(strCurrentLine.equalsIgnoreCase(task)){
                            same = true;
                        }
                    }
                    
                    if(same == false){
                        writer.write(task+"\n");
                        
                    }else{
                        writer.write(lineToModify+"\n");
                    }
                    
                    continue;
                }
                
                writer.write(strCurrentLine+"\n");    
                
            }
            
            writer.close();
            reader.close();
            reader2.close();
            
            inputFile.delete();   
            tempFile.renameTo(inputFile);
            txtTask.setText("");
            listTask();
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(VentanaController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(VentanaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Method that takes the text from the fieldText and adds it to the file, first checking if
     * the task was already in the file.
     * @param event Create button pressed
     */
    @FXML
    private void createTask(ActionEvent event) {
        BufferedWriter writer = null;
        String strCurrentLine;
        File inputFile = new File("F:\\Users\\Equipo\\Desktop\\Tasks.txt");
        Boolean same = false;
        
        try {
            
            writer = new BufferedWriter(new FileWriter(inputFile, true));
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            
            String task = txtTask.getText();
            
            while((strCurrentLine = reader.readLine()) != null) {
    
                if(strCurrentLine.equalsIgnoreCase(task)){
                    same = true;
                    
                }
            }
            if(same == false){
                writer.write(task+"\n");
            }
            
            writer.close();
            reader.close();
            txtTask.setText("");
            listTask();
            
        } catch (IOException ex) {
            Logger.getLogger(VentanaController.class.getName()).log(Level.SEVERE, null, ex);
        }                           
    }
    
    /**
     * Method that takes the text from the selected task and puts it in the fieldText. 
     * @param event Click on a task from the listView
     */
    @FXML
    private void selectTask(MouseEvent event) {
        String task = (String) listTasks.getSelectionModel().getSelectedItem();
        
        if(task != null){
            txtTask.setText(task);
        }
        
    }
    
    /**
     * Method that puts the tasks from the file to an ObservableList and then puts it in the listView. 
     */
    private void listTask(){
        
        ObservableList task = FXCollections.observableArrayList();
        BufferedReader br = null;
        
        try {
            
            br = new BufferedReader(new FileReader("F:\\Users\\Equipo\\Desktop\\Tasks.txt"));
            String strCurrentLine;

            while ((strCurrentLine = br.readLine()) != null) {
                
                task.add(strCurrentLine);
                
            }
                       
            br.close();
            listTasks.setItems(task);
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(VentanaController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(VentanaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }

    
}
