package com.tastyhomemade.tastyhomemade.Others;

import android.graphics.Bitmap;

import android.graphics.BitmapFactory;
import android.util.Log;

import com.tastyhomemade.tastyhomemade.Business.ConnectionProperties;

import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Raed on 12/26/2016.
 */

public class FileUploader implements Runnable {

    URL connectURL;
    String responseString;
    String Title;
    String Description;
    byte[ ] dataToServer;

    public FileUploader(){
        try{
            connectURL = new URL(ConnectionProperties.SiteUrl+"/ReceiveFile.aspx");
            Title= "test";
            Description = "test";
        }catch(Exception ex){
            Log.i("HttpFileUpload","URL Malformatted");
        }
    }

    public String SaveImage(InputStream fs)
    {
        try {
            byte[] Image = new byte[fs.available()];
            fs.read(Image,0, fs.available());
            return SaveImage (Image);
        }
        catch (Exception ex)
        {

        }
        return "";
    }


    private  String SaveImage (byte[] p_Image)
    {

        try {
            String sFileName = java.util.UUID.randomUUID().toString().replace("-", "") + ".jpg";
//            String sFullFileName = ConnectionProperties.SiteUrl + "/Images/" + sFileName;
//            String sFolderName = ConnectionProperties.SiteUrl + "/Images";


            String lineEnd = "\r\n";
            String twoHyphens = "--";
            String boundary = "*****";
            String Tag="fSnd";
            try
            {
                Log.e(Tag,"Starting Http File Sending to URL");
                URL connectURL = new URL("http://www.tastyhomemade.net"+ "/ReceiveFile.aspx");
                // Open a HTTP connection to the URL
                HttpURLConnection conn = (HttpURLConnection)connectURL.openConnection();

                // Allow Inputs
                conn.setDoInput(true);

                // Allow Outputs
                conn.setDoOutput(true);

                // Don't use a cached copy.
                conn.setUseCaches(false);

                // Use a post method.
                conn.setRequestMethod("POST");

                conn.setRequestProperty("Connection", "Keep-Alive");

                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary="+boundary);

                DataOutputStream dos = new DataOutputStream(conn.getOutputStream());

                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"title\""+ lineEnd);
                dos.writeBytes(lineEnd);
                dos.writeBytes("test");
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + lineEnd);

                dos.writeBytes("Content-Disposition: form-data; name=\"description\""+ lineEnd);
                dos.writeBytes(lineEnd);
                dos.writeBytes("description");
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + lineEnd);

                dos.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\"" + sFileName +"\"" + lineEnd);
                dos.writeBytes(lineEnd);

                Log.e(Tag,"Headers are written");

                // create a buffer of maximum size
                int bytesAvailable = p_Image.length;

                int maxBufferSize = 1024;
                int bufferSize = Math.min(bytesAvailable, maxBufferSize);
                byte[ ] buffer = new byte[bufferSize];

                // read file and write it into form...
                ByteArrayInputStream fileInputStream= new ByteArrayInputStream(p_Image);
                int bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                while (bytesRead > 0)
                {
                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable,maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0,bufferSize);
                }
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                // close streams
                fileInputStream.close();

                dos.flush();

                Log.e(Tag,"File Sent, Response: "+String.valueOf(conn.getResponseCode()));

                InputStream is = conn.getInputStream();

                // retrieve the response from server
                int ch;

                StringBuffer b =new StringBuffer();
                while( ( ch = is.read() ) != -1 ){ b.append( (char)ch ); }
                String s=b.toString();
                Log.i("Response",s);
                dos.close();
                return sFileName;
            }
            catch (MalformedURLException ex)
            {
                Log.e(Tag, "URL error: " + ex.getMessage(), ex);
            }

            catch (IOException ioe)
            {
                Log.e(Tag, "IO error: " + ioe.getMessage(), ioe);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return "";
    }

    public  Bitmap LoadImage (String p_sImageName) throws IOException {
        URL ImageUrl = new URL(ConnectionProperties.SiteUrl + "/Images/" + p_sImageName);
        Bitmap ObjBitmap = BitmapFactory.decodeStream(ImageUrl.openConnection().getInputStream());
        return ObjBitmap;
    }


    @Override
    public void run() {

    }
}
