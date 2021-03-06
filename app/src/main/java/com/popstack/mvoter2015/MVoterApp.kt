package com.popstack.mvoter2015

import android.app.Application
import android.os.Build
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.popstack.mvoter2015.di.AppInjector
import com.popstack.mvoter2015.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import timber.log.Timber
import timber.log.Timber.DebugTree
import javax.inject.Inject

class MVoterApp : Application(), HasAndroidInjector {

  @Inject
  lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

  override fun onCreate() {
    super.onCreate()

    DaggerAppComponent.builder()
      .application(this)
      .build()
      .inject(this)

    if (BuildConfig.DEBUG) {
      //Setup Debug Configs
      Timber.plant(DebugTree())
    } else {
      //Setup Non-Debug Configs
      FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)
      FirebaseAnalytics.getInstance(this).setAnalyticsCollectionEnabled(true)
    }

    AppInjector.initAutoInjection(this)
  }

  private fun isRoboUnitTest(): Boolean {
    return "robolectric" == Build.FINGERPRINT
  }

  override fun androidInjector(): AndroidInjector<Any> {
    return dispatchingAndroidInjector
  }

  //Per https://github.com/firebase/quickstart-android/issues/370#issuecomment-499521771
  //Disable firebase auto tracking activity screen views
  //We use Single activity view, so we don't want this
  override fun registerActivityLifecycleCallbacks(callback: ActivityLifecycleCallbacks?) {
    if (callback != null && !callback.javaClass.name.startsWith("com.google.android.gms.measurement.")) {
      super.registerActivityLifecycleCallbacks(callback)
    }
  }

}