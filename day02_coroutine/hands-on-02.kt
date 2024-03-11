import kotlinx.coroutines.*

suspend fun loadUser(): String {
    delay(1000)
    return "user"
}

suspend fun loadException(): String {
    delay(500)
    throw Exception("error")
}

// launch로 두 가지 API를 받아올 때의 예외 처리


// // -----------------------------------
// // #1. try-catch를 사용
// //     : async > launch > scope로 예외가 전파됨 (X)

// fun main() = runBlocking {
//     // SupervisorJob
//     // 코루틴의 예외 전파 범위를 제어, 그러나 예외로 인해 앱은 종료됨
    
//     // Handler
//     // 예외를 처리하여 앱이 종료되지 않게 함
    
//     val handler = CoroutineExceptionHandler { _, exception -> 
//     	println("handler: Caught exception: $exception")
//     }
//     val scope = CoroutineScope(Dispatchers.Default + SupervisorJob() + handler)
    
//     scope.launch {
//         val user = async { loadUser() }
//         val product = async { loadException() }
        
//         try {
//             println("user: ${user.await()} / product: ${product.await()}")
//         } catch (exception: Exception) {
//             println("exception: $exception")
//         }
//     }
    
//     Thread.sleep(2000)
// }


// // -----------------------------------
// // #2. 새로운 CoroutineScope 생성
// //     : 예외 전파는 중단되지만 launch와 async의 부모 자식 관계가 끊어짐 (X)

// fun main() = runBlocking {
//     val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())
    
//     scope.launch {
//         val scope2 = CoroutineScope(Dispatchers.Default + SupervisorJob())
        
//         val user = scope2.async { loadUser() }
//         val product = scope2.async { loadException() }
        
//         try {
//             println("user: ${user.await()} / product: ${product.await()}")
//         } catch (exception: Exception) {
//             println("exception: $exception")
//         }
//     }
    
//     Thread.sleep(2000)
// }


// // -----------------------------------
// // #3. coroutineScope 사용
// //     : 부모 자식 관계 유지하면서 새로운 코루틴 생성
// //     : 그러나 중단 함수라서 제어권이 내부 블록으로 넘어감 (X)

// fun main() = runBlocking {
//     val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())
    
//     scope.launch {
        
//         coroutineScope {
//             val user = async { loadUser() }
//             val product = async { loadException() }

//             try {
//                 println("user: ${user.await()} / product: ${product.await()}")
//             } catch (exception: Exception) {
//                 println("exception: $exception")
//             }
//         }
//     }
    
//     Thread.sleep(2000)
// }


// -----------------------------------
// #4. supervisorScope 사용
//     : 부모 자식 관계 유지하면서 새로운 SupervisorJob 코루틴 생성
//     : async의 예외가 try-catch에서 처리되어 다른 코루틴에 영향을 주지 않음 (O)

fun main() = runBlocking {
    val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())
    
    scope.launch {
        
        supervisorScope {
            val user = async { loadUser() }
            val product = async { loadException() }

            try {
                println("user: ${user.await()} / product: ${product.await()}")
            } catch (exception: Exception) {
                println("exception: $exception")
            }
        }
    }
    
    Thread.sleep(2000)
}