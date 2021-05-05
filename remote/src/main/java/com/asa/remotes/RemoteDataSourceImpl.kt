package com.asa.remotes

import com.asa.data.sources.RemoteDataSource
import com.asa.domain.AddCoursesUseCase
import com.asa.domain.LogInUseCase
import com.asa.domain.model.UserDomain
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(private val firebaseAuth: FirebaseAuth) : RemoteDataSource {

    override fun login(params: LogInUseCase.Params): Single<UserDomain> {
        return Single.create<UserDomain> { emitter ->
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

                            val user = UserDomain(fireBaseUser.uid, email)
                            emitter.onSuccess(user)

                        } else {
                            emitter.onError(task.exception ?: Throwable("Error loggin in"))
                        }

                    }
        }
    }


    override fun saveCourses(params: AddCoursesUseCase.Params): Completable {
        return Completable.create { emitter ->


        }
    }
}