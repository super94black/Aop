package org.redrock.ioc.base;


/**
 * 1.Java对象是通过类的信息Class对象来创建这个类的所有对象的
 * 2.为了生成这个Class对象，Java虚拟机使用"类加载器"
 * 3.所有类都是在第一次对类的静态成员引用时，类加载器会首先检查这个类的Class对象是否已经加载。如果没有会根据类名查找.class字节码文件，生成Class对象
 * 4.Class对象在被加载时，会完成static初始化
 */
public class Test {
    public static void main(String[] args) throws ClassNotFoundException {

    }

    /**
     * 1.加载，有类加载器执行，通过查找字节码，并通过字节码创建Class对象
     * 2.链接，在链接阶段将验证类中的字节码，为静态域分配存储空间，必要时，解析这个类创建的对其他类的所有引用
     * 3.初始化，如果该类具有超类，则对其初始化，执行静态初始化器和静态初始化块
     * 初始化被延迟到了对静态方法（构造器隐式地是静态的）或者非常数静态域进行首次引用时执行
     */
    public static void testClass() {
        //Class的泛化引用
        //类字面常量
        Class<?> helloClass = Hello.class;
    }

    public static void testClassForName() {
        try {
            Class.forName("org.redrock.ioc.base.Hello");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 转型前检查
     * 1.通过异常进行处理
     * 2.通过其Class对象进行判别
     * 3.instanceof 判断对象是否为某个对象实例
     */
    public static void cast() {
        Class<?> clazz = null;
        try {
            clazz = Class.forName("org.redrock.ioc.base.Student");
            Object object = clazz.newInstance();
            // 1.
            try {
                Student student1 = (Student) object;
            } catch (ClassCastException e) {
                e.printStackTrace();
            }
            //2
            if (object.getClass().getCanonicalName().equals("org.redrock.ioc.base.Student")) {
               Student student2 = (Student) object;
            }
            //3
            if (object instanceof Student) {
                Student student3 = (Student) object;
            }

        } catch (ClassNotFoundException e) {
            //类字节码文件没有找到
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            //类的构造函数为私有
            e.printStackTrace();
        } catch (InstantiationException e) {
            //创建实例失败，缺少参数
            e.printStackTrace();
        }
    }
}

class Student {
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}