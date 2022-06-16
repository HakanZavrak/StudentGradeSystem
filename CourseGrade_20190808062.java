//Name : Hakan Zavrak  Date : 08.01.2021
package CourseGrade;
import java.io.*;
import java.util.Scanner;

public class CourseGrade_20190808062 {
    public static int countCategory(String filename){
        int category=0;
        try{
            Scanner input=new Scanner(new FileReader(filename));
            while (input.hasNextLine()){
                String check=input.nextLine();
                category++;
            }
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        return category;
    }

    public static void getCategory(String []category,int[]quantity,
                                   int []weight,String filename){
        try {
            Scanner input=new Scanner(new FileReader(filename));
            int check=0;
            while (input.hasNextLine()){
                String say=input.nextLine();
                String[]arr=say.split(" ");
                category[check]=arr[0];
                quantity[check]=Integer.valueOf(arr[1]);
                weight[check]=Integer.valueOf(arr[2]);
                check++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void writeGrades(String[]student,double[]grade,
                                   String basefilename){
        try(BufferedWriter error=
                    new BufferedWriter(new FileWriter(basefilename+
                            "_StudentGrade.txt"))){
            for (int i = 0; i < student.length; i++) {
                if (grade[i]!=-1){
                    error.write(student[i]+" "+ grade[i] +
                            " "+gradeLetter(grade[i])+" "+
                            gpaPoints((grade[i])) +"" +
                            " "+status(grade[i])+"\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }try(BufferedWriter error=
                     new BufferedWriter(new FileWriter(basefilename+
                             "_log.txt"))){
            for (int i = 0; i < student.length; i++) {
                if (grade[i]==-1){
                    error.write("ERROR: Student "+student[i]+ " - cannot " +
                            "calculate due to invalid " +
                            "grade entered \n");


                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        String basefilename=args[0];
        String filename1=args[0]+"_CourseDetails.txt";
        int categor=countCategory(filename1);
        String[] category =new String[categor];
        int[] quantity =new int[categor];
        int[] weight =new int[categor];
        getCategory(category,quantity,weight,filename1);
        String filename2=args[0]+"_StudentScores.txt";
        int categor2=countCategory(filename2);
        String[] student =new String[categor2];
        double[] grade =new double[categor2];
        try{
            Scanner input=new Scanner(new FileReader(filename2));
            int count=0;
            while (input.hasNextLine()){
                String checck=input.nextLine();
                String[]arr=checck.split(" ");
                student[count]=arr[0];
                double[] gradearr =new double[arr.length-1];
                int count2=0;
                for (int i = 1; i < arr.length; i++) {
                    gradearr[count2]=Double.valueOf(arr[i]);
                    count2++;
                }
                int sum=0;
                int errorquan=0;
                int dquan=0;
                double[] totalarr =new double[quantity.length];
                for (int i = 0; i < quantity.length; i++) {
                    errorquan= errorquan+quantity[i];
                    sum+=quantity[i];
                    double total=0;
                    for (int j = dquan; j < sum; j++) {
                        total+=gradearr[j];
                    }
                    totalarr[i]=total;
                    if (errorquan<=0){
                        try(BufferedWriter error=
                                    new BufferedWriter(new FileWriter(
                                            basefilename+
                                                    "_log.txt"))){
                            error.write("ERROR: Course details - invalid "+
                                    "quantity" +
                                    " - quantity can not be negative");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }System.exit(1);
                    }
                    dquan+=quantity[i];
                }double weii=0;
                double[] newarr =new double[quantity.length];
                for (int i = 0; i < quantity.length; i++) {
                    newarr[i]+=(totalarr[i]/quantity[i])*weight[i]/100;
                    weii= weii+weight[i];

                }if (weii!=100){
                    try(BufferedWriter error=
                                new BufferedWriter(new FileWriter(
                                        basefilename+
                                                "_log.txt"))){
                        error.write("ERROR: Course details - invalid " +
                                "weight - does " +
                                "not sum to 100");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }System.exit(1);
                }
                double overall=0;
                for (int i = 0; i < quantity.length; i++) {
                    overall+=newarr[i];
                }
                grade[count]=overall;
                for (int i = 0; i < gradearr.length; i++) {
                    if (gradearr[i]<0 || gradearr[i]>100)
                        grade[count]=-1;
                }
                count++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        writeGrades(student,grade,basefilename);
    }

    public static String gradeLetter(Double grade) {
        if (grade >= 88 && grade <= 100)
            return "AA";
        else if (grade < 88 && grade >= 81)
            return "AB";
        else if (grade < 81 && grade >= 74)
            return "BB";
        else if (grade < 74 && grade >= 67)
            return "BC";
        else if (grade < 67 && grade >= 60)
            return "CC";
        else if (grade < 60 && grade >= 53)
            return "CD";
        else if (grade < 53 && grade >= 46)
            return "DD";
        else if (grade < 46 && grade >= 35)
            return "FD";
        else if (grade < 35 && grade >= 0)
            return "FF";
        return "ERROR";
    }

    public static String gpaPoints(Double grade) {
        if (grade >= 88 && grade <= 100)
            return "4.0";
        else if (grade < 88 && grade >= 81)
            return "3.5";
        else if (grade < 81 && grade >= 74)
            return "3.0";
        else if (grade < 74 && grade >= 67)
            return "2.5";
        else if (grade < 67 && grade >= 60)
            return "2.0";
        else if (grade < 60 && grade >= 53)
            return "1.5";
        else if (grade < 53 && grade >= 46)
            return "1.0";
        else if (grade < 46 && grade >= 35)
            return "0.5";
        else if (grade < 35 && grade >= 0)
            return "0.0";
        return "ERROR";
    }

    public static String status(Double grade) {
        if (grade >= 88 && grade <= 100)
            return "passed";
        else if (grade < 88 && grade >= 81)
            return "passed";
        else if (grade < 81 && grade >= 74)
            return "passed";
        else if (grade < 74 && grade >= 67)
            return "passed";
        else if (grade < 67 && grade >= 60)
            return "passed";
        else if (grade < 60 && grade >= 53)
            return "conditionally passed";
        else if (grade < 53 && grade >= 46)
            return "conditionally passed";
        else if (grade < 46 && grade >= 35)
            return "failed";
        else if (grade < 35 && grade >= 0)
            return "failed";
        return "ERROR";
    }
}
