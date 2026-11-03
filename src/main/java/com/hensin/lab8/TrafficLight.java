package com.hensin.lab8;

import com.hensin.LabException;
import com.hensin.LabException.Code;

public class TrafficLight {

    public static final int MIN_GREEN = 1;
    public static final int MAX_GREEN = 300;
    public static final int MIN_LANES = 1;
    public static final int MAX_LANES = 8;
    public static final int MIN_CARS  = 0;
    public static final int MAX_CARS  = 200;
    public static final int MIN_CROSS = 1;
    public static final int MAX_CROSS = 30;

    private final int greenTime;
    private final int lanes;
    private final int cars;
    private final int avgCrossTime;

    public TrafficLight(int greenTime, int lanes, int cars, int avgCrossTime) throws LabException {
        check(greenTime,    MIN_GREEN, MAX_GREEN, Code.GREEN_TIME_OUT_OF_RANGE);
        check(lanes,        MIN_LANES, MAX_LANES, Code.LANES_OUT_OF_RANGE);
        check(cars,         MIN_CARS,  MAX_CARS,  Code.CARS_OUT_OF_RANGE);
        check(avgCrossTime, MIN_CROSS, MAX_CROSS, Code.CROSS_TIME_OUT_OF_RANGE);

        this.greenTime = greenTime;
        this.lanes = lanes;
        this.cars = cars;
        this.avgCrossTime = avgCrossTime;
    }

    private static void check(int value, int min, int max, Code code) throws LabException {
        if (value < min || value > max) {
            throw new LabException(code, value);
        }
    }

    public int getGreenTime()    { return greenTime; }
    public int getLanes()        { return lanes; }
    public int getCars()         { return cars; }
    public int getAvgCrossTime() { return avgCrossTime; }

    public int carsPassed() {
        return lanes * (greenTime / avgCrossTime);
    }

    public int carsRemaining() {
        return Math.max(0, cars - carsPassed());
    }

    public int timeToClear() {
        return (carsRemaining() + lanes - 1) / lanes * avgCrossTime;
    }
}