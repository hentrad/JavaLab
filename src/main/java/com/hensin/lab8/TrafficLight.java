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
        if (greenTime < MIN_GREEN || greenTime > MAX_GREEN) {
            throw new LabException(Code.GREEN_TIME_OUT_OF_RANGE, greenTime);
        }
        if (lanes < MIN_LANES || lanes > MAX_LANES) {
            throw new LabException(Code.LANES_OUT_OF_RANGE, lanes);
        }
        if (cars < MIN_CARS || cars > MAX_CARS) {
            throw new LabException(Code.CARS_OUT_OF_RANGE, cars);
        }
        if (avgCrossTime < MIN_CROSS || avgCrossTime > MAX_CROSS) {
            throw new LabException(Code.CROSS_TIME_OUT_OF_RANGE, avgCrossTime);
        }

        this.greenTime = greenTime;
        this.lanes = lanes;
        this.cars = cars;
        this.avgCrossTime = avgCrossTime;
    }

    public int getGreenTime()    { return greenTime; }
    public int getLanes()        { return lanes; }
    public int getCars()         { return cars; }
    public int getAvgCrossTime() { return avgCrossTime; }

    public int carsPassed() {
        int perLane = greenTime / avgCrossTime;
        return lanes * perLane;
    }

    public int carsRemaining() {
        return Math.max(0, cars - carsPassed());
    }

    public int timeToClear() {
        int remaining = carsRemaining();
        if (remaining == 0) return 0;
        int cycles = (int) Math.ceil(remaining / (double) lanes);
        return cycles * avgCrossTime;
    }
}