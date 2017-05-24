
package id.paniclabs.arch.data.rest.utils;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;

public class QueryInterceptorFactory {

    private static final String APIKEY = "76cabe3b54f74ab4c0934c6fcfd4bd90";

    public Interceptor getQueryInterceptor() {
        return chain -> {

            Request original = chain.request();
            HttpUrl originalHttpUrl = original.url();

            HttpUrl url = originalHttpUrl.newBuilder()
                    .addQueryParameter("api_key", APIKEY)
                    .build();

            Request.Builder requestBuilder = original.newBuilder()
                    .url(url);

            Request request = requestBuilder.build();
            return chain.proceed(request);
        };
    }

}
