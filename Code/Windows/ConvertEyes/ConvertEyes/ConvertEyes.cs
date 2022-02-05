using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.IO;

namespace ConvertEyes
{
  public partial class ConvertEyes : Form
  {
    const int WIDTH = 32;
    const int HEIGHT = 32;

    Bitmap mMainImage;
    string mName = "";

    public ConvertEyes()
    {
        InitializeComponent();
    }

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

      //      results = "{ " + top.ToString() + ", " + bottom.ToString();
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

    private void bSave_Click(object sender, EventArgs e)
    {
      StreamWriter mFile = new StreamWriter(mName+".cpp");
      mFile.Write(tbCode.Text);
      mFile.Close();
    }
  }
}
