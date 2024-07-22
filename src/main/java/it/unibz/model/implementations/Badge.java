package it.unibz.model.implementations;

public class Badge {
    private String name;
    private String description;

    public Badge() {}

    public Badge(String name, String description)
    {
        this.name = name;
        this.description = description;
    }

    // Getters
    public String getName()
    {
        return name;
    }

    public String getDescription()
    {
        return description;
    }

    // Setters
    public void setName(String name)
    {
        this.name = name;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    @Override
    public String toString() {
        return name + ", description:'" + description;
    }

}
