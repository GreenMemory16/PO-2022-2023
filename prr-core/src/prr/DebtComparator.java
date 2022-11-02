package prr;
import java.util.Comparator;

import prr.clients.Client;

class DebtComparator implements Comparator<Client> {
  
    // override the compare() method
    @Override
    //-1 if less; 0 equals; 1 if greater than
    public int compare(Client client1, Client client2){
        if(client1.Debts() > client2.Debts()){
            return -1;
        }
        if(client1.Debts() == client2.Debts()){
            if(client1.getKey().compareTo(client2.getKey()) < 0){
                //retorna 1 se maior
                //retorna 0 se igual
                //retorna -1 se menor
                return -1;
            }
            return 1;
        }
        else{
            return 1;
        }
    }
}
