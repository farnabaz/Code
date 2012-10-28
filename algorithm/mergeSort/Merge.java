import java.io.*;
import java.util.*;
import java.math.*;

public class Merge{

    public static void MergeSort(int arg0[]){
        MergeSort(arg0, 0, arg0.length-1);
    }
    
    private static void MergeSort(int arg0[], int start, int end){
        if (start >= end) return;
        int middle = (end + start) / 2;
        
        MergeSort(arg0, start, middle);
        MergeSort(arg0, middle + 1, end);
        
        Merge(arg0, start, middle, end);
    }

   /**
    *	Merge two sorted array
    */    
    private static void Merge(int arg0[], int start, int middle, int end){
        int tmp[] = new int[end - start + 1];
        int s = start;
        int m = middle+1;
        for (int i = 0; i < tmp.length; i++) {
            if (s > middle) {
                while(m <= end)
                    tmp[i++] = arg0[m++];
                break;
            } else if ( m > end ) {
                while (s <= middle)
                    tmp[i++] = arg0[s++];
                break;
            }
            if (arg0[s] < arg0[m]) {
                tmp[i] = arg0[s++];
            } else {
                tmp[i] = arg0[m++];
            }
        }
        for (int i=0; i< tmp.length; i++) {
            arg0[start+i] = tmp[i];
        }
    }

    public static void main(String args[]){
        int a[] = {5, 6, 9, 1, 4, 13, 76, 343, 1};
        
	MergeSort(a);
        
	for (int i=0;i<a.length;i++)
            System.out.println(a[i]);
    }
}
