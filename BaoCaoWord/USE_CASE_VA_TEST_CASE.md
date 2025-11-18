# TÓM TẮT: USE CASE VÀ TEST CASES

## Phân biệt rõ ràng

### ✅ 1 USE CASE = 1 Chức năng nghiệp vụ

**USE CASE:** CREATE SYSTEM MESSAGE (Tạo thông báo hệ thống)

**Mô tả:** Chức năng tạo các thông báo tự động của hệ thống khi có sự kiện xảy ra (user join/leave, server start/stop, error, v.v.)

**Phạm vi:** 
- Method 1: `Utils.createSystemMessage(String content)` - Tạo message
- Method 2: `Utils.isSystemMessage(Message message)` - Kiểm tra message

---

### ✅ 6 TEST CASES = 6 Trường hợp kiểm thử

Test cases được **dẫn xuất** từ use case trên để kiểm tra các khía cạnh khác nhau:

| # | Test Case | Mục đích | Kiểm tra gì? |
|---|-----------|----------|--------------|
| **TC1** | testCreateSystemMessage_ValidContent | Happy path | Input hợp lệ → Output đúng |
| **TC2** | testCreateSystemMessage_NullContent | Edge case | Input null → Không crash |
| **TC3** | testIsSystemMessage_ValidSystemMessage | Positive case | Nhận diện đúng system message |
| **TC4** | testIsSystemMessage_UserMessage | Negative case | Phân biệt user message |
| **TC5** | testIsSystemMessage_NotificationButNotSystem | Security | Phát hiện fake message |
| **TC6** | testCreateSystemMessage_SerializeDeserialize | Integration | End-to-end flow |

---

## Mối quan hệ Use Case → Test Cases

```
┌───────────────────────────────────────────────────────────┐
│           1 USE CASE: CREATE SYSTEM MESSAGE               │
│                 (Chức năng nghiệp vụ)                     │
│                                                           │
│  Gồm 2 phương thức:                                       │
│  • createSystemMessage(String content)                    │
│  • isSystemMessage(Message message)                       │
└───────────────────────────────────────────────────────────┘
                            │
            ┌───────────────┼───────────────┐
            │               │               │
            ▼               ▼               ▼
    ┌───────────────┐  ┌─────────┐  ┌──────────────┐
    │ createSystem  │  │isSystem │  │ Integration  │
    │   Message()   │  │Message()│  │   Testing    │
    └───────┬───────┘  └────┬────┘  └──────┬───────┘
            │               │               │
      ┌─────┴─────┐    ┌────┴────┐         │
      ▼           ▼    ▼    ▼    ▼         ▼
    ┌────┐     ┌────┐┌────┐┌────┐┌────┐ ┌────┐
    │TC1 │     │TC2 ││TC3 ││TC4 ││TC5 │ │TC6 │
    │Valid│    │Null││True││User││Fake│ │E2E │
    └────┘     └────┘└────┘└────┘└────┘ └────┘
        ↓          ↓     ↓     ↓     ↓      ↓
    6 TEST CASES (Trường hợp kiểm thử cụ thể)
```

---

## Tại sao 1 Use Case sinh ra 6 Test Cases?

### Lý do 1: Equivalence Partitioning (Phân vùng tương đương)
- **Use Case** có nhiều loại input khác nhau
- Mỗi **Test Case** đại diện cho 1 vùng input:
  - TC1: Vùng Valid (input hợp lệ)
  - TC2: Vùng Null (biên null)

### Lý do 2: Decision Table Testing (Kiểm thử bảng quyết định)
- **Use Case** có logic phức tạp (2 điều kiện: type AND sender)
- Sinh ra nhiều **Test Cases** cho từng combination:
  - TC3: type=NOTIFICATION AND sender=SYSTEM → TRUE
  - TC4: type≠NOTIFICATION → FALSE
  - TC5: type=NOTIFICATION AND sender≠SYSTEM → FALSE

### Lý do 3: Integration Testing
- **Use Case** không đơn thuần là 1 method
- Cần test cả **flow end-to-end**:
  - TC6: Create → Serialize → Deserialize → Verify

---

## Công thức tính số Test Cases

Từ **1 Use Case**, số test cases phụ thuộc vào:

1. **Số vùng input** (Equivalence Partitioning)
   - Valid, Empty, Null, Special → 4 vùng
   - Chọn 2 vùng quan trọng nhất → **2 test cases** (TC1, TC2)

2. **Số quy tắc trong Decision Table**
   - 2 điều kiện (c1, c2)
   - 2² = 4 combinations
   - 3 combinations khả thi → **3 test cases** (TC3, TC4, TC5)

3. **Integration scenarios**
   - End-to-end flow → **1 test case** (TC6)

**Tổng cộng:** 2 + 3 + 1 = **6 test cases**

---

## So sánh với báo cáo gốc (Triangle)

| Tiêu chí | Báo cáo gốc | Báo cáo của chúng em |
|----------|-------------|----------------------|
| **Số Use Case** | 1 (CheckTriangle) | 1 (CREATE SYSTEM MESSAGE) |
| **Số Test Cases** | 8 | 6 |
| **Dẫn xuất từ** | Decision Table (11 quy tắc) | EP + BVA + DT + Integration |
| **Phương pháp** | Chủ yếu Decision Table | 4 phương pháp kết hợp |

---

## Bảng ánh xạ đầy đủ

### Use Case → Methods → Test Cases

| Use Case | Method | Test Case | Phương pháp | Input | Expected |
|----------|--------|-----------|-------------|-------|----------|
| CREATE SYSTEM MESSAGE | createSystemMessage() | TC1 | EP | "User joined" | Message valid |
| CREATE SYSTEM MESSAGE | createSystemMessage() | TC2 | BVA | null | No crash |
| CREATE SYSTEM MESSAGE | isSystemMessage() | TC3 | DT | System msg | TRUE |
| CREATE SYSTEM MESSAGE | isSystemMessage() | TC4 | DT | User msg | FALSE |
| CREATE SYSTEM MESSAGE | isSystemMessage() | TC5 | DT | Fake msg | FALSE |
| CREATE SYSTEM MESSAGE | Both | TC6 | Integration | Full flow | Data OK |

---

## Bảng Decision Table tổng hợp

### Use Case: CREATE SYSTEM MESSAGE

| Quy tắc # | Điều kiện | Test Case | Kết quả |
|-----------|-----------|-----------|---------|
| R1 | content valid | TC1 | PASS ✓ |
| R2 | content null | TC2 | PASS ✓ |
| R3 | type=NOTIF AND sender=SYS | TC3 | TRUE ✓ |
| R4 | type≠NOTIF | TC4 | FALSE ✓ |
| R5 | type=NOTIF AND sender≠SYS | TC5 | FALSE ✓ |
| R6 | Integration all steps | TC6 | OK ✓ |

**Coverage:** 6/6 rules = 100%

---

## Kết luận

### ✅ Hiểu đúng

- **1 USE CASE** = 1 chức năng nghiệp vụ = CREATE SYSTEM MESSAGE
- **6 TEST CASES** = 6 trường hợp kiểm thử = TC1 đến TC6
- Test Cases được **dẫn xuất** từ Use Case bằng các phương pháp:
  - Equivalence Partitioning
  - Boundary Value Analysis
  - Decision Table Testing
  - Integration Testing

### ✅ Kết quả

- **100% test cases PASS** (6/6)
- **100% use case coverage** (tất cả flows được test)
- **100% decision coverage** (tất cả quy tắc được test)

---

**Tóm lại:**
- Bạn test **1 USE CASE** (chức năng)
- Từ use case đó, bạn tạo ra **6 TEST CASES** (trường hợp)
- Mỗi test case kiểm tra 1 khía cạnh cụ thể của use case

**Đây chính là cách làm đúng theo tiêu chuẩn kiểm thử phần mềm!** ✅
