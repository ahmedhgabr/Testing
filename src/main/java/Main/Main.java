package Main;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import ANTLR.Java8Lexer;
import ANTLR.Java8Parser;
import Listeners.MainListener;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import testGenerate.Tester;

public class Main {

    static ArrayList<String> filesPath = new ArrayList<>();
    static Listeners.MainListener MainListener;

    public static void main(String[] args) {

        String path = takeInput();

        // find all java files in this file
        findFiles(path);

        //main listener to find all classes , interfaces , enums , annotations
        MainListener = new MainListener();
        for (String file : filesPath) {
            isJavas(file);
        }
        //we will use Javas meaning classes , interfaces , annotations , enums
        // , or any java file

        ArrayList<Javas> myJavas = MainListener.javas;
        //here we have all javas Objects with names and paths

        // use more specific listener for each file
        // (class -> class listener - > method listener , interface -> interface listener , etc)

        for (Javas j : myJavas) {
            System.out.println(j.path);
            System.out.println(j.name);
            try {
                j.analyze();
            } catch (Exception e) {
                System.out.println("problem happened here");
                System.out.println(e.getMessage());
            }
            System.out.println("----------");
        }


        // here, we should have all data we need from our input
        // have all classes( names , variables , methods )
        // interfaces (methods)
        //TODO: support annotations


        // empty the output files (delete old output if found)
        String fileT = "PublicTest.java";
        File oldFileT = new File(fileT);
        oldFileT.delete();


        // start generate tests
        Tester tester = new Tester(fileT);
        for (Javas j : myJavas) {
            tester.writeImport(j.path);
        }
        tester.writeIntro();
        for (Javas j : myJavas) {
            j.generateTest(fileT);
        }
        tester.writeOutro();

    }


    private static String takeInput() {
//        enter the path of the src
        System.out.println("Enter src path : ");
        Scanner sc = new Scanner(System.in);
        String p = sc.next();

        // input example. Watch Out for the format -> "ex/am/ple/src"
//        String p = "C:/Users/Asus/Downloads/Cases/Cases/Cheating case 1/Team 73 - Sami El Sebaei/LastOfUs_Game/src";
        return p;
    }

    /**
     * recursive method will search all the folders/packages for files with .java extension
     */
    private static void findFiles(String path) {
        File directory = new File(path);
        String contents[] = directory.list();
        for (String inside : contents) {
            String insidePath = path + "/" + inside;
            File insidePathF = new File(insidePath);
            if (insidePathF.isDirectory()) {
                findFiles(insidePath);
            } else if (insidePath.contains(".java")) {
                filesPath.add(insidePath);
            }
        }

    }

    /**
     * Use MainListener to differentiate between classes , interfaces , enums
     * add them to the MainListener.javas arraylist
     */
    private static void isJavas(String file) {
        Path fileP = Paths.get(file);
        Java8Lexer lexer = null;
        try {
            lexer = new Java8Lexer(CharStreams.fromPath(fileP));
        } catch (IOException e) {
            System.out.println("Error in " + fileP);
        }
        Java8Parser parser = new Java8Parser(new CommonTokenStream(lexer));
        parser.addParseListener(MainListener);
        parser.compilationUnit();
        // add path to the javas
        MainListener.javas.get(MainListener.javas.size() - 1).path = file;
    }


}