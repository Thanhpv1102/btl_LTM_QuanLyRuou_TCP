/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import model.Nuocsx;
import model.Ruou;
import view.ServerView;

/**
 *
 * @author phamv
 */
public class ServerCtr {

    private int port;
    private String host;
    private ServerDAO dao;
    private ServerSocket myServer;
    private ArrayList<Socket> list;

    public ServerCtr() throws IOException {
        port = 8888;
        host = "localhost";
        dao = new ServerDAO();
        list = new ArrayList<>();
        openSocket();
        while (true) {
            try {
                Socket s = myServer.accept();
                list.add(s);
                ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
                String code = (String) ois.readObject();
                if(code.equals("1")){
                    executeNuocsx(s);
                }else if(code.equals("2")){
                    executeRuou(s);
                }else {
                    new ServerView().showMessage("Unsupported action: " + code);
                }
                    
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void sendResult(String res) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(list.get(list.size() - 1).getOutputStream());
            oos.writeObject(res);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void sendListItem(ArrayList<String> listItem) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(list.get(list.size() - 1).getOutputStream());
            oos.writeObject(listItem);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendListNSX(ArrayList<Nuocsx> listNSX) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(list.get(list.size() - 1).getOutputStream());
            oos.writeObject(listNSX);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendListRuou(ArrayList<Ruou> listRuou) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(list.get(list.size() - 1).getOutputStream());
            oos.writeObject(listRuou);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void executeNuocsx(Socket s) {
        Nuocsx n = receiveNuocsx(s);
        try {
            if (n.getAction().equals("addN")) {
                if (dao.addNuocsx(n)) {
                    sendResult("ok");
                    new ServerView().showMessage("Add Success!");
                } else {
                    sendResult("failed");
                    new ServerView().showMessage("Add Failed!");
                }
            } else if (n.getAction().equals("viewallN")) {
                ArrayList<Nuocsx> listNSX = dao.getAllNuocsx();
                sendListNSX(listNSX);
                new ServerView().showMessage("Send all NuocSX ok!");
            } else if (n.getAction().equals("updateN")) {
                if (dao.updateNuocsx(n)) {
                    sendResult("ok");
                    new ServerView().showMessage("Update Success!");
                } else {
                    sendResult("failed");
                    new ServerView().showMessage("Update Failed!");
                }
            } else if (n.getAction().equals("deleteN")) {
                if (dao.deleteNuocsx(n)) {
                    sendResult("ok");
                    new ServerView().showMessage("Delete Success!");
                } else {
                    sendResult("failed");
                    new ServerView().showMessage("Delete Failed!");
                }
            } else if (n.getAction().equals("searchN")) {
                ArrayList<Nuocsx> listByName = dao.searchNuocsxByName(n);
                sendListNSX(listByName);
                new ServerView().showMessage("Search by name NuocSX ok!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void executeRuou(Socket s){
        Ruou r = receiveRuou(s);
        try {
            if (r.getAction().equals("addR")) {
                if (dao.addRuou(r)) {
                    sendResult("ok");
                    new ServerView().showMessage("Add Success!");
                } else {
                    sendResult("failed");
                    new ServerView().showMessage("Add Failed!");
                }
            } else if(r.getAction().equals("getItem")){
                ArrayList<String> listItem = dao.getItemNSX(r);
                sendListItem(listItem);
                new ServerView().showMessage("send item ok!");
            }else if (r.getAction().equals("viewallR")) {
                ArrayList<Ruou> listR = dao.getAllRuou();
                sendListRuou(listR);
                new ServerView().showMessage("Send all Ruou ok!");
            }else if (r.getAction().equals("updateR")) {
                if (dao.updateRuou(r)) {
                    sendResult("ok");
                    new ServerView().showMessage("Update Success!");
                } else {
                    sendResult("failed");
                    new ServerView().showMessage("Update Failed!");
                }
            } else if (r.getAction().equals("deleteR")) {
                if (dao.deleteRuou(r)) {
                    sendResult("ok");
                    new ServerView().showMessage("Delete Success!");
                } else {
                    sendResult("failed");
                    new ServerView().showMessage("Delete Failed!");
                }
            } else if (r.getAction().equals("searchR")) {
                ArrayList<Ruou> listSearch = dao.searchRuou(r);
                sendListRuou(listSearch);
                new ServerView().showMessage("Search Ruou ok!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void openSocket() {
        try {
            myServer = new ServerSocket(port);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Nuocsx receiveNuocsx(Socket s) {
        Nuocsx n = null;
        try {
            ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
            n = (Nuocsx) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return n;
    }

    public Ruou receiveRuou(Socket s) {
        Ruou r = null;
        try {
            ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
            r = (Ruou) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return r;
    }
    
}
