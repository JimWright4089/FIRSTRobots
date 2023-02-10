using System;
using System.Collections.Generic;
using System.Text;
using System.Windows.Forms;
using System.Drawing;

namespace DragAndDropTest
{
    class Text : DrawingObjects 
    {
        Point mPoint;
        string mText;
        double mSize;
        bool isRightJust;

        public Text(double x1, double y1, double size, Color color, string text) : base()
    {
            mPoint = new Point(x1, y1, size, color);
            mText = text;
            mColor = color;
            mSize = size;
        }

        public Text(double x1, double y1, double size, Color color, string text, bool isRightJust) : base()
    {
            mPoint = new Point(x1, y1, size, color);
            mText = text;
            mColor = color;
            mSize = size;
            this.isRightJust = isRightJust;
        }

        public void SetText(string text)
        {
            mText = text;
        }

        public override void Draw(Graphics graph, double xOffset, double yOffset)
        {
            Brush theBush = new SolidBrush(mColor);
            Font theFont = new Font("Arial", 14);

            graph.DrawString(mText, theFont, theBush, (float)(mPoint.GetX()+xOffset), (float)(mPoint.GetY()+yOffset));
        }
    }
}
