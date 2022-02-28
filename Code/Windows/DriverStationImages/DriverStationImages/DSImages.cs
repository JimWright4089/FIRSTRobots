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

namespace DriverStationImages
{
    public partial class DSImages : Form
    {
        Bitmap mImage;
        String mFileName;

        public DSImages()
        {
            InitializeComponent();
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
                mImage = new Bitmap(openFileDialog.FileName);
                pbImages.Image = mImage;
                mFileName = openFileDialog.FileName;
            }
        }

        private void bSave_Click(object sender, EventArgs e)
        {
            SaveFileDialog saveFileDialog = new SaveFileDialog();

            saveFileDialog.Filter = "C File (.c)|*.c|All Files (*.*)|*.*";
            saveFileDialog.FilterIndex = 1;
            saveFileDialog.RestoreDirectory = true;

            if (saveFileDialog.ShowDialog() == DialogResult.OK)
            {
                StreamWriter file = new StreamWriter(saveFileDialog.FileName);

                for(int i=0;i<mImage.Height;i++)
                {
                    for (int j = 0; j < mImage.Width; j++)
                    {
                        Color pixel = mImage.GetPixel(j, i);
                        int red = (pixel.R)/8;
                        int green = (pixel.G)/4;
                        int blue = (pixel.B)/8;
                        Int32 color = (red&0x1F) << 11;
                        color += (green&0x3F) << 5;
                        color += (blue & 0x1F);

                        file.Write("0x"+color.ToString("X4")+",");
                    }
                    file.WriteLine("");
                }
                file.Close();
            }
        }
    }
}
