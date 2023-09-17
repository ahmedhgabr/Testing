package testGenerate;

import java.lang.reflect.Constructor;
import java.util.ArrayList;

public class Tester {

    TestWriter writer;

    public Tester(String outputPath) {
        this.writer = new TestWriter(outputPath);
    }

    public void write(String s) {
        writer.writeToFile(s);
    }

    public void writeIntro() {
        writer.writeToFile(intro);
    }

    public void writeOutro() {
        writer.writeToFile(outro);
    }

    // write class path as String in test
    public void writePath(String name, String path) {
        path = path.substring(path.indexOf("src/")+4 , path.indexOf(".java")).replace("/" , ".") ;
        writer.writeToFile("String " + pathV(name) + " = " + "\"" + path + "\" ;\n");
    }

    String pathV(String name) { // use this method so the variable name is the same every time
        return name + "Path";
    }


//    static ArrayList<Object> myPoolValue = new ArrayList<>();
//    static ArrayList<String> myPoolString = new ArrayList<>();
//    public void pool(Main.Class myClass){
//        try {
//            String s = "Main.Class" ;
////            String p = myClass.getPath().substring(0,myClass.getPath().indexOf("src")) + myClass.getPath().substring(myClass.getPath().indexOf("src") , myClass.getPath().indexOf(".java") ).replace("/",".");
//
//            Class<?> clazz = Class.forName(s);
////            Constructor c = clazz.getConstructor();
////            Object  o = c.newInstance();
////            Class c = Class.forName(myClass.getName());
//            System.out.println(clazz);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//    }

    String intro = "import static org.junit.Assert.assertEquals;\n" +
            "import static org.junit.Assert.assertFalse;\n" +
            "import static org.junit.Assert.assertTrue;\n" +
            "import static org.junit.Assert.fail;\n" +
            "\n" +
            "import java.lang.reflect.Field;\n" +
            "import java.lang.reflect.Method;\n" +
            "import java.lang.reflect.Modifier;\n" +
            "import java.util.ArrayList;\n" +
            "\n" +
            "import org.junit.Test;\n" +
            "\n" +
            "import java.io.File;\n" +
            "import java.io.IOException;\n" +
            "import java.io.PrintWriter;\n" +
            "import java.io.FileNotFoundException;\n" +
            "import java.lang.reflect.InvocationTargetException;\n" +
            "\n" +
            "public class Test\n" +
            "{";
    String outro = "\n// ############################################# Helper methods\n" +
            "private Object giveMeRandom(String type) {\n" +
            "        Random random = new Random();\n" +
            "\n" +
            "        switch (type) {\n" +
            "            case \"boolean\":\n" +
            "                return random.nextBoolean();\n" +
            "            case \"byte\":\n" +
            "                byte[] randomByte = new byte[1];\n" +
            "                random.nextBytes(randomByte);\n" +
            "                return randomByte;\n" +
            "            case \"short\":\n" +
            "                return (short) random.nextInt();\n" +
            "            case \"int\":\n" +
            "                return random.nextInt();\n" +
            "            case \"long\":\n" +
            "                return random.nextLong();\n" +
            "            case \"float\":\n" +
            "                return random.nextFloat();\n" +
            "            case \"double\":\n" +
            "                return random.nextDouble();\n" +
            "            case \"char\":\n" +
            "                return generateRandomChar();\n" +
            "            case \"String\":\n" +
            "                return generateRandomString();\n" +
            "            default: mock();\n" +
            "\n" +
            "        }\n" +
            "        return null;\n" +
            "    }\n" +
            "\n" +
            "\n" +
            "    private static char generateRandomChar() {\n" +
            "        Random random = new Random();\n" +
            "        return (char) (random.nextInt(26) + 'a');\n" +
            "    }\n" +
            "\n" +
            "private Byte randomByte() {\n" +
            "    Random random = new Random();\n" +
            "        byte[] randomByte = new byte[1];\n" +
            "        random.nextBytes(randomByte);\n" +
            "        return randomByte[0];\n" +
            "    }"+
            "    private static String generateRandomString() {\n" +
            "        int length = 10;\n" +
            "        String characters = \"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789\";\n" +
            "        StringBuilder sb = new StringBuilder(length);\n" +
            "        Random random = new Random();\n" +
            "\n" +
            "        for (int i = 0; i < length; i++) {\n" +
            "            int index = random.nextInt(characters.length());\n" +
            "            sb.append(characters.charAt(index));\n" +
            "        }\n" +
            "\n" +
            "        return sb.toString();\n" +
            "    }" +
            "\n" +
            "\n" +
            "\tprivate boolean testConstructorInitializationWithRandom(Object createdObject, String[] names, Object[] values) throws NoSuchMethodException, SecurityException, IllegalArgumentException, IllegalAccessException \n" +
            "\t{\n" +
            "\t\tboolean res = true;\n" +
            "\t\tfor (int i = 0; i < names.length; i++) \n" +
            "\t\t{\n" +
            "\t\t\tField f = null;\n" +
            "\t\t\tClass curr = createdObject.getClass();\n" +
            "\t\t\tString currName = names[i];\n" +
            "\t\t\tObject currValue = values[i];\n" +
            "\t\t\twhile (f == null) \n" +
            "\t\t\t{\n" +
            "\t\t\t\tif (curr == Object.class)\n" +
            "\t\t\t\t\tfail(\"Class \" + createdObject.getClass().getSimpleName() + \" should have the instance variable \\\"\" + currName + \"\\\".\");\n" +
            "\t\t\t\ttry \n" +
            "\t\t\t\t{\n" +
            "\t\t\t\t\tf = curr.getDeclaredField(currName);\n" +
            "\t\t\t\t} \n" +
            "\t\t\t\tcatch (NoSuchFieldException e)\n" +
            "\t\t\t\t{\n" +
            "\t\t\t\t\tcurr = curr.getSuperclass();\n" +
            "\t\t\t\t}\n" +
            "\t\t\t}\n" +
            "\t\t\tf.setAccessible(true);\n" +
            "\t\t\tres = res &&  currValue.equals( f.get(createdObject));\n" +
            "\t\t}\n" +
            "\t\treturn res;\n" +
            "\t}\n" +
            "\t\n" +
            "\tprivate void testEnumValues(Class aClass, String [] value) throws ClassNotFoundException \n" +
            "\t{\n" +
            "\t\tfor(int i=0;i<value.length;i++)\n" +
            "\t\t{\n" +
            "\t\t\ttry \n" +
            "\t\t\t{\n" +
            "\t\t\t\tEnum.valueOf((Class<Enum>) aClass, value[i]);\n" +
            "\t\t\t} \n" +
            "\t\t\tcatch (IllegalArgumentException e) \n" +
            "\t\t\t{\n" +
            "\t\t\t\tfail(aClass.getSimpleName()+\" enum can be \" + value[i]);\n" +
            "\t\t\t}\n" +
            "\t\t}\n" +
            "\t}\n" +
            "\n" +
            "\tprivate void testInstanceVariableIsPresent(Class aClass, String varName, boolean implementedVar) throws SecurityException \n" +
            "\t{\n" +
            "\t\tboolean thrown = false;\n" +
            "\t\ttry \n" +
            "\t\t{\n" +
            "\t\t\taClass.getDeclaredField(varName);\n" +
            "\t\t} \n" +
            "\t\tcatch (NoSuchFieldException e) \n" +
            "\t\t{\n" +
            "\t\t\tthrown = true;\n" +
            "\t\t}\n" +
            "\t\tif (implementedVar) \n" +
            "\t\t{\n" +
            "\t\t\tassertFalse(\"There should be \\\"\" + varName + \"\\\" instance variable in class \" + aClass.getSimpleName() + \".\", thrown);\n" +
            "\t\t} \n" +
            "\t\telse \n" +
            "\t\t{\n" +
            "\t\t\tassertTrue(\"The instance variable \\\"\" + varName + \"\\\" should not be declared in class \" + aClass.getSimpleName() + \".\", thrown);\n" +
            "\t\t}\n" +
            "\t}\n" +
            "\t\n" +
            "\tprivate void testInstanceVariableIsPrivate(Class aClass, String varName) throws NoSuchFieldException, SecurityException \n" +
            "\t{\n" +
            "\t\tField f = aClass.getDeclaredField(varName);\n" +
            "\t\tassertEquals(\"The \\\"\"+ varName + \"\\\" instance variable in class \" + aClass.getSimpleName() + \" should not be accessed outside that class.\", 2, f.getModifiers());\n" +
            "\t}\n" +
            "\t\n" +
            "\tprivate void testGetterMethodExistsInClass(Class aClass, String methodName, Class returnedType, boolean writeVariable)\n" +
            "\t{\n" +
            "\t\tMethod m = null;\n" +
            "\t\tboolean found = true;\n" +
            "\t\ttry\n" +
            "\t\t{\n" +
            "\t\t\tm = aClass.getDeclaredMethod(methodName);\n" +
            "\t\t} catch (Exception e)\n" +
            "\t\t{\n" +
            "\t\t\tfound = false;\n" +
            "\t\t}\n" +
            "\t\tString varName = \"\";\n" +
            "\t\tif (returnedType == boolean.class)\n" +
            "\t\t\tvarName = methodName.substring(2);\n" +
            "\t\telse\n" +
            "\t\t\tvarName = methodName.substring(3);\n" +
            "\t\tvarName = Character.toLowerCase(varName.charAt(0))\n" +
            "\t\t\t\t+ varName.substring(1);\n" +
            "\t\tif (writeVariable)\n" +
            "\t\t{\n" +
            "\t\t\tassertTrue(\"The \\\"\" + varName + \"\\\" instance variable in class \" + aClass.getSimpleName() + \" is a READ variable.\", found);\n" +
            "\t\t\tassertTrue(\"Incorrect return type for \" + methodName + \" method in \" + aClass.getSimpleName() + \" class.\", m.getReturnType()\n" +
            "\t\t\t\t\t.isAssignableFrom(returnedType));\n" +
            "\t\t}\n" +
            "\t\telse\n" +
            "\t\t{\n" +
            "\t\t\tassertFalse(\"The \\\"\" + varName + \"\\\" instance variable in class \" + aClass.getSimpleName() + \" is not a READ variable.\", found);\n" +
            "\t\t}\n" +
            "\t}\n" +
            "\t\n" +
            "\tprivate void testSetterMethodExistsInClass(Class aClass, String methodName, Class inputType, boolean writeVariable) \n" +
            "\t{\n" +
            "\t\tMethod[] methods = aClass.getDeclaredMethods();\n" +
            "\t\tString varName = methodName.substring(3);\n" +
            "\t\tvarName = Character.toLowerCase(varName.charAt(0))\n" +
            "\t\t\t\t+ varName.substring(1);\n" +
            "\t\tif (writeVariable) \n" +
            "\t\t{\n" +
            "\t\t\tassertTrue(\"The \\\"\" + varName + \"\\\" instance variable in class \" + aClass.getSimpleName() + \" is a WRITE variable.\", containsMethodName(methods, methodName));\n" +
            "\t\t} \n" +
            "\t\telse \n" +
            "\t\t{\n" +
            "\t\t\tassertFalse(\"The \\\"\" + varName + \"\\\" instance variable in class \" + aClass.getSimpleName() + \" is not a WRITE variable.\", containsMethodName(methods, methodName));\n" +
            "\t\t\treturn;\n" +
            "\t\t}\n" +
            "\t\tMethod m = null;\n" +
            "\t\tboolean found = true;\n" +
            "\t\ttry \n" +
            "\t\t{\n" +
            "\t\t\tm = aClass.getDeclaredMethod(methodName, inputType);\n" +
            "\t\t} \n" +
            "\t\tcatch (NoSuchMethodException e) \n" +
            "\t\t{\n" +
            "\t\t\tfound = false;\n" +
            "\t\t}\n" +
            "\t\tassertTrue(aClass.getSimpleName() + \" class should have \" + methodName + \" method that takes one \" + inputType.getSimpleName() + \" parameter.\", found);\n" +
            "\t\tassertTrue(\"Incorrect return type for \" + methodName + \" method in \" + aClass.getSimpleName() + \".\", m.getReturnType().equals(Void.TYPE));\n" +
            "\t}\n" +
            "\t\n" +
            "\tprivate static boolean containsMethodName(Method[] methods, String name) \n" +
            "\t{\n" +
            "\t\tfor (Method method : methods) \n" +
            "\t\t{\n" +
            "\t\t\tif (method.getName().equals(name))\n" +
            "\t\t\t\treturn true;\n" +
            "\t\t}\n" +
            "\t\treturn false;\n" +
            "\t}\n" +
            "\t\n" +
            "\tprivate void testConstructorExists(Class aClass, Class[] inputs) \n" +
            "\t{\n" +
            "\t\tboolean thrown = false;\n" +
            "\t\ttry \n" +
            "\t\t{\n" +
            "\t\t\taClass.getConstructor(inputs);\n" +
            "\t\t} \n" +
            "\t\tcatch (NoSuchMethodException e) \n" +
            "\t\t{\n" +
            "\t\t\tthrown = true;\n" +
            "\t\t}\n" +
            "\t\tif (inputs.length > 0) \n" +
            "\t\t{\n" +
            "\t\t\tString msg = \"\";\n" +
            "\t\t\tint i = 0;\n" +
            "\t\t\tdo \n" +
            "\t\t\t{\n" +
            "\t\t\t\tmsg += inputs[i].getSimpleName() + \" and \";\n" +
            "\t\t\t\ti++;\n" +
            "\t\t\t} while (i < inputs.length);\n" +
            "\t\t\tmsg = msg.substring(0, msg.length() - 4);\n" +
            "\t\t\tassertFalse(\"Missing constructor with \" + msg + \" parameter\" + (inputs.length > 1 ? \"s\" : \"\") + \" in \" + aClass.getSimpleName() + \" class.\", thrown);\n" +
            "\t\t} \n" +
            "\t\telse\n" +
            "\t\t\tassertFalse(\"Missing constructor with zero parameters in \" + aClass.getSimpleName() + \" class.\", thrown);\n" +
            "\t}\n" +
            "\t\n" +
            "\tprivate void testClassIsAbstract(Class aClass) \n" +
            "\t{\n" +
            "\t\tassertTrue(\"You should not be able to create new instances from \" + aClass.getSimpleName() + \" class.\", Modifier.isAbstract(aClass.getModifiers()));\n" +
            "\t}\n" +
            "\n" +
            "\tprivate void testIsInterface(Class aClass) \n" +
            "\t{\n" +
            "\t\tassertEquals(aClass.getName() + \" should be an Interface\", aClass.isInterface(), true);\n" +
            "\t}\n" +
            "\t\n" +
            "\tprivate void testIsEnum(Class aClass) \n" +
            "\t{\n" +
            "\t\tassertEquals(aClass.getName() + \" should be an Enum\", aClass.isEnum(), true);\n" +
            "\t}\n" +
            "\n" +
            "\tprivate void testClassIsSubclass(Class subClass, Class superClass) \n" +
            "\t{\n" +
            "\t\tassertEquals(subClass.getSimpleName() + \" class should be a subclass from \" + superClass.getSimpleName() + \".\", superClass, subClass.getSuperclass());\n" +
            "\t}\n" +
            "\t\n" +
            "\tprivate void testConstructorInitializationException(Object createdObject, String message) throws NoSuchMethodException, SecurityException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException \n" +
            "\t{\n" +
            "\t\tClass throwableClass = Throwable.class;\n" +
            "\t\tField messageFiled = throwableClass.getDeclaredField(\"detailMessage\");\n" +
            "\t\tmessageFiled.setAccessible(true);\n" +
            "\t\tassertEquals(\"The Exception constructor of the \" + createdObject.getClass().getSimpleName() + \" class should initialize the message correctly.\", message, messageFiled.get(createdObject));\n" +
            "\t}\n" +
            "\t\n" +
            "\tprivate void testConstructorInitialization(Object createdObject, String[] names, Object[] values) throws NoSuchMethodException, SecurityException, IllegalArgumentException, IllegalAccessException \n" +
            "\t{\n" +
            "\t\tfor (int i = 0; i < names.length; i++) \n" +
            "\t\t{\n" +
            "\t\t\tField f = null;\n" +
            "\t\t\tClass curr = createdObject.getClass();\n" +
            "\t\t\tString currName = names[i];\n" +
            "\t\t\tObject currValue = values[i];\n" +
            "\t\t\twhile (f == null) \n" +
            "\t\t\t{\n" +
            "\t\t\t\tif (curr == Object.class)\n" +
            "\t\t\t\t\tfail(\"Class \" + createdObject.getClass().getSimpleName() + \" should have the instance variable \\\"\" + currName + \"\\\".\");\n" +
            "\t\t\t\ttry \n" +
            "\t\t\t\t{\n" +
            "\t\t\t\t\tf = curr.getDeclaredField(currName);\n" +
            "\t\t\t\t} \n" +
            "\t\t\t\tcatch (NoSuchFieldException e)\n" +
            "\t\t\t\t{\n" +
            "\t\t\t\t\tcurr = curr.getSuperclass();\n" +
            "\t\t\t\t}\n" +
            "\t\t\t}\n" +
            "\t\t\tf.setAccessible(true);\n" +
            "\t\t\tassertEquals(\"The constructor of the \" + createdObject.getClass().getSimpleName() + \" class should initialize the instance variable \\\"\" + currName + \"\\\" correctly.\", currValue, f.get(createdObject));\n" +
            "\t\t}\n" +
            "\t}\n" +
            "\t\n" +
            "\tprivate void testGetterLogic(Object createdObject, String name, Object value) throws Exception \n" +
            "\t{\n" +
            "\t\tField f = null;\n" +
            "\t\tClass curr = createdObject.getClass();\n" +
            "\t\twhile (f == null) \n" +
            "\t\t{\n" +
            "\t\t\tif (curr == Object.class)\n" +
            "\t\t\t\tfail(\"Class \" + createdObject.getClass().getSimpleName() + \" should have the instance variable \\\"\" + name + \"\\\".\");\n" +
            "\t\t\ttry \n" +
            "\t\t\t{\n" +
            "\t\t\t\tf = curr.getDeclaredField(name);\n" +
            "\t\t\t} \n" +
            "\t\t\tcatch (NoSuchFieldException e) \n" +
            "\t\t\t{\n" +
            "\t\t\t\tcurr = curr.getSuperclass();\n" +
            "\t\t\t}\n" +
            "\t\t}\n" +
            "\t\tf.setAccessible(true);\n" +
            "\t\tf.set(createdObject, value);\n" +
            "\t\tCharacter c = name.charAt(0);\n" +
            "\t\tString methodName = \"get\" + Character.toUpperCase(c) + name.substring(1, name.length());\n" +
            "\t\tif (value.getClass().equals(Boolean.class))\n" +
            "\t\t\tmethodName = \"is\" + Character.toUpperCase(c) + name.substring(1, name.length());\n" +
            "\t\tMethod m = createdObject.getClass().getMethod(methodName);\n" +
            "\t\tassertEquals(\"The method \\\"\" + methodName + \"\\\" in class \" + createdObject.getClass().getSimpleName() + \" should return the correct value of variable \\\"\" + name + \"\\\".\", value, m.invoke(createdObject));\n" +
            "\t}\n" +
            "\t\n" +
            "\tprivate void testSetterLogic(Object createdObject, String name, Object valueIn, Object valueOut, Class type) throws Exception \n" +
            "\t{\n" +
            "\t\tField f = null;\n" +
            "\t\tClass curr = createdObject.getClass();\n" +
            "\t\twhile (f == null)\n" +
            "\t\t{\n" +
            "\n" +
            "\t\t\tif (curr == Object.class)\n" +
            "\t\t\t\tfail(\"Class \" + createdObject.getClass().getSimpleName() + \" should have the instance variable \\\"\" + name + \"\\\".\");\n" +
            "\t\t\ttry \n" +
            "\t\t\t{\n" +
            "\t\t\t\tf = curr.getDeclaredField(name);\n" +
            "\t\t\t} \n" +
            "\t\t\tcatch (NoSuchFieldException e) \n" +
            "\t\t\t{\n" +
            "\t\t\t\tcurr = curr.getSuperclass();\n" +
            "\t\t\t}\n" +
            "\t\t}\n" +
            "\t\tf.setAccessible(true);\n" +
            "\t\tCharacter c = name.charAt(0);\n" +
            "\t\tString methodName = \"set\" + Character.toUpperCase(c) + name.substring(1, name.length());\n" +
            "\t\tMethod m = createdObject.getClass().getMethod(methodName, type);\n" +
            "\t\tm.invoke(createdObject, valueIn);\n" +
            "\t\tassertEquals(\"The method \\\"\" + methodName + \"\\\" in class \" + createdObject.getClass().getSimpleName() + \" should set the correct value of variable \\\"\" + name + \"\\\".\", valueOut, f.get(createdObject));\n" +
            "\t}\n" +
            "\t\n" +
            "\tprivate void testClassImplementsInterface(Class aClass, Class interfaceName)\n" +
            "\t{\n" +
            "\t\tClass[] interfaces = aClass.getInterfaces();\n" +
            "\t\tboolean implement = false;\n" +
            "\t\tfor (Class i : interfaces)\n" +
            "\t\t{\n" +
            "\t\t\tif (i.toString().equals(interfaceName.toString()))\n" +
            "\t\t\t\timplement = true;\n" +
            "\t\t}\n" +
            "\t\tassertTrue(aClass.getSimpleName() + \" class should implement \" + interfaceName.getSimpleName() + \" interface.\", implement);\n" +
            "\t}\n" +
            "\t\n" +
            "}\n";


}
