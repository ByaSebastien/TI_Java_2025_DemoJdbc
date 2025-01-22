package be.bstorm;

import be.bstorm.entities.Book;

import java.util.ArrayList;
import java.util.List;

public class RappelGenerique<Truc> {

    private List<Truc> trucs = new ArrayList<>();

    public List<Truc> getTrucs() {
        return trucs;
    }

    public void add(Truc truc){
        trucs.add(truc);
    }

    public void remove(Truc truc){
        trucs.remove(truc);
    }
}
