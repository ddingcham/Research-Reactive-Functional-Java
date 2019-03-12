# [Mastering Observables](https://developer.couchbase.com/documentation/server/3.x/developer/java-2.0/observables.html)  
> 출처 : Couchbase sdk 관련 문선데 설명이 잘되어 있음    
> 처음용이라기 보다는 머리에 먼가 떠올르게 하는데 좋은 설명임  

## Motivation  
> 비동기/반응형 방식은 효율적인 시스템 자원 활용을 지원  
> 네트워크나 디스크 I/O 작업 대기로 인해 낭비되는 스레드 대기 시간 같은 거  

```
실용성이 없는 java.util.concurrent.Future 나 
본격적으로 라이브러리와 런타임을 지원하는 Akka 같은
비동기/반응형 방식을 지원하기 위한 여러가지 기술이 존재하는 중 
```

* database driver requirements  
  * Rich functionality  
  * Interoperable and not opinionated  
  * Performant  
  * Small dependency and runtime footprint  
  
#### 위 요구사항을 충족하면서 비동기/반응형 방식을 따르는 db driver  
> After evaluating the requirements and solutions closely,  
> 요구사항과 솔루션들을 검토한 결과 RxJava 가 눈에 띔  

* 이유  
  * very rich set to compose asynchronous workflows  
    > async 를 지원하는 풍부한 도구들  
  * 자체 의존성이 없음.  
  * Netflix 같은 큰 회사 사용사례 존재  
  * Rx model 기반의 생태계  

#### 전통적인 블록킹 모델도 지원  

## Java 8, Lambdas and Anonymous Classes  
> Java 8 부터 지원하는 Lambdas 베이스 코드 스타일도 지원 한다는 얘기  

## Understanding Observables  
> 본격적인 Observable 모델 설명  

#### cousin("dual") // Duality of Iterable & Observable    
* push-based, asynchronous **Observable(Push)**  
  > retrieve data : onNext(T)  
  > discover error : onError(Exception)  
  > complete : onCompleted()  
* pull-based, synchronous **Iterable(Pull)**  
  > retrieve data : onNext(T)  
  > discover error : throws Exception  
  > complete : returns  
  
* **Observable** can also be converted into a **BlockingObservable**  
  > behaves very much like a Iterable  
  
#### [**key element 1**] (a complete event)zero to N data events can happen : Observable emit events  
> an error event can happen at any time too  

Once you start to work on streams instead of single value,  
> you will very much appreciate this fact.  

#### [**key element 2**] Observable does not imply that the underlying code is executed asynchronously.  
* As a consumer of an Observable  
  > 소비자는 실제 구현을 공급자에게 위임하고,  
  > 공급자는 코드를 당장 수정하지 않고, 나중에 변경하는 사이클  
  * no need to move the execution away from the caller thread  
    ~~~java
    public interface FooService {
        Observable<String> load();
    }
    ~~~
    > when load() is called, //   
    > the String value is fetched right out of **a Map in memory**  
    > or even a hard-coded value  
    
    **in this case, there is no need to move the execution away from the caller thread,**  
    > the value will be returned instantaneously. // 작업에 대한 대기 시간 동기화 불필요  
    
  * implementation is free to move it to a **Scheduler**  
    > implementation needs to be changed,  
    > so that the **String** is loaded **through a web service**(외부와 연동되는 데 시간 걸리지)  
    > 이 때, 외부 연동에 대한 시간 처리가 필요하더라도, 기본적인 Rx API의 클라이언트 코드는 그대로 둘 수 있는 구조  
    > free to move it to a **Scheduler** 니깐,  
    
## Example Code : Consuming Observables  
> Consuming a Observable means **subscribing** to it.  
> java 8 경우엔 lambdas 도 다 지원하는 거 까먹지 말고,

#### 같이 떠올리면 좋은 거, Spring Batch 랑 Javascript promise / async & await API 형태
> 비슷한 걸 해결하는 거라 비슷한 인터페이스가 나오는 듯  

### simple subscribing example  
> emitting items (print)

* basic example  
> Observer가 모든 이벤트에 대해 알림을 받고 완료된 이벤트를 수신하는 것을 볼 수 있습니다.
~~~java
    Observable
        .just(1, 2, 3)
        .subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                sout("completed");
            }
            
            @Override
            public void onError(Throwable throwable) {
                serr(throwable.getMessage());
            }
            
            // <T>
            @Override
            public void onNext(Integer integer) {
                sout("Got : " + integer);
            }
        });
~~~

  * **Note**  
    ```
    A well-formed Observable will invoke its Subscriber's onNext method
    zero or more times (Zero - N data events),
    and then will invoke either the onCompleted or onError method
    !!!!!!!!!!!!!! exactly once.  
    ```
    
* 특별한 데이터 이벤트에 대한 예외 처리 (여기선 2)  
> throws an exception and therefore **terminates the observable**  
> no subsequent values are allowed to be emitted after a error event.
~~~java
    Observable
        .just(1, 2, 3)
        .doOnNext(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                if (integer.equals(2)) {
                    throw new RuntimeException("I don't like 2");
                }
            }
        })
        .subscribe(new Subscriber<Integer>() {
            //위랑 동일
        });
~~~

```
Got : 1
I don't like 2
```

  * **Note**  
    ```
    The subscribe method also returns **Subscription**
    -> you can use to unsubscribe case
    -> you do not receive further events 
    ```

* 구독할 이벤트 갯수 제어 -> 체이닝 take(갯수)  
  * **Note**  
    ```
    If you take a close look at the API,
    subscribe() can be fed with either an Observer or a Subscriber.
    
    Unless you are implementing a custom Observer,
    always use Subscriber.
    
    because otherwise it will be wrapped in one internally anyway
    and you are saving unnecessary object allocations
    > 불필요한 객체할당 방지 가능
    ```
    
* subscribe with Action*<>() {@Ovveride public void call(T type)}  
  > You do not need to implement the full Subscriber every time.  
  > 이 경우, error case 에 대해 OnErrorNotImplementedException 이 propagated 됨
  
  **if you just want to try things out quickly or for illustrative purposes.**  
  
### From Async to Sync  
> Observable 이 같은 스레드에서 동작할 때는 스레드간 통신이 필요 없지만,  

#### Observable 의 흐름(flow)이 다른 스레드에서 실행되는 케이스에서는 data event 유실을 방지하기 위한 작업이 필요  
> promise, async/await 같은 느낌 : 다 오지도 안았는데, 구독 작업이 끝났다고 판단될 수 있음  

* 문제가 없어보이지만, 이슈가 숨어있음 example  
> 백그라운드 스레드 작업(subscribing) 도중에 main thread 가 terminated

~~~java
p s v main(String args) {
    Observable
        .interval(1, TimeUnit.SECONDS)
        .subscribe(new Action1<Long>() {
            @Override
            public void call(Long counter) {
                sout("Got: " + counter);
            }
        });
}
~~~  

* common way -> CountDownLatch  
> 개념은 기본적인 백그라운드 Thread 활용 주의 사항인데 인터페이스가 정말 이쁨  

~~~java
void thread_depth_1() {
    final CountDownLatch latch = new CountDownLatch(5);
    Observable
        .interval(1, TimeUnit.SECONDS)
        .subscribe(new Action1<Long>(){
            // subscribing in depth 2 Override call(T type)            
        });
    
    latch.await();
}
~~~

  * **Note** / 재발명 깝 ㄴㄴ  
    > One common **mistake** is to use Thread.sleep()  
    > instead of a latch to synchronize the execution between threads.  
    ```
    이는 실제로 아무 것도 동기화하지 않기 때문에 좋지 않은 아이디어이지만 일정한 시간 동안 하나의 스레드를 유지합니다. 
    실제 통화 시간이 오래 걸리면 시간 낭비가되고 길게 걸리면 원하는 효과를 얻지 못합니다. 
    단위 테스트에서이 작업을 수행하는 경우 많은 양의 비 결정적 테스트와 무작위로 실패한 테스트를 준비하십시오.
    ```
    > 그 시간에 차라리 github 가서 **CountDownLatchTest** 보는 게 나을 듯  
    
* 그 때만 BlockingObservable 로 갈아입히기 ~  
  > unique technique Observable  
  > Observable(Async) -> Iterable(Sync) // 실 코드가 아닌 컨셉을 말하는 거임  
  > CountDownLatch 가 항상 적합한 건 아니니깐  
  
  * 전환 자체와 이전 작업은 non-blocking 후속 작업(호출)만 blocking  
~~~java
    // This is not block.
    BlockingObservable<Long> observable = 
        Observable
            .interval(1, TimeUnit.SECONDS)
            .toBlocking();

    // This blocks and is called for every emitted item.
    observable.forEach(new Action1<Long>() {
        void call() {~~~~~~~~}
    })
   
~~~

  * block at the very end // 블로킹 작업은 최대한 뒤쪽에  
    > Since this will run forever,
    > you are free to chain any asynchronous computations before.
~~~java
    Observable
        .interval(1, TimeUnit.SECONDS)
        .take(5)
        .toBlocking()
        .forEach(~~~~~~~~~)
~~~

  * 확실한 single value case  
~~~java
    int value = Observable.just(1).toBlocking().single();
~~~

  > 하나 넘어가면 : IllegalArgumentException  
  > no value : NoSuchElementException  
  
  * singleOrDefault() : 이런 거 지원안하면 노답 ...
  ```
  If you check out the API documentation of the BlockingObservable, 
  you will discover many more possibilities, 
  including iterators or grabbing the first and/or last valuess.
  ```
~~~java
    JsonDocument doc = bucket.get("id").toBlocking().singleOrDefault(CONSTANT);
    if (doc == CONSTANT) {
        
    } else {
        
    }
~~~
> 요런 느낌  
  
    