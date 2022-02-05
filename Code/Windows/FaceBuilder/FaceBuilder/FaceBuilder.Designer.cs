namespace FaceBuilder
{
  partial class FaceBuilder
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
      this.components = new System.ComponentModel.Container();
      this.bUp = new System.Windows.Forms.Button();
      this.bDown = new System.Windows.Forms.Button();
      this.lFace = new System.Windows.Forms.Label();
      this.bRightUp = new System.Windows.Forms.Button();
      this.bRightDown = new System.Windows.Forms.Button();
      this.bRight = new System.Windows.Forms.Button();
      this.bRightRight = new System.Windows.Forms.Button();
      this.bRightLeft = new System.Windows.Forms.Button();
      this.bLeftDown = new System.Windows.Forms.Button();
      this.lRightTop = new System.Windows.Forms.Label();
      this.lLeftTop = new System.Windows.Forms.Label();
      this.lRightLeft = new System.Windows.Forms.Label();
      this.lLeftLeft = new System.Windows.Forms.Label();
      this.bLeftUp = new System.Windows.Forms.Button();
      this.bLeftRight = new System.Windows.Forms.Button();
      this.bLeftLeft = new System.Windows.Forms.Button();
      this.bLeft = new System.Windows.Forms.Button();
      this.lbFrames = new System.Windows.Forms.ListBox();
      this.bRemove = new System.Windows.Forms.Button();
      this.bAnimate = new System.Windows.Forms.Button();
      this.lAddFrame = new System.Windows.Forms.Button();
      this.tDisplay = new System.Windows.Forms.Timer(this.components);
      this.bSave = new System.Windows.Forms.Button();
      this.bClear = new System.Windows.Forms.Button();
      this.bLoad = new System.Windows.Forms.Button();
      this.bAnamBack = new System.Windows.Forms.Button();
      this.SuspendLayout();
      // 
      // bUp
      // 
      this.bUp.Location = new System.Drawing.Point(20, 20);
      this.bUp.Margin = new System.Windows.Forms.Padding(5);
      this.bUp.Name = "bUp";
      this.bUp.Size = new System.Drawing.Size(94, 39);
      this.bUp.TabIndex = 0;
      this.bUp.Text = "Up";
      this.bUp.UseVisualStyleBackColor = true;
      this.bUp.Click += new System.EventHandler(this.bUp_Click);
      // 
      // bDown
      // 
      this.bDown.Location = new System.Drawing.Point(124, 20);
      this.bDown.Margin = new System.Windows.Forms.Padding(5);
      this.bDown.Name = "bDown";
      this.bDown.Size = new System.Drawing.Size(94, 39);
      this.bDown.TabIndex = 1;
      this.bDown.Text = "Down";
      this.bDown.UseVisualStyleBackColor = true;
      this.bDown.Click += new System.EventHandler(this.bDown_Click);
      // 
      // lFace
      // 
      this.lFace.AutoSize = true;
      this.lFace.Location = new System.Drawing.Point(226, 28);
      this.lFace.Name = "lFace";
      this.lFace.Size = new System.Drawing.Size(20, 24);
      this.lFace.TabIndex = 2;
      this.lFace.Text = "0";
      // 
      // bRightUp
      // 
      this.bRightUp.Location = new System.Drawing.Point(816, 121);
      this.bRightUp.Name = "bRightUp";
      this.bRightUp.Size = new System.Drawing.Size(75, 31);
      this.bRightUp.TabIndex = 3;
      this.bRightUp.Text = "Up";
      this.bRightUp.UseVisualStyleBackColor = true;
      this.bRightUp.Click += new System.EventHandler(this.bRightUp_Click);
      // 
      // bRightDown
      // 
      this.bRightDown.Location = new System.Drawing.Point(816, 180);
      this.bRightDown.Name = "bRightDown";
      this.bRightDown.Size = new System.Drawing.Size(75, 31);
      this.bRightDown.TabIndex = 4;
      this.bRightDown.Text = "Down";
      this.bRightDown.UseVisualStyleBackColor = true;
      this.bRightDown.Click += new System.EventHandler(this.bRightDown_Click);
      // 
      // bRight
      // 
      this.bRight.Location = new System.Drawing.Point(667, 39);
      this.bRight.Name = "bRight";
      this.bRight.Size = new System.Drawing.Size(75, 31);
      this.bRight.TabIndex = 5;
      this.bRight.Text = "Right";
      this.bRight.UseVisualStyleBackColor = true;
      this.bRight.Click += new System.EventHandler(this.bRight_Click);
      // 
      // bRightRight
      // 
      this.bRightRight.Location = new System.Drawing.Point(736, 80);
      this.bRightRight.Name = "bRightRight";
      this.bRightRight.Size = new System.Drawing.Size(75, 31);
      this.bRightRight.TabIndex = 6;
      this.bRightRight.Text = "->";
      this.bRightRight.UseVisualStyleBackColor = true;
      this.bRightRight.Click += new System.EventHandler(this.bRightRight_Click);
      // 
      // bRightLeft
      // 
      this.bRightLeft.Location = new System.Drawing.Point(591, 80);
      this.bRightLeft.Name = "bRightLeft";
      this.bRightLeft.Size = new System.Drawing.Size(75, 31);
      this.bRightLeft.TabIndex = 7;
      this.bRightLeft.Text = "<-";
      this.bRightLeft.UseVisualStyleBackColor = true;
      this.bRightLeft.Click += new System.EventHandler(this.bRightLeft_Click);
      // 
      // bLeftDown
      // 
      this.bLeftDown.Location = new System.Drawing.Point(279, 180);
      this.bLeftDown.Name = "bLeftDown";
      this.bLeftDown.Size = new System.Drawing.Size(75, 31);
      this.bLeftDown.TabIndex = 8;
      this.bLeftDown.Text = "Down";
      this.bLeftDown.UseVisualStyleBackColor = true;
      this.bLeftDown.Click += new System.EventHandler(this.bLeftDown_Click);
      // 
      // lRightTop
      // 
      this.lRightTop.AutoSize = true;
      this.lRightTop.Location = new System.Drawing.Point(822, 155);
      this.lRightTop.Name = "lRightTop";
      this.lRightTop.Size = new System.Drawing.Size(60, 24);
      this.lRightTop.TabIndex = 9;
      this.lRightTop.Text = "label1";
      // 
      // lLeftTop
      // 
      this.lLeftTop.AutoSize = true;
      this.lLeftTop.Location = new System.Drawing.Point(285, 155);
      this.lLeftTop.Name = "lLeftTop";
      this.lLeftTop.Size = new System.Drawing.Size(60, 24);
      this.lLeftTop.TabIndex = 10;
      this.lLeftTop.Text = "label2";
      // 
      // lRightLeft
      // 
      this.lRightLeft.AutoSize = true;
      this.lRightLeft.Location = new System.Drawing.Point(672, 84);
      this.lRightLeft.Name = "lRightLeft";
      this.lRightLeft.Size = new System.Drawing.Size(60, 24);
      this.lRightLeft.TabIndex = 11;
      this.lRightLeft.Text = "label3";
      // 
      // lLeftLeft
      // 
      this.lLeftLeft.AutoSize = true;
      this.lLeftLeft.Location = new System.Drawing.Point(432, 84);
      this.lLeftLeft.Name = "lLeftLeft";
      this.lLeftLeft.Size = new System.Drawing.Size(60, 24);
      this.lLeftLeft.TabIndex = 12;
      this.lLeftLeft.Text = "label4";
      // 
      // bLeftUp
      // 
      this.bLeftUp.Location = new System.Drawing.Point(279, 121);
      this.bLeftUp.Name = "bLeftUp";
      this.bLeftUp.Size = new System.Drawing.Size(75, 31);
      this.bLeftUp.TabIndex = 13;
      this.bLeftUp.Text = "Up";
      this.bLeftUp.UseVisualStyleBackColor = true;
      this.bLeftUp.Click += new System.EventHandler(this.bLeftUp_Click);
      // 
      // bLeftRight
      // 
      this.bLeftRight.Location = new System.Drawing.Point(496, 80);
      this.bLeftRight.Name = "bLeftRight";
      this.bLeftRight.Size = new System.Drawing.Size(75, 31);
      this.bLeftRight.TabIndex = 14;
      this.bLeftRight.Text = "->";
      this.bLeftRight.UseVisualStyleBackColor = true;
      this.bLeftRight.Click += new System.EventHandler(this.bLeftRight_Click);
      // 
      // bLeftLeft
      // 
      this.bLeftLeft.Location = new System.Drawing.Point(351, 84);
      this.bLeftLeft.Name = "bLeftLeft";
      this.bLeftLeft.Size = new System.Drawing.Size(75, 31);
      this.bLeftLeft.TabIndex = 15;
      this.bLeftLeft.Text = "<-";
      this.bLeftLeft.UseVisualStyleBackColor = true;
      this.bLeftLeft.Click += new System.EventHandler(this.bLeftLeft_Click);
      // 
      // bLeft
      // 
      this.bLeft.Location = new System.Drawing.Point(427, 39);
      this.bLeft.Name = "bLeft";
      this.bLeft.Size = new System.Drawing.Size(75, 31);
      this.bLeft.TabIndex = 16;
      this.bLeft.Text = "Left";
      this.bLeft.UseVisualStyleBackColor = true;
      this.bLeft.Click += new System.EventHandler(this.bLeft_Click);
      // 
      // lbFrames
      // 
      this.lbFrames.FormattingEnabled = true;
      this.lbFrames.ItemHeight = 22;
      this.lbFrames.Location = new System.Drawing.Point(922, 24);
      this.lbFrames.Name = "lbFrames";
      this.lbFrames.Size = new System.Drawing.Size(247, 268);
      this.lbFrames.TabIndex = 17;
      this.lbFrames.SelectedIndexChanged += new System.EventHandler(this.lbFrames_SelectedIndexChanged);
      // 
      // bRemove
      // 
      this.bRemove.Location = new System.Drawing.Point(1084, 310);
      this.bRemove.Name = "bRemove";
      this.bRemove.Size = new System.Drawing.Size(75, 31);
      this.bRemove.TabIndex = 18;
      this.bRemove.Text = "Remove";
      this.bRemove.UseVisualStyleBackColor = true;
      this.bRemove.Click += new System.EventHandler(this.bRemove_Click);
      // 
      // bAnimate
      // 
      this.bAnimate.Location = new System.Drawing.Point(826, 239);
      this.bAnimate.Name = "bAnimate";
      this.bAnimate.Size = new System.Drawing.Size(75, 31);
      this.bAnimate.TabIndex = 19;
      this.bAnimate.Text = "Anam";
      this.bAnimate.UseVisualStyleBackColor = true;
      this.bAnimate.Click += new System.EventHandler(this.bAnimate_Click);
      // 
      // lAddFrame
      // 
      this.lAddFrame.Location = new System.Drawing.Point(922, 310);
      this.lAddFrame.Name = "lAddFrame";
      this.lAddFrame.Size = new System.Drawing.Size(75, 31);
      this.lAddFrame.TabIndex = 20;
      this.lAddFrame.Text = "Add";
      this.lAddFrame.UseVisualStyleBackColor = true;
      this.lAddFrame.Click += new System.EventHandler(this.lAddFrame_Click);
      // 
      // tDisplay
      // 
      this.tDisplay.Enabled = true;
      this.tDisplay.Interval = 50;
      this.tDisplay.Tick += new System.EventHandler(this.tDisplay_Tick);
      // 
      // bSave
      // 
      this.bSave.Location = new System.Drawing.Point(1003, 310);
      this.bSave.Name = "bSave";
      this.bSave.Size = new System.Drawing.Size(75, 31);
      this.bSave.TabIndex = 21;
      this.bSave.Text = "Save...";
      this.bSave.UseVisualStyleBackColor = true;
      this.bSave.Click += new System.EventHandler(this.bSave_Click);
      // 
      // bClear
      // 
      this.bClear.Location = new System.Drawing.Point(1084, 357);
      this.bClear.Name = "bClear";
      this.bClear.Size = new System.Drawing.Size(75, 31);
      this.bClear.TabIndex = 22;
      this.bClear.Text = "Clear";
      this.bClear.UseVisualStyleBackColor = true;
      this.bClear.Click += new System.EventHandler(this.bClear_Click);
      // 
      // bLoad
      // 
      this.bLoad.Location = new System.Drawing.Point(1003, 357);
      this.bLoad.Name = "bLoad";
      this.bLoad.Size = new System.Drawing.Size(75, 31);
      this.bLoad.TabIndex = 23;
      this.bLoad.Text = "Load";
      this.bLoad.UseVisualStyleBackColor = true;
      this.bLoad.Click += new System.EventHandler(this.bLoad_Click);
      // 
      // bAnamBack
      // 
      this.bAnamBack.Location = new System.Drawing.Point(826, 269);
      this.bAnamBack.Name = "bAnamBack";
      this.bAnamBack.Size = new System.Drawing.Size(75, 31);
      this.bAnamBack.TabIndex = 24;
      this.bAnamBack.Text = "A Bck";
      this.bAnamBack.UseVisualStyleBackColor = true;
      this.bAnamBack.Click += new System.EventHandler(this.bAnamBack_Click);
      // 
      // FaceBuilder
      // 
      this.AutoScaleDimensions = new System.Drawing.SizeF(10F, 22F);
      this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
      this.ClientSize = new System.Drawing.Size(1191, 420);
      this.Controls.Add(this.bAnamBack);
      this.Controls.Add(this.bLoad);
      this.Controls.Add(this.bClear);
      this.Controls.Add(this.bSave);
      this.Controls.Add(this.lAddFrame);
      this.Controls.Add(this.bAnimate);
      this.Controls.Add(this.bRemove);
      this.Controls.Add(this.lbFrames);
      this.Controls.Add(this.bLeft);
      this.Controls.Add(this.bLeftLeft);
      this.Controls.Add(this.bLeftRight);
      this.Controls.Add(this.bLeftUp);
      this.Controls.Add(this.lLeftLeft);
      this.Controls.Add(this.lRightLeft);
      this.Controls.Add(this.lLeftTop);
      this.Controls.Add(this.lRightTop);
      this.Controls.Add(this.bLeftDown);
      this.Controls.Add(this.bRightLeft);
      this.Controls.Add(this.bRightRight);
      this.Controls.Add(this.bRight);
      this.Controls.Add(this.bRightDown);
      this.Controls.Add(this.bRightUp);
      this.Controls.Add(this.lFace);
      this.Controls.Add(this.bDown);
      this.Controls.Add(this.bUp);
      this.DoubleBuffered = true;
      this.Font = new System.Drawing.Font("Microsoft Sans Serif", 11.78182F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
      this.Margin = new System.Windows.Forms.Padding(5);
      this.Name = "FaceBuilder";
      this.Text = "Face Builder";
      this.Load += new System.EventHandler(this.FaceBuilder_Load);
      this.Paint += new System.Windows.Forms.PaintEventHandler(this.FaceBuilder_Paint);
      this.ResumeLayout(false);
      this.PerformLayout();

    }

    #endregion

    private System.Windows.Forms.Button bUp;
    private System.Windows.Forms.Button bDown;
    private System.Windows.Forms.Label lFace;
    private System.Windows.Forms.Button bRightUp;
    private System.Windows.Forms.Button bRightDown;
    private System.Windows.Forms.Button bRight;
    private System.Windows.Forms.Button bRightRight;
    private System.Windows.Forms.Button bRightLeft;
    private System.Windows.Forms.Button bLeftDown;
    private System.Windows.Forms.Label lRightTop;
    private System.Windows.Forms.Label lLeftTop;
    private System.Windows.Forms.Label lRightLeft;
    private System.Windows.Forms.Label lLeftLeft;
    private System.Windows.Forms.Button bLeftUp;
    private System.Windows.Forms.Button bLeftRight;
    private System.Windows.Forms.Button bLeftLeft;
    private System.Windows.Forms.Button bLeft;
    private System.Windows.Forms.ListBox lbFrames;
    private System.Windows.Forms.Button bRemove;
    private System.Windows.Forms.Button bAnimate;
    private System.Windows.Forms.Button lAddFrame;
    private System.Windows.Forms.Timer tDisplay;
    private System.Windows.Forms.Button bSave;
    private System.Windows.Forms.Button bClear;
    private System.Windows.Forms.Button bLoad;
    private System.Windows.Forms.Button bAnamBack;
  }
}

