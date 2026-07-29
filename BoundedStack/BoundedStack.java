    import java.util.*;

    public class BoundedStack {
        
        /**
         * จัดทำโดย: นายธนกร อุตะนะตะ 6821651281
         * พีรณัฐ หอมแม้น 6821651566	
         * BoundedStack เก็บรายชื่อ-นามสกุลนิสิตเข้าห้องเรียน
         */
        // ===== representation =====
        // Abstraction Function:
        // AF(users) = ห้องเรียน ที่ประกอบไปด้วยนิสิต

        // Representation Invariant:
        // ห้องเรียนต้อง > 0
        // นิสิตต้อง != null
        // ความจุนิสิตต้อง <= ความจุห้องเรียน
        // ชื่อ-นามสกุลห้ามซ้ำกัน
        
        private final List<String>  users; // รายชื่อนิสิต
        private final int capacity; // ความจุห้องเรียน

        //แปลง RI ทุกข้อเป็น assert หนึ่งบรรทัด พร้อมข้อความอธิบาย
        private void checkRep() {
            assert capacity > 0 ; // ความจุห้องต้อง > 0 
            assert users != null;; // รายชื่อนิสิต้อง != null
            assert users.size() <= capacity; // ความจุนิสิตต้อง <= ความจุห้องเรียน
            Set<String> seen = new HashSet<>();
            for (String u : users) {
                assert u != null;
                assert seen.add(u); // ชื่อ-นามสกุลนิสิตห้ามซ้ำกัน
            }
        }
        
        // ===== Creator =====
        // สร้างห้องเรียนว่างที่มีความจุ
        public BoundedStack(int capacitys) {
        this.users = new ArrayList<>();
        this.capacity = capacitys;
        checkRep();
        }
    /**
     * สร้างห้องเรียนที่มีนิสิตเริ่มต้นจาก initial และความจุเท่ากับ rooms
     * @param initial รายชื่อนิสิตเริ่มต้น
     * @param rooms ความจุของห้อง
     * @throws IllegalArgumentException ถ้า initial เป็น null, rooms <= 0 , initial.size() > rooms
     */
        
        public BoundedStack(List<String> initial, int capacitys) {
            if(initial == null) throw new IllegalArgumentException(); // ถ้านิสิต = null จะเกิด exception
            if(capacitys <= 0  ) throw new IllegalArgumentException(); // ถ้าความจุห้อง <= 0  จะเกิด exception
            if(initial.size() > capacitys) throw new IllegalArgumentException(); //ถ้าความจุนิสิต > ความจุห้อง จะเกิด exception
            Set<String> seen = new HashSet<>();
            for (String s : initial) {
                if(s == null) throw new IllegalArgumentException();
                if(!seen.add(s)) throw new IllegalArgumentException();
            }
        this.users = new ArrayList<>(initial);
        this.capacity = capacitys;
        checkRep();
        }
        
        //===== mutator =====
    /**
     * เพิ่มนิสิตใหม่ลงในห้องเรียน
     * @param user ชื่อ-นามสกุลของนิสิต ต้องไม่เป็น null 
     * @return true หากเพิ่มนิสิตสำเร็จ, false หากห้องเต็มหรือนิสิตซ้ำ
     * @throws IllegalArgumentException หาก user เป็น null
     */
        public boolean push(String user) {
            if(user == null ) throw new IllegalArgumentException(); // ถ้าชื่อ-สกุล == null จะเกิด exception
            if(users.contains(user)) return false; // ถ้าตรวจสอบเจอชื่อนิสิตซ้ำกันจะ return false
            if(users.size() >= capacity) return false; // ถ้าความจุนิสิต >= ความจุห้อง return false
            users.add(user);
            checkRep();
            return true;
        }
    /**
     * ลบนิสิตออกจากห้องเรียน
     * @param user ชื่อ-นามสกุลของนิสิตที่ต้องการลบ
     * @return true หากลบนิสิตสำเร็จ, false หากนิสิตไม่พบในห้อง
     */
        public boolean pop(String user) {
            if(!users.contains(user)) return false;
            users.remove(user);
            return true;
        }

        //===== observer =====
        // คืนค่าจำนวนของนิสิตในห้องเรียน
        public int size() {
            return users.size();
        }
        // คืนค่าความจุของห้องเรียน
        public int getssize() {
            return capacity;
        }

        
        // ตรวจสอบว่ามีชื่อ-นามสกุลนิสิตที่ระบุอยู่ในห้องหรือไม่
        // คืนค่า: true หากมี, false หากไม่มี
        public boolean contains(String user){
            return users.contains(user);
        }
        // ตรวจสอบว่าห้องเรียนเต็มหรือไม่
        // คืนค่า: true หากห้องเต็ม, false หากห้องยังไม่เต็ม
        // ห้องเต็มหมายถึงจำนวนนิสิตในห้อง >= ความจุของห้อง
        public boolean Full() {
        return users.size() >= capacity;
        }

        // คืนสำเนารายชื่อนิสิตทั้งหมด
        public List<String> users(){
            return new ArrayList<>(users);
        }

        //===== producer =====
        //คืนรายชื่อนิสิตใหม่ที่มีรายชื่อเก่าเหมือนเดิมแต่สลับที่กัน
        //@return รายชื่อนิสิตที่สลับที่แล้ว

        public BoundedStack shuffled(){
            List<String> copy = new ArrayList<>(users);
            Collections.shuffle(copy);
            return new BoundedStack(copy, capacity);
        }
        
    }