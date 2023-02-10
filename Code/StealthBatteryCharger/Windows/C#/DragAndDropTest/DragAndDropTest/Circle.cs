using System;
using System.Collections.Generic;
using System.Text;
using System.Windows.Forms;
using System.Drawing;

namespace DragAndDropTest
{
    class Circle : DrawingObjects 
    {
        double mX;
        double mY;
        double mSize;

        public Circle(double x, double y, double size, Color color) : base()
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
            Pen thePen = new Pen(mColor);
            thePen.Width = 3;
            graph.DrawEllipse(  thePen,
                                (float)(xOffset + mX-(mSize/2)),
                                (float)(yOffset + mY - (mSize / 2)),
                                (float)(mSize),
                                (float)(mSize));
        }
    }
}
