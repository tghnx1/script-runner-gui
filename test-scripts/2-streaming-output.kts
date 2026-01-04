// Test 2: Live Streaming Output
// This script prints output progressively with delays
// You should see each line appear one at a time, not all at once

println("Starting streaming test...")
println()

for (i in 1..10) {
    println("[$i/10] Processing item $i...")
    Thread.sleep(500)  // 500ms delay between each line
}

println()
println("Streaming test complete!")
println("If you saw lines appearing gradually, the test passed!")

