package com.popstack.mvoter2015.di

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection

object AppInjector {

  fun initAutoInjection(application: Application) {

    application.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
      override fun onActivityPaused(activity: Activity) {
        //Do Nothing
      }

      override fun onActivityStarted(activity: Activity) {
        //Do Nothing
      }

      override fun onActivityDestroyed(activity: Activity) {
        //Do Nothing
      }

      override fun onActivitySaveInstanceState(
        activity: Activity,
        outState: Bundle
      ) {
        //Do Nothing
      }

      override fun onActivityStopped(activity: Activity) {
        //Do Nothing
      }

      override fun onActivityCreated(
        activity: Activity,
        savedInstanceState: Bundle?
      ) {
        handleActivity(activity)
      }

      override fun onActivityResumed(activity: Activity) {
        //Do Nothing
      }

    })
  }

  private fun handleActivity(activity: Activity) {
    if (activity is Injectable) {
      AndroidInjection.inject(activity)
    }
    if (activity is FragmentActivity) {
      activity.supportFragmentManager
        .registerFragmentLifecycleCallbacks(
          object : FragmentManager.FragmentLifecycleCallbacks() {

            override fun onFragmentAttached(
              fm: FragmentManager,
              f: Fragment,
              context: Context
            ) {
              super.onFragmentAttached(fm, f, context)
              if (f is Injectable) {
                AndroidSupportInjection.inject(f)
              }
            }

            override fun onFragmentCreated(
              fm: FragmentManager,
              f: Fragment,
              savedInstanceState: Bundle?
            ) {
              //Do Nothing
            }
          }, true
        )
    }
  }
}