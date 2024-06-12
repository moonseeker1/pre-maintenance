package com.system;

import java.util.Deque;
import java.util.LinkedList;

class Solution {
    int[][] vist;
    public int maxDistance(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        vist = new int[n][m];
        int max = 0;
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(vist[i][j]!=1&&grid[i][j]!=1){
                    max = Math.max(bfs(grid,i,j,n,m),max);
                    vist = new int[n][m];
                }
            }
        }
        if(max==0){
            return -1;
        }
        else{
            return max;
        }
    }
    public int bfs(int[][]grid,int x,int y,int n,int m){
        Deque<int[]> stack = new LinkedList<>();
        stack.push(new int[]{x,y});
        while(!stack.isEmpty()){
            int[] node = stack.pop();
            vist[node[0]][node[1]]=1;
            if(grid[node[0]][node[1]]==1){
                return Math.abs(x-node[0])+Math.abs(y-node[1]);
            }
            if(x+1<n&&vist[x+1][y]!=1){
                stack.push(new int[]{x+1,y});
            }
            if(x-1>=0&&vist[x-1][y]!=1){
                stack.push(new int[]{x-1,y});
            }
            if(y-1>=0&&vist[x][y-1]!=1){
                stack.push(new int[]{x,y-1});
            }
            if(y+1<n&&vist[x][y+1]!=1){
                stack.push(new int[]{x,y+1});
            }
        }
        return 0;
    }
}