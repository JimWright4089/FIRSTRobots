using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Drawing;
using System.Windows.Forms;

namespace DragAndDropTest
{
  class BatteryHistory
  {
    public const int STAT_UNKNOWN = -1;
    public const int STAT_NEW_BATTERY = 1;
    public const int STAT_BATTERY_INSERTED = 2;
    public const int STAT_BATTERY_REMOVED = 3;
    public const int STAT_BATTERY_SWAP = 4;
    public const int STAT_BATTERY_CHARGING = 5;
    public const int STAT_BATTERY_CHARGED = 6;
    List<BatteryHistoryLine> mList = new List<BatteryHistoryLine>();
    static BatteryHistory mInstance;

    public static BatteryHistory GetInstance()
    {
      if(null == mInstance)
      {
        mInstance = new BatteryHistory();
      }
      return mInstance;
    }

    private BatteryHistory()
    {
    }

    public BatteryHistoryLine Add(BatteryInfo battery, int code)
    {
      BatteryHistoryLine newLine = new BatteryHistoryLine(battery,code);
      mList.Add(newLine);
      return newLine;
    }

    public void FillTextBox(TextBox tbStatus)
    {
      tbStatus.Text = "";
      for(int i=mList.Count-1;i>-1;i--)
      {
        tbStatus.Text += mList[i].ToString() + "\r\n";
      }
    }
  }

  class BatteryHistoryLine
  {
    BatteryInfo mBattery;
    int mCode = BatteryHistory.STAT_UNKNOWN;
    DateTime mEntered = DateTime.MinValue;

    public BatteryHistoryLine()
    {

    }

    public BatteryHistoryLine(BatteryInfo battery, int code)
    {
      mBattery = battery;
      mCode = code;
      mEntered = DateTime.Now;
    }

    public string GetText(int check)
    {
      switch(check)
      {
        case (BatteryHistory.STAT_NEW_BATTERY):
          return "First Seen";
        case (BatteryHistory.STAT_BATTERY_INSERTED):
          return "Inserted";
        case (BatteryHistory.STAT_BATTERY_REMOVED):
          return "Removed";
        case (BatteryHistory.STAT_BATTERY_SWAP):
          return "Swapped";
        case (BatteryHistory.STAT_BATTERY_CHARGING):
          return "Charging";
        case (BatteryHistory.STAT_BATTERY_CHARGED):
          return "Charged";
  }

      return "Unknown";
    }


    override
    public String ToString()
    {
      return mBattery.RFID.ToString() + " " +
             mBattery.ID.ToString() + " " +
             GetText(mCode) + " " +
             mEntered.ToString();
    }
  }
}
