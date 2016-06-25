package com.rubik.applogincard.app.controllers;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

    /**
     * Created by Rubik on 17/6/16.
     *  -> Managed the internet request
     */
    public class AppControllerVolley extends Application {

        public static final String TAG = AppControllerVolley.class.getSimpleName();
        private RequestQueue m_RequestQueue;
        private static AppControllerVolley m_Instance;

        @Override
        public void onCreate() {
            super.onCreate();
            m_Instance = this;
        }

        public static synchronized AppControllerVolley getInstance() {
            return m_Instance;
        }

        public RequestQueue getRequestQueue() {
            if (m_RequestQueue == null) {
                m_RequestQueue = Volley.newRequestQueue(getApplicationContext());
            }
            return m_RequestQueue;
        }

        public <T> void addToRequestQueue(Request request, String tag) {
            request.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
            getRequestQueue().add(request);
        }


        public <T> void addToRequestQueue(Request request) {
            request.setTag(TAG);
            getRequestQueue().add(request);
        }

        public void cancelPendingRequest(Object tag) {
            if (m_RequestQueue != null) {
                m_RequestQueue.cancelAll(tag);
            }
        }

    }
