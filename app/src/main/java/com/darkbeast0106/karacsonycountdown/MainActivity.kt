package com.darkbeast0106.karacsonycountdown

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var szamlalo: TextView
    private lateinit var karacsony: Date
    private lateinit var myTimer: Timer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        szamlalo = findViewById(R.id.szamlalo)
        val most = Calendar.getInstance()
        var ev = most.get(Calendar.YEAR)
        if (most.get(Calendar.MONTH) == 11 && most.get(Calendar.DATE) >= 24) {
            ev++
        }
        val karacsonyCalendar = Calendar.getInstance()
        karacsonyCalendar.set(ev, 11, 24, 0, 0, 0)
        karacsony = karacsonyCalendar.time
    }


    override fun onStop() {
        super.onStop()
        myTimer.cancel()
    }

    override fun onStart() {
        super.onStart()
        myTimer = Timer()
        val task = object : TimerTask() {
            override fun run() {
                val most = Calendar.getInstance().time
                var hatralevoIdo = karacsony.time - most.time

                val masodPercMili: Long = 1000
                val percMili = masodPercMili * 60
                val oraMili = percMili * 60
                val napMili = oraMili * 24

                val nap = hatralevoIdo / napMili
                hatralevoIdo %= napMili
                val ora = hatralevoIdo / oraMili
                hatralevoIdo %= oraMili
                val perc = hatralevoIdo / percMili
                hatralevoIdo %= percMili
                val masodperc = hatralevoIdo / masodPercMili

                val hatralevoSzoveg =
                    getString(R.string.szamlaloFormatum, nap, ora, perc, masodperc)
                runOnUiThread { szamlalo.text = hatralevoSzoveg }
            }
        }
        myTimer.schedule(task, 0, 500)
    }
}