# PowerShell Script to run individual JUnit tests
# Usage: .\run-single-test.ps1 -TestMethod "testAddClient_Success"

param(
    [Parameter(Mandatory=$true)]
    [string]$TestMethod
)

$className = "server.ServerControllerClientTest"
$classpath = "bin;test-bin;lib/*"

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Running Test: $TestMethod" -ForegroundColor Yellow
Write-Host "Class: $className" -ForegroundColor Gray
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Create a temporary test runner
$tempRunner = @"
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.Request;

public class SingleTestRunner {
    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.err.println("Usage: SingleTestRunner <class> <method>");
            System.exit(1);
        }
        
        String className = args[0];
        String methodName = args[1];
        
        Class<?> testClass = Class.forName(className);
        Request request = Request.method(testClass, methodName);
        
        JUnitCore junit = new JUnitCore();
        Result result = junit.run(request);
        
        System.out.println("\n========================================");
        System.out.println("Test Results:");
        System.out.println("========================================");
        System.out.println("Tests run: " + result.getRunCount());
        System.out.println("Failures: " + result.getFailureCount());
        System.out.println("Time: " + result.getRunTime() + "ms");
        
        if (result.wasSuccessful()) {
            System.out.println("\nSTATUS: PASSED");
        } else {
            System.out.println("\nSTATUS: FAILED");
            System.out.println("\nFailure Details:");
            for (Failure failure : result.getFailures()) {
                System.out.println(failure.toString());
            }
        }
        
        System.exit(result.wasSuccessful() ? 0 : 1);
    }
}
"@

# Save temporary runner (without BOM)
$Utf8NoBomEncoding = New-Object System.Text.UTF8Encoding $False
[System.IO.File]::WriteAllText("SingleTestRunner.java", $tempRunner, $Utf8NoBomEncoding)

# Compile temporary runner
javac -cp $classpath SingleTestRunner.java

if ($LASTEXITCODE -ne 0) {
    Write-Host "Failed to compile test runner" -ForegroundColor Red
    exit 1
}

# Run the specific test
java -cp ".;$classpath" SingleTestRunner $className $TestMethod

# Cleanup
Remove-Item "SingleTestRunner.java" -ErrorAction SilentlyContinue
Remove-Item "SingleTestRunner.class" -ErrorAction SilentlyContinue

Write-Host ""
