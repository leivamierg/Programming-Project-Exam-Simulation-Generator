package it.unibz.model.implementations;

public record Score(int correct, int wrong, int blank, int selected, int total, double percentage) {}
