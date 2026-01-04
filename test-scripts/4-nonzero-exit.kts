// Test 4: Non-Zero Exit Code
// This script throws an exception, resulting in a non-zero exit code
// The Exit label should turn RED and show a non-zero value

println("Starting test...")
println("About to throw an exception...")
Thread.sleep(500)

throw RuntimeException("Intentional error for testing!")

// This line won't be reached
println("This message won't appear")

