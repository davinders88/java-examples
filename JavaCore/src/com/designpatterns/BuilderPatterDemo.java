package com.designpatterns;

public class BuilderPatterDemo {
	public static void main(String[] args) {
	     final CarBuildDirector carBuildDirector = new CarBuildDirector(new Car.CarBuilderImpl());
	     System.out.println(carBuildDirector.construct());
	}
}

class CarBuildDirector {
    private Car.CarBuilderImpl builder;

    public CarBuildDirector(final Car.CarBuilderImpl builder) {
        this.builder = builder;
    }

    public Car construct() {
        return builder.setWheels(4)
                      .setColor("Red")
                      .build();
    }
}


class Car {
    private int wheels;
    private String color;

    private Car() {
    }

    public String getColor() {
        return color;
    }

    public void setColor(final String color) {
        this.color = color;
    }

    public int getWheels() {
        return wheels;
    }

    public void setWheels(final int wheels) {
        this.wheels = wheels;
    }

    public static class CarBuilderImpl {
        private Car car;

        public CarBuilderImpl() {
            car = new Car();
        }

        public Car build() {
            return car;
        }

        public CarBuilderImpl setColor(final String color) {
            car.setColor(color);
            return this;
        }

        public CarBuilderImpl setWheels(final int wheels) {
            car.setWheels(wheels);
            return this;
        }
    }
    
    @Override
    public String toString() {
        return "Car [wheels = " + wheels + ", color = " + color + "]";
    }
}