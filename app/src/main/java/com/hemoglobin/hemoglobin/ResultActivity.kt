package com.hemoglobin.hemoglobin

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson
import com.hemoglobin.hemoglobin.helper.EncryptionHelper
import com.hemoglobin.hemoglobin.models.ProfileObjects
import kotlinx.android.synthetic.main.activity_result.*
import java.lang.RuntimeException

class ResultActivity : AppCompatActivity() {

    companion object {
        private const val SCANNED_STRING: String = "scanned_string"
        fun getScannedActivity(callingClassContext: Context, encryptedString: String): Intent {
            return Intent(callingClassContext, ResultActivity::class.java)
                    .putExtra(SCANNED_STRING, encryptedString)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        if (intent.getSerializableExtra(SCANNED_STRING) == null)
            throw RuntimeException("No encrypted String found in intent")
        val decryptedString = EncryptionHelper.getInstance().getDecryptionString(intent.getStringExtra(SCANNED_STRING))
        val userObject = Gson().fromJson(decryptedString, ProfileObjects::class.java)
        result_name.text = userObject.Name
        result_email.text = userObject.Email
        result_age.text = userObject.Age
        result_gender.text = userObject.Gender
        result_weight.text = userObject.Weight
        result_gender.text = userObject.Gender
        result_place.text = userObject.Place
        result_dob.text = userObject.Dob
        result_bg.text = userObject.Blood_Group
        result_hl.text = userObject.Hemoglobin_Level
        result_pr.text = userObject.Pulse_Rate
        result_bp.text = userObject.BP_Diastolic
        result_bt.text = userObject.Body_Temperature
        result_rd.text = userObject.Recent_Donation
        result_alcohol.text = userObject.Alcohol
        result_hiv.text = userObject.HIV
        result_ca.text = userObject.Cardia_Arrest
        result_ht.text = userObject.Hyper_Tension
        result_ka.text = userObject.Kidney_Alignments
        result_dia.text = userObject.Diabetics
        result_ep.text = userObject.Epilepsy
        result_asth.text = userObject.Asthmatic
        result_can.text = userObject.Cancer
        result_tatt.text = userObject.Tattoo
        result_tm.text = userObject.TM
        result_bbp.text = userObject.BBP
        result_tuber.text = userObject.Tuberculosis
        result_jan.text = userObject.Jaundice
        if (userObject.Alcohol>="Yes")
            result_alcohol.setTextColor(Color.RED)
        if (userObject.HIV>="Yes")
            result_hiv.setTextColor(Color.RED)
        if (userObject.Alcohol>="Yes")
            result_alcohol.setTextColor(Color.RED)
        result_approve.setOnClickListener {
            val registerIndent = Intent(this@ResultActivity, ApproveActivity::class.java)
            registerIndent.putExtra("user_id", userObject.User_Id)
//            registerIndent.putExtra("email", intent.getStringExtra("email_address"))
//            registerIndent.putExtra("gender", gender)
//            registerIndent.putExtra("age", age)
//            registerIndent.putExtra("weight", weight)
//            registerIndent.putExtra("day", day)
//            registerIndent.putExtra("month", month)
//            registerIndent.putExtra("year", year)
//            registerIndent.putExtra("state", state)
//            registerIndent.putExtra("district", district)
//            registerIndent.putExtra("city", city)
            startActivity(registerIndent)
        }

    }
}
