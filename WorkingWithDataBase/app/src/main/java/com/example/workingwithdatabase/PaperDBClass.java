package com.example.workingwithdatabase;

import android.content.Context;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

import io.paperdb.Paper;

public class PaperDBClass {

    public ArrayList<Serial> getSerial(){
        return Paper.book("Serials").read("Serials", new ArrayList<Serial>());
    }

    public void saveSerialList(ArrayList<Serial> serials){
        Paper.book("Serials").write("Serials", serials);
    }

    public void addSerial(Serial serial, Context context){
        ArrayList<Serial> serials = getSerial();

        for(int i = 0; i < serials.size(); i++){
            if(serials.get(i).getSerial_Name().equals(serial.getSerial_Name())){
                Toast.makeText(context, "Такой сериал уже существует", Toast.LENGTH_LONG).show();
                return;
            }
        }
        serials.add(serial);
        saveSerialList(serials);
    }

    public void deleteSerial(Serial serial){
        ArrayList<Serial> serials = getSerial();

        for(int i = 0; i < serials.size(); i++){
            if(serials.get(i).getSerial_Name().equals(serial.getSerial_Name())){
                serials.remove(i);
                saveSerialList(serials);
                return;
            }
        }
    }

    public void updateSerial(Serial serial, String tempName){
        ArrayList<Serial> serials = getSerial();
        ArrayList<Serial> tempList = new ArrayList<Serial>();
        for(int i = 0; i < serials.size(); i++){
            if(serials.get(i).getSerial_Name().equals(tempName)){
                tempList.add(serial);
            }
            else{
                tempList.add(serials.get(i));
            }
        }
        saveSerialList(tempList);
    }
}
