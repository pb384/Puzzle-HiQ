import java.util.Random;
import java.util.Scanner;
import java.nio.charset.Charset;
import java.io.*;

public class MyCPLHiQ {

	static int[] mypegs = new int[15];
	  static int bettersol;
	  static int lines_req=0;
	  static int[][] moves = new int[15][3];
	  static int[][] moves_allowed = new int[100][3];//{{0, 1, 3},{0,2,5},{1, 3, 6},{1, 4, 8},{2, 4, 7},{2, 5, 9},{3, 4, 5},{3, 6, 10},{3, 7, 12},{4, 7, 11},{4, 8, 13},{5, 8, 12},{5, 9, 14},{6, 7, 8},{7, 8, 9},{10, 11, 12},{11, 12, 13},{12, 13, 14}};
	  
	  public static void main(String[] args){

		    int init_hole = 0;
		    int i=0;
		    int better=0;
		    uploadmymoves();
		    Scanner input = new Scanner(System.in);
		    System.out.print("Initial hole to be empty at: ");
		    init_hole = input.nextInt();
		    System.out.print("No of pegs you want to leave at the end of game: ");
		    better = input.nextInt();
		    bettersol = better;
		    for(i=0;i<15;i++) mypegs[i] = 1;
		    mypegs[init_hole] = 0;
		    checkforsolution(0);
		  }
	  
	  public static void uploadmymoves(){
		    try{
		      Scanner sc = new Scanner(new File("mymoves.txt"));
		      int i = 0;
		      String text = "";
		      while (sc.hasNextLine()) {
		        text = sc.nextLine();
		        String[] arr=text.split(" ");
		        for(int j=0;j<arr.length;j++) moves_allowed[lines_req][j]=Integer.parseInt(arr[j]);
		        lines_req++;
		      }
		    }
		    catch(Throwable e){
		      System.out.println(e);
		    }
		  }

	  
	  public static void checkforsolution(int num_moves){

		    int i=0;
		    int j=0;
		    int k=0;
		    int count=0;

		    for(i=0;i<15;i++){
		      if(mypegs[i]==1){
		        count+=1;
		      }
		    }
		    if(count<=bettersol || count==1){
		      System.out.println("No. of Moves: "+num_moves +" \nPegs Remaining: "+count);

		      for(j=0;j<num_moves;j++) System.out.println(moves[j][0] +" " +moves[j][1] +" " +moves[j][2]);
		      System.out.println();
		      System.out.println("Do you want to search for a better solution? (yes/no): ");
		      Scanner scanner = new Scanner(System.in);
		      String resp = scanner.nextLine();
			if(resp.equalsIgnoreCase("no")){
		        File f = new File("output.txt");
		        System.out.println("Thank you!! Solution is in output.txt");
		        try{

		          OutputStream fo = new FileOutputStream(f);
		          for(i=0;i<num_moves;i++){
		            fo.write((moves[i][0]+" "+moves[i][1]+" "+moves[i][2]+"\n").getBytes("utf-8"));
		          }
		          fo.close();

		          System.exit(1);
		        }
		        catch(Throwable e){}
		      }
		      if(bettersol!=1) bettersol--;
		    }

		    for(k=0;k<15;k++){
		      if(mypegs[k]==1) continue;
		      for(i=0;i<15;i++){
		        for(j=0;j<15;j++){
		          if(check_possiblemove(i,j,k)==1){

		            mypegs[i] = 0;
		            mypegs[j] = 0;
		            mypegs[k] = 1;

		            moves[num_moves][0] = i;
		            moves[num_moves][1] = j;
		            moves[num_moves][2] = k;
		            checkforsolution(num_moves+1);

		            mypegs[i] = 1;
		            mypegs[j] = 1;
		            mypegs[k] = 0;
		          }
		        }
		      }
		    }
		  }

	  
	  
	  int findmyrow(int num){
	    int i = 0;
	    int count = 2;
	    if(num==0) return 1;
	    while(i<=num) {
	      i+= count;
	      count++;
	    }
	    return count-1;
	  }

	  public static int check_possiblemove(int from, int over, int to){

	    for(int i=0;i<lines_req;i++){
	      if(mypegs[from]==1 && mypegs[to]==0 && mypegs[over]==1){
	        if(from == moves_allowed[i][0] && over == moves_allowed[i][1] && to==moves_allowed[i][2]) return 1;
	        if(from == moves_allowed[i][2] && over == moves_allowed[i][1] && to==moves_allowed[i][0]) return 1;
	      }
	    }
	    return 0;
	  }

	   

	
}
