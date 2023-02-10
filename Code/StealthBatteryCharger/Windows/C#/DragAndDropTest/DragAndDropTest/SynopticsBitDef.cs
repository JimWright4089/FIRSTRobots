using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DragAndDropTest
{
  public class SynopticsBitDef
  {
    public SYNOPTICS mID;
    public int mLabel;
    public int mLocation;
    public int mLength;
    public int mType;
    public int mValue;
    public static bool mChanged = true;

    public const int LINE_TYPE = 1;
    public const int OUTLINE_TYPE = 2;
    public const int COLOR_TYPE = 3;
    public const int VOLT_TYPE = 4;
    public const int AMPS_TYPE = 5;
    public const int TEMP_TYPE = 6;
    public const int ID_TYPE = 7;

    public const int MESS_BOX1 = 192;
    public const int MESS_BOX2 = 193;
    public const int MESS_BOX3 = 194;
    public const int MESS_HOT_LINE = 195;
    public const int MESS_1ST_COL_LINE = 196;
    public const int MESS_2ND_COL_LINE = 197;
    public const int MESS_3RD_COL_LINE = 198;

    public const int MESS_SINGLE_AMPS = 200;
    public const int MESS_FIRST_AMPS = 201;
    public const int MESS_SECOND_AMPS = 202;
    public const int MESS_THIRD_AMPS = 203;

    public const int MESS_ID_HOT_SLOT = 205;
    public const int MESS_ID_TEST_SLOT = 206;
    public const int MESS_ID_SPARE_SLOT = 207;
    public const int MESS_ID_1ST_SLOT = 208;
    public const int MESS_ID_2ND_SLOT = 209;
    public const int MESS_ID_3RD_SLOT = 210;
    public const int MESS_ID_4TH_SLOT = 211;
    public const int MESS_ID_5TH_SLOT = 212;
    public const int MESS_ID_6TH_SLOT = 213;

    public const int MESS_M_HOT_SLOT_A = 214;
    public const int MESS_M_HOT_SLOT_B = 215;
    public const int MESS_M_TEST_SLOT_A = 216;
    public const int MESS_M_TEST_SLOT_B = 217;

    public const int MESS_M_1ST_SLOT_A = 218;
    public const int MESS_M_1ST_SLOT_B = 219;
    public const int MESS_M_2ND_SLOT_A = 220;
    public const int MESS_M_2ND_SLOT_B = 221;
    public const int MESS_M_3RD_SLOT_A = 222;
    public const int MESS_M_3RD_SLOT_B = 223;
    public const int MESS_M_4TH_SLOT_A = 224;
    public const int MESS_M_4TH_SLOT_B = 225;
    public const int MESS_M_5TH_SLOT_A = 226;
    public const int MESS_M_5TH_SLOT_B = 227;
    public const int MESS_M_6TH_SLOT_A = 228;
    public const int MESS_M_6TH_SLOT_B = 229;

    public enum SYNOPTICS : int
    {
      C1_SINGLE_CHARGER = 0,
      C3_THREE_CHARGER,
      B_1ST_COL_TOP,
      B_1ST_COL_BOTTOM,
      B_2ND_COL_TOP,
      B_2ND_COL_BOTTOM,
      B_3RD_COL_TOP,
      B_3RD_COL_BOTTOM,
      B_HOT_SLOT,
      B_SPARE_SLOT,
      B_TEST_SLOT,
      L_HOT_CHARGE,
      L_1ST_COL_1ST_SEG,
      L_1ST_COL_2ND_SEG,
      L_1ST_COL_1ST_SLOT,
      L_1ST_COL_2ND_SLOT,
      L_2ND_COL_1ST_SEG,
      L_2ND_COL_2ND_SEG,
      L_2ND_COL_1ST_SLOT,
      L_2ND_COL_2ND_SLOT,
      L_3RD_COL_1ST_SEG,
      L_3RD_COL_2ND_SEG,
      L_3RD_COL_1ST_SLOT,
      L_3RD_COL_2ND_SLOT,
      A1_SINGLE_AMPS,
      A2_FIRST_AMPS,
      A3_SECOND_AMPS,
      A4_THIRD_AMPS,
      ID_HOT_SLOT,
      ID_TEST_SLOT,
      ID_SPARE_SLOT,
      ID_1ST_SLOT,
      ID_2ND_SLOT,
      ID_3RD_SLOT,
      ID_4TH_SLOT,
      ID_5TH_SLOT,
      ID_6TH_SLOT,
      V_HOT_SLOT,
      V_TEST_SLOT,
      V_1ST_SLOT,
      V_2ND_SLOT,
      V_3RD_SLOT,
      V_4TH_SLOT,
      V_5TH_SLOT,
      V_6TH_SLOT,
      T_HOT_SLOT,
      T_TEST_SLOT,
      T_1ST_SLOT,
      T_2ND_SLOT,
      T_3RD_SLOT,
      T_4TH_SLOT,
      T_5TH_SLOT,
      T_6TH_SLOT,
      END_DRAWING_LIST  //This must be the last item in the list
    };

    public SynopticsBitDef(int label, int location, int length, int value, SYNOPTICS id, int type)
    {
      mID = id;
      mLabel = label;
      mLength = length;
      mLocation = location;
      mValue = value;
      mType = type;

      SynMessages.GetInstance().Add(this);
    }
  };

  public class SynMessages
  {
    static SynMessages mInstances = null;
    List<Message> mDefs = new List<Message>();
    static DragAndDrop mForm;

    public static SynMessages GetInstance()
    {
      if(null == mInstances)
      {
        mInstances = new SynMessages();
      }
      return mInstances;
    }

    private SynMessages()
    {

    }

    public static void SetForm(DragAndDrop form)
    {
      mForm = form;
    }

    public void Add(SynopticsBitDef def)
    {
      mDefs.Add(new Message(def));
    }

    public void SetColor(SynopticsBitDef.SYNOPTICS label, DrawingObjects.STANDARD_COLOR color)
    {
      SetColor(label, (int)color);
    }

    public DrawingObjects.STANDARD_COLOR GetColor(SynopticsBitDef.SYNOPTICS label)
    {
      foreach (Message currentMessage in mDefs)
      {
        if (label == currentMessage.def.mID)
        {
            return currentMessage.GetColor(mForm);
        }
      }
      return DrawingObjects.STANDARD_COLOR.BLACK;
    }

    public void SetColor(SynopticsBitDef.SYNOPTICS label, int data)
    {
      foreach (Message currentMessage in mDefs)
      {
        if (label == currentMessage.def.mID)
        {
          if ((SynopticsBitDef.OUTLINE_TYPE == currentMessage.def.mType) ||
             (SynopticsBitDef.LINE_TYPE == currentMessage.def.mType) ||
            (SynopticsBitDef.COLOR_TYPE == currentMessage.def.mType))
          {
            currentMessage.PutAChunk(data);
            currentMessage.BitSet(mForm,data);
          }
        }
      }
    }

    public void SetText(SynopticsBitDef.SYNOPTICS label, int data)
    {
      foreach (Message currentMessage in mDefs)
      {
        if (label == currentMessage.def.mID)
        {
          if ((SynopticsBitDef.ID_TYPE == currentMessage.def.mType) ||
             (SynopticsBitDef.AMPS_TYPE == currentMessage.def.mType) ||
             (SynopticsBitDef.VOLT_TYPE == currentMessage.def.mType) ||
            (SynopticsBitDef.TEMP_TYPE == currentMessage.def.mType))
          {
            currentMessage.PutAChunk(data);
            currentMessage.BitSet(mForm,data);
          }
        }
      }
    }
  }



#if JUNK
  class MessageConnection
  {
    int mLabel = 0;
    int mItem = 0;
    int mLocation = 0;
    int mSize = 0;

    public MessageConnection()
    {
    }

    public MessageConnection(int label, int item, int location, int size)
    {
      mLabel = label;
      mItem = item;
      mLocation = location;
      mSize = size;
    }

    public int label
    {
      get { return mLabel; }
    }
    public int item
    {
      get { return mItem; }
    }
    public int location
    {
      get { return mLocation; }
    }
    public int size
    {
      get { return mSize; }
    }
  }
#endif

  class Message
  {
    SynopticsBitDef mDef;

    public Message(SynopticsBitDef label)
    {
      mDef = label;
    }

    public SynopticsBitDef def
    {
      get { return mDef; }
    }

    public void PutAChunk(int data)  
    {
      // The location must be moved over to accommodate the label and SDI
      int newRollLeftValue = mDef.mLocation;

      int newSetMask = ((1 << mDef.mLength) - 1);
      newSetMask <<= newRollLeftValue;

      int newClearMask = ~newSetMask;

      int newData = data << newRollLeftValue;
      newData &= newSetMask;

      mDef.mValue &= newClearMask;
      mDef.mValue |= newData;
    }

    public void BitSet(DragAndDrop myForm,int data)
    {
      switch (mDef.mType)
      {
        case (SynopticsBitDef.LINE_TYPE):
          myForm.SetALineColor((int)mDef.mID, data);
          break;
        case (SynopticsBitDef.OUTLINE_TYPE):
          myForm.SetABoxColor((int)mDef.mID, data);
          break;
        case (SynopticsBitDef.VOLT_TYPE):
          myForm.SetVoltText((int)mDef.mID, data);
          break;
        case (SynopticsBitDef.AMPS_TYPE):
          myForm.SetAmpsText((int)mDef.mID, data);
          break;
        case (SynopticsBitDef.TEMP_TYPE):
          myForm.SetTempText((int)mDef.mID, data);
          break;
        case (SynopticsBitDef.ID_TYPE):
          myForm.SetIDText((int)mDef.mID, data);
          break;
        case (SynopticsBitDef.COLOR_TYPE):
          myForm.SetTextColor((int)mDef.mID, data);
          break;
      }
    }

    public DrawingObjects.STANDARD_COLOR GetColor(DragAndDrop myForm)
    {
      return myForm.GetColor((int)mDef.mID);
    }
  }
}
