package edu.iu.c212.models;

public class Staff {
    private String fullName;
    private int age;
    private String role;
    private String availability;

    public Staff(String fullName, int age, String role, String availability) {
        setName(fullName);
        setAge(age);
        setRole(role);
        setAvailability(availability);
    }

    public String getName() {
        return fullName;
    }

    public void setName(String fullName) {
        if (fullName == null || fullName.trim().isEmpty()) throw new IllegalArgumentException("Full name cannot be empty.");
        this.fullName = fullName.trim();
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age < 16 || age > 100) throw new IllegalArgumentException("Invalid age.");
        this.age = age;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        if (role == null || role.trim().isEmpty()) throw new IllegalArgumentException("Role cannot be empty.");
        this.role = role.trim();
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability.trim();
    }

    @Override
    public String toString() {
        return String.format("Staff{name='%s', age=%d, role='%s', availability='%s'}", fullName, age, role, availability);
    }
}
