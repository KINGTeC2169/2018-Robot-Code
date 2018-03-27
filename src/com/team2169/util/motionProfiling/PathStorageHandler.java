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
import com.team2169.util.Converter;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Trajectory.Segment;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.modifiers.TankModifier;

public class PathStorageHandler {
	

	public static MotionProfilePath handlePath(Waypoint[] waypoints, Trajectory.Config config) {

		String pathHash = getPathHash(waypoints) + getConfigHash(config);
		SmartDashboard.putString("Path Hash", pathHash);
		DriverStation.reportError("Path Hash: " + pathHash, false);
		File pathFile = new File("/home/lvuser/paths/" + pathHash + ".csv");
		if (pathFile.exists()) {
			System.out.println("File Found!");
			MotionProfilePath profile = readProfile(pathHash);
			return profile;
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
				leftCSVWriter.writeNext(new String[] { "" + p.position, "" + p.velocity, "" + p.timeStep, "" + p.zeroPos,"" + p.isLastPoint,});
			}
			for (MotionProfilePoint p : profile.rightPath) {
				rightCSVWriter.writeNext(new String[] { "" + p.position, "" + p.velocity, "" + p.timeStep, "" + p.zeroPos,"" + p.isLastPoint,});
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
		int i = 0;
		for (Segment s : right.segments) {
			if(i == 0) {
				profile.rightPath.add(new MotionProfilePoint(Converter.talonPositionConverter(s.position), Converter.talonVelocityConverter(s.velocity, PathfinderData.wheel_diameter), s.dt, true, false));	
			}
			else if(i == right.segments.length) {
				profile.rightPath.add(new MotionProfilePoint(Converter.talonPositionConverter(s.position), Converter.talonVelocityConverter(s.velocity, PathfinderData.wheel_diameter), s.dt, false, true));	
			}
			else {
				profile.rightPath.add(new MotionProfilePoint(Converter.talonPositionConverter(s.position), Converter.talonVelocityConverter(s.velocity, PathfinderData.wheel_diameter), s.dt, false, false));	
			}
			
			i++;
		}

		i = 0;
		// Populate Left Path
		for (Segment s : left.segments) {

			if(i == 0) {
				profile.leftPath.add(new MotionProfilePoint(Converter.talonPositionConverter(s.position), Converter.talonVelocityConverter(s.velocity, PathfinderData.wheel_diameter), s.dt, true, false));	
			}
			else if(i == right.segments.length) {
				profile.leftPath.add(new MotionProfilePoint(Converter.talonPositionConverter(s.position), Converter.talonVelocityConverter(s.velocity, PathfinderData.wheel_diameter), s.dt, false, true));	
			}
			else {
				profile.leftPath.add(new MotionProfilePoint(Converter.talonPositionConverter(s.position), Converter.talonVelocityConverter(s.velocity, PathfinderData.wheel_diameter), s.dt, false, false));	
			}
			
			i++;
		}

		return profile;

	}

	public static MotionProfilePath readProfile(String fileName) {

		MotionProfilePath profile = new MotionProfilePath();
		CSVParser parserLeft = new CSVParserBuilder().withSeparator(',').build();
		CSVParser parserRight = new CSVParserBuilder().withSeparator(',').build();

		File leftFile = new File("/home/lvuser/paths/" + fileName + "Left" + ".csv");
		File rightFile = new File("/home/lvuser/paths/" + fileName + "Right" + ".csv");

		try (BufferedReader brLeft = Files.newBufferedReader(Paths.get(leftFile.getPath()), StandardCharsets.UTF_8);
				CSVReader reader = new CSVReaderBuilder(brLeft).withCSVParser(parserLeft).build()) {

			// Add All Lines to Profile
			List<String[]> records = reader.readAll();
			for (String[] record : records) {
				profile.leftPath.add(new MotionProfilePoint(Double.parseDouble(record[0]),
						Double.parseDouble(record[1]), Double.parseDouble(record[2]), Boolean.parseBoolean(record[3]), Boolean.parseBoolean(record[4])));
			}

		} catch (IOException e) {
			e.printStackTrace();
			profile.leftPath.add(new MotionProfilePoint(0, 0, 0, true, true));

		}

		try (BufferedReader brRight = Files.newBufferedReader(Paths.get(rightFile.getPath()), StandardCharsets.UTF_8);
				CSVReader reader = new CSVReaderBuilder(brRight).withCSVParser(parserRight).build()) {

			// Add All Lines to Profile
			List<String[]> records = reader.readAll();
			for (String[] record : records) {
				profile.rightPath.add(new MotionProfilePoint(Double.parseDouble(record[0]),
						Double.parseDouble(record[1]), Double.parseDouble(record[2]), Boolean.parseBoolean(record[3]), Boolean.parseBoolean(record[4])));
			}

		} catch (IOException e) {

			System.out.println(e.getMessage());
			profile.rightPath.add(new MotionProfilePoint(0, 0, 0, true, true));

		}

		return profile;

	}
	
	static String getPathHash(Waypoint[] waypoints) {
		
		double value = 0;
		String returnable = "";
		
		for(Waypoint w:waypoints) {
			value += w.angle;
			value += w.x;
			value += w.y;
		}
		
		returnable += value;
		
		return returnable.hashCode() + "";
	}
	
	static String getConfigHash(Trajectory.Config config) {
		
		String returnable = "";
		double value = 0;
		
			value += config.dt;
			value += config.fit.name().length();
			value += config.max_acceleration;
			value += config.max_jerk;
			value += config.max_velocity;
			value += config.sample_count;
		
			SmartDashboard.putNumber("Config Sum", value);
			
			value = Math.abs(value);
			
			returnable = "" + value;
			
		return returnable.hashCode() + "";
	}
	
	
}
