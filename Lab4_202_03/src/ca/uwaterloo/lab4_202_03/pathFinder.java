package ca.uwaterloo.lab4_202_03;

import java.util.ArrayList;
import java.util.List;

import mapper.MapView;
import mapper.NavigationalMap;
import android.graphics.PointF;

public class pathFinder {
	
	PointF start;
	PointF end;
	List<PointF> pathvals;
	NavigationalMap map;
	MapView mv;
	
	public pathFinder(PointF start, PointF end, MapView mv, NavigationalMap map){
		this.start = start;
		this.end = end;
		this.mv = mv;
		this.map = map;
	}
	
	
	public List<PointF> getPathvals() {
		return pathvals;
	}

	public void findSmallestPath(){
		
		//Pathfinding algorithm goes here, use the start and end and populate the list pathvals with 
		//the Points for closest path to destination.
		pathvals = new ArrayList<PointF>();
		PointF tmppoint = new PointF();
		PointF tmp2point = new PointF();
		
		if(map.calculateIntersections(start, end).size() == 0){
			pathvals.add(start);
			pathvals.add(end);
			mv.setUserPath(pathvals);
			return;
		}else{
			pathvals.add(start);
			tmppoint.x = map.calculateIntersections(start,end).get(0).getPoint().x;
			tmppoint.y = (float) 9;
			pathvals.add(tmppoint);
			if(map.calculateIntersections(tmppoint, end).size() == 0){
				pathvals.add(end);
				mv.setUserPath(pathvals);
				return;
			}else{
				tmp2point.x = end.x;
				tmp2point.y = (float) 9;
				pathvals.add(tmp2point);
				pathvals.add(end);
				mv.setUserPath(pathvals);
				return;
			}
			
		}
	}
		
	
}
