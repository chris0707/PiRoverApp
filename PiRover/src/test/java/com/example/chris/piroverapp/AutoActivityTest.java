package com.example.chris.piroverapp;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Chris on 2018-01-15.
 */
public class AutoActivityTest {

    private AutoActivity autoActivity =  new AutoActivity();

    @Test
    public void sendMsg() throws Exception {
        try {
            autoActivity.outputStream.write("Stop".getBytes());
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

}