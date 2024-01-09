public class Test {
    public static void foo() {
        System.out.println("Foo!");
    }

    public static void main(String[] args) {
        System.out.println((Runnable) Test::foo);
    }
}
