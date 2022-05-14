import java.util.ArrayList;

/*
    2022/3/1 14:43
    @author 张渭
    Project Name:blog-parent
     
    theme:
*/
public class Test {
    @org.junit.jupiter.api.Test
    public void ss(){

        // //包装类型只能接受自己的基本类型
        // Double a=3;
        // //正常小数都是double类型
        // //float类型：11.1f
        // float f=11.1;//11.1是double
        // double d=5.3e12;
        // //大类型引用指向小类型，不会报错
        // //小类型引用指向大类型，会报错
        // double e=1;
        // int i=0.0;//0.0是double


        ArrayList<Integer> list = new ArrayList<>();
        list.add(0);
        list.add(1);
        list.add(0,4);
        for (Integer integer : list) {
            System.out.println(integer);
        }
        list.remove(0);
        for (Integer integer : list) {
            System.out.println(integer);
        }

        System.out.println();

        char s='A'+10;
        double s2='A'+3.14;

    }

    @org.junit.Test
    public void tt(){
        String a= null;
        System.out.println(a.length());
    }
}
