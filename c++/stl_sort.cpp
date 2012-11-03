#include <iostream>
#include <algorithm> // includes for sort & random_shuffle functions
#include <cstdlib>   // includes for qsort function

using namespace std;

// target array -- with length 10
int Q[10] = {3, 4, 7, 3, 1, 2, 9, 5, 7, 10};

/**
 * Print array
 */
void print();
/**
 * Quick sort compare function -- inceasing
 */
int qsort_compare_increasing(const void *a, const void *b);
/**
 * Quick sort compare function -- descending
 */
int qsort_compare_descending(const void *a, const void *b);


int main(){
    
    // sort function - increasing
    sort(Q, Q + 10);
    print();
    
    // --> shuffle array
    random_shuffle(Q, Q + 10);
    
    // sort function - descending
    sort(Q, Q + 10, greater<int>());
    print();
    
    // --> shuffle array
    random_shuffle(Q, Q + 10);
    
    // qsort function - increasing
    qsort(Q, 10, sizeof(int), qsort_compare_increasing);
    print();
    
    // --> shuffle array
    random_shuffle(Q, Q + 10);
    
    // qsort function - descending
    qsort(Q, 10, sizeof(int), qsort_compare_descending);
    print();
    
    return 0;
}


int qsort_compare_increasing(const void *a, const void *b) {
    int ai = *(int*)a;
    int bi = *(int*)b;
    return ai - bi;
}

int qsort_compare_descending(const void *a, const void *b) {
    int ai = *(int*)a;
    int bi = *(int*)b;
    return bi - ai;
}

void print() {
    for (int i=0; i<10; i++) {
        cout<<Q[i]<<" ";
    }
    cout<<endl;
}
