package edu.asu.cse546.helper;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.*;

import java.util.List;

public class EC2Helper implements EC2HelperInterface {

    private final AmazonEC2 ec2 = AmazonEC2ClientBuilder.standard().withRegion(Regions.US_WEST_1).build();

    public void createInstance() {
        System.out.println("Create an instance");

        String imageId = "ami-07303b67";
        int minInstanceCount = 1;
        int maxInstanceCount = 1;

        RunInstancesRequest runRequest = new RunInstancesRequest()
                .withImageId(imageId)
                .withInstanceType(InstanceType.T2Micro)
                .withMinCount(minInstanceCount)
                .withMaxCount(maxInstanceCount)
                .withKeyName("546")
                .withSecurityGroups("546-cc-sg");


        RunInstancesResult runResponce = ec2.runInstances(runRequest);

        List<Instance> resultInstances = runResponce.getReservation().getInstances();
        for (Instance ins: resultInstances) {
            System.out.println("New instance has been created: " + ins.getInstanceId());
        }
    }

    public void startInstance(String instanceId) {
        StartInstancesRequest request = new StartInstancesRequest().withInstanceIds(instanceId);
        ec2.startInstances(request);
    }

    public void stopInstance(String instanceId) {
        StopInstancesRequest request = new StopInstancesRequest().withInstanceIds(instanceId);
        ec2.stopInstances(request);
        System.out.println("The instance has been stoped.");
    }

    public void rebootInstance(String instanceId) {
        RebootInstancesRequest request = new RebootInstancesRequest().withInstanceIds(instanceId);
        ec2.rebootInstances(request);
    }

    public void describeInstance() {
        boolean done = false;

        DescribeInstancesRequest request = new DescribeInstancesRequest();
        while (!done) {
            DescribeInstancesResult response = ec2.describeInstances(request);

            for (Reservation reservation : response.getReservations()) {
                System.out.println("one reservation");
                for (Instance instance : reservation.getInstances()) {
                    System.out.printf(
                            "Found instance with id %s, " +
                                    "AMI %s, " +
                                    "type %s, " +
                                    "state %s " +
                                    "and monitoring state %s\n",
                            instance.getInstanceId(),
                            instance.getImageId(),
                            instance.getInstanceType(),
                            instance.getState().getName(),
                            instance.getMonitoring().getState());
                }
            }

            request.setNextToken(response.getNextToken());

            if (response.getNextToken() == null) {
                done = true;
            }
        }
    }

    public static void main(String[] args) {
        EC2Helper example = new EC2Helper();

//        example.stopInstance("i-075542805fd9d9ee4");
//        example.createInstance();
//        EC2Example.createInstance();
        example.describeInstance();
    }
}





// ref: https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/examples-ec2-instances.html