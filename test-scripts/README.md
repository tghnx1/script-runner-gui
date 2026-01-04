# Test Scripts Collection

This directory contains ready-to-use test scripts for validating all features of the Script Runner GUI.

## Quick Start

1. Open the Script Runner application: `./gradlew run`
2. Copy the content of any test script below into the editor
3. Select the appropriate language (Kotlin or Swift)
4. Click Run
5. Observe the expected behavior

## Test Scripts Overview

| File | Language | Tests | Expected Result |
|------|----------|-------|-----------------|
| `1-keyword-highlight.kts` | Kotlin | Keyword highlighting | Keywords appear in blue and bold |
| `2-streaming-output.kts` | Kotlin | Live output streaming | Lines appear progressively with delays |
| `3-clickable-error.kts` | Kotlin | Error navigation | Click error → jumps to line 5 |
| `4-nonzero-exit.kts` | Kotlin | Exit code display | Exit: 1 (or similar) in RED |
| `5-successful-script.kts` | Kotlin | Success case | Exit: 0 in BLACK |
| `6-swift-basic.swift` | Swift | Swift execution | Output printed, Exit: 0 |
| `7-swift-error.swift` | Swift | Swift error handling | Clickable error, non-zero exit |
| `8-long-running.kts` | Kotlin | Stop button | Use Stop to terminate |
| `9-stdout-stderr.kts` | Kotlin | Stream capturing | Both stdout and stderr appear |

## Detailed Test Instructions

### Test 1: Keyword Highlighting
```bash
File: 1-keyword-highlight.kts
```
**Steps:**
1. Select "Kotlin" language
2. Open or copy `1-keyword-highlight.kts` into editor
3. Observe the keywords: `fun`, `val`, `var`, `if`, `else`, `for`, `while`, `return`, `class`, `object`

**Expected:** All these keywords should be **blue and bold**

---

### Test 2: Streaming Output
```bash
File: 2-streaming-output.kts
```
**Steps:**
1. Run the script
2. Watch the output pane

**Expected:** 
- Lines appear **one by one** with ~500ms delays
- Not all at once at the end

---

### Test 3: Clickable Errors
```bash
File: 3-clickable-error.kts
```
**Steps:**
1. Run the script
2. Look for error line in output (should be blue and underlined)
3. Click the error line

**Expected:** 
- Editor cursor jumps to line 5 where `undefinedFunction()` is called
- Exit code is non-zero and red

---

### Test 4: Non-Zero Exit Code
```bash
File: 4-nonzero-exit.kts
```
**Steps:**
1. Run the script

**Expected:** 
- Script throws exception
- Exit label shows **"Exit: 1"** (or similar) in **RED**

---

### Test 5: Successful Script
```bash
File: 5-successful-script.kts
```
**Steps:**
1. Run the script

**Expected:** 
- Script completes without errors
- Exit label shows **"Exit: 0"** in **BLACK**

---

### Test 6: Swift Basic
```bash
File: 6-swift-basic.swift
```
**Steps:**
1. Select **"Swift"** language
2. Run the script

**Expected:** 
- Output shows "Hello from Swift!" and count output
- Exit: 0 in black

---

### Test 7: Swift Error
```bash
File: 7-swift-error.swift
```
**Steps:**
1. Select **"Swift"** language
2. Run the script
3. Click the error line in output

**Expected:** 
- Error is clickable
- Cursor jumps to line 4
- Exit code is non-zero and red

---

### Test 8: Stop Button
```bash
File: 8-long-running.kts
```
**Steps:**
1. Run the script
2. Wait for 2-3 lines of output
3. Click **Stop** button

**Expected:** 
- Output stops immediately
- Message "Process stopped" appears
- Status returns to "Idle"

---

### Test 9: Stdout and Stderr
```bash
File: 9-stdout-stderr.kts
```
**Steps:**
1. Run the script

**Expected:** 
- Both "This is STDOUT" and "This is STDERR" lines appear in output
- All output is captured regardless of stream

---

## How to Use These Tests

### Option 1: Copy and Paste
Open each `.kts` or `.swift` file, copy its content, and paste into the Script Runner editor.

### Option 2: Quick Command-Line Test
You can verify the scripts work independently:
```bash
# Kotlin script
kotlinc -script test-scripts/1-keyword-highlight.kts

# Swift script
swift test-scripts/6-swift-basic.swift
```

### Option 3: Batch Testing
Test all scripts in sequence to ensure comprehensive coverage of features.

## Troubleshooting

**Keywords not highlighting?**
- Ensure "Kotlin" is selected in the language dropdown
- Type manually to trigger the document listener

**Errors not clickable?**
- Verify the error message format includes `script.kts:LINE:COL:` or `script.swift:LINE:COL:`
- Some compiler versions may format differently

**Swift not working?**
- Check Swift installation: `swift --version`
- Ensure `/usr/bin/env swift` is accessible

## Test Coverage

These scripts validate:
- ✅ Keyword highlighting (10 Kotlin keywords)
- ✅ Live streaming output
- ✅ Clickable error navigation
- ✅ Exit code display (zero and non-zero)
- ✅ Exit code color coding (red for errors)
- ✅ Swift language support
- ✅ Stop button functionality
- ✅ Stdout and stderr capture
- ✅ Long-running process handling
- ✅ Process cleanup on stop

## Additional Test Ideas

**Stress Test:** Create a script that prints 1000 lines rapidly
**Unicode Test:** Test scripts with emoji and international characters
**Multiline Errors:** Test compiler errors that span multiple lines
**Syntax Highlighting Edge Cases:** Keywords in strings, comments, etc.


