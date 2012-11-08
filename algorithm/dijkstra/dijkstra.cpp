#include <iostream>
#include <set>
#include <list>

using namespace std;

#define INF 987654321 // Infinity
#define MAX 10000     // max graph nodes

typedef pair< int, int > ii;

/**
 * copy c to all elements of array a
 */
void copy_all(int a[], int count, int c){
    while (count>0) {
        a[--count]=c;
    }
}

list< ii > G[MAX];  // adjacency list og graph
int D[MAX];         // dijkstra final list ( distance from start node )
set< ii > Q;        // 

/**
 * Start dijkstra algorithm on graph G - calculate smaller path
 *
 * @param n node count
 * @param start first node
 */
void dijkstra(int n, int start) {
    // initialize array
    copy_all(D, n, INF);
    
    D[start] = 0;
    Q.insert(ii(0,start));
    
    ii top;
    int v, d, v2, cost;
    
    while (!Q.empty()) {
    
        // get nearest vertex
        top = *Q.begin();
        Q.erase(Q.begin());
        
        // vertex index
        v = top.second;
        // vertex reach cost
        d = top.first;
        
        // iterate throw all neighbors of v
        list< ii >::iterator it = G[v].begin();
        while (it != G[v].end()) {
            
            ii x=(ii)(*it);
            v2   = x.first;
            cost = x.second;
            
            if (D[v2] > D[v] + cost) {
                // smaller path found
                if (D[v2] != INF) {
                    Q.erase(Q.find(ii(D[v2], v2)));
                }
                D[v2] = D[v] + cost;
                Q.insert(ii(D[v2], v2));
            }
            it++;
        }
        
    }
}

/**
 * main function
 */
int main(){
    int n,m;
    scanf("%d %d", &n, &m);
    int a, b, w;
    for (int i=0; i<m; i++) {
        scanf("%d %d %d", &a, &b, &w);
        G[a].push_back(ii(b, w));
        // comment below line if graph is directed
        G[b].push_back(ii(a, w));
    }
    
    dijkstra(n, 0);
    
    for (int i=0; i<n; i++) {
        printf("cost 0 -> %d: %d \n",i, D[i]);
    }
    
    
    return 0;
}
