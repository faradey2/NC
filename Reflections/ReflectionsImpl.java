import java.lang.reflect.*;
import java.util.*;

public class ReflectionsImpl implements Reflections{

    @Override
    public Object getFieldValueByName(Object object, String fieldName) throws NoSuchFieldException {
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        try {
            return field.get(object);
        } catch (IllegalAccessException e) {
            return null;
        }
    }

    @Override
    public Set<String> getProtectedMethodNames(Class clazz) {
        Set<String> set = new HashSet<>();
        Method[] methods = clazz.getDeclaredMethods();
        for(Method m: methods)
            if(Modifier.isProtected(m.getModifiers()))
                set.add(m.getName());
        return set;
    }

    @Override
    public Set<Method> getAllImplementedMethodsWithSupers(Class clazz) {
        Set<Method> set = new HashSet<Method>();

        Collections.addAll(set, clazz.getDeclaredMethods());
        while(clazz.getSuperclass() != null) {
            clazz = clazz.getSuperclass();
            Collections.addAll(set, clazz.getDeclaredMethods());
        }
        return set;
    }

    @Override
    public List<Class> getExtendsHierarchy(Class clazz) {
        List<Class> classes = new LinkedList<>();
        while(clazz.getSuperclass() != null) {
            clazz = clazz.getSuperclass();
            classes.add(clazz);
        }
        return classes;
    }

    @Override
    public Set<Class> getImplementedInterfaces(Class clazz) {
        Set<Class> intrfcs = new HashSet<>();
        Collections.addAll(intrfcs,clazz.getInterfaces());
        return intrfcs;
    }

    @Override
    public List<Class> getThrownExceptions(Method method) {
        List<Class> list = new ArrayList<>();
        Collections.addAll(list,method.getExceptionTypes());
        return list;
    }

    @Override
    public String getFooFunctionResultForDefaultConstructedClass() {
        try {
            Class clazz = SecretClass.class;
            Constructor cons = clazz.getDeclaredConstructor();
            cons.setAccessible(true);
            SecretClass obj = (SecretClass) cons.newInstance();
            Method method = clazz.getDeclaredMethod("foo");
            method.setAccessible(true);
            return (String) method.invoke(obj);
        }
        catch (Exception e){
            System.out.println(e);
            return null;
        }
    }

    @Override
    public String getFooFunctionResultForClass(String constructorParameter, String string, Integer... integers) {
        try {
            Class clazz = SecretClass.class;
            SecretClass obj = new SecretClass(constructorParameter);
            Class[] paramTypes = new Class[]{String.class, Integer[].class};
            Method method = clazz.getDeclaredMethod("foo",paramTypes);
            method.setAccessible(true);
            return (String) method.invoke(obj,string,integers);
        }
        catch (Exception e){
            System.out.println(e);
            return null;
        }
    }
}