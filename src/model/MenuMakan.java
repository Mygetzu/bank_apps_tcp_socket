package model;

import java.io.Serializable;

public class MenuMakan implements Serializable{
    private int id;
    private String nama_menu;
    private double harga;
    private int stock;

    public MenuMakan(int id, String nama_menu, double harga, int stock) {
        this.id = id;
        this.nama_menu = nama_menu;
        this.harga = harga;
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama_menu() {
        return nama_menu;
    }

    public void setNama_menu(String nama_menu) {
        this.nama_menu = nama_menu;
    }

    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
