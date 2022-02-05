//----------------------------------------------------------------------------
//
//  $Workfile: ConvertEyes.cs$
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
using System.Drawing;
using System.Windows.Forms;
using System.IO;

namespace ConvertEyes
{
  //----------------------------------------------------------------------------
  //  Class Declarations
  //----------------------------------------------------------------------------
  //
  // Class Name: ConvertEyes
  // 
  // Purpose:
  //      Make the code for eyes from images
  //
  //----------------------------------------------------------------------------
  public partial class ConvertEyes : Form
  {
    //----------------------------------------------------------------------------
    //  Class Constants
    //----------------------------------------------------------------------------
    const int WIDTH = 32;
    const int HEIGHT = 32;

    //----------------------------------------------------------------------------
    //  Class Attributes
    //----------------------------------------------------------------------------
    Bitmap mMainImage;
    string mName = "";

    //--------------------------------------------------------------------
    // Purpose:
    //     Constructor
    //
    // Notes:
    //     None.
    //--------------------------------------------------------------------
    public ConvertEyes()
    {
        InitializeComponent();
    }

    //--------------------------------------------------------------------
    // Purpose:
    //     Look at the image and build code out of it
    //
    // Notes:
    //     None.
    //--------------------------------------------------------------------
    public string buildCode()
    {
      int top = -1;
      int bottom = -1;

      string results = "";

      if (WIDTH != mMainImage.Width)
      {
        MessageBox.Show("Width is not 32");
        return results;
      }

      if (HEIGHT != mMainImage.Height)
      {
        MessageBox.Show("Height is not 32");
        return results;
      }

      for(int i=0; i<HEIGHT; i++)
      {
        for(int j=0;j<WIDTH; j++)
        {
          if(Color.FromArgb(255,0,0,0) != mMainImage.GetPixel(j,i))
          {
            if(-1 == top)
            {
              top = i;
            }
            else
            {
              bottom = i;
            }
            break;
          }
        }
      }

      results = "{ 0";

      for (int i = 0; i < HEIGHT; i++)
      {
        long bitMap = 0;
        for (int j = 0; j < WIDTH; j++)
        {
          bitMap = (bitMap << 1);
          if (Color.FromArgb(255, 0, 0, 0) != mMainImage.GetPixel(j, i))
          {
            bitMap++;
          }
        }
        results += ", " + bitMap.ToString("X08");
      }

      results += "}";

      return results;
    }

    //--------------------------------------------------------------------
    // Purpose:
    //     Load the image and convert it
    //
    // Notes:
    //     None.
    //--------------------------------------------------------------------
    private void bLoad_Click(object sender, EventArgs e)
    {
      OpenFileDialog openFileDialog = new OpenFileDialog();

      openFileDialog.Filter = "All Files (*.*)|*.*";
      openFileDialog.FilterIndex = 1;
      openFileDialog.Multiselect = false;

      DialogResult dialogResult = openFileDialog.ShowDialog();

      if (DialogResult.OK == dialogResult)
      {
        mMainImage = new Bitmap(openFileDialog.FileName);
        pbImage.Image = mMainImage;
        tbCode.Text = buildCode();
        mName = openFileDialog.FileName;
      }
    }

    //--------------------------------------------------------------------
    // Purpose:
    //     Save the code to a file
    //
    // Notes:
    //     None.
    //--------------------------------------------------------------------
    private void bSave_Click(object sender, EventArgs e)
    {
      StreamWriter mFile = new StreamWriter(mName+".cpp");
      mFile.Write(tbCode.Text);
      mFile.Close();
    }
  }
}
