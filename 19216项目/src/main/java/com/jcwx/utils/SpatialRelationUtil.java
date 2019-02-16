package com.jcwx.utils;

import java.util.List;

import com.jcwx.entity.util.Area;

public class SpatialRelationUtil {
	  private SpatialRelationUtil() {  
	    }  
	  
	    public class Point {  
	        double x;  
	        double y;  
	        
	        
	        public Point(Double px, Double py) {  
	            this.x = px;  
	            this.y = py;  
	        }  
	    }  
	  
	    /** 
	     * 返回一个点是否在一个多边形区域内 
	     * 
	     * @param mPoints 多边形坐标点列表 
	     * @param point   待判断点 
	     * @return true 多边形包含这个点,false 多边形未包含这个点。 
	     */  
	    public static boolean isPolygonContainsPoint(List<Area> mPoints, Area point) {  
	        int nCross = 0;  
	        for (int i = 0; i < mPoints.size(); i++) {  
	        	Area p1 = mPoints.get(i);  
	        	Area p2 = mPoints.get((i + 1) % mPoints.size());  
	            // 取多边形任意一个边,做点point的水平延长线,求解与当前边的交点个数  
	            // p1p2是水平线段,要么没有交点,要么有无限个交点  
	            if (p1.getPy() == p2.getPy())  
	                continue;  
	            // point 在p1p2 底部 --> 无交点  
	            if (point.getPy() < Math.min(p1.getPy(), p2.getPy()))  
	                continue;  
	            // point 在p1p2 顶部 --> 无交点  
	            if (point.getPy() >= Math.max(p1.getPy(), p2.getPy()))  
	                continue;  
	            // 求解 point点水平线与当前p1p2边的交点的 X 坐标  
	            double x = (point.getPy() - p1.getPy()) * (p2.getPx() - p1.getPx()) / (p2.getPy() - p1.getPy()) + p1.getPx();  
	            if (x > point.getPx()) // 当x=point.x时,说明point在p1p2线段上  
	                nCross++; // 只统计单边交点  
	        }  
	        // 单边交点为偶数，点在多边形之外 ---  
	        return (nCross % 2 == 1);  
	    }  
	  
	    /** 
	     * 返回一个点是否在一个多边形边界上 
	     * 
	     * @param mPoints 多边形坐标点列表 
	     * @param point   待判断点 
	     * @return true 点在多边形边上,false 点不在多边形边上。 
	     */  
	    public static boolean isPointInPolygonBoundary(List<Point> mPoints, Point point) {  
	        for (int i = 0; i < mPoints.size(); i++) {  
	            Point p1 = mPoints.get(i);  
	            Point p2 = mPoints.get((i + 1) % mPoints.size());  
	            // 取多边形任意一个边,做点point的水平延长线,求解与当前边的交点个数  
	  
	            // point 在p1p2 底部 --> 无交点  
	            if (point.y < Math.min(p1.y, p2.y))  
	                continue;  
	            // point 在p1p2 顶部 --> 无交点  
	            if (point.y > Math.max(p1.y, p2.y))  
	                continue;  
	  
	            // p1p2是水平线段,要么没有交点,要么有无限个交点  
	            if (p1.y == p2.y) {  
	                double minX = Math.min(p1.x, p2.x);  
	                double maxX = Math.max(p1.x, p2.x);  
	                // point在水平线段p1p2上,直接return true  
	                if ((point.y == p1.y) && (point.x >= minX && point.x <= maxX)) {  
	                    return true;  
	                }  
	            } else { // 求解交点  
	                double x = (point.y - p1.y) * (p2.x - p1.x) / (p2.y - p1.y) + p1.x;  
	                if (x == point.x) // 当x=point.x时,说明point在p1p2线段上  
	                    return true;  
	            }  
	        }  
	        return false;  
	    }  
}
