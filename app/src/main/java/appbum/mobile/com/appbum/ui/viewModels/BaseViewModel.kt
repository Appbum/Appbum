package appbum.mobile.com.appbum.ui.viewModels

import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import appbum.mobile.com.appbum.di.components.DaggerViewModelComponent
import appbum.mobile.com.appbum.ui.AppbumApplication
import appbum.mobile.com.appbum.ui.models.events.SnackBarEvent
import appbum.mobile.com.appbum.ui.models.factories.SnackBarFactory
import appbum.mobile.com.appbum.utils.ErrorUtils
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

open class BaseViewModel {

    private val showLoading: BehaviorSubject<Boolean> = BehaviorSubject.create()

    private val showKeyboard: PublishSubject<Boolean> = PublishSubject.create()

    private val snackBarSubject: PublishSubject<SnackBarEvent> = PublishSubject.create()

    private val showProgressDialog = BehaviorSubject.createDefault(Pair(false, 0))

    val startActivityEvent: PublishSubject<Any> = PublishSubject.create();

    val disposables = ArrayList<Disposable>();

    fun getComponent() = DaggerViewModelComponent.builder().appComponent(getApplication().appComponent).build()

    protected fun getApplication(): AppbumApplication = AppbumApplication.get()

    fun getDisposablesArray(): ArrayList<Disposable> = disposables

    /*  fun getDisposables(): Disposable {
          val size = disposables.size()
          Disposable[] vectorDisposable = new Disposable[size]
          for (int i = 0; i < size; i++) {
              vectorDisposable[i] = disposables.get(i);
          }
          return vectorDisposable;
      }*/


    fun clearDisposables() = disposables.forEach { it.dispose() }

    protected fun showKeyboard() = showKeyboard.onNext(true)

    protected fun hideKeyBoard() = showKeyboard.onNext(false)

    protected fun eventKeyboard(event: Boolean) = showKeyboard.onNext(event)

    fun showLoading() = showLoading.onNext(true)

    fun hideLoading() = showLoading.onNext(false)

    protected fun eventLoading(event: Boolean) = showLoading.onNext(event)

    protected fun showProgressDialog(@StringRes message: Int) = showProgressDialog.onNext(Pair(true, message))

    protected fun dismissProgressDialog() = showProgressDialog.onNext(Pair(false, 0))

    protected fun hideProgressDialog() = showProgressDialog.onNext(Pair(false, 0))

    protected fun eventProgressDialog(event: Pair<Boolean, Int>) = showProgressDialog.onNext(event)

    protected fun showServiceError(throwable: Throwable) {
        showSnackBarMessage(SnackBarFactory.TYPE_ERROR, ErrorUtils.getMessageError(throwable), Snackbar.LENGTH_LONG)
        hideLoading()
        hideProgressDialog()
    }

    protected fun showSnackBarError(message: String) = showSnackBarMessage(SnackBarFactory.TYPE_ERROR, message, Snackbar.LENGTH_LONG)

    protected fun showSnackBarError(messageId: Int) = showSnackBarMessage(SnackBarFactory.TYPE_ERROR, messageId, Snackbar.LENGTH_LONG)

    protected fun eventSnackBar(event: SnackBarEvent) = snackBarSubject.onNext(event)

    protected fun showSnackBarMessage(@SnackBarFactory.SnackBarType typeSnackBar: String, stringResId: Int, duration: Int) {
        val message = getApplication().resources.getString(stringResId)
        showSnackBarMessage(typeSnackBar, message, duration);
    }

    protected fun showSnackBarMessage(@SnackBarFactory.SnackBarType typeSnackBar: String, message: String, duration: Int) =
            snackBarSubject.onNext(SnackBarEvent(typeSnackBar, message, duration))

    fun observableShowLoading(): Observable<Boolean> = showLoading.observeOn(AndroidSchedulers.mainThread());

    fun observableShowProgress(): Observable<Pair<Boolean, Int>> = showProgressDialog.observeOn(AndroidSchedulers.mainThread());

    fun observableShowKeyboard(): Observable<Boolean> = showKeyboard.observeOn(AndroidSchedulers.mainThread());

    fun observableSnackBar(): Observable<SnackBarEvent> = snackBarSubject.observeOn(AndroidSchedulers.mainThread());

    fun observableStartActivity(): Observable<Any> = startActivityEvent.observeOn(AndroidSchedulers.mainThread());

}