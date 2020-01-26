/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;



import frc.robot.subsystems.*;

public class RunForwardRelative extends CommandBase {
  
  private double mWhereAreWe = 0;
  private DriveSubsystem mDriveSubsystem;
  
  public RunForwardRelative( DriveSubsystem driveSubsystem) {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
 
  mDriveSubsystem = driveSubsystem;
  }

  // Called just before this Command runs the first time
  @Override
  public void initialize() {
    System.out.printf("Init\n");
    mWhereAreWe = mDriveSubsystem.getLeftEncoderPosition();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  public void execute() {
    mDriveSubsystem.tankDrive(0.5,0.5);
    
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  public boolean isFinished() {
    System.out.printf("Run Forward %f %f\n",
    mDriveSubsystem.getLeftEncoderPosition(),
    mWhereAreWe + 10000);
    
    if(mDriveSubsystem.getLeftEncoderPosition()> mWhereAreWe + 10000)
    {
        return true;
       
    }
    else
    {
    return false;
    }
  }

  // Called once after isFinished returns true
  @Override
  public void end(boolean interrupted) {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run

 
}
