package ru.fizteh.fivt.students.mesherinIlya.asmproxy;

import java.util.NoSuchElementException;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import ru.fizteh.fivt.proxy.Collect;
import ru.fizteh.fivt.proxy.DoNotProxy;

import ru.fizteh.fivt.students.mesherinIlya.asmproxy.MyTestInterface;

public class UnitTests {

    @Test(expected = IllegalArgumentException.class)
    public void testProxyWithNullTargets() {
        new AsmShardingProxyFactory().createProxy(null, new Class[1]);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testProxyWithEmptyTargets() {
        new AsmShardingProxyFactory().createProxy(new Object[0], new Class[1]);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testProxyWithNullInterfaces() {
        new AsmShardingProxyFactory().createProxy(new Object[1], null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testProxyWithEmptyInterfaces() {
        new AsmShardingProxyFactory().createProxy(new Object[1], new Class[0]);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testProxyWithOneEmptyInterface() {
        Class[] clazz = new Class[3];
        clazz[1] = null;
        new AsmShardingProxyFactory().createProxy(new Object[1], clazz);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testProxyWithOneNullTarget() {
        Object[] clazz = new Object[2];
        Class[] interf = new Class[1];
        clazz[0] = new MyTestClass();
        interf[0] = MyTestInterface.class;
        clazz[1] = null;
        new AsmShardingProxyFactory().createProxy(clazz, interf);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testProxyWithOneNullInterface() {
        Object[] clazz = new Object[1];
        Class[] interf = new Class[2];
        clazz[0] = new MyTestClass();
        interf[0] = MyTestInterface.class;
        interf[1] = null;
        new AsmShardingProxyFactory().createProxy(clazz, interf);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testProxyWithNoSuchClasses() {
        Object[] clazz = new Object[1];
        Class[] interf = new Class[1];
        clazz[0] = new MyTestOtherClass();
        interf[0] = MyTestInterface.class;
        new AsmShardingProxyFactory().createProxy(clazz, interf);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testProxyWithNoMethodInInterface() {
        Object[] clazz = new Object[1];
        Class[] interf = new Class[2];
        clazz[0] = new MyTestClass();
        interf[0] = MyTestInterface2.class;
        interf[1] = MyTestInterface.class;
        new AsmShardingProxyFactory().createProxy(clazz, interf);
    }
    
    
    interface IPerson {
        public String getName();
        public void setName(String name);
        public void rename(String new_name);
    }

    class Person implements IPerson {
        private String name;
        
        public String getName() {
            return name;
        }
        
        public void setName(String name) {
            this.name = name;
        }
     
        public void rename(String new_name) {
            if (!new_name.equals(name))	this.name = new_name;
        }

    }
    
    

    @Test(expected = IllegalStateException.class)
    public void testProxyWithDoNotProxyAnnotation() {
        Object[] clazz = new Object[2];
        Class[] interf = new Class[1];
        clazz[0] = new MyTestClass();
        clazz[1] = new MyTestClass();
        interf[0] = MyTestInterface.class;
        AsmShardingProxyFactory factory = new AsmShardingProxyFactory();
    /*!!!*/    MyTestInterface myInter = (MyTestInterface) factory.createProxy(clazz, interf);
        Assert.assertTrue(myInter.getI(412) == 124);
    }

    interface MyTestInterface2 {
    }

    class MyTestOtherClass {
    }

    @Test
    public void testProxyNormal() {
        Object[] clazz = new Object[2];
        Class[] interf = new Class[1];
        clazz[0] = new MyTestClass();
        clazz[1] = new MyTestClass();
        interf[0] = MyTestInterface.class;
        AsmShardingProxyFactory factory = new AsmShardingProxyFactory();
    /*!!!*/    MyTestInterface myInter = (MyTestInterface) factory.createProxy(clazz, interf);
        Assert.assertTrue(myInter.smth(2, 3) == 8);
        Assert.assertTrue(myInter.getA(3L) == -3L);
        Assert.assertEquals(myInter.getL(412L), 826);
        //Assert.assertEquals(myInter.getSomething(), 30);
        //Assert.assertEquals(myInter.getLength("123456"), 12);
    }

    @Rule
    public ExpectedException e = ExpectedException.none();
/*
    @Test
    public void exceptionTest() {
        e.expect(RuntimeException.class);
        e.expectMessage("testRuntimeException");
        Object[] clazz = new Object[1];
        Class[] interf = new Class[1];
        clazz[0] = new MyTestClass();
        interf[0] = MyTestInterface.class;
        AsmShardingProxyFactory factory = new AsmShardingProxyFactory();
        MyTestInterface myInter = (MyTestInterface) factory.createProxy(clazz,
                interf);
        myInter.throwException("testRuntimeException");
    }
*/
}
