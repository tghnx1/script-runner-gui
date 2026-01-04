# Testing Guide for Script Runner GUI

This guide provides step-by-step instructions to test all implemented features.

## Prerequisites

1. Build and run the application:
   ```bash
   ./gradlew run
   ```
   Or open in IntelliJ IDEA and run the `main` function from `src/main/kotlin/Main.kt`.

2. The application window should open with:
   - Left pane: Code editor
   - Right pane: Output display
   - Top controls: Run, Stop, Language selector, Status, Exit code

---

## Test 1: Keyword Highlighting (Kotlin)

**What to test:** Verify that Kotlin keywords are highlighted in blue and bold.

**Steps:**
1. Ensure "Kotlin" is selected in the Language dropdown
2. Clear the editor and type the following:
   ```kotlin
   fun main() {
       val x = 10
       var y = 20
       if (x > 5) {
           for (i in 1..3) {
               println("Hello")
           }
       } else {
           while (y > 0) {
               return
           }
       }
       class MyClass
       object Singleton
   }
   ```

**Expected result:** 
- The words `fun`, `val`, `var`, `if`, `for`, `while`, `return`, `else`, `class`, `object` should appear in **blue and bold**
- Other text (numbers, strings, parentheses) should remain black

---

## Test 2: Live Streaming Output (Kotlin)

**What to test:** Verify that output appears progressively during script execution, not just at the end.

**Steps:**
1. Select "Kotlin" language
2. Paste this script in the editor:
   ```kotlin
   for (i in 1..10) {
       println("Line $i")
       Thread.sleep(500)
   }
   println("Done!")
   ```
3. Click **Run**

**Expected result:**
- Status should change to "Running…"
- Output pane should show "Line 1", "Line 2", etc. appearing **one by one** with visible delays
- Each line should appear ~500ms apart (not all at once)
- After completion: Status returns to "Idle", Exit code shows "Exit: 0" in black

---

## Test 3: Clickable Error Navigation (Kotlin)

**What to test:** Verify that compiler/runtime errors are clickable and jump to the correct line.

**Steps:**
1. Select "Kotlin" language
2. Paste this intentionally broken script:
   ```kotlin
   println("Line 1")
   foo()
   println("Line 3")
   ```
3. Click **Run**

**Expected result:**
- Output pane shows an error message like `script.kts:2:1: error: cannot find 'foo' in scope`
- The error line should be **blue and underlined**
- When you **click** the error line, the editor cursor should jump to line 2, column 1
- Exit code should show a non-zero value in **red**

---

## Test 4: Non-Zero Exit Code Display

**What to test:** Verify that non-zero exit codes are displayed in red.

**Steps:**
1. Select "Kotlin" language
2. Paste this script:
   ```kotlin
   println("About to fail")
   throw RuntimeException("Intentional error")
   ```
3. Click **Run**

**Expected result:**
- Output shows the error message
- Exit label shows "Exit: 1" (or another non-zero value) in **red color**

**Bonus test:**
1. Now paste a working script:
   ```kotlin
   println("Success!")
   ```
2. Click **Run**

**Expected result:**
- Exit label shows "Exit: 0" in **black color**

---

## Test 5: Swift Language Support

**What to test:** Verify that Swift scripts can be executed.

**Steps:**
1. Select **"Swift"** from the Language dropdown
2. Paste this script:
   ```swift
   print("Hello from Swift!")
   for i in 1...5 {
       print("Count: \(i)")
   }
   print("Done")
   ```
3. Click **Run**

**Expected result:**
- Output pane shows:
  ```
  Hello from Swift!
  Count: 1
  Count: 2
  Count: 3
  Count: 4
  Count: 5
  Done
  ```
- Exit code shows "Exit: 0"

---

## Test 6: Swift Error Clickability

**What to test:** Verify that Swift errors are also clickable.

**Steps:**
1. Select **"Swift"** language
2. Paste this broken script:
   ```swift
   print("Line 1")
   undefinedFunction()
   print("Line 3")
   ```
3. Click **Run**

**Expected result:**
- Output shows a Swift error (format may vary)
- If error contains `script.swift:2:1:` pattern, it should be **clickable**
- Clicking should jump to line 2 in the editor
- Exit code should be non-zero and **red**

---

## Test 7: Stop Button Functionality

**What to test:** Verify that long-running scripts can be stopped.

**Steps:**
1. Select "Kotlin" language
2. Paste this long-running script:
   ```kotlin
   for (i in 1..1000) {
       println("Count $i")
       Thread.sleep(1000)
   }
   ```
3. Click **Run**
4. Wait for a few lines of output (e.g., Count 1, Count 2, Count 3...)
5. Click **Stop**

**Expected result:**
- Output stops updating
- A message "Process stopped" appears in the output
- Status returns to "Idle"
- Stop button becomes disabled again
- Run button becomes enabled

---

## Test 8: Kotlinc Fallback Mechanism

**What to test:** Verify that the app attempts `/usr/bin/env kotlinc` fallback when direct kotlinc fails.

**Background:** You reported a "Permission denied" error earlier. The code now handles this.

**Steps:**
1. Select "Kotlin" language
2. Run any simple script:
   ```kotlin
   println("Testing fallback")
   ```
3. Click **Run**

**Expected result:**
- If the discovered kotlinc has permission issues, the app should **automatically** try `/usr/bin/env kotlinc` as a fallback
- Script should execute successfully
- Output shows "Testing fallback"
- No "Permission denied" error in output

**Manual simulation (optional):**
- Temporarily make the kotlinc binary non-executable to trigger the fallback
- The app should recover automatically

---

## Test 9: Concurrent Stdout and Stderr

**What to test:** Verify that both stdout and stderr are captured and displayed.

**Steps:**
1. Select "Kotlin" language
2. Paste this script:
   ```kotlin
   println("This is stdout")
   System.err.println("This is stderr")
   println("Back to stdout")
   ```
3. Click **Run**

**Expected result:**
- Output pane shows all three lines (order may vary slightly due to buffering):
  ```
  This is stdout
  This is stderr
  Back to stdout
  ```
- Both streams are captured and displayed

---

## Test 10: Editor Functionality

**What to test:** Basic editor usability.

**Steps:**
1. Type some code in the editor
2. Use arrow keys to navigate
3. Select text and delete it
4. Use Cmd+A (Mac) or Ctrl+A to select all
5. Paste text from clipboard

**Expected result:**
- All standard text editing operations work
- Keyword highlighting updates as you type
- No lag or freezing during typing

---

## Test 11: Window Close Cleanup

**What to test:** Verify that closing the window terminates running processes.

**Steps:**
1. Start a long-running script (see Test 7)
2. While the script is running, close the application window (click X)

**Expected result:**
- Application closes immediately
- Background process is terminated (verified by checking system processes)

---

## Quick Test Checklist

Use this checklist for rapid validation:

- [ ] Keyword highlighting works (Kotlin)
- [ ] Live streaming output visible during execution
- [ ] Error lines are clickable and jump to correct position
- [ ] Non-zero exit codes display in red
- [ ] Swift scripts execute correctly
- [ ] Stop button terminates running scripts
- [ ] Kotlinc fallback handles permission errors
- [ ] Both stdout and stderr are captured
- [ ] Editor is responsive and usable
- [ ] Window close terminates processes

---

## Troubleshooting

### Keywords not highlighting
- Ensure "Kotlin" is selected in Language dropdown
- Check that you're using one of these 10 keywords: `fun`, `val`, `var`, `if`, `else`, `for`, `while`, `return`, `class`, `object`

### Errors not clickable
- Verify error message contains pattern like `script.kts:LINE:COL:` or `script.swift:LINE:COL:`
- Some Swift error formats may vary by Swift version

### Swift not working
- Ensure Swift is installed: `swift --version`
- Check that `/usr/bin/env swift` is accessible from terminal

### Kotlin permission denied persists
- Verify kotlinc is in PATH: `which kotlinc`
- Or ensure Gradle cache kotlinc exists: `~/.gradle/caches/.../kotlinc/bin/kotlinc`
- The fallback should catch this automatically

---

## Performance Notes

- The keyword highlighter runs on every keystroke (lightweight regex)
- For very large scripts (>10,000 lines), highlighting may have slight delay
- Output streaming handles long-running processes efficiently
- Multiple concurrent runs are not supported (by design)


