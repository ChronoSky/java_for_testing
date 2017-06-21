package ru.stqa.pft.sandbox;

import static ru.stqa.pft.sandbox.Point.distance;

public class MyFirstProgram {

	public static void main (String[] args){

		Point p1 = new Point(2.0, 3.0);
		Point p2 = new Point(5.0, 7.0);

		// решение через функцию
		System.out.println("Расстояние между точками p1 (x = "+ p1.x+", y = "+ p1.y +") и p2 (x = "+ p2.x+", y = "+ p2.y +") равно "+ distance(p1,p2));

		// решение через метод
		System.out.println("Расстояние между точками p1 (x = "+ p1.x+", y = "+ p1.y +") и p2 (x = "+ p2.x+", y = "+ p2.y +") равно "+ p1.distance(p2));
	}

}