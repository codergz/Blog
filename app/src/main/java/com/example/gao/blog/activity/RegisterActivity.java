package com.example.gao.blog.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.gao.blog.Config;
import com.example.gao.blog.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by gao on 2016/8/7.
 */
public class RegisterActivity extends Activity {
    String name;
    Button btn_register;
    EditText etRegAccount,etRegPassword,etRegSurePassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);
        btn_register = (Button)findViewById(R.id.btn_reg_register);
        etRegAccount = (EditText)findViewById(R.id.etRegAccount);
        etRegPassword = (EditText)findViewById(R.id.etRegPassword);
        etRegSurePassword = (EditText)findViewById(R.id.etSurePassword);


        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(etRegAccount.getText())){
                    Toast.makeText(RegisterActivity.this,"请输入账号",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(etRegPassword.getText()) || TextUtils.isEmpty(etRegSurePassword.getText())){
                    Toast.makeText(RegisterActivity.this,"密码和确认密码不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!etRegPassword.getText().toString().equals(etRegSurePassword.getText().toString())){
                    Toast.makeText(RegisterActivity.this,"密码和确认密码不一致",Toast.LENGTH_SHORT).show();
                    return;
                }

                RequestQueue mQueue = Volley.newRequestQueue(RegisterActivity.this);
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("name", etRegAccount.getText().toString());
                    jsonObject.put("password", etRegPassword.getText().toString());
                    jsonObject.put("surePassword", etRegSurePassword.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, Config.REG_URL, jsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                           name=jsonObject.toString();
                            if(name.contains("success")){
                                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                                startActivity(intent);
                            } else if(name.contains("error")){
                                Toast.makeText(RegisterActivity.this,"用户已存在，请换个账号注册",Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getApplicationContext(),volleyError.toString(),Toast.LENGTH_SHORT).show();
                    }
                });
                request.setTag("reg");
                mQueue.add(request);
                mQueue.start();
            }
        });

    }
}
