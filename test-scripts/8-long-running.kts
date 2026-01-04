// Test 8: Long Running Script (Stop Button Test)
// This script runs for a long time
// Click STOP button after a few seconds to test termination

println("Starting long-running script...")
println("Click STOP button to terminate")
println()

for (i in 1..100) {
    println("[$i/100] Still running... (${i}%)")
    Thread.sleep(1000)  // 1 second delay
}

println("Completed all 100 iterations")
println("(You probably stopped it before reaching here)")

