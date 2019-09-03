package com.hemoglobin.hemoglobin

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import com.hemoglobin.hemoglobin.helper.EncryptionHelper
import com.hemoglobin.hemoglobin.helper.QRCodeHelper
import com.hemoglobin.hemoglobin.models.ProfileObjects
import kotlinx.android.synthetic.main.activity_profile_view.*
import kotlinx.android.synthetic.main.activity_qrcode.*

class ProfileViewActivity : AppCompatActivity() {

    companion object {

        fun getGenerateQrCodeActivity(callingClassContext: Context) = Intent(callingClassContext, ProfileViewActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_view)

            val mAuth = FirebaseAuth.getInstance()
            val mCurrentUser = mAuth.getCurrentUser()
            val mCurrentUserId = mCurrentUser!!.uid
            val database = FirebaseDatabase.getInstance()
            val mProfileDatabase = database.getReference("Profile").child(mCurrentUserId)

            val postListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    if (dataSnapshot.exists()) {
                        val name = dataSnapshot.child("name").value!!.toString()
                        val email = dataSnapshot.child("email_id").value!!.toString()
                        val gender = dataSnapshot.child("gender").value!!.toString()
                        val age = dataSnapshot.child("age").value!!.toString()
                        val date_of_birth = dataSnapshot.child("date_of_birth").value!!.toString()
                        val weight = dataSnapshot.child("weight").value!!.toString()
                        val state = dataSnapshot.child("state").value!!.toString()
                        val district = dataSnapshot.child("district").value!!.toString()
                        val city = dataSnapshot.child("city").value!!.toString()
                        val blood_group = dataSnapshot.child("blood_group").value!!.toString()
                        val hemoglobin = dataSnapshot.child("hemoglobin_level").value!!.toString()
                        val pulse_rate = dataSnapshot.child("pulse_rate").value!!.toString()
                        val bp_diastolic = dataSnapshot.child("bp_diastolic").value!!.toString()
                        val body_temperrature = dataSnapshot.child("body_temperature").value!!.toString()
                        val recent_donation = dataSnapshot.child("recent_donation").value!!.toString()
                        val alcohol = dataSnapshot.child("alcohol").value!!.toString()
                        val hiv = dataSnapshot.child("hiv").value!!.toString()
                        val cardiac_arrest = dataSnapshot.child("cardiac_arrest").value!!.toString()
                        val hyper_tension = dataSnapshot.child("hyper_tension").value!!.toString()
                        val kidney_alignments = dataSnapshot.child("kidney_alignments").value!!.toString()
                        val diabetics = dataSnapshot.child("diabetics").value!!.toString()
                        val epilepsy = dataSnapshot.child("epilepsy").value!!.toString()
                        val asth = dataSnapshot.child("asthmatic").value!!.toString()
                        val tuber = dataSnapshot.child("tuberculosis").value!!.toString()
                        val cancer = dataSnapshot.child("cancer").value!!.toString()
                        val bbp = dataSnapshot.child("bbp").value!!.toString()
                        val tm = dataSnapshot.child("typhoid_or_malaria").value!!.toString()
                        val tatt = dataSnapshot.child("tattoo").value!!.toString()
                        val jan = dataSnapshot.child("jaundice").value!!.toString()
                        val abort = dataSnapshot.child("periods").value!!.toString()
                        val periods = dataSnapshot.child("abortion").value!!.toString()

                        profile_view_name.setText(name)
                        profile_view_email.setText(email)
                        profile_view_gender.setText(gender)
                        profile_view_age.setText(age)
                        profile_view_dob.setText("Date Of Bith : " + date_of_birth)
                        profile_view_place.setText(state + ", " + district + ", " + city)
                        profile_view_weight.setText(weight + " Kg")
                        profile_view_bg.setText("Blood Group : "+blood_group)
                        profile_view_hl.setText("Hemoglobin Level : "+hemoglobin + " g/dL")
                        profile_view_pr.setText("Pulse Rate : "+pulse_rate + " bpm")
                        profile_view_bp.setText("Blood Pressure : "+bp_diastolic)
                        profile_view_rd.setText("Recent Donation : "+recent_donation)
                        val tmp: Char = 0x00B0.toChar()
                        profile_view_bt.setText("Body Temperature : "+body_temperrature + tmp + "C")
                        profile_view_alcohol.setText("Alcohol Consumer : "+alcohol)
                        profile_view_hiv.setText("Needle Prick : "+hiv)
                        profile_view_ca.setText("Cardiac Arrest (Heart Patient) : "+cardiac_arrest)
                        profile_view_ht.setText("Hyper Tension : "+hyper_tension)
                        profile_view_ka.setText("Kidney Alignments : "+kidney_alignments)
                        profile_view_dia.setText("Diabetics : "+diabetics)
                        profile_view_ep.setText("Epilepsy : "+epilepsy)
                        profile_view_as.setText("Asthmatic : "+asth)
                        profile_view_tuber.setText("Tuberculosis : "+tuber)
                        profile_view_can.setText("Cancer Patient : "+cancer)
                        profile_view_tm.setText("Typhoid / Malaria (1 year) : "+tm)
                        profile_view_jan.setText("Jaundice (1 year) : "+jan)
                        profile_view_tatt.setText("Tattoo : "+tatt)
                        profile_view_bbp.setText("Blood / Blood Product Transfussion (1 year) : "+bbp)
                        if (gender == "Female" ){
                            profile_view_lp.setText("Under Going Periods : "+periods)
                            profile_view_la.setText("Recently Baby Aborted: "+abort)
                            profile_view_lp.visibility = View.VISIBLE
                            profile_view_la.visibility = View.VISIBLE
                        }


                        val place = state + ", " + district + ", " + city

                        val user = ProfileObjects(name, email, gender, age, weight, place, date_of_birth, blood_group, hemoglobin, pulse_rate, bp_diastolic, body_temperrature, recent_donation, alcohol, hiv, cardiac_arrest, hyper_tension, kidney_alignments, diabetics, epilepsy,
                                asth, tm, cancer, tatt, jan, bbp, tuber, mCurrentUserId)
                        val serializeString = Gson().toJson(user)
                        val encryptedString = EncryptionHelper.getInstance().encryptionString(serializeString).encryptMsg()
                        profile_view_qrcode.setOnClickListener {
                            setImageBitmap(encryptedString)
                        }
                    }else{
                                Toast.makeText(this@ProfileViewActivity, "Update Your Profile", Toast.LENGTH_SHORT).show()
                    }


                }

                override fun onCancelled(databaseError: DatabaseError) {

                }
            }
            mProfileDatabase.addValueEventListener(postListener)

}

    private fun setImageBitmap(encryptedString: String?) {
        val bitmap = QRCodeHelper.newInstance(this).setContent(encryptedString).setErrorCorrectionLevel(ErrorCorrectionLevel.Q).setMargin(2).qrcOde
        val mDialogView = LayoutInflater.from(this@ProfileViewActivity).inflate(R.layout.activity_qrcode, null)
        val mBuilder = AlertDialog.Builder(this@ProfileViewActivity)
                .setView(mDialogView)
                .setTitle("Your QR Code")
        val  mAlertDialog = mBuilder.show()
        mAlertDialog.qrcode.setImageBitmap(bitmap)
    }

}
