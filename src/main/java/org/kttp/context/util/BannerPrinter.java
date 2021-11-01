package org.kttp.context.util;

import com.github.lalyos.jfiglet.FigletFont;

import java.io.IOException;

public class BannerPrinter {

    public static void printBanner() {
        try {
            System.out.println(FigletFont.convertOneLine("kttp-server"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
