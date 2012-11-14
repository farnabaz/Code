#include <iostream>
#include <list>
#include <set>
#include <algorithm>

#define ii  pair< int, int>

// maximum possible nodes
#define MAX_NODES 100001

/**
 * graph edge data structure
 */
typedef struct node{
    // edge start node
    int from;
    // edge end node
    int to;
    // edge weight
    int weight;
    
    /**
     * default constructor
     */
    node(const int& f, const int& t, const int& w): from(f), to(t), weight(w) {}
    
    /**
     * override less than operator
     */
    bool operator<(const node& n) const {
        if (weight == n.weight) {
            if (from == n.from) {
                return to < n.to;
            } else {
                return from < n.from;
            }
        } else {
            return weight < n.weight;
        }
    }
    
};
using namespace std;

// node visitation
int visited[MAX_NODES];

// graph adjacency list
list< ii > G[MAX_NODES];

set< node > S;

// minimum spanning tree
set< node > MST;

/**
 * start prim's algorithm in graph G
 *
 * @params n nodes count
 * @params start prim algorithm start node
 */
int prim(int n, int start){
    
    // first node is visited
    visited[start] = 1;
    
    // insert first available edges
    list< ii >::iterator it = G[start].begin();
    while ( it != G[start].end()) {
        ii x = (ii) *it;
        node new_node(start, x.first, x.second);
        
        S.insert( new_node );
        it++;
    }
    
    // main lop
    while (MST.size() != n-1  && !S.empty()) {
        // get & remove smallest edge from set
        node top = (node)*S.begin();
        S.erase(S.begin());
        
        int to = top.to;

        // prevent from loop
        if (visited[to] == 0) {
            visited[to] = 1;
            MST.insert(top); // insert to spanning tree
            
            // insert new available edges
            list< ii >::iterator it = G[to].begin();
            while ( it != G[to].end()) {
                ii x = (ii) *it;
                if (visited[x.first] == 0) {
                    node new_node( to, x.first, x.second );
                    
                    S.insert( new_node );
                }
                it++;
            }
        }
    }
}

/**
 * clear datas
 */
void clear(){
    S.clear();
    MST.clear();
    for (int i=0; i<MAX_NODES; i++) {
        G[i].clear();
        visited[i] = 0;
    }
}


int main(){
        int n,m;
        scanf("%d", &n);
        scanf("%d", &m);

        for (int i=0; i<m; i++) {
            int a, b, w;
            scanf("%d %d %d", &a, &b, &w);
            
            G[a-1].push_back(ii(b-1, w));
            G[b-1].push_back(ii(a-1, w));
        }
        prim(n,0);
        long long sum = 0;
        set<node>::iterator it = MST.begin();
        while (it != MST.end()) {
            sum += ((node)*it).weight;
            it++;
        }
        printf("%lld\n", sum);
}
