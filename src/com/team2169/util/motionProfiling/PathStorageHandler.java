package com.team2169.util.motionProfiling;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import com.team2169.robot.subsystems.DriveTrain.PathfinderData;
import com.team2169.util.motionProfiling.MotionProfilePath.MotionProfilePoint;

import edu.wpi.first.wpilibj.DriverStation;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Trajectory.Segment;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.modifiers.TankModifier;

public class PathStorageHandler {
	

	public static MotionProfilePath handlePath(Waypoint[] waypoints, Trajectory.Config config) {

		String pathHash = getPathHash(waypoints) + getConfigHash(config);
		DriverStation.reportError("Path Hash: " + pathHash, false);
		File pathFile = new File("/home/lvuser/paths/" + pathHash + ".csv");
		if (pathFile.exists()) {
			System.out.println("File Found!");
			return readProfile(pathFile);
		} else {
			MotionProfilePath profile = pathToProfile(Pathfinder.generate(waypoints, config));
			storeProfile(pathHash, profile);
			System.out.println("File Created!");
			return profile;
		}

	}

	public static void storeProfile(String hash, MotionProfilePath profile) {

		//System.out.println("MAKING PATH");

		File m = new File("/home/lvuser/paths/" + hash + ".csv");

		m.getParentFile().mkdirs(); 
		try {
			m.createNewFile();
		} catch (IOException e1) {

		}
		
		File l = new File("/home/lvuser/paths/" + hash + "Left.csv");

		l.getParentFile().mkdirs(); 
		try {
			l.createNewFile();
		} catch (IOException e1) {

		}
		
		File r = new File("/home/lvuser/paths/" + hash + "Right.csv");

		r.getParentFile().mkdirs(); 
		try {
			r.createNewFile();
		} catch (IOException e1) {

		}
		
		try (
				
				Writer leftWriter = Files.newBufferedWriter(Paths.get("/home/lvuser/paths/" + hash + "Left.csv"));

				CSVWriter leftCSVWriter = new CSVWriter(leftWriter, CSVWriter.DEFAULT_SEPARATOR,
						CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);

				Writer rightWriter = Files.newBufferedWriter(Paths.get("/home/lvuser/paths/" + hash + "Right.csv"));

				CSVWriter rightCSVWriter = new CSVWriter(rightWriter, CSVWriter.DEFAULT_SEPARATOR,
						CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);

		) {
			for (MotionProfilePoint p : profile.leftPath) {
				leftCSVWriter.writeNext(new String[] { "" + p.position, "" + p.velocity, "" + p.dt });
			}
			for (MotionProfilePoint p : profile.rightPath) {
				rightCSVWriter.writeNext(new String[] { "" + p.position, "" + p.velocity, "" + p.dt });
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		//System.out.println("Path Written!");
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

			// Add All Lines to Profile
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

			// Add All Lines to Profile
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

	static String getPathHash(Waypoint[] waypoints) {
		
		String returnable = "";
		
		for(Waypoint w:waypoints) {
			returnable += w.angle;
			returnable += w.x;
			returnable += w.y;
		}
		
		return returnable.hashCode() + "";
	}
	
	static String getConfigHash(Trajectory.Config config) {
		
		String returnable = "";
		
			returnable += config.dt;
			returnable += config.fit;
			returnable += config.max_acceleration;
			returnable += config.max_jerk;
			returnable += config.max_velocity;
			returnable += config.sample_count;
		
		return returnable.hashCode() + "";
	}
	
	
}
