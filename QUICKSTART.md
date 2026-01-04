# Quick Start Guide - Script Runner GUI

## TL;DR - Run in 30 seconds

```bash
# Clone or navigate to project
cd script-runner-gui

# Option 1: Direct run with kotlinc (fast, if kotlinc is in PATH)
./run.sh

# Option 2: Via IntelliJ IDEA (recommended - easiest)
# Open project in IntelliJ, right-click Main.kt, select "Run MainKt"

# Option 3: Via Gradle (may have Java 25 compatibility issues)
./gradlew run
```

That's it! The GUI will open.

---

## What You Can Test Immediately

### 1. Keyword Highlighting (5 seconds)
- Editor already has Kotlin code
- Keywords like `println` are highlighted in blue
- Try typing: `fun`, `val`, `var`, `if`, `else`, `for`, `while`, `return`, `class`, `object`

### 2. Run a Script (10 seconds)
1. Click **Run** button
2. Watch output appear on the right
3. Check exit code at top (should be "Exit: 0" in black)

### 3. Live Streaming (20 seconds)
Replace editor content with:
```kotlin
for (i in 1..5) {
    println("Line $i")
    Thread.sleep(500)
}
```
Click **Run** and watch lines appear progressively.

### 4. Clickable Errors (15 seconds)
Replace editor content with:
```kotlin
println("Line 1")
undefinedFunction()
println("Line 3")
```
Click **Run**, then click the blue underlined error in output.
Your cursor jumps to the error location!

### 5. Swift Support (10 seconds)
1. Select **"Swift"** from Language dropdown
2. Replace editor with:
```swift
print("Hello from Swift!")
for i in 1...3 {
    print("Count: \(i)")
}
```
3. Click **Run**

### 6. Stop Button (10 seconds)
Replace editor with:
```kotlin
for (i in 1..100) {
    println("Count $i")
    Thread.sleep(1000)
}
```
Click **Run**, wait 3 seconds, then click **Stop**.

---

## All Features at a Glance

✅ **Editor with syntax highlighting** (Kotlin keywords)
✅ **Live streaming output** (see results as they happen)
✅ **Clickable errors** (jump to exact line/column)
✅ **Exit code display** (red for errors, black for success)
✅ **Language selector** (Kotlin or Swift)
✅ **Stop button** (terminate long scripts)
✅ **Process cleanup** (auto-kill on window close)

---

## Build Methods

### Method 1: Direct Compilation (Recommended)
```bash
./run.sh
```
**Pros:** 
- Fast
- No Gradle issues
- Works with any Java version

**Cons:**
- Requires `kotlinc` in PATH

### Method 2: Gradle
```bash
./gradlew run
```
**Pros:**
- Standard build tool
- Handles dependencies

**Cons:**
- May have issues with Java 25+

### Method 3: Manual
```bash
# Compile
kotlinc src/main/kotlin/Main.kt -include-runtime -d build/script-runner.jar

# Run
java -jar build/script-runner.jar
```

### Method 4: IDE (IntelliJ IDEA)
1. Open project in IntelliJ
2. Right-click `src/main/kotlin/Main.kt`
3. Select "Run MainKt"

---

## Test Scripts Provided

Ready-to-use test scripts in `test-scripts/`:

```
1-keyword-highlight.kts    → Test syntax highlighting
2-streaming-output.kts     → Test live output
3-clickable-error.kts      → Test error navigation
4-nonzero-exit.kts         → Test exit code (error)
5-successful-script.kts    → Test exit code (success)
6-swift-basic.swift        → Test Swift execution
7-swift-error.swift        → Test Swift error handling
8-long-running.kts         → Test stop button
9-stdout-stderr.kts        → Test stream capture
```

**How to use:**
1. Open any test script file
2. Copy its content
3. Paste into the Script Runner editor
4. Select appropriate language (Kotlin/Swift)
5. Click Run
6. Follow instructions in script comments

---

## Troubleshooting

### "kotlinc not found"
```bash
# Check if kotlinc is installed
which kotlinc

# If not, install via:
# - SDKMAN: sdk install kotlin
# - Homebrew: brew install kotlin
# - Or download from kotlinlang.org
```

### "Permission denied" on kotlinc
The app automatically tries `/usr/bin/env kotlinc` as fallback.
If still failing:
```bash
chmod +x $(which kotlinc)
```

### "Swift not found"
```bash
# Install Xcode Command Line Tools
xcode-select --install

# Or download Swift from swift.org
```

### Gradle build fails with Java 25
Use the direct run script instead:
```bash
./run.sh
```

### Application doesn't open
Check Java installation:
```bash
java -version  # Should be JDK 11+
```

---

## Quick Reference Card

| Action | How To |
|--------|--------|
| **Run script** | Click "Run" button |
| **Stop script** | Click "Stop" button |
| **Switch language** | Use "Language" dropdown |
| **Jump to error** | Click blue underlined error line |
| **Clear output** | Click "Run" (auto-clears) |
| **Check exit code** | Look at "Exit: N" label (red = error) |
| **See if running** | Check status label (shows "Running…") |

---

## Testing Checklist

Copy this checklist to verify all features:

```
□ Keywords highlighted (try typing: fun, val, var)
□ Script runs successfully (Exit: 0 in black)
□ Output streams live (not all at once)
□ Error is clickable (jumps to correct line)
□ Non-zero exit shows red
□ Swift scripts execute
□ Stop button terminates script
□ Both stdout and stderr captured
```

---

## What to Show an Evaluator

1. **Start app:** `./run.sh`
2. **Demo highlighting:** Type `fun test() { val x = 5 }`
3. **Demo streaming:** Run test script #2
4. **Demo errors:** Run test script #3, click error
5. **Demo Swift:** Select Swift, run test script #6
6. **Demo stop:** Run test script #8, click Stop

Total demo time: ~2 minutes

---

## Files Overview

```
script-runner-gui/
├── run.sh                  ← Run this to start (recommended)
├── src/main/kotlin/Main.kt ← Single file implementation
├── test-scripts/           ← 9 ready-to-use test scripts
│   ├── 1-keyword-highlight.kts
│   ├── 2-streaming-output.kts
│   └── ...
├── TESTING.md             ← Comprehensive testing guide
└── README.md              ← Full documentation
```

---

## Next Steps

1. **Run the app:** `./run.sh`
2. **Try the tests:** Copy scripts from `test-scripts/`
3. **Read details:** See `TESTING.md` for comprehensive testing
4. **Review code:** Single file `src/main/kotlin/Main.kt` (~300 lines)

---

## Support

**Common Questions:**

Q: Can I run multiple scripts at once?
A: No, by design. Click Stop first, then Run another.

Q: Can I save scripts?
A: Not built-in. Copy/paste from files.

Q: Does it support other languages?
A: Currently Kotlin and Swift. Easy to extend.

Q: Why single file implementation?
A: Simpler to review, understand, and demonstrate.

---

**Ready to test?** Run: `./run.sh`

See `TESTING.md` for detailed test procedures.

