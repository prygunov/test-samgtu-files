package net.artux.timetable;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.*;

import java.io.File;

public interface API {

  @FormUrlEncoded
  @POST("/site/login")
  Call<Void> login(@Field("LoginForm[username]")String login, @Field("LoginForm[password]")String password);

  @GET("/distancelearning/distancelearning/view")
  Call<ResponseBody> occupation(@Query("id")Integer id);


  @GET("/files")
  @Streaming
  Call<ResponseBody> download(@Query("id") int id);
}
