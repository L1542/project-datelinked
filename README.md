# Music Playlist Management System

โปรแกรมจัดการเพลย์ลิสต์เพลงบน Command Line เขียนด้วยภาษา Java โดยใช้โครงสร้างข้อมูลแบบ **Doubly Linked List**

## ฟังก์ชันหลัก

| เมนู | ฟังก์ชัน | รายละเอียด |
|---|---|---|
| 1 | **Add** | เพิ่มเพลงต่อท้ายเพลย์ลิสต์ (ชื่อเพลง + ศิลปิน) |
| 2 | **Delete** | ลบเพลงตามชื่อ |
| 3 | **Search** | ค้นหาเพลงตามชื่อ แสดงชื่อเพลง ศิลปิน และลำดับที่ |
| 4 | **Next/Previous** | เลื่อนไปเพลงถัดไปหรือก่อนหน้า พร้อมแสดงเพลงที่กำลังเล่น |
| 0 | ปิดโปรแกรม | ออกจากโปรแกรม |

## โครงสร้างข้อมูล: Doubly Linked List

แต่ละเพลงเป็น `Node` ที่มีตัวชี้ไปทั้งเพลงก่อนหน้า (`prev`) และเพลงถัดไป (`next`)

```
null <- [Node A] <-> [Node B] <-> [Node C] -> null
          head                       tail
```

- `Node` เก็บ `title`, `artist`, `prev`, `next`
- `Playlist` เก็บตัวชี้ `head`, `tail` และ `current` (เพลงที่กำลังเล่น)
- ค่า `current` ถูกจำไว้ ออกจากเมนู Next/Previous แล้วกลับเข้ามาก็ยังอยู่เพลงเดิม

### ความซับซ้อนของเวลา

| การทำงาน | Time Complexity |
|---|---|
| Add (ต่อท้าย) | O(1) |
| Search | O(n) |
| Delete (รวมการค้นหา) | O(n) |
| Next / Previous | O(1) |

## กฎและการดัก Error

- ชื่อเพลงห้ามว่าง และยาวไม่เกิน 100 ตัวอักษร
- ชื่อเพลงห้ามซ้ำ (ไม่สนตัวพิมพ์เล็ก/ใหญ่)
- ศิลปินเว้นว่างได้ จะแสดงเป็น "ไม่ระบุศิลปิน"
- Delete และ Search ไม่ทำงานถ้าเพลย์ลิสต์ว่าง
- ถ้าลบเพลงที่กำลังเล่น เพลงปัจจุบันจะเลื่อนไปเพลงถัดไป (ถ้าไม่มีให้ถอยไปเพลงก่อนหน้า)
- เมนูรับค่าว่าง ตัวอักษร หรือเลขนอกช่วง 0-4 จะแจ้ง error แล้วกลับเมนู
- ถ้า input ถูกปิดกลางทาง (Ctrl+D / Ctrl+Z) โปรแกรมจะออกอย่างปลอดภัย
- มีตัวกันรอบนอกสำหรับ error ที่ไม่คาดคิด โปรแกรมจะไม่ปิดเอง

## วิธีรัน

ต้องมี JDK 8 ขึ้นไป

```bash
# โครงสร้างโฟลเดอร์
# src/musicplaylist/MusicPlaylist.java

javac -encoding UTF-8 -d out src/musicplaylist/MusicPlaylist.java
java -Dfile.encoding=UTF-8 -cp out musicplaylist.MusicPlaylist
```

ถ้าใช้ Windows แล้วภาษาไทยแสดงผลเพี้ยน ให้รันคำสั่งนี้ก่อน

```bash
chcp 65001
```

หรือเปิดด้วย IDE (เช่น NetBeans, IntelliJ IDEA) แล้วตั้ง encoding ของโปรเจกต์เป็น UTF-8

## ตัวอย่างการใช้งาน

```
-----------
เพลย์ลิสต์เพลง
1. Add
2. Delete
3. Search
4. Next/Previous
0. ปิดโปรแกรม
-----------
เลือกเมนู : 1
กรอกชื่อเพลง : Shape of You
กรอกชื่อศิลปิน (เว้นว่างได้) : Ed Sheeran
เพิ่มเพลง "Shape of You" สำเร็จ
```

หน้า Next/Previous

```
---- เพลย์ลิสต์ ----
1. Shape of You - Ed Sheeran  <--- กำลังเล่น
2. Blinding Lights - The Weeknd
--------------------
กำลังเล่น : Shape of You - Ed Sheeran
[N] Next  [P] Previous  [B] Back :
```

## โครงสร้างโปรเจกต์

```
.
├── README.md
└── src
    └── musicplaylist
        └── MusicPlaylist.java
```
