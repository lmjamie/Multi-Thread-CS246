package edu.byui.landon.multi_thread;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class OpenFile extends AppCompatActivity {

    ArrayAdapter<Integer> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_file);
    }

    public void createFile(View v) {
        try (FileWriter fout = new FileWriter(new File(getFilesDir(), "number.txt"))) {
            for (int i = 1; i <= 10 ; ++i){
                fout.write(i + System.getProperty("line.separator"));
                Thread.sleep(250);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (InterruptedException e) {
            System.out.println("This won't happen");
        }
        //System.out.println("You clicked Create!");
    }

    public void loadFile(View v) {
        try (Scanner fin = new Scanner(new File(getFilesDir(), "number.txt"))) {
            ArrayList<Integer> list = new ArrayList<>();
            System.out.println(fin.hasNextInt());
            while (fin.hasNextInt()) {
                list.add(fin.nextInt());
                Thread.sleep(250);
            }
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
            ListView myLView = (ListView) findViewById(R.id.listView);
            myLView.setAdapter(adapter);
        }
        catch (FileNotFoundException e) {
            System.out.println("Could not find the file number.txt!");
        }
        catch (InterruptedException e) {
            System.out.println("This won't happen");
        }
        //System.out.println("You clicked Load!");
       }

    public void clearFile(View v) {
        adapter.clear();
        System.out.println("You clicked Clear!");
    }
}
