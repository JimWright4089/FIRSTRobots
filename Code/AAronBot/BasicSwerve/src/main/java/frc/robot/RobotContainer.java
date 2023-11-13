package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.Autos.EXIT_COMMUNITY;
import frc.robot.commands.Autos.DO_NOTHING;
import frc.robot.commands.DefaultCommands.TeleopDrivebaseDefaultCommand;
import frc.robot.subsystems.Swerve.DrivebaseSubsystem;

public class RobotContainer {
  public static final int k_DRIVER_CONTROLLER_PORT = 0;
  public static final int k_OPERATOR_CONTROLLER_PORT = 1;
  /* Controllers */
  private final Joystick driver = new Joystick(0);
  private final CommandXboxController driverController = new CommandXboxController(
      k_DRIVER_CONTROLLER_PORT);

  /* Drive Controls */
  private final int translationAxis = XboxController.Axis.kLeftY.value;
  private final int strafeAxis = XboxController.Axis.kLeftX.value;
  private final int rotationAxis = XboxController.Axis.kRightX.value;

  /* Driver Buttons */
  private final JoystickButton zeroGyro = new JoystickButton(driver, XboxController.Button.kA.value);
  // private final JoystickButton robotCentric = new JoystickButton(driver,
  // XboxController.Button.kLeftBumper.value);

  /* Subsystems */
  private final DrivebaseSubsystem swerve;

  private UsbCamera camera = CameraServer.startAutomaticCapture();
  private SendableChooser<Command> autoChooser = new SendableChooser<>();

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {

    swerve = new DrivebaseSubsystem();

    camera.setResolution(160, 120);
    camera.setFPS(30);

    swerve.setDefaultCommand(
        new TeleopDrivebaseDefaultCommand(
            swerve,
            () -> -driverController.getRawAxis(translationAxis),
            () -> -driverController.getRawAxis(strafeAxis),
            () -> -driverController.getRawAxis(rotationAxis),
            () -> driverController.b().getAsBoolean(),
            () -> driverController.rightBumper().getAsBoolean()));

    autoChooser.addOption("EXIT COMMUNITY", new EXIT_COMMUNITY(swerve));
    autoChooser.addOption("DO NOTHING", new DO_NOTHING(swerve));
    SmartDashboard.putData("Selected Autonomous", autoChooser);

    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing
   * it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */

  private void configureButtonBindings() {
    zeroGyro.onTrue(new InstantCommand(() -> swerve.zeroGyro()));
  }

  public void autonomousInit() {
  }

  public void teleopInit() {
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // System.out.println("Selected Autonomous: " + autoChooser.getSelected());
    return autoChooser.getSelected();
    // return null;
    // return new InstantCommand();
    // return new BluePreloadOnly(swerve, endEffector, rotator, telescope);
  }
}
