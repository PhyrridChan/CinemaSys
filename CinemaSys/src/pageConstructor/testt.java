package pageConstructor;

import java.util.Arrays;

public class testt {
    public static void main(String[] args) {
        String a = "*qwe*qwe(qwe*";
        //         [,qwe,qwe(qwe]
        int i = 2;
        String repla = "$wawawaw$";
        String[] str = a.split("\\*");
        System.out.println(Arrays.toString(str));
        str[i-1] += repla;
        System.out.println(Arrays.toString(str));

        System.out.println();
    }
}

