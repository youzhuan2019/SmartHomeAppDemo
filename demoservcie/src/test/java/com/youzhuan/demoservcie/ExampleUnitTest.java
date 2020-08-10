package com.youzhuan.demoservcie;

import com.google.gson.Gson;
import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testGson() {
        Gson gson = new Gson();
        Root root = gson.fromJson("{\"name\":\"so json 在线工具\",\"age\":11}",Root.class);
        System.out.println(root.toString());
    }

    public static class Root {
        private String name;

        private int age;

        public void setName(String name){
            System.out.println("setName");
            this.name = name;
        }
        public String getName(){
            return this.name;
        }
        public void setAge(int age){
            System.out.println("setAge");
            this.age = age;
        }
        public int getAge(){
            return this.age;
        }

        @Override
        public String toString() {
            return "Root{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }
}