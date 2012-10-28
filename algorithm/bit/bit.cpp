#include <stdio.h>
#include <iostream>

using namespace std;

typedef long long number;

#define length 100005
// cumulative tree
number tree[length];

/**
 *  initialize tree
 */
void init(){
    int i = 0;
    for (i = 0; i < length; i++) {
        tree[i] = 0;
    }
}
/**
 *  get value at index ix in cumulative array
 */
number getC(int ix){
    number sum = 0;
    while (ix > 0) {
        sum += tree[ix];
        ix &= ix - 1;
    }
    return sum;
}

/**
 *  get value at index ix in normal array
 */
number getSingle(int ix){
    // return getC(ix) - getC(ix - 1);
    number value = tree[ix];
    int parent = ix & (ix - 1);
    if( ix > 0 ){
        ix = ix - 1;
        while ( parent != ix ) {
            value -= tree[ix];
            ix &= ix - 1;
        }
        return value;
    }
    return 0;
}

/**
 *  add value to index ix
 */
void putC(int ix, number value){
    while (ix < length) {
        tree[ix] += value;
        ix += ix & (-ix);
    }
}
