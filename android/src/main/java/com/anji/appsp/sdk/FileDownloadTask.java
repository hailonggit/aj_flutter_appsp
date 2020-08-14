package com.anji.appsp.sdk;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Json file downloader
 *
 * @author hailong
 * Create Date:2020.7.2
 */
public class FileDownloadTask extends AsyncTask<String, Void, String> {
    private String url;
    private SpHandler spHandler;

    public FileDownloadTask(String url, SpHandler spHandler) {
        this.url = url;
        this.spHandler = spHandler;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... strings) {
        //1，Download json file
        //2，Get content
        String content = getServerContent();
        SpLog.d("doInBackground content is " + content);
        return content;
    }

    @Override
    protected void onPostExecute(String jsonContent) {
        SpLog.d("onPostExecute  spHandler   is " + spHandler);
        if (spHandler != null) {
            //download success
            SpLog.d("Download json success and content is " + jsonContent);
            if (jsonContent != null && jsonContent.length() > 0) {
                spHandler.setJsonContent(jsonContent);
                spHandler.removeMessages(SpConstant.Msg_FileDownload_Success);
                spHandler.sendEmptyMessage(SpConstant.Msg_FileDownload_Success);
            }
        }
        super.onPostExecute(jsonContent);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        SpLog.e("Download json canceled ");
        if (spHandler != null) {
            spHandler.removeMessages(SpConstant.Msg_FileDownload_Cancel);
            spHandler.sendEmptyMessage(SpConstant.Msg_FileDownload_Cancel);
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    private String getServerContent() {
        String urlStr = url;
        SpLog.d("getServerContent url  is " + urlStr);
        InputStream input = null;
        StringBuffer sb = new StringBuffer();
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5 * 1000);
            conn.setReadTimeout(5 * 1000);
            input = conn.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(input));
            String line = null;
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
            SpLog.e("MalformedURLException error  " + e.toString());
            spHandler.removeMessages(SpConstant.Msg_FileDownload_UrlFormatError);
            spHandler.sendEmptyMessage(SpConstant.Msg_FileDownload_UrlFormatError);
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            SpLog.e("IOException error  " + e.toString());
            spHandler.removeMessages(SpConstant.Msg_FileDownload_Timeout);
            spHandler.sendEmptyMessage(SpConstant.Msg_FileDownload_Timeout);
            return null;
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (Exception e) {
                return null;
            }

        }
        SpLog.d("getServerContent str  is " + sb.toString());
        return sb.toString();
    }

}
