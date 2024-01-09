public class Test {
    public void foo() {
        System.out.println("Foo!");
    }

    public static void main(String[] args) {
        Runnable lambda = new Test()::foo;
        System.out.println(lambda);
    }
}
