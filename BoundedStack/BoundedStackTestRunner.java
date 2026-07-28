import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


/**
 * Test runner 
 */
public class BoundedStackTestRunner {

    private static int passed = 0;
    private static int failed = 0;

    /** helper กลาง — พิมพ์ PASS/FAIL และนับผลให้เอง */
    private static void check(String name, boolean condition) {
        if (condition) {
            passed++;
            System.out.println("[PASS] " + name);
        } else {
            failed++;
            System.out.println("[FAIL] " + name);
        }
    }

    public static void main(String[] args) {
        boolean assertsOn = false;
        assert assertsOn = true;
        if (!assertsOn) {
            System.out.println("WARNING: assertions disabled"
                    + " - re-run with: java -ea BoundedStackTest\n");
        }

        System.out.println("=== BoundedStack Test Suite ===\n");

        testCreators();
        testPush();
        testPop();
        testObservers();
        testProducer();

        System.out.println("\n=== Summary ===");
        System.out.println("Passed: " + passed);
        System.out.println("Failed: " + failed);
        System.out.println("Total : " + (passed + failed));
        System.out.println(failed == 0 ? "ALL TESTS PASSED" : "SOME TESTS FAILED");

        if (failed > 0) {
            System.exit(1);
        }
    }
    private static void testCreators() {
        System.out.println("-- Creators --");

        BoundedStack empty = new BoundedStack(50);
        check("new() -> empty users", empty.usersize() == 0);
        check("new() -> room capacity is 50",empty.getroomssize() == 50);    

        BoundedStack b = new BoundedStack(Arrays.asList("A", "B", "C") , 50);
        check("new(list) -> correct users size", b.usersize() == 3);
        check("new(list) -> contains B", b.usercontains("B"));
        check("new(list) -> correct room capacity", b.getroomssize() == 50);

    // boundary: list ว่างคือขอบล่างที่ถูกต้อง
        BoundedStack fromEmpty = new BoundedStack(new ArrayList<String>(), 50);
        check("new(empty list) -> empty", fromEmpty.usersize() == 0);

        // input ที่ผิดเงื่อนไขต้องโยน exception ไม่ใช่ปล่อยผ่าน
        boolean threwDup = false;
        try {
            new BoundedStack(Arrays.asList("A", "A"), 0);
        } catch (IllegalArgumentException e) {
            threwDup = true;
        }
        check("new(duplicates) -> throws IllegalArgumentException", threwDup);

        boolean threwNull = false;
        try {
            new BoundedStack(Arrays.asList("A", null), 0);
        } catch (IllegalArgumentException e) {
            threwNull = true;
        }
        check("new(list with null) -> throws IllegalArgumentException", threwNull);

        boolean threwNullList = false;
        try {
            new BoundedStack(null, 50);
        } catch (IllegalArgumentException e) {
            threwNullList = true;
        }
        check("new(null) -> throws IllegalArgumentException", threwNullList);
    }
    // --- Mutator: push ต้องรักษาลำดับและกันเพลงซ้ำ ---
    private static void testPush() {
        System.out.println("\n-- Push --");

        BoundedStack b = new BoundedStack(50);
        check("push(A) -> returns true", b.push("A"));
        check("push(A) -> size 1", b.usersize() == 3);
        check("push(A) -> found by contains", b.usercontains("A"));

        b.push("B");
        b.push("C");
        check("push preserves insertion order",
                b.users().equals(Arrays.asList("A", "B", "C")));

        // ชื่อ-นามสกุลซ้ำไม่ใช่ error — คืน false เฉย ๆ
        check("push duplicate -> returns false", !b.push("A"));
        check("failed push leaves size unchanged", b.usersize() == 3);
        // input ที่ผิดเงื่อนไขต้องโยน exception
        boolean threwEmpty = false;
        try {
            b.push("");
        } catch (IllegalArgumentException e) {
            threwEmpty = true;
        }
        check("push(empty string) -> throws IllegalArgumentException", threwEmpty);

        boolean threwNull = false;
        try {
            b.push(null);
        } catch (IllegalArgumentException e) {
            threwNull = true;
        }
        check("push(null) -> throws IllegalArgumentException", threwNull);
        
        // boundary: เติมจนเต็มพอดีแล้วเติมเพิ่ม
        BoundedStack full = new BoundedStack(50);
        for (int i = 0; i < 50; i++) {
            full.push("user" + i);
        }
        check("can fill up to 50", full.usersize() == 50);
        check("push when full -> returns false", !full.push("one more"));
        check("full users stays at 50",
                full.users().size() == 50);
    }
    // --- Mutator: pop ทั้งกรณีพบและไม่พบ ---
    private static void testPop() {
        System.out.println("\n-- Pop --");

        BoundedStack s = new BoundedStack(Arrays.asList("A", "B", "C"), 50);
        check("pop(B) -> returns true", s.pop("B"));
        check("pop -> size decreases", s.usersize() == 2);
        check("pop -> users is gone", !s.usercontains("B"));
        check("pop keeps the others in order",
                s.users().equals(Arrays.asList("A", "C")));

        // ลบชื่อ-นามสกุลที่ไม่มีไม่ใช่ error — คืน false เฉย ๆ
        check("pop  users -> returns false", !s.pop("nope"));
        check("failed pop leaves size unchanged", s.usersize() == 2);

        // boundary: ลบจนหมด
        s.pop("A");
        s.pop("C");
        check("pop all -> empty", s.usersize() == 0);
        check("pop on empty users -> returns false", !s.pop("A"));
    }
    // --- Observer ต้องไม่มี side effect ---
    private static void testObservers() {
        System.out.println("\n-- Observers --");

        BoundedStack s = new BoundedStack(Arrays.asList("A", "B"), 50);
        check("usersize reports 2", s.usersize() == 2);
        check("usercontains finds an existing user", s.usercontains("A"));
        check("usercontains rejects a missing user", !s.usercontains("Z"));
        check("users returns the full list in order",
                s.users().equals(Arrays.asList("A", "B")));

        int before = s.usersize();
        s.usersize();
        s.usercontains("A");
        s.users();
        check("observers have no side effects", s.usersize() == before);
    }
    // --- Producer ต้องคืนตัวใหม่ ไม่แก้ตัวเดิม ---
     private static void testProducer() {
        System.out.println("\n-- Producer --");

        BoundedStack s = new BoundedStack(Arrays.asList("A", "B"), 50);
        List<String> users = s.users();
        users.add("C");
        check("users() returns a copy, not the original",
                s.usersize() == 2 && !s.usercontains("C"));
    }
}