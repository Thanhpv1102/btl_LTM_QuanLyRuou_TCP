/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import model.Nuocsx;
import model.Ruou;

/**
 *
 * @author phamv
 */
public class ClientCtr {
    private int port;
    private String host;
    private Socket mySocket;

    public ClientCtr() {
        host = "localhost";
        port = 8888;
    }
    
    public void openSocket(){
        try {
            mySocket = new Socket(host, port);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void sendNuocsx(Nuocsx n){
        try {
            ObjectOutputStream oos = new ObjectOutputStream(mySocket.getOutputStream());
            oos.writeObject(n); 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void sendRuou(Ruou r){
        try {
            ObjectOutputStream oos = new ObjectOutputStream(mySocket.getOutputStream());
            oos.writeObject(r); 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void sendCode(String code) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(mySocket.getOutputStream());
            oos.writeObject(code); 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getResult(){
        String res = "";
        try {
            ObjectInputStream ois = new ObjectInputStream(mySocket.getInputStream());
            res = (String)ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return res;
    }
    
    public List<Nuocsx> getListNSX(){
        List<Nuocsx> listNSX = null;
        try {
            ObjectInputStream ois = new ObjectInputStream(mySocket.getInputStream());
            listNSX = (List<Nuocsx>) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listNSX;
    }
    
    public List<Ruou> getListRuou(){
        List<Ruou> listR = null;
        try {
            ObjectInputStream ois = new ObjectInputStream(mySocket.getInputStream());
            listR = (List<Ruou>) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listR;
    }
    
    public List<String> getItem(){
        List<String> listItem = null;
        try {
            ObjectInputStream ois = new ObjectInputStream(mySocket.getInputStream());
            listItem = (List<String>) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listItem;
    }
    
    public void closeConnection(){
        try {
            mySocket.close();
        }catch (Exception e){
        }
    }
}
