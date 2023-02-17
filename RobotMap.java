package ru.gb.lesson1.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RobotMap {

    private final int n;
    private final int m;
    int num;

    private final List<Robot> robots;

    public RobotMap(int n, int m, int... len) {
        validateMap(n, m);
        this.n = n;
        this.m = m;
        this.robots = new ArrayList<>();
        if (len.length < 1) {
            len = addElement(len, 5);
            num = num + len[0];
        }
        
        // this.num = num;
    }
        

    static int[] addElement(int[] a, int e) {
            a = Arrays.copyOf(a, a.length + 1);
            a[a.length - 1] = e;
            return a;
    }

    private void validateMap(int m, int n) {
        if (n <= 0 || m <= 0) {
            throw new IllegalStateException("Карту создать невозможно, выберите другие значения!");
        }
    }


    public Robot createRobot(Point point) {
        validateRobot(num);
        validatePoint(point);
        Robot robot = new Robot(point);
        robots.add(robot);

        return robot;
    }

    public void validateRobot(int num) {
    
        if (robots.size() > num) {
            throw new StackOverflowError("Превышено количество роботов, новый создать невозможно!");
        }
    }

    private void validatePoint(Point point) {
        validatePointIsCorrect(point);
        validatePointIsFree(point);
    }

    private void validatePointIsCorrect(Point point) {
        if (point.x() < 0 || point.x() > n || point.y() < 0 || point.y() > m) {
            throw new IllegalStateException("Некоректное значение точки!");
        }
    }

    private void validatePointIsFree(Point point) {
        for (Robot robot : robots) {
            Point robotPoint = robot.point;
            if (robotPoint.equals(point)) {
                throw new IllegalStateException("Точка " + point + " занята!");
            }
        }
    }

    public class Robot {

        public static final Direction DEFAULT_DIRECTION = Direction.TOP;

        private Direction direction;
        private Point point;

        public Robot(Point point) {
            this.direction = DEFAULT_DIRECTION;
            this.point = point;
        }

        public void changeDirection(Direction direction) {
            this.direction = direction;
        }

        public void move() {
            Point newPoint = switch (direction) {
                case TOP -> new Point(point.x() - 1, point.y());
                case RIGHT -> new Point(point.x(), point.y() + 1);
                case BOTTOM -> new Point(point.x() + 1, point.y());
                case LEFT -> new Point(point.x(), point.y() - 1);
            };
            validatePoint(newPoint);

            System.out.println("Робот переместился с " + point + " на " + newPoint);
            this.point = newPoint;
        }

        @Override
        public String toString() {
            return point.toString() + ", [" + direction.name() + "]";
        }
    }

}
