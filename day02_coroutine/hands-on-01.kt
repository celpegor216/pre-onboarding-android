import kotlinx.coroutines.*

suspend fun loadUser(): String {
    delay(1000)
    return "user"
}

suspend fun loadProduct(): String {
    delay(500)
    return "product"
}

// // -----------------------------------
// // #1. 하나의 스레드에서 여러 작업을 순서대로 수행
// fun main() = runBlocking {
//     println("main started")
    
//     val user = loadUser()
//     println("user: $user")
    
//     val product = loadProduct()
//     println("product: $product")
    
//     println("main finished")
// }


// // -----------------------------------
// // #2. 하나의 스레드에서 여러 작업을 동시에 수행
// //     -> 작업 별로 코루틴을 생성(launch)
// fun main() = runBlocking {
//     println("main started")
    
//     launch {
//         val user = loadUser()
//         println("user: $user")
//     }
    
//     launch {
//         val product = loadProduct()
//         println("product: $product")
//     }
    
//     println("main finished")
// }


// // -----------------------------------
// // #3. 하나의 스레드에서 여러 작업을 동시에 수행하고 기다리기
// //     -> 작업 별로 코루틴을 생성(async), 메인 코루틴을 작업이 완료될 때까지 중단(await)
// fun main() = runBlocking {
//     println("main started")
    
//     val user = async { loadUser() }
//     println("user loading requested")
    
//     val product = async { loadProduct() }
//     println("product loading requested")
    
//     println("user: ${user.await()}")
//     println("product: ${product.await()}")
//     println("main finished")
// }


// // -----------------------------------
// // #4. 두 개 이상의 스레드에서 여러 작업을 순서대로 수행
// //     -> 스레드 생성(withContext)
// fun main() = runBlocking {
//     println("main started")
    
//     val user = withContext(Dispatchers.IO) { loadUser() }
//     println("user: $user")
    
//     val product = withContext(Dispatchers.IO) { loadProduct() }
//     println("product: $product")
    
//     println("main finished")
// }


// -----------------------------------
// #5. 두 개 이상의 스레드에서 여러 작업을 동시에 수행
//     -> 스레드 생성(launch, async)
fun main() = runBlocking {
    println("main started")
    
    val user = async(Dispatchers.IO) { loadUser() }
    println("user loading requested")
    
    val product = async(Dispatchers.IO) { loadProduct() }
    println("product loading requested")
    
    println("user: ${user.await()}")
    println("product: ${product.await()}")
    println("main finished")
}