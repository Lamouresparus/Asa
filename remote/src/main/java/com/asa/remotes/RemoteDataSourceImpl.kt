package com.asa.remotes

import com.asa.data.sources.RemoteDataSource
import com.asa.domain.LogInUseCase
import com.asa.domain.RegisterUseCase
import com.asa.domain.model.UserDomain
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(private val firebaseAuth: FirebaseAuth,
                                               private val firestore: FirebaseFirestore) : RemoteDataSource {

    override fun login(params: LogInUseCase.Params): Single<UserDomain> {
        return Single.create { emitter ->
            firebaseAuth
                    .signInWithEmailAndPassword(params.email, params.password)
                    .addOnCompleteListener { task ->

                        if (task.isSuccessful) {
                            val fireBaseUser = task.result?.user
                            val email = fireBaseUser?.email
                            if (fireBaseUser == null || email == null) {
                                emitter.onError(Throwable("user does not exist"))
                                return@addOnCompleteListener
                            }

                            firestore
                                    .collection(USERS_COLLECTION_PATH)
                                    .document(fireBaseUser.uid)
                                    .get()
                                    .addOnCompleteListener {
                                        if (it.isSuccessful) {
                                            val user = it.result?.toObject(UserDomain::class.java)

                                            if (user != null) emitter.onSuccess(user)
                                            else emitter.onError(it.exception
                                                    ?: Throwable("Error loggin in"))


                                        } else {
                                            emitter.onError(it.exception
                                                    ?: Throwable("Error loggin in"))
                                        }
                                    }
                        } else {
                            emitter.onError(task.exception ?: Throwable("Error loggin in"))
                        }

                    }
        }
    }

    override fun register(param: RegisterUseCase.Params): Single<UserDomain> {
        return Single.create { emitter ->

            firebaseAuth
                    .createUserWithEmailAndPassword(param.email, param.password)
                    .addOnCompleteListener { task ->

                        if (task.isSuccessful) {
                            val fireBaseUser = task.result?.user
                            val email = fireBaseUser?.email
                            if (fireBaseUser == null || email == null) {
                                emitter.onError(Throwable("user does not exist"))
                                return@addOnCompleteListener
                            }

                            firestore
                                    .collection(USERS_COLLECTION_PATH)
                                    .document(fireBaseUser.uid)
                                    .set(param)
                                    .addOnCompleteListener { dbTask ->

                                        if (dbTask.isSuccessful) {

                                            val user = when (param) {
                                                is RegisterUseCase.StudentParams -> {
                                                    UserDomain(fireBaseUser.uid, email, 0, regNumber = param.studentRegistrationNumber)
                                                }
                                                is RegisterUseCase.StaffParams -> {
                                                    UserDomain(fireBaseUser.uid, email, 1, staffId = param.staffIdentificationNumber)
                                                }
                                                else -> throw UnsupportedOperationException("Invalid user type")
                                            }

                                            emitter.onSuccess(user)
                                        } else {
                                            emitter.onError(dbTask.exception
                                                    ?: Throwable("Error creating user"))
                                        }
                                    }

                        } else {
                            emitter.onError(task.exception ?: Throwable("Error login in"))
                        }

                    }

        }
    }

    override fun logOut(): Completable {
        return Completable.fromAction {

        }
    }

    companion object {
        private const val USERS_COLLECTION_PATH = "users"
    }
}