import java.util.*;

public class BoundedStack {

    public static final int MAX_CAPACITY = 50;
    // ===== representation =====
    // Abstraction Function:
    // AF(users) = ห้องเรียน ที่ประกอบไปด้วยนิสิต

    // Representation Invariant:
    // ห้องเรียนต้อง > 0
    // ห้องเรียนต้องมีความจุ <= MAX_CAPACITY
    // นิสิตต้อง != null
    // ความจุนิสิตต้อง <= ความจุห้องเรียน
    // ชื่อ-นามสกุลห้ามซ้ำกัน
    
    private final List<String>  users; // รายชื่อนิสิต
    private final int rooms; // ความจุห้องเรียน

    //แปลง RI ทุกข้อเป็น assert หนึ่งบรรทัด พร้อมข้อความอธิบาย
    private void checkRep() {
        assert rooms > 0 && rooms <= MAX_CAPACITY; // ความจุห้องต้อง > 0 && ความจุห้องต้อง <= MAX_CAPACITY
        assert users != null;; // รายชื่อนิสิต้อง != null
        assert users.size() <= rooms; // ความจุนิสิตต้อง <= ความจุห้องเรียน
        Set<String> seen = new HashSet<>();
        for (String u : users) {
            assert u != null;
            assert seen.add(u); // ชื่อ-นามสกุลนิสิตห้ามซ้ำกัน
        }
    }
    
    // ===== Creator =====
    // สร้างห้องเรียนว่างที่มีความจุ MAX_CAPACITY
    public BoundedStack(){
        this.users = new ArrayList<>();
        this.rooms = MAX_CAPACITY;
        checkRep();
    }
    // เช็คว่าถ้าความจุห้อง<=0 || ถ้าความจุห้อง > MAX_ROOMS จะเกิด exception
    public BoundedStack(int rooms) {
        if(rooms <= 0 || rooms > MAX_CAPACITY) throw new IllegalArgumentException("Invalid room Capacity");
    this.users = new ArrayList<>();
    this.rooms = rooms;
    }
/**
 * สร้างห้องเรียนที่มีนิสิตเริ่มต้นจาก initial และความจุเท่ากับ rooms
 * @param initial รายชื่อนิสิตเริ่มต้น
 * @param rooms ความจุของห้อง
 * @throws IllegalArgumentException ถ้า initial เป็น null, rooms <= 0 || rooms > MAX_CAPACITY, initial.size() > rooms
 */
    
    public BoundedStack(List<String> initial, int rooms) {
        if(initial == null) throw new IllegalArgumentException(); // ถ้านิสิต = null จะเกิด exception
        if(rooms <= 0 || rooms > MAX_CAPACITY ) throw new IllegalArgumentException(); // ถ้าความจุห้อง <= 0 || ความจุห้อง > MAX_CAPACITY จะเกิด exception
        if(initial.size() > rooms) throw new IllegalArgumentException(); //ถ้าความจุนิสิต > ความจุห้อง จะเกิด exception
    this.users = new ArrayList<>(initial);
    this.rooms = rooms;
    
    }
    
    //===== mutator =====
/**
 * เพิ่มนิสิตใหม่ลงในห้องเรียน
 * @param user ชื่อ-นามสกุลของนิสิตใหม่ ต้องไม่เป็น null และไม่เป็นสตริงว่าง
 * @return true หากเพิ่มนิสิตสำเร็จ, false หากห้องเต็มหรือนิสิตซ้ำ
 * @throws IllegalArgumentException หาก user เป็น null
 */
    public boolean add(String user) {
        return false;
    }
/**
 * ลบนิสิตออกจากห้องเรียน
 * @param user ชื่อ-นามสกุลของนิสิตที่ต้องการลบ
 * @return true หากลบนิสิตสำเร็จ, false หากนิสิตไม่พบในห้อง
 */
     public boolean remove(String user) {
        return false;
    }

    //===== observer =====
     // คืนค่าจำนวนของนิสิตในห้องเรียน
    public int usersize() {
        return 0;
    }
    // คืนค่าความจุของห้องเรียน
    public int getrooms() {
        return 0;
    }

    
    // ตรวจสอบว่ามีชื่อ-นามสกุลนิสิตที่ระบุอยู่ในห้องหรือไม่
    // คืนค่า: true หากมี, false หากไม่มี
    public boolean usercontains(String user){
        return false;
    }
    // ตรวจสอบว่าห้องเรียนเต็มหรือไม่
    // คืนค่า: true หากห้องเต็ม, false หากห้องยังไม่เต็ม
    // ห้องเต็มหมายถึงจำนวนนิสิตในห้อง >= ความจุของห้อง
    public boolean Fullroom() {
    return false;
    }

    //===== producer =====

    // คืนสำเนารายชื่อนิสิตทั้งหมด
    // ผลลัพธ์: คืน List ใหม่ที่มีข้อมูลเหมือนกับรายชื่อนิสิตในห้อง
    public List<String> getusers() {
        return null;
    }
    
}
