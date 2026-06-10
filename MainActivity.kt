package com.example.campsitecommander

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

/**
 * MainActivity.kt
 * ─────────────────────────────────────────────────────────────────
 * SCREEN 2 – Main Screen
 *
 * Requirements met:
 *   ✅ "Add Gear" button → AlertDialog to enter item details
 *   ✅ Total Items Packed displayed, calculated using a FOR loop
 *   ✅ Navigate to DetailedViewActivity
 *   ✅ Dark Mode / nature-themed colour palette
 *   ✅ Input error handling with constructive Toast feedback
 *   ✅ Parallel arrays stored in GearRepository singleton
 * ─────────────────────────────────────────────────────────────────
 */
class MainActivity : AppCompatActivity() {

    private lateinit var tvTotalItems : TextView
    private lateinit var tvGearCount  : TextView
    private lateinit var btnAddGear   : Button
    private lateinit var btnViewGear  : Button
    private lateinit var btnClearAll  : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Wire views
        tvTotalItems = findViewById(R.id.tvTotalItems)
        tvGearCount  = findViewById(R.id.tvGearCount)
        btnAddGear   = findViewById(R.id.btnAddGear)
        btnViewGear  = findViewById(R.id.btnViewGear)
        btnClearAll  = findViewById(R.id.btnClearAll)

        refreshTotals()

        // ── Button listeners ──────────────────────────────────────

        btnAddGear.setOnClickListener { showAddGearDialog() }

        btnViewGear.setOnClickListener {
            startActivity(Intent(this, DetailedViewActivity::class.java))
        }

        btnClearAll.setOnClickListener {
            if (GearRepository.itemCount == 0) {
                Toast.makeText(this, "The gear list is already empty.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            AlertDialog.Builder(this)
                .setTitle("🗑️  Clear All Gear?")
                .setMessage("This will remove all ${GearRepository.itemCount} items from your list. This cannot be undone.")
                .setPositiveButton("Clear All") { _, _ ->
                    GearRepository.clearAll()
                    refreshTotals()
                    Toast.makeText(this, "Gear list cleared.", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Cancel", null)
                .show()
        }
    }

    override fun onResume() {
        super.onResume()
        // Keep totals accurate when returning from DetailedViewActivity
        refreshTotals()
    }

    // ── Private helpers ───────────────────────────────────────────

    /**
     * Refreshes both total labels.
     * Uses a FOR loop (via GearRepository.totalItemsPacked()) as required.
     */
    private fun refreshTotals() {
        val entries = GearRepository.totalGearEntries()
        val units   = GearRepository.totalItemsPacked()  // ← uses FOR loop inside

        tvGearCount.text  = "Gear Entries: $entries"
        tvTotalItems.text = "Total Units Packed: $units"
    }

    /**
     * Shows a custom AlertDialog with four input fields:
     *   - Item name (required, 2–50 chars)
     *   - Category  (Spinner)
     *   - Quantity  (required, 1–99)
     *   - Comments  (optional)
     *
     * Validates all inputs and shows constructive error feedback.
     */
    private fun showAddGearDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_gear, null)
        val etName     = dialogView.findViewById<EditText>(R.id.etGearName)
        val spinnerCat = dialogView.findViewById<Spinner>(R.id.spinnerCategory)
        val etQty      = dialogView.findViewById<EditText>(R.id.etQuantity)
        val etComment  = dialogView.findViewById<EditText>(R.id.etComment)

        // Category options
        val catOptions = arrayOf(
            "🏕️ Shelter", "🍖 Food", "🔦 Safety", "👕 Clothing",
            "🧰 Tools", "💊 First Aid", "🎒 Accessories", "Other"
        )
        spinnerCat.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, catOptions)

        AlertDialog.Builder(this)
            .setTitle("⛺  Add Gear Item")
            .setView(dialogView)
            .setPositiveButton("Add to List") { _, _ ->

                val name    = etName.text.toString().trim()
                val cat     = spinnerCat.selectedItem.toString()
                val qtyStr  = etQty.text.toString().trim()
                val comment = etComment.text.toString().trim()

                // ── Validation with constructive feedback ─────────
                when {
                    name.isEmpty() ->
                        toast("⚠️ Please enter a gear item name.")
                    name.length < 2 ->
                        toast("⚠️ Item name must be at least 2 characters long.")
                    name.length > 50 ->
                        toast("⚠️ Item name is too long (max 50 characters).")
                    qtyStr.isEmpty() ->
                        toast("⚠️ Please enter a quantity.")
                    qtyStr.toIntOrNull() == null ->
                        toast("⚠️ Quantity must be a whole number (e.g. 1, 2, 3).")
                    qtyStr.toInt() < 1 ->
                        toast("⚠️ Quantity must be at least 1.")
                    qtyStr.toInt() > 99 ->
                        toast("⚠️ Quantity cannot exceed 99.")
                    GearRepository.itemCount >= GearRepository.MAX_ITEMS ->
                        toast("⚠️ Gear list is full. Maximum ${GearRepository.MAX_ITEMS} items allowed.")
                    else -> {
                        // ── All valid – save to repository ────────
                        GearRepository.addItem(name, cat, qtyStr.toInt(), comment)
                        refreshTotals()
                        toast("✅ \"$name\" added to your gear list!")
                    }
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun toast(msg: String) =
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
}
