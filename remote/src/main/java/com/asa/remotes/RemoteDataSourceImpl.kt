package com.asa.remotes

import android.util.Log
import com.asa.data.sources.RemoteDataSource
import com.asa.domain.AddCourseUseCase
import com.asa.domain.LogInUseCase
import com.asa.domain.ReadingTimeSetUpUseCase
import com.asa.domain.RegisterUseCase
import com.asa.domain.UploadReadingTimetableUseCase
import com.asa.domain.model.CourseDomain
import com.asa.domain.model.ReadingTimePreferencesDomain
import com.asa.domain.model.ReadingTimetableDomain
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
import java.util.*
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

    override fun uploadReadingTimetable(params: UploadReadingTimetableUseCase.Params): Completable {

        return Completable.create { emitter ->

            val user = firebaseAuth.currentUser
            if (user == null) {
                emitter.onError(Throwable("Invalid user"))

                return@create
            }

            firestore
                .collection(SEMESTER_COLLECTION_PATH)
                .document(user.uid)
                .collection(USER_READING_TIMETABLE_PATH)
                .document(READING_TIMETABLE_PATH)
                .set(params)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        emitter.onComplete()
                    } else {

                        emitter.onError(task.exception ?: Throwable("Error uploading reading timetable"))
                    }
                }
        }
    }

    override fun getReadingPreferences(): Single<ReadingTimePreferencesDomain> {

        return Single.create { emitter ->

            val user = firebaseAuth.currentUser
            if (user == null) {
                emitter.onError(Throwable("Invalid user"))

                return@create
            }
            firestore.collection(USERS_READING_TIME_COLLECTION_PATH).document(user.uid)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {

                        val readingPref = task.result?.toObject(ReadingTimePreferencesDomain::class.java)

                        if (readingPref == null) {
                            emitter.onError(Throwable("No reading preference found"))
                        } else {
                            emitter.onSuccess(readingPref)
                        }
                    } else {
                        emitter.onError(task.exception ?: Throwable("Error fetching reading preference"))
                    }
                }
        }
    }

    override fun getReadingTimetable(): Single<List<ReadingTimetableDomain>> {
        val dayOfTheWeek = arrayListOf(
            "Monday",
            "Tuesday",
            "Wednesday",
            "Thursday",
            "Friday",
            "Saturday",
            "Sunday"
        )
        val listOfReadDays = mutableListOf<ReadingTimetableDomain>()
        return Single.create { emitter ->
            val user = firebaseAuth.currentUser
            if (user == null) {
                emitter.onError(Throwable("Invalid User"))
                return@create
            }

            Log.d("reading", "about to fetch list")


            firestore.collection(SEMESTER_COLLECTION_PATH)
                .document(user.uid)
                .collection(USER_READING_TIMETABLE_PATH)
                .document(READING_TIMETABLE_PATH)
                .get().addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("reading", "succesful")

                        val documentSnapShot = task.result

                        if (documentSnapShot != null) {
                            val result = documentSnapShot.toObject(UploadReadingTimetableUseCase.Params::class.java)?.readingTimetable
                            Log.d("reading", "List of sorted day is $result")

                            if (!result.isNullOrEmpty()) {

                                for (day in dayOfTheWeek) {
                                    val course = result.filter { it.day == day }
                                    if (!course.isNullOrEmpty()) listOfReadDays.add(ReadingTimetableDomain(day, course))
                                }
                            }
                        }

                        emitter.onSuccess(listOfReadDays)
                    } else {
                        emitter.onError(Throwable("Error fetching Reading Timetable"))
                    }
                }

        }
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
            val courseCreditUnit = params.course.creditUnit.toLong()

            firestore.runBatch { batch ->

                batch.set(addCourseRef, params.course)
                batch.update(semesterRef, "noOfCoursesOffered", FieldValue.increment(1))
                batch.update(semesterRef, "totalCreditUnit", FieldValue.increment(courseCreditUnit))

            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    emitter.onComplete()
                } else {
                    emitter.onError(task.exception ?: Throwable("Error adding courses"))
                }
            }
        }
    }

    private fun getDayOfTheWeek(): String {
        val dayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
        val daysOfTheWeek = arrayListOf(
            "Sunday",
            "Monday",
            "Tuesday",
            "Wednesday",
            "Thursday",
            "Friday",
            "Saturday"
        )
        return daysOfTheWeek[dayOfWeek - 1]
    }

    override fun getCoursesForToday(): Single<List<CourseDomain>> {
        val day = getDayOfTheWeek()

        return Single.create { emitter ->
            val user = firebaseAuth.currentUser
            if (user == null) {
                emitter.onError(Throwable("Invalid user"))
                return@create
            }

            firestore.collection(SEMESTER_COLLECTION_PATH)
                .document(user.uid)
                .collection(USER_COURSES_COLLECTION_PATH)
                .whereArrayContains("lectureDayOfWeek", day)
                .get()
                .addOnCompleteListener { task ->

                    if (!emitter.isDisposed) {

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
                            emitter.onError(task.exception ?: Throwable("Error fetching courses"))
                        }
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

    companion object {
        private const val USERS_COLLECTION_PATH = "users"
        private const val USERS_READING_TIME_COLLECTION_PATH = "users_reading_time"
        private const val SEMESTER_COLLECTION_PATH = "semester_information"
        private const val USER_COURSES_COLLECTION_PATH = "user_courses"
        private const val USER_READING_TIMETABLE_PATH = "user_reading_timetable"
        private const val READING_TIMETABLE_PATH = "timetable"
    }
}