package com.example.campsitecommander

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

/**
 * DetailedViewActivity.kt
 * ─────────────────────────────────────────────────────────────────
 * SCREEN 3 – Detailed View Screen
 *
 * Requirements met:
 *   ✅ Displays full list of gear with: Item, Category, Quantity,
 *      and specific Notes/Comments
 *   ✅ "Back to Base" button navigates back to Main Screen (finish())
 *   ✅ Uses GearRepository.fullGearListSummary() which uses a FOR loop
 *   ✅ Nature-themed dark colour palette
 * ─────────────────────────────────────────────────────────────────
 */
class DetailedViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_view)

        val tvSummary   = findViewById<TextView>(R.id.tvGearSummary)
        val tvStats     = findViewById<TextView>(R.id.tvStats)
        val btnBackBase = findViewById<Button>(R.id.btnBackToBase)

        // ── Populate the gear summary (uses FOR loop in repository) ─
        tvSummary.text = GearRepository.fullGearListSummary()

        // ── Stats banner ──────────────────────────────────────────
        val entries = GearRepository.totalGearEntries()
        val units   = GearRepository.totalItemsPacked()
        tvStats.text = "📦 $entries gear entries   •   🎒 $units total units packed"

        // ── Back to Base navigation ───────────────────────────────
        btnBackBase.setOnClickListener {
            finish()   // pops this activity → returns to MainActivity
        }
    }
}
