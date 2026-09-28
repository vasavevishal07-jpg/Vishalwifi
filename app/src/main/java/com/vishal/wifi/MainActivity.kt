package com.vishal.wifi

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.wifi.WifiManager
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

class MainActivity : AppCompatActivity() {
    private lateinit var wifiManager: WifiManager
    private lateinit var txtStatus: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Simple UI without XML layout
        val layout = android.widget.LinearLayout(this).apply {
            orientation = android.widget.LinearLayout.VERTICAL
            setPadding(50,100,50,50)
        }
        txtStatus = TextView(this).apply {
            text = "Vishal WiFi App\n\nBoss Ready Hai Boss!"
            textSize = 20f
        }
        val btnScan = Button(this).apply { text = "WiFi SCAN KARO BOSS" }
        val btnInfo = Button(this).apply { text = "WIFI INFO DIKHAO" }

        layout.addView(txtStatus)
        layout.addView(btnScan)
        layout.addView(btnInfo)
        setContentView(layout)

        wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
        }

        btnScan.setOnClickListener {
            if (!wifiManager.isWifiEnabled) {
                wifiManager.isWifiEnabled = true
                Toast.makeText(this, "WiFi ON Kiya Boss!", Toast.LENGTH_SHORT).show()
            }
            wifiManager.startScan()
            val results = wifiManager.scanResults
            var info = "Total WiFi Mile: ${results.size}\n\n"
            for (r in results.take(15)) {
                info += "${r.SSID} - ${r.level} dBm\n"
            }
            txtStatus.text = info
        }

        btnInfo.setOnClickListener {
            val info = wifiManager.connectionInfo
            txtStatus.text = "Connected WiFi:\n${info.ssid}\nIP: ${info.ipAddress}\nSpeed: ${info.linkSpeed} Mbps\nBoss Ka WiFi Strong Hai!"
        }
    }
}
