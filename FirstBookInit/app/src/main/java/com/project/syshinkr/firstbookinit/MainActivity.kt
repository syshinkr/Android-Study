package com.project.syshinkr.firstbookinit

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.twitter.sdk.android.core.*
import com.twitter.sdk.android.core.identity.TwitterAuthClient
import java.util.*

class MainActivity : AppCompatActivity() {
    var auth: FirebaseAuth? = null
    var authListener: FirebaseAuth.AuthStateListener? = null
    var googleSignInClient: GoogleSignInClient? = null // 구글 로그인 관리
    var callbackManager: CallbackManager? = null // 페이스북 로그인
    var twitterAuthClient: TwitterAuthClient? = null // 트위터 로그인

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Twitter.initialize(this)
        twitterAuthClient = TwitterAuthClient()

        var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    fun signIn() {
        val signInIntent = googleSignInClient?.signInIntent
        startActivityForResult(signInIntent, 100) // request 100

    }

    /**
     * 페이스북 로그인
     */
    /*
    fun facebookLogin() {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_Profile","email"))
        LoginManager.getInstance().registerCallback(callbackManager, object: FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                handleFacebookAccessToken(loginResult.accessToken) // 페이스북 로그인 성공
            }

            override fun onCancel() {
                // 페이스북 로그인 취소
            }

            override fun onError(error: FacebookException) {
                // 페이스북 로그인 실패
            }
        })
    }
    */

    fun twitterLogin() {
        twitterAuthClient?.authorize(this, object : Callback<TwitterSession>() {
            override fun success(result: Result<TwitterSession>?) {

            }

            override fun failure(exception: TwitterException?) {

            }

        })
    }


    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // 구글 로그인
        if (requestCode == 100) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if (result.isSuccess) {
                // 로그인 성공
                val account = result.signInAccount
                val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
                FirebaseAuth.getInstance().signInWithCredential(credential)
            }
        }

        // 페이스북 로그인
        // callbackManager?.onActivityResult(requestCode, resultCode, data)

        // 트위터 로그인
        // twitterAuthClient?.onActivityResult(requestCode, resultCode, data)
    }
}

data class UserDTO(var name: String? = null, var address: String? = null)
