#!/bin/bash
# Simple build and run script that bypasses Gradle

echo "Building Script Runner GUI..."
kotlinc src/main/kotlin/Main.kt -include-runtime -d build/script-runner.jar

if [ $? -eq 0 ]; then
    echo "Build successful! Starting application..."
    java -jar build/script-runner.jar
else
    echo "Build failed!"
    exit 1
fi

