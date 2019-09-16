package zegar;

import java.util.*;



public class Solution {
	
	 public static String solution(int a, int b, int c, int d, int e, int f){
		boolean moszna=false;
		int temp;
		int []n=new int[6];
		n[0]=a;
		n[1]=b;
		n[2]=c;
		n[3]=d;
		n[4]=e;
		n[5]=f;
		int counter=0;
		for(int i=0;i<6;i++){
			for(int j=0;j<6;j++){
			
				if(n[i]<n[j]){
				temp=n[j];
				n[j]=n[i];
				n[i]=temp;
			}
		
		}
	}
	for(int i=0;i<6;i++){
		
			if(n[i]>5){
				counter++;
			}	
		}

		if(counter>3){
			moszna=false;	
		}
		else{
			moszna=true;
		if(counter==3){
			
			temp=n[3];
			n[3]=n[1];
			n[1]=temp;
			temp=n[4];
			n[4]=n[3];
			n[3]=temp;
		}		
		else if(counter==2){
			
			temp=n[4];
			n[4]=n[3];
			n[3]=temp;
		}
		
		
		
		
			
		
		a=n[0];
		b=n[1];
		c=n[2];
		d=n[3];
		e=n[4];
		f=n[5];
	}
		if(n[0]>2){
			moszna=false;	
		}
		if(moszna==true){
		
		return a+":"+b+":"+c+""+d+":"+e+""+f;
	}
		else{
			return "NOT POSSIBLE";
		}
	}


	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		solution(1,2,3,4,5,6);
	}

}
