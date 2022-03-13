package com.tuxdave.erasmusapp.ws_segnalazioni;

import com.tuxdave.erasmusapp.ws_segnalazioni.entity.Segnalazione;
import lombok.val;

import java.util.ArrayList;
import java.util.List;

public class Utils<T> {
    public List<T> intersecaListe(List<T> l1, List<T> l2) {
        l1 = new ArrayList<T>(l1);
        l2 = new ArrayList<T>(l2);
        List<T> ret = new ArrayList<T>();
        int length = Math.min(l1.size(), l2.size());
        if(l1.size() != length){
            List<T> temp = l1;
            l1 = l2;
            l2 = temp;
        }
        for(int i = 0; i < length; i++){
            T s1 = l1.get(i);
            for(T j : l2){
                if(s1.equals(j)){
                    ret.add(s1);
                    l2.remove(j);
                    break;
                }
            }
        }
        return ret;
    }
}
