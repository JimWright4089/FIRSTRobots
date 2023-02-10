using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace DragAndDropTest
{
  public partial class DragAndDrop : Form
  {
    const int MAX_BATTERIES = 10;
    const int MAX_BOXES = 19;
    const int MAX_SLOTS = 9;
    const int HOT_SLOT = 6;
    const int TEST_SLOT = 8;
    const int SPARE_SLOT = 7;
    const int MAX_CHARGE_SLOTS = 6;
    const int MAX_RELAYS = 6;
    const int MAX_CHARGE_COLS = 3;

    List<DrawingObjects> mDrawingList = new List<DrawingObjects>();
    PictureBox[] mBoxes;
    PictureBox mSelected;
    Battery[] mBatterys = new Battery[MAX_BATTERIES];
    Battery[] mOutsideSlots = new Battery[MAX_BATTERIES];
    Battery[] mSlots = new Battery[MAX_SLOTS];
    string[] mBoxesNames = new string[MAX_BOXES];
    CheckBox[] mConnectBoxes;

    SlotStatus[] mSlotStatus = new SlotStatus[MAX_SLOTS];
    SlotStatus[] mPrevSlotStatus = new SlotStatus[MAX_SLOTS];
    //    bool[] mCharging = new bool[MAX_SLOTS];
    //    bool[] mCharged = new bool[MAX_SLOTS];
    bool[] mRelays = new bool[MAX_RELAYS];
    int[] mChargeCount = new int[MAX_CHARGE_COLS];
    SynopticSlot[] mSynopticSlot = new SynopticSlot[MAX_SLOTS];

    BatteryInfoFile mBatteryFile = BatteryInfoFile.GetInstance();
    BatteryHistory mBatteryHistory = BatteryHistory.GetInstance();

    const int MAIN_CHARGER_TOP = 10; // Original 120
    const int FIRST_HORZ_LINE = 40;

    const int SINGLE_CHARGER_TOP = FIRST_HORZ_LINE;
    const int SINGLE_CHARGER_LEFT = 10;

    const int THREE_CHARGER_TOP = MAIN_CHARGER_TOP;
    const int THREE_CHARGER_LEFT = 40;

    // Error codes indicating success or failure of function calls 
    SynopticsBitDef[] synopticsBitDeffList =
    {
      new SynopticsBitDef(SynopticsBitDef.MESS_BOX1,0,4,0,SynopticsBitDef.SYNOPTICS.C1_SINGLE_CHARGER,SynopticsBitDef.OUTLINE_TYPE),
      new SynopticsBitDef(SynopticsBitDef.MESS_BOX1,4,4,0,SynopticsBitDef.SYNOPTICS.C3_THREE_CHARGER,SynopticsBitDef.OUTLINE_TYPE),
      new SynopticsBitDef(SynopticsBitDef.MESS_BOX1,8,4,0,SynopticsBitDef.SYNOPTICS.B_1ST_COL_TOP,SynopticsBitDef.OUTLINE_TYPE),
      new SynopticsBitDef(SynopticsBitDef.MESS_BOX1,12,4,0,SynopticsBitDef.SYNOPTICS.B_1ST_COL_BOTTOM,SynopticsBitDef.OUTLINE_TYPE),

      new SynopticsBitDef(SynopticsBitDef.MESS_BOX2,0,4,0,SynopticsBitDef.SYNOPTICS.B_2ND_COL_TOP,SynopticsBitDef.OUTLINE_TYPE),
      new SynopticsBitDef(SynopticsBitDef.MESS_BOX2,4,4,0,SynopticsBitDef.SYNOPTICS.B_2ND_COL_BOTTOM,SynopticsBitDef.OUTLINE_TYPE),
      new SynopticsBitDef(SynopticsBitDef.MESS_BOX2,8,4,0,SynopticsBitDef.SYNOPTICS.B_3RD_COL_TOP,SynopticsBitDef.OUTLINE_TYPE),
      new SynopticsBitDef(SynopticsBitDef.MESS_BOX2,12,4,0,SynopticsBitDef.SYNOPTICS.B_3RD_COL_BOTTOM,SynopticsBitDef.OUTLINE_TYPE),

      new SynopticsBitDef(SynopticsBitDef.MESS_BOX3,0,4,0,SynopticsBitDef.SYNOPTICS.B_HOT_SLOT,SynopticsBitDef.OUTLINE_TYPE),
      new SynopticsBitDef(SynopticsBitDef.MESS_BOX3,4,4,0,SynopticsBitDef.SYNOPTICS.B_SPARE_SLOT,SynopticsBitDef.OUTLINE_TYPE),
      new SynopticsBitDef(SynopticsBitDef.MESS_BOX3,8,4,0,SynopticsBitDef.SYNOPTICS.B_TEST_SLOT,SynopticsBitDef.OUTLINE_TYPE),

      new SynopticsBitDef(SynopticsBitDef.MESS_HOT_LINE,0,4,0,SynopticsBitDef.SYNOPTICS.L_HOT_CHARGE,SynopticsBitDef.LINE_TYPE),

      new SynopticsBitDef(SynopticsBitDef.MESS_1ST_COL_LINE,0,4,0,SynopticsBitDef.SYNOPTICS.L_1ST_COL_1ST_SEG,SynopticsBitDef.LINE_TYPE),
      new SynopticsBitDef(SynopticsBitDef.MESS_1ST_COL_LINE,4,4,0,SynopticsBitDef.SYNOPTICS.L_1ST_COL_2ND_SEG,SynopticsBitDef.LINE_TYPE),
      new SynopticsBitDef(SynopticsBitDef.MESS_1ST_COL_LINE,8,4,0,SynopticsBitDef.SYNOPTICS.L_1ST_COL_1ST_SLOT,SynopticsBitDef.LINE_TYPE),
      new SynopticsBitDef(SynopticsBitDef.MESS_1ST_COL_LINE,12,4,0,SynopticsBitDef.SYNOPTICS.L_1ST_COL_2ND_SLOT,SynopticsBitDef.LINE_TYPE),

      new SynopticsBitDef(SynopticsBitDef.MESS_2ND_COL_LINE,0,4,0,SynopticsBitDef.SYNOPTICS.L_2ND_COL_1ST_SEG,SynopticsBitDef.LINE_TYPE),
      new SynopticsBitDef(SynopticsBitDef.MESS_2ND_COL_LINE,4,4,0,SynopticsBitDef.SYNOPTICS.L_2ND_COL_2ND_SEG,SynopticsBitDef.LINE_TYPE),
      new SynopticsBitDef(SynopticsBitDef.MESS_2ND_COL_LINE,8,4,0,SynopticsBitDef.SYNOPTICS.L_2ND_COL_1ST_SLOT,SynopticsBitDef.LINE_TYPE),
      new SynopticsBitDef(SynopticsBitDef.MESS_2ND_COL_LINE,12,4,0,SynopticsBitDef.SYNOPTICS.L_2ND_COL_2ND_SLOT,SynopticsBitDef.LINE_TYPE),

      new SynopticsBitDef(SynopticsBitDef.MESS_3RD_COL_LINE,0,4,0,SynopticsBitDef.SYNOPTICS.L_3RD_COL_1ST_SEG,SynopticsBitDef.LINE_TYPE),
      new SynopticsBitDef(SynopticsBitDef.MESS_3RD_COL_LINE,4,4,0,SynopticsBitDef.SYNOPTICS.L_3RD_COL_2ND_SEG,SynopticsBitDef.LINE_TYPE),
      new SynopticsBitDef(SynopticsBitDef.MESS_3RD_COL_LINE,8,4,0,SynopticsBitDef.SYNOPTICS.L_3RD_COL_1ST_SLOT,SynopticsBitDef.LINE_TYPE),
      new SynopticsBitDef(SynopticsBitDef.MESS_3RD_COL_LINE,12,4,0,SynopticsBitDef.SYNOPTICS.L_3RD_COL_2ND_SLOT,SynopticsBitDef.LINE_TYPE),

      new SynopticsBitDef(SynopticsBitDef.MESS_SINGLE_AMPS,0,4,0,SynopticsBitDef.SYNOPTICS.A1_SINGLE_AMPS,SynopticsBitDef.COLOR_TYPE),
      new SynopticsBitDef(SynopticsBitDef.MESS_SINGLE_AMPS,4,12,0,SynopticsBitDef.SYNOPTICS.A1_SINGLE_AMPS,SynopticsBitDef.AMPS_TYPE),

      new SynopticsBitDef(SynopticsBitDef.MESS_FIRST_AMPS,0,4,0,SynopticsBitDef.SYNOPTICS.A2_FIRST_AMPS,SynopticsBitDef.COLOR_TYPE),
      new SynopticsBitDef(SynopticsBitDef.MESS_FIRST_AMPS,4,12,0,SynopticsBitDef.SYNOPTICS.A2_FIRST_AMPS,SynopticsBitDef.AMPS_TYPE),

      new SynopticsBitDef(SynopticsBitDef.MESS_SECOND_AMPS,0,4,0,SynopticsBitDef.SYNOPTICS.A3_SECOND_AMPS,SynopticsBitDef.COLOR_TYPE),
      new SynopticsBitDef(SynopticsBitDef.MESS_SECOND_AMPS,4,12,0,SynopticsBitDef.SYNOPTICS.A3_SECOND_AMPS,SynopticsBitDef.AMPS_TYPE),

      new SynopticsBitDef(SynopticsBitDef.MESS_THIRD_AMPS,0,4,0,SynopticsBitDef.SYNOPTICS.A4_THIRD_AMPS,SynopticsBitDef.COLOR_TYPE),
      new SynopticsBitDef(SynopticsBitDef.MESS_THIRD_AMPS,4,12,0,SynopticsBitDef.SYNOPTICS.A4_THIRD_AMPS,SynopticsBitDef.AMPS_TYPE),


      new SynopticsBitDef(SynopticsBitDef.MESS_ID_HOT_SLOT,0,4,0,SynopticsBitDef.SYNOPTICS.ID_HOT_SLOT,SynopticsBitDef.COLOR_TYPE),
      new SynopticsBitDef(SynopticsBitDef.MESS_ID_HOT_SLOT,4,12,0,SynopticsBitDef.SYNOPTICS.ID_HOT_SLOT,SynopticsBitDef.ID_TYPE),

      new SynopticsBitDef(SynopticsBitDef.MESS_ID_TEST_SLOT,0,4,0,SynopticsBitDef.SYNOPTICS.ID_TEST_SLOT,SynopticsBitDef.COLOR_TYPE),
      new SynopticsBitDef(SynopticsBitDef.MESS_ID_TEST_SLOT,4,12,0,SynopticsBitDef.SYNOPTICS.ID_TEST_SLOT,SynopticsBitDef.ID_TYPE),

      new SynopticsBitDef(SynopticsBitDef.MESS_ID_SPARE_SLOT,0,4,0,SynopticsBitDef.SYNOPTICS.ID_SPARE_SLOT,SynopticsBitDef.COLOR_TYPE),
      new SynopticsBitDef(SynopticsBitDef.MESS_ID_SPARE_SLOT,4,12,0,SynopticsBitDef.SYNOPTICS.ID_SPARE_SLOT,SynopticsBitDef.ID_TYPE),

      new SynopticsBitDef(SynopticsBitDef.MESS_ID_1ST_SLOT,0,4,0,SynopticsBitDef.SYNOPTICS.ID_1ST_SLOT,SynopticsBitDef.COLOR_TYPE),
      new SynopticsBitDef(SynopticsBitDef.MESS_ID_1ST_SLOT,4,12,0,SynopticsBitDef.SYNOPTICS.ID_1ST_SLOT,SynopticsBitDef.ID_TYPE),

      new SynopticsBitDef(SynopticsBitDef.MESS_ID_2ND_SLOT,0,4,0,SynopticsBitDef.SYNOPTICS.ID_2ND_SLOT,SynopticsBitDef.COLOR_TYPE),
      new SynopticsBitDef(SynopticsBitDef.MESS_ID_2ND_SLOT,4,12,0,SynopticsBitDef.SYNOPTICS.ID_2ND_SLOT,SynopticsBitDef.ID_TYPE),

      new SynopticsBitDef(SynopticsBitDef.MESS_ID_3RD_SLOT,0,4,0,SynopticsBitDef.SYNOPTICS.ID_3RD_SLOT,SynopticsBitDef.COLOR_TYPE),
      new SynopticsBitDef(SynopticsBitDef.MESS_ID_3RD_SLOT,4,12,0,SynopticsBitDef.SYNOPTICS.ID_3RD_SLOT,SynopticsBitDef.ID_TYPE),

      new SynopticsBitDef(SynopticsBitDef.MESS_ID_4TH_SLOT,0,4,0,SynopticsBitDef.SYNOPTICS.ID_4TH_SLOT,SynopticsBitDef.COLOR_TYPE),
      new SynopticsBitDef(SynopticsBitDef.MESS_ID_4TH_SLOT,4,12,0,SynopticsBitDef.SYNOPTICS.ID_4TH_SLOT,SynopticsBitDef.ID_TYPE),

      new SynopticsBitDef(SynopticsBitDef.MESS_ID_5TH_SLOT,0,4,0,SynopticsBitDef.SYNOPTICS.ID_5TH_SLOT,SynopticsBitDef.COLOR_TYPE),
      new SynopticsBitDef(SynopticsBitDef.MESS_ID_5TH_SLOT,4,12,0,SynopticsBitDef.SYNOPTICS.ID_5TH_SLOT,SynopticsBitDef.ID_TYPE),

      new SynopticsBitDef(SynopticsBitDef.MESS_ID_6TH_SLOT,0,4,0,SynopticsBitDef.SYNOPTICS.ID_6TH_SLOT,SynopticsBitDef.COLOR_TYPE),
      new SynopticsBitDef(SynopticsBitDef.MESS_ID_6TH_SLOT,4,12,0,SynopticsBitDef.SYNOPTICS.ID_6TH_SLOT,SynopticsBitDef.ID_TYPE),

      new SynopticsBitDef(SynopticsBitDef.MESS_M_HOT_SLOT_A,0,4,0,SynopticsBitDef.SYNOPTICS.V_HOT_SLOT,SynopticsBitDef.COLOR_TYPE),
      new SynopticsBitDef(SynopticsBitDef.MESS_M_HOT_SLOT_A,4,12,0,SynopticsBitDef.SYNOPTICS.V_HOT_SLOT,SynopticsBitDef.VOLT_TYPE),
      new SynopticsBitDef(SynopticsBitDef.MESS_M_HOT_SLOT_B,0,4,0,SynopticsBitDef.SYNOPTICS.T_HOT_SLOT,SynopticsBitDef.COLOR_TYPE),
      new SynopticsBitDef(SynopticsBitDef.MESS_M_HOT_SLOT_B,4,12,0,SynopticsBitDef.SYNOPTICS.T_HOT_SLOT,SynopticsBitDef.TEMP_TYPE),

      new SynopticsBitDef(SynopticsBitDef.MESS_M_TEST_SLOT_A,0,4,0,SynopticsBitDef.SYNOPTICS.V_TEST_SLOT,SynopticsBitDef.COLOR_TYPE),
      new SynopticsBitDef(SynopticsBitDef.MESS_M_TEST_SLOT_A,4,12,0,SynopticsBitDef.SYNOPTICS.V_TEST_SLOT,SynopticsBitDef.VOLT_TYPE),
      new SynopticsBitDef(SynopticsBitDef.MESS_M_TEST_SLOT_B,0,4,0,SynopticsBitDef.SYNOPTICS.T_TEST_SLOT,SynopticsBitDef.COLOR_TYPE),
      new SynopticsBitDef(SynopticsBitDef.MESS_M_TEST_SLOT_B,4,12,0,SynopticsBitDef.SYNOPTICS.T_TEST_SLOT,SynopticsBitDef.TEMP_TYPE),

      new SynopticsBitDef(SynopticsBitDef.MESS_M_1ST_SLOT_A,0,4,0,SynopticsBitDef.SYNOPTICS.V_1ST_SLOT,SynopticsBitDef.COLOR_TYPE),
      new SynopticsBitDef(SynopticsBitDef.MESS_M_1ST_SLOT_A,4,12,0,SynopticsBitDef.SYNOPTICS.V_1ST_SLOT,SynopticsBitDef.VOLT_TYPE),
      new SynopticsBitDef(SynopticsBitDef.MESS_M_1ST_SLOT_B,0,4,0,SynopticsBitDef.SYNOPTICS.T_1ST_SLOT,SynopticsBitDef.COLOR_TYPE),
      new SynopticsBitDef(SynopticsBitDef.MESS_M_1ST_SLOT_B,4,12,0,SynopticsBitDef.SYNOPTICS.T_1ST_SLOT,SynopticsBitDef.TEMP_TYPE),

      new SynopticsBitDef(SynopticsBitDef.MESS_M_2ND_SLOT_A,0,4,0,SynopticsBitDef.SYNOPTICS.V_2ND_SLOT,SynopticsBitDef.COLOR_TYPE),
      new SynopticsBitDef(SynopticsBitDef.MESS_M_2ND_SLOT_A,4,12,0,SynopticsBitDef.SYNOPTICS.V_2ND_SLOT,SynopticsBitDef.VOLT_TYPE),
      new SynopticsBitDef(SynopticsBitDef.MESS_M_2ND_SLOT_B,0,4,0,SynopticsBitDef.SYNOPTICS.T_2ND_SLOT,SynopticsBitDef.COLOR_TYPE),
      new SynopticsBitDef(SynopticsBitDef.MESS_M_2ND_SLOT_B,4,12,0,SynopticsBitDef.SYNOPTICS.T_2ND_SLOT,SynopticsBitDef.TEMP_TYPE),

      new SynopticsBitDef(SynopticsBitDef.MESS_M_3RD_SLOT_A,0,4,0,SynopticsBitDef.SYNOPTICS.V_3RD_SLOT,SynopticsBitDef.COLOR_TYPE),
      new SynopticsBitDef(SynopticsBitDef.MESS_M_3RD_SLOT_A,4,12,0,SynopticsBitDef.SYNOPTICS.V_3RD_SLOT,SynopticsBitDef.VOLT_TYPE),
      new SynopticsBitDef(SynopticsBitDef.MESS_M_3RD_SLOT_B,0,4,0,SynopticsBitDef.SYNOPTICS.T_3RD_SLOT,SynopticsBitDef.COLOR_TYPE),
      new SynopticsBitDef(SynopticsBitDef.MESS_M_3RD_SLOT_B,4,12,0,SynopticsBitDef.SYNOPTICS.T_3RD_SLOT,SynopticsBitDef.TEMP_TYPE),

      new SynopticsBitDef(SynopticsBitDef.MESS_M_4TH_SLOT_A,0,4,0,SynopticsBitDef.SYNOPTICS.V_4TH_SLOT,SynopticsBitDef.COLOR_TYPE),
      new SynopticsBitDef(SynopticsBitDef.MESS_M_4TH_SLOT_A,4,12,0,SynopticsBitDef.SYNOPTICS.V_4TH_SLOT,SynopticsBitDef.VOLT_TYPE),
      new SynopticsBitDef(SynopticsBitDef.MESS_M_4TH_SLOT_B,0,4,0,SynopticsBitDef.SYNOPTICS.T_4TH_SLOT,SynopticsBitDef.COLOR_TYPE),
      new SynopticsBitDef(SynopticsBitDef.MESS_M_4TH_SLOT_B,4,12,0,SynopticsBitDef.SYNOPTICS.T_4TH_SLOT,SynopticsBitDef.TEMP_TYPE),

      new SynopticsBitDef(SynopticsBitDef.MESS_M_5TH_SLOT_A,0,4,0,SynopticsBitDef.SYNOPTICS.V_5TH_SLOT,SynopticsBitDef.COLOR_TYPE),
      new SynopticsBitDef(SynopticsBitDef.MESS_M_5TH_SLOT_A,4,12,0,SynopticsBitDef.SYNOPTICS.V_5TH_SLOT,SynopticsBitDef.VOLT_TYPE),
      new SynopticsBitDef(SynopticsBitDef.MESS_M_5TH_SLOT_B,0,4,0,SynopticsBitDef.SYNOPTICS.T_5TH_SLOT,SynopticsBitDef.COLOR_TYPE),
      new SynopticsBitDef(SynopticsBitDef.MESS_M_5TH_SLOT_B,4,12,0,SynopticsBitDef.SYNOPTICS.T_5TH_SLOT,SynopticsBitDef.TEMP_TYPE),

      new SynopticsBitDef(SynopticsBitDef.MESS_M_6TH_SLOT_A,0,4,0,SynopticsBitDef.SYNOPTICS.V_6TH_SLOT,SynopticsBitDef.COLOR_TYPE),
      new SynopticsBitDef(SynopticsBitDef.MESS_M_6TH_SLOT_A,4,12,0,SynopticsBitDef.SYNOPTICS.V_6TH_SLOT,SynopticsBitDef.VOLT_TYPE),
      new SynopticsBitDef(SynopticsBitDef.MESS_M_6TH_SLOT_B,0,4,0,SynopticsBitDef.SYNOPTICS.T_6TH_SLOT,SynopticsBitDef.COLOR_TYPE),
      new SynopticsBitDef(SynopticsBitDef.MESS_M_6TH_SLOT_B,4,12,0,SynopticsBitDef.SYNOPTICS.T_6TH_SLOT,SynopticsBitDef.TEMP_TYPE),
    };

    public DragAndDrop()
    {
      InitializeComponent();
      mBoxes = new PictureBox[] {  pbBattery1,  pbBattery2,  pbBattery3,
                                        pbBattery4,  pbBattery5,  pbBattery6,
                                        pbBattery7,  pbBattery8,  pbBattery9,
                                        pbBattery10, pbSlot1,     pbSlot2,
                                        pbSlot3,     pbSlot4,     pbSlot5,
                                        pbSlot6,     pbSlot7,     pbSlot8,
                                        pbSlot9};
      mBoxesNames = new string[] {  "pbBattery1", "pbBattery2", "pbBattery3",
                                    "pbBattery4", "pbBattery5", "pbBattery6",
                                    "pbBattery7", "pbBattery8", "pbBattery9",
                                    "pbBattery10",
                                    "pbSlot1","pbSlot2","pbSlot3",
                                    "pbSlot4","pbSlot5","pbSlot6",
                                    "pbSlot7","pbSlot8","pbSlot9"};
      mConnectBoxes = new CheckBox[] {  cbConnect1, cbConnect2, cbConnect3,
                                        cbConnect4, cbConnect5, cbConnect6,
                                        cbConnect7, cbConnect8, cbConnect9 };


      SynMessages.SetForm(this);

      foreach (var box in mBoxes)
      {
        box.AllowDrop = true;
        box.DragDrop += PictureBox_DragDrop;
        box.DragEnter += PictureBox_DragEnter;
        box.MouseClick += PictureBox_MouseClick;
        box.MouseMove += PictureBox_MouseMove;
        box.Paint += PictureBox_Paint;
      }

      mBatterys[0] = new Battery(1, 85.23, 0.001, 0.12);
      mBatterys[1] = new Battery(2, 85.23, 0.0012, 0.05);
      mBatterys[2] = new Battery(3, 70.23, 0.001, 0.13);
      mBatterys[3] = new Battery(4, 70.23, 0.0014, 0.06);
      mBatterys[4] = new Battery(5, 70.23, 0.001, 0.17);
      mBatterys[5] = new Battery(6, 70.23, 0.0021, 0.01);
      mBatterys[6] = new Battery(7, 70.23, 0.0013, 0.21);
      mBatterys[7] = new Battery(8, 80.23, 0.0009, 0.08);
      mBatterys[8] = new Battery(9, 90.23, 0.002, 0.22);
      mBatterys[9] = new Battery(10, 0.23, 0.0011, 0.12);


      mSynopticSlot[0] = new SynopticSlot(SynopticsBitDef.SYNOPTICS.ID_1ST_SLOT, SynopticsBitDef.SYNOPTICS.B_1ST_COL_TOP);
      mSynopticSlot[1] = new SynopticSlot(SynopticsBitDef.SYNOPTICS.ID_2ND_SLOT, SynopticsBitDef.SYNOPTICS.B_2ND_COL_TOP);
      mSynopticSlot[2] = new SynopticSlot(SynopticsBitDef.SYNOPTICS.ID_3RD_SLOT, SynopticsBitDef.SYNOPTICS.B_3RD_COL_TOP);
      mSynopticSlot[3] = new SynopticSlot(SynopticsBitDef.SYNOPTICS.ID_4TH_SLOT, SynopticsBitDef.SYNOPTICS.B_1ST_COL_BOTTOM);
      mSynopticSlot[4] = new SynopticSlot(SynopticsBitDef.SYNOPTICS.ID_5TH_SLOT, SynopticsBitDef.SYNOPTICS.B_2ND_COL_BOTTOM);
      mSynopticSlot[5] = new SynopticSlot(SynopticsBitDef.SYNOPTICS.ID_6TH_SLOT, SynopticsBitDef.SYNOPTICS.B_3RD_COL_BOTTOM);
      mSynopticSlot[6] = new SynopticSlot(SynopticsBitDef.SYNOPTICS.ID_HOT_SLOT, SynopticsBitDef.SYNOPTICS.B_HOT_SLOT);
      mSynopticSlot[7] = new SynopticSlot(SynopticsBitDef.SYNOPTICS.ID_SPARE_SLOT, SynopticsBitDef.SYNOPTICS.B_SPARE_SLOT);
      mSynopticSlot[8] = new SynopticSlot(SynopticsBitDef.SYNOPTICS.ID_TEST_SLOT, SynopticsBitDef.SYNOPTICS.B_TEST_SLOT);

      mSynopticSlot[0].AddConnectionLine(SynopticsBitDef.SYNOPTICS.L_1ST_COL_1ST_SLOT);
      mSynopticSlot[1].AddConnectionLine(SynopticsBitDef.SYNOPTICS.L_2ND_COL_1ST_SLOT);
      mSynopticSlot[2].AddConnectionLine(SynopticsBitDef.SYNOPTICS.L_3RD_COL_1ST_SLOT);
      mSynopticSlot[3].AddConnectionLine(SynopticsBitDef.SYNOPTICS.L_1ST_COL_2ND_SLOT);
      mSynopticSlot[4].AddConnectionLine(SynopticsBitDef.SYNOPTICS.L_2ND_COL_2ND_SLOT);
      mSynopticSlot[5].AddConnectionLine(SynopticsBitDef.SYNOPTICS.L_3RD_COL_2ND_SLOT);
      mSynopticSlot[6].AddConnectionLine(SynopticsBitDef.SYNOPTICS.L_HOT_CHARGE);

      mSynopticSlot[0].AddVoltsText(SynopticsBitDef.SYNOPTICS.V_1ST_SLOT);
      mSynopticSlot[1].AddVoltsText(SynopticsBitDef.SYNOPTICS.V_2ND_SLOT);
      mSynopticSlot[2].AddVoltsText(SynopticsBitDef.SYNOPTICS.V_3RD_SLOT);
      mSynopticSlot[3].AddVoltsText(SynopticsBitDef.SYNOPTICS.V_4TH_SLOT);
      mSynopticSlot[4].AddVoltsText(SynopticsBitDef.SYNOPTICS.V_5TH_SLOT);
      mSynopticSlot[5].AddVoltsText(SynopticsBitDef.SYNOPTICS.V_6TH_SLOT);
      mSynopticSlot[6].AddVoltsText(SynopticsBitDef.SYNOPTICS.V_HOT_SLOT);
      mSynopticSlot[8].AddVoltsText(SynopticsBitDef.SYNOPTICS.V_TEST_SLOT);

      mSynopticSlot[0].AddTopLine(SynopticsBitDef.SYNOPTICS.L_1ST_COL_1ST_SEG);
      mSynopticSlot[1].AddTopLine(SynopticsBitDef.SYNOPTICS.L_2ND_COL_1ST_SEG);
      mSynopticSlot[2].AddTopLine(SynopticsBitDef.SYNOPTICS.L_3RD_COL_1ST_SEG);
      mSynopticSlot[3].AddTopLine(SynopticsBitDef.SYNOPTICS.L_1ST_COL_1ST_SEG);
      mSynopticSlot[4].AddTopLine(SynopticsBitDef.SYNOPTICS.L_2ND_COL_1ST_SEG);
      mSynopticSlot[5].AddTopLine(SynopticsBitDef.SYNOPTICS.L_3RD_COL_1ST_SEG);
      mSynopticSlot[3].AddBottomLine(SynopticsBitDef.SYNOPTICS.L_1ST_COL_2ND_SEG, SynopticsBitDef.SYNOPTICS.L_1ST_COL_1ST_SLOT);
      mSynopticSlot[4].AddBottomLine(SynopticsBitDef.SYNOPTICS.L_2ND_COL_2ND_SEG, SynopticsBitDef.SYNOPTICS.L_2ND_COL_1ST_SLOT);
      mSynopticSlot[5].AddBottomLine(SynopticsBitDef.SYNOPTICS.L_3RD_COL_2ND_SEG, SynopticsBitDef.SYNOPTICS.L_2ND_COL_1ST_SLOT);

      for (int i = 0; i < MAX_BATTERIES; i++)
      {
        mOutsideSlots[i] = mBatterys[i];
      }

      mDrawingList.Insert((int)SynopticsBitDef.SYNOPTICS.C1_SINGLE_CHARGER, new Box(10, 80, 60, 105, 3, Color.Lime));
      mDrawingList.Insert((int)SynopticsBitDef.SYNOPTICS.C3_THREE_CHARGER, new Box(60, 10, 310, 35, 3, Color.Lime));

      mDrawingList.Insert((int)SynopticsBitDef.SYNOPTICS.B_1ST_COL_TOP, new Box(90, 80, 140, 105, 3, Color.LightGray));
      mDrawingList.Insert((int)SynopticsBitDef.SYNOPTICS.B_1ST_COL_BOTTOM, new Box(90, 150, 140, 175, 3, Color.LightGray));

      mDrawingList.Insert((int)SynopticsBitDef.SYNOPTICS.B_2ND_COL_TOP, new Box(170, 80, 220, 105, 3, Color.LightGray));
      mDrawingList.Insert((int)SynopticsBitDef.SYNOPTICS.B_2ND_COL_BOTTOM, new Box(170, 150, 220, 175, 3, Color.LightGray));

      mDrawingList.Insert((int)SynopticsBitDef.SYNOPTICS.B_3RD_COL_TOP, new Box(250, 80, 300, 105, 3, Color.LightGray));
      mDrawingList.Insert((int)SynopticsBitDef.SYNOPTICS.B_3RD_COL_BOTTOM, new Box(250, 150, 300, 175, 3, Color.LightGray));

      mDrawingList.Insert((int)SynopticsBitDef.SYNOPTICS.B_HOT_SLOT, new Box(10, 205, 60, 230, 3, Color.LightGray));
      mDrawingList.Insert((int)SynopticsBitDef.SYNOPTICS.B_SPARE_SLOT, new Box(90, 205, 140, 230, 3, Color.LightGray));
      mDrawingList.Insert((int)SynopticsBitDef.SYNOPTICS.B_TEST_SLOT, new Box(260, 205, 310, 230, 3, Color.LightGray));

      mDrawingList.Insert((int)SynopticsBitDef.SYNOPTICS.L_HOT_CHARGE, new Line(35, 105, 35, 205, 3, Color.LightGray));

      mDrawingList.Insert((int)SynopticsBitDef.SYNOPTICS.L_1ST_COL_1ST_SEG, new Line(75, 38, 75, 94, 3, Color.LightGray));
      mDrawingList.Insert((int)SynopticsBitDef.SYNOPTICS.L_1ST_COL_2ND_SEG, new Line(75, 95, 75, 165, 3, Color.Red));
      mDrawingList.Insert((int)SynopticsBitDef.SYNOPTICS.L_1ST_COL_1ST_SLOT, new Line(75, 93, 90, 93, 3, Color.Red));
      mDrawingList.Insert((int)SynopticsBitDef.SYNOPTICS.L_1ST_COL_2ND_SLOT, new Line(75, 165, 90, 165, 3, Color.Red));

      mDrawingList.Insert((int)SynopticsBitDef.SYNOPTICS.L_2ND_COL_1ST_SEG, new Line(155, 38, 155, 94, 3, Color.LightGray));
      mDrawingList.Insert((int)SynopticsBitDef.SYNOPTICS.L_2ND_COL_2ND_SEG, new Line(155, 95, 155, 165, 3, Color.Red));
      mDrawingList.Insert((int)SynopticsBitDef.SYNOPTICS.L_2ND_COL_1ST_SLOT, new Line(155, 93, 170, 93, 3, Color.Red));
      mDrawingList.Insert((int)SynopticsBitDef.SYNOPTICS.L_2ND_COL_2ND_SLOT, new Line(155, 165, 170, 165, 3, Color.Red));

      mDrawingList.Insert((int)SynopticsBitDef.SYNOPTICS.L_3RD_COL_1ST_SEG, new Line(235, 39, 235, 92, 3, Color.LightGray));
      mDrawingList.Insert((int)SynopticsBitDef.SYNOPTICS.L_3RD_COL_2ND_SEG, new Line(235, 93, 235, 165, 3, Color.Red));
      mDrawingList.Insert((int)SynopticsBitDef.SYNOPTICS.L_3RD_COL_1ST_SLOT, new Line(235, 93, 250, 93, 3, Color.Red));
      mDrawingList.Insert((int)SynopticsBitDef.SYNOPTICS.L_3RD_COL_2ND_SLOT, new Line(235, 165, 250, 165, 3, Color.Red));

      mDrawingList.Insert((int)SynopticsBitDef.SYNOPTICS.A1_SINGLE_AMPS, new Text(15, 82, 3, Color.Magenta, "20A"));
      mDrawingList.Insert((int)SynopticsBitDef.SYNOPTICS.A2_FIRST_AMPS, new Text(97, 12, 3, Color.Magenta, "20A"));
      mDrawingList.Insert((int)SynopticsBitDef.SYNOPTICS.A3_SECOND_AMPS, new Text(178, 12, 3, Color.Magenta, "20A"));
      mDrawingList.Insert((int)SynopticsBitDef.SYNOPTICS.A4_THIRD_AMPS, new Text(256, 12, 3, Color.Magenta, "20A"));

      mDrawingList.Insert((int)SynopticsBitDef.SYNOPTICS.ID_HOT_SLOT, new Text(15, 207, 3, Color.White, "  1"));
      mDrawingList.Insert((int)SynopticsBitDef.SYNOPTICS.ID_TEST_SLOT, new Text(265, 207, 3, Color.Red, "  3"));
      mDrawingList.Insert((int)SynopticsBitDef.SYNOPTICS.ID_SPARE_SLOT, new Text(98, 207, 3, Color.DarkSlateGray, "  2"));
      mDrawingList.Insert((int)SynopticsBitDef.SYNOPTICS.ID_1ST_SLOT, new Text(98, 82, 3, Color.Orange, "  4"));
      mDrawingList.Insert((int)SynopticsBitDef.SYNOPTICS.ID_2ND_SLOT, new Text(179, 82, 3, Color.Yellow, "10"));
      mDrawingList.Insert((int)SynopticsBitDef.SYNOPTICS.ID_3RD_SLOT, new Text(264, 82, 3, Color.Lime, "11"));
      mDrawingList.Insert((int)SynopticsBitDef.SYNOPTICS.ID_4TH_SLOT, new Text(98, 152, 3, Color.Blue, "12"));
      mDrawingList.Insert((int)SynopticsBitDef.SYNOPTICS.ID_5TH_SLOT, new Text(179, 152, 3, Color.DarkOrchid, "14"));
      mDrawingList.Insert((int)SynopticsBitDef.SYNOPTICS.ID_6TH_SLOT, new Text(264, 152, 3, Color.Magenta, "67"));

      mDrawingList.Insert((int)SynopticsBitDef.SYNOPTICS.V_HOT_SLOT, new Text(9, 54, 3, Color.Cyan, "12.5V"));
      mDrawingList.Insert((int)SynopticsBitDef.SYNOPTICS.V_TEST_SLOT, new Text(188, 207, 3, Color.Goldenrod, "12.5V"));
      mDrawingList.Insert((int)SynopticsBitDef.SYNOPTICS.V_1ST_SLOT, new Text(84, 54, 3, Color.Gray, " 22C"));
      mDrawingList.Insert((int)SynopticsBitDef.SYNOPTICS.V_2ND_SLOT, new Text(163, 54, 3, Color.DodgerBlue, "12.5V"));
      mDrawingList.Insert((int)SynopticsBitDef.SYNOPTICS.V_3RD_SLOT, new Text(249, 54, 3, Color.Green, "13.5V"));
      mDrawingList.Insert((int)SynopticsBitDef.SYNOPTICS.V_4TH_SLOT, new Text(84, 124, 3, Color.HotPink, "12.5V"));
      mDrawingList.Insert((int)SynopticsBitDef.SYNOPTICS.V_5TH_SLOT, new Text(163, 124, 3, Color.Magenta, "14.5V"));
      mDrawingList.Insert((int)SynopticsBitDef.SYNOPTICS.V_6TH_SLOT, new Text(249, 124, 3, Color.Magenta, "67.8V"));

      mDrawingList.Insert((int)SynopticsBitDef.SYNOPTICS.T_HOT_SLOT, new Text(9, 54, 3, Color.Cyan, "12.5V"));
      mDrawingList.Insert((int)SynopticsBitDef.SYNOPTICS.T_TEST_SLOT, new Text(188, 207, 3, Color.Goldenrod, "12.5V"));
      mDrawingList.Insert((int)SynopticsBitDef.SYNOPTICS.T_1ST_SLOT, new Text(84, 54, 3, Color.Gray, " 22C"));
      mDrawingList.Insert((int)SynopticsBitDef.SYNOPTICS.T_2ND_SLOT, new Text(163, 54, 3, Color.DodgerBlue, "12.5V"));
      mDrawingList.Insert((int)SynopticsBitDef.SYNOPTICS.T_3RD_SLOT, new Text(249, 54, 3, Color.Green, "13.5V"));
      mDrawingList.Insert((int)SynopticsBitDef.SYNOPTICS.T_4TH_SLOT, new Text(84, 124, 3, Color.HotPink, "12.5V"));
      mDrawingList.Insert((int)SynopticsBitDef.SYNOPTICS.T_5TH_SLOT, new Text(163, 124, 3, Color.Magenta, "14.5V"));
      mDrawingList.Insert((int)SynopticsBitDef.SYNOPTICS.T_6TH_SLOT, new Text(249, 124, 3, Color.Magenta, "67.8V"));

      SynMessages.GetInstance().SetColor(SynopticsBitDef.SYNOPTICS.C1_SINGLE_CHARGER, DrawingObjects.STANDARD_COLOR.GREEN);
      SynMessages.GetInstance().SetColor(SynopticsBitDef.SYNOPTICS.C3_THREE_CHARGER, DrawingObjects.STANDARD_COLOR.GREEN);
      SynMessages.GetInstance().SetColor(SynopticsBitDef.SYNOPTICS.B_1ST_COL_TOP, DrawingObjects.STANDARD_COLOR.GRAY);
      SynMessages.GetInstance().SetColor(SynopticsBitDef.SYNOPTICS.B_1ST_COL_BOTTOM, DrawingObjects.STANDARD_COLOR.GRAY);
      SynMessages.GetInstance().SetColor(SynopticsBitDef.SYNOPTICS.B_2ND_COL_TOP, DrawingObjects.STANDARD_COLOR.GRAY);
      SynMessages.GetInstance().SetColor(SynopticsBitDef.SYNOPTICS.B_2ND_COL_BOTTOM, DrawingObjects.STANDARD_COLOR.GRAY);
      SynMessages.GetInstance().SetColor(SynopticsBitDef.SYNOPTICS.B_3RD_COL_TOP, DrawingObjects.STANDARD_COLOR.GRAY);
      SynMessages.GetInstance().SetColor(SynopticsBitDef.SYNOPTICS.B_3RD_COL_BOTTOM, DrawingObjects.STANDARD_COLOR.GRAY);
      SynMessages.GetInstance().SetColor(SynopticsBitDef.SYNOPTICS.B_HOT_SLOT, DrawingObjects.STANDARD_COLOR.GRAY);
      SynMessages.GetInstance().SetColor(SynopticsBitDef.SYNOPTICS.B_SPARE_SLOT, DrawingObjects.STANDARD_COLOR.GRAY);
      SynMessages.GetInstance().SetColor(SynopticsBitDef.SYNOPTICS.B_TEST_SLOT, DrawingObjects.STANDARD_COLOR.GRAY);
      SynMessages.GetInstance().SetColor(SynopticsBitDef.SYNOPTICS.L_HOT_CHARGE, DrawingObjects.STANDARD_COLOR.BLACK);

      SynMessages.GetInstance().SetColor(SynopticsBitDef.SYNOPTICS.L_1ST_COL_1ST_SEG, DrawingObjects.STANDARD_COLOR.GRAY);
      SynMessages.GetInstance().SetColor(SynopticsBitDef.SYNOPTICS.L_1ST_COL_2ND_SEG, DrawingObjects.STANDARD_COLOR.GRAY);
      SynMessages.GetInstance().SetColor(SynopticsBitDef.SYNOPTICS.L_1ST_COL_1ST_SLOT, DrawingObjects.STANDARD_COLOR.BLACK);
      SynMessages.GetInstance().SetColor(SynopticsBitDef.SYNOPTICS.L_1ST_COL_2ND_SLOT, DrawingObjects.STANDARD_COLOR.BLACK);

      SynMessages.GetInstance().SetColor(SynopticsBitDef.SYNOPTICS.L_2ND_COL_1ST_SEG, DrawingObjects.STANDARD_COLOR.GRAY);
      SynMessages.GetInstance().SetColor(SynopticsBitDef.SYNOPTICS.L_2ND_COL_2ND_SEG, DrawingObjects.STANDARD_COLOR.GRAY);
      SynMessages.GetInstance().SetColor(SynopticsBitDef.SYNOPTICS.L_2ND_COL_1ST_SLOT, DrawingObjects.STANDARD_COLOR.BLACK);
      SynMessages.GetInstance().SetColor(SynopticsBitDef.SYNOPTICS.L_2ND_COL_2ND_SLOT, DrawingObjects.STANDARD_COLOR.BLACK);

      SynMessages.GetInstance().SetColor(SynopticsBitDef.SYNOPTICS.L_3RD_COL_1ST_SEG, DrawingObjects.STANDARD_COLOR.GRAY);
      SynMessages.GetInstance().SetColor(SynopticsBitDef.SYNOPTICS.L_3RD_COL_2ND_SEG, DrawingObjects.STANDARD_COLOR.GRAY);
      SynMessages.GetInstance().SetColor(SynopticsBitDef.SYNOPTICS.L_3RD_COL_1ST_SLOT, DrawingObjects.STANDARD_COLOR.BLACK);
      SynMessages.GetInstance().SetColor(SynopticsBitDef.SYNOPTICS.L_3RD_COL_2ND_SLOT, DrawingObjects.STANDARD_COLOR.BLACK);

      SynMessages.GetInstance().SetColor(SynopticsBitDef.SYNOPTICS.A1_SINGLE_AMPS, DrawingObjects.STANDARD_COLOR.GREEN);
      SynMessages.GetInstance().SetText(SynopticsBitDef.SYNOPTICS.A1_SINGLE_AMPS, 224);
      SynMessages.GetInstance().SetColor(SynopticsBitDef.SYNOPTICS.A2_FIRST_AMPS, DrawingObjects.STANDARD_COLOR.GREEN);
      SynMessages.GetInstance().SetText(SynopticsBitDef.SYNOPTICS.A2_FIRST_AMPS, 68);
      SynMessages.GetInstance().SetColor(SynopticsBitDef.SYNOPTICS.A3_SECOND_AMPS, DrawingObjects.STANDARD_COLOR.GREEN);
      SynMessages.GetInstance().SetText(SynopticsBitDef.SYNOPTICS.A3_SECOND_AMPS, 111);
      SynMessages.GetInstance().SetColor(SynopticsBitDef.SYNOPTICS.A4_THIRD_AMPS, DrawingObjects.STANDARD_COLOR.GREEN);
      SynMessages.GetInstance().SetText(SynopticsBitDef.SYNOPTICS.A4_THIRD_AMPS,0xFFFF);

      SynMessages.GetInstance().SetColor(SynopticsBitDef.SYNOPTICS.ID_HOT_SLOT, DrawingObjects.STANDARD_COLOR.BLACK);
      SynMessages.GetInstance().SetText(SynopticsBitDef.SYNOPTICS.ID_HOT_SLOT, 0);
      SynMessages.GetInstance().SetColor(SynopticsBitDef.SYNOPTICS.ID_TEST_SLOT, DrawingObjects.STANDARD_COLOR.BLACK);
      SynMessages.GetInstance().SetText(SynopticsBitDef.SYNOPTICS.ID_TEST_SLOT, 0);
      SynMessages.GetInstance().SetColor(SynopticsBitDef.SYNOPTICS.ID_SPARE_SLOT, DrawingObjects.STANDARD_COLOR.BLACK);
      SynMessages.GetInstance().SetText(SynopticsBitDef.SYNOPTICS.ID_SPARE_SLOT, 0);

      SynMessages.GetInstance().SetColor(SynopticsBitDef.SYNOPTICS.ID_1ST_SLOT, DrawingObjects.STANDARD_COLOR.BLACK);
      SynMessages.GetInstance().SetText(SynopticsBitDef.SYNOPTICS.ID_1ST_SLOT, 0);
      SynMessages.GetInstance().SetColor(SynopticsBitDef.SYNOPTICS.ID_2ND_SLOT, DrawingObjects.STANDARD_COLOR.BLACK);
      SynMessages.GetInstance().SetText(SynopticsBitDef.SYNOPTICS.ID_2ND_SLOT, 0);
      SynMessages.GetInstance().SetColor(SynopticsBitDef.SYNOPTICS.ID_3RD_SLOT, DrawingObjects.STANDARD_COLOR.BLACK);
      SynMessages.GetInstance().SetText(SynopticsBitDef.SYNOPTICS.ID_3RD_SLOT, 0);
      SynMessages.GetInstance().SetColor(SynopticsBitDef.SYNOPTICS.ID_4TH_SLOT, DrawingObjects.STANDARD_COLOR.BLACK);
      SynMessages.GetInstance().SetText(SynopticsBitDef.SYNOPTICS.ID_4TH_SLOT, 0);
      SynMessages.GetInstance().SetColor(SynopticsBitDef.SYNOPTICS.ID_5TH_SLOT, DrawingObjects.STANDARD_COLOR.BLACK);
      SynMessages.GetInstance().SetText(SynopticsBitDef.SYNOPTICS.ID_5TH_SLOT, 0);
      SynMessages.GetInstance().SetColor(SynopticsBitDef.SYNOPTICS.ID_6TH_SLOT, DrawingObjects.STANDARD_COLOR.BLACK);
      SynMessages.GetInstance().SetText(SynopticsBitDef.SYNOPTICS.ID_6TH_SLOT, 0);

      SynMessages.GetInstance().SetColor(SynopticsBitDef.SYNOPTICS.V_HOT_SLOT, DrawingObjects.STANDARD_COLOR.BLACK);
      SynMessages.GetInstance().SetText(SynopticsBitDef.SYNOPTICS.V_HOT_SLOT, 0);
      SynMessages.GetInstance().SetColor(SynopticsBitDef.SYNOPTICS.V_TEST_SLOT, DrawingObjects.STANDARD_COLOR.BLACK);
      SynMessages.GetInstance().SetText(SynopticsBitDef.SYNOPTICS.V_TEST_SLOT, 0);
      SynMessages.GetInstance().SetColor(SynopticsBitDef.SYNOPTICS.V_1ST_SLOT, DrawingObjects.STANDARD_COLOR.BLACK);
      SynMessages.GetInstance().SetText(SynopticsBitDef.SYNOPTICS.V_1ST_SLOT, 0);
      SynMessages.GetInstance().SetColor(SynopticsBitDef.SYNOPTICS.V_2ND_SLOT, DrawingObjects.STANDARD_COLOR.BLACK);
      SynMessages.GetInstance().SetText(SynopticsBitDef.SYNOPTICS.V_2ND_SLOT, 0);
      SynMessages.GetInstance().SetColor(SynopticsBitDef.SYNOPTICS.V_3RD_SLOT, DrawingObjects.STANDARD_COLOR.BLACK);
      SynMessages.GetInstance().SetText(SynopticsBitDef.SYNOPTICS.V_3RD_SLOT, 0);
      SynMessages.GetInstance().SetColor(SynopticsBitDef.SYNOPTICS.V_4TH_SLOT, DrawingObjects.STANDARD_COLOR.BLACK);
      SynMessages.GetInstance().SetText(SynopticsBitDef.SYNOPTICS.V_4TH_SLOT, 0);
      SynMessages.GetInstance().SetColor(SynopticsBitDef.SYNOPTICS.V_5TH_SLOT, DrawingObjects.STANDARD_COLOR.BLACK);
      SynMessages.GetInstance().SetText(SynopticsBitDef.SYNOPTICS.V_5TH_SLOT, 0);
      SynMessages.GetInstance().SetColor(SynopticsBitDef.SYNOPTICS.V_6TH_SLOT, DrawingObjects.STANDARD_COLOR.BLACK);
      SynMessages.GetInstance().SetText(SynopticsBitDef.SYNOPTICS.V_6TH_SLOT, 0);

      SynMessages.GetInstance().SetColor(SynopticsBitDef.SYNOPTICS.T_HOT_SLOT, DrawingObjects.STANDARD_COLOR.BLACK);
      SynMessages.GetInstance().SetText(SynopticsBitDef.SYNOPTICS.T_HOT_SLOT, 0);
      SynMessages.GetInstance().SetColor(SynopticsBitDef.SYNOPTICS.T_TEST_SLOT, DrawingObjects.STANDARD_COLOR.BLACK);
      SynMessages.GetInstance().SetText(SynopticsBitDef.SYNOPTICS.T_TEST_SLOT, 0);
      SynMessages.GetInstance().SetColor(SynopticsBitDef.SYNOPTICS.T_1ST_SLOT, DrawingObjects.STANDARD_COLOR.BLACK);
      SynMessages.GetInstance().SetText(SynopticsBitDef.SYNOPTICS.T_1ST_SLOT, 0);
      SynMessages.GetInstance().SetColor(SynopticsBitDef.SYNOPTICS.T_2ND_SLOT, DrawingObjects.STANDARD_COLOR.BLACK);
      SynMessages.GetInstance().SetText(SynopticsBitDef.SYNOPTICS.T_2ND_SLOT, 0);
      SynMessages.GetInstance().SetColor(SynopticsBitDef.SYNOPTICS.T_3RD_SLOT, DrawingObjects.STANDARD_COLOR.BLACK);
      SynMessages.GetInstance().SetText(SynopticsBitDef.SYNOPTICS.T_3RD_SLOT, 0);
      SynMessages.GetInstance().SetColor(SynopticsBitDef.SYNOPTICS.T_4TH_SLOT, DrawingObjects.STANDARD_COLOR.BLACK);
      SynMessages.GetInstance().SetText(SynopticsBitDef.SYNOPTICS.T_4TH_SLOT, 0);
      SynMessages.GetInstance().SetColor(SynopticsBitDef.SYNOPTICS.T_5TH_SLOT, DrawingObjects.STANDARD_COLOR.BLACK);
      SynMessages.GetInstance().SetText(SynopticsBitDef.SYNOPTICS.T_5TH_SLOT, 0);
      SynMessages.GetInstance().SetColor(SynopticsBitDef.SYNOPTICS.T_6TH_SLOT, DrawingObjects.STANDARD_COLOR.BLACK);
      SynMessages.GetInstance().SetText(SynopticsBitDef.SYNOPTICS.T_6TH_SLOT, 0);


      for (int i=0;i<MAX_SLOTS;i++)
      {
        mSlotStatus[i] = new SlotStatus();
        mPrevSlotStatus[i] = new SlotStatus();
      }
    }

    private void PictureBox_DragDrop(object sender, DragEventArgs e)
    {
      var target = (PictureBox)sender;
      if (e.Data.GetDataPresent(typeof(PictureBox)))
      {
        var source = (PictureBox)e.Data.GetData(typeof(PictureBox));
        if (source != target)
        {
          Console.WriteLine("Do DragDrop from " + source.Name + " to " + target.Name);
          // You can swap the images out, replace the target image, etc.
          SwapImages(source, target);

          mSelected = null;
          SelectBox(target);
          return;
        }
      }
      Console.WriteLine("Don't do DragDrop");
    }

    /// <summary>
    /// Set the target's accepted DragDropEffect. Should match the source.
    /// </summary>
    /// <param name="sender"></param>
    /// <param name="e"></param>
    private void PictureBox_DragEnter(object sender, DragEventArgs e)
    {
      e.Effect = DragDropEffects.Move;
    }

    /// <summary>
    /// Handle mouse click on picture box
    /// </summary>
    /// <param name="sender"></param>
    /// <param name="e"></param>
    private void PictureBox_MouseClick(object sender, MouseEventArgs e)
    {
      SelectBox((PictureBox)sender);
    }

    /// <summary>
    /// Only start DragDrop if the mouse moves. Allows MouseClick to trigger
    /// </summary>
    /// <param name="sender"></param>
    /// <param name="e"></param>
    private void PictureBox_MouseMove(object sender, MouseEventArgs e)
    {
      if (e.Button == MouseButtons.Left)
      {
        var pb = (PictureBox)sender;
        if (pb.Image != null)
        {
          pb.DoDragDrop(pb, DragDropEffects.Move);
        }
      }
    }

    /// <summary>
    /// Override paint so we can draw a border on a selected image
    /// </summary>
    /// <param name="sender"></param>
    /// <param name="e"></param>
    private void PictureBox_Paint(object sender, PaintEventArgs e)
    {
      var pb = (PictureBox)sender;
      pb.BackColor = Color.White;
      if (mSelected == pb)
      {
        ControlPaint.DrawBorder(e.Graphics, pb.ClientRectangle,
           Color.Blue, 5, ButtonBorderStyle.Solid,  // Left
           Color.Blue, 5, ButtonBorderStyle.Solid,  // Top
           Color.Blue, 5, ButtonBorderStyle.Solid,  // Right
           Color.Blue, 5, ButtonBorderStyle.Solid); // Bottom
      }
    }

    /// <summary>
    /// Set the selected image, and trigger repaint on all boxes.
    /// </summary>
    /// <param name="pb"></param>
    private void SelectBox(PictureBox pb)
    {
      if (mSelected != pb)
      {
        mSelected = pb;
      }
      else
      {
        mSelected = null;
      }

      // Cause each box to repaint
      foreach (var box in mBoxes) box.Invalidate();
    }

    /// <summary>
    /// Swap images between two PictureBoxes
    /// </summary>
    /// <param name="source"></param>
    /// <param name="target"></param>
    private void SwapImages(PictureBox source, PictureBox target)
    {
      if (source.Image == null && target.Image == null)
      {
        return;
      }

      Battery sourceBattery;
      Battery targetBattery;
      int sourceIndex = -1;
      int targetIndex = -1;

      for (int i = 0; i < MAX_BOXES; i++)
      {
        if (source.Name == mBoxesNames[i])
        {
          sourceIndex = i;
        }
        if (target.Name == mBoxesNames[i])
        {
          targetIndex = i;
        }
      }

      if ((-1 == sourceIndex) || (-1 == targetIndex))
      {
        MessageBox.Show("Could not find index!!!");
        return;
      }

      if (sourceIndex >= MAX_BATTERIES)
      {
        sourceBattery = mSlots[sourceIndex - MAX_BATTERIES];
      }
      else
      {
        sourceBattery = mOutsideSlots[sourceIndex];
      }

      if (targetIndex >= MAX_BATTERIES)
      {
        targetBattery = mSlots[targetIndex - MAX_BATTERIES];
        mSlots[targetIndex - MAX_BATTERIES] = sourceBattery;
      }
      else
      {
        targetBattery = mOutsideSlots[targetIndex];
        mOutsideSlots[targetIndex] = sourceBattery;
      }

      if (sourceIndex >= MAX_BATTERIES)
      {
        mSlots[sourceIndex - MAX_BATTERIES] = targetBattery;
      }
      else
      {
        mOutsideSlots[sourceIndex] = targetBattery;
      }

      Console.WriteLine(source.Name + " " + target.Name);

      var temp = target.Image;
      target.Image = source.Image;
      source.Image = temp;
    }

    private void tDisplay_Tick(object sender, EventArgs e)
    {
      lSynoptics.Invalidate();
      mBatteryHistory.FillTextBox(tbStatus);

      lvBatteries.Items.Clear();
      for (int i = 0; i < MAX_BATTERIES; i++)
      {
        ListViewItem item = new ListViewItem(mBatterys[i].ID.ToString());
        item.SubItems.Add(mBatterys[i].ChargeLevel.ToString("F3"));
        lvBatteries.Items.Add(item);
      }

      lvSlots.Items.Clear();
      for (int i = 0; i < MAX_SLOTS; i++)
      {
        ListViewItem item = new ListViewItem((i + 1).ToString());
        item.SubItems.Add(mSlotStatus[i].ID.ToString());
        item.SubItems.Add(mSlotStatus[i].Connected.ToString());
        item.SubItems.Add(mSlotStatus[i].Volts.ToString());
        item.SubItems.Add(mSlotStatus[i].Charging.ToString());
        item.SubItems.Add(mSlotStatus[i].Charged.ToString());
        lvSlots.Items.Add(item);
      }

      lStatus.Text = mRelays[0].ToString() + " " + mRelays[3].ToString() + "\n\r" +
                     mRelays[1].ToString() + " " + mRelays[4].ToString() + "\n\r" +
                     mRelays[2].ToString() + " " + mRelays[5].ToString() + "\n\r" +
                      mChargeCount[0].ToString() + " " +
                      mChargeCount[1].ToString() + " " +
                      mChargeCount[2].ToString();

    }

    private void tBattery_Tick(object sender, EventArgs e)
    {
      foreach (Battery curBattery in mOutsideSlots)
      {
        if (null != curBattery)
        {
          curBattery.DischargeTick();
        }
      }
      for (int i = 0; i < MAX_SLOTS; i++)
      {
        if (null != mSlots[i])
        {
          if (true == mSlotStatus[i].Charging)
          {
            mSlots[i].ChargeTick();
          }
        }
      }
      mBatteryFile.Check();
      InputAcquisition();
      ModeLogic();
      ConditionMonitor();
    }

    public double checkVoltage(Battery battery, CheckBox connection, double volt)
    {
      if ((null != battery) && (true == connection.Checked))
      {
        return volt;
      }
      return 0;
    }

    public void InputAcquisition()
    {
      if (null != mSlots[HOT_SLOT])
      {
        mSlotStatus[HOT_SLOT].ID = mSlots[HOT_SLOT].ID;
        if (mConnectBoxes[HOT_SLOT].Checked)
        {
          mSlotStatus[HOT_SLOT].Connected = true;
        }
        else
        {
          mSlotStatus[HOT_SLOT].Connected = false;
        }
      }
      else
      {
        mSlotStatus[HOT_SLOT].ID = -1;
        mSlotStatus[HOT_SLOT].Connected = false;
        mSlotStatus[HOT_SLOT].Charged = false;
        mSlotStatus[HOT_SLOT].Charging = false;
      }
      mSlotStatus[HOT_SLOT].Volts = checkVoltage(mSlots[HOT_SLOT], mConnectBoxes[HOT_SLOT], 12.0);
      if (mSlotStatus[HOT_SLOT].Volts > 0)
      {
        if (mSlots[HOT_SLOT].ChargeLevel > 99.9)
        {
          mSlotStatus[HOT_SLOT].Charged = true;
        }
        else
        {
          mSlotStatus[HOT_SLOT].Charged = false;
        }
      }
      else
      {
        mSlotStatus[HOT_SLOT].Charged = false;
      }

      for (int i = 0; i < MAX_SLOTS; i++)
      {
        if (null != mSlots[i])
        {
          mSlotStatus[i].ID = mSlots[i].ID;
          if (mConnectBoxes[i].Checked)
          {
            mSlotStatus[i].Connected = true;
          }
          else
          {
            mSlotStatus[i].Connected = false;
          }
        }
        else
        {
          mSlotStatus[i].ID = -1;
          mSlotStatus[i].Connected = false;
          mSlotStatus[i].Charged = false;
          mSlotStatus[i].Charging = false;
          mConnectBoxes[i].Checked = false;
        }
      }

      for (int i = 0; i < MAX_CHARGE_SLOTS; i++)
      {
        mSlotStatus[i].Volts = checkVoltage(mSlots[i], mConnectBoxes[i], 12.0);
        if (mSlotStatus[i].Volts > 0)
        {
          if (mSlots[i].ChargeLevel > 99.9)
          {
            mSlotStatus[i].Charged = true;
          }
          else
          {
            mSlotStatus[i].Charged = false;
          }
        }
        else
        {
          mSlotStatus[i].Charged = false;
        }
      }

      mSlotStatus[TEST_SLOT].Volts = checkVoltage(mSlots[TEST_SLOT], mConnectBoxes[TEST_SLOT], 12.0);
    }

    public void ModeLogic()
    {
      // Handle the hot slot
      if (mSlotStatus[HOT_SLOT].Volts > 0)
      {
        mSlotStatus[HOT_SLOT].Charging = true;
      }
      else
      {
        mSlotStatus[HOT_SLOT].Charging = false;
      }

      // Handle the charging slots
      for (int i = 0; i < MAX_CHARGE_COLS; i++)
      {
        int row1 = i;
        int row2 = i + MAX_CHARGE_COLS;

        // Nothing on the row is charging
        if ((false == mSlotStatus[row1].Charging) && (false == mSlotStatus[row2].Charging))
        {
          mChargeCount[i] = 0;
          if (mSlotStatus[row1].Volts > 0)
          {
            mRelays[row1] = true;
            mRelays[row2] = false;
            mSlotStatus[row1].Charging = true;
          }
          if (mSlotStatus[row2].Volts > 0)
          {
            mRelays[row1] = true;
            mRelays[row2] = true;
            mSlotStatus[row2].Charging = true;
          }
        }
        else
        {
          if ((true == mSlotStatus[row1].Charged) && (true == mSlotStatus[row2].Charged) && (mChargeCount[i] < 100))
          {
            mChargeCount[i]++;
          }
          else
          {
            mChargeCount[i] = 0;
            if (true == mSlotStatus[row1].Charging)
            {
              if ((true == mSlotStatus[row1].Charged) && (mSlotStatus[row2].Volts > 0))
              {
                mRelays[row1] = true;
                mRelays[row2] = true;
                mSlotStatus[row2].Charging = true;
                mSlotStatus[row1].Charging = false;
              }
            }
            else
            {
              if ((true == mSlotStatus[row2].Charged) && (mSlotStatus[row1].Volts > 0))
              {
                mRelays[row1] = true;
                mRelays[row2] = false;
                mSlotStatus[row2].Charging = false;
                mSlotStatus[row1].Charging = true;
              }
            }
          }
        }
      }
    }

    public void ConditionMonitor()
    {
      for(int i=0;i<MAX_SLOTS;i++)
      {
        if (mSlotStatus[i].ID != mPrevSlotStatus[i].ID)
        {
          if (-1 == mSlotStatus[i].ID)
          {
            BatteryInfo curBattery = mBatteryFile.GetInfo(mPrevSlotStatus[i].ID);
            curBattery.Remove();
            mSynopticSlot[i].TakeOut();
            mSynopticSlot[i].HideVolts();
          }
          else
          {
            BatteryInfo curBattery = mBatteryFile.GetInfo(mSlotStatus[i].ID);
            curBattery.Insert();
            mSynopticSlot[i].PutIn(mSlotStatus[i].ID);
          }
        }

        if (mSlotStatus[i].Connected != mPrevSlotStatus[i].Connected)
        {
          if (true == mSlotStatus[i].Connected)
          {
            mSynopticSlot[i].Connect();
          }
          else
          {
            mSynopticSlot[i].Disconnect();
            mSynopticSlot[i].HideVolts();
          }
        }

        if (true == mSlotStatus[i].Charged)
        {
          mSynopticSlot[i].Charged();
        }
        else
        {
          if (true == mSlotStatus[i].Charging)
          {
            mSynopticSlot[i].Charging();
          }
        }

        mSynopticSlot[i].ShowVolts(mSlotStatus[i].Volts);

        mPrevSlotStatus[i] = new SlotStatus(mSlotStatus[i]);
      }
    }

    private void DragAndDrop_Paint(object sender, PaintEventArgs e)
    {
      int i;
      Graphics graph = lSynoptics.CreateGraphics();

      for (i = 0; i < mDrawingList.Count; i++)
      {
        mDrawingList[i].Draw(graph, 0, 0);
      }
    }

    private void lvSlots_SelectedIndexChanged(object sender, EventArgs e)
    {

    }

    private void pbSlot5_Click(object sender, EventArgs e)
    {

    }

    private void lSynoptics_Click(object sender, EventArgs e)
    {

    }

    private void lSynoptics_Paint(object sender, PaintEventArgs e)
    {
      for (int i = 0; i < mDrawingList.Count; i++)
      {
        if ((i == (int)SynopticsBitDef.SYNOPTICS.T_1ST_SLOT) ||
           (i == (int)SynopticsBitDef.SYNOPTICS.T_HOT_SLOT) ||
           (i == (int)SynopticsBitDef.SYNOPTICS.T_6TH_SLOT) ||
           (i == (int)SynopticsBitDef.SYNOPTICS.T_5TH_SLOT) ||
           (i == (int)SynopticsBitDef.SYNOPTICS.T_4TH_SLOT) ||
           (i == (int)SynopticsBitDef.SYNOPTICS.T_3RD_SLOT) ||
           (i == (int)SynopticsBitDef.SYNOPTICS.T_2ND_SLOT) ||
           (i == (int)SynopticsBitDef.SYNOPTICS.T_1ST_SLOT))
        {

      }
        else
        {
          mDrawingList[i].Draw(e.Graphics, 0, 0);
        }
      }
    }

    public void SetVoltText(int outlineItem, int theValue)
    {
      int theDec = theValue & 0xF;
      int theBase = theValue & 0x7F0;
      theBase = theBase >> 4;

      if (theBase >= 64)
      {
        theBase -= 129;
        if (0 == theDec)
        {
          theBase++;
        }
      }

      float theFloat = (float)((float)(theDec) / 16.0);
      theFloat += theBase;

      Text myText = (Text)mDrawingList[outlineItem];
      myText.SetText(theFloat.ToString("F1") + "V");
    }

    public void SetIDText(int outlineItem, int theValue)
    {
      Text myText = (Text)mDrawingList[outlineItem];
      if (theValue > 9)
      {
        myText.SetText(theValue.ToString(""));
      }
      else
      {
        myText.SetText("  "+theValue.ToString(""));
      }
    }


    public void SetAmpsText(int outlineItem, int theValue)
    {
      int theDec = theValue & 0xF;
      int theBase = theValue & 0xFFF0;
      theBase = theBase >> 4;

      float theFloat = (float)((float)(theDec) / 16.0);

      if (theBase >= 2047)
      {
        theBase = 0xFFE - theBase;
        if (0 == theDec)
        {
          theBase++;
        }
        theFloat *= (float)-1.0;
      }

      theFloat += theBase;

      if ((theBase>9)||(theFloat < 0.0))
      {
        Text myText = (Text)mDrawingList[outlineItem];
        myText.SetText(theFloat.ToString("F0") + "A");
      }
      else
      {
        Text myText = (Text)mDrawingList[outlineItem];
        myText.SetText(theFloat.ToString("F1") + "A");
      }
    }

    public void SetTempText(int outlineItem, int theValue)
    {
      int theDec = theValue & 0xF;
      int theBase = theValue & 0xFFF0;
      theBase = theBase >> 4;

      if (theBase >= 128)
      {
        theBase -= 257;
        if (0 == theDec)
        {
          theBase++;
        }
      }

      float theFloat = (float)((float)(theDec) / 16.0);
      theFloat += theBase;

      Text myText = (Text)mDrawingList[outlineItem];
      myText.SetText(theFloat.ToString("F1") + "C");
    }

    public void SetTextColor(int outlineItem, int theColor)
    {
      Text myText = (Text)mDrawingList[outlineItem];
      myText.SetStandardColor((DrawingObjects.STANDARD_COLOR)theColor);
    }

    Color GetALineColor(int item)
    {
      Line myLine = (Line)mDrawingList[item];
      return (myLine.GetColor());
    }
    public DrawingObjects.STANDARD_COLOR GetColor(int item)
    {
      DrawingObjects myLine = mDrawingList[item];
      return myLine.GetStandardColor();
    }

    public void SetALineColor(int item, int theColor)
    {
      Line myLine = (Line)mDrawingList[item];
      myLine.SetStandardColor((DrawingObjects.STANDARD_COLOR)theColor);
    }

    public void SetABoxColor(int item, int theColor)
    {
      DrawingObjects myBox = mDrawingList[item];
      myBox.SetStandardColor((DrawingObjects.STANDARD_COLOR)theColor);
    }

    private void DragAndDrop_FormClosing(object sender, FormClosingEventArgs e)
    {
      mBatteryFile.Save();
    }

    /*
        public void SetAMessage(int label, int data)
        {
          for (int index = 0; index < synopticsBitDeffList.Length; index++)
          {
            if (label == synopticsBitDeffList[index].mLabel)
            {
              int length = synopticsBitDeffList[index].mLength;
              int bit = 22 - (synopticsBitDeffList[index].mBit - 11) - length;
              long newClearMask = (1 << length) - 1;
              long mask = newClearMask << bit;
              long value = data & mask;
              value = value >> bit;
              synopticsBitDeffList[index].bitSet((int)value, this);
            }
          }
        }
    */
  }

  public class SlotStatus
  {
    int mID = -1;
    bool mConnected = false;
    double mVolts = 0.0;
    bool mCharging = false;
    bool mCharged = false;
    public int ID
    {
      get { return mID; }
      set { mID = value; }
    }

    public bool Charged
    {
      get { return mCharged; }
      set { mCharged = value; }
    }

    public bool Charging
    {
      get { return mCharging; }
      set { mCharging = value; }
    }

    public bool Connected
    {
      get { return mConnected; }
      set { mConnected = value; }
    }

    public double Volts
    {
      get { return mVolts; }
      set { mVolts = value; }
    }

    public SlotStatus()
    {

    }
    
    public SlotStatus(SlotStatus other)
    {
      mID = other.ID;
      mConnected = other.Connected;
      mVolts = other.Volts;
      mCharged = other.Charged;
      mCharging = other.Charging;
    }
  }


  public class SynopticSlot
  {
    private SynopticsBitDef.SYNOPTICS mVolts;
    private SynopticsBitDef.SYNOPTICS mConnectedLine;
    private SynopticsBitDef.SYNOPTICS mText;
    private SynopticsBitDef.SYNOPTICS mBox;
    private int mID = 0;
    private DrawingObjects.STANDARD_COLOR mVoltsColor = DrawingObjects.STANDARD_COLOR.BLACK;
    private SynopticsBitDef.SYNOPTICS mTopLine;
    private SynopticsBitDef.SYNOPTICS mBottomLine;
    private SynopticsBitDef.SYNOPTICS mCheckLine;

    public SynopticSlot(SynopticsBitDef.SYNOPTICS text, SynopticsBitDef.SYNOPTICS box)
    {
      mText = text;
      mBox = box;
      mConnectedLine = SynopticsBitDef.SYNOPTICS.END_DRAWING_LIST;
      mVolts = SynopticsBitDef.SYNOPTICS.END_DRAWING_LIST;
      mCheckLine = SynopticsBitDef.SYNOPTICS.END_DRAWING_LIST;
      mTopLine = SynopticsBitDef.SYNOPTICS.END_DRAWING_LIST;
      mBottomLine = SynopticsBitDef.SYNOPTICS.END_DRAWING_LIST;
    }

    public void AddConnectionLine(SynopticsBitDef.SYNOPTICS connectLine)
    {
      mConnectedLine = connectLine;
    }

    public void AddTopLine(SynopticsBitDef.SYNOPTICS connectLine)
    {
      mTopLine = connectLine;
    }

    public void AddBottomLine(SynopticsBitDef.SYNOPTICS connectLine, SynopticsBitDef.SYNOPTICS checkLine)
    {
      mBottomLine = connectLine;
      mCheckLine = checkLine;
    }

    public void AddVoltsText(SynopticsBitDef.SYNOPTICS volts)
    {
      mVolts = volts;
    }

    public void PutIn(int id)
    {
      mID = id;
      SynMessages.GetInstance().SetColor(mText, DrawingObjects.STANDARD_COLOR.WHITE);
      SynMessages.GetInstance().SetText(mText, mID);
      SynMessages.GetInstance().SetColor(mBox, DrawingObjects.STANDARD_COLOR.WHITE);
    }

    public void TakeOut()
    {
      mID = 0;
      SynMessages.GetInstance().SetColor(mText, DrawingObjects.STANDARD_COLOR.BLACK);
      SynMessages.GetInstance().SetText(mText, mID);
      SynMessages.GetInstance().SetColor(mBox, DrawingObjects.STANDARD_COLOR.GRAY);
      mVoltsColor = DrawingObjects.STANDARD_COLOR.BLACK;

      if (SynopticsBitDef.SYNOPTICS.END_DRAWING_LIST != mTopLine)
      {
        if (SynopticsBitDef.SYNOPTICS.END_DRAWING_LIST == mCheckLine)
        {
          SynMessages.GetInstance().SetColor(mTopLine, DrawingObjects.STANDARD_COLOR.BLACK);
        }
        else
        {
          if ((DrawingObjects.STANDARD_COLOR.BLACK == SynMessages.GetInstance().GetColor(mCheckLine)))
          {
            if (SynopticsBitDef.SYNOPTICS.END_DRAWING_LIST != mTopLine)
            {
              SynMessages.GetInstance().SetColor(mTopLine, DrawingObjects.STANDARD_COLOR.BLACK);
            }
          }
        }
      }

      if (SynopticsBitDef.SYNOPTICS.END_DRAWING_LIST != mBottomLine)
      {
        SynMessages.GetInstance().SetColor(mBottomLine, DrawingObjects.STANDARD_COLOR.BLACK);
      }
    }

    public void Connect()
    {
      SynMessages.GetInstance().SetColor(mText, DrawingObjects.STANDARD_COLOR.WHITE);
      SynMessages.GetInstance().SetText(mText, mID);
      SynMessages.GetInstance().SetColor(mBox, DrawingObjects.STANDARD_COLOR.WHITE);
      mVoltsColor = DrawingObjects.STANDARD_COLOR.WHITE;

      if (SynopticsBitDef.SYNOPTICS.END_DRAWING_LIST != mConnectedLine)
      {
        SynMessages.GetInstance().SetColor(mConnectedLine, DrawingObjects.STANDARD_COLOR.WHITE);
      }

      if (SynopticsBitDef.SYNOPTICS.END_DRAWING_LIST != mTopLine)
      {
        if (SynopticsBitDef.SYNOPTICS.END_DRAWING_LIST == mCheckLine)
        {
          SynMessages.GetInstance().SetColor(mTopLine, DrawingObjects.STANDARD_COLOR.WHITE);
        }
        else
        {
          if ((DrawingObjects.STANDARD_COLOR.BLACK == SynMessages.GetInstance().GetColor(mCheckLine)) ||
            (DrawingObjects.STANDARD_COLOR.WHITE == SynMessages.GetInstance().GetColor(mCheckLine)))
          {
            if (SynopticsBitDef.SYNOPTICS.END_DRAWING_LIST != mTopLine)
            {
              SynMessages.GetInstance().SetColor(mTopLine, DrawingObjects.STANDARD_COLOR.WHITE);
            }
          }
        }
      }

      if (SynopticsBitDef.SYNOPTICS.END_DRAWING_LIST != mBottomLine)
      {
        SynMessages.GetInstance().SetColor(mBottomLine, DrawingObjects.STANDARD_COLOR.WHITE);
      }

    }

    public void Charging()
    {
      SynMessages.GetInstance().SetColor(mText, DrawingObjects.STANDARD_COLOR.YELLOW);
      SynMessages.GetInstance().SetText(mText, mID);
      SynMessages.GetInstance().SetColor(mBox, DrawingObjects.STANDARD_COLOR.YELLOW);
      mVoltsColor = DrawingObjects.STANDARD_COLOR.YELLOW;

      if (SynopticsBitDef.SYNOPTICS.END_DRAWING_LIST != mConnectedLine)
      {
        SynMessages.GetInstance().SetColor(mConnectedLine, DrawingObjects.STANDARD_COLOR.YELLOW);
      }

      if (SynopticsBitDef.SYNOPTICS.END_DRAWING_LIST != mTopLine)
      {
        if (SynopticsBitDef.SYNOPTICS.END_DRAWING_LIST == mCheckLine)
        {
          SynMessages.GetInstance().SetColor(mTopLine, DrawingObjects.STANDARD_COLOR.YELLOW);
        }
        else
        {
          if ((DrawingObjects.STANDARD_COLOR.BLACK == SynMessages.GetInstance().GetColor(mCheckLine)) ||
            (DrawingObjects.STANDARD_COLOR.WHITE == SynMessages.GetInstance().GetColor(mCheckLine)))
          {
            if (SynopticsBitDef.SYNOPTICS.END_DRAWING_LIST != mTopLine)
            {
              SynMessages.GetInstance().SetColor(mTopLine, DrawingObjects.STANDARD_COLOR.YELLOW);
            }
          }
        }
      }

      if (SynopticsBitDef.SYNOPTICS.END_DRAWING_LIST != mBottomLine)
      {
        SynMessages.GetInstance().SetColor(mBottomLine, DrawingObjects.STANDARD_COLOR.YELLOW);
      }
    }

    public void Charged()
    {
      SynMessages.GetInstance().SetColor(mText, DrawingObjects.STANDARD_COLOR.GREEN);
      SynMessages.GetInstance().SetText(mText, mID);
      SynMessages.GetInstance().SetColor(mBox, DrawingObjects.STANDARD_COLOR.GREEN);
      mVoltsColor = DrawingObjects.STANDARD_COLOR.GREEN;

      if (SynopticsBitDef.SYNOPTICS.END_DRAWING_LIST != mConnectedLine)
      {
        SynMessages.GetInstance().SetColor(mConnectedLine, DrawingObjects.STANDARD_COLOR.BLACK);
      }

      if (SynopticsBitDef.SYNOPTICS.END_DRAWING_LIST != mTopLine)
      {
        if (SynopticsBitDef.SYNOPTICS.END_DRAWING_LIST == mCheckLine)
        {
          SynMessages.GetInstance().SetColor(mTopLine, DrawingObjects.STANDARD_COLOR.GREEN);
        }
        else
        {
          if ((DrawingObjects.STANDARD_COLOR.BLACK == SynMessages.GetInstance().GetColor(mCheckLine)) ||
            (DrawingObjects.STANDARD_COLOR.WHITE == SynMessages.GetInstance().GetColor(mCheckLine)))
          {
            if (SynopticsBitDef.SYNOPTICS.END_DRAWING_LIST != mTopLine)
            {
              SynMessages.GetInstance().SetColor(mTopLine, DrawingObjects.STANDARD_COLOR.GREEN);
            }
          }
        }
      }

      if (SynopticsBitDef.SYNOPTICS.END_DRAWING_LIST != mBottomLine)
      {
        SynMessages.GetInstance().SetColor(mBottomLine, DrawingObjects.STANDARD_COLOR.GREEN);
      }
    }

    public void Disconnect()
    {
      SynMessages.GetInstance().SetColor(mText, DrawingObjects.STANDARD_COLOR.WHITE);
      SynMessages.GetInstance().SetText(mText, mID);
      SynMessages.GetInstance().SetColor(mBox, DrawingObjects.STANDARD_COLOR.WHITE);
      mVoltsColor = DrawingObjects.STANDARD_COLOR.BLACK;

      if (SynopticsBitDef.SYNOPTICS.END_DRAWING_LIST != mConnectedLine)
      {
        SynMessages.GetInstance().SetColor(mConnectedLine, DrawingObjects.STANDARD_COLOR.BLACK);
      }

      if (SynopticsBitDef.SYNOPTICS.END_DRAWING_LIST != mTopLine)
      {
        if (SynopticsBitDef.SYNOPTICS.END_DRAWING_LIST == mCheckLine)
        {
          SynMessages.GetInstance().SetColor(mTopLine, DrawingObjects.STANDARD_COLOR.BLACK);
        }
        else
        {
          if ((DrawingObjects.STANDARD_COLOR.BLACK == SynMessages.GetInstance().GetColor(mCheckLine)))
          {
            if (SynopticsBitDef.SYNOPTICS.END_DRAWING_LIST != mTopLine)
            {
              SynMessages.GetInstance().SetColor(mTopLine, DrawingObjects.STANDARD_COLOR.BLACK);
            }
          }
        }
      }

      if (SynopticsBitDef.SYNOPTICS.END_DRAWING_LIST != mBottomLine)
      {
        SynMessages.GetInstance().SetColor(mBottomLine, DrawingObjects.STANDARD_COLOR.BLACK);
      }


    }

    public void ShowVolts(double volts)
    {
      int baseNum = (int)volts;
      double decNum = baseNum - volts;
      baseNum = baseNum << 4;
      baseNum += (int)decNum * 16;

      if (SynopticsBitDef.SYNOPTICS.END_DRAWING_LIST != mVolts)
      {
        SynMessages.GetInstance().SetColor(mVolts, mVoltsColor);
        SynMessages.GetInstance().SetText(mVolts, baseNum);
      }
    }

    public void HideVolts()
    {
      SynMessages.GetInstance().SetColor(mVolts, DrawingObjects.STANDARD_COLOR.BLACK);
    }

  }


  public class Battery
  {
    private int mID = -1;
    private double mChargeLevel = 0.0;
    private double mDischargeRate = 0.01;
    private double mChargeRate = 0.001;

    public int ID
    {
      get { return mID; }
      set { mID = value; }
    }

    public double ChargeLevel
    {
      get { return mChargeLevel; }
      set { mChargeLevel = value; }
    }

    public double DischargeRate
    {
      get { return mDischargeRate; }
      set { mDischargeRate = value; }
    }

    public double ChargeRate
    {
      get { return mChargeRate; }
      set { mChargeRate = value; }
    }

    public Battery()
    {

    }

    public Battery(int id, double chargeLevel,
                    double dischargeRate, double chargeRate)
    {
      mID = id;
      mChargeLevel = chargeLevel;
      mDischargeRate = dischargeRate;
      mChargeRate = chargeRate;
    }

    public void ChargeTick()
    {
      mChargeLevel += mChargeRate;
      if (mChargeLevel > 100.0)
      {
        mChargeLevel = 100.0;
      }
    }

    public void DischargeTick()
    {
      mChargeLevel -= mDischargeRate;
      if (mChargeLevel < 0.0)
      {
        mChargeLevel = 0.0;
      }
    }
  }
}
