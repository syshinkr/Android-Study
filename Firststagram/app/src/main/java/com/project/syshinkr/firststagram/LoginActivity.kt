package com.project.syshinkr.firststagram

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.*
import com.twitter.sdk.android.core.*
import com.twitter.sdk.android.core.identity.TwitterAuthClient
import kotlinx.android.synthetic.main.activity_login.*
import java.util.*

class
LoginActivity : AppCompatActivity() {
    var auth: FirebaseAuth? = null

    // 구글 로그인 관리
    var googleSignInClient: GoogleSignInClient? = null

    // 페북 로그인 처리 결과 관리
    var callbackManager: CallbackManager? = null

    val GOOGLE_LOGIN_CODE = 9001 // Intent Request ID

    var twitterAuthClient: TwitterAuthClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Twitter.initialize(this)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        var gso =
                GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso) // 구글 세팅
        callbackManager = CallbackManager.Factory.create() // 페이스북 세팅
        twitterAuthClient = TwitterAuthClient() // 트위터 세팅

        google_sign_in_button.setOnClickListener{ googleLogin() }

        facebook_login_button.setOnClickListener { facebookLogin() }

        email_login_button.setOnClickListener{ emailLogin() }

        twitter_login_button.setOnClickListener{ twitterLogin() }
    }

    fun moveMainPage(user: FirebaseUser?) {
        // Signed in
        if (user != null) {
            Toast.makeText(this, getString(R.string.signin_complete), Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    fun googleLogin() {
        progress_bar.visibility = View.VISIBLE
        var signInIntent = googleSignInClient?.signInIntent
        startActivityForResult(signInIntent, GOOGLE_LOGIN_CODE)
    }

    fun facebookLogin() {
        progress_bar.visibility = View.VISIBLE

        LoginManager.getInstance()
                .logInWithReadPermissions(this, Arrays.asList("public_profile", "email"))
        LoginManager.getInstance().registerCallback(callbackManager, object: FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                handleFacebookAccessToken(loginResult.accessToken)
            }

            override fun onCancel() {
                progress_bar.visibility = View.GONE
            }

            override fun onError(error: FacebookException) {
                progress_bar.visibility = View.GONE
            }
        })
    }

    fun twitterLogin() {
        progress_bar.visibility = View.VISIBLE
        twitterAuthClient?.authorize(this, object: Callback<TwitterSession>() {
            override fun success(result: Result<TwitterSession>?) {
                val credential = TwitterAuthProvider.getCredential(
                        result?.data?.authToken?.token!!,
                        result?.data?.authToken?.secret!!)
                println(result?.data?.authToken?.token!!)
                println(result?.data?.authToken?.secret!!)
                auth?.signInWithCredential(credential)?.addOnCompleteListener{ task ->
                    progress_bar.visibility = View.GONE
                    // 다음 페이지 이동
                    if(task.isSuccessful) {
                        moveMainPage(auth?.currentUser)
                    }
                }
            }

            override fun failure(exception: TwitterException?) {
                println(exception.toString())
            }
        })
    }

    // Facebook 토큰을 Firebase 로 넘기기
    fun handleFacebookAccessToken(token: AccessToken) {
        val credential = FacebookAuthProvider.getCredential(token.token)
        auth?.signInWithCredential(credential)
                ?.addOnCompleteListener { task ->
                    progress_bar.visibility = View.GONE
                    // 다음 페이지 이동
                    if(task.isSuccessful) {
                        moveMainPage(auth?.currentUser)
                    }
                }
    }

    // 이메일 회원가입 및 로그인 메소드
    fun createAndLoginEmail() {
        auth?.createUserWithEmailAndPassword(
                email_edittext.text.toString(), password_edittext.text.toString())
                ?.addOnCompleteListener { task ->
                    progress_bar.visibility = View.GONE
                    if(task.isSuccessful) {
                        // 아이디 생성 성공
                        Toast.makeText(this, getString(R.string.signup_complete), Toast.LENGTH_SHORT).show()
                        moveMainPage(auth?.currentUser)
                    } else if(task.exception?.message.isNullOrEmpty()) {
                        Toast.makeText(this, task.exception!!.message, Toast.LENGTH_SHORT).show()
                    } else {
                        signinEmail()
                    }
                }
    }

    fun emailLogin() {
        if(email_edittext.text.toString().isNullOrEmpty() ||
                password_edittext.text.toString().isNullOrEmpty()) {
            Toast.makeText(this, getString(R.string.signout_fail_null), Toast.LENGTH_SHORT).show()
        } else {
            progress_bar.visibility = View.VISIBLE
            createAndLoginEmail()
        }
    }

    // 로그인 메소드
    fun signinEmail() {
        auth?.signInWithEmailAndPassword(
                email_edittext.text.toString(), password_edittext.text.toString())
                ?.addOnCompleteListener { task ->
                    progress_bar.visibility = View.GONE

                    if(task.isSuccessful) {
                        // 로그인 성공 및 다음 페이지 이동
                        moveMainPage(auth?.currentUser)
                    } else {
                        // 로그인 실패
                        Toast.makeText(this, task.exception!!.message, Toast.LENGTH_SHORT).show()
                    }
                }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Facebook SDK로 값 넘겨주기
        callbackManager?.onActivityResult(requestCode, resultCode, data)

        // Twitter SDK로 값 넘겨주기
        twitterAuthClient?.onActivityResult(requestCode, resultCode, data)

        // 구글에서 승인된 정보 가져오기
        if(requestCode == GOOGLE_LOGIN_CODE) { // && resultCode == Activity.RESULT_OK
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)

            if(result.isSuccess) {
                val account = result.signInAccount
                firebaseAuthWithGoogle(account!!)
            } else {
                progress_bar.visibility = View.GONE
            }
        }
    }

    fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth?.signInWithCredential(credential)
                ?.addOnCompleteListener { task ->
                    progress_bar.visibility = View.GONE
                    if(task.isSuccessful) {
                        // 다음 페이지로
                        moveMainPage(auth?.currentUser)
                    }
                }
    }

    override fun onStart() {
        super.onStart()

        // 자동 로그인
        moveMainPage(auth?.currentUser)
    }
}
