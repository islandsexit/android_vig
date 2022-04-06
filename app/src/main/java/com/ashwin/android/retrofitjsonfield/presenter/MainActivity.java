package com.ashwin.android.retrofitjsonfield.presenter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ashwin.android.retrofitjsonfield.R;
import com.ashwin.android.retrofitjsonfield.data.AddressDeserializer;
import com.ashwin.android.retrofitjsonfield.data.ProfileApi;
import com.ashwin.android.retrofitjsonfield.model.GET_CODE;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    public static final String APP_TAG = "retrofit-json-variable";
    private static final String BASE_URL = "http://192.168.48.131/";

    private TextView cityTextView;
    private EditText invite_code_ET;
    private Button btn_sbmt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        invite_code_ET = findViewById(R.id.invite_code_text);
        btn_sbmt = findViewById(R.id.btn_code);

        cityTextView = findViewById(R.id.city_textview);
        btn_sbmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String invite_code = invite_code_ET.getText().toString();
                loadProfile(invite_code);
            }
        });
    }



    private void loadProfile(String invite_code) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(JSONObject.class, new AddressDeserializer());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
                .build();

        ProfileApi profileApi = retrofit.create(ProfileApi.class);
        Call<GET_CODE> call = profileApi.Check_code(invite_code, "secretmasterpasswordvig");
        call.enqueue(new Callback<GET_CODE>() {
            @Override
            public void onResponse(Call<GET_CODE> call, Response<GET_CODE> response) {
                try {
                    int statusCode = response.code();
                    if (statusCode == 200) {
                        GET_CODE GETCODE = response.body();
                        Log.w(APP_TAG, "onResponse| response: " + GETCODE);
                        final List data_get = GETCODE.getData();
                        String RESULT = (String) data_get.get(0);
                        String id = (String) data_get.get(1);
                        String name = (String) data_get.get(2);
                        runOnUiThread(() -> {
                            cityTextView.setText("Result: " + RESULT+" id: " + id+" name: " + name);
                        });
                    } else {
                        Log.e(APP_TAG, "onResponse | status: " + statusCode);
                    }
                } catch (Exception e) {
                    Log.e(APP_TAG, "onResponse | exception", e);
                }
            }

            @Override
            public void onFailure(Call<GET_CODE> call, Throwable t) {
                Log.e(APP_TAG, "onFailure", t);
            }
        });
    }
}
