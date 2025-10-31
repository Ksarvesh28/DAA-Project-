
// 1. Greedy Method
// Project Title: Optimal Resource Allocation System
// Domain: Scheduling and Optimization
// Tasks:
// 1. Implement Job Scheduling Algorithm – Schedule jobs with deadlines and profits using a
// greedy approach.
// 2. Design Resource Selection Module – Choose minimal-cost resources using an activity or
// knapsack-based greedy logic.
// 3. Analyze and Compare Efficiency – Compare results with non-greedy (brute force) methods
// to verify optimality.

import java.util.*;

class Job {
    String jobTitle;
    int deadline;
    int profit;

    Job(String jobTitle, int deadline, int profit) {
        this.jobTitle = jobTitle;
        this.deadline = deadline;
        this.profit = profit;
    }
}

public class Index {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Job> jobs = getJobs(sc);
        System.out.println("Choose scheduling method:\n1. Schedule by Profit\n2. Schedule by Deadline\n3. Schedule by Profit/Deadline Ratio");
        int choice = sc.nextInt();
        switch (choice) {
            case 1: {
                scheduleByProfit(jobs);
                break;
            }
            case 2: {
                scheduleByDeadline(jobs);
                break;
            }

            case 3: {
                scheduleByRatio(jobs);
                break;
            }

            default: {
                System.out.println("Invalid choice. Exiting.");
                break;
            }
        }
    }


    static List<Job> getJobs(Scanner sc) {
        List<Job> jobs = new ArrayList<>();
        boolean next = true;

        while (next) {
            System.out.print("Enter Job Title: ");
            String jobTitle = sc.nextLine();

            System.out.print("Enter Job Deadline (in days): ");
            int deadline = Integer.parseInt(sc.nextLine().trim());

            System.out.print("Enter the Profit for this job: ");
            int profit = Integer.parseInt(sc.nextLine().trim());

            jobs.add(new Job(jobTitle, deadline, profit));

            System.out.print("Do you want to add another job? (yes/no): ");
            String response = sc.nextLine();
            if (response.equalsIgnoreCase("no")) {
                next = false;
            }
        }
        return jobs;
    }

    static int maxDeadLine(List<Job> jobs){
        int max = Integer.MIN_VALUE;
        for(Job job: jobs){
            if(job.deadline > max){
                max = job.deadline;
            }
        }
        return max;
    }

    static void scheduleByProfit(List<Job> jobs) {
        
        if (jobs == null || jobs.isEmpty()) {
            System.out.println("No jobs to schedule.");
            return;
        }

    
        jobs.sort((a, b) -> Integer.compare(b.profit, a.profit));

        int maxDeadline = maxDeadLine(jobs);
        
        int slotsCount = Math.min(maxDeadline, jobs.size());
        if (slotsCount <= 0) slotsCount = 1;

        Job[] slots = new Job[slotsCount + 1]; 

        int totalProfit = 0;

        for (Job job : jobs) {
            int d = Math.min(job.deadline, slotsCount);
            for (int slot = d; slot >= 1; slot--) {
                if (slots[slot] == null) {
                    slots[slot] = job;
                    totalProfit += job.profit;
                    break;
                }
            }
        }

        System.out.println("\nScheduled jobs (by profit):");
        System.out.println("--------------------------------------------------");
        System.out.println("Slot\tJob Title\tDeadline\tProfit");
        for (int i = 1; i <= slotsCount; i++) {
            Job j = slots[i];
            if (j != null) {
                System.out.printf("%d\t%s\t%d\t%d\n", i, j.jobTitle, j.deadline, j.profit);
            } else {
                System.out.printf("%d\t[free]\n", i);
            }
        }

        System.out.println("--------------------------------------------------");
        System.out.println("Total Profit: " + totalProfit);
    }

    static void scheduleByDeadline(List<Job> jobs) {
        if (jobs == null || jobs.isEmpty()) {
            System.out.println("No jobs to schedule.");
            return;
        }

        jobs.sort(Comparator.comparingInt(a -> a.deadline));

        int maxDeadline = maxDeadLine(jobs);
        int slotsCount = Math.min(maxDeadline, jobs.size());
        if (slotsCount <= 0) slotsCount = 1;

        Job[] slots = new Job[slotsCount + 1]; 
        int scheduled = 0;
        int totalProfit = 0;

        for (Job job : jobs) {
          
            int limit = Math.min(job.deadline, slotsCount);
            for (int s = 1; s <= limit; s++) {
                if (slots[s] == null) {
                    slots[s] = job;
                    scheduled++;
                    totalProfit += job.profit;
                    break;
                }
            }
        }

        System.out.println("\nScheduled jobs (by earliest deadline):");
        System.out.println("--------------------------------------------------");
        System.out.println("Slot\tJob Title\tDeadline\tProfit");
        for (int i = 1; i <= slotsCount; i++) {
            Job j = slots[i];
            if (j != null) {
                System.out.printf("%d\t%s\t%d\t%d\n", i, j.jobTitle, j.deadline, j.profit);
            } else {
                System.out.printf("%d\t[free]\n", i);
            }
        }
        System.out.println("--------------------------------------------------");
        System.out.println("Jobs scheduled: " + scheduled + ", Total Profit: " + totalProfit);
    }

    static void scheduleByRatio(List<Job> jobs) {
      
        if (jobs == null || jobs.isEmpty()) {
            System.out.println("No jobs to schedule.");
            return;
        }

        jobs.sort((a, b) -> {
            double ra = a.deadline <= 0 ? a.profit : (double) a.profit / a.deadline;
            double rb = b.deadline <= 0 ? b.profit : (double) b.profit / b.deadline;
            return Double.compare(rb, ra);
        });

        int maxDeadline = maxDeadLine(jobs);
        int slotsCount = Math.min(maxDeadline, jobs.size());
        if (slotsCount <= 0) slotsCount = 1;

        Job[] slots = new Job[slotsCount + 1];
        int totalProfit = 0;

        for (Job job : jobs) {
            int d = Math.min(job.deadline, slotsCount);
            for (int slot = d; slot >= 1; slot--) {
                if (slots[slot] == null) {
                    slots[slot] = job;
                    totalProfit += job.profit;
                    break;
                }
            }
        }

        System.out.println("\nScheduled jobs (by profit/deadline ratio):");
        System.out.println("--------------------------------------------------");
        System.out.println("Slot\tJob Title\tDeadline\tProfit");
        for (int i = 1; i <= slotsCount; i++) {
            Job j = slots[i];
            if (j != null) {
                System.out.printf("%d\t%s\t%d\t%d\n", i, j.jobTitle, j.deadline, j.profit);
            } else {
                System.out.printf("%d\t[free]\n", i);
            }
        }
        System.out.println("--------------------------------------------------");
        System.out.println("Total Profit: " + totalProfit);
    }
}
