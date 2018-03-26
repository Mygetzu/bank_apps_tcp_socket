package server;

import model.ListMenuMakan;
import model.ListOrderMenu;
import model.MenuMakan;
import model.OrderMenu;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Connect extends Thread {
    private Socket client           = null;
    private ObjectInputStream ois   = null;
    private ObjectOutputStream oos  = null;
    ListMenuMakan listMenuMakan     = null;

    public Connect(){}

    public Connect(Socket clientSocket, List<MenuMakan> itemMenu) {
        client = clientSocket;

        try {
            oos = new ObjectOutputStream(client.getOutputStream());
            ois = new ObjectInputStream(client.getInputStream());

            if (listMenuMakan == null) {
                listMenuMakan = new ListMenuMakan();
                listMenuMakan.setMenuMakanList(itemMenu);
                oos.writeObject(listMenuMakan);
                oos.flush();
            }
        } catch (Exception e) {
            try {
                client.close();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }

        this.start();
    }

    public void run() {
        ListMenuMakan listMenuMakan = null;
        ListOrderMenu listOrderMenu = null;
        List<OrderMenu> itemOrder   = new ArrayList<>();

        try {
            listOrderMenu   = (ListOrderMenu) ois.readObject();
            listMenuMakan   = (ListMenuMakan) ois.readObject();

            System.out.println("Transaction from : " + client.getInetAddress());
            double total = 0;
            for (int i = 0; i < listOrderMenu.getOrderMenuList().size(); i++) {
                listMenuMakan.getMenuMakanList().get(listOrderMenu.getOrderMenuList().get(i).getId() - 1).setStock(
                        listMenuMakan.getMenuMakanList().get(listOrderMenu.getOrderMenuList().get(i).getId() - 1).getStock()
                        - listOrderMenu.getOrderMenuList().get(i).getQty()
                );

                total += listMenuMakan.getMenuMakanList().get(listOrderMenu.getOrderMenuList().get(i).getId() - 1).getHarga() *
                        listOrderMenu.getOrderMenuList().get(i).getQty();
            }
            System.out.println("Total : Rp. " + total);

            MenuMakanServer.setMenuMakanList(listMenuMakan.getMenuMakanList());

            oos.writeObject(listMenuMakan);
            oos.writeObject(total);
            oos.flush();

            System.out.println(
                    "\nMenu :\nID. \tMenu \tHarga \t\tStock\n"
            );
            for (int i = 0; i < listMenuMakan.getMenuMakanList().size(); i++) {
                System.out.println(
                        (i + 1) + ". \t\t" +
                        listMenuMakan.getMenuMakanList().get(i).getNama_menu() + " \t" +
                        listMenuMakan.getMenuMakanList().get(i).getHarga() + " \t" +
                        listMenuMakan.getMenuMakanList().get(i).getStock()
                );
            }

            oos.close();
            ois.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
