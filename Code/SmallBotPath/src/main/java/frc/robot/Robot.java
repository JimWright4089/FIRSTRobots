package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.*;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot
{

  Command autonomousCommand;
  SendableChooser<Command> chooser = new SendableChooser<>();

  public static OI oi;
  public static Drive drive;
  public static Power power;
	public static DriverStation mDriversStation;
	NetworkTableInstance netTableInst;
  NetworkTable toPiTable;
  NetworkTableEntry ledStatusEntry;
  NetworkTableEntry casErrorEntry;
  NetworkTableEntry casWarnEntry;
  NetworkTableEntry casStatusEntry;
  NetworkTableEntry casInfoEntry;


  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit()
  {
    RobotMap.init();
    drive = new Drive();
    power = new Power();

    // OI must be constructed after subsystems. If the OI creates Commands
    // (which it very likely will), subsystems are not guaranteed to be
    // constructed yet. Thus, their requires() statements may grab null
    // pointers. Bad news. Don't move it.
    oi = new OI();

    SmartDashboard.putData("Auto mode", chooser);

	  mDriversStation = DriverStation.getInstance();
		netTableInst = NetworkTableInstance.getDefault();
	  toPiTable = netTableInst.getTable("toPi");
	  ledStatusEntry = toPiTable.getEntry("ledStatus");
	  casErrorEntry  = toPiTable.getEntry("casError");
	  casWarnEntry   = toPiTable.getEntry("casWarn");
	  casStatusEntry = toPiTable.getEntry("casStatus");
	  casInfoEntry   = toPiTable.getEntry("casInfo");
  }

  /**
   * This function is called when the disabled button is hit. You can use it to
   * reset subsystems before shutting down.
   */
  @Override
  public void disabledInit()
  {
  }

  @Override
  public void disabledPeriodic()
  {
    Scheduler.getInstance().run();
    setStatus();
  }

  @Override
  public void autonomousInit()
  {
    autonomousCommand = chooser.getSelected();
    // schedule the autonomous command (example)
    if (autonomousCommand != null)
      autonomousCommand.start();

    RobotMap.SetUpTalonsForAuto();
    drive.ClearCurrentAngle();
    RobotMap.pigeonIMU.setFusedHeading(0.0, 30);

    drive.SetAuto();

//    autonomousCommand = new MoveForward();
    autonomousCommand = new FollowPath();
    Scheduler.getInstance().add(autonomousCommand);
    setStatus();
  }

  /**
   * This function is called periodically during autonomous
   */
  @Override
  public void autonomousPeriodic()
  {
    Scheduler.getInstance().run();
  }

  @Override
  public void teleopInit()
  {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (autonomousCommand != null)
      autonomousCommand.cancel();

    // Init teleop
    System.out.println("tele init");
    RobotMap.SetUpTalonsForTele();
    Robot.drive.SetTele();

    RobotMap.SetUpTalonsForTele();
    setStatus();
  }

  /**
   * This function is called periodically during operator control
   */
  @Override
  public void teleopPeriodic()
  {
    Scheduler.getInstance().run();
    Robot.drive.DriveRobot(oi.driveStick);
  }

  private void setStatus()
	{
	   if(mDriversStation.isEnabled())
	    {
	      ledStatusEntry.setDouble((double)'G');  
	    }
	    else
	    {
	      if(Alliance.Red == mDriversStation.getAlliance())
	      {
	        ledStatusEntry.setDouble((double)'R');  
	      }
	      else
	      {
	        ledStatusEntry.setDouble((double)'B');  
	      }
	    }
	}
}
