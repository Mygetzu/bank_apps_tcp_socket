package client;

import model.ListMenuMakan;
import model.ListOrderMenu;
import model.MenuMakan;
import model.OrderMenu;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class CustomerClient {
    private static InetAddress      host;
    private static final int        PORT  = 10000;
    private static BufferedReader   in;
    private static PrintWriter      out;
    private static BufferedReader   keyboard;

    public static void main(String args[]) throws Exception {
        ObjectOutputStream oos  = null;
        ObjectInputStream ois   = null;
        Socket socket           = null;

        String answer;
        int orderQty, orderID;

        try {
            host    = InetAddress.getLocalHost();
//            host    = InetAddress.getByName("10.252.133.66");
            socket = new Socket(host, PORT);

            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());

            keyboard    = new BufferedReader(new InputStreamReader(System.in));

            ListMenuMakan listMenuMakan = null;
            List<MenuMakan> menuMakans  = new ArrayList<>();

            listMenuMakan   = (ListMenuMakan) ois.readObject();
            menuMakans      = listMenuMakan.getMenuMakanList();

            System.out.println(
                "\nMenu :\nID. \tMenu \tHarga \t\tStock\n"
            );
            for (int i = 0; i < menuMakans.size(); i++) {
                System.out.println(
                    (i + 1) + ". \t\t" +
                    menuMakans.get(i).getNama_menu() + " \t" +
                    menuMakans.get(i).getHarga() + " \t" +
                    menuMakans.get(i).getStock()
                );
            }

            ListOrderMenu orderMenu = new ListOrderMenu();

            do {
                System.out.print("\nPilih Menu (ID): ");
                orderID = Integer.parseInt(keyboard.readLine());

                System.out.print("Beli berapa ?  : ");
                orderQty= Integer.parseInt(keyboard.readLine());

                if (orderQty > menuMakans.get(orderID - 1).getStock())
                    System.out.println("Stock tidak cukup !");
                else
                    orderMenu.addListOrderMenu(new OrderMenu(orderID, orderQty));

                System.out.print("Mau pilih menu lagi ? : ");
                answer  = keyboard.readLine();
            } while (answer.equals("Y") || answer.equals("y"));

            System.out.println(
                    "\n#################### Nota ######################" +
                    "\nID. \tMenu \tHarga \t\tQTY \t SubTotal\n"
            );
            double total = 0;
            for (int i = 0; i < orderMenu.getOrderMenuList().size(); i++) {
                System.out.println(
                        menuMakans.get(orderMenu.getOrderMenuList().get(i).getId() - 1).getId() + ". \t\t" +
                        menuMakans.get(orderMenu.getOrderMenuList().get(i).getId() - 1).getNama_menu() + " \t" +
                        menuMakans.get(orderMenu.getOrderMenuList().get(i).getId() - 1).getHarga() + " \t" +
                        orderMenu.getOrderMenuList().get(i).getQty() + " \t\tRp. " +
                        menuMakans.get(orderMenu.getOrderMenuList().get(i).getId() - 1).getHarga() *
                        orderMenu.getOrderMenuList().get(i).getQty()
                );
            }

            oos.writeObject(orderMenu);
            oos.writeObject(listMenuMakan);
            oos.flush();

            listMenuMakan   = (ListMenuMakan) ois.readObject();
            total           = (double) ois.readObject();

            System.out.println("\nTotal :\t\t\t\t\t\t\t\tRp. " + total);
            System.out.println(
                    "################################################\n"
            );

            oos.close();
            ois.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
