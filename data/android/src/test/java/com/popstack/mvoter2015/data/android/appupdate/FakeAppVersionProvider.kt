package com.popstack.mvoter2015.data.android.appupdate

import com.popstack.mvoter2015.domain.infra.AppVersionProvider
import javax.inject.Inject

class FakeAppVersionProvider @Inject constructor() : AppVersionProvider {

  var versionCode = 0L
  var versionName = "0.0.0"

  override fun versionCode(): Long {
    return versionCode
  }

  override fun versionName(): String {
    return versionName
  }

}