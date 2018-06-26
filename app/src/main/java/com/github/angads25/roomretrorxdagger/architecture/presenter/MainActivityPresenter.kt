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
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .map {
                            mainActivityView.hideProgress()
                            it.propertyListing
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
                        .doOnError {
                            it.printStackTrace()
                            val dbDisposable = propertyDbRepository
                                    .getProperties()
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .onErrorReturn {
                                        it.printStackTrace()
                                        Collections.emptyList()
                                    }
                                    .subscribe { mainActivityView.showData(it) }
                            disposable.add(dbDisposable)
                        }
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ mainActivityView.showData(it) }, {
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
                            mainActivityView.onError(it)
                            Collections.emptyList()
                        }
                        .doOnNext { mainActivityView.hideProgress() }
                        .subscribe({ mainActivityView.showData(it) }, Throwable::printStackTrace)
                disposable.add(dbDisposable)
        })
        disposable.add(networkDisposable)
    }

    override fun dumpData() { disposable.dispose() }
}
