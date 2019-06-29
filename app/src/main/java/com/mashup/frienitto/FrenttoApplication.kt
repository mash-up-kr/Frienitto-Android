package com.mashup.frienitto

import android.app.Application
import android.util.Log
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.plugins.RxJavaPlugins
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.logger.AndroidLogger
import org.koin.core.context.startKoin
import java.io.IOException
import java.net.SocketException

class FrenttoApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@FrenttoApplication)
            modules(module)
        }
        handleRxError()
    }

    private fun handleRxError() {
        RxJavaPlugins.setErrorHandler { e ->
            var error = e
            if (error is UndeliverableException) {
                error = e.cause
            }
            if (error is IOException || error is SocketException || error is InterruptedException) {
                return@setErrorHandler
            }
            if (error is NullPointerException || error is IllegalArgumentException || error is IllegalStateException) {
                Thread.currentThread().uncaughtExceptionHandler
                    .uncaughtException(Thread.currentThread(), error)
                return@setErrorHandler
            }
            Log.w(resources.getString(R.string.error_log), error)
        }
    }
}