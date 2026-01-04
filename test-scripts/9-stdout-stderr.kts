// Test 9: Stdout and Stderr
// Tests that both standard output and standard error are captured

println("This is STDOUT - line 1")
System.err.println("This is STDERR - error line 1")
println("This is STDOUT - line 2")
System.err.println("This is STDERR - error line 2")
println("This is STDOUT - line 3")

println("\nBoth stdout and stderr should appear in the output pane")
println("(Order might vary slightly due to buffering)")

