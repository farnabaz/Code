#include<iostream>
#include<list>

using namespace std;

// maximum graph nodes
#define MAX_NODES 1000

// graph adjacency list
list<int> graph[MAX_NODES];

// nodes color
short color[MAX_NODES];

// graph nodes count
int nodes;

/**
 *  init
 */
void init(){
    for (int i=0; i<MAX_NODES; i++) {
        color[i] = 0;
    }
}

/**
 *  Visit each node and every descendants
 */
void dfs_visit(int i){
    color[i] = 1;
    int value;

    // visited

    list<int>::iterator it = graph[i].begin();
    for (; it != graph[i].end(); it++) {
        value = (int) *it;
        if (color[value] == 0) {
            dfs_visit(value);
        }
    }
    
    // processed
}

/**
 *  Call dfs_visit for every unvisited node
 */
void dfs(){
    for (int i=0; i<nodes; i++) {
        if (color[i] == 0) {
            dfs_visit(i);
        }
    }
}
