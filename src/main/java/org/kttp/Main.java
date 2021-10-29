package org.kttp;

public class Main {
    public static void main(String[] args) {
        new KttpListener(
                new KttpParser()
        ).boostrap();
    }
}
