package appbum.mobile.com.appbum.managers.events;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

public class RxBus {

    private static final RxBus INSTANCE = new RxBus();
    private final Subject<Object> busSubject = PublishSubject.create();

    public RxBus() {
    }

    public static RxBus getInstance() {
        return INSTANCE;
    }

    public <T> Disposable register(final Class<T> eventClass, Consumer<T> onNext) {
        return this.busSubject.filter(event -> event.getClass().equals(eventClass))
                .map(object -> (T) object)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(onNext);
    }

    public void unRegister(Disposable disposables) {
        if (disposables != null && disposables.isDisposed()) {
            disposables.dispose();
        }

    }

    public void post(Object event) {
        this.busSubject.onNext(event);
    }

}
