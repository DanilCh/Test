package com.danil;


import java.io.IOException;
import java.util.Scanner;


public class Test {

    private static final int x = 0, y = 1;

    public static void main(String args[]) throws IOException {
        double[] A,B,P;
        System.out.println("A(x;y):");
        A = inputValidator();
        System.out.println("B(x;y):");
        B = inputValidator();
        System.out.println("P(x;y):");
        P = inputValidator();
        Test test = new Test();
        double AB = Math.sqrt((B[x] - A[x])*(B[x] - A[x]) + (B[y]-A[y])*(B[y]-A[y]));
        if(AB == 0){ // A and B have the same coordinates
            double minimum = Math.sqrt((P[x]-A[x])*(P[x]-A[x]) + (P[y]-A[y])*(P[y]-A[y]));
            System.out.println("Minimum distance between P and AB = " + minimum);
            return;
        }

        System.out.println("Minimum distance between P and AB = " + test.getMinimumDistance(A,B,P));
    }


    private double getMinimumDistance(double[] A, double[] B, double[] P) {
        double middleOfAxBx = (A[x] + B[x]) / 2; // точка х середины отрезка AB
        double averageAxBx = Math.abs((A[x] - B[x]) / 2); // длина от точки Ax или Bx до середины проекции отрезка AB на x
        double distancePx = Math.abs(P[x] - middleOfAxBx); // длина от Px до середины проекции отрека AB на x
        boolean inRangeOfAb = distancePx <= averageAxBx; // проверка точки P, лежит ли она в пределах отрезка AB по оси x
        if (inRangeOfAb) {
            if (isOnTheAB(A, B, P)) // P лежит на отрезке AB => расстояние=0
                return 0;

            double AB, AP, PB;
            AB = length(B, A);
            AP = length(P, A);
            PB = length(B, P);
            double p = (AB + AP + PB) / 2; // полупериметр треугольника ABP
            double s = Math.sqrt(p * (p - AB) * (p - AP) * (p - PB)); // площадь треугольника ABP
            if (s != 0)
                return 2 * s / p; // высота опущенная с точки P на AB
        }

        // если все точки лежат на одной прямой, но P не лежит на AB
        if (A[x] != 0 && B[x] != 0) {
            return allPointsOnTheSameLine(x,A,B,P);
        }

        return allPointsOnTheSameLine(y,A,B,P);
    }

    private boolean isOnTheAB(double[] A, double[] B, double[] P){
        double[] ABVector = new double[2];
        double[] APVector = new double[2];
        double xMin, xMax, yMin, yMax;
        ABVector[x] = B[x] - A[x];
        ABVector[y] = B[y] - A[y];
        APVector[x] = P[x] - A[x];
        APVector[y] = P[y] - A[y];
        boolean isCollinear = (APVector[x]*ABVector[y] - ABVector[x]*APVector[y]) == 0;
        if(A[x] >= B[x]){
            xMin = B[x];
            xMax = A[x];
        }else{
            xMin = A[x];
            xMax = B[x];
        }
        if(A[y] >= B[y]){
            yMin = B[y];
            yMax = A[y];
        }else{
            yMin = A[y];
            yMax = B[y];
        }
        boolean isOnTheSameSegment =  P[x] <= xMax && P[x] >= xMin && P[y] <= yMax && P[y] >= yMin;
        return isCollinear && isOnTheSameSegment; //если вектора коллинеарны и точка
    }

    private double allPointsOnTheSameLine(int axis, double[] A, double[] B, double[] P){
        double[] maxPoint;
        double[] minPoint;
        if (A[axis] >= B[axis]) {
            maxPoint = A;
            minPoint = B;
        } else {
            maxPoint = B;
            minPoint = A;
        }

        if (P[axis] > maxPoint[axis])
            return length(P, maxPoint);
        else
            return length(P, minPoint);
    }

    private double length(double[] D, double[] C){
        return Math.sqrt((D[x]-C[x])*(D[x]-C[x]) + (D[y]-C[y])*(D[y]-C[y]));
    }


    public static double[] inputValidator(){
        double[] arr = new double[2]; // somePoint(x,y)
        Scanner scanner = new Scanner(System.in);
        String wrongInput;
        while(true){
            System.out.print("x:");
            if(scanner.hasNextDouble()) {
                arr[0] = scanner.nextDouble();
                System.out.print("y:");
                if(scanner.hasNextDouble()) {
                    arr[1] = scanner.nextDouble();
                    break;
                }
            }
            wrongInput = scanner.nextLine();
            System.out.println("Enter the number, please");
        }
        return arr;
    }
}

