public class Test {
    public static void foo() {
        System.out.println("Foo!");
    }

    public static void main(String[] args) {
        Runnable lambda = Test::foo;
        System.out.println(lambda);
    }
}
