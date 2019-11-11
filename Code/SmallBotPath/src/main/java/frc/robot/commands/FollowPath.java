/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.Notifier;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.PathfinderFRC;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.followers.EncoderFollower;
import com.ctre.phoenix.sensors.PigeonIMU;
import frc.robot.RobotMap;
import frc.robot.Constants;


public class FollowPath extends CommandGroup {
  public FollowPath() {
    InitPath(Constants.k_path_name);
  }

  public FollowPath(String name) {
    InitPath(name);
  }

  public void InitPath(String name) {
    try
    {
      System.out.println(name + ".left");
      System.out.println(name + ".right");
      Trajectory left_trajectory = PathfinderFRC.getTrajectory(name + ".left");
      Trajectory right_trajectory = PathfinderFRC.getTrajectory(name + ".right");
      RobotMap.sLeftFollower = new EncoderFollower(left_trajectory);
      RobotMap.sRightFollower = new EncoderFollower(right_trajectory);
   
      RobotMap.sLeftFollower.configureEncoder(-1*RobotMap.sMotorLeftA.getSelectedSensorPosition(0), 
          Constants.k_ticks_per_rev, Constants.k_wheel_diameter);
      // You must tune the PID values on the following line!
      RobotMap.sLeftFollower.configurePIDVA(1.0, 0.0, 0.0, 1 / Constants.k_max_velocity, 0);
  
      RobotMap.sRightFollower.configureEncoder(-1*RobotMap.sMotorRightA.getSelectedSensorPosition(0), 
      Constants.k_ticks_per_rev, Constants.k_wheel_diameter);
      // You must tune the PID values on the following line!
      RobotMap.sRightFollower.configurePIDVA(1.0, 0.0, 0.0, 1 / Constants.k_max_velocity, 0);
      
      RobotMap.sFollowerNotifier = new Notifier(this::followPath);
      RobotMap.sFollowerNotifier.startPeriodic(left_trajectory.get(0).dt);
    }
    catch(Exception e)
    {
      System.console().printf(e.toString());
    }
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
	  return RobotMap.sLeftFollower.isFinished() || RobotMap.sRightFollower.isFinished();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }


  private void followPath() {
    if (isFinished()) {
      RobotMap.sFollowerNotifier.stop();
      RobotMap.sMotorLeftA.set(0);
      RobotMap.sMotorRightA.set(0);
    } else {

      PigeonIMU.FusionStatus fusionStatus = new PigeonIMU.FusionStatus();
      double[] xyz_dps = new double[3];
      RobotMap.sPigeonIMU.getRawGyro(xyz_dps);
      RobotMap.sPigeonIMU.getFusedHeading(fusionStatus);
  
      double left_speed = RobotMap.sLeftFollower.calculate(RobotMap.sMotorLeftA.getSelectedSensorPosition(0)*-1);
      double right_speed = RobotMap.sRightFollower.calculate(RobotMap.sMotorRightA.getSelectedSensorPosition(0)*-1);
      double heading = fusionStatus.heading;
      double desired_heading = Pathfinder.r2d(RobotMap.sLeftFollower.getHeading());
      double heading_difference = Pathfinder.boundHalfDegrees(desired_heading - heading);
      double turn =  0.8 * (-1.0/80.0) * heading_difference;
/*      
      System.out.print(left_speed);
      System.out.print(" ");
      System.out.print(right_speed);
      System.out.print(" ");
      System.out.print(heading);
      System.out.print(" ");
      System.out.print(desired_heading);
      System.out.print(" ");
      System.out.println(turn);
*/
      RobotMap.sMotorLeftA.set(-1*(left_speed + turn));
      RobotMap.sMotorRightA.set(-1*(right_speed - turn));
    }
  }
}
