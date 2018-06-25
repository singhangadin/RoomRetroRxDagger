package com.github.angads25.roomretrorxdagger.architecture.presenter

import android.content.Context
import com.github.angads25.roomretrorxdagger.architecture.contract.PropertyContract
import com.github.angads25.roomretrorxdagger.dagger.qualifier.ApplicationContext
import com.github.angads25.roomretrorxdagger.retrofit.repository.PropertyApiRepository
import com.github.angads25.roomretrorxdagger.room.repository.PropertyDbRepository
import com.github.angads25.roomretrorxdagger.utils.Utility
import io.reactivex.Completable
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
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .map {
                            mainActivityView.hideProgress()
                            it.propertyListing
                        }
                        .onErrorReturn {
                            it.printStackTrace()
                            mainActivityView.hideProgress()
                            Collections.emptyList()
                        }
                        .subscribe({
                            val dbDisposable = Completable.fromAction {
                                propertyDbRepository.deleteAll()
                                propertyDbRepository.insertAll(it)
                            }
                                    .subscribeOn(Schedulers.io())
                                    .subscribe()
                            mainActivityView.showData(it)
                            disposable.add(dbDisposable)
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
