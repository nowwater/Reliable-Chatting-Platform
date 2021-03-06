package com.example.chattingapp.view

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.chattingapp.R
import com.example.chattingapp.dto.User
import com.example.chattingapp.service.UserApiService
import okhttp3.ResponseBody
import java.io.IOException

class SignUpActivity : AppCompatActivity(), View.OnClickListener, SimpleTextWatcher {
    private var mBackBtn // 뒤로가기
            : LinearLayout? = null
    private var inputID: EditText? = null
    private var inputIDcheck: TextView? = null
    private var inputPW: EditText? = null
    private var inputPWconfirm: EditText? = null
    private var inputName: EditText? = null
    private var inputNickname: EditText? = null
    private var inputPhone: TextView? = null


    //약관동의
    private var mAllLayout: LinearLayout? = null
    private var mcheck1Layout: LinearLayout? = null
    private var mcheck2Layout: LinearLayout? = null
    private var mcheck3Layout: LinearLayout? = null
    private var mcheck4Layout: LinearLayout? = null
    private var mAllCheckBox: ImageView? = null
    private var mChec1kBox: ImageView? = null
    private var mChec2kBox: ImageView? = null
    private var mChec3kBox: ImageView? = null
    private var mChec4kBox: ImageView? = null

    //약관보기
    private var mAuthBtn1: TextView? = null
    private var mAuthBtn2: TextView? = null
    private var mAuthBtn3: TextView? = null
    private var mSubmitJoin: Button? = null
    private var isCheckAll = false
    private var isCheck1 = false
    private var isCheck2 = false
    private var isCheck3 = false
    private var isCheck4 = false
    private val REQUEST_AUTH = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        initView()
        setUpAdapter()
        setUpListener()
    }

    fun initView() {
        mBackBtn = findViewById(R.id.back_button)
        inputID = findViewById(R.id.input_id) as EditText?
        inputIDcheck = findViewById(R.id.input_id_check) as TextView?
        inputPW = findViewById(R.id.input_pw) as EditText?
        inputPWconfirm = findViewById(R.id.input_pw_confirm) as EditText?
        inputName = findViewById(R.id.input_name) as EditText?
        inputNickname = findViewById(R.id.input_nickname) as EditText?
        inputPhone = findViewById(R.id.input_phone) as TextView?
        mAllLayout = findViewById(R.id.check_all_layout) as LinearLayout?
        mcheck1Layout = findViewById(R.id.check_1_layout) as LinearLayout?
        mcheck2Layout = findViewById(R.id.check_2_layout) as LinearLayout?
        mcheck3Layout = findViewById(R.id.check_3_layout) as LinearLayout?
        mcheck4Layout = findViewById(R.id.check_4_layout) as LinearLayout?
        mAllCheckBox = findViewById(R.id.check_all) as ImageView?
        mChec1kBox = findViewById(R.id.check_1) as ImageView?
        mChec2kBox = findViewById(R.id.check_2) as ImageView?
        mChec3kBox = findViewById(R.id.check_3) as ImageView?
        mChec4kBox = findViewById(R.id.check_4) as ImageView?
        mAuthBtn1 = findViewById(R.id.auth_1) as TextView?
        mAuthBtn2 = findViewById(R.id.auth_2) as TextView?
        mAuthBtn3 = findViewById(R.id.auth_3) as TextView?
        mSubmitJoin = findViewById(R.id.join_submit) as Button?
    }

    fun setUpAdapter() {}
    fun setUpListener() {
        mBackBtn!!.setOnClickListener(this)
        mAllLayout!!.setOnClickListener(this)
        mcheck1Layout!!.setOnClickListener(this)
        mcheck2Layout!!.setOnClickListener(this)
        mcheck3Layout!!.setOnClickListener(this)
        mcheck4Layout!!.setOnClickListener(this)
        mAuthBtn1!!.setOnClickListener(this)
        mAuthBtn2!!.setOnClickListener(this)
        mAuthBtn3!!.setOnClickListener(this)
        mSubmitJoin!!.setOnClickListener(this)
        inputID!!.addTextChangedListener(mIdTextWatcher)
    }

    override fun onClick(v: View) {
        val id = v.id
        when (id) {
            R.id.back_button -> {
                onBackPressed()
            }
//            R.id.input_phone_layout, R.id.phone_auth -> {
//                val i = Intent(getApplicationContext(), PhoneAuthActivity::class.java)
//                startActivityForResult(i, REQUEST_AUTH)
//            }
            R.id.check_all_layout -> {
                isCheckAll = !isCheckAll
                mCheckAllStatus()
            }
            R.id.check_1_layout -> {
                isCheck1 = !isCheck1
                isCheckAll = isCheck1 && isCheck2 && isCheck3 && isCheck4
                mCheckImageChange()
            }
            R.id.check_2_layout -> {
                isCheck2 = !isCheck2
                isCheckAll = isCheck1 && isCheck2 && isCheck3 && isCheck4
                mCheckImageChange()
            }
            R.id.check_3_layout -> {
                isCheck3 = !isCheck3
                isCheckAll = isCheck1 && isCheck2 && isCheck3 && isCheck4
                mCheckImageChange()
            }
            R.id.check_4_layout -> {
                isCheck4 = !isCheck4
                isCheckAll = isCheck1 && isCheck2 && isCheck3 && isCheck4
                mCheckImageChange()
            }
            R.id.auth_1 -> {
            }
            R.id.auth_2 -> {
            }
            R.id.auth_3 -> {
            }
            R.id.join_submit -> {
                requestJoin()
            }
        }
    }

    fun mCheckAllStatus() {
        if (isCheckAll) {
            isCheck1 = true
            isCheck2 = true
            isCheck3 = true
            isCheck4 = true
        } else {
            isCheck1 = false
            isCheck2 = false
            isCheck3 = false
            isCheck4 = false
        }
        mCheckImageChange()
    }

    fun mCheckImageChange() {
        val On = ContextCompat.getDrawable(getApplicationContext(), R.drawable.check_box_on)
        val Off = ContextCompat.getDrawable(getApplicationContext(), R.drawable.check_box)
        if (isCheckAll) {
            mAllCheckBox!!.setImageDrawable(On)
        } else {
            mAllCheckBox!!.setImageDrawable(Off)
        }
        if (isCheck1) {
            mChec1kBox!!.setImageDrawable(On)
        } else {
            mChec1kBox!!.setImageDrawable(Off)
        }
        if (isCheck2) {
            mChec2kBox!!.setImageDrawable(On)
        } else {
            mChec2kBox!!.setImageDrawable(Off)
        }
        if (isCheck3) {
            mChec3kBox!!.setImageDrawable(On)
        } else {
            mChec3kBox!!.setImageDrawable(Off)
        }
        if (isCheck4) {
            mChec4kBox!!.setImageDrawable(On)
        } else {
            mChec4kBox!!.setImageDrawable(Off)
        }
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == REQUEST_AUTH) {
//            if (resultCode == RESULT_OK) {
//                inputPhone!!.text = data.getStringExtra("result")
//                mPhoneAuthBtn!!.text = "재인증"
//            } else {   // RESULT_CANCEL
//            }
//        }
//    }

//    fun response_isEmailCheck(id: String?) {
//        RetrofitClient.getInstance(this)
//            .getAuthApiService()
//            .requestEmailCheck(id)
//            .enqueue(object : Callback<ResponseBody?> {
//                override fun onResponse(
//                    call: Call<ResponseBody?>,
//                    response: Response<ResponseBody?>
//                ) {
//                    Log.d("Response code", response.code().toString())
//                    val code = response.code()
//                    if (code == 200) {
////                    ResponseBody responseBody = (ResponseBody) response.body();
//                        mInputIDcheck!!.text = "사용할 수 있는 아이디 입니다."
//                        mInputIDcheck!!.setTextColor(Color.parseColor("#df504a"))
//                    } else if (code == 409) {
//                        mInputIDcheck!!.text = "중복된 이메일 입니다."
//                        mInputIDcheck!!.setTextColor(Color.parseColor("#4282ff"))
//                    }
//                }
//
//                override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {}
//            })
//    }

    fun requestJoin() {
        if (inputID!!.text.toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "이메일을 입력하세요.", Toast.LENGTH_SHORT).show()
            inputID!!.requestFocus()
            return
        }
        if (inputPW!!.text.toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show()
            inputPW!!.requestFocus()
            return
        }
        if (inputPWconfirm!!.text.toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "비밀번호 확인을 입력하세요.", Toast.LENGTH_SHORT).show()
            inputPWconfirm!!.requestFocus()
            return
        }
        if (inputName!!.text.toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "이름을 입력하세요.", Toast.LENGTH_SHORT).show()
            inputName!!.requestFocus()
            return
        }
        if (inputNickname!!.text.toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "닉네임을 입력하세요.", Toast.LENGTH_SHORT).show()
            inputNickname!!.requestFocus()
            return
        }
        if (inputPhone!!.text.toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "전화번호 인증이 되지 않았습니다.", Toast.LENGTH_SHORT).show()
            inputPhone!!.requestFocus()
//            val i = Intent(getApplicationContext(), PhoneAuthActivity::class.java)
//            startActivityForResult(i, REQUEST_AUTH)
            return
        }
        val isCheckPw = pwPattern(inputPW!!.text.toString())
        if (!isCheckPw) {
            Toast.makeText(getApplicationContext(), "비밀번호가 형식에 맞지 않습니다.", Toast.LENGTH_SHORT).show()
            inputPW!!.requestFocus()
            return
        }
        if (inputPW!!.text.toString() != inputPWconfirm!!.text.toString()) {
            Toast.makeText(getApplicationContext(), "비밀번호와 비밀번호 확인이 다릅니다.", Toast.LENGTH_SHORT)
                .show()
            inputPWconfirm!!.requestFocus()
            return
        }
        if (isCheck1 && isCheck2 && isCheck3) {
            val newUser: User = User(12345, inputID!!.text.toString(), inputName!!.text.toString(), inputNickname!!.text.toString(),
                inputPW!!.text.toString(), inputPhone!!.text.toString(), null, null)


            Log.d("InfoNickname", inputNickname!!.text.toString())
            Log.d("InfoId", newUser.accountId)

            UserApiService.instance.signUp(newUser) {
                Log.d("newUser", it.toString())//왜 안뜰까??

                Toast.makeText(this, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT)
                finish()
            }

//            RetrofitClient.getInstance(this)
//                .getAuthApiService()
//                .requestPostJoin(
//                    mInputID!!.text.toString(),
//                    mInputPW!!.text.toString(),
//                    mInputName!!.text.toString(),
//                    mInputPhone!!.text.toString(),
//                    "P"
//                )
//                .enqueue(object : Callback<ResponseBody?> {
//                    override fun onResponse(
//                        call: Call<ResponseBody?>,
//                        response: Response<ResponseBody?>
//                    ) {
//                        Log.d("Response code", response.code().toString())
//                        val code = response.code()
//                        if (code == 200) {
//                            requestLogin()
//                            try {
//                                Log.d("Data Response = ", "" + response.body()!!.string())
//                            } catch (e: IOException) {
//                                e.printStackTrace()
//                            }
//                        }
//                    }
//
//                    override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
//                        Log.d("Login Error = ", "onFailed!!")
//                    }
//                })
        } else {
            Toast.makeText(
                getApplicationContext(),
                "필수 약관에 모두 동의하셔야 가입이 가능합니다.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private val mIdTextWatcher: SimpleTextWatcher = object : SimpleTextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
            inputIDcheck!!.visibility = View.VISIBLE
            val isCheckEmail = emailPattern(inputID!!.text.toString())
            if (isCheckEmail) {
                inputIDcheck!!.text = "적합한 형식의 아이디(이메일) 입니다."
                inputIDcheck!!.setTextColor(Color.parseColor("#df504a"))
            } else {
                inputIDcheck!!.text = "이메일 형식에 맞지 않습니다."
                inputIDcheck!!.setTextColor(Color.parseColor("#6e6e6e"))
            }
        }
    }

    // 이메일 패턴 검사
    fun emailPattern(email: String): Boolean {
        val repExp =
            Regex("^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$")
        return email.matches(repExp)
    }

    // 비밀번호 패턴 검사
    fun pwPattern(pw: String): Boolean {
        val repExp = Regex("^([0-9a-zA-Z]).{3,20}$")
        return pw.matches(repExp)
    }


}