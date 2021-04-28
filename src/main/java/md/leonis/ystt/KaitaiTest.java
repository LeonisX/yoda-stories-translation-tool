package md.leonis.ystt;

import md.leonis.ystt.model.Yodesk;

import java.io.IOException;

public class KaitaiTest {

    public static void main(String[] args) throws IOException {

        md.leonis.ystt.oldmodel2.Yodesk yodesk = md.leonis.ystt.oldmodel2.Yodesk.fromFile("D:\\Working\\_Yoda\\YExplorer\\other\\Yoda Stories (10.08.1998)\\YODESK.DTA");

        System.out.println(yodesk);

    }
}
