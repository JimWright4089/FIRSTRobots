using System;
using System.Collections.Generic;
using System.Text;
using System.Windows.Forms;
using System.Drawing;

namespace DragAndDropTest
{
  public class DrawingObjects
  {
    const int MAX_STANDARD_COLORS = 16;

    public enum STANDARD_COLOR
    {
      BLACK = 0,
      WHITE,
      GRAY,
      RED,
      ORANGE,
      YELLOW,
      GREEN,
      BLUE,
      PURPLE,
      MAGENTA,
      CYAN,
      LT_GRAY,
      LT_BLUE,
      DK_GREEN,
      PINK
    };

    Color[] mStandardColors = new Color[MAX_STANDARD_COLORS];
    protected Color mColor;

    public DrawingObjects()
    {
      mStandardColors[0] = Color.Black;
      mStandardColors[1] = Color.White;
      mStandardColors[2] = Color.DarkSlateGray;
      mStandardColors[3] = Color.Red;
      mStandardColors[4] = Color.Orange;
      mStandardColors[5] = Color.Yellow;
      mStandardColors[6] = Color.Lime;
      mStandardColors[7] = Color.Blue;
      mStandardColors[8] = Color.DarkOrchid;
      mStandardColors[9] = Color.Magenta;
      mStandardColors[10] = Color.Cyan;
      mStandardColors[11] = Color.Black;
      mStandardColors[12] = Color.Gray;
      mStandardColors[13] = Color.DodgerBlue;
      mStandardColors[14] = Color.Green;
      mStandardColors[15] = Color.HotPink;
    }

    public virtual void Draw(Graphics graph, double xOffset, double yOffset)
    {

    }

    public virtual void SetColor(Color color)
    {
      mColor = color;
    }

    public Color GetColor()
    {
      return mColor;
    }

    public void SetStandardColor(STANDARD_COLOR color)
    {
      SetColor(mStandardColors[(int)color]);
    }

    public STANDARD_COLOR GetStandardColor()
    {
      for(int i=0;i<MAX_STANDARD_COLORS;i++)
      {
        if(mColor == mStandardColors[i])
        {
          return (STANDARD_COLOR)i;
        }
      }
      return STANDARD_COLOR.BLACK;
    }
  }
}
