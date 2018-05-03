package com.wzg.core.http;

import android.content.Context;
import android.os.Environment;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.socks.library.KLog;
import com.wzg.core.App;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ApiRetrofit {

    private static ApiRetrofit mApiRetrofit;
    private final Retrofit mRetrofit;
    private OkHttpClient mClient;
    private ApiService mApiService;

    private GsonBuilder builder;

    //缓存配置
    private Interceptor mCacheInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            CacheControl.Builder cacheBuilder = new CacheControl.Builder();
            cacheBuilder.maxAge(0, TimeUnit.SECONDS);
            cacheBuilder.maxStale(365, TimeUnit.DAYS);
            CacheControl cacheControl = cacheBuilder.build();

            Request request = chain.request();
            if (!NetWorkUtils.INSTANCE.isNetworkAvailable(App.Companion.getInstance())) {
                request = request.newBuilder()
                        .cacheControl(cacheControl)
                        .build();
            }
            Response originalResponse = chain.proceed(request);
            if (NetWorkUtils.INSTANCE.isNetworkAvailable(App.Companion.getInstance())) {
                int maxAge = 0; // read from cache
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public ,max-age=" + maxAge)
                        .build();
            } else {
                int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
        }
    };

    /**
     * 请求访问quest和response拦截器
     */
    private Interceptor mLogInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            long startTime = System.currentTimeMillis();
            Response response = chain.proceed(chain.request());
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            okhttp3.MediaType mediaType = response.body().contentType();
            String content = response.body().string();
            KLog.e("----------Request Start----------------");
            KLog.e("| " + request.toString() + request.headers().toString());
            KLog.json("| Response:" + content);
            KLog.e("----------Request End:" + duration + "毫秒----------");
            return response.newBuilder()
                    .body(okhttp3.ResponseBody.create(mediaType, content))
                    .build();
        }
    };

    /**
     * 增加头部信息的拦截器
     */
    private Interceptor mHeaderInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request.Builder builder = chain.request().newBuilder();
            builder.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.108 Safari/537.36 2345Explorer/8.0.0.13547");
            builder.addHeader("Cache-Control", "max-age=0");
            builder.addHeader("Upgrade-Insecure-Requests", "1");
            builder.addHeader("X-Requested-With", "XMLHttpRequest");
            builder.addHeader("Cookie", "uuid=\"w:f2e0e469165542f8a3960f67cb354026\"; __tasessionId=4p6q77g6q1479458262778; csrftoken=7de2dd812d513441f85cf8272f015ce5; tt_webid=36385357187");
//        LoginResponse loginResponse = SharedPreferencesUtil.getPreferences(Constant.SP_NAME, Constant.SP_LOGIN_RESPONSE);
//        if (loginResponse != null && !TextUtils.isEmpty(loginResponse.getToken())) {
//            builder.addHeader("X-OperatorToken", loginResponse.getToken());
//        }
            return chain.proceed(builder.build());
        }
    };


    public ApiRetrofit() {
        //cache url
        File httpCacheDirectory = new File(getDiskCachePath(App.Companion.getInstance()), "responses");
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(httpCacheDirectory, cacheSize);

        //        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT);
        //        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);//请求/响应行 + 头 + 体

        mClient = new OkHttpClient.Builder()
                .addInterceptor(mHeaderInterceptor)//添加头部信息拦截器
                .addInterceptor(mLogInterceptor)//添加log拦截器
                .cache(cache)
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();
        builder = new GsonBuilder();
        builder.registerTypeAdapter(Date.class, new DateSerializer()).setDateFormat(DateFormat.LONG);
        builder.registerTypeAdapter(Date.class, new DateDeserializer()).setDateFormat(DateFormat.LONG);

        mRetrofit = new Retrofit.Builder()
                .baseUrl("http://penny.tunnel.qydev.com/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(CustomGsonConverterFactory.create(builder.create()))
                .client(mClient)
                .build();

        mApiService = mRetrofit.create(ApiService.class);
    }

    public static String getDiskCachePath(Context context) {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable()) {
            return context.getExternalCacheDir().getPath();
        } else {
            return context.getCacheDir().getPath();
        }
    }

    public static ApiRetrofit getInstance() {
        if (mApiRetrofit == null) {
            synchronized (Object.class) {
                if (mApiRetrofit == null) {
                    mApiRetrofit = new ApiRetrofit();
                }
            }
        }
        return mApiRetrofit;
    }

    public ApiService getApiService() {
        return mApiService;
    }

    public class DateSerializer implements JsonSerializer<Date> {
        public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.getTime());
        }
    }

    public class DateDeserializer implements JsonDeserializer<Date> {
        public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return new Date(json.getAsJsonPrimitive().getAsLong());
        }
    }
}
