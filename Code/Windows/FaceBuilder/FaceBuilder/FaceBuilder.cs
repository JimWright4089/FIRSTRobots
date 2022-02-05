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

namespace FaceBuilder
{
  public partial class FaceBuilder : Form
  {
    const int TOP_OFFSET = 70;
    const int LEFT_OFFSET = 20;
    const int MAX_HEIGHT = 32;
    const int MAX_WIDTH = 32;
    const int PIXEL_SIZE = 7;
    const int MAX_FACE = 26;

    const int FULL_TOP_OFFSET = 120;
    const int FULL_LEFT_OFFSET = 360;

    UInt32[] faces = {
      0x00000000, 0x00000000, 0x00000000, 0x00FFFF00, 0x01FFFF80, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x01FFFF80, 0x00FFFF00, 0x00000000, 0x00000000, 0x00000000,
      0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00FFFF00, 0x01FFFF80, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x01FFFF80, 0x00FFFF00, 0x00000000, 0x00000000, 0x00000000, 0x00000000,
      0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00FFFF00, 0x01FFFF80, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x01FFFF80, 0x00FFFF00, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000,
      0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00FFFF00, 0x01FFFF80, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x01FFFF80, 0x00FFFF00, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000,
      0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00FFFF00, 0x01FFFF80, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x01FFFF80, 0x00FFFF00, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000,
      0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00FFFF00, 0x01FFFF80, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x01FFFF80, 0x00FFFF00, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000,
      0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x01FFFF80, 0x03FFFFC0, 0x03FFFFC0, 0x01FFFF80, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000,
      0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00FFFF00, 0x01FFFF80, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x030000C0, 0x02000040, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000,
      0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00FFFF00, 0x01FFFF80, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x030000C0, 0x030000C0, 0x02000040, 0x02000040, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000,
      0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00FFFF00, 0x01FFFF80, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x030000C0, 0x030000C0, 0x02000040, 0x02000040, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000,
      0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00FFFF00, 0x01FFFF80, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x030000C0, 0x030000C0, 0x02000040, 0x02000040, 0x02000040, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000,
      0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00FFFF00, 0x01FFFF80, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x030000C0, 0x030000C0, 0x030000C0, 0x02000040, 0x02000040, 0x02000040, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000,
      0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00FFFF00, 0x01FFFF80, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x030000C0, 0x030000C0, 0x02000040, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000,
      0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00FFFF00, 0x01FFFF80, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x01FFFF80, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000,
      0x7FFFFFFE, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF, 0x7FFFFFFE, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000,
      0x3FFFFFFC, 0x7FFFFFFE, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF, 0x7FFFFFFE, 0x3FFFFFFC, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000,
      0x1FFFFFF8, 0x3FFFFFFC, 0x7FFFFFFE, 0x7FFFFFFE, 0x7FFFFFFE, 0x7FFFFFFE, 0x7FFFFFFE, 0x7FFFFFFE, 0x7FFFFFFE, 0x7FFFFFFE, 0x3FFFFFFC, 0x1FFFFFF8, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000,
      0x0FFFFFF0, 0x1FFFFFF8, 0x3FFFFFFC, 0x3FFFFFFC, 0x3FFFFFFC, 0x3FFFFFFC, 0x3FFFFFFC, 0x3FFFFFFC, 0x3FFFFFFC, 0x3FFFFFFC, 0x3FFFFFFC, 0x3FFFFFFC, 0x3FFFFFFC, 0x1FFFFFF8, 0x0FFFFFF0, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000,
      0x07FFFFE0, 0x0FFFFFF0, 0x1FFFFFF8, 0x1FFFFFF8, 0x1FFFFFF8, 0x1FFFFFF8, 0x1FFFFFF8, 0x1FFFFFF8, 0x1FFFFFF8, 0x1FFFFFF8, 0x1FFFFFF8, 0x1FFFFFF8, 0x1FFFFFF8, 0x1FFFFFF8, 0x1FFFFFF8, 0x1FFFFFF8, 0x0FFFFFF0, 0x07FFFFE0, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000,
      0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x03000000, 0x03E00000, 0x03FC0000, 0x03FF8000, 0x03FFF000, 0x03FFFE00, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x01FFFF80, 0x00FFFF00, 0x00000000, 0x00000000, 0x00000000, 0x00000000,
      0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x02000000, 0x03800000, 0x03E00000, 0x03F80000, 0x03FE0000, 0x03FF8000, 0x03FFE000, 0x03FFF800, 0x03FFFE00, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x01FFFF80, 0x00FFFF00, 0x00000000, 0x00000000, 0x00000000, 0x00000000,
      0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x03000000, 0x03E00000, 0x03FC0000, 0x03FF8000, 0x03FFF000, 0x03FFFE00, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x01FFFF80, 0x00FFFF00, 0x00000000, 0x00000000, 0x00000000, 0x00000000,
      0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x000001C0, 0x00000FC0, 0x00007FC0, 0x0003FFC0, 0x001FFFC0, 0x00FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x01FFFF80, 0x00FFFF00, 0x00000000, 0x00000000, 0x00000000, 0x00000000,
      0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x000000C0, 0x000007C0, 0x00001FC0, 0x00007FC0, 0x0001FFC0, 0x0007FFC0, 0x001FFFC0, 0x007FFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x01FFFF80, 0x00FFFF00, 0x00000000, 0x00000000, 0x00000000, 0x00000000,
      0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x000000C0, 0x000007C0, 0x00003FC0, 0x0001FFC0, 0x000FFFC0, 0x007FFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x03FFFFC0, 0x01FFFF80, 0x00FFFF00, 0x00000000, 0x00000000, 0x00000000, 0x00000000,
      0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000
      };

    // 25

    int mCurFace = 0;

    Frame mCurFrame = new Frame();
    List<Frame> mFrames = new List<Frame> ();
    bool mAnamate = false;
    int mCurFrameNumber = 0;
    bool mForward = true;

    public FaceBuilder()
    {
      InitializeComponent();
      clearFace(this.CreateGraphics());
    }

    private void bUp_Click(object sender, EventArgs e)
    {
      mCurFace++;
      mCurFace = mCurFace % MAX_FACE;
      lFace.Text = mCurFace.ToString();
      this.Invalidate();
    }

    private void bDown_Click(object sender, EventArgs e)
    {
      mCurFace--;
      if (mCurFace < 0)
      {
        mCurFace = MAX_FACE - 1;
      }
      lFace.Text = mCurFace.ToString();
      this.Invalidate();
    }

    private void clearFace(Graphics graph)
    {
      Brush blackBrush = new SolidBrush(Color.Black);

      for (int i = 0; i < MAX_HEIGHT; i++)
      {
        for (int j = 0; j < MAX_WIDTH; j++)
        {
          graph.FillRectangle(blackBrush,
                          LEFT_OFFSET + (j * PIXEL_SIZE),
                          TOP_OFFSET + (i * PIXEL_SIZE),
                          PIXEL_SIZE,
                          PIXEL_SIZE);
        }
      }
    }

    private void drawFace(Graphics graph)
    {
      Brush whiteBrush = new SolidBrush(Color.White);

      int rowIndex = MAX_HEIGHT * mCurFace;

      for (int i = 0; i < MAX_HEIGHT; i++)
      {
        UInt32 curValue = faces[rowIndex + i];

        for (int j = 0; j < MAX_WIDTH; j++)
        {
          if (0 != (curValue & (1 << MAX_WIDTH - (j + 1))))
          {
            graph.FillRectangle(whiteBrush,
                            LEFT_OFFSET + (j * PIXEL_SIZE),
                            TOP_OFFSET + (i * PIXEL_SIZE),
                            PIXEL_SIZE,
                            PIXEL_SIZE);
          }
        }
      }
    }

    private void clearFullFace(Graphics graph)
    {
      Brush blackBrush = new SolidBrush(Color.Black);

      for (int i = 0; i < MAX_HEIGHT; i++)
      {
        for (int j = 0; j < MAX_WIDTH + MAX_WIDTH; j++)
        {
          graph.FillRectangle(blackBrush,
                          FULL_LEFT_OFFSET + (j * PIXEL_SIZE),
                          FULL_TOP_OFFSET + (i * PIXEL_SIZE),
                          PIXEL_SIZE,
                          PIXEL_SIZE);
        }
      }
    }

    private void drawFullFace(Graphics graph, Frame frame)
    {
      Brush whiteBrush = new SolidBrush(Color.White);

      for (int i = 0; i < MAX_HEIGHT; i++)
      {
        int rowIndex = MAX_HEIGHT * frame.leftEye;
        int topIndex = (i + frame.leftEyeTop) % MAX_HEIGHT;
        int itemIndex = (rowIndex + topIndex);
        if (itemIndex > (rowIndex + MAX_HEIGHT))
        {
          itemIndex = MAX_HEIGHT - itemIndex;
        }

        UInt32 curValue = faces[itemIndex];

        for (int j = 0; j < MAX_WIDTH; j++)
        {
          if (0 != (curValue & (1 << MAX_WIDTH - (j + 1))))
          {
            int eyeLeft = frame.leftEyeLeft+j;

            if(eyeLeft < 0)
            {
              eyeLeft = MAX_WIDTH + MAX_WIDTH + eyeLeft;
            }
            else
            {
              if (eyeLeft >= MAX_WIDTH + MAX_WIDTH)
              {
                eyeLeft -= MAX_WIDTH + MAX_WIDTH;
              }
            }

            graph.FillRectangle(whiteBrush,
                            FULL_LEFT_OFFSET + (eyeLeft * PIXEL_SIZE),
                            FULL_TOP_OFFSET + (i * PIXEL_SIZE),
                            PIXEL_SIZE,
                            PIXEL_SIZE);
          }
        }

        rowIndex = MAX_HEIGHT * frame.rightEye;
        topIndex = (i + frame.rightEyeTop) % MAX_HEIGHT;
        itemIndex = (rowIndex + topIndex);
        if (itemIndex > (rowIndex + MAX_HEIGHT))
        {
          itemIndex = MAX_HEIGHT - itemIndex;
        }
        curValue = faces[itemIndex];
        for (int j = 0; j < MAX_WIDTH; j++)
        {
          if (0 != (curValue & (1 << MAX_WIDTH - (j + 1))))
          {
            int eyeRight = MAX_WIDTH + frame.rightEyeLeft + j;

            if (eyeRight < 0)
            {
              eyeRight = MAX_WIDTH + MAX_WIDTH + eyeRight;
            }
            else
            {
              if (eyeRight >= MAX_WIDTH + MAX_WIDTH)
              {
                eyeRight -= MAX_WIDTH + MAX_WIDTH;
              }
            }

            graph.FillRectangle(whiteBrush,
                            FULL_LEFT_OFFSET + (eyeRight * PIXEL_SIZE),
                            FULL_TOP_OFFSET + (i * PIXEL_SIZE),
                            PIXEL_SIZE,
                            PIXEL_SIZE);
          }
        }
      }
    }

    private void FaceBuilder_Load(object sender, EventArgs e)
    {
      lLeftLeft.Text = mCurFrame.leftEyeLeft.ToString();
      lRightLeft.Text = mCurFrame.rightEyeLeft.ToString();
      lLeftTop.Text = mCurFrame.leftEyeTop.ToString();
      lRightTop.Text = mCurFrame.rightEyeTop.ToString();
    }

    private void FaceBuilder_Paint(object sender, PaintEventArgs e)
    {
      clearFace(e.Graphics);
      drawFace(e.Graphics);
      clearFullFace(e.Graphics);
      drawFullFace(e.Graphics, mCurFrame);
    }

    private void bLeft_Click(object sender, EventArgs e)
    {
      mCurFrame.leftEye = mCurFace;
      this.Invalidate();
    }

    private void bRight_Click(object sender, EventArgs e)
    {
      mCurFrame.rightEye = mCurFace;
      
      this.Invalidate();
    }

    private void bLeftLeft_Click(object sender, EventArgs e)
    {
      mCurFrame.leftEyeLeft = mCurFrame.leftEyeLeft-1;
      lLeftLeft.Text = mCurFrame.leftEyeLeft.ToString();
      this.Invalidate();
    }

    private void bLeftRight_Click(object sender, EventArgs e)
    {
      mCurFrame.leftEyeLeft = mCurFrame.leftEyeLeft + 1;
      lLeftLeft.Text = mCurFrame.leftEyeLeft.ToString();
      this.Invalidate();
    }

    private void bRightLeft_Click(object sender, EventArgs e)
    {
      mCurFrame.rightEyeLeft = mCurFrame.rightEyeLeft - 1;
      lRightLeft.Text = mCurFrame.rightEyeLeft.ToString();
      this.Invalidate();
    }

    private void bRightRight_Click(object sender, EventArgs e)
    {
      mCurFrame.rightEyeLeft = mCurFrame.rightEyeLeft + 1;
      lRightLeft.Text = mCurFrame.rightEyeLeft.ToString();
      this.Invalidate();
    }

    private void bLeftUp_Click(object sender, EventArgs e)
    {
      mCurFrame.leftEyeTop = mCurFrame.leftEyeTop + 1;
      lLeftTop.Text = mCurFrame.leftEyeTop.ToString();
      this.Invalidate();
    }

    private void bLeftDown_Click(object sender, EventArgs e)
    {
      mCurFrame.leftEyeTop = mCurFrame.leftEyeTop - 1;
      lLeftTop.Text = mCurFrame.leftEyeTop.ToString();
      this.Invalidate();
    }

    private void bRightUp_Click(object sender, EventArgs e)
    {
      mCurFrame.rightEyeTop = mCurFrame.rightEyeTop + 1;
      lRightTop.Text = mCurFrame.rightEyeTop.ToString();
      this.Invalidate();
    }

    private void bRightDown_Click(object sender, EventArgs e)
    {
      mCurFrame.rightEyeTop = mCurFrame.rightEyeTop - 1;
      lRightTop.Text = mCurFrame.rightEyeTop.ToString();
      this.Invalidate();
    }

    private void lAddFrame_Click(object sender, EventArgs e)
    {
      mFrames.Add(new Frame(mCurFrame));

      lbFrames.Items.Clear();

      foreach(Frame frame in mFrames) 
      {
        lbFrames.Items.Add(frame.ToString());
      }
    }

    private void bSave_Click(object sender, EventArgs e)
    {
      SaveFileDialog saveFileDialog = new SaveFileDialog();

      saveFileDialog.Filter = "Frame Files (.frame)|*.frame|All Files (*.*)|*.*";
      saveFileDialog.FilterIndex = 1;
      saveFileDialog.RestoreDirectory = true;

      if (saveFileDialog.ShowDialog() == DialogResult.OK)
      {
        StreamWriter file = new StreamWriter(saveFileDialog.FileName);
        foreach (Frame frame in mFrames)
        {
          file.WriteLine(frame.ToString()+","); 
        }
        file.Close();
      }
    }

    private void tDisplay_Tick(object sender, EventArgs e)
    {
      if(true == mAnamate)
      {
        mCurFrame = mFrames[mCurFrameNumber];
        this.Invalidate();

        if (true == mForward)
        {
          mCurFrameNumber++;
          if (mCurFrameNumber >= mFrames.Count)
          {
            mAnamate = false;
          }
        }
        else
        {
          mCurFrameNumber--;
          if (mCurFrameNumber < 0)
          {
            mAnamate = false;
          }
        }
      }
    }

    private void bAnimate_Click(object sender, EventArgs e)
    {
      if(mFrames.Count > 0)
      {
        mAnamate = true;
        mForward = true;
        mCurFrameNumber = 0;
      }
    }

    private void bClear_Click(object sender, EventArgs e)
    {
      mFrames = new List<Frame>();
      lbFrames.Items.Clear();
    }

    private void bRemove_Click(object sender, EventArgs e)
    {
      if(-1 != lbFrames.SelectedIndex)
      {
        mFrames.RemoveAt(lbFrames.SelectedIndex);
        lbFrames.Items.Clear();

        foreach (Frame frame in mFrames)
        {
          lbFrames.Items.Add(frame.ToString());
        }
      }
    }

    private void bLoad_Click(object sender, EventArgs e)
    {
      OpenFileDialog openFileDialog = new OpenFileDialog();

      openFileDialog.Filter = "Frame Files (.frame)|*.frame|All Files (*.*)|*.*";
      openFileDialog.FilterIndex = 1;
      openFileDialog.Multiselect = false;

      DialogResult dialogResult = openFileDialog.ShowDialog();

      if (DialogResult.OK == dialogResult)
      {
        mFrames.Clear();
        StreamReader file = new StreamReader(openFileDialog.FileName);
        while(!file.EndOfStream)
        {
          string line = file.ReadLine();
          mFrames.Add(new Frame(line)); 
        }
        file.Close();
        lbFrames.Items.Clear();

        foreach (Frame frame in mFrames)
        {
          lbFrames.Items.Add(frame.ToString());
        }
      }
    }

    private void bAnamBack_Click(object sender, EventArgs e)
    {
      if (mFrames.Count > 0)
      {
        mAnamate = true;
        mForward = false;
        mCurFrameNumber = mFrames.Count - 1;
      }
    }

    private void lbFrames_SelectedIndexChanged(object sender, EventArgs e)
    {
      if(-1 != lbFrames.SelectedIndex)
      {
        mCurFrame = new Frame(mFrames[lbFrames.SelectedIndex]);
        this.Invalidate();
      }
    }
  }

  public class Frame
  {
    int mLeftEye = 1;
    int mLeftEyeTop = 0;
    int mLeftEyeLeft = 3;

    int mRightEye = 1;
    int mRightEyeTop = 0;
    int mRightEyeLeft = -3;

    public int leftEye
    {
      get { return mLeftEye; }
      set { mLeftEye = value; }
    }

    public int leftEyeLeft
    {
      get { return mLeftEyeLeft; }
      set { mLeftEyeLeft = value; }
    }
    public int leftEyeTop
    {
      get { return mLeftEyeTop; }
      set { mLeftEyeTop = value; }
    }
    public int rightEye
    {
      get { return mRightEye; }
      set { mRightEye = value; }
    }
    public int rightEyeLeft
    {
      get { return mRightEyeLeft; }
      set { mRightEyeLeft = value; }
    }
    public int rightEyeTop
    {
      get { return mRightEyeTop; }
      set { mRightEyeTop = value; }
    }

    public Frame()
    {

    }

    public Frame(int leftEye, int leftEyeTop, int leftEyeLeft, 
                 int rightEye, int rightEyeTop, int rightEyeLeft)
    {
      mLeftEye = leftEye;
      mLeftEyeTop = leftEyeTop;
      mLeftEyeLeft = leftEyeLeft;
      mRightEye = rightEye;
      mRightEyeTop = rightEyeTop;
      mRightEyeLeft = rightEyeLeft;
    }

    public Frame(Frame other)
    {
      mLeftEye = other.leftEye;
      mLeftEyeTop = other.leftEyeTop;
      mLeftEyeLeft = other.leftEyeLeft;
      mRightEye = other.rightEye;
      mRightEyeTop = other.rightEyeTop;
      mRightEyeLeft = other.rightEyeLeft;
    }

    public Frame(string line)
    {
      string substring = "";

      line = line.Replace("{", "");
      line = line.Replace("}", "");

      int loc = line.IndexOf(",");
      if (loc != -1)
      {
        substring = line.Substring(0, loc);
        int.TryParse(substring, out mLeftEye);
        line = line.Substring(loc + 1);

        loc = line.IndexOf(",");
        if (loc != -1)
        {
          substring = line.Substring(0, loc);
          int.TryParse(substring, out mLeftEyeLeft);
          line = line.Substring(loc + 1);

          loc = line.IndexOf(",");
          if (loc != -1)
          {
            substring = line.Substring(0, loc);
            int.TryParse(substring, out mLeftEyeTop);
            line = line.Substring(loc + 1);

            loc = line.IndexOf(",");
            if (loc != -1)
            {
              substring = line.Substring(0, loc);
              int.TryParse(substring, out mRightEye);
              line = line.Substring(loc + 1);

              loc = line.IndexOf(",");
              if (loc != -1)
              {
                substring = line.Substring(0, loc);
                int.TryParse(substring, out mRightEyeLeft);
                line = line.Substring(loc + 1);

                loc = line.IndexOf(",");
                if (loc != -1)
                {
                  substring = line.Substring(0, loc);
                  int.TryParse(substring, out mRightEyeTop);
                }
              }
            }
          }
        }
      }
    }

    public override string ToString()
    {
      return "{" +
          mLeftEye.ToString() + ", " +
          mLeftEyeLeft.ToString() + ", " +
          mLeftEyeTop.ToString() + ", " +
          mRightEye.ToString() + ", " +
          mRightEyeLeft.ToString() + ", " +
          mRightEyeTop.ToString() + "}";
    }
  } 
}
