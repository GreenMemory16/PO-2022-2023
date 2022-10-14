package prr.clients;

import java.io.Serializable;

import prr.NetworkManager;

public class Client implements Serializable{

    private String _key;
    private String _name;
    private int _taxId;

    public Client(String key, String name, int taxId) {
        _key = key;
        _name = name;
        _taxId = taxId;
    }

    public String getKey() { return _key; }
    public String getName() { return _name; }
    public int getTaxId() { return _taxId; }

    @Override
    public String toString() {
        return "CLIENT|" + getKey() + "|" + getName() + "|" + getTaxId();
    }

}
