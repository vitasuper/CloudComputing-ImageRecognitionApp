package edu.asu.cse546.helper;

public interface EC2HelperInterface {

    // Create an instance
    void createInstance();

    // Start an instance with instance ID
    void startInstance(String instanceId);

    // Stop an instance with instance ID
    void stopInstance(String instanceId);

    // Reboot an instance with instance ID
    void rebootInstance(String instanceId);

    // Get the
    void describeInstance();
}

