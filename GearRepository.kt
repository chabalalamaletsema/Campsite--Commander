package com.example.campsitecommander

/**
 * GearRepository.kt
 * ─────────────────────────────────────────────────────────────────
 * Singleton data store for all camping gear items.
 *
 * Uses PARALLEL ARRAYS so that itemNames[i], categories[i],
 * quantities[i], and comments[i] all describe the same item at
 * index i — required by the brief.
 *
 * Maximum capacity : MAX_ITEMS (50)
 * Pre-loaded sample: 3 items from the brief's example table.
 * ─────────────────────────────────────────────────────────────────
 */
object GearRepository {

    const val MAX_ITEMS = 50

    // ── Parallel arrays ───────────────────────────────────────────
    val itemNames  = Array(MAX_ITEMS) { "" }
    val categories = Array(MAX_ITEMS) { "" }
    val quantities = IntArray(MAX_ITEMS) { 0 }
    val comments   = Array(MAX_ITEMS) { "" }

    /** How many items are currently stored. */
    var itemCount: Int = 0
        private set

    // ── Initialise with sample data from the brief ────────────────
    init { loadSampleData() }

    private fun loadSampleData() {
        addItem("Tent",          "Shelter", 1, "4-person waterproof")
        addItem("Marshmallows",  "Food",    3, "For S'mores (Mega size)")
        addItem("Flashlight",    "Safety",  2, "Check batteries (AA)")
    }

    // ── CRUD ──────────────────────────────────────────────────────

    /**
     * Add a new gear item.
     * @return true on success, false if list is full or name is blank.
     */
    fun addItem(name: String, category: String, quantity: Int, comment: String): Boolean {
        if (itemCount >= MAX_ITEMS) return false
        val trimName = name.trim()
        if (trimName.isEmpty()) return false
        itemNames[itemCount]  = trimName
        categories[itemCount] = category.trim()
        quantities[itemCount] = quantity
        comments[itemCount]   = comment.trim()
        itemCount++
        return true
    }

    /** Remove all items and reset the counter. */
    fun clearAll() {
        for (i in 0 until MAX_ITEMS) {
            itemNames[i]  = ""
            categories[i] = ""
            quantities[i] = 0
            comments[i]   = ""
        }
        itemCount = 0
    }

    // ── Computed values (use loops as required by brief) ──────────

    /**
     * Total number of individual units packed.
     * Uses a FOR loop as specified in the brief.
     */
    fun totalItemsPacked(): Int {
        var total = 0
        for (i in 0 until itemCount) {
            total += quantities[i]
        }
        return total
    }

    /** Total number of distinct gear entries. */
    fun totalGearEntries(): Int = itemCount

    /**
     * Build a full formatted gear list string (used in DetailedViewActivity).
     * Uses a FOR loop to iterate through all items.
     */
    fun fullGearListSummary(): String {
        if (itemCount == 0) return "No gear added yet.\nTap 'Add Gear' to get started!"
        val sb = StringBuilder()
        sb.appendLine("🏕️  FULL GEAR LIST  ($itemCount entries | ${totalItemsPacked()} units)")
        sb.appendLine("━".repeat(38))
        for (i in 0 until itemCount) {
            sb.appendLine("${i + 1}. ${itemNames[i]}")
            sb.appendLine("   📂 Category : ${categories[i]}")
            sb.appendLine("   🔢 Quantity : ${quantities[i]}")
            if (comments[i].isNotEmpty()) {
                sb.appendLine("   💬 Note     : ${comments[i]}")
            }
            if (i < itemCount - 1) sb.appendLine("   ─────────────────────────────")
        }
        return sb.toString().trimEnd()
    }
}
