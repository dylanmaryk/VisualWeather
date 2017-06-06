package com.dylanmaryk.visualweather.lifecycle;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.android.ActivityEvent;
import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.subjects.BehaviorSubject;
import java.util.HashMap;
import java.util.Map;

public class LifecycleHandler {
  private final Map<Activity, BehaviorSubject<ActivityEvent>> subjects = new HashMap<>();
  private BehaviorSubject<ActivityEvent> activeSubject;

  public LifecycleHandler(Application application) {
    application.registerActivityLifecycleCallbacks(lifecycleCallbacks);
  }

  public <T> ObservableTransformer<? super T, ? extends T> bindUntilEvent(ActivityEvent
                                                                              activityEvent) {
    return RxLifecycle.bindUntilEvent(activeSubject, activityEvent);
  }

  public Observable<ActivityEvent> startOnEvent(final ActivityEvent givenActivityEvent) {
    return activeSubject.filter(activityEvent -> activityEvent == givenActivityEvent);
  }

  public Observable<ActivityEvent> receiveActivityEvents() {
    return activeSubject;
  }

  private final Application.ActivityLifecycleCallbacks lifecycleCallbacks =
      new Application.ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
          activeSubject = BehaviorSubject.create();
          subjects.put(activity, activeSubject);
          activeSubject.onNext(ActivityEvent.CREATE);
        }

        @Override
        public void onActivityStarted(Activity activity) {
          activeSubject = subjects.get(activity);
          subjects.get(activity).onNext(ActivityEvent.START);
        }

        @Override
        public void onActivityResumed(Activity activity) {
          activeSubject = subjects.get(activity);
          subjects.get(activity).onNext(ActivityEvent.RESUME);
        }

        @Override
        public void onActivityPaused(Activity activity) {
          subjects.get(activity).onNext(ActivityEvent.PAUSE);
        }

        @Override
        public void onActivityStopped(Activity activity) {
          subjects.get(activity).onNext(ActivityEvent.STOP);
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
          subjects.get(activity).onNext(ActivityEvent.DESTROY);
          subjects.remove(activity);
        }
      };
}
