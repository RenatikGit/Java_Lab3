package com.company;
import java.io.PrintStream;
import java.nio.channels.InterruptedByTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.function.Function;

public class Main {
    public static class AnyDimensions{
        Integer width;
        Integer height;
        Integer length;
        public AnyDimensions(Integer width, Integer height, Integer length) {
            this.width = width;
            this.height = height;
            this.length = length;
        }
    }

    public static class Transport {
        Random random = new Random();
        String model;
        String color;
        Integer power;
        AnyDimensions dimensions;
        String type = "Транспорт";

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public Integer getPower() {
            return power;
        }

        public void setPower(Integer power) {
            this.power = power;
        }

        public AnyDimensions getDimensions() {
            return dimensions;
        }

        public void setDimensions(Integer width, Integer height, Integer length) {
            this.dimensions = new AnyDimensions(width, height, length);
        }
        public void getDescription()
        {
            System.out.printf("Класс = %s%n", getType());
            System.out.printf("Модель = %s%n", getModel());
            System.out.printf("Мощность = %d, цвет = %s%n", getPower(), getColor());
            System.out.println("Габариты :");
            System.out.printf("\tДлина  = %d%n", dimensions.length);
            System.out.printf("\tВысота = %d%n", dimensions.height);
            System.out.printf("\tШирина = %d%n", dimensions.width);
        }
    }

    public abstract static class TransportDecorator extends Transport {
        public Transport transport;
        public TransportDecorator(Transport transport)
        {
            this.transport = transport;
        }
        public abstract void getDescription();
    }

    public static class LandTransportDecorator extends TransportDecorator
    {
        Integer numberOfDoors;

        public LandTransportDecorator(Transport transport, Integer numberOfDoors) {
            super(transport);
            this.transport.setType("Сухопутный транспорт");
            this.numberOfDoors = numberOfDoors;
        }

        public LandTransportDecorator(Transport transport) {
            super(transport);
            this.transport.setType("Сухопутный транспорт");
            this.numberOfDoors = random.nextInt(5);
        }

        public Integer getNumberOfDoors() {
            return numberOfDoors;
        }

        public void setNumberOfDoors(Integer numberOfDoors) {
            this.numberOfDoors = numberOfDoors;
        }

        @Override
        public void getDescription()
        {
            transport.getDescription();
            System.out.printf("Дверей = %d%n", getNumberOfDoors());
        }

    }


    public static class SportsCarDecorator extends LandTransportDecorator
    {
        Float aerodynamicsRating;

        public SportsCarDecorator(Transport transport, Integer numberOfDoors, Float aerodynamicsRating) {
            super(transport, numberOfDoors);
            this.transport.setType("Спорткар");
            this.aerodynamicsRating = aerodynamicsRating;
        }

        public SportsCarDecorator(LandTransportDecorator transport) {
            super(transport);
            this.transport.setType("Спорткар");
            this.aerodynamicsRating = random.nextFloat()*100;
        }

        public Float getAerodynamicsRating() {
            return aerodynamicsRating;
        }

        public void setAerodynamicsRating(Float aerodynamicsRating) {
            this.aerodynamicsRating = aerodynamicsRating;
        }

        @Override
        public void getDescription()
        {
            this.transport.getDescription();
            System.out.printf("Показатель аэродинамики = %f%n", getAerodynamicsRating());
        }
    }

    public static class ElectricSportsCarDecorator extends  SportsCarDecorator
    {
        Integer batteryVolume;

        public ElectricSportsCarDecorator(Transport transport, Integer numberOfDoors, Float aerodynamicsRating, Integer batteryVolume) {
            super(transport, numberOfDoors, aerodynamicsRating);
            this.transport.setType("Электроспорткар");
            this.batteryVolume = batteryVolume;
        }

        public ElectricSportsCarDecorator(SportsCarDecorator transport) {
            super(transport);
            this.transport.setType("Электроспорткар");
            this.batteryVolume = random.nextInt(5000);
        }

        public Integer getBatteryVolume() {
            return batteryVolume;
        }

        public void setBatteryVolume(Integer batteryVolume) {
            this.batteryVolume = batteryVolume;
        }
        @Override
        public void getDescription()
        {
            this.transport.getDescription();
            System.out.printf("Емкость аккумулятора = %d%n", getBatteryVolume());
        }
    }


    public static class SeaTransportDecorator extends TransportDecorator
    {
        Integer liftingCapacity;

        public SeaTransportDecorator(Transport transport) {
            super(transport);
            this.transport.setType("Морской транспорт");
            this.liftingCapacity = random.nextInt(5000);
        }

        public Integer getLiftingCapacity() {
            return liftingCapacity;
        }

        public void setLiftingCapacity(Integer liftingCapacity) {
            this.liftingCapacity = liftingCapacity;
        }

        @Override
        public void getDescription()
        {
            this.transport.getDescription();
            System.out.printf("Грузоподъемность = %d%n", getLiftingCapacity());
        }
    }


    public static class AirTransportDecorator  extends TransportDecorator
    {
        Integer liftingCapacity;

        public AirTransportDecorator(Transport transport) {
            super(transport);
            this.transport.setType("Воздушный транспорт");
            this.liftingCapacity = random.nextInt(5000);
        }

        public Integer getLiftingCapacity() {
            return liftingCapacity;
        }

        public void setLiftingCapacity(Integer liftingCapacity) {
            this.liftingCapacity = liftingCapacity;
        }

        @Override
        public void getDescription()
        {
            this.transport.getDescription();
            System.out.printf("Грузоподъемность = %d%n", getLiftingCapacity());
        }
    }

    public static class TransportUpdater{
        public static Transport updateType(Integer type, Transport transport) {
            switch (type) {
                case 0 -> {
                    return new LandTransportDecorator(transport);
                }
                case 1 -> {
                    return new SportsCarDecorator(new LandTransportDecorator(transport));
                }
                case 2 -> {
                    return new ElectricSportsCarDecorator(new SportsCarDecorator(new LandTransportDecorator(transport)));
                }
                case 3 -> {
                    return new SeaTransportDecorator(transport);
                }
                case 4 -> {
                    return new AirTransportDecorator(transport);
                }
            }
            return transport;
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String[] models = {"Toyota", "Mazda", "Honda", "Tesla", "Lada"};
        String[] colors = {"Red", "Purple", "Blue", "Green", "Yellow"};
        Random random = new Random();
        System.out.println("Enter N");
        int N = in.nextInt();
        Transport[] transportArray = new Transport[N];
        for (int i=0; i < N; i++) {
            transportArray[i] = new Transport();
            transportArray[i].setModel(models[random.nextInt(5)]);
            transportArray[i].setColor(colors[random.nextInt(5)]);
            transportArray[i].setDimensions(random.nextInt(5), random.nextInt(5), random.nextInt(5));
            transportArray[i].setPower(random.nextInt(3000));
            transportArray[i] = TransportUpdater.updateType(random.nextInt(5),transportArray[i]);
        }
        for (int i = 0; i < N; i++)
        {
            transportArray[i].getDescription();
            System.out.print("\n");
        }
    }
}
