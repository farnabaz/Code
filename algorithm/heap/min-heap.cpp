#include<iostream>

using namespace std;

// maximum array length
#define MAX_LENGTH 1000

// heap array
int arr[MAX_LENGTH];

// current heap length
int length;

/**
 *  left child of node i
 */
int left(int i){
    return (i << 1) + 1;
}

/**
 *  right child of node i
 */
int right(int i){
    return ((i + 1) << 1);
}

/**
 *  heapify
 */
void heapify(int i){
    int r = right(i);
    int l = left(i);
    
    int min = arr[i], min_index = i;
    
    if (r < length && arr[i] > arr[r]) {
        min_index = r;
        min = arr[min_index];
    }
    
    if (l < length && min > arr[l]) {
        min_index = l;
        min = arr[min_index];
    }
    
    if (i != min_index) {
        arr[min_index] = arr[i];
        arr[i] = min;
        heapify(min_index);
    }

}

/**
 *  make heap
 */
void make_heap(){
    for (int i = ((length - 1) << 1); i >= 0; i--) {
        heapify(i);
    }
}

/**
 *  heap sort
 */
void heap(){
    int tmp;
    int n = length;
    for (length -- > 0) {
        tmp = arr[0];
        arr[0] = arr[length];
        arr[length] = tmp;
        heapify(0);
    }
    length = n;
}


int main(){
    cin >> length;
    for (int i = 0; i < length; i++) {
        cin >> arr[i];
    }
    make_heap();
    heap();
    for (int i = 0; i < length; i++) {
        cout << arr[i] << " ";
    }
    cout << endl;
    return 0;
}
