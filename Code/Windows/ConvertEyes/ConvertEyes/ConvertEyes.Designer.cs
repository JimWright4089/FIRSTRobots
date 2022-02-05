namespace ConvertEyes
{
    partial class ConvertEyes
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
      this.bLoad = new System.Windows.Forms.Button();
      this.bSave = new System.Windows.Forms.Button();
      this.tbCode = new System.Windows.Forms.TextBox();
      this.pbImage = new System.Windows.Forms.PictureBox();
      ((System.ComponentModel.ISupportInitialize)(this.pbImage)).BeginInit();
      this.SuspendLayout();
      // 
      // bLoad
      // 
      this.bLoad.Location = new System.Drawing.Point(530, 547);
      this.bLoad.Margin = new System.Windows.Forms.Padding(5);
      this.bLoad.Name = "bLoad";
      this.bLoad.Size = new System.Drawing.Size(125, 39);
      this.bLoad.TabIndex = 0;
      this.bLoad.Text = "Load";
      this.bLoad.UseVisualStyleBackColor = true;
      this.bLoad.Click += new System.EventHandler(this.bLoad_Click);
      // 
      // bSave
      // 
      this.bSave.Location = new System.Drawing.Point(665, 547);
      this.bSave.Margin = new System.Windows.Forms.Padding(5);
      this.bSave.Name = "bSave";
      this.bSave.Size = new System.Drawing.Size(125, 39);
      this.bSave.TabIndex = 1;
      this.bSave.Text = "Save";
      this.bSave.UseVisualStyleBackColor = true;
      this.bSave.Click += new System.EventHandler(this.bSave_Click);
      // 
      // tbCode
      // 
      this.tbCode.Location = new System.Drawing.Point(452, 14);
      this.tbCode.Margin = new System.Windows.Forms.Padding(5);
      this.tbCode.Multiline = true;
      this.tbCode.Name = "tbCode";
      this.tbCode.Size = new System.Drawing.Size(336, 523);
      this.tbCode.TabIndex = 2;
      // 
      // pbImage
      // 
      this.pbImage.Location = new System.Drawing.Point(12, 14);
      this.pbImage.Name = "pbImage";
      this.pbImage.Size = new System.Drawing.Size(400, 400);
      this.pbImage.SizeMode = System.Windows.Forms.PictureBoxSizeMode.StretchImage;
      this.pbImage.TabIndex = 3;
      this.pbImage.TabStop = false;
      // 
      // ConvertEyes
      // 
      this.AutoScaleDimensions = new System.Drawing.SizeF(10F, 22F);
      this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
      this.ClientSize = new System.Drawing.Size(807, 606);
      this.Controls.Add(this.pbImage);
      this.Controls.Add(this.tbCode);
      this.Controls.Add(this.bSave);
      this.Controls.Add(this.bLoad);
      this.Font = new System.Drawing.Font("Microsoft Sans Serif", 11.78182F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
      this.Margin = new System.Windows.Forms.Padding(5);
      this.Name = "ConvertEyes";
      this.Text = "Convert Eyes";
      ((System.ComponentModel.ISupportInitialize)(this.pbImage)).EndInit();
      this.ResumeLayout(false);
      this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Button bLoad;
        private System.Windows.Forms.Button bSave;
        private System.Windows.Forms.TextBox tbCode;
        private System.Windows.Forms.PictureBox pbImage;
    }
}

