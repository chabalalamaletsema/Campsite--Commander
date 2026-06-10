package com.example.campsitecommander

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AlphaAnimation
import android.view.animation.AnimationSet
import android.view.animation.ScaleAnimation
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

/**
 * SplashActivity.kt
 * ─────────────────────────────────────────────────────────────────
 * SCREEN 1 – Splash Screen
 *
 * Requirements met:
 *   ✅ Displays "Campsite Commander" logo (campfire emoji) and title
 *   ✅ Shown while the app is loading
 *   ✅ Automatically transitions to MainActivity after 3 seconds (3000ms)
 *   ✅ Nature-themed dark colour palette
 *   ✅ Fade + scale animation on the logo
 * ─────────────────────────────────────────────────────────────────
 */
@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Animate the logo: fade in + slight scale up
        val logo = findViewById<TextView>(R.id.tvSplashLogo)
        val fadeIn = AlphaAnimation(0f, 1f).apply { duration = 1200 }
        val scaleUp = ScaleAnimation(
            0.7f, 1f, 0.7f, 1f,
            ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
            ScaleAnimation.RELATIVE_TO_SELF, 0.5f
        ).apply { duration = 1200 }
        val animSet = AnimationSet(true).apply {
            addAnimation(fadeIn)
            addAnimation(scaleUp)
        }
        logo.startAnimation(animSet)

        // ── Transition to MainActivity after 3000ms ───────────────
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()   // remove splash from back stack
        }, 3000)
    }
}
