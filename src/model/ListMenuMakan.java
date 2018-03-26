package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ListMenuMakan implements Serializable {
    private List<MenuMakan> menuMakanList = new ArrayList<>();

    public ListMenuMakan(){}

    public List<MenuMakan> getMenuMakanList() {
        return menuMakanList;
    }

    public void setMenuMakanList(List<MenuMakan> menuMakanList) {
        this.menuMakanList = menuMakanList;
    }

    public void addMenuMakan(MenuMakan menuMakan) {
        menuMakanList.add(menuMakan);
    }
}
