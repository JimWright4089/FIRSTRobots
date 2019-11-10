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
      System.out.println("Start of try");
      System.out.println(name + ".Left");
      System.out.println(name + ".Right");
      System.out.println(PathfinderFRC.DEFAULT_JERK);
      Trajectory left_trajectory = PathfinderFRC.getTrajectory(Constants.k_path_name + ".left");
      Trajectory right_trajectory = PathfinderFRC.getTrajectory(Constants.k_path_name + ".right");
      RobotMap.m_left_follower = new EncoderFollower(left_trajectory);
      RobotMap.m_right_follower = new EncoderFollower(right_trajectory);
      System.out.println("After Init");
   
      RobotMap.m_left_follower.configureEncoder(RobotMap.motorLeftA.getSelectedSensorPosition(0), 
          Constants.k_ticks_per_rev, Constants.k_wheel_diameter);
      // You must tune the PID values on the following line!
      RobotMap.m_left_follower.configurePIDVA(1.0, 0.0, 0.0, 1 / Constants.k_max_velocity, 0);
  
      RobotMap.m_right_follower.configureEncoder(RobotMap.motorRightA.getSelectedSensorPosition(0), 
      Constants.k_ticks_per_rev, Constants.k_wheel_diameter);
      // You must tune the PID values on the following line!
      RobotMap.m_right_follower.configurePIDVA(1.0, 0.0, 0.0, 1 / Constants.k_max_velocity, 0);
      
      RobotMap.m_follower_notifier = new Notifier(this::followPath);
      RobotMap.m_follower_notifier.startPeriodic(left_trajectory.get(0).dt);
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
	  return RobotMap.m_left_follower.isFinished() || RobotMap.m_right_follower.isFinished();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }


  private void followPath() {
    if (isFinished()) {
      RobotMap.m_follower_notifier.stop();
    } else {

      PigeonIMU.FusionStatus fusionStatus = new PigeonIMU.FusionStatus();
      double[] xyz_dps = new double[3];
      RobotMap.pigeonIMU.getRawGyro(xyz_dps);
      RobotMap.pigeonIMU.getFusedHeading(fusionStatus);
  
      double left_speed = RobotMap.m_left_follower.calculate(RobotMap.motorLeftA.getSelectedSensorPosition(0));
      double right_speed = RobotMap.m_right_follower.calculate(RobotMap.motorRightA.getSelectedSensorPosition(0));
      double heading = fusionStatus.heading;
      double desired_heading = Pathfinder.r2d(RobotMap.m_left_follower.getHeading());
      double heading_difference = Pathfinder.boundHalfDegrees(desired_heading - heading);
      double turn =  0.8 * (-1.0/80.0) * heading_difference;
      
      System.out.print(left_speed);
      System.out.print(" ");
      System.out.print(right_speed);
      System.out.print(" ");
      System.out.print(heading);
      System.out.print(" ");
      System.out.print(desired_heading);
      System.out.print(" ");
      System.out.println(turn);

      RobotMap.motorLeftA.set(left_speed + turn);
      RobotMap.motorRightA.set(right_speed - turn);
    }
  }
}
