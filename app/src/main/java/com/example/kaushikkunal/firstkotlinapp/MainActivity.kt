package com.example.kaushikkunal.firstkotlinapp

import android.content.Context
import android.media.AudioManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.HashSet

class MainActivity : AppCompatActivity() ,TextToSpeech.OnInitListener{
    private var tts:TextToSpeech? = null
    private var buttonspeak:Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tts = TextToSpeech(this ,this)

        buttonspeak = this.btnspeak
        buttonspeak!!.isEnabled=false

        buttonspeak!!.setOnClickListener {
            var text = edit_speak.text.toString()

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                tts!!.speak(text,TextToSpeech.QUEUE_FLUSH,null,null)
            }
            else{
                val hash =HashMap<String, String>()
                hash.put(TextToSpeech.Engine.KEY_PARAM_STREAM,AudioManager.STREAM_NOTIFICATION.toString())
                tts!!.speak(text,TextToSpeech.QUEUE_FLUSH,hash)
            }
        }

    }
    override fun onInit(status: Int) {
        if(status==TextToSpeech.SUCCESS){
            val result= tts!!.setLanguage(Locale.US)
            if (result!= TextToSpeech.LANG_MISSING_DATA ||
                    result!= TextToSpeech.LANG_NOT_SUPPORTED){
                buttonspeak!!.isEnabled=true
            }
        }



    }

    public override fun onDestroy() {
        if (tts!=null){
            tts!!.stop()
            tts!!.shutdown()
        }
        super.onDestroy()
    }

}
