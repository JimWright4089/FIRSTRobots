namespace DriverStationImages
{
    partial class DSImages
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.bSave = new System.Windows.Forms.Button();
            this.bLoad = new System.Windows.Forms.Button();
            this.pbImages = new System.Windows.Forms.PictureBox();
            ((System.ComponentModel.ISupportInitialize)(this.pbImages)).BeginInit();
            this.SuspendLayout();
            // 
            // bSave
            // 
            this.bSave.Location = new System.Drawing.Point(177, 166);
            this.bSave.Margin = new System.Windows.Forms.Padding(6, 6, 6, 6);
            this.bSave.Name = "bSave";
            this.bSave.Size = new System.Drawing.Size(150, 44);
            this.bSave.TabIndex = 0;
            this.bSave.Text = "Save";
            this.bSave.UseVisualStyleBackColor = true;
            this.bSave.Click += new System.EventHandler(this.bSave_Click);
            // 
            // bLoad
            // 
            this.bLoad.Location = new System.Drawing.Point(15, 166);
            this.bLoad.Margin = new System.Windows.Forms.Padding(6, 6, 6, 6);
            this.bLoad.Name = "bLoad";
            this.bLoad.Size = new System.Drawing.Size(150, 44);
            this.bLoad.TabIndex = 1;
            this.bLoad.Text = "Load";
            this.bLoad.UseVisualStyleBackColor = true;
            this.bLoad.Click += new System.EventHandler(this.bLoad_Click);
            // 
            // pbImages
            // 
            this.pbImages.Location = new System.Drawing.Point(15, 15);
            this.pbImages.Margin = new System.Windows.Forms.Padding(6, 6, 6, 6);
            this.pbImages.Name = "pbImages";
            this.pbImages.Size = new System.Drawing.Size(1024, 128);
            this.pbImages.SizeMode = System.Windows.Forms.PictureBoxSizeMode.StretchImage;
            this.pbImages.TabIndex = 2;
            this.pbImages.TabStop = false;
            // 
            // DSImages
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(12F, 25F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(1066, 255);
            this.Controls.Add(this.pbImages);
            this.Controls.Add(this.bLoad);
            this.Controls.Add(this.bSave);
            this.Font = new System.Drawing.Font("Microsoft Sans Serif", 13.74545F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.Margin = new System.Windows.Forms.Padding(6, 6, 6, 6);
            this.Name = "DSImages";
            this.Text = "Form1";
            ((System.ComponentModel.ISupportInitialize)(this.pbImages)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Button bSave;
        private System.Windows.Forms.Button bLoad;
        private System.Windows.Forms.PictureBox pbImages;
    }
}

