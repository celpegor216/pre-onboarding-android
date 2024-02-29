import kotlin.system.*
import kotlinx.coroutines.*

// co-routine
// Kotlin의 coroutine은 launch와 같은 동시 작업을 명령하지 않는 이상
// 기본적으로 순차적이고 event loop를 공유함


// Network 작업 등
suspend fun printForecast() {
    // suspend function
    // CoroutineScope 혹은 다른 suspend function 내에서 호출되어야 함
    delay(1000)
    println("Sunny")
}

suspend fun printTemperature() {
    delay(1000)
    println("30\u00b0C")
}

suspend fun getForecast(): String {
    delay(1000)
    return "Sunny"
}

suspend fun getTemperature(): String {
    delay(1000)
    return "30\u00b0C"
}

suspend fun getError(): String {
    delay(500)
    throw AssertionError("Temperature is invalid")
    return "30\u00b0C"
}


// ----------------------------------------------------
// synchronous
// 이전 작업이 끝나야 다음 작업이 시작함

// fun main() {    
//     val time = measureTimeMillis {
//     	   // CoroutineScope 생성
//         // Android에서는 event loop를 제공하기 때문에 runBlocking()을 사용하지 않음
//         runBlocking {
//             println("Weather forecast")
//             printForecast()
//             printTemperature()
//         }
//     }
//     println("Execution time: ${time / 1000.0} seconds")
// }

// printForecast()가 끝난 이후에 printTemperature()가 실행됨 -> 약 2초


// ----------------------------------------------------
// asynchronous
// 이전 작업이 끝나지 않아도 다음 작업이 시작함(launch, async 등)

// fun main() {
//     val time = measureTimeMillis {
//         runBlocking {
//             println("Weather forecast")
//             launch {
//                 printForecast()
//             }
//             // 비동기 작업의 반환 값이 있는 경우 사용
//             // promise와 유사한 Deferred를 반환 받아, .await()로 값에 접근
//             val temperature: Deferred<String> = async {
//                 getTemperature()
//             }
//             println(temperature.await())
//             println("Have a good day!")
//         }
//     }
//     println("Execution time: ${time / 1000.0} seconds")
// }

// printForecast()가 실행된 이후에 printTemperature()가 실행됨 -> 약 1초


// ----------------------------------------------------
// Parallel Decomposition
// 병렬로 처리될 수 있는 작은 작업들을 병렬 처리 후,
// 모두 완료되면 결과들을 합쳐서 반환하는 것
// 실제로는 비동기 작업이지만,
// 모든 비동기 작업이 끝나야 결과가 반환되기 때문에 동기 작업처럼 보임

// // local scope를 생성
// suspend fun getWeatherReport() = coroutineScope {
//     val forecast = async { getForecast() }
//     val temperature = async { getTemperature() }
//     "${forecast.await()} ${temperature.await()}"
// }

// fun main() {
//     runBlocking {
//         println("Weather forecast")
//         println(getWeatherReport())
//         println("Have a good day!")
//     }
// }


// ----------------------------------------------------
// Exceptions and cancellation
// coroutine은 위계가 있음
// 자식 coroutine 중 하나가 예외 발생
//   -> 부모 coroutine으로 전파됨
//     -> 부모와 모든 자식 coroutine 종료
// 따라서 예외는 자식 coroutine에서, producer(async())가 처리하는 게 좋음
// 그러나 cancel의 경우 다른 coroutine에 영향을 주지 않음

// suspend fun getWeatherReport() = coroutineScope {
//     val forecast = async { getForecast() }
//     val temperature = async { getTemperature() }
//     val error = async {
//         try {
//             getError()
//         } catch (e: AssertionError) {
//             println("Caught exception $e")
//             "{ No temperature found }"
//         }
//     }
    
//     delay(200)
//     temperature.cancel()
    
//     "${forecast.await()} ${error.await()}"
// }

// // getWeatherReport()를 실행하는 coroutine이 부모
// // getForecast(), getError()를 실행하는 각각의 coroutine이 자식들
// fun main() {
//     runBlocking {
//         println("Weather forecast")
//         println(getWeatherReport())
//         println("Have a good day!")
//     }
// }


// ----------------------------------------------------
// Job
// launch()를 실행하면 반환되는 값, async()의 반환값인 Deffered도 Job의 일종
// handle, reference를 가지고 있어서 coroutine의 lifecycle을 관리할 수 있음
// 위계질서를 가져서 부모 Job이 cancel -> 자식 Job들도 cancel
// 그러나 자식 Job의 cancel은 부모에 영향을 주지 않음(exception은 영향을 줌)


// ----------------------------------------------------
// CoroutineScope
// runBlocking(), coroutineScope{}으로 생성할 수 있음
// Android에서는 Activity의 lifecycleScope나 ViewModel의 viewModelScope 사용

// CoroutineContext
// coroutine이 실행될 context에 대한 정보
// - name(구분자)
// - job(coroutine의 lifecycle 관리)
// - dispatcher(적절한 스레드에 작업을 dispatch)
// - exception handler(coroutine에서 발생한 예외 처리)
// 등을 포함하여 다음과 같이 정의될 수 있음
//   Job() + Dispatchers.Main + exceptionHandler
// 자식 coroutine의 경우 부모의 CoroutineContext를 상속받고, Job은 새로 생성함
// launch()나 async() 사용 시 CoroutineContext의 인자를 넘길 수 있음
//   scope.launch(Dispatcher.Default) { ... }

// Dispatcher
// 실행할 스레드를 결정
// 앱이 실행되면 main thread(UI thread)가 생성됨
// main thread에서 특정 작업을 오래 수행할 경우, UI가 실시간으로 업데이트되지 못함
// 이를 방지하기 위해 시간이 오래 걸리는 작업은 main thread가 아닌 다른 곳에서 실행되어야 함
// built-in dispatchers
// - Dispatchers.Main(기본값, UI, 상호작용, 빠르게 수행되는 작업 등)
// - Dispatchers.IO(disk 혹은 network I/O)
// - Dispatchers.Default(일반적으로 launch()와 async() 호출에 사용, bitmap 이미지 처리 등에도 사용)

// 일반적으로 함수들은 blocking -> 작업이 끝나기 전까지 스레드를 양보하지 않음
// non-blocking -> 특정 조건이 충족되면 스레드를 양보
// 따라서 비동기 함수로 non-blocking을 수행할 수 있음

fun main() {
    runBlocking {
        println("${Thread.currentThread().name} - runBlocking function")
                launch {
            println("${Thread.currentThread().name} - launch function")
            withContext(Dispatchers.Default) {
                println("${Thread.currentThread().name} - withContext function")
                delay(1000)
                println("10 results found.")
            }
            println("${Thread.currentThread().name} - end of launch function")
        }
        println("Loading...")
    }
}