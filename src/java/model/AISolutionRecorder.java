package model;

public interface AISolutionRecorder {
    void recordStep(String action, int stepNumber, long timestamp);
}
