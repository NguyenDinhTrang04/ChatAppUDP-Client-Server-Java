package common;

import java.util.Scanner;

/**
 * INTERACTIVE COMPLETE TEST - Test tương tác với cả POSITIVE và NEGATIVE cases
 * 
 * Cho phép:
 * - Chọn test case từ 1-6
 * - Nhập dữ liệu tùy ý
 * - Test cả trường hợp đúng VÀ lỗi
 * - Xem kết quả chi tiết
 * 
 * @author Nguyen Dinh Trang
 * @date November 17, 2025
 */
public class InteractiveCompleteTest {
    
    private static Scanner scanner = new Scanner(System.in);
    private static int totalTests = 0;
    private static int passedTests = 0;
    private static int failedTests = 0;
    
    public static void main(String[] args) {
        System.out.println(createLine(70));
        System.out.println("  INTERACTIVE COMPLETE TEST SUITE");
        System.out.println("  Test cả trường hợp ĐÚNG và LỖI");
        System.out.println(createLine(70));
        
        boolean running = true;
        
        while (running) {
            displayMenu();
            int choice = getChoice();
            
            switch (choice) {
                case 1:
                    testCase1_CreateSystemMessage();
                    break;
                case 2:
                    testCase2_NullSafety();
                    break;
                case 3:
                    testCase3_IsSystemMessage();
                    break;
                case 4:
                    testCase4_UserMessages();
                    break;
                case 5:
                    testCase5_SecurityTests();
                    break;
                case 6:
                    testCase6_SerializationTests();
                    break;
                case 0:
                    running = false;
                    displaySummary();
                    break;
                default:
                    System.out.println("❌ Lựa chọn không hợp lệ! Vui lòng chọn từ 0-6.");
            }
            
            if (choice >= 1 && choice <= 6) {
                System.out.println("\nNhấn Enter để tiếp tục...");
                scanner.nextLine();
            }
        }
        
        scanner.close();
        System.out.println("\n👋 Cảm ơn bạn đã sử dụng Interactive Test Suite!");
    }
    
    private static void displayMenu() {
        System.out.println("\n" + createLine(70));
        System.out.println("📋 MENU - CHỌN TEST CASE");
        System.out.println(createLine(70));
        System.out.println("  1. TC1: Create System Message (POSITIVE + NEGATIVE)");
        System.out.println("  2. TC2: Null Safety Tests (POSITIVE + NEGATIVE)");
        System.out.println("  3. TC3: isSystemMessage() Tests (POSITIVE + NEGATIVE)");
        System.out.println("  4. TC4: User Message Tests (POSITIVE + NEGATIVE)");
        System.out.println("  5. TC5: Security Tests (SQL Injection, XSS, Unicode...)");
        System.out.println("  6. TC6: Serialization Tests (POSITIVE + NEGATIVE)");
        System.out.println("  0. Thoát và xem tổng kết");
        System.out.println(createLine(70));
        System.out.print("👉 Nhập lựa chọn (0-6): ");
    }
    
    private static int getChoice() {
        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());
            return choice;
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    // ========================================================================
    // TC1: Create System Message
    // ========================================================================
    
    private static void testCase1_CreateSystemMessage() {
        System.out.println("\n" + createLine(70));
        System.out.println("TEST CASE 1: CREATE SYSTEM MESSAGE");
        System.out.println(createLine(70));
        
        System.out.println("\n📝 Nhập nội dung message (hoặc nhập 'null' để test null):");
        System.out.print("Content: ");
        String input = scanner.nextLine();
        
        String content = input.equals("null") ? null : input;
        
        System.out.println("\n" + createLine(70));
        System.out.println("🔍 ĐANG TEST...");
        System.out.println(createLine(70));
        
        // Test POSITIVE: Tạo message
        System.out.println("\n✅ POSITIVE TEST: Tạo system message");
        System.out.println("   Input: " + (content == null ? "null" : "\"" + content + "\""));
        
        try {
            Message msg = Utils.createSystemMessage(content);
            
            System.out.println("   Result:");
            System.out.println("   - Message created: " + (msg != null ? "✓" : "✗"));
            
            if (msg != null) {
                System.out.println("   - Type: " + msg.getType());
                System.out.println("   - Sender: " + msg.getSender());
                System.out.println("   - Content: " + (msg.getContent() == null ? "null" : "\"" + msg.getContent() + "\""));
                System.out.println("   - Timestamp: " + msg.getTimestamp());
                
                // Verify
                boolean typeOK = Constants.MESSAGE_TYPE_NOTIFICATION.equals(msg.getType());
                boolean senderOK = "SYSTEM".equals(msg.getSender());
                boolean contentOK = (content == null && msg.getContent() == null) || 
                                   (content != null && content.equals(msg.getContent()));
                boolean timestampOK = msg.getTimestamp() != null && 
                                     msg.getTimestamp().matches("\\d{2}:\\d{2}:\\d{2}");
                
                if (typeOK && senderOK && contentOK && timestampOK) {
                    System.out.println("\n   ✅ PASSED: Tất cả thuộc tính đúng!");
                    passedTests++;
                } else {
                    System.out.println("\n   ❌ FAILED: Có thuộc tính không đúng!");
                    if (!typeOK) System.out.println("      - Type sai");
                    if (!senderOK) System.out.println("      - Sender sai");
                    if (!contentOK) System.out.println("      - Content sai");
                    if (!timestampOK) System.out.println("      - Timestamp sai");
                    failedTests++;
                }
                totalTests++;
            }
        } catch (Exception e) {
            System.out.println("   ❌ EXCEPTION: " + e.getClass().getSimpleName());
            System.out.println("   Message: " + e.getMessage());
            failedTests++;
            totalTests++;
        }
        
        // Test NEGATIVE: Các trường hợp lỗi
        System.out.println("\n" + createLine(70));
        System.out.println("❌ NEGATIVE TESTS: Kiểm tra xử lý lỗi");
        System.out.println(createLine(70));
        
        // N1: Empty string
        testNegativeCase("Empty string", "", "Nên cảnh báo hoặc reject");
        
        // N2: Whitespace only
        testNegativeCase("Whitespace only", "   ", "Nên cảnh báo hoặc reject");
        
        // N3: Very long input (nếu input > 100 chars)
        if (content != null && content.length() > 100) {
            System.out.println("\n⚠️  STRESS TEST: Input dài " + content.length() + " ký tự");
            System.out.println("   - Cảnh báo: Nên giới hạn độ dài để tránh DoS attack");
        }
        
        // N4: Special characters
        if (content != null && (content.contains("<") || content.contains(">") || 
                                content.contains("'") || content.contains("\""))) {
            System.out.println("\n⚠️  SECURITY WARNING: Input chứa ký tự đặc biệt");
            System.out.println("   - Phát hiện: " + content);
            System.out.println("   - Cảnh báo: Có thể là XSS hoặc SQL injection attempt");
        }
        
        System.out.println("\n" + createLine(70));
        System.out.println("📊 Test Case 1 hoàn thành!");
        System.out.println("   Total: " + totalTests + " | Passed: " + passedTests + " | Failed: " + failedTests);
        System.out.println(createLine(70));
    }
    
    // ========================================================================
    // TC2: Null Safety Tests
    // ========================================================================
    
    private static void testCase2_NullSafety() {
        System.out.println("\n" + createLine(70));
        System.out.println("TEST CASE 2: NULL SAFETY TESTS");
        System.out.println(createLine(70));
        
        // POSITIVE: Xử lý null đúng cách
        System.out.println("\n✅ POSITIVE TEST: Tạo message với null content");
        Message msg = Utils.createSystemMessage(null);
        
        if (msg != null) {
            System.out.println("   ✓ Message created (not null)");
            System.out.println("   ✓ Type: " + msg.getType());
            System.out.println("   ✓ Sender: " + msg.getSender());
            System.out.println("   ✓ Content: " + msg.getContent());
            passedTests++;
        } else {
            System.out.println("   ✗ Message is null (unexpected)");
            failedTests++;
        }
        totalTests++;
        
        // NEGATIVE: Truy cập null content
        System.out.println("\n❌ NEGATIVE TEST: Truy cập null content");
        try {
            if (msg != null && msg.getContent() != null) {
                int len = msg.getContent().length();
                System.out.println("   ✓ Safe access, length = " + len);
            } else {
                System.out.println("   ✓ Content is null (handled safely)");
            }
            passedTests++;
        } catch (NullPointerException e) {
            System.out.println("   ❌ NullPointerException thrown!");
            System.out.println("   → RECOMMEND: Add null check before access");
            failedTests++;
        }
        totalTests++;
        
        // NEGATIVE: Serialize null content
        System.out.println("\n❌ NEGATIVE TEST: Serialize message với null content");
        try {
            String serialized = msg.serialize();
            System.out.println("   ✓ Serialized: " + serialized);
            System.out.println("   ✓ Handles null content in serialization");
            passedTests++;
        } catch (Exception e) {
            System.out.println("   ❌ Exception: " + e.getMessage());
            failedTests++;
        }
        totalTests++;
        
        System.out.println("\n" + createLine(70));
        System.out.println("📊 Test Case 2 hoàn thành!");
        System.out.println("   Total: " + totalTests + " | Passed: " + passedTests + " | Failed: " + failedTests);
        System.out.println(createLine(70));
    }
    
    // ========================================================================
    // TC3: isSystemMessage() Tests
    // ========================================================================
    
    private static void testCase3_IsSystemMessage() {
        System.out.println("\n" + createLine(70));
        System.out.println("TEST CASE 3: isSystemMessage() TESTS");
        System.out.println(createLine(70));
        
        System.out.println("\n📝 Nhập thông tin message để test:");
        System.out.print("Type (nhấn Enter để dùng NOTIFICATION): ");
        String typeInput = scanner.nextLine().trim();
        String type = typeInput.isEmpty() ? Constants.MESSAGE_TYPE_NOTIFICATION : typeInput;
        
        System.out.print("Sender (nhấn Enter để dùng SYSTEM): ");
        String senderInput = scanner.nextLine().trim();
        String sender = senderInput.isEmpty() ? "SYSTEM" : senderInput;
        
        System.out.print("Content: ");
        String content = scanner.nextLine();
        
        System.out.println("\n" + createLine(70));
        System.out.println("🔍 ĐANG TEST...");
        System.out.println(createLine(70));
        
        // POSITIVE: Test với system message hợp lệ
        if (Constants.MESSAGE_TYPE_NOTIFICATION.equals(type) && "SYSTEM".equals(sender)) {
            System.out.println("\n✅ POSITIVE TEST: Valid system message");
            Message msg = new Message(type, sender, content);
            boolean result = Utils.isSystemMessage(msg);
            
            System.out.println("   Input: type=" + type + ", sender=" + sender);
            System.out.println("   Result: " + result);
            
            if (result == true) {
                System.out.println("   ✅ PASSED: Nhận diện đúng system message");
                passedTests++;
            } else {
                System.out.println("   ❌ FAILED: Không nhận diện được system message");
                failedTests++;
            }
            totalTests++;
        }
        
        // Test với input đã nhập
        System.out.println("\n🔍 TEST VỚI INPUT CỦA BẠN:");
        Message msg = new Message(type, sender, content);
        boolean result = Utils.isSystemMessage(msg);
        
        System.out.println("   Type: " + type);
        System.out.println("   Sender: " + sender);
        System.out.println("   Content: " + content);
        System.out.println("   isSystemMessage(): " + result);
        
        boolean expected = Constants.MESSAGE_TYPE_NOTIFICATION.equals(type) && "SYSTEM".equals(sender);
        if (result == expected) {
            System.out.println("   ✅ PASSED: Kết quả đúng như mong đợi");
            passedTests++;
        } else {
            System.out.println("   ❌ FAILED: Kết quả không đúng (expected: " + expected + ")");
            failedTests++;
        }
        totalTests++;
        
        // NEGATIVE TESTS
        System.out.println("\n" + createLine(70));
        System.out.println("❌ NEGATIVE TESTS");
        System.out.println(createLine(70));
        
        // N1: Wrong type
        System.out.println("\n❌ TEST: Type = TEXT, Sender = SYSTEM");
        Message wrongType = new Message(Constants.MESSAGE_TYPE_TEXT, "SYSTEM", "test");
        boolean r1 = Utils.isSystemMessage(wrongType);
        System.out.println("   Result: " + r1 + " (expected: false)");
        if (!r1) {
            System.out.println("   ✅ PASSED");
            passedTests++;
        } else {
            System.out.println("   ❌ FAILED");
            failedTests++;
        }
        totalTests++;
        
        // N2: Wrong sender
        System.out.println("\n❌ TEST: Type = NOTIFICATION, Sender = Alice");
        Message wrongSender = new Message(Constants.MESSAGE_TYPE_NOTIFICATION, "Alice", "test");
        boolean r2 = Utils.isSystemMessage(wrongSender);
        System.out.println("   Result: " + r2 + " (expected: false)");
        if (!r2) {
            System.out.println("   ✅ PASSED");
            passedTests++;
        } else {
            System.out.println("   ❌ FAILED");
            failedTests++;
        }
        totalTests++;
        
        // N3: Null type
        System.out.println("\n❌ TEST: Type = null, Sender = SYSTEM");
        try {
            Message nullType = new Message(null, "SYSTEM", "test");
            boolean r3 = Utils.isSystemMessage(nullType);
            System.out.println("   Result: " + r3 + " (expected: false)");
            if (!r3) {
                System.out.println("   ✅ PASSED: Null type handled");
                passedTests++;
            } else {
                System.out.println("   ❌ FAILED");
                failedTests++;
            }
        } catch (NullPointerException e) {
            System.out.println("   ❌ NullPointerException!");
            System.out.println("   → RECOMMEND: Add null check");
            failedTests++;
        }
        totalTests++;
        
        // N4: Case sensitivity
        System.out.println("\n❌ TEST: Case sensitivity (notification vs NOTIFICATION)");
        Message lowerCase = new Message("notification", "SYSTEM", "test");
        boolean r4 = Utils.isSystemMessage(lowerCase);
        System.out.println("   Result: " + r4 + " (expected: false - case sensitive)");
        if (!r4) {
            System.out.println("   ✅ PASSED: Case sensitive check works");
            passedTests++;
        } else {
            System.out.println("   ⚠️  WARNING: Case insensitive (security risk!)");
            failedTests++;
        }
        totalTests++;
        
        System.out.println("\n" + createLine(70));
        System.out.println("📊 Test Case 3 hoàn thành!");
        System.out.println("   Total: " + totalTests + " | Passed: " + passedTests + " | Failed: " + failedTests);
        System.out.println(createLine(70));
    }
    
    // ========================================================================
    // TC4: User Message Tests
    // ========================================================================
    
    private static void testCase4_UserMessages() {
        System.out.println("\n" + createLine(70));
        System.out.println("TEST CASE 4: USER MESSAGE TESTS");
        System.out.println(createLine(70));
        
        System.out.println("\n📝 Nhập thông tin user message:");
        System.out.print("Sender (tên người gửi): ");
        String sender = scanner.nextLine().trim();
        
        System.out.print("Content (nội dung tin nhắn): ");
        String content = scanner.nextLine();
        
        System.out.println("\n" + createLine(70));
        System.out.println("🔍 ĐANG TEST...");
        System.out.println(createLine(70));
        
        // POSITIVE: User message không phải system message
        System.out.println("\n✅ POSITIVE TEST: User message should return false");
        Message userMsg = new Message(Constants.MESSAGE_TYPE_TEXT, sender, content);
        boolean result = Utils.isSystemMessage(userMsg);
        
        System.out.println("   Type: " + Constants.MESSAGE_TYPE_TEXT);
        System.out.println("   Sender: " + sender);
        System.out.println("   Content: " + content);
        System.out.println("   isSystemMessage(): " + result);
        
        if (!result) {
            System.out.println("   ✅ PASSED: User message không bị nhầm với system message");
            passedTests++;
        } else {
            System.out.println("   ❌ FAILED: User message bị nhận dạng sai là system message!");
            failedTests++;
        }
        totalTests++;
        
        // NEGATIVE TESTS
        System.out.println("\n" + createLine(70));
        System.out.println("❌ NEGATIVE TESTS");
        System.out.println(createLine(70));
        
        // N1: Empty sender
        System.out.println("\n❌ TEST: Empty sender");
        Message emptySender = new Message(Constants.MESSAGE_TYPE_TEXT, "", content);
        boolean r1 = Utils.isSystemMessage(emptySender);
        System.out.println("   Result: " + r1 + " (expected: false)");
        System.out.println("   ⚠️  Warning: Empty sender should be validated");
        if (!r1) {
            passedTests++;
        } else {
            failedTests++;
        }
        totalTests++;
        
        // N2: Null sender
        System.out.println("\n❌ TEST: Null sender (anonymous)");
        try {
            Message nullSender = new Message(Constants.MESSAGE_TYPE_TEXT, null, content);
            boolean r2 = Utils.isSystemMessage(nullSender);
            System.out.println("   Result: " + r2 + " (expected: false)");
            System.out.println("   ⚠️  Warning: Anonymous messages should be rejected");
            if (!r2) {
                passedTests++;
            } else {
                failedTests++;
            }
        } catch (Exception e) {
            System.out.println("   Exception: " + e.getMessage());
            failedTests++;
        }
        totalTests++;
        
        // N3: Fake SYSTEM sender (security test)
        System.out.println("\n❌ SECURITY TEST: User trying to fake SYSTEM sender");
        Message fakeSender = new Message(Constants.MESSAGE_TYPE_TEXT, "SYSTEM", "Fake message");
        boolean r3 = Utils.isSystemMessage(fakeSender);
        System.out.println("   Type: TEXT (not NOTIFICATION)");
        System.out.println("   Sender: SYSTEM (fake!)");
        System.out.println("   isSystemMessage(): " + r3);
        
        if (!r3) {
            System.out.println("   ✅ PASSED: Fake SYSTEM sender detected (checks BOTH type AND sender)");
            passedTests++;
        } else {
            System.out.println("   ❌ FAILED: Security hole! Only checks sender, not type!");
            failedTests++;
        }
        totalTests++;
        
        System.out.println("\n" + createLine(70));
        System.out.println("📊 Test Case 4 hoàn thành!");
        System.out.println("   Total: " + totalTests + " | Passed: " + passedTests + " | Failed: " + failedTests);
        System.out.println(createLine(70));
    }
    
    // ========================================================================
    // TC5: Security Tests
    // ========================================================================
    
    private static void testCase5_SecurityTests() {
        System.out.println("\n" + createLine(70));
        System.out.println("TEST CASE 5: SECURITY TESTS");
        System.out.println(createLine(70));
        
        System.out.println("\n🔴 Security test menu:");
        System.out.println("  1. SQL Injection test");
        System.out.println("  2. XSS Attack test");
        System.out.println("  3. Unicode bypass test");
        System.out.println("  4. Buffer overflow test");
        System.out.println("  5. Custom security test (nhập tùy ý)");
        System.out.print("\n👉 Chọn test (1-5): ");
        
        int choice = getChoice();
        
        String maliciousSender = "";
        String attackType = "";
        
        switch (choice) {
            case 1:
                maliciousSender = "SYSTEM'; DROP TABLE messages;--";
                attackType = "SQL Injection";
                break;
            case 2:
                maliciousSender = "<script>alert('SYSTEM')</script>";
                attackType = "XSS Attack";
                break;
            case 3:
                maliciousSender = "SYS\u200BTEM"; // Zero-width space
                attackType = "Unicode Bypass";
                break;
            case 4:
                StringBuilder huge = new StringBuilder();
                for (int i = 0; i < 10000; i++) {
                    huge.append("SYSTEM");
                }
                maliciousSender = huge.toString();
                attackType = "Buffer Overflow / DoS";
                break;
            case 5:
                System.out.print("Nhập malicious sender: ");
                maliciousSender = scanner.nextLine();
                attackType = "Custom Attack";
                break;
            default:
                System.out.println("Lựa chọn không hợp lệ!");
                return;
        }
        
        System.out.println("\n" + createLine(70));
        System.out.println("🔴 TESTING: " + attackType);
        System.out.println(createLine(70));
        
        System.out.println("\n❌ ATTACK ATTEMPT:");
        System.out.println("   Type: NOTIFICATION");
        System.out.println("   Sender: " + (maliciousSender.length() > 50 ? 
                           maliciousSender.substring(0, 50) + "... (" + maliciousSender.length() + " chars)" :
                           maliciousSender));
        System.out.println("   Content: Malicious payload");
        
        Message attackMsg = new Message(Constants.MESSAGE_TYPE_NOTIFICATION, maliciousSender, "Attack");
        boolean result = Utils.isSystemMessage(attackMsg);
        
        System.out.println("\n🛡️  SECURITY CHECK:");
        System.out.println("   isSystemMessage(): " + result);
        
        if (!result) {
            System.out.println("   ✅ PASSED: " + attackType + " BLOCKED!");
            System.out.println("   🛡️  System is protected against this attack");
            passedTests++;
        } else {
            System.out.println("   ❌ FAILED: SECURITY VULNERABILITY!");
            System.out.println("   ⚠️  " + attackType + " was NOT detected!");
            failedTests++;
        }
        totalTests++;
        
        // Additional check for exact match
        System.out.println("\n📊 ANALYSIS:");
        System.out.println("   Actual sender: \"" + maliciousSender + "\"");
        System.out.println("   Expected: \"SYSTEM\"");
        System.out.println("   Exact match: " + "SYSTEM".equals(maliciousSender));
        System.out.println("\n   Recommendation: Always use exact string comparison");
        System.out.println("   Protection: Type checking + Exact sender match");
        
        System.out.println("\n" + createLine(70));
        System.out.println("📊 Test Case 5 hoàn thành!");
        System.out.println("   Total: " + totalTests + " | Passed: " + passedTests + " | Failed: " + failedTests);
        System.out.println(createLine(70));
    }
    
    // ========================================================================
    // TC6: Serialization Tests
    // ========================================================================
    
    private static void testCase6_SerializationTests() {
        System.out.println("\n" + createLine(70));
        System.out.println("TEST CASE 6: SERIALIZATION TESTS");
        System.out.println(createLine(70));
        
        System.out.println("\n📝 Nhập content cho system message:");
        System.out.print("Content: ");
        String content = scanner.nextLine();
        
        System.out.println("\n" + createLine(70));
        System.out.println("🔍 ĐANG TEST...");
        System.out.println(createLine(70));
        
        // POSITIVE: Serialize -> Deserialize
        System.out.println("\n✅ POSITIVE TEST: Serialize -> Deserialize");
        Message original = Utils.createSystemMessage(content);
        
        System.out.println("\n   ORIGINAL MESSAGE:");
        System.out.println("   - Type: " + original.getType());
        System.out.println("   - Sender: " + original.getSender());
        System.out.println("   - Content: " + original.getContent());
        System.out.println("   - Timestamp: " + original.getTimestamp());
        
        String serialized = original.serialize();
        System.out.println("\n   SERIALIZED: " + serialized);
        
        Message deserialized = Message.deserialize(serialized);
        
        System.out.println("\n   DESERIALIZED MESSAGE:");
        if (deserialized != null) {
            System.out.println("   - Type: " + deserialized.getType());
            System.out.println("   - Sender: " + deserialized.getSender());
            System.out.println("   - Content: " + deserialized.getContent());
            System.out.println("   - Timestamp: " + deserialized.getTimestamp());
            
            boolean typeOK = original.getType().equals(deserialized.getType());
            boolean senderOK = original.getSender().equals(deserialized.getSender());
            boolean contentOK = (original.getContent() == null && deserialized.getContent() == null) ||
                               (original.getContent() != null && original.getContent().equals(deserialized.getContent()));
            boolean stillSystem = Utils.isSystemMessage(deserialized);
            
            System.out.println("\n   VERIFICATION:");
            System.out.println("   - Type match: " + typeOK);
            System.out.println("   - Sender match: " + senderOK);
            System.out.println("   - Content match: " + contentOK);
            System.out.println("   - Still system message: " + stillSystem);
            
            if (typeOK && senderOK && contentOK && stillSystem) {
                System.out.println("\n   ✅ PASSED: Serialization works correctly!");
                passedTests++;
            } else {
                System.out.println("\n   ❌ FAILED: Data corrupted during serialization!");
                failedTests++;
            }
        } else {
            System.out.println("   ❌ FAILED: Deserialized message is null!");
            failedTests++;
        }
        totalTests++;
        
        // NEGATIVE TESTS
        System.out.println("\n" + createLine(70));
        System.out.println("❌ NEGATIVE TESTS");
        System.out.println(createLine(70));
        
        // N1: Corrupt data
        System.out.println("\n❌ TEST: Corrupt serialized data");
        String corruptData = "INVALID|||CORRUPT|||DATA";
        try {
            Message corrupted = Message.deserialize(corruptData);
            if (corrupted == null) {
                System.out.println("   ✓ Returns null for corrupt data");
                passedTests++;
            } else {
                System.out.println("   ⚠️  Warning: Accepted corrupt data!");
                System.out.println("   Result: " + corrupted.getType() + " / " + corrupted.getSender());
                failedTests++;
            }
        } catch (Exception e) {
            System.out.println("   ✓ Exception thrown: " + e.getClass().getSimpleName());
            passedTests++;
        }
        totalTests++;
        
        // N2: Missing delimiter
        System.out.println("\n❌ TEST: Missing delimiter");
        String noDelimiter = "NOTIFICATION SYSTEM test 12:00:00";
        try {
            Message noDelim = Message.deserialize(noDelimiter);
            if (noDelim == null) {
                System.out.println("   ✓ Rejected invalid format");
                passedTests++;
            } else {
                System.out.println("   ⚠️  Warning: Accepted invalid format!");
                failedTests++;
            }
        } catch (Exception e) {
            System.out.println("   ✓ Exception for invalid format");
            passedTests++;
        }
        totalTests++;
        
        // N3: Null input
        System.out.println("\n❌ TEST: Null serialized string");
        try {
            Message nullInput = Message.deserialize(null);
            if (nullInput == null) {
                System.out.println("   ✓ Returns null for null input");
                passedTests++;
            } else {
                System.out.println("   ⚠️  Created message from null!");
                failedTests++;
            }
        } catch (NullPointerException e) {
            System.out.println("   ❌ NullPointerException!");
            System.out.println("   → RECOMMEND: Add null check in deserialize()");
            failedTests++;
        }
        totalTests++;
        
        // N4: Data tampering
        System.out.println("\n❌ TEST: Data tampering detection");
        String tampered = serialized.replace("SYSTEM", "HACKER");
        Message tamperedMsg = Message.deserialize(tampered);
        
        if (tamperedMsg != null) {
            boolean isSystem = Utils.isSystemMessage(tamperedMsg);
            System.out.println("   Original: " + serialized);
            System.out.println("   Tampered: " + tampered);
            System.out.println("   isSystemMessage(): " + isSystem);
            
            if (!isSystem) {
                System.out.println("   ✅ PASSED: Tampering detected!");
                passedTests++;
            } else {
                System.out.println("   ❌ FAILED: Tampered message accepted as system!");
                failedTests++;
            }
        } else {
            System.out.println("   ✓ Rejected tampered data");
            passedTests++;
        }
        totalTests++;
        
        System.out.println("\n" + createLine(70));
        System.out.println("📊 Test Case 6 hoàn thành!");
        System.out.println("   Total: " + totalTests + " | Passed: " + passedTests + " | Failed: " + failedTests);
        System.out.println(createLine(70));
    }
    
    // ========================================================================
    // Helper Methods
    // ========================================================================
    
    private static void testNegativeCase(String testName, String input, String note) {
        System.out.println("\n❌ NEGATIVE: " + testName);
        System.out.println("   Input: \"" + input + "\"");
        
        try {
            Message msg = Utils.createSystemMessage(input);
            if (msg != null) {
                System.out.println("   ⚠️  Message created");
                System.out.println("   Note: " + note);
            }
        } catch (Exception e) {
            System.out.println("   Exception: " + e.getMessage());
        }
    }
    
    private static void displaySummary() {
        System.out.println("\n" + createLine(70));
        System.out.println("📊 TỔNG KẾT TEST SESSION");
        System.out.println(createLine(70));
        System.out.println("  Total tests run: " + totalTests);
        System.out.println("  ✅ Passed: " + passedTests);
        System.out.println("  ❌ Failed: " + failedTests);
        
        if (totalTests > 0) {
            double passRate = (passedTests * 100.0) / totalTests;
            System.out.println("  📈 Pass rate: " + String.format("%.1f%%", passRate));
            
            if (passRate == 100) {
                System.out.println("\n  🎉 HOÀN HẢO! Tất cả tests đều PASSED!");
            } else if (passRate >= 80) {
                System.out.println("\n  👍 TỐT! Hầu hết tests đều passed.");
            } else if (passRate >= 60) {
                System.out.println("\n  ⚠️  CẦN CẢI THIỆN! Nhiều tests failed.");
            } else {
                System.out.println("\n  ❌ CẦN REVIEW! Quá nhiều lỗi.");
            }
        }
        System.out.println(createLine(70));
    }
    
    private static String createLine(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append("=");
        }
        return sb.toString();
    }
}
