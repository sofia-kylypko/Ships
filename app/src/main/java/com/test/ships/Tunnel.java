package com.test.ships;

import android.util.Log;

import com.test.ships.ships.Ship;
import com.test.ships.ships.types.Type;

import java.util.ArrayList;
import java.util.List;

public class Tunnel {
    private static final int maxShipsInTunnel =5;
    private static final int minShipsInTunnel =0;
    private int shipsCounter=0;
    private List<Ship> store;

    public Tunnel(){
        store=new ArrayList<>();
    }

    public synchronized boolean add(Ship element){
        try{
            if(shipsCounter<maxShipsInTunnel){
                notifyAll();

                store.add(element);

                Log.d("ADD SHIP", " Store size:"+store.size()+" Type ship:"+element.getType()+" Size ship:"+element.getSize()+" Current Thread:"+Thread.currentThread().getName());

                shipsCounter++;
            }else{
                Log.d("ADD SHIP", " Store size:"+store.size()+" Current Thread:"+Thread.currentThread().getName());
                wait();
                return false;
            }
        }catch(InterruptedException e){
            e.printStackTrace();
        }


        return true;
    }

    public synchronized Ship get(Type shipType){
        try{
            if(shipsCounter>minShipsInTunnel){
                notifyAll();
                for(Ship ship: store){
                    if (ship.getType()==shipType){
                        shipsCounter--;
                        store.remove(ship);
                        return ship;
                    }
                }
            }
            wait();
        }catch(InterruptedException e){
            e.printStackTrace();
        }

        return null;
    }
}
