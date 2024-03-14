// -----------------------------------
// 1. Single Responsibility Principle / 단일 책임 원칙
//    : 클래스가 상호작용 하는 대상이 단일 대상이어야 함

// 아래 클래스는 상호작용하는 대상이 둘임 (X)

class User(val name: String) {
    fun login() {}    // 유저 인증 개발자
    fun reportToAnalytics() {}    // 유저 통계 개발자
}


// 상호작용하는 대상에 따라 클래스를 분리함 (O)

class User(val name: String)

class AuthManager() {
    fun login(user: User) {}
}

class AnalyticsReporter() {
    fun reportToAnalytics(user: User) {}
}





// -----------------------------------
// 2. Open-Closed Principle / 개방-폐쇄 원칙
//    : 개체는 기능 확장은 가능해야 하고, 개체 자체의 변경은 불가능해야 함
//    : 즉, 개체와 개체의 기능 구현을 분리하고, 개체가 기능을 직접 참조하지 않아야 함

// 아래 Controller는 DatabaseRepository를 직접 사용하여
// Database를 Network로 변경할 경우 코드 변경이 필요함 (X)

class Controller(val databaseRepository: DatabaseRepository) {
    fun doSomething() {
        databaseRepository.getUserAll()
    }
}

class DatabaseRepository {
    fun getUserAll() {}
}

class NetworkRepository {
    fun getUserAll() {}
}


// 인터페이스(혹은 추상 클래스)를 추가하여 개체와 개체의 기능 구현을 분리 (O)

class Controller(val repository: Repository) {
    fun doSomething() {
        repository.getUserAll()
    }
}

interface Repository {
    fun getUserAll()
}

class DatabaseRepository: Repository {
    override fun getUserAll() {}
}

class NetworkRepository: Repository {
    override fun getUserAll() {}
}





// -----------------------------------
// 3. Liskov Substitution Principle / 리스코프 치환 원칙
//    : 하위 클래스 객체는 상위 클래스 객체의 역할을 그대로 수행할 수 있어야 함

// Repository에 clearCache를 정의하였지만, 
// 이를 구현한 NonCacheRepository는 이를 수행하지 못하고 예외를 발생시킴 (X)

interface DataRepository {
    fun getData(): String
    fun cleanCache()
}

class CacheReposiroty: DataRepository {
    override fun getData(): String = "cached data"
    override fun cleanCache() {
        println("clear cache")
    }
}

class NonCacheReposiroty: DataRepository {
    override fun getData(): String = "non cached data"
    override fun cleanCache() {
        throw UnsupportedOperationException("I don't have cache")
    }
}

class CacheManager {
    fun cleanCache(dataRepository: DataReposiroty) {
        dataRepository.cleanCache()
    }
}


// 모든 하위 클래스가 포함된 모든 작업을 수행하도록 인터페이스를 추가하여 작업을 분리 (O)

interface DataRepository {
    fun getData(): String
}

interface Cache {
    fun cleanCache()
}

class CacheReposiroty: DataRepository, Cache {
    override fun getData(): String = "cached data"
    override fun cleanCache() {
        println("clear cache")
    }
}

class NonCacheReposiroty: DataRepository {
    override fun getData(): String = "non cached data"
}

class CacheManager {
    fun cleanCache(cache: Cache) {
        cache.cleanCache()
    }
}





// -----------------------------------
// 4. Interface Segregation Principle / 인터페이스 분리 원칙
//    : 자신이 이용하지 않는 메소드에는 의존하지 않아야 함

// fetchProduct가 변경되었을 때, 그 메소드와 관련은 없지만
// 메소드를 포함한 인터페이스를 참조하는 ViewModel에 영향이 없는지 확인해야 함 (X)

interface NetworkClient {
    fun fetchUser()
    fun fetchProduct()
}

class ViewModel(val networkClient: NetworkClient) {
    fun fetchUser() {
        networkClient.fetchUser()
    }
}


// 인터페이스를 분리하여 ViewModel이 필요한 메소드가 포함된 인터페이스만 참조 (O)

interface UserFetcher {
    fun fetchUser()
}

interface ProductFetcher {
    fun fetchProduct()
}

class ViewModel(val userFetcher: UserFetcher) {
    fun fetchUser() {
        userFetcher.fetchUser()
    }
}





// -----------------------------------
// 5. Dependency Inversion Principle / 의존성 역전 원칙
//    : 변경이 많은 부분이 변경이 적은 부분을 의존하도록 반전

// Usecase가 Repository의 구현체를 직접 참조하여
// Repository의 구현이 바뀔 때마다 Usecase에 영향이 없는지 확인해야 함 (X)
// 의존 방향 : Usecase → Repository
// 제어 방향 : Usecase → Repository

class Repository() {
    fun loadData() = "data"
}

class GetDataUseCase(val repository: Repository) {
    fun execute() {
        val data = repository.loadData()
    }
}


// Repository 추상체를 추가하여, 구현체가 변경되어도 Usecase에는 영향이 없음 (O)
// 의존 방향 : Usecase → Repository Interface, Repository → Repository Interface
// 제어 방향 : Usecase → Repository

interface Repository {
    fun loadData(): String
}

class RepositoryImpl: Repository {
    override fun loadData() = "data"
}

class GetDataUseCase(val repository: Repository) {
    fun execute() {
        val data = repository.loadData()
    }
}