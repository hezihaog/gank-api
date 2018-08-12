package com.github.wally.gank;

import com.github.wally.gank.entity.Student;
import com.github.wally.gank.entity.Teacher;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Notification;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.BooleanSupplier;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void testSubscribe() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                try {
                    emitter.onNext("文章一");
                    emitter.onNext("文章二");
                    emitter.onNext("文章三");
                    throw new RuntimeException("模拟错误...");
//                    emitter.onComplete();
                } catch (Exception e) {
                    e.printStackTrace();
                    emitter.onError(e);
                }
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println("onNext:: " + s);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                System.out.println("onError:: " + throwable.getMessage());
            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                System.out.println("onComplete！");
            }
        });
    }

    @Test
    public void testMap() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                //输入为数组，map转换为string输出
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
            }
        }).map(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) throws Exception {
                return String.valueOf(integer);
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println("接收到: " + s);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                System.out.println("异常了...");
            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                System.out.println("结束了...");
            }
        });
    }

    /**
     * FlatMap操作符，分拆出来的事件是无序的
     */
    @Test
    public void testFlatMap() {
        Observable.create(new ObservableOnSubscribe<Teacher>() {
            @Override
            public void subscribe(ObservableEmitter<Teacher> emitter) throws Exception {
                //老师1的学生
                ArrayList<Student> students = new ArrayList<Student>();
                for (int i = 0; i < 5; i++) {
                    Student student = new Student("老师1的学生" + i, 10 + i);
                    students.add(student);
                }
                Teacher teacher = new Teacher(students);
                //老师2的学生
                ArrayList<Student> students2 = new ArrayList<Student>();
                for (int i = 0; i < 5; i++) {
                    Student student = new Student("老师2的学生" + i, 10 + i);
                    students2.add(student);
                }
                Teacher teacher2 = new Teacher(students2);
                emitter.onNext(teacher);
                emitter.onNext(teacher2);
                emitter.onComplete();
            }
        }).flatMap(new Function<Teacher, ObservableSource<Student>>() {
            @Override
            public ObservableSource<Student> apply(Teacher teacher) throws Exception {
                return Observable.fromIterable(teacher.getStudents());
            }
        }).subscribe(new Observer<Student>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Student student) {
                System.out.println("onNext --> " + student.getName());
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                System.out.println("onComplete --> 结束了");
            }
        });
    }

    /**
     * ConcatMap操作符，分拆出来的事件是有序的
     */
    @Test
    public void testConcatMap() {
        //输入一批学号，拼接处一个有前缀的字符串
        Observable.create(new ObservableOnSubscribe<ArrayList<Integer>>() {
            @Override
            public void subscribe(ObservableEmitter<ArrayList<Integer>> emitter) throws Exception {
                //学号
                ArrayList<Integer> list = new ArrayList<>();
                list.add(1);
                list.add(2);
                list.add(3);
                emitter.onNext(list);
                emitter.onComplete();
            }
        }).concatMap(new Function<ArrayList<Integer>, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(ArrayList<Integer> integers) throws Exception {
                ArrayList<String> list = new ArrayList<String>();
                for (Integer integer : integers) {
                    list.add("学号:" + integer);
                }
                return Observable.fromIterable(list);
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println("接收到 ---> " + s);
            }
        });
    }

    @Test
    public void testBuffer() {
        Observable.just(1, 2, 3, 4)
                .buffer(3, 1)
                //缓存区大小，每次输出的数量
                //步长，每次获取的事件数量
                .subscribe(new Consumer<List<Integer>>() {
                    @Override
                    public void accept(List<Integer> integers) throws Exception {
                        System.out.println("buffer --> 数量" + integers.size());
                        for (Integer integer : integers) {
                            System.out.println("buffer 事件--> " + integer);
                        }
                    }
                });
    }

    @Test
    public void testJust() {
        Observable.just(1, 2, 3, 4, 5)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        System.out.println("testJust 收到 -->" + integer);
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("testJust 异常了");
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("testJust 结束了");
                    }
                });
    }

    @Test
    public void testFromArray() {
        Integer[] ints = {1, 2, 3, 4, 5};
        Observable.fromArray(ints)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        System.out.println("遍历数组 :" + integer);
                    }
                });
    }

    @Test
    public void testFromIterable() {
        ArrayList<String> stringList = new ArrayList<>();
        stringList.add("元素1");
        stringList.add("元素2");
        stringList.add("元素3");
        stringList.add("元素4");
        stringList.add("元素5");
        Observable.fromIterable(stringList)
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        System.out.println("集合遍历：" + s);
                    }
                });
    }

    private int i = 1;

    @Test
    public void testDefer() {
        //延迟，被观察者直到被注册才创建，所以虽然先执行了被观察者代码，i还是为5
        Observable<Integer> observable = Observable.defer(new Callable<ObservableSource<Integer>>() {
            @Override
            public ObservableSource<Integer> call() throws Exception {
                return Observable.just(i);
            }
        });
        i = 5;
        observable.subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                System.out.println("收到整数：" + integer);
            }
        });
    }

    @Test
    public void testTimer() {
        //定时器，延时2毫秒执行，已过时，推荐使用interval和take来实现
        Observable
                .timer(2, TimeUnit.MILLISECONDS)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        System.out.println("timer --> onNext" + aLong);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        System.out.println("timer --> onComplete");
                    }
                });
    }

    private Disposable disposable;

    @Test
    public void testInterval() {
        Observable.timer(2, TimeUnit.MILLISECONDS)
                .take(3)//设置总共发送的次数
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(Long aLong) {
                        System.out.println("onNext --> " + aLong);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        if (disposable != null && !disposable.isDisposed()) {
                            disposable.dispose();
                            disposable = null;
                        }
                    }
                });
    }

    @Test
    public void testConcat() {
        //concatArray或者concat，可传入多个被观察数据源，concat只能接受<=4，concatArray则>4
        //多个数据源发送是串行的，会一个发送完到下一个
        Observable.concatArray(Observable.just("A类型事件1", "A类型事件2"),
                Observable.just("B类型事件3", "B类型事件4"),
                Observable.just("C类型事件5", "C类型事件6"),
                Observable.just("D类型事件7", "D类型事件8"))
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        System.out.println("收到：" + s);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Test
    public void testMerge() {
        //和concat一样，可以传入多个被观察的数据源，但是merge是并行的
        Observable.mergeArray(
                //intervalRange，从1开始，输出5个数据，第一次的延时开始0秒，2毫秒执行一次
                //intervalRange为可设置延迟时间，range则不可以
                Observable.intervalRange(0, 5, 0, 2, TimeUnit.MILLISECONDS),
                Observable.intervalRange(10, 5, 0, 2, TimeUnit.MILLISECONDS))
                .map(new Function<Long, String>() {
                    @Override
                    public String apply(Long aLong) throws Exception {
                        //map转换
                        return String.valueOf(aLong);
                    }
                })
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        System.out.println("收到：" + s);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Test
    public void testRange() {
        Observable
                //指定开始位置，快速发送100个事件
                .range(1, 100)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        System.out.println("range --> " + integer);
                    }
                });
    }

    @Test
    public void testConcatDelayError() {
        //concatArrayDelayError，如果其中一个数据源发送的onError事件，则所有的数据源都中断发送，如果使用concatArrayDelayError，则不会中断，会将错误事件推迟到最后才发送
        //mergeDelayError同理
        Observable.concatArrayDelayError(
                Observable.create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                        emitter.onNext(1);
                        emitter.onNext(2);
                        //发送Error事件，使用了concatArrayDelayError，会等到全部数据源都发送完时间，才发送错误事件
                        emitter.onError(new NullPointerException());
                        emitter.onNext(3);
                        emitter.onComplete();
                    }
                }),
                Observable.just(4, 5, 6))
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Integer value) {
                        System.out.println("testConcatDelayError --> " + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("testConcatDelayError --> onError");
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    @Test
    public void testZip() {
        //观察者1，设置被注册时，工作再io线程
        Observable observable1 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io());
        //观察者2，设置注册时，工作在另外一个新线程
        Observable observable2 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("A");
                emitter.onNext("B");
                emitter.onNext("C");
                //多出来的D没有数据源配对，也会发送，只是最后注册时的回调没有收到
                emitter.onNext("D");
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.newThread());
        //zip操作符，组合2个数据源
        Observable.zip(observable1, observable2, new BiFunction<Integer, String, String>() {

            @Override
            public String apply(Integer integer, String s) throws Exception {
                return "result: " + integer + "||" + s;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println("result --> " + s);
            }
        });
    }

    @Test
    public void testReduce() {
        //将数据源的数据聚合为一次输出
        Observable.just(1, 2, 3, 4)
                //将数据源的每一个事件进行聚合最后输出一次，如果想每次聚合都调用观察者的输出则使用scan操作符
                .reduce(new BiFunction<Integer, Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer, Integer integer2) throws Exception {
                        //左边integer是上一次apply计算的结果，右边integer2则为下一个数
                        System.out.println(integer + " -- " + integer2);
                        return integer + integer2;
                    }
                }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                System.out.println("全部相加的结果 " + integer);
            }
        });
    }

    @Test
    public void testCollect() {
        //将数据源发出来的每一个事件收集到一个数据结构中
        Observable
                .just(1, 2, 3, 4)
                .collect(new Callable<ArrayList<Integer>>() {
                    @Override
                    public ArrayList<Integer> call() throws Exception {
                        return new ArrayList<Integer>();
                    }
                }, new BiConsumer<ArrayList<Integer>, Integer>() {
                    @Override
                    public void accept(ArrayList<Integer> integers, Integer integer) throws Exception {
                        //将数据源发出的事件中带的数据存储到集合
                        integers.add(integer);
                    }
                }).subscribe(new Consumer<ArrayList<Integer>>() {
            @Override
            public void accept(ArrayList<Integer> integers) throws Exception {
                //最后一次回调返回集合
                System.out.println(integers);
            }
        });
    }

    @Test
    public void testStartWith() {
        Observable.just(5, 6, 7, 8)
                .startWithArray(1, 2, 3, 4)//将一个数组的数据插入到最前面
                .startWith(0)//将一个数据插入到最前面
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        System.out.println(integer);
                    }
                });
    }

    @Test
    public void testCount() {
        Observable
                .just(1, 2, 3, 4)
                .count()//统计事件数量
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        System.out.println("事件总数：" + aLong);
                    }
                });
    }

    @Test
    public void testDelay() {
        //延时让数据源发送事件
        Observable
                .just(1, 2, 3, 4)
                .delay(2, TimeUnit.SECONDS)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        System.out.println("testDelay: " + integer);
                    }
                });
        try {
            Thread.sleep(3 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDo() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("A");
                emitter.onNext("B");
                emitter.onNext("C");
                emitter.onNext("D");
//                emitter.onError(new RuntimeException());
                emitter.onComplete();
            }
        })
                //每次数据源发送事件就回调一次，不管是next还是error类型
                .doOnEach(new Consumer<Notification<String>>() {
                    @Override
                    public void accept(Notification<String> stringNotification) throws Exception {
                        System.out.println("数据源发送数据啦--->");
                    }
                })
                //数据源发送onNext前时回调
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        System.out.println("数据源<准备>发送onNext了");
                    }
                })
                //数据源发送onNext后调用
                .doAfterNext(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        System.out.println("数据源发送onNext<结束>");
                    }
                })
                //数据源发送error后调用
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        System.out.println("数据源发送onError");
                    }
                })
                //数据源被订阅时回调
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        System.out.println("数据源被订阅啦");
                    }
                })
                //数据源发送完毕后回调，无论是onError还是onComplete
                .doOnTerminate(new Action() {
                    @Override
                    public void run() throws Exception {
                        System.out.println("doOnTerminate()被调用");
                    }
                })
                //finally操作，最终执行，只有在onComplete时回调，onError时不会调用的
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        System.out.println("doFinally()被调用");
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        System.out.println(s);
                    }
                });
    }

    @Test
    public void testErrorReturn() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onError(new RuntimeException("出异常了..."));
            }
        }).onErrorReturn(new Function<Throwable, Integer>() {
            @Override
            public Integer apply(Throwable throwable) throws Exception {
                //捕获异常事件，发送一个code为500的事件到onNext，来正常完成事件
                System.out.println("捕获到异常啦 -->" + throwable.getMessage());
                return 500;
            }
        }).map(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) throws Exception {
                return String.valueOf(integer);
            }
        })
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String integer) {
                        System.out.println("接收到：" + integer);
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("发生错误啦" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("onComplete()被调用了");
                    }
                });
    }

    @Test
    public void testOnErrorResumeNext() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onNext(4);
                emitter.onError(new RuntimeException());
            }
        })
                .onErrorResumeNext(new Function<Throwable, ObservableSource<? extends Integer>>() {
                    @Override
                    public ObservableSource<? extends Integer> apply(Throwable throwable) throws Exception {
                        System.out.println("捕获异常 " + throwable.getMessage());
                        //捕获异常，新建一个数据源来发送onNext
                        return Observable.just(110, 120, 114);
                    }
                })
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) throws Exception {
                        return String.valueOf(integer);
                    }
                })
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String integer) {
                        System.out.println("收到了" + integer);
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("异常，onError()");
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("正常结束onComplete()");
                    }
                });
    }

    @Test
    public void testRetry() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onNext(4);
                emitter.onError(new RuntimeException("出异常啦"));
            }
        })
                //.retry()//出错就重新发送，一直重复
                //.retry(3)//重试3次，3次就调用onError
//                .retry(new Predicate<Throwable>() {//重试条件，前面如果不加上次数则是不断调用
//                    @Override
//                    public boolean test(Throwable throwable) throws Exception {
//                        //true代表重试，false代表不重试
//                        return true;
//                    }
//                })
                .retry(3, new Predicate<Throwable>() {//一般使用重试次数加上判断条件
                    @Override
                    public boolean test(Throwable throwable) throws Exception {
                        return true;
                    }
                })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        System.out.println("收到了" + integer);
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("异常，onError()");
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("正常结束onComplete()");
                    }
                });
    }

    @Test
    public void testRetryUntil() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onNext(4);
                emitter.onError(new RuntimeException("出异常啦"));
            }
            //retryUntil，重试条件
        }).retryUntil(new BooleanSupplier() {
            @Override
            public boolean getAsBoolean() throws Exception {
                //作用相当于retry(new Predicate<Throwable>() {})
                //唯一区别，返回true为不重试，并且不发送事件
                //false就为一直重试
                return false;
            }
        })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        System.out.println("收到了" + integer);
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("异常，onError()");
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("正常结束onComplete()");
                    }
                });
    }

    @Test
    public void testRetryWhen() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onNext(4);
                emitter.onError(new RuntimeException("出异常啦"));
            }
            //retryWhen，onError()时回调，发生异常时返回一个新的数据源
        }).retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(Observable<Throwable> throwableObservable) throws Exception {
                //flatMap转换返回一个新的数据源
                return throwableObservable.flatMap(new Function<Throwable, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Throwable throwable) throws Exception {
                        //如果新的数据源发送onError()，则原始的数据源不再发送事件
                        //如果新的数据源发送onNext()，则原始的数据源继续发送事件，如果又发生错误，则继续出发重试
//                        return Observable.just(1);
                        return Observable.error(new RuntimeException("retryWhen终止啦"));
                    }
                });
            }
        })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        System.out.println("收到了" + integer);
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("异常，onError()");
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("正常结束onComplete()");
                    }
                });
    }

    @Test
    public void testRepeat() {
        //重复发送数据源事件
        Observable
                .just(1, 2, 3)
                //.repeat()//不设置次数则一直重复
                .repeat(3)//重复发送3次
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        System.out.println("收到: " + integer);
                    }
                });
    }

    @Test
    public void testRepeatWhen() {
        Observable.just(1, 2, 4)
                .repeat(2)
                .repeatWhen(new Function<Observable<Object>, ObservableSource<?>>() {
            @Override
            // 在Function函数中，必须对输入的 Observable<Object>进行处理，这里我们使用的是flatMap操作符接收上游的数据
            public ObservableSource<?> apply(Observable<Object> objectObservable) throws Exception {
                // 将原始 Observable 停止发送事件的标识（Complete（） /  Error（））转换成1个 Object 类型数据传递给1个新被观察者（Observable）
                // 以此决定是否重新订阅 & 发送原来的 Observable
                // 此处有2种情况：
                // 1. 若新被观察者（Observable）返回1个Complete（） /  Error（）事件，则不重新订阅 & 发送原来的 Observable
                // 2. 若新被观察者（Observable）返回其余事件，则重新订阅 & 发送原来的 Observable
                return objectObservable.flatMap(new Function<Object, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Object throwable) throws Exception {
                        // 情况1：若新被观察者（Observable）返回1个Complete（） /  Error（）事件，则不重新订阅 & 发送原来的 Observable
                        return Observable.empty();
                        // Observable.empty() = 发送Complete事件，但不会回调观察者的onComplete（）

                        // return Observable.error(new Throwable("不再重新订阅事件"));
                        // 返回Error事件 = 回调onError（）事件，并接收传过去的错误信息。

                        // 情况2：若新被观察者（Observable）返回其余事件，则重新订阅 & 发送原来的 Observable
//                         return Observable.just(1);
                        // 仅仅是作为1个触发重新订阅被观察者的通知，发送的是什么数据并不重要，只要不是Complete（） /  Error（）事件
                    }
                });
            }
        })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        System.out.println("收到了" + integer);
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("异常，onError()");
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("正常结束onComplete()");
                    }
                });
    }
}