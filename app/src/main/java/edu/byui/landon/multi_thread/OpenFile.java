package edu.byui.landon.multi_thread;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

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

    private class CreateFileTask extends AsyncTask<String, Integer, Void> {
        @Override
        protected void onPreExecute() {
            ((ProgressBar) findViewById(R.id.progressBar)).setProgress(0);
        }

        @Override
        protected Void doInBackground(String... params) {
            try (FileWriter fout = new FileWriter(params[0])) {
                for (int i = 1; i <= 10 ; ++i){
                    fout.write(i + System.getProperty("line.separator"));
                    Thread.sleep(250);
                    publishProgress(i);

                }
            }
            catch (IOException e) {

                e.printStackTrace();
            }
            catch (InterruptedException e) {
                System.out.println("This won't happen");
            }
            return null;
        }

        protected void onProgressUpdate(Integer... progress) {
            ((ProgressBar) findViewById(R.id.progressBar)).setProgress(10 * progress[0]);
        }
    }

    public void createClick(View v) { new CreateFileTask().execute(getFilesDir() + "/number.txt"); }

    private class LoadFileTask extends AsyncTask<String, Integer, Void> {
        ArrayList<Integer> list = new ArrayList<>();

        @Override
        protected void onPreExecute() { ((ProgressBar) findViewById(R.id.progressBar)).setProgress(0); }

        @Override
        protected Void doInBackground(String... params) {
            try (Scanner fin = new Scanner(new File(params[0]))) {
                System.out.println(fin.hasNextInt());
                while (fin.hasNextInt()) {
                    list.add(fin.nextInt());
                    Thread.sleep(250);
                    publishProgress(10);
                }
            }
            catch (FileNotFoundException e) {
                System.out.println("Could not find the file number.txt!");
            }
            catch (InterruptedException e) {
                System.out.println("This won't happen");
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            ProgressBar pBar = ((ProgressBar) findViewById(R.id.progressBar));
            pBar.setProgress(pBar.getProgress() + values[0]);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            adapter = new ArrayAdapter<>(OpenFile.this, android.R.layout.simple_list_item_1, list);
            ListView myLView = (ListView) findViewById(R.id.listView);
            myLView.setAdapter(adapter);
        }
    }
    public void loadClick(View v) { new LoadFileTask().execute(getFilesDir() + "/number.txt"); }

    public void clearClick(View v) {
        adapter.clear();
        ((ProgressBar) findViewById(R.id.progressBar)).setProgress(0);
    }
}
