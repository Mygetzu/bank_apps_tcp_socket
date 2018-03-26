package server;

import model.ListMenuMakan;
import model.MenuMakan;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MenuMakanServer extends Thread {
    private ServerSocket serverSocket;
    private ObjectInputStream ois   = null;
    private ObjectOutputStream oos  = null;
    private static List<MenuMakan> menuMakanList = new ArrayList<>();

    public static void main(String args[]) throws Exception {
        if (menuMakanList == null || menuMakanList.size() == 0) {
            for (int i = 0; i < 5; i++) {
                menuMakanList.add(new MenuMakan(
                        i + 1,
                        "Menu" + (i + 1),
                        10000 + (i * 1000),
                        20
                ));
            }
        }

        new MenuMakanServer();
    }

    public MenuMakanServer() throws Exception {
        serverSocket = new ServerSocket(10000);
        System.out.println("\nSocket server listening on port 10000...!");

        this.start();
    }

    public void run() {
        while (true) {
            try {
                System.out.println("Waiting for Connections...");

                Socket client = serverSocket.accept();
                System.out.println("\n\nConnection received from : " + client.getInetAddress() + "\n");

                Connect conn = new Connect(client, menuMakanList);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static List<MenuMakan> getMenuMakanList() {
        return menuMakanList;
    }

    public static void setMenuMakanList(List<MenuMakan> menuMakanList) {
        MenuMakanServer.menuMakanList = menuMakanList;
    }
}
