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
                    + " - re-run with: java -ea PlaylistTest\n");
        }

        System.out.println("=== Playlist Test Suite ===\n");

        testCreators();
        //testAdd();
        //testRemove();
        //testObservers();
        //testProducer();
        //testExposure();

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

        BoundedStack empty = new BoundedStack();
        //check("new() -> empty users", empty.usersize() == 0);
        check("new() -> room capacity is MAX_ROOMS",empty.getrooms() == BoundedStack.MAX_ROOMS);
        check("new() -> contains nothing users", !empty.usercontains("anything"));
        //check("new() -> contains nothing full", !empty.Fullroom());    

        BoundedStack n = new BoundedStack(Arrays.asList("a", "b", "c"), 5);
        check("new(list) -> correct users size", n.usersize() == 3); 
        check("new(list) -> contains b", n.usercontains("b"));
        check("new(list) -> correct room capacity", n.getrooms() == 5);
    }

}