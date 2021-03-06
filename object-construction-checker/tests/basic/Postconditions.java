import org.checkerframework.checker.objectconstruction.qual.*;

/**
 * Test for postcondition support via @EnsureCalledMethods
 */
class Postconditions {
    void build(@CalledMethods({"a", "b", "c"}) Postconditions this) { }

    void a() {}

    void b() {}

    void c() {}

    @EnsuresCalledMethods(value = "#1", methods = "b")
    static void callB(Postconditions x) {
        x.b();
    }

    @EnsuresCalledMethods(value = "#1", methods = "b")
    // :: error: contracts.postcondition.not.satisfied
    static void doesNotCallB(Postconditions x) {
    }

    @EnsuresCalledMethods(value = "#1", methods = {"b","c"})
    static void callBAndC(Postconditions x) {
        x.b();
        x.c();
    }

    static void allInOneMethod() {
        Postconditions y = new Postconditions();
        y.a();
        y.b();
        y.c();
        y.build();
    }

    static void invokeCallB() {
        Postconditions y = new Postconditions();
        y.a();
        callB(y);
        y.c();
        y.build();
    }

    static void invokeCallBLast() {
        Postconditions y = new Postconditions();
        y.a();
        y.c();
        callB(y);
        y.build();
    }

    static void invokeCallBAndC() {
        Postconditions y = new Postconditions();
        y.a();
        callBAndC(y);
        y.build();
    }

    static void invokeCallBAndCWrong() {
        Postconditions y = new Postconditions();
        callBAndC(y);
        // :: error: finalizer.invocation.invalid
        y.build();
    }
}