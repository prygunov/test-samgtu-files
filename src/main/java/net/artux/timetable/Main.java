package net.artux.timetable;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class Main {

  public static void main(String[] args) throws IOException {
    SamGTU samGTU = new SamGTU();
    if(samGTU.getApi().login("prygunov.mi", "dirGe991850").execute().isSuccessful()){

      PrintWriter writer = new PrintWriter("data.txt", "UTF-8");
      int spaces = 0;
      for (int i = 2750100; i<2795117; i++) {
        Response<ResponseBody> response = samGTU.getApi().download(i).execute();
        String filename = response.headers().values("content-disposition").toString();
        if (!filename.equals("[]")) {
          System.out.println("id: " + i + ", " + filename);
          writer.println("id: " + i + ", " + filename);
          if (filename.contains("2") && filename.contains("9")) {
            System.out.println("GOT!!!!!!!!!!!!! id: " + i + ", " + filename);
            writer.println("GOT!!!!!!!!!!!!! id: " + i + ", " + filename);
          }
        }else {
          spaces++;
          if (spaces>20){
            System.out.println("id: " + i + ", EMPTY, DROP 100");
            i = ((i / 100)+1)*100;
            spaces = 0;
          }
        }
      }
      writer.close();
    }
  }

}
