import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class ImgQuadTreeFileCreator {

    //constructor:
    public ImgQuadTreeFileCreator(){
        
        String filename = "smileyface.txt"; //can be changed for a different picture
        File image = new File(filename);
        int dimension = 256; //can be changed if the number of pixels was different
        int[][] imageArray = new int[dimension][dimension];

        /*This class takes input from a file which includes a 2D array's data fields in row-major
        order. It has to create a file which includes the data of a compressed image
        represented by an ImgQuadTree printed in that file in pre-order. But first, the array
        represented in the first file is created in the constructor, afterwards, the compressed
        image's file is created.
        */

        try{
            Scanner input = new Scanner(image);
            int[][] ar = createArray(input, imageArray); //creating the array first,
            createFile(ar); //then creating the file using that array.
        }
        catch(FileNotFoundException ex){
            System.out.println(ex.getMessage());
        }

    }

    //methods:

    /*
     The createArray method simply creates a 2D array from the file using a nested for loop, then it
     returns that array.
     */
    public int[][] createArray(Scanner scan, int[][] array){ 

        for(int i = 0; i <= array.length-1; i++){ 
            for(int j = 0; j <= array.length-1; j++){ 
                array[i][j] = scan.nextInt();
            }
        }
        return array;
    }

    /*
     The method same was created because in the createFile method, if a portion of the array
     has the same values throughout, this means it can be represneted by a leaf. This condition is
     checked for many times in the createFile method, so it was easier to create a method which
     checks for it for readability and reusability.
     This method takes the indices which represent the portion of the array that will be checked,
     and it simply traverses through that portion of the array. The veriable "val" holds the integer
     in the first element of this portion, and then all of the array's element are compared to val
     during the traversal. If an element's value != val, then the method same returns false. If the
     traversal finishes without returning false, then the method returns true.
     */
    public boolean same(int[][] array, int start_horiz, int end_horiz, int start_vert, int end_vert){
        int val = array[start_vert][start_horiz];
        for(int i = start_horiz; i <= end_horiz; i++){
            for(int j = start_vert; j <= end_vert; j++){
                if(array[j][i] != val){
                    return false;
                } 
            }
        }
        return true;
    }

    //The createFile method:
    /*
     this method has a helper method. First, the user is asked to enter the name of the file which will
     include the compressed image, and a PrintWriter object is created to write in that file. The method
     same is used to see if all the elmenets in the array are the same, if they are then one number
     only is printed into the file. Otherwises, -1 is printed, and then the array is sent to the helper
     method which breaks it down into 4 quarters, since each quarter represnts one child for the node
     who's intensity is -1. After the recursion is finished, the PrintWriter object is closed.
     */
    public void createFile(int[][] array){

        Scanner input = new Scanner(System.in);
        System.out.println("Enter the name of the file which will include the compressed image: ");
        String filename = input.next(); 
        File txtFile = new File(filename);
        try{
            PrintWriter output = new PrintWriter(txtFile);
            boolean identical = same(array, 0, array.length-1, 0, array.length-1);
            if(identical){
                output.println(array[0][0]);
            }
            else{
                output.println("-1");
                createFile(output, array, 0, array.length-1, 0, array.length-1); //it's going to be broken down
                                                                                              //in the helper method into its 4 parts
            }
            output.close();
        }
        catch(FileNotFoundException ex){
            System.out.println(ex.getMessage());
        }
    }

    //its helper method:
    /*
     similar to how indices were adjusted in the getImageArray method in the ImgQuadTree class, they
     are adjusted in this method because each quarter represents a child of the parent node. Each
     Quadrant is checked using the method same, if same returns true then the number in that quadrant
     is printed into the file. Otherwise, -1 is printed and then this quadrant is sent recursively to
     the same method. This process is repeated 4 times, once with each quadrant, once all quadrants
     are checked, the recursion ends.
     Note: even though it is understood that each quadrant represents a child, an ImgQuadTree is not
     created, rather the values are printed directly into the file, in a manner identical to what
     a pre-order traversal of an ImgQuadTree would have resulted in.
     */
    public void createFile(PrintWriter output, int[][] array, int start_horiz, int end_horiz, int start_vert, int end_vert){

        //Q1: the first quadrant of the array, AKA the first child of the parent node
        boolean identical = same(array, start_horiz, (start_horiz + end_horiz)/2, start_vert,(start_vert+end_vert)/2);
        if(identical){
            int printable = array[start_vert][start_horiz];
            output.println(printable);
        }
        else{
            output.println("-1");
            createFile(output, array, start_horiz, (start_horiz + end_horiz)/2, start_vert,(start_vert+end_vert)/2);
        }
        //Q2:
        boolean identical_2 = same(array, (start_horiz + end_horiz)/2+1, end_horiz, start_vert, (start_vert+end_vert)/2);
        if(identical_2){
            int printable = array[start_vert][(start_horiz + end_horiz)/2+1];
            output.println(printable);
        }
        else{
            output.println("-1");
            createFile(output, array, (start_horiz + end_horiz)/2+1, end_horiz, start_vert, (start_vert+end_vert)/2);
        }
        //Q3:
        boolean identical_3 = same(array, start_horiz, (start_horiz + end_horiz)/2, (start_vert+end_vert)/2+1, end_vert);
        if(identical_3){
            int printable = array[(start_vert+end_vert)/2+1][start_horiz];
            output.println(printable);
        }
        else{
            output.println("-1");
            createFile(output, array, start_horiz, (start_horiz + end_horiz)/2, (start_vert+end_vert)/2+1, end_vert);
        }
        //Q4:
        boolean identical_4 = same(array,  (start_horiz + end_horiz)/2+1, end_horiz, (start_vert+end_vert)/2+1, end_vert);
        if(identical_4){
            int printable = array[(start_vert+end_vert)/2+1][(start_horiz + end_horiz)/2+1];
            output.println(printable);
        }
        else{
            output.println("-1");
            createFile(output, array, (start_horiz + end_horiz)/2+1, end_horiz, (start_vert+end_vert)/2+1, end_vert);
        }

    }

}