// Test 5: Successful Script (Zero Exit Code)
// This script completes successfully
// The Exit label should show "Exit: 0" in BLACK

println("=".repeat(50))
println("Running successful script test")
println("=".repeat(50))

val numbers = listOf(1, 2, 3, 4, 5)
println("\nProcessing numbers: $numbers")

val sum = numbers.sum()
println("Sum: $sum")

val doubled = numbers.map { it * 2 }
println("Doubled: $doubled")

println("\n✓ Script completed successfully!")
println("Expected: Exit code 0 (black text)")

