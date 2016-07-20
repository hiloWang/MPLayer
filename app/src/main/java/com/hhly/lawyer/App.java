package com.hhly.lawyer;

import android.app.Application;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.facebook.stetho.Stetho;
import com.frogermcs.androiddevmetrics.AndroidDevMetrics;
import com.hhly.lawyer.data.di.components.ApiComponent;
import com.hhly.lawyer.data.di.components.DaggerApiComponent;
import com.hhly.lawyer.data.di.modules.ApiModule;
import com.hhly.lawyer.data.util.Logger;
import com.hhly.lawyer.di.components.AppComponent;
import com.hhly.lawyer.di.components.DaggerAppComponent;
import com.hhly.lawyer.di.modules.AppModule;

import java.lang.ref.WeakReference;

public class App extends Application {

    private AppComponent appComponent;
    private ApiComponent apiComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        this.initializeInjector();
        this.initializeLakCanary();
        this.initializeExceptionHandler();
        this.initializeDevMetrics();
        this.initializeRuntimeMemoryManager();
        this.initializeLogger();
        this.initializeStetho();
    }

    private void initializeStetho() {
        if (BuildConfig.DEBUG) {
            Stetho.initialize(Stetho.newInitializerBuilder(this)
                    .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                    .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                    .build());
        }
    }

    private void initializeLogger() {
        Logger.init(BuildConfig.LOG_ENABLE, "LAWYER");
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    public ApiComponent getApiComponent() {
        return apiComponent;
    }

    private void initializeDevMetrics() {
        if (BuildConfig.DEBUG) {
            AndroidDevMetrics.initWith(this);
        }
    }

    private void initializeExceptionHandler() {
        //        CrashHandler crashHandler = CrashHandler.getInstance();
        //        crashHandler.init(this);
    }

    private void initializeLakCanary() {
        // 检查内存泄露
        //		LeakCanary.install(this);
    }

    private void initializeInjector() {
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
        apiComponent = DaggerApiComponent.builder().apiModule(new ApiModule(this, BuildConfig.API_HOST_URL)).build();
    }

    private void initializeRuntimeMemoryManager() {
        if (dummyHandler == null) dummyHandler = new DummyHandler(this);
        dummyHandler.obtainMessage().sendToTarget();
    }

    DummyHandler dummyHandler;

    private Runnable dummyRunnable = new Runnable() {
        @Override
        public void run() {
//			if (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory() > Runtime.getRuntime().maxMemory() * 0.8) {
//
//			}
            onTrimMemory(TRIM_MEMORY_RUNNING_LOW);
            if (Runtime.getRuntime().totalMemory() > 25000000) {
                Log.e("HILO", "释放内存中");
                System.gc();
            }
            dummyHandler.obtainMessage().sendToTarget();
        }
    };

    private static class DummyHandler extends Handler {

        private WeakReference<App> weakReference;

        DummyHandler(App app) {
            weakReference = new WeakReference<>(app);
        }

        @Override
        public void handleMessage(Message msg) {
            final App mApp = weakReference.get();
            if (mApp != null) {
                mApp.dummyHandler.postDelayed(mApp.dummyRunnable, 3000);
            }
        }
    }
}
