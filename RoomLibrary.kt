1. Room 구현
    1. 공식문서에서 room검색 
    => https://developer.android.com/training/data-storage/room?hl=ko#usage

    2. dependency에 넣기 ksp까지 넣기(gradle(Module)에서 한다)
        def room_version = "2.5.0"

            implementation "androidx.room:room-runtime:$room_version"
            annotationProcessor "androidx.room:room-compiler:$room_version"

            // To use Kotlin annotation processing tool (kapt)
            kapt "androidx.room:room-compiler:$room_version"
            // To use Kotlin Symbol Processing (KSP)
            ksp "androidx.room:room-compiler:$room_version"

        kotlin은 kapt를 삭제

        build.gradle(project) 에 버전맞추기(plugin에 넣으면 된다)

            id 'com.android.application' version '8.0.2' apply false
            id 'com.android.library' version '8.0.2' apply false
            id 'org.jetbrains.kotlin.android' version '1.8.0' apply false
            id 'com.google.devtools.ksp' version '1.8.0-1.0.8' apply false

            넣었으면 위에 영어(Sync Now) 누르기(error가 뜨면? 일단 밑에꺼 까지 해보자)

        build.gradle(module) 에 composeOpttions 버전바꾸기
            
            kotlinCompilerExtensionVersion '1.4.1'  

            넣었으면 위에 영어 누르기(error가 뜨면 강사님부르기)

    ※중간중간 실행을 해서 에러가 나는지 확인해봅시다!


    3. DB파일들(?)

        1. Data파일
            java밑 파일 우클릭 new, package 이름: data


        2. User data Class 만들기
            data파일 우클릭 kotlin누르고 data Class만들기 User
            공홈에 Room (맨처음에 들어온곳) 내리다보면 데이터항목 밑의 내용 긁어서 수정
            @Entity
            data class User(
                //이부분 수정한거!!
                @PrimaryKey(autoGenerate = true) val uid: Int = 0,
                //이부분 수정한거!!
                @ColumnInfo("name") val name: String,
            )

        3. UserDAO interface 만들기
            data파일 우클릭 kotliln 누르고 interface로 UserDAO
            이것도 공홈에 DAO 예제 코드 복붙후 수정
            @Dao
            interface UserDAO {
                @Query("SELECT * FROM user")
                fun getAll(): List<User>

                @Query("SELECT * FROM user WHERE uid IN (:userIds)")
                fun loadAllByIds(userIds: IntArray): List<User>
                //이부분 수정한거!!
                @Query("SELECT * FROM user WHERE name LIKE :name LIMIT 1")
                fun findByName(name: String): User

                @Insert
                fun insertAll(vararg users: User)

                @Delete
                fun delete(user: User)
            }

        4. AppDatabase class 만들기
            data파일 우클릭 kotlin누르고 class만들기 AppDatabase
            공홈에 있는 데이터베이스 밑의 예시코드 복붙후 수정

            //entities = [원하는것::class]
            @Database(entities = [User::class], version = 1)
            abstract class AppDatabase : RoomDatabase() {
                //이부분 수정한거!! UserDao임 원래!!
                abstract fun userDao(): UserDAO
            }



2. 사용

    1. name변수 위에 넣기(var name by remember { mutableStateOf("") })
        val context = LocalContext.current
        val db = Room.databaseBuilder(
            context,
            AppDatabase::class.java, "contacts.db"
        ).build()


    2. 버튼 onClick에 와서 db.userDao().insertAll()넣기
        Button(onClick = {
                    displayName = name
                    //val newUser User(uid = , name = name)
                    val newUser User(uid = 0, name = name)
                    // insertAll(newUser)
                    db.userDao().insertAll(newUser)
                })


    3. val db에서 Room을 remember{}에 넣기
        val db = remember { 
            Room.databaseBuilder(
                context,
                AppDatabase::class.java, "contacts.db"
            ).build()
        }


    4. db밑에 val scope = rememberCoroutineScope() 넣기
        val db = remember { 
            Room.databaseBuilder(
                context,
                AppDatabase::class.java, "contacts.db"
            ).build()
        }
        val scope = rememberCoroutineScope()

    5. 다시 button onClick으로 가서 newUser밑에 scope.launch{}생성하고 그밑db.어쩌구를 {}에 넣기
        Button(onClick = {
                displayName = name
                val newUser = User(uid = 0, name = name)
                scope.launch(Dispatchers.IO) { db.userDao().insertAll(newUser) }
            })


    App Inspection(Logcat있는곳 오른쪽 끝에 있는거) 또는 View - Tool windows - App Inspection
    이걸로 db를 볼 수 있다.


3. 값 가져오기

    1. User에 간다. 전화번호, 이메일 추가
        결과
        @Entity
        data class User(
            @PrimaryKey(autoGenerate = true) val uid: Int = 0,
            @ColumnInfo("name") val name: String,
            @ColumnInfo("phone") val phone: String? = null,
            @ColumnInfo("email") val email: String? = null,
        )


    2. 다시 메인으로 가서 버튼 onClick에서 User(uid = 0)이거 uid없엔다.
        Button(onClick = {
                    displayName = name
                    val newUser = User(name = name)
                    scope.launch(Dispatchers.IO) { db.userDao().insertAll(newUser) }
                })

    3. AppDatabase에서 version을 2로 바꾼다.(뭔가 새로운게 오면 버전이 바뀌어야 함)
    => https://developer.android.com/training/data-storage/room/migrating-db-versions?hl=ko#manual
        이곳에 수동이전에서 예시코드 두번째 단락을 복붙
        abstract class 밖에 복붙
        버전 1->2가 되므로 변수명과 Migration()을 바꿔준다.
        //수정한거!!
        val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            //이부분 "내부 바뀜!! String이 아니라 TEXT!!"
            database.execSQL("ALTER TABLE User ADD COLUMN phone TEXT")
            database.execSQL("ALTER TABLE User ADD COLUMN email TEXT")
            }
        }

        => 대충 database phone email TABLE을 추가한다 라는뜻

        연습을 위해 age 추가(User와 database 혼자 해보세요!) -> database.execSQL("ALTER TABLE User ADD COLUMN age INTEGER")

    ※중간중간 실행을 해서 에러가 나는지 확인해봅시다!

    4. main으로 와서  .addMigrations(MIGRATION_1_2) 추가(build앞에)
        val db = remember {
            Room.databaseBuilder(
                context,
                AppDatabase::class.java, "contacts.db"
                //이밑에 수정한거!!
            ).addMigrations(MIGRATION_1_2).build()
        }

번외) DB를 초기화 하려면 실행기에서 어플을 꾹누르고 app info에 들어가서 Clear storage를 누른다 => 저장된 파일들 다삭제

만약 버전을 추가하고 싶다

AppDatabase에서 

@Database(entities = [User::class], version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDAO
}
val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE User ADD COLUMN phone TEXT")
        database.execSQL("ALTER TABLE User ADD COLUMN email TEXT")
    }
}
val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE User ADD COLUMN age INTEGER")
    }
}

바꾸고

main에서
val db = remember {
    Room.databaseBuilder(
        context,
        AppDatabase::class.java, "contacts.db"
    ).addMigrations(
        MIGRATION_1_2, MIGRATION_2_3
    ).build()
}
이렇게 버전 업을 추가한다.
