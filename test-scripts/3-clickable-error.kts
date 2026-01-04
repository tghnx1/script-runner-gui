// Test 3: Clickable Error Navigation
// This script has an intentional error on line 9
// After running, click the error in the output pane to jump to the error location

println("Line 1: Starting...")
println("Line 2: Everything OK so far")
println("Line 3: Still going...")
println("Line 4: About to encounter error...")
undefinedFunction()  // INTENTIONAL ERROR - line 9 (for testing clickable errors)
println("Line 6: This won't execute")

