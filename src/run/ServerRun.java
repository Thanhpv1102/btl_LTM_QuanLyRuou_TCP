/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package run;

import controller.ServerCtr;
import java.io.IOException;

/**
 *
 * @author phamv
 */
public class ServerRun {
    public static void main(String[] args) throws IOException {
        System.out.println("Server is running...");
        new ServerCtr();
    }
}
