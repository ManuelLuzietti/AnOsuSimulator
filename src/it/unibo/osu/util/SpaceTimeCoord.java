package it.unibo.osu.util;


import javafx.geometry.Point2D;

public class SpaceTimeCoord {
	private Point2D position;
	private double time;
	
	public SpaceTimeCoord(Point2D position, double time) {
		this.position = position;
		this.time = time;
	}
	public SpaceTimeCoord(double x,double y, double time) {
		this.position = new Point2D(x, y);
		this.time = time;
	}
	public void setPosition(Point2D position) {
		this.position = position;
	}
	public void setTime(double time) {
		this.time = time;
	}
	public Point2D getPosition() {
		return position;
	}
	public double getTime() {
		return time;
	}
	public double getX() {
		return this.position.getX();
	}
	public double getY() {
		return this.position.getY();
	}
	public void setX(double x) {
		this.position = new Point2D(x, this.position.getY());
	}
	public void  setY(double y) {
		this.position = new Point2D(this.position.getX(), y);
	}
	@Override
	public String toString() {
		return "[" + this.position + ", time " + this.time +"]";
	}
}
