public class SearchAlgorithm {

	/**
	 * Backtracking Method
	 * 
	 * @param problem
	 * @param deadline
	 * @return
	 */
	public Schedule backtracking(SchedulingProblem problem, long deadline) {

		// get an empty solution to start from
		Schedule solution = problem.getEmptySchedule();

		for (int i = 0; i < problem.courses.size(); i++) {
			
			//get course
			Course c = problem.courses.get(i);
			boolean scheduled = false;

			for (int j = 0; j < c.timeSlotValues.length; j++) {
				if (scheduled)
					break;
				
				//if greater than 0, get preferred building location
				if (c.timeSlotValues[j] > 0) {
					double x = c.preferredLocation.xCoord;
					double y = c.preferredLocation.yCoord;

					//schedule based on preference
					for (int k = 0; k < problem.rooms.size(); k++) {
						if (x == problem.rooms.get(k).b.xCoord && y == problem.rooms.get(k).b.yCoord) {
							solution.schedule[k][j] = i;
							scheduled = true;
							break;
						}
					}
					
					//if it couldn't schedule with preference, then schedule 'randomly'
					if (!scheduled) {
						for (int k = 0; k < problem.rooms.size(); k++) {
							if (solution.schedule[k][j] < 0) {
								solution.schedule[k][j] = i;
								scheduled = true;
								break;
							}
						} 
					} 
				} 
			}
		} 
		return solution;
	}

	/**
	 * Simulated Annealing Method
	 * @param problem
	 * @param deadline
	 * @return
	 */
	public Schedule simulatedAnnealing(SchedulingProblem problem, long deadline) {

		// get a starting solution
		Schedule currSolution = problem.getEmptySchedule();
		currSolution = startingSchedule(problem);
		
		Schedule secondSolution = problem.getEmptySchedule();
		boolean scheduled = false;
		// initialize temp and cooling rate
		double temp = 100;
		double coolingRate = 0.01;

		// while temp hasn't cooled
		while (temp > 1) {
			
			// empty second solution
			secondSolution = problem.getEmptySchedule();

			// go through courses
			for (int i = 0; i < problem.courses.size() - 1; i++) {
				
				//take course and set schedule to false
				Course c1 = problem.courses.get(i);
				scheduled = false;
				
				//for loop to schedule course
				for (int j = 0; j < c1.timeSlotValues.length; j++) {
					if (scheduled)
						break;
					if (c1.timeSlotValues[j] > 0) {
						
						//go through each room
						for (int k = 0; k < problem.rooms.size(); k++) {
							int ran1 = (int) (Math.random() * problem.rooms.size());
							int ran2 = (int) (Math.random() * c1.timeSlotValues.length);
							
							//if schedule is free, then schedule it
							if (secondSolution.schedule[ran1][ran2] < 0) {
								secondSolution.schedule[ran1][ran2] = i;
								scheduled = true;
								break;
							}
						}
					}
				}
			} 
			
			//get evaluations
			double currEnergy = problem.evaluateSchedule(currSolution);
			double secondEnergy = problem.evaluateSchedule(secondSolution);

			double acceptanceProbability = 1.0;

			//compare 
			if (secondEnergy > currEnergy)
				acceptanceProbability = Math.exp((currEnergy - secondEnergy) / temp);

			
			if (acceptanceProbability > Math.random())
				currSolution = secondSolution;

			//increase temp
			temp *= coolingRate;
		}

		return currSolution;

	}

	// This is a very naive baseline scheduling strategy
	// It should be easily beaten by any reasonable strategy
	public Schedule naiveBaseline(SchedulingProblem problem, long deadline) {

		// get an empty solution to start from
		Schedule solution = problem.getEmptySchedule();

		for (int i = 0; i < problem.courses.size(); i++) {
			Course c = problem.courses.get(i);
			boolean scheduled = false;
			for (int j = 0; j < c.timeSlotValues.length; j++) {
				if (scheduled)
					break;
				if (c.timeSlotValues[j] > 0) {
					for (int k = 0; k < problem.rooms.size(); k++) {
						if (solution.schedule[k][j] < 0) {
							solution.schedule[k][j] = i;
							scheduled = true;
							break;
						}
					}
				}
			}
		}

		return solution;
	}

	/**
	 * Method to start a schedule
	 * @param problem
	 * @return
	 */
	public static Schedule startingSchedule(SchedulingProblem problem) {
		Schedule currSolution = problem.getEmptySchedule();

		for (int i = 0; i < problem.courses.size() - 1; i++) {
			// course
			Course c1 = problem.courses.get(i);
			boolean scheduled = false;

			//schedule course
			for (int j = 0; j < c1.timeSlotValues.length; j++) {
				if (scheduled)
					break;
				if (c1.timeSlotValues[j] > 0) {
					for (int k = 0; k < problem.rooms.size(); k++) {
						if (currSolution.schedule[k][j] < 0) {
							currSolution.schedule[k][j] = i;
							scheduled = true;
							break;
						}
					}
				} 
			}

		}
		return currSolution;
	}
}
