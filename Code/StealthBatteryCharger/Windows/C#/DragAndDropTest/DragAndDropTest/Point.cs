using System;
using System.Collections.Generic;
using System.Text;
using System.Windows.Forms;
using System.Drawing;

namespace DragAndDropTest
{
    class Point : DrawingObjects
    {
        double mX;
        double mY;
        double mSize;
        Color  mColor;

        public Point(double x, double y, double size, Color color)
        {
            mX = x;
            mY = y;
            mSize = size;
            mColor = color;
        }

        public double GetX()
        {
            return mX;
        }

        public double GetY()
        {
            return mY;
        }

        public override void Draw(Graphics graph, double xOffset, double yOffset)
        {
            Brush theBush = new SolidBrush(mColor);
            graph.FillEllipse(theBush,
                                (float)(xOffset + mX-(mSize/2)),
                                (float)(yOffset + mY - (mSize / 2)),
                                (float)(mSize),
                                (float)(mSize));
        }
    }
}
