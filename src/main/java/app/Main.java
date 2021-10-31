package app;

import org.kttp.context.KttpInitializer;

public class Main {
    public static void main(String[] args) {
        new KttpInitializer("app").start();
    }
}
