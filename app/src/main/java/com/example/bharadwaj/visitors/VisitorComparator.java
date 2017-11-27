package com.example.bharadwaj.visitors;

import java.util.Comparator;

/**
 * Created by Bharadwaj on 11/26/17.
 */

public class VisitorComparator implements Comparator<Visitor> {

    @Override
    public int compare(Visitor visitor1, Visitor visitor2) {
        if(visitor1.getmArriveTime() < visitor2.getmArriveTime()){
            return -1;
        } else if(visitor1.getmArriveTime() == visitor2.getmArriveTime()){
            return 1;
        } else {
            return 1;
        }
    }

    /*@Override
    public boolean equals(Object visitor) {
        if (visitor instanceof Visitor && ((Visitor) visitor).getmArriveTime()==this.getmArriveTime()){
            return true;
        }
        return false;
    }*/
}
