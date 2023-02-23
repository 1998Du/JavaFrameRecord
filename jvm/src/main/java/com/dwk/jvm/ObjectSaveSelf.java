package com.dwk.jvm;

/**
 * 对象自救
 * 1、对象可以在被GC时自救
 * 2、通过finalize()方法自救，对象的finalize()只会被调用一次，因此自救机会只有一次
 */
public class ObjectSaveSelf {

    private static ObjectSaveSelf instance = null;

    /**finalize()方法优先级很低*/
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        ObjectSaveSelf.instance = this;
    }

    public static void main(String[] args) throws InterruptedException {
        instance = new ObjectSaveSelf();
        instance = null;
        System.gc();
        Thread.sleep(1000);
        if (instance != null){
            System.out.println("存活");
        }else{
            System.out.println("死亡");
        }

        instance = null;
        System.gc();
        Thread.sleep(1000);
        if (instance != null){
            System.out.println("存活");
        }else{
            System.out.println("死亡");
        }
    }

}
