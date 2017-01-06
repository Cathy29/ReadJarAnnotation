/**
 * Created by nzinfo on 17-1-5.
 */

import net.sf.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class myClassLoader extends ClassLoader {

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException,
            InvocationTargetException {
        try {

            Class t2 = new myClassLoader().loadClassFromJar( args[0], args[1]);
            ClassInfo classInfo = (ClassInfo) t2.getAnnotation(ClassInfo.class);
            Field[] field = t2.getDeclaredFields();
            Method[] methods=t2.getDeclaredMethods();
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("author",classInfo.author());
            jsonObj.put("comment",classInfo.comments());
            jsonObj.put("date",classInfo.date());


            int i=0;
            int j=0;
            for (Field f : field) {
                FieldInfo ano = f.getAnnotation(FieldInfo.class);
                jsonObj.put("var"+i+"_name:",ano.name());
                jsonObj.put("var"+i+"_type:",ano.type());
                jsonObj.put("var"+i+"_value:",ano.value());
                i++;
            }

            for(Method m: methods){
                MethodInfo ano = m.getAnnotation(MethodInfo.class);
                jsonObj.put("method"+j+"_name:",ano.name());
                jsonObj.put("method"+j+"_description:",ano.description());
                j++;
            }

            System.out.println(jsonObj.toString());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Class loadClassFromJar(String className, String jarpath) throws ClassNotFoundException {
        Class result = null;
        byte[] classData = null;
        try {
            classData = getByteArrayFromJarFile(className, jarpath);
            result = defineClass(className, classData, 0, classData.length);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    private byte[] getByteArrayFromJarFile(String className, String jarpath) {
        JarFile jarfile = null;
        try {
            jarfile = new JarFile(jarpath);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        className = className.replace('.', '/');
        System.out.println(className);
        try {
            JarEntry jarEntry = jarfile.getJarEntry(className + ".class");
            if (jarEntry != null) {
                System.out.println("jarEntry:" + jarEntry.getName());
                try {
                    InputStream inputStream = jarfile.getInputStream(jarEntry);
                    int arrayLength = inputStream.available();
                    byte[] bytes = new byte[arrayLength];
                    int pos = 0;
                    while (true) {
                        int n = inputStream.read(bytes, pos, arrayLength - pos);
                        if (n <= 0)
                            break;
                        pos += n;
                    }
                    inputStream.close();
                    return bytes;
                } catch (IOException exp) {
                    exp.printStackTrace();
                }
            } else {
                System.out.println("not found");
            }
        } catch (Exception ex) {
            //ex.printStackTrace();
            return null;
        }
        return null;
    }

}
