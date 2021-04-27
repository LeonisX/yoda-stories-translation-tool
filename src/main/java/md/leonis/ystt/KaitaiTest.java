package md.leonis.ystt;

import md.leonis.ystt.model.Yodesk;

import java.io.IOException;

public class KaitaiTest {

    public static void main(String[] args) throws IOException {

        Yodesk yodesk = Yodesk.fromFile("D:\\Working\\_Yoda\\YExplorer\\other\\Yoda Stories (10.08.1998)\\YODESK.DTA");

        System.out.println(yodesk);

    }
}
