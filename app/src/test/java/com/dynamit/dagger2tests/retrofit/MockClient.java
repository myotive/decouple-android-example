package com.dynamit.dagger2tests.retrofit;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

import retrofit.client.Client;
import retrofit.client.Request;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

/**
 * MockClient for use in Mocking Retrofit APIs
 * See: http://www.cumulations.com/blogs/13/Mock-API-response-in-Retrofit-using-custom-clients
 */
public class MockClient implements Client {

    Context context;

    public MockClient(Context context)
    {
        this.context=context;
    }

    @Override
    public Response execute(Request request) throws IOException {
        Uri uri = Uri.parse(request.getUrl());

        Log.d("MOCK SERVER", "fetching uri: " + uri.toString());

        String filename=uri.getPath();
        filename = filename.substring(filename.lastIndexOf('/') + 1).split("?")[0];

        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        InputStream is = context.getAssets().open(filename.toLowerCase()+".txt");
        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();
        String responseString = new String(buffer);
        return new Response(request.getUrl(), 200, "nothing", Collections.EMPTY_LIST, new TypedByteArray("application/json", responseString.getBytes()));
    }
}
