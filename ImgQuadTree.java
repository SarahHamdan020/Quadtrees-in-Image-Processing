//بسم الله الرحمن الرحيم

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class ImgQuadTree {

	//instance vars:
	private QTNode root = new QTNode();
	private int nodes_num = 1; //initially we only have the root
	private int leaves_num = 0;

	//constructor:
	public ImgQuadTree(String filename){

		File image = new File(filename);
		try{
			Scanner input = new Scanner(image);
			this.root.intensity = input.nextInt(); 
			add(root, input); //The constructor creates the tree through the add method
		}
		catch(FileNotFoundException ex){
			System.out.println(ex.getMessage());
		}
			
	}

	//methods:

	/*The add method takes a node "root" and input from the file, if the nextInt was != -1, 
	this means the int is a child of the node (this only applies to the first four integers,
	past these 4, the next int is the child of the node's parent). If the nextInt == -1, this method
	goes recursively with this node as the parent node of the following integers. With each parent
	node this method checks for 4 children only, then it stops, this is basically the condition that
	allows the recursion to end.
	Note: the number itself is not the child but rather the intensity of the child, therefore
	a node is created each time and is given this intensity.
	*/
	public void add(QTNode root, Scanner input){

			int num_1 = input.nextInt();
			nodes_num ++; //each number from the input becomes a node, so this counter is incremented.
			if(num_1 == -1){
				QTNode c = new QTNode();
				root.child_1 = c;
				root.child_1.intensity = num_1;
				add(root.child_1, input);
			}
			else{
				leaves_num ++; //if it's != -1 then it is a leaf, so this counter is incremented.
				QTNode c = new QTNode();
				root.child_1 = c;
				root.child_1.intensity = num_1;
			}

			int num_2 = input.nextInt();
			nodes_num ++;
			if(num_2 == -1){
				QTNode c = new QTNode();
				root.child_2 = c;
				root.child_2.intensity = num_2;
				add(root.child_2, input);
			}
			else{
				leaves_num ++;
				QTNode c = new QTNode();
				root.child_2 = c;
				root.child_2.intensity = num_2;
			}

			int num_3 = input.nextInt();
			nodes_num ++;
			if(num_3 == -1){
				QTNode c = new QTNode();
				root.child_3 = c;
				root.child_3.intensity = num_3;
				add(root.child_3, input);
			}
			else{
				leaves_num ++;
				QTNode c = new QTNode();
				root.child_3 = c;
				root.child_3.intensity = num_3;
			}

			int num_4 = input.nextInt();
			nodes_num ++;
			if(num_4 == -1){
				QTNode c = new QTNode();
				root.child_4 = c;
				root.child_4.intensity = num_4;
				add(root.child_4, input);
			}
			else{
				leaves_num ++;
				QTNode c = new QTNode();
				root.child_4 = c;
				root.child_4.intensity = num_4;
			}
	}

	/*
	 since nodes_num and leaves_num are instance variables incremented in the add method, their getters
	 only have to return them. There is no need for traversal here.
	 */
	public int getNumNodes() {
		return nodes_num;
	}
	
	public int getNumLeaves() {
		return leaves_num;
	}

	//the getImageArray method:
	/*
	 this getImageArray method basically sets up the recursion, most of the work is done in
	 its helper method...
	 */
	public int[][] getImageArray(){
		int dimention = 256; //dimention can be changed depending on the number of pixels.
		int[][] image_array = new int[dimention][dimention];
		getImageArray(image_array, this.root, 0, dimention-1, 0, dimention-1);
		return image_array;
	}

	//its helper method:
	/*
	 Once we know we hit a leaf and we know which part of the array it should be in, filling the array
	 is simply done in a nested for loop. The trick is in changing the array's indices which we are
	 searching for leaves in. We start with the root node and each of its children represents a quarter
	 of the array, and the indices are changed accordingly. The same adjustment of the indices is done
	 with each child node recursively if its intensity == -1.
	 */
	public void getImageArray(int[][] array, QTNode node, int start_horiz, int end_horiz, int start_vert, int end_vert){
		if(node.intensity == -1){
			getImageArray(array, node.child_1, start_horiz, (start_horiz + end_horiz)/2, start_vert,(start_vert+end_vert)/2);
			getImageArray(array, node.child_2, (start_horiz + end_horiz)/2+1, end_horiz, start_vert, (start_vert+end_vert)/2);
			getImageArray(array, node.child_3, start_horiz, (start_horiz + end_horiz)/2, (start_vert+end_vert)/2+1, end_vert);
			getImageArray(array, node.child_4, (start_horiz + end_horiz)/2+1, end_horiz, (start_vert+end_vert)/2+1, end_vert);
		}
		else{
			
			for(int i = start_horiz; i <= end_horiz; i++){
				for(int j = start_vert; j <= end_vert; j++){
					array[j][i] = node.intensity;
				}
			}
		}

	}

	//This is an extra method which allows the user to view the 2D array
    public static void print2DArray(int array[][]){
        for (int i = 0 ; i < array.length ; i++){
            System.out.println(Arrays.toString(array[i])); 
        }
    } 

	/*
	 The QTNode class is how each ImgQuadTree node is created. Each node has 5 attributes:
	 4 children (which are also QTNodes) + 1 intensity value.
	 */
	private class QTNode {

		//instance vars:
		private QTNode child_1;
		private QTNode child_2;
		private QTNode child_3;
		private QTNode child_4;
		private int intensity;

		//constructor:
		QTNode(){

			this.intensity = -1; //The intensity is initially == -1 and can be changed later.
			this.child_1 = null; //Each child is initially set to null.
			this.child_2 = null;
			this.child_3 = null;
			this.child_4 = null;

		}

	}
	
}