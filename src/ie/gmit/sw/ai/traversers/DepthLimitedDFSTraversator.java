package ie.gmit.sw.ai.traversers;

import ie.gmit.sw.ai.node.*;
public class DepthLimitedDFSTraversator implements Traversator{
	private Node[][] maze;
	private int limit;
	private boolean keepRunning = true;
	private long time = System.currentTimeMillis();
	private int visitCount = 0;
    private Node goal;
	
	public DepthLimitedDFSTraversator(int limit,Node goal){
		this.limit = limit;
		this.goal = goal;
	}
	
	public void traverse(Node[][] maze, Node node) {
		this.maze = maze;
        // System.out.println("Search with limit " + limit);
		dfs(node, 1);
	}
	
	private void dfs(Node node, int depth){
		if (!keepRunning || depth > limit) return;

        node.setVisited(true);
		visitCount++;
		
		if (node.equals(goal)){
	        time = System.currentTimeMillis() - time; //Stop the clock
	        TraversatorStats.printStats(node, time, visitCount);
	        keepRunning = false;
			return;
		}
		
//		try { //Simulate processing each expanded node
//			Thread.sleep(50);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		
		Node[] children = node.adjacentNodes(maze);
		for (int i = 0; i < children.length; i++) {
			if (children[i] != null && !children[i].isVisited()){
				children[i].setParent(node);
				dfs(children[i], depth + 1);
			}
		}
	}
}