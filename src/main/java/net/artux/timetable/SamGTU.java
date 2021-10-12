package net.artux.timetable;

import okhttp3.*;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SamGTU {

  static ArrayList<Cookie> cookies = new ArrayList<>();
  private static API api;

  SamGTU(){
    final OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
    clientBuilder.addInterceptor(new Interceptor() {
      @Override
      public Response intercept(Chain chain) throws IOException {

        Response response = chain.proceed(chain.request());
        Response modified = response.newBuilder()
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .build();

        return modified ;
      }
    });
    clientBuilder.cookieJar(new CookieJar() {

      @Override
      public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        for (Cookie cookie: cookies)
          if (!SamGTU.cookies.contains(cookie))
            SamGTU.cookies.add(cookie);
      }

      @Override
      public List<Cookie> loadForRequest(HttpUrl url) {
        return cookies;
      }
    });
    Cookie.Builder b = new Cookie.Builder();
    b.name("PHPSESSID");
    b.value("hlsucqggam8ao6euhp3ciau7o1");
    b.httpOnly();
    b.domain("lk.samgtu.ru");
    b.path("/");
    cookies.add(b.build());


    Retrofit.Builder builder = new Retrofit.Builder();
    api = builder.baseUrl("https://lk.samgtu.ru/")
            .client(clientBuilder.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(API.class);
  }

  public static API getApi() {
    return api;
  }


}
