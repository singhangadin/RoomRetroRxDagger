package com.github.angads25.roomretrorxdagger.architecture.presenter

import android.content.Context
import com.github.angads25.roomretrorxdagger.architecture.contract.PropertyContract
import com.github.angads25.roomretrorxdagger.dagger.qualifier.ApplicationContext
import com.github.angads25.roomretrorxdagger.retrofit.repository.PropertyApiRepository
import com.github.angads25.roomretrorxdagger.room.repository.PropertyDbRepository
import com.github.angads25.roomretrorxdagger.utils.Utility
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainActivityPresenter
@Inject constructor(@ApplicationContext val context: Context,
                    val propertyApiRepository: PropertyApiRepository,
                    val mainActivityView: PropertyContract.PropertyView,
                    val propertyDbRepository: PropertyDbRepository
) : PropertyContract.PropertyPresenter {
    private val disposable = CompositeDisposable()

    override fun loadData() {
        mainActivityView.showProgress()
        val networkDisposable = Utility.isNetworkAvailable(context)
            .subscribe({
                val retroDisposable = propertyApiRepository
                        .getPropertyList()
                        .delay(10, TimeUnit.SECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .map {
                            mainActivityView.hideProgress()
                            it.propertyListing
                        }
                        .doOnError {
                            it.printStackTrace()
                            propertyDbRepository
                                    .getProperties()
                                    .subscribeOn(Schedulers.io())
                                    .onErrorReturn {
                                        it.printStackTrace()
                                        Collections.emptyList()
                                    }
                                    .subscribe()
                        }
                        .switchMap {
                            return@switchMap Observable.just(it)
                                    .map {
                                        propertyDbRepository.deleteAll()
                                        propertyDbRepository.insertAll(it)
                                        it
                                    }
                                    .subscribeOn(Schedulers.io())
                        }
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            mainActivityView.showData(it)
                        }, {
                            mainActivityView.hideProgress()
                            mainActivityView.onError(it)
                        })
                disposable.add(retroDisposable)
        }, {
            it.printStackTrace()
                val dbDisposable = propertyDbRepository
                        .getProperties()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .onErrorReturn {
                            mainActivityView.hideProgress()
                            mainActivityView.onError(it)
                            Collections.emptyList()
                        }
                        .subscribe({
                            mainActivityView.hideProgress()
                            mainActivityView.showData(it)
                        }, Throwable::printStackTrace)
                disposable.add(dbDisposable)
        })
        disposable.add(networkDisposable)
    }

    override fun dumpData() {
        disposable.dispose()
    }
}
