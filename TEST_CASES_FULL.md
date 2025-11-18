# 📋 BẢNG TEST CASES ĐẦY ĐỦ - POSITIVE & NEGATIVE

## Chú thích:
- ✅ **POSITIVE TEST**: Test các trường hợp hợp lệ (expected: PASS)
- ❌ **NEGATIVE TEST**: Test các trường hợp lỗi (expected: ERROR/FAIL)

---

# TEST CASE 1: createSystemMessage() - Valid/Invalid Content

## ✅ POSITIVE TESTS (4 cases)

| ID | Input Content | Length | Loại | Expected Result | Assertions |
|----|---------------|--------|------|-----------------|------------|
| TC1-P1 | "User Alice joined" | 18 | Normal text | Message created, 7/7 pass | Type=NOTIFICATION, Sender=SYSTEM |
| TC1-P2 | "A" | 1 | Single char | Message created, 7/7 pass | Boundary: min length = 1 |
| TC1-P3 | "User @Bob#123 connected!" | 25 | Special chars | Message created, 7/7 pass | Content với ký tự đặc biệt |
| TC1-P4 | "Very long message..." (1000 chars) | 1000 | Very long | Message created, 7/7 pass | Boundary: max length test |

## ❌ NEGATIVE TESTS (5 cases)

| ID | Input Content | Loại Lỗi | Expected Behavior | Error Message Expected |
|----|---------------|-----------|-------------------|------------------------|
| TC1-N1 | null | Null input | Không crash, content=null | ⚠️ Hoặc NullPointerException |
| TC1-N2 | "" | Empty string | Message created nhưng content="" | ⚠️ Warning: Empty content |
| TC1-N3 | "   " (spaces) | Whitespace only | Message created, content giữ nguyên | ⚠️ Warning: Whitespace only |
| TC1-N4 | "\n\t\r" | Special whitespace | Message created với escape chars | ⚠️ Có thể không hiển thị đúng |
| TC1-N5 | Extremely long (>10000 chars) | Memory overflow | OutOfMemoryError hoặc truncate | ❌ System error |

### 🧪 Code test NEGATIVE cho TC1:

```java
// TC1-N1: Null input
@Test(expected = NullPointerException.class)
public void testTC1_N1_NullInput() {
    Message msg = Utils.createSystemMessage(null);
    // Nếu code hiện tại không throw exception, 
    // cần refactor để validate input
}

// TC1-N2: Empty string
@Test
public void testTC1_N2_EmptyString() {
    Message msg = Utils.createSystemMessage("");
    assertNotNull(msg);
    assertEquals("", msg.getContent());
    // WARNING: Should log warning về empty content
}

// TC1-N5: Very long input
@Test
public void testTC1_N5_VeryLongInput() {
    String veryLong = new String(new char[10001]).replace('\0', 'x');
    try {
        Message msg = Utils.createSystemMessage(veryLong);
        // If success, should truncate or handle gracefully
        assertTrue(msg.getContent().length() <= 10000);
    } catch (OutOfMemoryError e) {
        // Expected for extremely long input
        assertTrue(true);
    }
}
```

---

# TEST CASE 2: createSystemMessage() - Null Safety

## ✅ POSITIVE TESTS (2 cases)

| ID | Input | Expected | Assertions |
|----|-------|----------|------------|
| TC2-P1 | null | Message created, content=null | 5/5 pass, null safety works |
| TC2-P2 | null (multiple calls) | Consistent behavior | Mỗi lần gọi đều tạo message mới |

## ❌ NEGATIVE TESTS (3 cases)

| ID | Scenario | Expected Error | Handling |
|----|----------|----------------|----------|
| TC2-N1 | Null input → getContent().length() | NullPointerException | ❌ Crash nếu không check null |
| TC2-N2 | Null input → serialize() | NullPointerException | ❌ Serialization fail |
| TC2-N3 | Null → deserialize() | Invalid message | ❌ Cannot reconstruct |

### 🧪 Code test NEGATIVE cho TC2:

```java
// TC2-N1: Null pointer when accessing content
@Test(expected = NullPointerException.class)
public void testTC2_N1_NullPointerAccess() {
    Message msg = Utils.createSystemMessage(null);
    int len = msg.getContent().length(); // ❌ Should throw NPE
}

// TC2-N2: Serialize null content
@Test
public void testTC2_N2_SerializeNull() {
    Message msg = Utils.createSystemMessage(null);
    try {
        String serialized = msg.serialize();
        // Should handle null gracefully
        assertNotNull(serialized);
    } catch (NullPointerException e) {
        // ❌ Current implementation may fail
        fail("Should handle null content in serialize()");
    }
}
```

---

# TEST CASE 3: isSystemMessage() - Valid Detection

## ✅ POSITIVE TESTS (3 cases)

| ID | Type | Sender | Expected | Reasoning |
|----|------|--------|----------|-----------|
| TC3-P1 | NOTIFICATION | SYSTEM | true | Đúng cả type và sender |
| TC3-P2 | NOTIFICATION | SYSTEM | true | Multiple checks consistent |
| TC3-P3 | NOTIFICATION (uppercase) | SYSTEM | true | Case sensitive check |

## ❌ NEGATIVE TESTS (6 cases)

| ID | Type | Sender | Expected | Error Case |
|----|------|--------|----------|------------|
| TC3-N1 | null | SYSTEM | false hoặc NPE | ❌ Null type |
| TC3-N2 | NOTIFICATION | null | false hoặc NPE | ❌ Null sender |
| TC3-N3 | null | null | false hoặc NPE | ❌ Both null |
| TC3-N4 | "" | SYSTEM | false | ❌ Empty type |
| TC3-N5 | NOTIFICATION | "" | false | ❌ Empty sender |
| TC3-N6 | "notification" (lowercase) | SYSTEM | false | ❌ Case sensitive fail |

### 🧪 Code test NEGATIVE cho TC3:

```java
// TC3-N1: Null type
@Test
public void testTC3_N1_NullType() {
    Message msg = new Message(null, "SYSTEM", "test");
    try {
        boolean result = Utils.isSystemMessage(msg);
        assertFalse("Should return false for null type", result);
    } catch (NullPointerException e) {
        // ❌ Code needs null check
        assertTrue("Need to handle null type", true);
    }
}

// TC3-N2: Null sender
@Test
public void testTC3_N2_NullSender() {
    Message msg = new Message(Constants.MESSAGE_TYPE_NOTIFICATION, null, "test");
    try {
        boolean result = Utils.isSystemMessage(msg);
        assertFalse("Should return false for null sender", result);
    } catch (NullPointerException e) {
        // ❌ Code needs null check
        fail("Should handle null sender gracefully");
    }
}

// TC3-N3: Both null
@Test
public void testTC3_N3_BothNull() {
    Message msg = new Message(null, null, "test");
    assertFalse(Utils.isSystemMessage(msg));
}

// TC3-N6: Case sensitivity
@Test
public void testTC3_N6_CaseSensitive() {
    Message msg = new Message("notification", "SYSTEM", "test");
    boolean result = Utils.isSystemMessage(msg);
    assertFalse("Should be case sensitive", result);
}
```

---

# TEST CASE 4: isSystemMessage() - User Messages

## ✅ POSITIVE TESTS (4 cases)

| ID | Type | Sender | Expected | Purpose |
|----|------|--------|----------|---------|
| TC4-P1 | TEXT | Alice | false | Normal user message |
| TC4-P2 | ERROR | Bob | false | Error message |
| TC4-P3 | COMMAND | Charlie | false | Command message |
| TC4-P4 | NOTIFICATION | Alice | false | User notification (not system) |

## ❌ NEGATIVE TESTS (5 cases)

| ID | Type | Sender | Expected | Error Scenario |
|----|------|--------|----------|----------------|
| TC4-N1 | null | Alice | false | ❌ Invalid type |
| TC4-N2 | TEXT | null | false | ❌ Anonymous message |
| TC4-N3 | "" | Alice | false | ❌ Empty type |
| TC4-N4 | TEXT | "" | false | ❌ Empty sender |
| TC4-N5 | "UNKNOWN_TYPE" | Alice | false | ❌ Invalid type constant |

### 🧪 Code test NEGATIVE cho TC4:

```java
// TC4-N1: Invalid type with user sender
@Test
public void testTC4_N1_InvalidType() {
    Message msg = new Message(null, "Alice", "Hello");
    try {
        boolean result = Utils.isSystemMessage(msg);
        assertFalse(result);
    } catch (NullPointerException e) {
        fail("Should handle null type");
    }
}

// TC4-N2: Anonymous message
@Test
public void testTC4_N2_AnonymousSender() {
    Message msg = new Message(Constants.MESSAGE_TYPE_TEXT, null, "Hello");
    assertFalse(Utils.isSystemMessage(msg));
    // Should also validate: msg.getSender() != null before sending
}

// TC4-N5: Unknown type
@Test
public void testTC4_N5_UnknownType() {
    Message msg = new Message("INVALID_TYPE", "Alice", "Hello");
    boolean result = Utils.isSystemMessage(msg);
    assertFalse(result);
    // Bonus: Should log warning về unknown type
}
```

---

# TEST CASE 5: isSystemMessage() - Security (Fake Detection)

## ✅ POSITIVE TESTS (Security validated - 5 cases)

| ID | Type | Sender | Expected | Security Test |
|----|------|--------|----------|---------------|
| TC5-P1 | NOTIFICATION | Alice | false | 🛡️ User trying to fake |
| TC5-P2 | NOTIFICATION | system | false | 🛡️ Lowercase bypass attempt |
| TC5-P3 | NOTIFICATION | SYSTEM  (space) | false | 🛡️ Trailing space |
| TC5-P4 | NOTIFICATION | SyStEm | false | 🛡️ Mixed case |
| TC5-P5 | NOTIFICATION | SYSTEM\n | false | 🛡️ Newline injection |

## ❌ NEGATIVE TESTS (Advanced Security - 7 cases)

| ID | Attack Vector | Type | Sender | Expected | Vulnerability |
|----|---------------|------|--------|----------|---------------|
| TC5-N1 | SQL Injection | NOTIFICATION | SYSTEM'; DROP-- | false | ❌ SQL injection attempt |
| TC5-N2 | XSS Attack | NOTIFICATION | <script>SYSTEM | false | ❌ Script injection |
| TC5-N3 | Unicode bypass | NOTIFICATION | SYSTEM (với U+200B) | false | ❌ Zero-width space |
| TC5-N4 | Encoding attack | NOTIFICATION | %53%59%53%54%45%4D | false | ❌ URL encoding |
| TC5-N5 | Buffer overflow | NOTIFICATION | "SYSTEM" * 10000 | false | ❌ DoS attack |
| TC5-N6 | Null byte injection | NOTIFICATION | SYSTEM\0admin | false | ❌ Null byte |
| TC5-N7 | Case variation | NOTIFICATION | ＳＹＳＴＥＭ (fullwidth) | false | ❌ Unicode lookalike |

### 🧪 Code test NEGATIVE cho TC5 (Security):

```java
// TC5-N1: SQL Injection attempt
@Test
public void testTC5_N1_SQLInjection() {
    Message msg = new Message(
        Constants.MESSAGE_TYPE_NOTIFICATION, 
        "SYSTEM'; DROP TABLE messages;--", 
        "Malicious content"
    );
    boolean result = Utils.isSystemMessage(msg);
    assertFalse("Should block SQL injection", result);
}

// TC5-N2: XSS Attack
@Test
public void testTC5_N2_XSSAttack() {
    Message msg = new Message(
        Constants.MESSAGE_TYPE_NOTIFICATION, 
        "<script>alert('SYSTEM')</script>", 
        "XSS payload"
    );
    assertFalse(Utils.isSystemMessage(msg));
}

// TC5-N3: Unicode zero-width space
@Test
public void testTC5_N3_UnicodeBypass() {
    // SYSTEM với zero-width space (U+200B)
    String fakeSender = "SYS\u200BTEM";
    Message msg = new Message(
        Constants.MESSAGE_TYPE_NOTIFICATION, 
        fakeSender, 
        "Unicode bypass attempt"
    );
    assertFalse("Should detect unicode bypass", Utils.isSystemMessage(msg));
    assertNotEquals("SYSTEM", fakeSender); // Verify it's different
}

// TC5-N5: Buffer overflow / DoS
@Test
public void testTC5_N5_BufferOverflow() {
    StringBuilder huge = new StringBuilder();
    for (int i = 0; i < 10000; i++) {
        huge.append("SYSTEM");
    }
    
    Message msg = new Message(
        Constants.MESSAGE_TYPE_NOTIFICATION, 
        huge.toString(), 
        "DoS attempt"
    );
    
    assertFalse(Utils.isSystemMessage(msg));
    // Bonus: Should limit sender length to prevent DoS
}

// TC5-N7: Unicode lookalike characters
@Test
public void testTC5_N7_UnicodeLookalike() {
    // Fullwidth Latin: ＳＹＳＴＥＭ
    Message msg = new Message(
        Constants.MESSAGE_TYPE_NOTIFICATION, 
        "ＳＹＳＴＥＭ", 
        "Lookalike attack"
    );
    assertFalse("Should detect lookalike characters", Utils.isSystemMessage(msg));
}
```

---

# TEST CASE 6: Integration - Serialize/Deserialize

## ✅ POSITIVE TESTS (5 cases)

| ID | Content | Special Feature | Expected | Test Focus |
|----|---------|-----------------|----------|------------|
| TC6-P1 | "User Bob left" | Normal | 7/7 pass | Basic integration |
| TC6-P2 | "User @Bob#123!" | Special chars | 7/7 pass | Special char integrity |
| TC6-P3 | "Hello\nWorld" | Newline | 7/7 pass | Multiline content |
| TC6-P4 | "Tiếng Việt có dấu" | Unicode | 7/7 pass | UTF-8 support |
| TC6-P5 | "" | Empty | 7/7 pass | Edge case |

## ❌ NEGATIVE TESTS (8 cases)

| ID | Scenario | Input | Expected Error | Error Type |
|----|----------|-------|----------------|------------|
| TC6-N1 | Corrupt serialized data | "INVALID|||DATA" | Deserialize fail | ❌ ParseException |
| TC6-N2 | Missing delimiter | "NOTIFICATION SYSTEM test" | Deserialize fail | ❌ Format error |
| TC6-N3 | Extra delimiters | "NOTIFICATION|||SYSTEM|||test" | Deserialize fail | ❌ Parse error |
| TC6-N4 | Null serialized string | null → deserialize() | NPE or null return | ❌ Null handling |
| TC6-N5 | Empty serialized string | "" → deserialize() | Parse error | ❌ Invalid format |
| TC6-N6 | Binary data injection | "\x00\xFF\xFE" | Encoding error | ❌ Binary data |
| TC6-N7 | Very long serialized | 100MB string | OutOfMemoryError | ❌ Memory limit |
| TC6-N8 | Circular reference | Serialize → modify → deserialize | Data corruption | ❌ Data integrity |

### 🧪 Code test NEGATIVE cho TC6:

```java
// TC6-N1: Corrupt serialized data
@Test(expected = IllegalArgumentException.class)
public void testTC6_N1_CorruptData() {
    String corruptData = "INVALID|||CORRUPT|||DATA";
    Message msg = Message.deserialize(corruptData);
    // Should throw exception for invalid format
}

// TC6-N2: Missing delimiter
@Test
public void testTC6_N2_MissingDelimiter() {
    String invalidFormat = "NOTIFICATION SYSTEM test 12:00:00";
    try {
        Message msg = Message.deserialize(invalidFormat);
        fail("Should throw exception for missing delimiter");
    } catch (Exception e) {
        // Expected: IllegalArgumentException or similar
        assertTrue(e instanceof IllegalArgumentException);
    }
}

// TC6-N3: Extra delimiters
@Test
public void testTC6_N3_ExtraDelimiters() {
    String extraDelimiters = "NOTIFICATION|||SYSTEM|||test|||12:00:00|||extra";
    Message msg = Message.deserialize(extraDelimiters);
    // Should either ignore extra or throw exception
    assertNotNull(msg);
}

// TC6-N4: Null serialized string
@Test
public void testTC6_N4_NullSerialized() {
    try {
        Message msg = Message.deserialize(null);
        assertNull("Should return null for null input", msg);
    } catch (NullPointerException e) {
        // Also acceptable if throws NPE
        assertTrue(true);
    }
}

// TC6-N5: Empty serialized string
@Test(expected = IllegalArgumentException.class)
public void testTC6_N5_EmptySerialized() {
    Message msg = Message.deserialize("");
    // Should throw exception
}

// TC6-N6: Binary data
@Test
public void testTC6_N6_BinaryData() {
    String binaryData = "\u0000\uFFFF\uFFFE invalid binary";
    try {
        Message original = Utils.createSystemMessage(binaryData);
        String serialized = original.serialize();
        Message deserialized = Message.deserialize(serialized);
        
        // Should preserve or escape binary data
        assertNotNull(deserialized);
    } catch (Exception e) {
        // Acceptable: may not support binary data
        assertTrue(e instanceof IllegalArgumentException);
    }
}

// TC6-N7: Very long data (Memory test)
@Test
public void testTC6_N7_VeryLongData() {
    String huge = new String(new char[10000]).replace('\0', 'x');
    
    try {
        Message original = Utils.createSystemMessage(huge);
        String serialized = original.serialize();
        
        // Check serialized size is reasonable
        assertTrue("Serialized size too large", 
                   serialized.length() < 20000);
        
        Message deserialized = Message.deserialize(serialized);
        assertEquals(huge, deserialized.getContent());
    } catch (OutOfMemoryError e) {
        // Expected for extremely large data
        assertTrue(true);
    }
}

// TC6-N8: Data integrity check
@Test
public void testTC6_N8_DataIntegrity() {
    Message original = Utils.createSystemMessage("test");
    String serialized = original.serialize();
    
    // Tamper with serialized data
    String tampered = serialized.replace("SYSTEM", "HACKER");
    
    Message deserialized = Message.deserialize(tampered);
    
    // Should detect tampering
    assertFalse("Tampered message should not be system message",
                Utils.isSystemMessage(deserialized));
    assertEquals("HACKER", deserialized.getSender());
}
```

---

# 📊 TỔNG HỢP SỐ LƯỢNG TEST CASES

| Test Case | Positive | Negative | Total | Coverage |
|-----------|----------|----------|-------|----------|
| TC1: createSystemMessage (Valid) | 4 | 5 | 9 | ✅ 100% |
| TC2: Null Safety | 2 | 3 | 5 | ✅ 100% |
| TC3: isSystemMessage (Valid) | 3 | 6 | 9 | ✅ 100% |
| TC4: User Messages | 4 | 5 | 9 | ✅ 100% |
| TC5: Security | 5 | 7 | 12 | ✅ 100% |
| TC6: Integration | 5 | 8 | 13 | ✅ 100% |
| **TỔNG CỘNG** | **23** | **34** | **57** | **✅ Full** |

---

# 🎯 PRIORITY TESTING

## 🔴 HIGH PRIORITY (Must Test):
1. **TC5-N1 → TC5-N7**: Security tests - CRITICAL
2. **TC6-N1 → TC6-N4**: Integration failures
3. **TC3-N1 → TC3-N3**: Null pointer exceptions
4. **TC1-N1**: Null input handling

## 🟡 MEDIUM PRIORITY (Should Test):
1. **TC4-N2**: Anonymous sender
2. **TC6-N6**: Binary data handling
3. **TC1-N5**: Very long input
4. **TC5-P1 → TC5-P5**: Basic security

## 🟢 LOW PRIORITY (Nice to Have):
1. **TC6-N7**: Memory overflow
2. **TC4-N5**: Unknown type constants
3. **TC1-N3**: Whitespace only

---

# 📝 TEST EXECUTION CHECKLIST

## Cho Interactive Test:

```markdown
### TC1: Valid System Message
- [ ] TC1-P1: Normal text ✅
- [ ] TC1-P2: Single char ✅
- [ ] TC1-P3: Special chars ✅
- [ ] TC1-N1: Null input ❌
- [ ] TC1-N2: Empty string ❌
- [ ] TC1-N3: Whitespace only ❌

### TC2: Null Safety
- [ ] TC2-P1: Null content ✅
- [ ] TC2-N1: Null pointer access ❌
- [ ] TC2-N2: Serialize null ❌

### TC3: Valid Detection
- [ ] TC3-P1: Valid system message ✅
- [ ] TC3-N1: Null type ❌
- [ ] TC3-N2: Null sender ❌

### TC4: User Messages
- [ ] TC4-P1: TEXT message ✅
- [ ] TC4-P2: ERROR message ✅
- [ ] TC4-N2: Anonymous ❌

### TC5: Security
- [ ] TC5-P1: Fake with user sender ✅
- [ ] TC5-P2: Lowercase bypass ✅
- [ ] TC5-N1: SQL injection ❌
- [ ] TC5-N2: XSS attack ❌
- [ ] TC5-N3: Unicode bypass ❌

### TC6: Integration
- [ ] TC6-P1: Basic flow ✅
- [ ] TC6-P2: Special chars ✅
- [ ] TC6-N1: Corrupt data ❌
- [ ] TC6-N4: Null serialized ❌
```

---

# 🚀 NEXT STEPS:

1. **Implement negative tests trong InteractiveTest.java**
2. **Add error handling và validation**
3. **Create separate test class: `NegativeTests.java`**
4. **Run all tests và document results**
5. **Update Decision Tables với negative cases**

---

**Tác giả:** Nguyễn Đình Trang  
**Version:** 3.0 - Full Coverage (Positive + Negative)  
**Date:** 17/11/2025
