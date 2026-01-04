# Feature Testing Checklist

Print this page and check off each feature as you test it.

---

## Prerequisites
- [ ] Application starts: `./run.sh` or `./gradlew run`
- [ ] Window opens with editor on left, output on right
- [ ] Controls visible: Run, Stop, Language selector, Status, Exit

---

## Core Features (Required)

### Editor & Output Panes
- [ ] Editor pane visible and editable
- [ ] Output pane visible on right side
- [ ] Split pane divider can be dragged to resize

### Script Execution (Kotlin)
- [ ] Can type in editor
- [ ] Click Run executes script
- [ ] Output appears in right pane
- [ ] Default script prints "Hello from script"

### Long-Running Scripts
- [ ] Run test-scripts/2-streaming-output.kts
- [ ] Output appears line-by-line (not all at once)
- [ ] 10 lines appear with ~500ms delays between them

### Error Display
- [ ] Run test-scripts/3-clickable-error.kts
- [ ] Error message appears in output
- [ ] Error line is blue and underlined
- [ ] Exit code is non-zero and RED

### Running Indicator
- [ ] Status shows "Idle" when not running
- [ ] Status shows "Running…" during execution
- [ ] Run button disabled while running
- [ ] Stop button enabled while running

### Exit Code Display
- [ ] Run successful script → Exit: 0 in BLACK
- [ ] Run failed script → Exit: 1+ in RED
- [ ] Exit code updates after script completes

---

## Enhanced Features (Optional - Both Implemented!)

### Keyword Highlighting
- [ ] Type `fun` → appears blue and bold
- [ ] Type `val` → appears blue and bold
- [ ] Type `var` → appears blue and bold
- [ ] Type `if` → appears blue and bold
- [ ] Type `else` → appears blue and bold
- [ ] Type `for` → appears blue and bold
- [ ] Type `while` → appears blue and bold
- [ ] Type `return` → appears blue and bold
- [ ] Type `class` → appears blue and bold
- [ ] Type `object` → appears blue and bold
- [ ] Highlighting updates as you type

### Clickable Error Navigation
- [ ] Run script with error
- [ ] Error line in output is blue/underlined
- [ ] Click error line
- [ ] Editor cursor jumps to error location
- [ ] Line and column match error message

---

## Bonus Features

### Language Selector
- [ ] Language dropdown visible in controls
- [ ] Can select "Kotlin"
- [ ] Can select "Swift"
- [ ] Selection persists between runs

### Swift Support
- [ ] Select "Swift" language
- [ ] Run test-scripts/6-swift-basic.swift
- [ ] Swift output appears
- [ ] Exit: 0 displayed
- [ ] Swift errors also clickable (test-scripts/7-swift-error.swift)

### Stop Button
- [ ] Run test-scripts/8-long-running.kts
- [ ] Wait for 2-3 lines of output
- [ ] Click Stop button
- [ ] Output stops immediately
- [ ] "Process stopped" message appears
- [ ] Status returns to "Idle"

### Stream Capture
- [ ] Run test-scripts/9-stdout-stderr.kts
- [ ] Both stdout lines appear ("This is STDOUT")
- [ ] Both stderr lines appear ("This is STDERR")
- [ ] All output captured in output pane

### Process Cleanup
- [ ] Run long script (test-scripts/8-long-running.kts)
- [ ] Close window with X button while running
- [ ] Window closes immediately
- [ ] No zombie processes left (check Activity Monitor)

### Kotlinc Fallback
- [ ] Run any Kotlin script
- [ ] Script executes successfully
- [ ] No "Permission denied" errors
- [ ] Fallback is automatic if needed

---

## Edge Cases

### Empty Script
- [ ] Clear editor completely
- [ ] Click Run
- [ ] No crash, graceful handling

### Rapid Runs
- [ ] Click Run
- [ ] Immediately click Run again (should do nothing)
- [ ] Wait for first to finish
- [ ] Click Run again (should work)

### Large Output
- [ ] Run script that prints 100+ lines
- [ ] Output pane scrolls automatically
- [ ] No lag or freezing
- [ ] All lines visible

### Unicode Characters
- [ ] Type emoji in script: `println("👍")`
- [ ] Run script
- [ ] Emoji displays correctly in output

---

## Test Scripts Validation

Go through each test script:

- [ ] **1-keyword-highlight.kts** → All keywords blue/bold
- [ ] **2-streaming-output.kts** → Lines appear progressively
- [ ] **3-clickable-error.kts** → Click error → jumps to line 5
- [ ] **4-nonzero-exit.kts** → Exit: 1 in RED
- [ ] **5-successful-script.kts** → Exit: 0 in BLACK
- [ ] **6-swift-basic.swift** → Swift output appears
- [ ] **7-swift-error.swift** → Swift error clickable
- [ ] **8-long-running.kts** → Stop button works
- [ ] **9-stdout-stderr.kts** → Both streams captured

---

## Performance

- [ ] Application starts in < 3 seconds
- [ ] Typing is responsive (no lag)
- [ ] Highlighting updates smoothly
- [ ] Script starts in < 1 second
- [ ] Output streams in real-time
- [ ] Memory usage reasonable (<200MB)

---

## UI/UX

- [ ] Window size appropriate (1100x700)
- [ ] Controls are clearly labeled
- [ ] Buttons have appropriate states (enabled/disabled)
- [ ] Colors are readable (blue keywords, red errors)
- [ ] Layout is intuitive
- [ ] Resize works properly

---

## Documentation

- [ ] README.md exists and is comprehensive
- [ ] TESTING.md provides detailed test instructions
- [ ] QUICKSTART.md offers quick start guide
- [ ] test-scripts/README.md explains test scripts
- [ ] All 9 test scripts are present
- [ ] run.sh script is executable

---

## Final Validation

### All Required Features ✓
- [ ] Editor pane ✓
- [ ] Output pane ✓
- [ ] Script execution (kotlinc -script) ✓
- [ ] Long-running script support ✓
- [ ] Live output streaming ✓
- [ ] Error display ✓
- [ ] Running indicator ✓
- [ ] Exit code indication ✓

### Both Optional Features ✓
- [ ] Keyword highlighting ✓
- [ ] Clickable error navigation ✓

### Bonus Points ✓
- [ ] Swift language support ✓
- [ ] Stop button ✓
- [ ] Smart kotlinc discovery ✓
- [ ] Permission denied fallback ✓
- [ ] Process cleanup ✓

---

## Sign-Off

**Tester Name:** _________________

**Date:** _________________

**Total Features Tested:** _____ / 60+

**Pass/Fail:** _________________

**Notes:**
```
_______________________________________________
_______________________________________________
_______________________________________________
_______________________________________________
_______________________________________________
```

---

## Quick Reference

**Start app:** `./run.sh`

**Test order:**
1. Basic run (default script)
2. Keyword highlighting (type keywords)
3. Streaming (test script #2)
4. Errors (test script #3)
5. Swift (test script #6)
6. Stop (test script #8)

**Expected time:** 10-15 minutes for full checklist

**Critical tests:** Streaming output, clickable errors, exit codes

---

✨ **All features working?** → Ready for demo!

