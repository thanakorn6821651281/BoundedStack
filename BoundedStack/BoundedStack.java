import java.util.*;

public class BoundedStack {

    public static final int MAX_NISITS = 50;
    public static final int MAX_ROOMS = 50;
    // ===== representation =====
    // Abstraction Function:
    // AF(users) = ห้องเรียน ที่ประกอบไปด้วยนิสิต

    // Representation Invariant:
    // ห้องเรียนต้อง > 0
    // นิสิตต้อง != null
    // ห้องเรียนต้องมีความจุ <= 50
    // นิสิตต้อง <= 50
    // ชื่อ-นามสกุลห้ามซ้ำกัน
    
    private final List<String>  users;
    private final int rooms;

    //แปลง RI ทุกข้อเป็น assert หนึ่งบรรทัด พร้อมข้อความอธิบาย
    private void checkRep() {
        
    }
    
    // ===== Creator =====
    // สร้างห้องเรียนว่างที่มีความจุ MAX_ROOMS
    public BoundedStack(){
        this.users = null;
        this.rooms = 0;
        
    }
    // เช็คว่าห้องเรียนมีความจุ<=0 || ถ้าความจุห้อง > MAX_ROOMS จะเกิด exception
    public BoundedStack(int rooms) {
    this.users = null;
    this.rooms = rooms;
    
    }
/**
 * สร้างห้องเรียนที่มีนิสิตเริ่มต้นจาก initial และความจุเท่ากับ rooms
 * @param initial รายชื่อนิสิตเริ่มต้น
 * @param rooms ความจุของห้อง
 * @throws IllegalArgumentException หาก initial เป็น null, rooms <= 0, rooms > MAX_ROOMS, หรือ initial.size() > rooms
 */
    
    public BoundedStack(List<String> initial, int rooms) {
    this.users = null;
    this.rooms = 0;
    
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
