abstract class Person{
    protected String name;
    protected int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() { return name; }
    public int getAge() { return age; }

    @Override
    public abstract String toString(); // Abstract method to be implemented by subclasses
}

class Customer extends Person {

    public Customer(String name, int age) {
        super(name, age);
    }

    @Override
    public String toString() {
        return "Hello, my name is " + name + ".";
    }
}

abstract class Employee extends Person {

    protected int monthsWorked;
    protected double salary;

    public Employee(String name, int age, int monthsWorked, double salary) {
        super(name, age);
        this.monthsWorked = monthsWorked;
        this.salary = salary;
    }

    public double calculateThirteenthMonth() {
        return salary / (6.0 / monthsWorked);
    }
}

class Clerk extends Employee {

    public Clerk(String name, int age, int monthsWorked, double salary) {
        super(name, age, monthsWorked, salary);
    }

    @Override
    public String toString() {
        return "Hello, my name is " + name + ". How may I help you?";
    }
}

class Manager extends Employee {

    public Manager(String name, int age, int monthsWorked, double salary) {
        super(name, age, monthsWorked, salary);
    }

    @Override
    public String toString() {
        return "Hello, my name is " + name + ".";
    }
}