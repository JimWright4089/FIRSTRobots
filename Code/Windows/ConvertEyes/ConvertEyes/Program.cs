//----------------------------------------------------------------------------
//
//  $Workfile: Program.cs$
//
//  $Revision: X$
//
//  Project:    Robot Eyes
//
//                            Copyright (c) 2022
//                               James A Wright
//                            All Rights Reserved
//
//  Modification History:
//  $Log:
//  $
//
//  Notes:
//     This is a tool for taking images that were made in Photoshop and convert
//     them to code.
//
//----------------------------------------------------------------------------
using System;
using System.Windows.Forms;

namespace ConvertEyes
{
  //----------------------------------------------------------------------------
  //  Class Declarations
  //----------------------------------------------------------------------------
  //
  // Class Name: Program
  // 
  // Purpose:
  //      Main runner
  //
  //----------------------------------------------------------------------------
  internal static class Program
  {
    //--------------------------------------------------------------------
    // Purpose:
    //     The main entry point for the application.
    //
    // Notes:
    //     None.
    //--------------------------------------------------------------------
    [STAThread]
    static void Main()
    {
        Application.EnableVisualStyles();
        Application.SetCompatibleTextRenderingDefault(false);
        Application.Run(new ConvertEyes());
    }
  }
}
