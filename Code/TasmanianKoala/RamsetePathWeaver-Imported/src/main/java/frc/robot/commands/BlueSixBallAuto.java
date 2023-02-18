package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.ramsete.*;
import frc.robot.trajectory.*;
import frc.robot.subsystems.DriveSubsystem;

public class BlueSixBallAuto extends SequentialCommandGroup
{
  public BlueSixBallAuto() {
    DriveTrajectory drivePath1 = new DriveTrajectory(BlueSixBallAutoCollectThree.getTrajectory());  
    DriveTrajectory drivePath2 = new DriveTrajectory(BlueSixBallAutoBackToLine.getTrajectory());      
    addCommands(new TurnToAngle(180,DriveSubsystem.getInstance()));
//    addCommands(drivePath1.getCommand());
    addCommands(new WaitTime().withTimeout(1));
//    addCommands(drivePath2.getCommand());
    addCommands(new TurnToAngle(0,DriveSubsystem.getInstance()));

  } 
  
  @Override
  public void initialize()
  {
    super.initialize();
    System.out.printf("S:%s\n",DriveSubsystem.getInstance().getPose().toString());
  }

  @Override
  public void end(boolean interrupted) {
    super.end(interrupted);
    System.out.printf("E:%s\n",DriveSubsystem.getInstance().getPose().toString());
  }

}