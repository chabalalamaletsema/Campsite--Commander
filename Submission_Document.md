# Campsite Commander – Submission Document

## GitHub Repository Link
`https://github.com/your-username/campsite-commander`

---

## App Explanation
**Campsite Commander** is a native Android Kotlin app that helps campers manage their gear
packing list across three screens.

### Screen 1 – Splash Screen (SplashActivity)
- Displays the "Campsite Commander" campfire 🔥 logo and title
- Shown while the app loads
- Automatically transitions to the Main Screen after **3 seconds (3000ms)** using `Handler.postDelayed()`
- Fade-in + scale animation on the logo
- Dark forest-green nature-themed background

### Screen 2 – Main Screen (MainActivity)
- **Add Gear** button → custom AlertDialog with 4 fields: Item Name, Category (Spinner), Quantity, Comments
- **Total Units Packed** calculated using a **FOR loop** (inside `GearRepository.totalItemsPacked()`)
- **Gear Entries** count displayed separately
- **View Full Gear List** button → navigates to Detailed View Screen
- **Clear All Gear** button with confirmation dialog
- Dark Mode / nature-themed colour palette throughout
- Full input validation with constructive Toast error messages

### Screen 3 – Detailed View Screen (DetailedViewActivity)
- Displays the **full list of gear** with: Item Name, Category, Quantity, and Comments/Notes
- Stats banner showing total entries and total units (loop-calculated)
- **"Back to Base"** button calls `finish()` to return to Main Screen
- Data iterated using a **FOR loop** in `GearRepository.fullGearListSummary()`

---

## Data Storage (Parallel Arrays)
All data is stored in **GearRepository.kt** (Kotlin singleton `object`):

| Array | Type | Description |
|---|---|---|
| `itemNames` | `Array<String>` | Gear item name |
| `categories` | `Array<String>` | Category (Shelter / Food / Safety etc.) |
| `quantities` | `IntArray` | How many units |
| `comments` | `Array<String>` | Optional notes/comments |
| `itemCount` | `Int` | Current number of items |

**Pre-loaded sample data** (from brief):
| Item | Category | Qty | Comment |
|---|---|---|---|
| Tent | Shelter | 1 | 4-person waterproof |
| Marshmallows | Food | 3 | For S'mores (Mega size) |
| Flashlight | Safety | 2 | Check batteries (AA) |

---

## Loop Usage
The brief requires loops in two places:

```kotlin
// GearRepository.kt – totalItemsPacked()
fun totalItemsPacked(): Int {
    var total = 0
    for (i in 0 until itemCount) {
        total += quantities[i]   // ← FOR loop over parallel array
    }
    return total
}

// GearRepository.kt – fullGearListSummary()
fun fullGearListSummary(): String {
    val sb = StringBuilder()
    for (i in 0 until itemCount) {  // ← FOR loop builds display string
        sb.appendLine("${i+1}. ${itemNames[i]}")
        sb.appendLine("   Category : ${categories[i]}")
        sb.appendLine("   Quantity : ${quantities[i]}")
        sb.appendLine("   Note     : ${comments[i]}")
    }
    return sb.toString()
}
```

---

## Error Handling (Constructive Feedback)
| Condition | Toast message shown |
|---|---|
| Item name blank | "⚠️ Please enter a gear item name." |
| Name < 2 chars | "⚠️ Item name must be at least 2 characters long." |
| Name > 50 chars | "⚠️ Item name is too long (max 50 characters)." |
| Quantity blank | "⚠️ Please enter a quantity." |
| Quantity not a number | "⚠️ Quantity must be a whole number (e.g. 1, 2, 3)." |
| Quantity < 1 | "⚠️ Quantity must be at least 1." |
| Quantity > 99 | "⚠️ Quantity cannot exceed 99." |
| List full (50 items) | "⚠️ Gear list is full. Maximum 50 items allowed." |

---

## Pseudocode
```
START

Display SplashActivity
    Show campfire logo + "Campsite Commander" title
    Wait 3000ms
    Transition to MainActivity
END Splash

Display MainActivity
    LOOP through quantities[] → calculate total units packed
    Display: Gear Entries count, Total Units Packed

    WHEN "Add Gear" clicked:
        Show dialog with Name, Category, Qty, Comment inputs
        Validate all fields
        IF valid:
            Save to GearRepository parallel arrays
            Recalculate and refresh totals using loop
        ELSE:
            Show constructive Toast error

    WHEN "View Full Gear List" clicked:
        Start DetailedViewActivity

    WHEN "Clear All Gear" clicked:
        Show confirmation dialog
        IF confirmed: clear all arrays, reset count, refresh totals

Display DetailedViewActivity
    FOR i = 0 to itemCount - 1:
        Display itemNames[i], categories[i], quantities[i], comments[i]
    END FOR
    Display total stats

    WHEN "Back to Base" clicked:
        finish() → return to MainActivity

END
```

---

## Project Structure
```
CampsiteCommander/
├── app/
│   ├── build.gradle.kts
│   └── src/main/
│       ├── AndroidManifest.xml
│       ├── java/com/example/campsitecommander/
│       │   ├── GearRepository.kt         ← parallel arrays + loops
│       │   ├── SplashActivity.kt         ← Screen 1 (3s auto-transition)
│       │   ├── MainActivity.kt           ← Screen 2 (Add Gear + totals)
│       │   └── DetailedViewActivity.kt   ← Screen 3 (full list + Back to Base)
│       └── res/
│           ├── layout/
│           │   ├── activity_splash.xml
│           │   ├── activity_main.xml
│           │   ├── activity_detailed_view.xml
│           │   └── dialog_add_gear.xml
│           ├── drawable/
│           │   ├── bg_forest_gradient.xml  ← dark nature background
│           │   ├── card_dark_green.xml
│           │   └── ic_campsite.xml         ← custom campfire app icon
│           └── values/
│               ├── strings.xml
│               └── themes.xml
├── build.gradle.kts
├── settings.gradle.kts
└── local.properties
```

---

## Setup Instructions
1. Unzip and open `CampsiteCommander` in **Android Studio Hedgehog** or later
2. Let Gradle sync complete
3. Run on an emulator or device with **API 24+**

---

*Replace the GitHub link above with your actual repository URL before submitting.*
