package ca.uwaterloo.lab4_202_03;

import mapper.MapView;
import mapper.NavigationalMap;
import mapper.PositionListener;
import mapper.VectorUtils;
import android.graphics.PointF;
import android.widget.TextView;

public class positionEventListener implements PositionListener {
	
	NavigationalMap map;
	TextView directiontv;
	PointF refpoint = new PointF(0,0);
	
	public positionEventListener(NavigationalMap map, TextView directiontv){
		this.map = map;
		this.directiontv = directiontv;
	}

	@Override
	public void originChanged(MapView source, PointF loc) {
		source.setUserPoint(loc);
		pathFinder path = new pathFinder(source.getOriginPoint(), source.getDestinationPoint(), 
				source, map);
		path.findSmallestPath();
		refpoint.set(loc.x, 0);
		directiontv.setText(String.format("Directions: %.2f", Math.toDegrees(VectorUtils.angleBetween(loc, refpoint, path.getPathvals().get(1)))));
		

	}

	@Override
	public void destinationChanged(MapView source, PointF dest) {
		source.setDestinationPoint(dest);
		pathFinder path = new pathFinder(source.getOriginPoint(), source.getDestinationPoint(), 
				source, map);
		path.findSmallestPath();
		refpoint.set(source.getOriginPoint().x, 0);
		directiontv.setText(String.format("Directions: %.2f", Math.toDegrees(VectorUtils.angleBetween(source.getOriginPoint(), refpoint, path.getPathvals().get(1)))));
	}

}
