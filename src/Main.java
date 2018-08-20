import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class Main {
    /**
     * https://juejin.im/post/5b17560e6fb9a01e2862246f
     * @param args
     */
    public static void main(String[] args) {



        Main main = new Main();

//        main.subscribe(main.createObservable(),main.createObserver());

//        main.just();

        main.from();

//        main.fromCallable();

//        main.fromFuture();

        main.fromIterable();
    }

    public Observable createObservable() {


        Observable observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {

                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onComplete();
            }
        });

        return observable;
    }


    public Observer createObserver() {

        Observer observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                System.out.println("=========onSubscribe ");
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                System.out.println("=========onNext " + integer);
            }


            @Override
            public void onError(@NonNull Throwable e) {
                System.out.println("=========onError ");

            }

            @Override
            public void onComplete() {
                System.out.println("=========onComplete ");

            }
        };

        return observer;
    }

    public void subscribe(Observable observable, Observer observer) {

        observable.subscribe(observer);
    }

    public void just() {
        Observable.just(1, 5, 9)
                .subscribe(createObserver());
    }

    public void from() {
        Integer array[] = {1, 2, 3, 4, 9, 10, 435, 3};
        Observable.fromArray(array)
                .subscribe(createObserver());
    }

    public void fromCallable() {


        Observable.fromCallable(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return 1;
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                System.out.println("===========accept " + integer);
            }
        });
    }

    public void fromFuture() {

        FutureTask<String> futureTask = new FutureTask(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "返回结果";
            }
        });

        Observable.fromFuture(futureTask)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        futureTask.run();
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        System.out.println("===========accept " + s);

                    }
                });
    }

    public void fromIterable() {
        List<Integer> list = new ArrayList<>();
        list.add(0);
        list.add(1);
        list.add(2);
        list.add(3);

        Observable.fromIterable(list)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                        System.out.println("============ onSubscribe");
                    }

                    @Override
                    public void onNext(@NonNull Integer integer) {
                        System.out.println("============ onNext " + integer);

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        System.out.println("============ onError");

                    }

                    @Override
                    public void onComplete() {
                        System.out.println("============ onComplete");

                    }
                });
    }
}
