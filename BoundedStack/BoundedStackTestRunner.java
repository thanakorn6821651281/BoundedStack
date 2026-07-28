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
        testExposure();

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
        check("push(A) -> size 1", b.usersize() == 1);
        check("push(A) -> found by contains", b.usercontains("A"));

        b.push("B");
        b.push("C");
        check("push preserves insertion order",
                b.users().equals(Arrays.asList("A", "B", "C")));

        // ชื่อ-นามสกุลซ้ำไม่ใช่ error — คืน false เฉย ๆ
        check("push duplicate -> returns false", !b.push("A"));
        check("failed push leaves size unchanged", b.usersize() == 3);
        // input ที่ผิดเงื่อนไขต้องโยน exception

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

        BoundedStack b = new BoundedStack(Arrays.asList("A", "B", "C"), 50);
        check("pop(B) -> returns true", b.pop("B"));
        check("pop -> size decreases", b.usersize() == 2);
        check("pop -> users is gone", !b.usercontains("B"));
        check("pop keeps the others in order",
                b.users().equals(Arrays.asList("A", "C")));

        // ลบชื่อ-นามสกุลที่ไม่มีไม่ใช่ error — คืน false เฉย ๆ
        check("pop  users -> returns false", !b.pop("nope"));
        check("failed pop leaves size unchanged", b.usersize() == 2);

        // boundary: ลบจนหมด
        b.pop("A");
        b.pop("C");
        check("pop all -> empty", b.usersize() == 0);
        check("pop on empty users -> returns false", !b.pop("A"));
    }
    // --- Observer ต้องไม่มี side effect ---
    private static void testObservers() {
        System.out.println("\n-- Observers --");

        BoundedStack b = new BoundedStack(Arrays.asList("A", "B"), 50);
        check("usersize reports 2", b.usersize() == 2);
        check("usercontains finds an existing user", b.usercontains("A"));
        check("usercontains rejects a missing user", !b.usercontains("Z"));
        check("users returns the full list in order",
                b.users().equals(Arrays.asList("A", "B")));

        int before = b.usersize();
        b.usersize();
        b.usercontains("A");
        b.users();
        check("observers have no side effects", b.usersize() == before);
    }
    // --- Producer ต้องคืนตัวใหม่ ไม่แก้ตัวเดิม ---
     private static void testProducer() {
        System.out.println("\n-- Producer (shuffled) --");

        BoundedStack original = new BoundedStack(Arrays.asList("A", "B", "C", "D"), 50);
        BoundedStack shuffled = original.shuffled();

        check("shuffled has the same size", shuffled.usersize() == original.usersize());

        List<String> a = new ArrayList<String>(original.users());
        List<String> b = new ArrayList<String>(shuffled.users());
        Collections.sort(a);
        Collections.sort(b);
        check("shuffled contains exactly the same users", a.equals(b));

        check("shuffled does not mutate the original",
                original.users().equals(Arrays.asList("A", "B", "C", "D")));

        // mutate ตัวใหม่ต้องไม่กระทบตัวเดิม
        shuffled.push("E");
        check("mutating the result does not affect the original",
                original.usersize() == 4);

        // boundary: shuffle เพลย์ลิสต์ว่างต้องไม่พัง
        BoundedStack emptyShuffled = new BoundedStack(50);
        check("shuffling an empty bounded stack is safe", emptyShuffled.usersize() == 0);
    }
    // --- ทดสอบว่าไม่เกิด representation exposure ---
    private static void testExposure() {
        System.out.println("\n-- Representation Exposure --");

        // ขาออก: แก้ list ที่ได้จาก songs() ต้องไม่กระทบ rep
        BoundedStack b = new BoundedStack(50);
        b.push("A");

        List<String> got = b.users();
        got.clear();
        check("clearing result of users() does not affect stack",
                b.usersize() == 1);

        got = b.users();
        got.add("injected");
        check("adding to result of users() does not affect stack",
                b.usersize() == 1 && !b.usercontains("injected"));

        // สองครั้งต้องเป็นคนละ object
        check("users() returns a fresh list each call",
                b.users() != b.users());

        // ขาเข้า: แก้ list ที่ส่งให้ constructor ต้องไม่กระทบ rep
        List<String> input = new ArrayList<String>(Arrays.asList("A", "B"));
        BoundedStack p = new BoundedStack(input, 50);

        input.clear();
        check("clearing constructor argument does not affect stack",
                p.usersize() == 2);

        input.add("injected");
        check("adding to constructor argument does not affect stack",
                !p.usercontains("injected"));
    }
}