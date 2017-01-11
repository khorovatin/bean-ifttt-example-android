package com.swstack.iftttconnector;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    private class HttpRequest extends AsyncTask<URL, Integer, Long> {

        private void makeRequest(URL url) throws IOException {
            System.out.println("Sending request");
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            System.out.println("Connection established");

            try {
                conn.setDoOutput(true);
                conn.setChunkedStreamingMode(0);

                InputStream in = new BufferedInputStream(conn.getInputStream());
                System.out.print("Reading response");
                System.out.print(in.read());

            } finally {
                conn.disconnect();
            }
        }

        protected Long doInBackground(URL... urls) {
            int count = urls.length;
            long totalSize = 0;

            try {
                makeRequest(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return totalSize;
        }

        protected void onProgressUpdate(Integer... progress) {
        }

        protected void onPostExecute(Long result) {
            System.out.println("DONE!!!!!");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button = (Button) findViewById(R.id.ifttt_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                try {
                    URL url = new URL("https://maker.ifttt.com/trigger/bean_trigger/with/key/bJfifNaGnmCOsvpqkJTAHq");
                    new HttpRequest().execute(url);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }


            }
        });
    }

}
