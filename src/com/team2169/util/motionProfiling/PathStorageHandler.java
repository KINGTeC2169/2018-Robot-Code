package com.team2169.util.motionProfiling;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.team2169.robot.subsystems.DriveTrain.PathfinderData;
import com.team2169.util.motionProfiling.MotionProfilePath.MotionProfilePoint;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Trajectory.Segment;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.modifiers.TankModifier;

public class PathStorageHandler {

	public static MotionProfilePath handlePath(Waypoint[] waypoints, Trajectory.Config config) {

		int pathHash = waypoints.hashCode() + config.hashCode();
		File pathFile = new File("/home/lvuser/paths/" + pathHash + ".csv");
		if (pathFile.exists()) {
			return readProfile(pathFile);
		} else {
			MotionProfilePath profile = pathToProfile(Pathfinder.generate(waypoints, config));
			storeProfile(pathFile, profile);
			return profile;
		}

	}

	public static void storeProfile(File file, MotionProfilePath profile) {

		try {

			File leftFile = new File("/home/lvuser/paths/" + file.getName() + "Left" + ".csv");
			File rightFile = new File("/home/lvuser/paths/" + file.getName() + "Right" + ".csv");

			PrintWriter pwLeft = new PrintWriter(leftFile);
			PrintWriter pwRight = new PrintWriter(rightFile);

			StringBuilder sbLeft = new StringBuilder();
			StringBuilder sbRight = new StringBuilder();

			for (MotionProfilePoint p : profile.leftPath) {

				sbLeft.append(p.position);
				sbLeft.append(',');
				sbLeft.append(p.velocity);
				sbLeft.append(',');
				sbLeft.append(p.dt);
				sbLeft.append('\n');

			}

			for (MotionProfilePoint p : profile.rightPath) {

				sbRight.append(p.position);
				sbRight.append(',');
				sbRight.append(p.velocity);
				sbRight.append(',');
				sbRight.append(p.dt);
				sbRight.append('\n');

			}

			pwLeft.write(sbLeft.toString());
			pwRight.write(sbRight.toString());

			pwLeft.close();
			pwRight.close();

			System.out.println("Path Written!");

		} catch (FileNotFoundException e) {
			System.out.println("Error Storing Profile! - File Creation Failure!");
		}

	}

	public static MotionProfilePath pathToProfile(Trajectory traj) {

		TankModifier modifier = new TankModifier(traj).modify(PathfinderData.wheel_base_width);
		Trajectory left = modifier.getLeftTrajectory();
		Trajectory right = modifier.getRightTrajectory();

		MotionProfilePath profile = new MotionProfilePath();

		// Populate Right Path
		for (Segment s : right.segments) {

			profile.rightPath.add(profile.new MotionProfilePoint(s.position, s.velocity, s.dt));

		}

		// Populate Left Path
		for (Segment s : left.segments) {

			profile.leftPath.add(profile.new MotionProfilePoint(s.position, s.velocity, s.dt));

		}

		return profile;

	}

	public static MotionProfilePath readProfile(File file) {

		MotionProfilePath profile = new MotionProfilePath();
		CSVParser parserLeft = new CSVParserBuilder().withSeparator(',').build();
		CSVParser parserRight = new CSVParserBuilder().withSeparator(',').build();

		File leftFile = new File("/home/lvuser/paths/" + file.getName() + "Left" + ".csv");
		File rightFile = new File("/home/lvuser/paths/" + file.getName() + "Right" + ".csv");

		
		try (BufferedReader brLeft = Files.newBufferedReader(Paths.get(leftFile.getPath()), StandardCharsets.UTF_8);
				CSVReader reader = new CSVReaderBuilder(brLeft).withCSVParser(parserLeft).build()) {
			
			//Add All Lines to Profile
			List<String[]> records = reader.readAll();
			for (String[] record : records) {
			    profile.leftPath.add(profile.new MotionProfilePoint(Double.parseDouble(record[0]), 
			    		Double.parseDouble(record[1]), Double.parseDouble(record[2])));
			}

		} catch (IOException e) {

			profile.leftPath.add(profile.new MotionProfilePoint(0, 0, 0));

		}

		
		try (BufferedReader brRight = Files.newBufferedReader(Paths.get(rightFile.getPath()), StandardCharsets.UTF_8);
				CSVReader reader = new CSVReaderBuilder(brRight).withCSVParser(parserRight).build()) {
			
			//Add All Lines to Profile
			List<String[]> records = reader.readAll();
			for (String[] record : records) {
			    profile.rightPath.add(profile.new MotionProfilePoint(Double.parseDouble(record[0]), 
			    		Double.parseDouble(record[1]), Double.parseDouble(record[2])));
			}
			
			

		} catch (IOException e) {

			profile.rightPath.add(profile.new MotionProfilePoint(0, 0, 0));

		}

		return profile;

	}

}
