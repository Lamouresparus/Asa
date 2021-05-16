package com.asa.remotes

import com.asa.data.sources.RemoteDataSource
import com.asa.domain.AddCourseUseCase
import com.asa.domain.LogInUseCase
import com.asa.domain.ReadingTimeSetUpUseCase
import com.asa.domain.RegisterUseCase
import com.asa.domain.model.CourseDomain
import com.asa.domain.model.SemesterDomain
import com.asa.domain.model.UserDomain
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import durdinapps.rxfirebase2.RxFirebaseAuth
import durdinapps.rxfirebase2.RxFirestore
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.SingleEmitter
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
        private val firebaseAuth: FirebaseAuth,
        private val firestore: FirebaseFirestore,
) : RemoteDataSource {

    override fun login(params: LogInUseCase.Params): Single<Pair<UserDomain, SemesterDomain>> {
        return RxFirebaseAuth
                .signInWithEmailAndPassword(firebaseAuth, params.email, params.password)
                .flatMapSingle {
                    val user = it.user ?: throw Throwable("user does not exist")
                    Single.zip(getUser(user.uid), getSemesterInformation(user.uid), { _user, semester ->
                        Pair(_user, semester)
                    })
                }

//        return Single.create { emitter ->
//            firebaseAuth
//                .signInWithEmailAndPassword(params.email, params.password)
//                .addOnCompleteListener { task ->
//
//                    if (task.isSuccessful) {
//                        val fireBaseUser = task.result?.user
//                        val email = fireBaseUser?.email
//                        if (fireBaseUser == null || email == null) {
//                            emitter.onError(Throwable("user does not exist"))
//                            return@addOnCompleteListener
//                        }
//
//                        firestore
//                            .collection(USERS_COLLECTION_PATH)
//                            .document(fireBaseUser.uid)
//                            .get()
//                            .addOnCompleteListener {
//                                if (it.isSuccessful) {
//                                    val user = it.result?.toObject(UserDomain::class.java)
//
//                                    if (user != null) {
//                                        if (user.userType == params.userType) {
//                                            emitter.onSuccess(user)
//                                        } else {
//                                            emitter.onError(Throwable("Invalid login details"))
//                                        }
//                                    } else emitter.onError(
//                                        it.exception
//                                            ?: Throwable("Error logging in")
//                                    )
//                                } else {
//                                    emitter.onError(
//                                        it.exception
//                                            ?: Throwable("Error logging in")
//                                    )
//                                }
//                            }
//                    } else {
//                        emitter.onError(task.exception ?: Throwable("Error logging in"))
//                    }
//
//                }
//        }
    }

    private fun getUser(userId: String): Single<UserDomain> {
        val docRef = firestore.collection(USERS_COLLECTION_PATH).document(userId)
        return RxFirestore.getDocument(docRef, UserDomain::class.java).toSingle()
    }

    private fun getSemesterInformation(userId: String): Single<SemesterDomain> {
        val semesterDocRef =
                firestore.collection(SEMESTER_COLLECTION_PATH).document(userId)
        return RxFirestore.getDocument(semesterDocRef, SemesterDomain::class.java).toSingle()
    }


    override fun startNewSemester(userId: String): Completable {
        val semesterDocRef =
                firestore.collection(SEMESTER_COLLECTION_PATH).document(userId)
        return RxFirestore.updateDocument(semesterDocRef, "hasSemesterBegun", true)
    }

    override fun register(param: RegisterUseCase.Params): Single<UserDomain> {
        return RxFirebaseAuth
                .createUserWithEmailAndPassword(firebaseAuth, param.email, param.password)
                .flatMapSingle {
                    val user = it.user ?: throw Throwable("user does not exist")

                    val userObject = when (param) {
                        is RegisterUseCase.StudentParams -> {
                            UserDomain(
                                    userId = user.uid,
                                    email = param.email,
                                    0,
                                    regNumber = param.studentRegistrationNumber,
                                    firstName = param.firstName,
                                    lastName = param.lastName,
                                    level = param.level,
                                    isRegistrationComplete = false
                            )
                        }
                        is RegisterUseCase.StaffParams -> {
                            UserDomain(
                                    userId = user.uid,
                                    email = param.email,
                                    1,
                                    staffId = param.staffIdentificationNumber
                            )
                        }
                        else -> throw UnsupportedOperationException("Invalid user type")
                    }

                    val userDocRef = firestore.collection(USERS_COLLECTION_PATH).document(user.uid)
                    val semesterDocRef =
                            firestore.collection(SEMESTER_COLLECTION_PATH).document(user.uid)
                    val batches = listOf(
                            firestore.batch().set(userDocRef, userObject),
                            firestore.batch().set(semesterDocRef, SemesterDomain())
                    )
                    RxFirestore.atomicOperation(batches).toSingle { user }
                }.flatMap { getUser(it.uid) }

//        return Single.create { emitter ->
//
//            firebaseAuth
//                .createUserWithEmailAndPassword(param.email, param.password)
//                .addOnCompleteListener { task ->
//
//                    if (task.isSuccessful) {
//                        val fireBaseUser = task.result?.user
//                        val email = fireBaseUser?.email
//                        if (fireBaseUser == null || email == null) {
//                            emitter.onError(Throwable("user does not exist"))
//                            return@addOnCompleteListener
//                        }
//
//                        firestore
//                            .collection(USERS_COLLECTION_PATH)
//                            .document(fireBaseUser.uid)
//                            .set(param)
//                            .addOnCompleteListener { dbTask ->
//
//                                if (dbTask.isSuccessful) {
//
//                                    val user = when (param) {
//                                        is RegisterUseCase.StudentParams -> {
//                                            UserDomain(
//                                                fireBaseUser.uid,
//                                                email,
//                                                0,
//                                                regNumber = param.studentRegistrationNumber,
//                                                firstName = param.firstName,
//                                                lastName = param.lastName
//                                            )
//                                        }
//                                        is RegisterUseCase.StaffParams -> {
//                                            UserDomain(
//                                                fireBaseUser.uid,
//                                                email,
//                                                1,
//                                                staffId = param.staffIdentificationNumber
//                                            )
//                                        }
//                                        else -> throw UnsupportedOperationException("Invalid user type")
//                                    }
//
//                                    emitter.onSuccess(user)
//                                } else {
//                                    emitter.onError(
//                                        dbTask.exception
//                                            ?: Throwable("Error creating user")
//                                    )
//                                }
//                            }
//
//                    } else {
//                        emitter.onError(task.exception ?: Throwable("Error login in"))
//                    }
//
//                }
//
//        }
    }


    override fun saveReadingTime(params: ReadingTimeSetUpUseCase.Params): Completable {
        return Completable.create { emitter ->
            val user = firebaseAuth.currentUser
            if (user == null) {
                emitter.onError(Throwable("user not found"))
                return@create
            }

            firestore
                    .collection(USERS_READING_TIME_COLLECTION_PATH)
                    .document(user.uid)
                    .set(params)
                    .addOnCompleteListener { dbTask ->

                        if (dbTask.isSuccessful) {

                            firestore
                                    .collection(USERS_COLLECTION_PATH)
                                    .document(user.uid)
                                    .update("registrationComplete", true)
                                    .addOnCompleteListener {
                                        if (it.isSuccessful) {
                                            emitter.onComplete()

                                        } else {
                                            emitter.onError(
                                                    it.exception
                                                            ?: Throwable("Error creating user")
                                            )
                                        }
                                    }

                        } else {
                            emitter.onError(
                                    dbTask.exception
                                            ?: Throwable("Error creating user")
                            )
                        }
                    }

        }
    }

    override fun logOut(): Completable {
        return Completable.fromAction {

        }
    }

    override fun saveCourses(params: AddCourseUseCase.Params): Completable {
        return Completable.create { emitter ->

            val user = firebaseAuth.currentUser
            if (user == null) {
                emitter.onError(Throwable("Invalid user"))
                return@create
            }

            val addCourseRef = firestore
                    .collection(SEMESTER_COLLECTION_PATH)
                    .document(user.uid)
                    .collection(USER_COURSES_COLLECTION_PATH)
                    .document(params.course.courseCode)

            val semesterRef = firestore
                    .collection(SEMESTER_COLLECTION_PATH)
                    .document(user.uid)

            firestore.runBatch { batch ->

                batch.set(addCourseRef, params.course)
                batch.update(semesterRef, "noOfCoursesOffered", FieldValue.increment(1))

            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    emitter.onComplete()
                } else {
                    emitter.onError(task.exception ?: Throwable("Error adding courses"))
                }
            }
        }
    }


    override fun getCoursesForToday(): Single<List<CourseDomain>> {
        return Single.create { emitter ->
            val user = firebaseAuth.currentUser
            if (user == null) {
                emitter.onError(Throwable("Invalid user"))
                return@create
            }

            firestore.collection(USER_COURSES_COLLECTION_PATH)
                    .document(user.uid)
                    .get()
                    .addOnCompleteListener { task ->

                        if (task.isSuccessful) {

                            val dataWrapper =
                                    task.result?.toObject(CourseAndLectureDaysWrapper::class.java)

                            if (dataWrapper != null) {

                                emitter.onSuccess(dataWrapper.course)
                            } else {
                                emitter.onError(
                                        task.exception
                                                ?: Throwable("Error fetching courses")
                                )
                            }

                        } else {
                            emitter.onError(task.exception ?: Throwable("Error fetching courses"))
                        }

                    }

        }
    }


    override fun getUserCourses(): Single<List<CourseDomain>> {

        return Single.create { emitter ->

            val user = firebaseAuth.currentUser
            if (user == null) {
                emitter.onError(Throwable("Invalid user"))
                return@create
            }

            getCourses(user.uid, emitter)
        }
    }

    private fun getCourses(userId: String, emitter: SingleEmitter<List<CourseDomain>>) {
        firestore
                .collection(SEMESTER_COLLECTION_PATH)
                .document(userId)
                .collection(USER_COURSES_COLLECTION_PATH)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {

                        val courses = task.result?.documents?.map {
                            it.toObject(CourseDomain::class.java)!!
                        }

                        if (courses.isNullOrEmpty()) {
                            emitter.onError(Throwable("No course found"))
                        } else {
                            emitter.onSuccess(courses)
                        }

                    } else {
                        emitter.onError(task.exception ?: Throwable("Error adding courses"))
                    }
                }

    }

    data class CourseAndLectureDaysWrapper(
            val course: List<CourseDomain>,
    )

    companion object {
        private const val USERS_COLLECTION_PATH = "users"
        private const val USERS_READING_TIME_COLLECTION_PATH = "users_reading_time"
        private const val SEMESTER_COLLECTION_PATH = "semester_information"
        private const val USER_COURSES_COLLECTION_PATH = "user_courses"
    }
}